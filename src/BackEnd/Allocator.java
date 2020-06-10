package BackEnd;

import Entity.FunctionEntity;
import Entity.ParameterEntity;
import Entity.Scope;
import ExceptionS.InternalErrorS;
import INS.*;
import Operand.*;

import java.util.*;

import static Compiler.Defines.REG_SIZE;

public class Allocator
{
	private final List<FunctionEntity> functionEntityList;
	private final List<Reference> para_register_ref;
	private final Set<Reference> caller_save_reg_ref;
	private final List<Register> colors = new LinkedList<>();

	// global
	private HashMap<Edge, Edge> edge_hash_map;
	private Set<Edge> edge_set;
	private final int K;
	private int local_offset;
	private Set<Edge> simplified_edge;

	// node set
	private final Set<Reference> precolored;
	private Set<Reference> initial;
	private Set<Reference> simplify_worklist;
	private Set<Reference> freeze_worklist;
	private Set<Reference> spill_worklist;
	private Set<Reference> spilled_nodes;
	private Set<Reference> coalesced_nodes;
	private Set<Reference> colored_nodes;
	private Set<Reference> select_worklist;
	private Stack<Reference> select_stack;

	// move set
	private Set<Move> active_moves;
	private Set<Move> worklist_moves;
	private Set<Move> frozen_moves;
	private Set<Move> constrained_moves;
	private Set<Move> coalesced_moves;

	private final Register rfp;
	private final Register rsp;
	private final Register ra0;
	private final Register rt0;
	private final Register rt1;
	private final Register rt2;
	private final Register rra;
	private final Register rgp;
	private final Register rtp;
	private final Reference rra0;


	private int iter;

	public Allocator(InstructionEmitter emitter, RegisterConfig registerConfig)
	{
		functionEntityList = emitter.getFunctionEntities();
		rgp = registerConfig.getRegisters().get(3);
		rtp = registerConfig.getRegisters().get(4);
		rra = registerConfig.getRegisters().get(1);
		rfp = registerConfig.getRegisters().get(8);
		rsp = registerConfig.getRegisters().get(2);
		ra0 = registerConfig.getRegisters().get(10);
		rt0 = registerConfig.getRegisters().get(5);
		rt1 = registerConfig.getRegisters().get(6);
		rt2 = registerConfig.getRegisters().get(7);

		para_register_ref = new LinkedList<>();
		for (Register register : registerConfig.getPara_registers())
		{
			para_register_ref.add(new Reference(register));
		}
		rra0 = para_register_ref.get(0);
		precolored = new LinkedHashSet<>();
		precolored.addAll(para_register_ref);
		precolored.add(rra0);
		for (int i = 28; i < 32; ++i)
			precolored.add(new Reference(registerConfig.getRegisters().get(i)));
		for (Reference reference : precolored)
		{
			reference.is_precolored = true;
			reference.color = reference.getRegister();
		}

		caller_save_reg_ref = new HashSet<>();
		for (Reference reference : precolored)
		{
			if (!reference.getRegister().isCallee_save())
				caller_save_reg_ref.add(reference);
		}

		for (int i = 9; i < 32; ++i)
			if (registerConfig.getRegisters().get(i).isCallee_save())
				colors.add(registerConfig.getRegisters().get(i));
		for (int i = 9; i < 32; ++i)
			if (!registerConfig.getRegisters().get(i).isCallee_save())
				colors.add(registerConfig.getRegisters().get(i));
		K = colors.size();
	}

	public void allocate()
	{
		for (FunctionEntity functionEntity : functionEntityList)
		{
			if (functionEntity.is_inlined())
				continue;
			init(functionEntity);
			load_precolored(functionEntity);
			allocate_function(functionEntity);
			Set<Reference> all_ref = new HashSet<>();
			Set<Register> reg_used = new HashSet<>();

			for (BasicBlock basicBlock : functionEntity.getBasicBlocks())
				for (Instruction ins : basicBlock.getIns())
					replace_reg_for_ins(ins, all_ref, reg_used);
			functionEntity.setAll_reference(all_ref);
			LinkedList<Register> listRegUse = new LinkedList<>(reg_used);
			functionEntity.setReg_used(listRegUse);
			if (iter != 1)
				functionEntity.getReg_used().add(rfp);
			functionEntity.setLocal_variable_offset(local_offset);
		}
	}

