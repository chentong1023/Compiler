package IR;

import Operand.Operand;

public class Unary extends Expr
{

	public enum UnaryOp {MINUS, LOGIC_NOT, BIT_NOT};
	private Expr expr;
	private UnaryOp operator;

	public Unary(Expr expr, UnaryOp operator)
	{
		this.expr = expr;
		this.operator = operator;
	}

	public Expr getExpr()
	{
		return expr;
	}

	public void setExpr(Expr expr)
	{
		this.expr = expr;
	}

	public void setOperator(UnaryOp operator)
	{
		this.operator = operator;
	}

	public UnaryOp getOperator()
	{
		return operator;
	}


	@Override
	public Operand accept(IRVisitor emitter)
	{
		return emitter.visit(this);
	}
}
