package IR;

import Operand.Operand;

public class IntConst extends Expr
{
	private int value;

	public IntConst(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return "Const int " + value;
	}

	@Override
	public Operand accept(IRVisitor emitter)
	{
		return emitter.visit(this);
	}
}