	private void replace_reg_for_ins(Instruction ins, Set<Reference> all_ref, Set<Register> reg_used)
	{
		for (Reference ref : ins.getAll_ref())
		{
			all_ref.add(ref);
			if (ref.color != null)
			{
				ref.set_register(ref.color);
				reg_used.add(ref.color);
			}
		}
	}

	// ===== Edge Begin =====
	private class Edge {
		Reference u, v;

		public Edge(Reference u, Reference v)
		{
			this.u = u;
			this.v = v;
		}

		@Override
		public String toString()
		{
			return "(" +  u + "," + v + ")";
		}

		@Override
		public int hashCode()
		{
			return u.hashCode() + v.hashCode();
		}

		@Override
		public boolean equals(Object obj)
		{
			Edge edge = (Edge) obj;
			return u == edge.u && v == edge.v;
		}
	}

	private Edge get_edge(Reference u, Reference v)
	{
		Edge temp_edge = new Edge(u, v);
		Edge find = edge_hash_map.get(temp_edge);
		if (find == null)
		{
			edge_hash_map.put(temp_edge, temp_edge);
			return temp_edge;
		}
		return find;
	}

	private void add_edge(Reference u, Reference v)
	{
		if (u == v) return;
		Edge edge = get_edge(u, v);
		if (!edge_set.contains(edge))
		{
			edge_set.add(edge);
			edge_set.add(get_edge(v, u));
			if (!u.is_precolored)
			{
				u.adj_list.add(v);
				u.degree++;
			}
			if (!v.is_precolored)
			{
				v.adj_list.add(u);
				v.degree++;
			}
		}
	}

	private void delete_edge(Reference u, Reference v)
	{
		Edge edge = get_edge(u, v);
		Edge edge1 = get_edge(v, u);

		if (!edge_set.contains(edge) || !edge_set.contains(edge1))
			throw new InternalErrorS("delete edge not exists");

		edge_set.remove(edge);
		edge_set.remove(edge1);

		if (!edge_hash_map.containsKey(edge) || !edge_hash_map.containsKey(edge1))
		{
			edge_hash_map.remove(edge);
			edge_hash_map.remove(edge1);
		}

		u.adj_list.remove(v);
		v.adj_list.remove(u);
		decrease_degrees(u);
		decrease_degrees(v);
	}

	// ===== Edge End =====
	private void move(Reference ref, Set<Reference> from, Set<Reference> to)
	{
		if (from.contains(ref))
		{
			if (!to.contains(ref))
			{
				from.remove(ref);
				to.add(ref);
			}
			else
				throw new InternalErrorS("already exist move " + ref.getName() + " from " + from + " to " + to);
		}
		else
			throw new InternalErrorS("empty move " + ref.getName() + " from " + from + " to " + to);
	}

	private void decrease_degrees(Reference ref)
	{
		int d = ref.degree--;
		if (d == K)
		{
			enable_moves(ref);
			ref.adj_list.forEach(this::enable_moves);

			if (spill_worklist.contains(ref))
			{
				if (is_move_related(ref))
					move(ref, spill_worklist, freeze_worklist);
				else
					move(ref, spill_worklist, simplify_worklist);
			}
		}
	}

	private boolean is_move_related(Reference ref)
	{
		for (Move move : ref.move_list)
		{
			if (active_moves.contains(move) || worklist_moves.contains(move))
				return true;
		}
		return false;
	}

	private void enable_moves(Reference ref)
	{
		for (Move move : ref.move_list)
		{
			if (active_moves.contains(move))
			{
				active_moves.remove(move);
				worklist_moves.add(move);
			}
		}
	}

