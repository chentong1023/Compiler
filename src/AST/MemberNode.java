package AST;

import Entity.Entity;
import FrontEnd.ASTVisitor;

public class MemberNode extends LHSNode {
    private ExprNode expr;
    private String member;
    private Entity entity;

    public MemberNode(ExprNode expr, String member)
    {
        super("Member ", expr.getLocation());
        this.expr = expr;
        this.member = member;
    }

    public ExprNode getExpr() {
        return expr;
    }
    public Entity getEntity() {
        return entity;
    }

    public String getMember() {
        return member;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override public boolean is_assignable() {return !entity.getType().is_function();}
    @Override public <S, E> E accept(ASTVisitor<S, E> visitor)
    {
        return visitor.visit(this);
    }
}