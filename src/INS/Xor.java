package INS;

import Operand.Operand;

public class Xor extends Bin
{
	public Xor(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String name()
	{
		return "xor";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
