package INS;

import Operand.Operand;

public class Add extends Bin
{
	public Add(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String getName()
	{
		return "add";
	}
}
