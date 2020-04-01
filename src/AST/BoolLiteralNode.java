package AST;

import FrontEnd.ASTVisitor;
import Type.BoolType;

public class BoolLiteralNode extends LiteralNode {
    private boolean value;

    public BoolLiteralNode(Location location, boolean value) {
        super("BoolLiteral", location, new BoolType());
        this.value = value;
    }
    public boolean getValue() {return value;}
    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor)
    {
        return visitor.visit(this);
    }
}