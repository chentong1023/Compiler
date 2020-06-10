package BackEnd;

import Entity.*;
import ExceptionS.InternalErrorS;
import INS.*;
import Operand.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static Compiler.Defines.*;

public class Translator implements INSVisitor
{
	private List<FunctionEntity> functionEntities;
	private Scope global_scope;

	private List<Register> registers;
	private List<Register> para_register;
	private List<String> asm = new LinkedList<>();

	private Register rfp, rsp, rt0, rt1, rt2, ra0, rra;

	public Translator(InstructionEmitter emitter, RegisterConfig registerConfig)
	{
		functionEntities = emitter.getFunctionEntities();
		global_scope = emitter.getGlobal_scope();

		registers = registerConfig.getRegisters();
		para_register = registerConfig.getPara_registers();

		rfp = registerConfig.getRegisters().get(8);
		rsp = registerConfig.getRegisters().get(2);
		rt0 = registerConfig.getRegisters().get(5);
		rt1 = registerConfig.getRegisters().get(6);
		rt2 = registerConfig.getRegisters().get(7);
		ra0 = registerConfig.getRegisters().get(10);
		rra = registerConfig.getRegisters().get(1);
	}

	private void add(String ins) {asm.add("\t" + ins);}
	private void add_label(String asm_name)
	{
		asm.add(asm_name + ":");
	}
	private void add(String op, Operand l, Operand r)
	{
		if (!(op.equals("mv")))
			asm.add("\t" + op + "\t" + l.to_NASM() + ",\t" + l.to_NASM() + ",\t" + r.to_NASM());
		else if (l.to_NASM() != r.to_NASM())
			asm.add("\t" + op + "\t" + l.to_NASM() + ",\t" + r.to_NASM());
	}
	private void add_move(Operand dest, Operand src)
	{
		if (dest.is_register() && src.is_register())
		{
			if (dest.to_NASM() != src.to_NASM())
				add("mv\t" + dest.to_NASM() + "\t" + src.to_NASM());
		}
		else if (dest.is_register())
		{
			if (src instanceof Address)
			{
				simplify_address(((Address) src));
				add("lw\t" + dest.to_NASM() + ",\t" + ((Address) src).getAdd() + "(" + rt0 + ")");
			}
			else if (src instanceof Immediate)
			{
				if (((Immediate) src).getType() == Immediate.Type.INTEGER)
					add("li\t" + dest.to_NASM() + ",\t" + src.to_NASM());
				else if (((Immediate) src).getType() == Immediate.Type.LABEL)
					add("la\t" + dest.to_NASM() + ",\t" + src.to_NASM());
				else
					throw new InternalErrorS("what the fuck?");
			}
			else if (!(src instanceof Reference))
			{
				throw new InternalErrorS("what the fuck?");
			}
			else if (((Reference) src).getType() == Reference.Type.GLOBAL)
			{
				add("la\t" + rt0 + ",\t" + src.to_NASM());
				add("lw\t" + dest.to_NASM() + ",\t" + "0(" + rt0 + ")");
			}
			else if (((Reference) src).getType() == Reference.Type.OFFSET)
			{
				add("lw\t" + dest.to_NASM() + ",\t" + ((Reference) src).getOffset() + "(" + ((Reference) src).getRegister() + ")");
			}
			else
			{
				throw new InternalErrorS("what the fuck?");
			}
		} // load
		else if (src.is_register())
		{
			if (dest instanceof Address)
			{
				simplify_address(((Address) dest));
				add("sw\t" + src.to_NASM() + ",\t" + ((Address) dest).getAdd() + "(" + rt0 + ")");
			}
			else if (!(dest instanceof Reference))
			{
				throw new InternalErrorS("what the fuck?");
			}
			else if (((Reference) dest).getType() == Reference.Type.GLOBAL)
			{
				add("la\t" + rt0 + ",\t" + dest.to_NASM());
				add("sw\t" + src.to_NASM() + ",\t0(" + rt0 + ")");
			}
			else if (((Reference) dest).getType() == Reference.Type.OFFSET)
			{
				add("sw\t" + src.to_NASM() + ",\t" + ((Reference) dest).getOffset() + "(" + ((Reference) dest).getRegister() + ")");
			}
			else
			{
				throw new InternalErrorS("what the fuck?");
			}
		}//save
		else
		{
			add_move(rt1, src);
			add_move(dest, rt1);
		}
	}
	private int log2(int x)
	{
		for (int i = 0; i < 30; ++i)
			if ((1 << i) == x) return i;
			return -1;
	}
	private void add_bin(String name, Operand left, Operand right)
	{
		Operand rs1 = left, rs2 = right;
		if (!left.is_register())
		{
			rs1 = rt0;
			add_move(rs1, left);
		}
		if (right instanceof Immediate && ((Immediate) right).getType() == Immediate.Type.INTEGER)
		{
			if ((name == "add" || name == "sub") && ((Immediate) right).getValue() == 0)
				;
			else if (name == "mul" || name == "div" || name == "rem")
			{
				if (log2(((Immediate) right).getValue()) != -1)
				{
					if (name == "mul") add("slli\t" + rs1.to_NASM() + ",\t" + rs1.to_NASM() + ",\t" + log2(((Immediate) right).getValue()));
					else add("srli\t" + rs1.to_NASM() + ",\t" + rs1.to_NASM() + ",\t" + log2(((Immediate) right).getValue()));
				}
				else
				{
					rs2 = rt1;
					add_move(rs2, right);
					add(name, rs1, rs2);
				}
			}
			else if (name == "sub")
				add("addi\t" + rs1.to_NASM() + ",\t" + rs1.to_NASM() + ",\t" + -((Immediate) right).getValue());
			else if (name == "sne" || name == "slt" || name == "sgt" || name == "seq")
			{
				if (((Immediate) right).getValue() == 0)
					add(name + "z\t" + left.to_NASM() + ",\t" + left.to_NASM());
				else if (name == "sgt")
					add(name + "\t" + right.to_NASM() + ",\t" + left.to_NASM() + ",\t" + right.to_NASM());
				else
					add(name + "\t" + left.to_NASM() + ",\t" + left.to_NASM() + ",\t" + right);
			}
			else add(name + "i\t" + rs1.to_NASM() + ",\t" + rs1.to_NASM() + ",\t" + ((Immediate) right).getValue());
		}
		else
		{
			if (!right.is_register())
			{
				rs2 = rt1;
				add_move(rs2, right);
			}
			add(name, rs1, rs2);
		}

		if (left != rs1)
			add_move(left, rs1);
	}
	private void add_push(Operand operand)
	{
		if (!operand.is_register())
			throw new InternalErrorS("push is not register.");
		add("push\t" + operand);
	}
	private void add_pop(Operand operand)
	{
		if (!operand.is_register())
			throw new InternalErrorS("pop is not register.");
		add("pop\t" + operand);
	}
	private void add_unary(String name, Operand operand)
	{
		Operand dest = operand;
		if (!operand.is_register())
		{
			add_move(rt0, operand);
			add(name + "\t" + rt0 + ",\t" + rt0);
			add_move(operand, rt0);
		}
		else
		{
			add(name + "\t" + operand.to_NASM() + ",\t" + operand.to_NASM());
		}
	}
	private void add(String op, Operand l)
	{
		asm.add("\t" + op + " " + l.to_NASM());
	}

