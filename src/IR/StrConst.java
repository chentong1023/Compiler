package IR;

import Entity.StringConstantEntity;

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
}
