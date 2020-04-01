package AST;

import Entity.StringConstantEntity;
import FrontEnd.ASTVisitor;
import Type.StringType;

public class StringLiteralNode extends LiteralNode {
    private String value;
    private StringConstantEntity entity;

    public StringLiteralNode(Location location, String value)
    {
        super("Literal String", location, new StringType());
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public StringConstantEntity getEntity() {
        return entity;
    }

    public void setEntity(StringConstantEntity entity) {
        this.entity = entity;
    }

    @Override public <S, E> E accept(ASTVisitor<S, E> visitor)
    {
        return visitor.visit(this);
    }
}