	public List<String> translate()
	{
		for (Entity entity : global_scope.getEntities().values())
		{
			if (entity instanceof VariableEntity)
			{
				add(".type\t" + entity.getName() + ",@object");
				add(".section\t.bss");
				add(".global\t" + entity.getName());
				add(".p2align\t2");
				add(entity.getName() + ":");
				add(".L" + entity.getName() + "$local:");
				add(".word\t0");
				add(".size\t" + entity.getName() + ", 4\n");
			}
			else if (entity instanceof StringConstantEntity)
			{
				String name = ((StringConstantEntity) entity).getAsm_name();
				String value = ((StringConstantEntity) entity).getValue();
				add(".type\t" + name + ",@object");
				add(".section\t.rodata");
				add(name + ":");
				add(".asciz\t\"" + value + "\"");
				add(".size\t" + name + ", " + (value.length() + 1) + "\n");
			}
		}

		add(".text");
		for (FunctionEntity functionEntity : functionEntities)
		{
			if (functionEntity.is_inlined())
				continue;
			add(".globl\t" + functionEntity.getAsm_name());
			add(".p2align\t1");
			add(".type\t" + functionEntity.getAsm_name() + ",@function");
			locate_frame(functionEntity);
			translate_function(functionEntity);
			add("");
		}
		paste_lib_function();
		return asm;
	}

	private void locate_frame(FunctionEntity entity)
	{
		int saved_reg_num = 0;
		for (Register register : entity.getReg_used())
		{
			if (register.isCallee_save())
				saved_reg_num++;
		}

		int para_base, lvar_base, total;
		para_base = 0;
		lvar_base = para_base;
		int sourceBase = saved_reg_num * REG_SIZE + REG_SIZE;
		List<ParameterEntity> params = entity.getParameterEntityList();
		for (int i = 0; i < params.size(); ++i)
		{
			ParameterEntity par = params.get(i);
			Reference ref = par.getReference();
			if (i < para_register.size())
			{
				par.getSource().set_register(para_register.get(i));
			}
			else
			{
				par.getSource().setOffset(sourceBase, rfp);
				sourceBase += par.getType().getSize();
				if (ref.is_unknown())
					ref.setOffset(par.getSource().getOffset(), par.getSource().getRegister());
			}
		}

		total = lvar_base + entity.getLocal_variable_offset();
		total += (FRAME_ALIGNMENT_SIZE - (total + REG_SIZE + REG_SIZE * saved_reg_num) % FRAME_ALIGNMENT_SIZE) % FRAME_ALIGNMENT_SIZE;
		entity.setFrame_size(total);
	}

