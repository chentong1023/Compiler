package AST;

import FrontEnd.*;

abstract public class StmtNode extends Node
{
	public StmtNode(String name, Location location)
	{
		super(name, location);
	}

	abstract public <S, E> S accept(ASTVisitor<S, E> visitor);
}