package AST;

import FrontEnd.ASTVisitor;

public class WhileNode extends StmtNode
{
	private ExprNode cond;
	private StmtNode body;

	public WhileNode(Location location, ExprNode cond, StmtNode body)
	{
		super("While_And_IF", location);
		this.cond = cond;
		this.body = BlockNode.wrapBlock(body);
	}

	public ExprNode getCond()
	{
		return cond;
	}

	public StmtNode getBody()
	{
		return body;
	}

	@Override
	public <S, E> S accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}