package AST;

import Type.Type;

abstract public class LHSNode extends ExprNode
{
	protected Type type;

	public LHSNode(String name, Location location)
	{
		super(name, location);
	}

	@Override
	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public long allocSize()
	{
		return type.allocSize();
	}

	@Override
	public boolean is_lvalue()
	{
		return true;
	}

	@Override
	public boolean is_assignable()
	{
		return true;
	}
}