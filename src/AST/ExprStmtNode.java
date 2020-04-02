package AST;

import FrontEnd.ASTVisitor;

public class ExprStmtNode extends StmtNode
{
	private ExprNode expr;

	public ExprStmtNode(Location location, ExprNode expr)
	{
		super("ExprStmt", location);
		this.expr = expr;
	}

	public void setExpr(ExprNode expr)
	{
		this.expr = expr;
	}

	public ExprNode getExpr()
	{
		return expr;
	}

	@Override
	public <S, E> S accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}