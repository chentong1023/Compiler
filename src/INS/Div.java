package INS;

import Operand.Operand;

public class Div extends Bin
{

	public Div(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String name()
	{
		return "div";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
