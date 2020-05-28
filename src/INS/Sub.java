package INS;

import Operand.Operand;

public class Sub extends Bin
{
	public Sub(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String name()
	{
		return "sub";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
