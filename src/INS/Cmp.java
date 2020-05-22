package INS;

import Operand.Operand;
import Operand.Reference;

public class Cmp extends Bin
{
	public enum Operator {
		EQ, NE, GE, GT, LE, LT
	}
	private Operator operator;

	public Cmp(Operand left, Operand right, Operator operator)
	{
		super(left, right);
		this.operator = operator;
	}

	@Override
	public String name()
	{
		return "cmp";
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}
}