	private void translate_function(FunctionEntity entity)
	{
		add_label(entity.getAsm_name());
		int start_pos = asm.size();
		LinkedList<Register> callee = new LinkedList<>();
		for (Register register : entity.getReg_used())
			if (register.isCallee_save())
				callee.add(register);
		callee.add(rra);
		for (BasicBlock basicBlock : entity.getBasicBlocks())
		{
			for (Instruction ins : basicBlock.getIns())
				ins.accept(this);
			if (basicBlock.getLabel() == entity.getEnd_label_INS())
			{
				int pop_cnter = 1;
				for (Register register : callee)
				{
					add_move(register, new Reference(callee.size() * REG_SIZE - pop_cnter * REG_SIZE, rsp));
					pop_cnter++;
				}
				add_bin("add", rsp, new Immediate((entity.getFrame_size() + callee.size() * REG_SIZE)));
				add("ret");
			}
		}

		List<String> backup = asm, prologue;
		asm = new LinkedList<>();

		int push_cnter = 1;
		if (entity.getReg_used().contains(rfp))
			add("mv", rfp, rsp);
		add_bin("sub", rsp, new Immediate(entity.getFrame_size() + callee.size() * REG_SIZE));
		for (Register register : callee)
		{
			add_move(new Reference(callee.size() * REG_SIZE - push_cnter * REG_SIZE, rsp), register);
			push_cnter++;
		}

		/*List<ParameterEntity> params = entity.getParameterEntityList();
		for (ParameterEntity param : params)
		{
			if (!param.getReference().equals(param.getSource()))
				add_move(param.getReference(),param.getSource());
		}
		add("");*/
		prologue = asm;
		asm = backup;
		asm.addAll(start_pos, prologue);
	}

	private void visit_bin(Bin ins)
	{
		Operand left = ins.getLeft(), right = ins.getRight();
		String name = ins.name();

		add_bin(name, left, right);
	}

	@Override
	public void visit(Add ins)
	{
		visit_bin(ins);
	}

	@Override
	public void visit(And ins)
	{
		visit_bin(ins);
	}

	@Override
	public void visit(Cmp ins)
	{
		Operand left, right;
		left = ins.getLeft();
		right = ins.getRight();

		String cmpname = "";
		switch (ins.getOperator())
		{
			case NE:
				add_bin("xor", left, right);
				add_bin("sne", left, new Immediate(0));
				break;
			case EQ:
				add_bin("xor", left, right);
				add_bin("seq", left, new Immediate(0));
				break;
			case LE:
				add_bin("slt", right, left);
				add("xori\t" + left.to_NASM() + ",\t" + right.to_NASM() + ",1");
				break;
			case GE:
				add_bin("slt", left, right);
				add("xori\t" + left.to_NASM() + ",\t" + left.to_NASM() + ",1");
				break;
			case GT:
				add_bin("sgt", left, right);
				break;
			case LT:
				add_bin("slt", left, right);
				break;
		}
	}

	@Override
	public void visit(Comment ins)
	{
		add("#"+ins);
	}

	@Override
	public void visit(Div ins)
	{
		visit_bin(ins);
	}

	@Override
	public void visit(InsCall ins)
	{
		List<Operand> operands = ins.getOperands();

//		System.err.println(ins.getEntity().getAsm_name());
//		for (Reference caller : ins.getCallorsave())
//			System.err.println(caller.to_NASM());

		int push_cnter = 0;
		int offset = (operands.size() - para_register.size()) * REG_SIZE;
		if (operands.size() > para_register.size())
			add_bin("sub", rsp, new Immediate((operands.size() - para_register.size()) * REG_SIZE));
		for (int i = 0; i < operands.size(); ++i)
		{
			if (i < para_register.size())
				add_move(para_register.get(i), operands.get(i));
			else
			{
				if (operands.get(i).is_register())
					add_move(operands.get(i), new Reference(push_cnter * REG_SIZE, rsp));
				else
				{
					add_move(rt0, operands.get(i));
					add_move(new Reference(push_cnter * REG_SIZE, rsp), rt0);
				}
				push_cnter++;
			}
		}
		add("call\t" + ins.getEntity().getAsm_name());
		if (operands.size() > para_register.size())
			add_bin("add", rsp, new Immediate((operands.size() - para_register.size()) * REG_SIZE));
		if (ins.getRet() != null)
			add_move(ins.getRet(), ra0);
	}

