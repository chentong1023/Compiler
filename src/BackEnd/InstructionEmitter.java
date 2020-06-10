package BackEnd;

import AST.FunctionDefNode;
import BackEnd.IRBuilder;
import Entity.*;
import ExceptionS.InternalErrorS;
import INS.*;
import IR.*;
import IR.Binary.BinaryOp.*;
import Operand.*;
import Utils.AddressTuple;
import Utils.Triple;

import java.util.*;

import static Compiler.Defines.*;

public class InstructionEmitter implements IRVisitor
{
	private List<IR> global_initializer;
	private Scope global_scope;
	private List<FunctionEntity> functionEntities;

	private List<Instruction> ins;
	private FunctionEntity currentFunction;
	private boolean is_inleaf = false;

	public InstructionEmitter(IRBuilder irBuilder)
	{
		this.global_scope = irBuilder.global_scope();
		this.functionEntities = new LinkedList<>(irBuilder.functionEntities());
		this.global_initializer = irBuilder.getGlobal_initializer();
		for (ClassEntity entity : irBuilder.getAst().getClassEntityList())
		{
			for (FunctionDefNode functionDefNode : entity.getMember_functions())
				this.functionEntities.add(functionDefNode.getEntity());
		}
	}

	private List<Reference> tmp_stack;
	private int tmp_top = 0;

	public void emit()
	{
		int string_counter = 1;
		for (Entity entity : global_scope.getEntities().values())
		{
			if (entity instanceof VariableEntity)
				entity.setReference(new Reference(entity.getName(), Reference.Type.GLOBAL));
			else if (entity instanceof StringConstantEntity)
				((StringConstantEntity) entity).setAsm_name(".LC"+string_counter++);
		}
		for (FunctionEntity functionEntity : functionEntities)
		{
			currentFunction = functionEntity;
			tmp_stack = new ArrayList<Reference>();
			functionEntity.setIns(emit_function(functionEntity));
			functionEntity.setTmp_stack(tmp_stack);
		}
	}

	private List<Instruction> emit_function(FunctionEntity entity)
	{
//		System.err.println("~~~~" + entity.getName() + "~~~~");
		int call_size = entity.getCalls().size();
		for (FunctionEntity call : entity.getCalls())
		{
			if (call.is_inlined() || call.is_lib_function())
				call_size--;
		}
		if (call_size == 0)
		{
			is_inleaf = true;
			used_global = new HashSet<>();
			for (Entity global : global_scope.getEntities().values())
			{
				if (global instanceof VariableEntity)
				{
					VariableEntity local = new VariableEntity(".global_" + global.getName(), global.getLocation(), global.getType(), null);
					global_local_map.put(global, local);
					currentFunction.getScope().insert(local);
				}
			}
		}
		else is_inleaf = false;
		for (ParameterEntity parameterEntity : entity.getParameterEntityList())
		{
			parameterEntity.setReference(new Reference(parameterEntity));
			parameterEntity.setSource(new Reference(parameterEntity.getName() + "_src", Reference.Type.CANNOT_COLOR));
		}
		for (VariableEntity variableEntity : entity.all_local_variables())
		{
			variableEntity.setReference(new Reference(variableEntity));
		}
		entity.set_label_INS(getLabel(entity.getBegin_label_IR().getName()), getLabel(entity.getEnd_label_IR().getName()));
		ins = new LinkedList<>();
		for (IR ir : entity.getIrs())
		{
			tmp_top = 0;
			expr_depth = 0;
			ir.accept(this);
		}
		if (is_inleaf)
		{
			for (Entity global : used_global)
			{
				ins.add(1, new Move(trans_entity(global).getReference(), global.getReference()));
				ins.add(ins.size(), new Move(global.getReference(), trans_entity(global).getReference()));
			}
		}
		return ins;
	}

	private int expr_depth = 0;
	private Map<Entity, Entity> global_local_map = new HashMap<>();
	private Set<Entity> used_global;

	private Map<String, InsLabel> labelMap = new HashMap<>();
	private InsLabel getLabel(String name)
	{
		InsLabel ret = labelMap.get(name);
		if (ret == null)
		{
			ret = new InsLabel(name);
			labelMap.put(name, ret);
		}
		return ret;
	}

	private Entity trans_entity(Entity entity)
	{
		if (is_inleaf)
		{
			Entity ret = global_local_map.get(entity);
			if (ret != null)
			{
				used_global.add(entity);
				return ret;
			}
		}
		return entity;
	}

