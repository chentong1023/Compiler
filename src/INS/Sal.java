package INS;

import Operand.Operand;

public class Sal extends Bin
{
	public Sal(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String name()
	{
		return "sll";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
