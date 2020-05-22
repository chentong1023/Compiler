package IR;

import Entity.Entity;
import Operand.Operand;

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

	@Override
	public Operand accept(IRVisitor emitter)
	{
		return emitter.visit(this);
	}
}
