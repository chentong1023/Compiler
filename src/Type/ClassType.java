package Type;

import Entity.ClassEntity;

public class ClassType extends Type
{
	public static final String CONSTRUCTOR_NAME = "__CONSTRUCTOR_";
	public static final int DEFAULT_SIZE = 8;
	private ClassEntity entity;

	public ClassType(String name)
	{
		super(name);
	}

	public void setEntity(ClassEntity entity)
	{
		this.entity = entity;
	}

	public ClassEntity getEntity()
	{
		return entity;
	}

	@Override
	public int getSize()
	{
		return DEFAULT_SIZE;
	}

	@Override
	public boolean is_class()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return "Class" + getName();
	}

	@Override
	public boolean is_compatible(Type other)
	{
		if (other.is_null()) return true;
		if (!other.is_class()) return false;
		return entity.equals(((ClassType) other).entity);
	}
}