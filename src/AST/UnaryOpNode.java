package AST;

import FrontEnd.ASTVisitor;
import Type.Type;

public class UnaryOpNode extends ExprNode {
    public enum UnaryOp {
        PRE_INC, PRE_DEC, SUF_INC, SUF_DEC, MINUS, ADD, LOGIC_NOT, BIT_NOT
    }
    private UnaryOp operator;
    private ExprNode expr;
    private Type type;
    private long amount;

    public UnaryOpNode(UnaryOp operator, ExprNode expr)
    {
        super("Unary operator", expr.getLocation());
        this.operator = operator;
        this.expr = expr;
        this.amount = 1;
    }

    public UnaryOp getOperator() {
        return operator;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    public Type getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
    @Override public Location getLocation() {
        return expr.getLocation();
    }
    @Override public <S, E> E accept(ASTVisitor<S, E> visitor)
    {
        return visitor.visit(this);
    }
}