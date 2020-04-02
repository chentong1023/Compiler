package AST;

import FrontEnd.ASTVisitor;

public class IfNode extends StmtNode
{
	private ExprNode cond;
	private StmtNode then_body, else_body;

	public IfNode(Location location, ExprNode cond, StmtNode then_body, StmtNode else_body)
	{
		super("If", location);
		this.cond = cond;
		this.then_body = then_body;
		this.else_body = else_body;
	}

	public ExprNode getCond()
	{
		return cond;
	}

	public StmtNode getThen_body()
	{
		return then_body;
	}

	public StmtNode getElse_body()
	{
		return else_body;
	}

	@Override
	public <S, E> S accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}
