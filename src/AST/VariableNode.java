package AST;

import Entity.*;
import FrontEnd.ASTVisitor;
import Type.Type;

public class VariableNode extends LHSNode
{
	private Entity entity;
	private ParameterEntity this_pointer = null;
	private boolean is_this = false;

	public VariableNode(Location location, String name)
	{
		super(name, location);
	}

	public VariableNode(Entity variable)
	{
		super(variable.getName(), variable.getLocation());
		this.entity = variable;
	}

	public Entity getEntity()
	{
		if (entity == null)
			throw new InternalError("Variable.Entity == null");
		return entity;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
		if (entity instanceof ParameterEntity)
			is_this = ((ParameterEntity) entity).isIs_this();
	}

	public ParameterEntity getThis_pointer()
	{
		return this_pointer;
	}

	public void setThis_pointer(ParameterEntity this_pointer)
	{
		this.this_pointer = this_pointer;
	}

	public boolean is_member()
	{
		return this_pointer != null;
	}

	@Override
	public boolean is_lvalue()
	{
		return !is_this;
	}

	@Override
	public boolean is_assignable()
	{
		return !is_this;
	}

	@Override
	public Type getType()
	{
		if (entity == null)
			throw new InternalError("Variable.Entity == null");
		return entity.getType();
	}

	@Override
	public <S, E> E accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}