package AST;

import FrontEnd.ASTVisitor;

public class ContinueNode extends StmtNode
{
	public ContinueNode(Location location)
	{
		super("Continue", location);
	}

	@Override
	public <S, E> S accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}