	private void init(FunctionEntity entity)
	{

		// global
		edge_hash_map     = new HashMap<>();
		edge_set          = new LinkedHashSet<>();
		simplified_edge   = new LinkedHashSet<>();

		// node set (disjoint)
		simplify_worklist = new LinkedHashSet<>();
		initial           = new LinkedHashSet<>();
		freeze_worklist   = new LinkedHashSet<>();
		spill_worklist    = new LinkedHashSet<>();
		spilled_nodes     = new LinkedHashSet<>();
		coalesced_nodes   = new LinkedHashSet<>();
		colored_nodes     = new LinkedHashSet<>();
		select_worklist   = new LinkedHashSet<>();
		select_stack      = new Stack<>();

		// move set (disjoint)
		coalesced_moves   = new LinkedHashSet<>();
		constrained_moves = new LinkedHashSet<>();
		frozen_moves      = new LinkedHashSet<>();
		worklist_moves    = new LinkedHashSet<>();
		active_moves      = new LinkedHashSet<>();

		init_liveness_analysis(entity);
		local_offset = 0;
	}

	// Liveness_Analysis
	private List<BasicBlock> sorted;
	private Set<BasicBlock> visited;
	private void init_liveness_analysis(FunctionEntity entity)
	{
		sorted = new LinkedList<>();
		visited = new HashSet<>();
		ListIterator it = entity.getBasicBlocks().listIterator(entity.getBasicBlocks().size());
		while (it.hasPrevious())
		{
			BasicBlock pre = ((BasicBlock) it.previous());
			if (!visited.contains(pre))
				dfs_sort(pre);
		}
	}

	private void liveness_analysis(FunctionEntity entity)
	{
		for (BasicBlock basicBlock : entity.getBasicBlocks())
		{
			Set<Reference> def = basicBlock.getDef();
			Set<Reference> use = basicBlock.getUse();
			basicBlock.getLive_in().clear();
			basicBlock.getLive_out().clear();
			def.clear();
			use.clear();
			for (Instruction ins : basicBlock.getIns())
			{
				for (Reference reference : ins.getUse())
					if (!def.contains(reference))
						use.add(reference);
				for (Reference reference : ins.getDef())
					def.add(reference);
				if (iter == 0)
					initial.addAll(ins.getAll_ref());
			}
		}
		boolean modified = true;
		while (modified)
		{
			modified = false;
			for (BasicBlock basicBlock : sorted)
			{
				Set<Reference> new_in = new HashSet<>();
				Set<Reference> right = new HashSet<>(basicBlock.getLive_out());
				right.removeAll(basicBlock.getDef());
				new_in.addAll(basicBlock.getUse());
				new_in.addAll(right);

				Set<Reference> new_out = new HashSet<>();
				for (BasicBlock suc : basicBlock.getSuccessor())
					new_out.addAll(suc.getLive_in());
				modified |= !basicBlock.getLive_in().equals(new_in) || !basicBlock.getLive_out().equals(new_out);

				basicBlock.setLive_in(new_in);
				basicBlock.setLive_out(new_out);
			}
		}
	}

	private void dfs_sort(BasicBlock basicBlock)
	{
		sorted.add(basicBlock);
		visited.add(basicBlock);
		for (BasicBlock pre : basicBlock.getPredecessor())
			if (!visited.contains(pre))
				dfs_sort(pre);
	}

