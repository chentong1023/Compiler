package INS;

import Operand.Reference;

public class Jmp extends Instruction
{
	private InsLabel dest;

	public Jmp(InsLabel dest)
	{
		this.dest = dest;
	}

	public InsLabel getDest()
	{
		return dest;
	}

	public void setDest(InsLabel dest)
	{
		this.dest = dest;
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{

	}

	@Override
	public void replace_def(Reference from, Reference to)
	{

	}

	@Override
	public void calc_def_and_use()
	{

	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "jmp " + dest;
	}
}
