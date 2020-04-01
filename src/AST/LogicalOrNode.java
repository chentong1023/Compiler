package AST;

import FrontEnd.ASTVisitor;

public class LogicalOrNode extends BinaryOpNode {
    public LogicalOrNode(ExprNode left_son, ExprNode right_son) {
        super(left_son, BinaryOp.LOGIC_OR, right_son);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
