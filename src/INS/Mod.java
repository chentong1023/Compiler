package INS;

import Operand.Operand;

public class Mod extends Bin
{
	public Mod(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String name()
	{
		return "rem";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
