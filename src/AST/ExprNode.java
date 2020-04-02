package AST;

import FrontEnd.ASTVisitor;
import Type.Type;

abstract public class ExprNode extends Node
{
	private boolean is_assignable = false;
	protected Type type;

	public Type getType()
	{
		return type;
	}

	public ExprNode(String name, Location location)
	{
		super(name, location);
	}

	public long allocsize()
	{
		return getType().allocSize();
	}

	public boolean is_constant()
	{
		return false;
	}

	public boolean is_parameter()
	{
		return false;
	}

	public boolean is_assignable()
	{
		return is_assignable;
	}

	public boolean is_loadable()
	{
		return false;
	}

	public boolean is_lvalue()
	{
		return false;
	}

	public void setIs_assignable(boolean is_assignable)
	{
		this.is_assignable = is_assignable;
	}

	abstract public <S, E> E accept(ASTVisitor<S, E> vistor);
}