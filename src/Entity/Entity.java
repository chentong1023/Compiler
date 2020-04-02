package Entity;

import AST.Location;
import Type.Type;

import java.util.HashSet;
import java.util.Set;

abstract public class Entity
{
	protected Type type;
	private int offset; //

	private String name;
	private Location location;

	public Entity(String name, Location location, Type type)
	{
		this.name = name;
		this.location = location;
		this.type = type;
	}

	public int getOffset()
	{
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public Type getType()
	{
		return type;
	}

	public int getSize()
	{
		return type.getSize();
	}

	public String getName()
	{
		return name;
	}

	public Location getLocation()
	{
		return location;
	}
}