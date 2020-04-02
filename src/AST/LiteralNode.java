package AST;

import Type.Type;

abstract public class LiteralNode extends ExprNode
{
	protected Type type;

	public LiteralNode(String name, Location location, Type type)
	{
		super(name, location);
		this.type = type;
	}

	@Override
	public boolean is_constant()
	{
		return true;
	}

	@Override
	public Type getType()
	{
		return type;
	}
}