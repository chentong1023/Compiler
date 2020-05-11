package IR;

import Entity.Entity;

public class Var extends Expr
{
	Entity entity;

	public Var(Entity entity)
	{
		this.entity = entity;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}

	@Override
	public String toString()
	{
		return entity.getName();
	}
}