	private boolean check_num(Expr expr)
	{
		if (expr instanceof IntConst)
		{
			int x = ((IntConst) expr).getValue();
			return x == 1 || x == 2 || x == 4 || x == 8;
		}
		return false;
	}

	private int tmp_counter = 0;
	public Reference get_tmp()
	{
		if (Enable_Global_Register_Allocation)
			return new Reference("ref_" + tmp_counter++, Reference.Type.UNKNOWN);
		else
		{
			if (tmp_top >= tmp_stack.size())
				tmp_stack.add(new Reference("ref_" + tmp_counter++, Reference.Type.UNKNOWN));
			return tmp_stack.get(tmp_top++);
		}
	}

	private Triple<Expr, Expr, Integer> match_base_indx_mul(Expr expr)
	{
		if (!(expr instanceof Binary))
			return null;
		Binary binary = ((Binary) expr);
		Expr base = null, indx = null;
		int mul = 0;
		boolean matched = false;
		if (binary.getOperator() == Binary.BinaryOp.ADD)
		{
			if (binary.getRight() instanceof Binary && ((Binary) binary.getRight()).getOperator() == Binary.BinaryOp.MUL)
			{
				base = binary.getLeft();
				Binary right = ((Binary) binary.getRight());
				if (check_num(right.getRight()))
				{
					indx = right.getLeft();
					mul = ((IntConst) right.getRight()).getValue();
					matched = true;
				}
				else if (check_num(right.getLeft()))
				{
					indx = right.getRight();
					mul = ((IntConst) right.getLeft()).getValue();
					matched = true;
				}
			}
			else if (binary.getLeft() instanceof Binary && ((Binary) binary.getLeft()).getOperator() == Binary.BinaryOp.MUL)
			{
				base = binary.getRight();
				Binary left = ((Binary) binary.getLeft());
				if (check_num(left.getRight()))
				{
					indx = left.getLeft();
					mul = ((IntConst) left.getRight()).getValue();
					matched = true;
				}
			}
		}
		if (matched)
			return new Triple<>(base, indx, mul);
		else
			return null;
	}

	private AddressTuple match_address(Expr expr)
	{
		if (!(expr instanceof Binary))
			return null;
		Binary bin = ((Binary) expr);
		Expr base = null, indx = null;
		int mul = 1, add = 0;
		boolean matched = false;
		Triple<Expr, Expr, Integer> base_indx_mul = null;
		if (bin.getOperator() == Binary.BinaryOp.ADD)
		{
			if (bin.getRight() instanceof IntConst)
			{
				add = ((IntConst) bin.getRight()).getValue();
				if ((base_indx_mul = match_base_indx_mul(bin.getLeft())) != null)
					matched = true;
				else
					base = bin.getLeft();
			}
			else if (bin.getLeft() instanceof IntConst)
			{
				add = ((IntConst) bin.getLeft()).getValue();
				if ((base_indx_mul = match_base_indx_mul(bin.getRight())) != null)
					matched = true;
				else
					base = bin.getRight();
			}
			else if ((base_indx_mul = match_base_indx_mul(bin)) != null)
				matched = true;
		}

		if (base_indx_mul != null)
		{
			base = base_indx_mul.first;
			indx = base_indx_mul.second;
			mul = base_indx_mul.third;
		}
		if (matched)
		{
			if (base != null && match_address(base) != null)
				return null;
			if (base != null && match_address(indx) != null)
				return null;
			return new AddressTuple(base, indx, mul, add);
		}
		return null;
	}

	private Operand eliminate_address(Operand operand)
	{
		if (operand instanceof Address || (operand instanceof Reference && ((Reference) operand).getType() == Reference.Type.GLOBAL))
		{
			Reference tmp = get_tmp();
			ins.add(new Move(tmp, operand));
			return tmp;
		}
		else
			return operand;
	}

	public Operand visit_expr(Expr ir)
	{
		boolean matched = false;
		Operand ret = null;
		expr_depth++;
		AddressTuple addressTuple;
		if ((addressTuple = match_address(ir)) != null)
		{
			Operand base = visit_expr(addressTuple.base);
			Operand indx = null;
			if (addressTuple.indx != null)
			{
				indx = visit_expr(addressTuple.indx);
				indx = eliminate_address(indx);
			}
			base = eliminate_address(base);
			ret = get_tmp();
			ins.add(new Lea((Reference) ret, new Address(base, indx, addressTuple.mul, addressTuple.add)));
			matched = true;
		}
		if (!matched)
			ret = ir.accept(this);
		expr_depth--;
		return ret;
	}

	@Override
	public Operand visit(Addr node)
	{
		throw new InternalErrorS("Unreachable Error!");
	}