	private void load_precolored(FunctionEntity entity)
	{
		for (BasicBlock basicBlock : entity.getBasicBlocks())
		{
			List<Instruction> newIns = new LinkedList<>();
			for (Instruction raw : basicBlock.getIns())
			{
				if (raw instanceof InsCall)
				{
					//System.err.println("~~~~~call:" + ((InsCall) raw).getEntity().getAsm_name() + "~~~~~~");
					Set<Reference> para_reg_used = new HashSet<>();
					InsCall ins = (InsCall) raw;
					int i = 0, push_counter = 0;
					LinkedList<Operand> pushList = new LinkedList<>();
					for (Operand operand : ins.getOperands())
					{
						if (i < para_register_ref.size())
						{
							para_reg_used.add(para_register_ref.get(i));
							newIns.add(new Move(para_register_ref.get(i), operand));
						}
						else
						{
							if (operand instanceof Immediate)
							{
								Reference tmp = new Reference("tmp_push", Reference.Type.UNKNOWN);
								newIns.add(new Move(tmp, operand));
								operand = tmp;
							}
							pushList.add(operand);
						}
						i++;
					}
					if (pushList.size() > 0)
						newIns.add(new Sub(rsp, new Immediate(pushList.size() * REG_SIZE)));
					for (Operand operand : pushList)
					{
						newIns.add(new Move(new Reference(push_counter * REG_SIZE, rsp), operand));
						push_counter++;
					}
					InsCall nw_ins_call = new InsCall(ins.getEntity(), new LinkedList<>());
					nw_ins_call.setCallorsave(caller_save_reg_ref);
					nw_ins_call.setUsed_parameter_register(para_reg_used);
					newIns.add(nw_ins_call);
					if (push_counter > 0)
						newIns.add(new Add(rsp, new Immediate(push_counter * REG_SIZE)));
					if (ins.getRet() != null)
						newIns.add(new Move(ins.getRet(), rra0));
				}
				else if (raw instanceof InsReturn)
				{
					if (((InsReturn) raw).getRet() != null)
						newIns.add(new Move(rra0, ((InsReturn) raw).getRet()));
					newIns.add(new InsReturn(null));
				}
				else if (raw instanceof InsLabel)
				{
					if (raw == entity.getBegin_label_INS())
					{
						int i = 0;
						for (ParameterEntity parameterEntity : entity.getParameterEntityList())
						{
							if (i < para_register_ref.size())
								newIns.add(new Move(parameterEntity.getReference(), para_register_ref.get(i)));
							else
								newIns.add(new Move(parameterEntity.getReference(), parameterEntity.getSource()));
							i++;
						}
					}
					newIns.add(raw);
				}
				else if (raw instanceof Cmp)
				{
					Operand left = ((Cmp) raw).getLeft();
					Operand right = ((Cmp) raw).getRight();
					transCompare(newIns, raw, left, right);
				}
				else if (raw instanceof InsCJump && ((InsCJump) raw).getType() != InsCJump.Type.BOOL)
				{
					Operand left = ((InsCJump) raw).getLeft();
					Operand right = ((InsCJump) raw).getRight();
					transCompare(newIns, raw, left, right);
				}
				else
					newIns.add(raw);
			}
			basicBlock.setIns(newIns);

			// System.err.println("===#$%Y%*%^&%*&%&*== load_precolors ===");
			// debug_print_basic_block(basicBlock);
		}
	}

	private void debug_print_basic_block(BasicBlock basicBlock)
	{
		System.err.println("--- block-begin ---");
		for (Instruction ins : basicBlock.getIns())
		{
			System.err.println(ins);
		}
		System.err.println("--- block-end ---");
	}

	private void transCompare(List<Instruction> newIns, Instruction raw, Operand left, Operand right)
	{
		if (left.is_address() && right.is_address())
		{
			Reference tmp = new Reference("tmp_cmp", Reference.Type.UNKNOWN);
			newIns.add(new Move(tmp, left));
			if (raw instanceof Cmp)
			{
				((Cmp) raw).setLeft(tmp);
				newIns.add(raw);
				newIns.add(new Move(left, tmp));
			}
			else if (raw instanceof InsCJump)
			{
				((InsCJump) raw).setLeft(tmp);
				newIns.add(raw);
			}
		}
		else
			newIns.add(raw);
	}

