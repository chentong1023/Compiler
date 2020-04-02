package Entity;

import AST.*;
import Type.Type;

public class VariableEntity extends Entity
{
	private ExprNode initializer;

	public VariableEntity(String name, Location location, Type type, ExprNode initializer)
	{
		super(name, location, type);
		this.initializer = initializer;
	}

	public ExprNode getInitializer()
	{
		return initializer;
	}

	public VariableEntity copy()
	{
		VariableEntity new_entity = new VariableEntity(getName(), getLocation(), getType(), getInitializer());
		return new_entity;
	}

	@Override
	public String toString()
	{
		return "Variable Entity: " + getName();
	}
}