	private Reference get_lvalue(Operand operand)
	{
		operand = eliminate_address(operand);
		if (operand instanceof Immediate || (operand instanceof Reference && !((Reference) operand).can_be_accumulator()))
		{
			Reference ret = get_tmp();
			ins.add(new Move(ret, operand));
			return ret;
		}
		return (Reference) operand;
	}
	@Override
	public Operand visit(Assign ir)
	{
		Operand dest = null;
		if (ir.getLeft() instanceof Addr)
			dest = trans_entity(((Addr) ir.getLeft()).getEntity()).getReference();
		else
		{
			Operand lhs = visit_expr(ir.getLeft());
			lhs = eliminate_address(lhs);
			dest = new Address(lhs);
		}
		expr_depth++;
		Operand rhs = visit_expr(ir.getRight());
		expr_depth--;
//		System.err.println(dest + " = " + rhs);
		if (dest.is_address() && rhs.is_address())
		{
			Reference tmp = get_tmp();
			ins.add(new Move(tmp, rhs));
			ins.add(new Move(dest, tmp));
		}
		else
			ins.add(new Move(dest, rhs));
		return null;
	}

	private Operand add_binary(Binary.BinaryOp op, Operand left, Operand right)
	{
		left = get_lvalue(left);
		switch (op)
		{
			case ADD: ins.add(new Add(left,right)); break;
			case SUB: ins.add(new Sub(left,right)); break;
			case MUL: ins.add(new Mul(left,right)); break;
			case DIV: ins.add(new Div(left,right)); break;
			case MOD: ins.add(new Mod(left,right)); break;
			case LOGIC_AND: case BIT_AND:
			ins.add(new And(left,right)); break;
			case LOGIC_OR: case BIT_OR:
			ins.add(new Or(left,right));  break;
			case BIT_XOR:
				ins.add(new Xor(left,right)); break;
			case LSHIFT:
				ins.add(new Sal(left,right)); break;
			case RSHIFT:
				ins.add(new Sar(left,right)); break;
			case EQ:
				ins.add(new Cmp(left,right,Cmp.Operator.EQ)); break;
			case NE:
				ins.add(new Cmp(left,right,Cmp.Operator.NE)); break;
			case GE:
				ins.add(new Cmp(left,right,Cmp.Operator.GE)); break;
			case GT:
				ins.add(new Cmp(left,right,Cmp.Operator.GT)); break;
			case LE:
				ins.add(new Cmp(left,right,Cmp.Operator.LE)); break;
			case LT:
				ins.add(new Cmp(left,right,Cmp.Operator.LT)); break;
			default:
				throw new InternalErrorS("Invalid operator " + op);
		}
		return left;
	}

	private int log2(int x)
	{
		for(int i = 0; i < 30; i++)
		{
			if(x == (1 << i))
				return i;
		}
		return -1;
	}

	private boolean is_commutative(Binary.BinaryOp op)
	{
		switch (op)
		{
			case ADD: case MUL:
			case BIT_AND: case BIT_OR:case BIT_XOR:
			case NE:case EQ:
				return true;
			default:
				return false;
		}
	}

	@Override
	public Operand visit(Binary ir)
	{
		Operand ret;
		Expr left = ir.getLeft(), right = ir.getRight();
		Binary.BinaryOp op = ir.getOperator();

		if (is_commutative(op) && left instanceof IntConst)
		{
			Expr t = left;
			left = right;
			right = t;
		}
		if (op == Binary.BinaryOp.MUL)
		{
			if (right instanceof IntConst && log2(((IntConst) right).getValue()) != -1)
			{
				op = Binary.BinaryOp.LSHIFT;
				right = new IntConst(log2(((IntConst) right).getValue()));
			}
		}
		else if (op == Binary.BinaryOp.DIV)
		{
			if (right instanceof IntConst && log2(((IntConst) right).getValue()) != -1)
			{
				op = Binary.BinaryOp.RSHIFT;
				right = new IntConst(log2(((IntConst) right).getValue()));
			}
		}
		ret = visit_expr(left);
		Operand new_right = visit_expr(right);
		ret = add_binary(op, ret, new_right);
		return ret;
	}

	@Override
	public Operand visit(Call ir)
	{
		List<Operand> operands = new LinkedList<>();
		for (Expr arg : ir.getArgs())
		{
			expr_depth++;
			operands.add(visit_expr(arg));
			expr_depth--;
		}
		Reference ret = null;
		InsCall call = new InsCall(ir.getEntity(), operands);
		if (expr_depth != 0)
		{
			ret = get_tmp();
			call.setRet(ret);
		}
		ins.add(call);
		return ret;
	}