	private void allocate_function(FunctionEntity functionEntity)
	{
		boolean finish;
		iter = 0;
		do {
			liveness_analysis(functionEntity);
			build(functionEntity);
			make_worklist();
			do
			{
				if (!simplify_worklist.isEmpty())
					simplify();
				else if (!worklist_moves.isEmpty())
					coalesce();
				else if (!freeze_worklist.isEmpty())
					freeze();
				else if (!spill_worklist.isEmpty())
					select_spill();
			} while (!simplify_worklist.isEmpty() || !worklist_moves.isEmpty() || !freeze_worklist.isEmpty() || !spill_worklist.isEmpty());
			assign_colors(functionEntity);
			finish = spilled_nodes.isEmpty();
			rewrite_program(functionEntity);
			iter++;
		} while (!finish);
	}

	private void build(FunctionEntity functionEntity)
	{
		simplified_edge.clear();
		edge_set.clear();
		initial.removeAll(precolored);
		for (Reference reference : initial)
			reference.reset();
		for (Reference reference : precolored)
			reference.reset();
		for (BasicBlock basicBlock : functionEntity.getBasicBlocks())
		{
			// debug_print_basic_block(basicBlock);
			HashSet<Reference> live = new HashSet<>(basicBlock.getLive_out());
			ListIterator it = basicBlock.getIns().listIterator(basicBlock.getIns().size());
			while (it.hasPrevious())
			{
				Set<Reference> tmp;
				Instruction ins = (Instruction) it.previous();

				for (Reference reference : live)
					reference.add_ref_time();

				tmp = new HashSet<>(live);
				ins.setOut(tmp);

				if (ins instanceof Move && ((Move) ins).is_ref_move())
				{
					live.removeAll(ins.getUse());
					for (Reference reference : ins.getDef())
						reference.move_list.add((Move) ins);
					for (Reference reference : ins.getUse())
						reference.move_list.add((Move) ins);
					worklist_moves.add((Move) ins);
				}
				live.addAll(ins.getDef());
				for (Reference d : ins.getDef())
					for (Reference l : live)
						add_edge(d, l);

					live.removeAll(ins.getDef());
					live.addAll(ins.getUse());

					tmp = new HashSet<>(live);
					ins.setIn(tmp);
			}
		}
	}

	private void make_worklist()
	{
		for (Reference ref : initial)
		{
			if (ref.degree >= K)
				spill_worklist.add(ref);
			else if (is_move_related(ref))
				freeze_worklist.add(ref);
			else
				simplify_worklist.add(ref);
		}
	}

	private void simplify()
	{
		Reference ref = simplify_worklist.iterator().next();

		move(ref, simplify_worklist, select_worklist);
		select_stack.push(ref);

		Set<Reference> backup = new HashSet<>(ref.adj_list);

		for (Reference adj : backup)
		{
			simplified_edge.add(new Edge(adj, ref));
			simplified_edge.add(new Edge(ref, adj));
			delete_edge(ref, adj);
		}
	}

	private Reference getAlias(Reference ref)
	{
		if (coalesced_nodes.contains(ref))
			return ref.alias = getAlias(ref.alias);
		else
			return ref;
	}

	private void add_work_list(Reference ref)
	{
		if (!precolored.contains(ref) && !is_move_related(ref) && ref.degree < K)
			move(ref, freeze_worklist, simplify_worklist);
	}

	private boolean check(Reference u, Reference v)
	{
		for (Reference to : v.adj_list)
		{
			if (!(to.degree < K || precolored.contains(to) || edge_set.contains(get_edge(to, u))))
				return false;
		}
		return true;
	}

	private boolean conservative(Reference u, Reference v)
	{
		int k = 0;
		Set<Reference> adjs = new HashSet<>();
		adjs.addAll(u.adj_list);
		adjs.addAll(v.adj_list);
		for (Reference adj : adjs)
		{
			if (adj.degree >= K)
				k++;
		}
		return k < K;
	}

