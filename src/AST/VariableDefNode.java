package AST;

import Entity.VariableEntity;
import FrontEnd.ASTVisitor;

public class VariableDefNode extends DefinitionNode {
    private VariableEntity entity;

    public VariableDefNode(VariableEntity entity)
    {
        super(entity.getName(), entity.getLocation());
        this.entity = entity;
    }

    public VariableEntity getEntity() {
        return entity;
    }
    @Override public <S, E> S accept(ASTVisitor<S, E> visitor)
    {
        return visitor.visit(this);
    }
}