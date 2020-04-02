package AST;

import Entity.FunctionEntity;
import FrontEnd.ASTVisitor;

public class FunctionDefNode extends DefinitionNode
{
	private FunctionEntity entity;

	public FunctionDefNode(FunctionEntity entity)
	{
		super(entity.getName(), entity.getLocation());
		this.entity = entity;
	}

	public FunctionEntity getEntity()
	{
		return entity;
	}

	@Override
	public <S, E> S accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}