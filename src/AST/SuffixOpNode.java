package AST;

import FrontEnd.ASTVisitor;

public class SuffixOpNode extends UnaryOpNode {
    public SuffixOpNode(UnaryOp operator, ExprNode expr) {
        super(operator, expr);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
