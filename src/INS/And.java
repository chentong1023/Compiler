package INS;

import Operand.Operand;

public class And extends Bin
{
	public And(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String getName()
	{
		return "and";
	}
}