	private void combine(Reference u, Reference v)
	{
		if (freeze_worklist.contains(v))
			move(v, freeze_worklist, coalesced_nodes);
		else
			move(v, spill_worklist, coalesced_nodes);

		v.alias = u;
		u.move_list.addAll(v.move_list);
		enable_moves(v);

		Set<Reference> backup = new HashSet<>(v.adj_list);

		for (Reference to : backup)
		{
			delete_edge(to, v);
			add_edge(u, to);
			if (to.degree >= K && freeze_worklist.contains(to))
				move(to, freeze_worklist, spill_worklist);
		}
		if (u.degree >= K && freeze_worklist.contains(u))
			move(u, freeze_worklist, spill_worklist);
	}

	private void coalesce()
	{
		Move move = worklist_moves.iterator().next();
		Reference x = getAlias(((Reference) move.getSrc()));
		Reference y = getAlias(((Reference) move.getDest()));
		Reference u, v;

		if (precolored.contains(y))
		{
			u = y;
			v = x;
		}
		else
		{
			u = x;
			v = y;
		}
		worklist_moves.remove(move);


		Edge edge = get_edge(u, v);
		if (u == v)
		{
			coalesced_moves.add(move);
			add_work_list(u);
		}
		else if (precolored.contains(v) || edge_set.contains(edge))
		{
			constrained_moves.add(move);
			add_work_list(u);
			add_work_list(v);
		}
		else if (precolored.contains(u) && check(u, v) || !precolored.contains(u) && conservative(u, v))
		{
			coalesced_moves.add(move);
			combine(u, v);
			add_work_list(u);
		}
		else
			active_moves.add(move);
	}
	private void freeze()
	{
		Reference u = freeze_worklist.iterator().next();
		move(u, freeze_worklist, simplify_worklist);
		freezeMove(u);
	}
	private void freezeMove(Reference u)
	{
		for (Move move : u.move_list)
		{
			if (active_moves.contains(move) || worklist_moves.contains(move))
			{
				Reference v;
				if (getAlias(((Reference) move.getSrc())) == getAlias(((Reference) move.getDest())))
					v = getAlias(((Reference) move.getSrc()));
				else
					v = getAlias(((Reference) move.getDest()));
				active_moves.remove(move);
				frozen_moves.add(move);
				boolean empty = true;
				for (Move move1 : v.move_list)
				{
					if (active_moves.contains(move1) || worklist_moves.contains(move1))
					{
						empty = false;
						break;
					}
				}
				if (empty && freeze_worklist.contains(v))
					move(v, freeze_worklist, simplify_worklist);
			}
		}
	}

	private final Set<String> protect = new HashSet<>();

	private void select_spill()
	{
		Iterator<Reference> iter = spill_worklist.iterator();
		Reference to_spill = iter.next();
		protect.add("i"); protect.add("j");
		while ((protect.contains(to_spill.getName()) || to_spill.getName().contains("spill")) && iter.hasNext())
			to_spill = iter.next();
		move(to_spill, spill_worklist, simplify_worklist);
		freezeMove(to_spill);
	}

	private void assign_colors(FunctionEntity entity)
	{
		for (Edge edge : simplified_edge)
			add_edge(getAlias(edge.u), getAlias(edge.v));
		LinkedList<Register> ok_colors = new LinkedList<>();
		while (!select_stack.empty())
		{
			Reference top = select_stack.pop();
			ok_colors.clear();
			ok_colors.addAll(colors);
			for (Reference to : top.adj_list)
			{
				to = getAlias(to);
				if (colored_nodes.contains(to) || precolored.contains(to))
					ok_colors.remove(to.color);
			}

			if (ok_colors.isEmpty())
			{
				move(top, select_worklist, spilled_nodes);
				top.color = null;
			}
			else
			{
				Register color = ok_colors.iterator().next();
				move(top, select_worklist, colored_nodes);
				top.color = color;
			}
		}
		for (Reference coalesced_node : coalesced_nodes)
			coalesced_node.color = getAlias(coalesced_node).color;
	}

