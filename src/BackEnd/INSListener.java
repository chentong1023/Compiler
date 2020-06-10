package BackEnd;

import INS.*;

import java.util.List;

public class INSListener extends Translator
{
	public INSListener(InstructionEmitter emitter, RegisterConfig registerConfig)
	{
		super(emitter, registerConfig);
	}

	@Override
	public List<String> translate()
	{
		return super.translate();
	}

	@Override
	public void visit(Add ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(And ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Cmp ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Comment ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Div ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(InsCall ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(InsLabel ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(InsCJump ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(InsReturn ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Jmp ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Lea ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Mod ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Move ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Mul ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Sal ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Xor ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Sar ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Sub ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Or ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Neg ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Not ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	@Override
	public void visit(Push ins)
	{
		debug_print(ins);
		super.visit(ins);
	}

	private void debug_print(Instruction ins)
	{
		if (ins instanceof InsLabel || ins instanceof Comment)
			System.err.println(ins);
		else
			System.err.println("\t" + ins);
	}
}
