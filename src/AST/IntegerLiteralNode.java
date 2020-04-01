package AST;

import FrontEnd.ASTVisitor;
import Type.IntegerType;

public class IntegerLiteralNode extends LiteralNode{
    private long value;
    public IntegerLiteralNode(Location location, long value)
    {
        super("LiteralInteger", location, new IntegerType());
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> vistor) {
        return vistor.visit(this);
    }
}
