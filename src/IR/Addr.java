package IR;

import Entity.Entity;
import Operand.Operand;

public class Addr extends Expr
{
	private Entity entity;
	public Addr(Entity entity)
	{
		super();
		this.entity = entity;
	}

	public Entity getEntity()
	{
		return entity;
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