	private int spilledCounter = 0;
	private void rewrite_program(FunctionEntity entity)
	{
		Set<Reference> new_tmp = new HashSet<>();
		List<Instruction> new_ins;

		for (Reference spilled_node : spilled_nodes)
		{
			spilled_node.is_spilled = true;
			local_offset += REG_SIZE;
			spilled_node.set_offset(-local_offset, rfp);
		}

		for(Reference ref:coalesced_nodes)
			getAlias(ref);

		List<Instruction> stores = new LinkedList<>();
		for (BasicBlock basicBlock : entity.getBasicBlocks())
		{
			new_ins = new LinkedList<>();
			for (Instruction ins : basicBlock.getIns())
			{
				Set<Reference> ins_use = ins.getUse();
				Set<Reference> ins_def = ins.getDef();

				stores.clear();
				if (!(ins instanceof InsLabel))
				{
					for (Reference use : ins_use)
					{
						if (use.is_spilled)
						{
							if (ins_def.contains(use))
							{
								Reference tmp = new Reference("spill_" + use.getName() + "_" + spilledCounter++, Reference.Type.UNKNOWN);
								new_tmp.add(tmp);
								new_ins.add(new Move(tmp, new Address(rfp, null, 1, use.getOffset())));
								ins.replace_all(use, tmp);
								stores.add(new Move(new Address(rfp, null, 1, use.getOffset()), tmp));
							}
							else
							{
								if (ins instanceof Move && !((Move) ins).getDest().is_address() && ((Move) ins).getSrc() == use)
									ins = new Move(((Move) ins).getDest(), new Address(rfp, null, 1, use.getOffset()));
								else
								{
									Reference tmp = new Reference("spill_" + use.getName() + "_" + spilledCounter++, Reference.Type.UNKNOWN);
									new_tmp.add(tmp);
									new_ins.add(new Move(tmp, new Address(rfp, null, 1, use.getOffset())));
									ins.replace_use(use, tmp);
								}
							}
						}
					}

					for (Reference def : ins_def)
					{
						if (def.is_spilled)
						{
							if (!ins_use.contains(def))
							{
								if (ins instanceof Move && !((Move) ins).getSrc().is_address() && ((Move) ins).getDest() == def)
									ins = new Move(new Address(rfp, null, 1, def.getOffset()), ((Move) ins).getSrc());
								else
								{
									Reference tmp = new Reference("spill_" + def.getName() + "_" + spilledCounter++, Reference.Type.UNKNOWN);
									new_tmp.add(tmp);
									ins.replace_def(def, tmp);
									stores.add(new Move(new Address(rfp, null, 1, def.getOffset()), tmp));
								}
							}
						}
					}
				}
				for (Reference ref : ins.getAll_ref())
				{
					if (coalesced_nodes.contains(ref))
						ins.replace_all(ref, getAlias(ref));
				}
				ins.init_def_and_use();
				ins.calc_def_and_use();
				if (!(ins instanceof Move && ((Move) ins).is_ref_move() && ((Move) ins).getDest()  == ((Move) ins).getSrc()))
					new_ins.add(ins);
				new_ins.addAll(stores);
			}
			basicBlock.setIns(new_ins);

//			System.err.println("--- block-begin ---");
//			for (Instruction ins : basicBlock.getIns())
//			{
//				System.err.println(ins);
//			}
//			System.err.println("--- block-end ---");
		}

		for (ParameterEntity parameterEntity : entity.getParameterEntityList())
		{
			if (coalesced_nodes.contains(parameterEntity.getReference()))
				parameterEntity.setReference(getAlias(parameterEntity.getReference()));
			if (colored_nodes.contains(parameterEntity.getReference()))
				parameterEntity.getReference().set_register(parameterEntity.getReference().color);
		}

		select_stack.clear();
		select_worklist.clear();
		spilled_nodes.clear();
		initial.clear();
		initial.addAll(colored_nodes);
		initial.addAll(coalesced_nodes);
		initial.addAll(new_tmp);
		colored_nodes.clear();
		coalesced_nodes.clear();
	}

}
