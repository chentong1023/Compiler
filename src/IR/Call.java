package IR;

import Entity.FunctionEntity;

import java.util.List;

public class Call extends Expr
{
	private FunctionEntity entity;
	private List<Expr> args;

	public Call(FunctionEntity entity, List<Expr> args)
	{
		this.entity = entity;
		this.args = args;
	}

	public FunctionEntity getEntity()
	{
		return entity;
	}

	public List<Expr> getArgs()
	{
		return args;
	}

	@Override
	public String toString()
	{
		return "Call : " + entity.getName();
	}
}
