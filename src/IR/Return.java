package IR;

public class Return extends IR
{
	Expr expr;

	public Return(Expr expr)
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
		return "Return : " + expr;
	}
}
