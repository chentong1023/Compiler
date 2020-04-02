package AST;

import FrontEnd.ASTVisitor;

public class BreakNode extends StmtNode
{
	public BreakNode(Location location)
	{
		super("Break", location);
	}

	@Override
	public <S, E> S accept(ASTVisitor<S, E> vistor)
	{
		return null;
	}
}