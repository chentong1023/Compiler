package AST;

import FrontEnd.ASTVisitor;

public class ReturnNode extends StmtNode {
    private ExprNode expr;
    public ReturnNode(Location location, ExprNode expr)
    {
        super("Return", location);
        this.expr = expr;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    @Override public <S, E> S accept(ASTVisitor<S, E> visitor)
    {
        return visitor.visit(this);
    }
}