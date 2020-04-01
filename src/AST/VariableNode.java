package AST;

import Entity.*;
import FrontEnd.ASTVisitor;
import Type.Type;

public class VariableNode extends LHSNode {
    private Entity entity;
    private ParameterEntity this_pointer = null;

    public VariableNode(Location location, String name)
    {
        super(name, location);
    }
    public VariableNode(Entity variable)
    {
        super(variable.getName(), variable.getLocation());
        this.entity = variable;
    }

    public Entity getEntity() {
        if (entity == null)
            throw new InternalError("Variable.Entity == null");
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public ParameterEntity getThis_pointer() {
        return this_pointer;
    }

    public void setThis_pointer(ParameterEntity this_pointer) {
        this.this_pointer = this_pointer;
    }

    public boolean is_member() {return this_pointer != null;}

    @Override
    public Type getType()
    {
        if (entity == null)
            throw new InternalError("Variable.Entity == null");
        return entity.getType();
    }

    @Override public <S, E> E accept(ASTVisitor<S, E> visitor)
    {
        return visitor.visit(this);
    }
}