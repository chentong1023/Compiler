package IR;

import Operand.Operand;

public class Mem extends Expr
{
	Expr expr;

	public Mem(Expr expr)
	{
		this.expr = expr;
	}

	public Expr getExpr()
	{
		return expr;
	}

	@Override
	public String toString()
	{
		return "" + expr;
	}

	@Override
	public Operand accept(IRVisitor emitter)
	{
		return emitter.visit(this);
	}
}
