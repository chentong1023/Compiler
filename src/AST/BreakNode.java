package AST;

import FrontEnd.ASTVisitor;

public class BreakNode extends ExprNode {
    public BreakNode(Location location) {super("Break", location);}
    @Override
    public <S, E> E accept(ASTVisitor<S, E> vistor) {
        return null;
    }
}