	@Override
	public void visit(InsLabel ins)
	{
		add_label(ins.getName());
	}

	@Override
	public void visit(InsCJump ins)
	{
		if (ins.getType() == InsCJump.Type.BOOL)
		{
			if (ins.getCond() instanceof Immediate)
			{
				if (((Immediate) ins.getCond()).getValue() != 0)
					add("j\t" + ins.getTrue_label().getName());
				else
					add("j\t" + ins.getFalse_label().getName());
			}
			else
			{
				add_move(rt0, ins.getCond());

				if (ins.getFall_through() == ins.getTrue_label())
					add("beqz\t" + rt0 + ",\t" + ins.getFalse_label().getName());
				else if (ins.getFall_through() == ins.getFalse_label())
					add("bnez\t" + rt0 + ",\t" + ins.getTrue_label().getName());
				else
				{
					add("bnez\t" + rt0 + ",\t" + ins.getTrue_label().getName());
					add("beqz\t" + rt0 + ",\t" + ins.getFalse_label().getName());
				}
			}
		}
		else
		{
			String name = ins.name();
			Operand left = ins.getLeft(), right = ins.getRight();

			if (!left.is_register())
			{
				add_move(rt0, left);
				left = rt0;
			}
			if (!right.is_register())
			{
				add_move(rt1, right);
				right = rt1;
			}

			if (ins.getFall_through() == ins.getTrue_label())
			{
				name = InsCJump.get_not_name(name);
				add(name + "\t" + left.to_NASM() + ",\t" + right.to_NASM() + ",\t" + ins.getFalse_label().getName());
			}
			else if (ins.getFall_through() == ins.getFalse_label())
				add(name + "\t" + left.to_NASM() + ",\t" + right.to_NASM() + ",\t" + ins.getTrue_label().getName());
			else
			{
				add(name + "\t" + left.to_NASM() + ",\t" + right.to_NASM() + ",\t" + ins.getTrue_label().getName());
				add(InsCJump.get_not_name(name) + "\t" + left.to_NASM() + ",\t" + right.to_NASM() + ",\t" + ins.getFalse_label().getName());
			}
		}
	}

	@Override
	public void visit(InsReturn ins)
	{
		if (ins.getRet() != null)
			add_move(ra0, ins.getRet());
	}

	@Override
	public void visit(Jmp ins)
	{
		add("j\t" + ins.getDest().getName());
	}

	@Override
	public void visit(Lea ins)
	{
		simplify_address(ins.getAddr());
		ins.getAddr().setShow_size(false);
		add_move(ins.getDest(), rt0);
	}

	@Override
	public void visit(Mod ins)
	{
		visit_bin(ins);
	}

	private void simplify_address(Address addr)
	{
		if (addr.getIndex() == null)
		{
			add_move(rt0, addr.getBase());
			return ;
		}
		add_move(rt1, addr.getIndex());
		add_move(rt0, addr.getBase());
		add_bin("mul", rt1, new Immediate(addr.getMul()));
		add_bin("add", rt0, rt1);
	}

	@Override
	public void visit(Move ins)
	{
		boolean isAddrLeft = ins.getDest().is_address();
		boolean isAddrRight = ins.getSrc().is_address();

		if (Enable_Global_Register_Allocation)
			add_move(ins.getDest(), ins.getSrc());
		else
		{
			if (isAddrLeft && isAddrRight) throw new InternalErrorS("what the fuck?");
			else
				add_move(ins.getDest(), ins.getSrc());
		}
	}

	@Override
	public void visit(Mul ins)
	{
		visit_bin(ins);
	}

	@Override
	public void visit(Sal ins)
	{
		visit_bin(ins);
	}

	@Override
	public void visit(Xor ins)
	{
		visit_bin(ins);
	}

	@Override
	public void visit(Sar ins)
	{
		visit_bin(ins);
	}

	@Override
	public void visit(Sub ins)
	{
		visit_bin(ins);
	}

	@Override
	public void visit(Or ins)
	{
		visit_bin(ins);
	}

	@Override
	public void visit(Neg ins)
	{
		add_unary("neg", ins.getOperand());
	}

	@Override
	public void visit(Not ins)
	{
		add_unary("not", ins.getOperand());
	}

	@Override
	public void visit(Push ins)
	{
		add_push(ins.getOperand());
	}

	private void paste_lib_function()
	{
		File f = new File("lib/lib.s");
		try {
			BufferedReader fin = new BufferedReader(new FileReader(f));
			String line;
			while ((line = fin.readLine()) != null)
				asm.add(line);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
