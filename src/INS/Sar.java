package INS;

import Operand.Operand;

public class Sar extends Bin
{
	public Sar(Operand left, Operand right)
	{
		super(left, right);
	}

	@Override
	public String name()
	{
		return "sra";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
