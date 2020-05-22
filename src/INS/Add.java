package INS;

import Operand.Operand;

public class Add extends Bin
{
	public Add(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String name()
	{
		return "add";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
