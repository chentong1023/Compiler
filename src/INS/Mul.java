package INS;

import Operand.Operand;

public class Mul extends Bin
{
	public Mul(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String name()
	{
		return "mul";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
