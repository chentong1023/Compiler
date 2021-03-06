package IR;

import Operand.Operand;

public class Assign extends IR
{
	Expr left, right;

	public Assign(Expr left, Expr right)
	{
		this.left = left;
		this.right = right;
	}

	public Expr getLeft()
	{
		return left;
	}

	public Expr getRight()
	{
		return right;
	}

	@Override
	public String toString()
	{
		return left + " = " + right;
	}

	@Override
	public Operand accept(IRVisitor emitter)
	{
		return emitter.visit(this);
	}
}