	private boolean is_compare_OP(Binary.BinaryOp op)
	{
		switch(op)
		{
			case EQ: case NE:
			case GT: case GE:
			case LT: case LE:
			return true;
			default:
				return false;
		}
	}
	@Override
	public Operand visit(CJump ir)
	{
		if (ir.getCondition() instanceof Binary && is_compare_OP(((Binary) ir.getCondition()).getOperator()))
		{
			Operand left = visit_expr(((Binary) ir.getCondition()).getLeft());
			Operand right = visit_expr(((Binary) ir.getCondition()).getRight());

			InsCJump.Type type;
			switch (((Binary) ir.getCondition()).getOperator())
			{
				case EQ: type = InsCJump.Type.EQ; break;
				case NE: type = InsCJump.Type.NE; break;
				case GT: type = InsCJump.Type.GT; break;
				case GE: type = InsCJump.Type.GE; break;
				case LT: type = InsCJump.Type.LT; break;
				case LE: type = InsCJump.Type.LE; break;
				default:
					throw new InternalErrorS("invalid compare operator @ visitCjump");
			}

			ins.add(new InsCJump(left, right, getLabel(ir.getTrue_label().getName()), getLabel(ir.getFalse_label().getName()), type));
		}
		else
		{
			Operand cond = visit_expr(ir.getCondition());
			if (cond instanceof Immediate)
			{
				if (((Immediate) cond).getValue() != 0)
					ins.add(new Jmp(getLabel(ir.getTrue_label().getName())));
				else
					ins.add(new Jmp(getLabel(ir.getFalse_label().getName())));
			}
			else
			{
				if (cond.is_address())
				{
					Reference tmp = get_tmp();
					ins.add(new Move(tmp, cond));
					cond = tmp;
				}
				ins.add(new InsCJump(cond, getLabel(ir.getTrue_label().getName()), getLabel(ir.getFalse_label().getName())));
			}
		}
		return null;
	}

	@Override
	public Operand visit(IntConst ir)
	{
		return new Immediate(ir.getValue());
	}

	@Override
	public Operand visit(Jump ir)
	{
		ins.add(new Jmp(getLabel(ir.getLabel().getName())));
		return null;
	}

	@Override
	public Operand visit(Label ir)
	{
		ins.add(getLabel(ir.getName()));
		return null;
	}

	@Override
	public Operand visit(Mem ir)
	{
		AddressTuple addressTuple;
		if ((addressTuple = match_address(ir.getExpr())) != null)
		{
			Operand base = visit_expr(addressTuple.base);
			Operand indx = null;
			if (addressTuple.indx != null)
			{
				indx = visit_expr(addressTuple.indx);
				indx = eliminate_address(indx);
			}
			base = eliminate_address(base);
			Reference ret = get_tmp();

			ins.add(new Move(ret, new Address(base, indx, addressTuple.mul, addressTuple.add)));
			return ret;
		}
		else
		{
			Operand expr = visit_expr(ir.getExpr());
			expr = eliminate_address(expr);
			return new Address(expr);
		}
	}

	@Override
	public Operand visit(Return ir)
	{
		if (ir.getExpr() == null)
		{
			ins.add(new InsReturn(null));
		}
		else
		{
			Operand ret = visit_expr(ir.getExpr());
			ins.add(new InsReturn(ret));
		}
		return null;
	}

	@Override
	public Operand visit(StrConst ir)
	{
		return new Immediate(ir.entity.getAsm_name());
	}

	@Override
	public Operand visit(Unary ir)
	{
		Operand ret = visit_expr(ir.getExpr());
		ret = get_lvalue(ret);
		switch(ir.getOperator())
		{
			case MINUS:
				ins.add(new Neg(ret)); break;
			case BIT_NOT:
				ins.add(new Not(ret)); break;
			case LOGIC_NOT:
				ins.add(new Xor(ret,new Immediate(1)));
				break;
			default:
				throw new InternalErrorS("invalid operator " + ir.getOperator());
		}
		return ret;
	}

	@Override
	public Operand visit(Var ir)
	{
		if (ir.getEntity().getName().equals("null"))
			return new Immediate(0);
		else
			return trans_entity(ir.getEntity()).getReference();
	}

	public List<IR> getGlobal_initializer()
	{
		return global_initializer;
	}

	public Scope getGlobal_scope()
	{
		return global_scope;
	}

	public List<FunctionEntity> getFunctionEntities()
	{
		return functionEntities;
	}

	public void print_INS()
	{
		ins.forEach(x -> System.err.println(x));
	}
}
