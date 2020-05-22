package IR;

import Entity.StringConstantEntity;
import Operand.Operand;

public class StrConst extends Expr
{
	public StringConstantEntity entity;

	public StrConst(StringConstantEntity entity)
	{
		this.entity = entity;
	}

	public StringConstantEntity getEntity()
	{
		return entity;
	}

	@Override
	public Operand accept(IRVisitor emitter)
	{
		return emitter.visit(this);
	}
}
