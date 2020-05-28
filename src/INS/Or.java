package INS;

import Operand.Operand;

public class Or extends Bin
{
	public Or(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String name()
	{
		return "or";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
