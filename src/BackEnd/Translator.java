package BackEnd;

import Entity.*;
import INS.*;
import Operand.*;

import java.util.LinkedList;
import java.util.List;

import static Compiler.Defines.FRAME_ALIGNMENT_SIZE;
import static Compiler.Defines.REG_SIZE;

public class Translator implements INSVisitor
{
	private List<FunctionEntity> functionEntities;
	private Scope global_scope;

	private List<Register> registers;
	private List<Register> para_register;
	private List<String> asm = new LinkedList<>();

	private Register rfp, rsp, rt0;

	public Translator(InstructionEmitter emitter, RegisterConfig registerConfig)
	{
		functionEntities = emitter.getFunctionEntities();
		global_scope = emitter.getGlobal_scope();

		registers = registerConfig.getRegisters();
		para_register = registerConfig.getPara_registers();

		rfp = registerConfig.getRegisters().get(8);
		rsp = registerConfig.getRegisters().get(2);
		rt0 = registerConfig.getRegisters().get(5);
	}

	private void add(String ins) {asm.add("\t" + ins);}
	private void add_label(String asm_name)
	{
		asm.add(asm_name + ":");
	}
	private void add(String op, Operand l, Operand r)
	{
		if (!(op.equals("mv") && l.to_NASM().equals(r.to_NASM())))
			asm.add("\t" + op + " " + l.to_NASM() + ", " + r.to_NASM());
	}
	private void add_move(Register reg, Operand operand)
	{
		if (operand.is_register())
			add("mv", reg, operand);
		else if (operand instanceof Address)
		{
		}
		else
		{
			
		}
	} // load
	private void add_move(Operand operand, Register reg)
	{

	} // save

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
	}


	@Override
	public void visit(Add ins)
	{

	}

	@Override
	public void visit(And ins)
	{

	}

	@Override
	public void visit(Cmp ins)
	{

	}

	@Override
	public void visit(Comment ins)
	{

	}

	@Override
	public void visit(Div ins)
	{

	}

	@Override
	public void visit(InsCall ins)
	{

	}

	@Override
	public void visit(InsLabel ins)
	{

	}

	@Override
	public void visit(InsCJump ins)
	{

	}

	@Override
	public void visit(InsReturn ins)
	{

	}

	@Override
	public void visit(Jmp ins)
	{

	}

	@Override
	public void visit(Lea ins)
	{

	}

	@Override
	public void visit(Mod ins)
	{

	}

	@Override
	public void visit(Move ins)
	{

	}

	@Override
	public void visit(Mul ins)
	{

	}

	@Override
	public void visit(Sal ins)
	{

	}

	@Override
	public void visit(Xor ins)
	{

	}

	@Override
	public void visit(Sar ins)
	{

	}

	@Override
	public void visit(Sub ins)
	{

	}

	@Override
	public void visit(Or ins)
	{

	}

	@Override
	public void visit(Neg ins)
	{

	}

	@Override
	public void visit(Not ins)
	{

	}

	@Override
	public void visit(Push ins)
	{

	}
}
