package Type;

import Entity.FunctionEntity;

public class FunctionType extends Type
{
	private FunctionEntity entity;

	public FunctionType(String name)
	{
		super(name);
	}

	@Override
	public int getSize()
	{
		return 0;
	}

	@Override
	public String toString()
	{
		return "Function :" + entity.getName();
	}

	public FunctionEntity getEntity()
	{
		return entity;
	}

	public void setEntity(FunctionEntity entity)
	{
		this.entity = entity;
	}

	@Override
	public boolean is_function()
	{
		return true;
	}

	@Override
	public boolean is_compatible(Type other)
	{
		if (!other.is_function()) return false;
		return entity.equals(((FunctionType) other).entity);
	}
}