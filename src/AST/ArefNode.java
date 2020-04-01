package AST;

import FrontEnd.ASTVisitor;
import Type.Type;

public class ArefNode extends LHSNode {
    private ExprNode expr, indx;
    public ArefNode(ExprNode expr, ExprNode indx)
    {
        super("Aref", expr.getLocation());
        this.expr = expr;
        this.indx = indx;
    }
    public ArefNode(ExprNode expr, ExprNode indx, Type type)
    {
        super("Aref", expr.getLocation());
        this.expr = expr;
        this.indx = indx;
        this.type = type;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public ExprNode getIndx() {
        return indx;
    }

    public boolean is_multi_dimension() {return expr instanceof ArefNode;}
    public ExprNode base_expr() {return is_multi_dimension() ? ((ArefNode) expr).base_expr() : expr;}
    public Location getLocation() {return expr.getLocation();}
    @Override public <S, E> E accept(ASTVisitor<S, E> visitor)
    {
        return visitor.visit(this);
    }
}