package AST;

import Entity.ClassEntity;
import FrontEnd.ASTVisitor;

public class ClassDefNode extends DefinitionNode
{
	private ClassEntity entity;

	public ClassDefNode(ClassEntity entity)
	{
		super(entity.getName(), entity.getLocation());
		this.entity = entity;
	}

	public ClassEntity getEntity()
	{
		return entity;
	}

	@Override
	public <S, E> S accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}