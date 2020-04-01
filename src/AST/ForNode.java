package AST;

import FrontEnd.ASTVisitor;

public class ForNode extends StmtNode {
    private ExprNode init, cond, incr;
    private StmtNode body;

    public ForNode(Location location, ExprNode init, ExprNode cond, ExprNode incr, StmtNode body)
    {
        super("For", location);
        this.init = init;
        this.cond = cond;
        this.incr = incr;
        this.body = BlockNode.wrapBlock(body);
    }

    public ExprNode getInit() {
        return init;
    }

    public ExprNode getCond() {
        return cond;
    }

    public ExprNode getIncr() {
        return incr;
    }

    public StmtNode getBody() {
        return body;
    }
    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor)
    {
        return visitor.visit(this);
    }
}