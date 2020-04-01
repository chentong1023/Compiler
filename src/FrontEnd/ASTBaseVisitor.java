package FrontEnd;

import AST.*;

import java.util.List;

public class ASTBaseVisitor implements ASTVisitor<Void, Void> {
    protected int level = 0;
    public void visitStmt(StmtNode stmtNode)
    {
        stmtNode.accept(this);
    }
    public void visitStmts(List<? extends StmtNode> stmtNodes)
    {
        for (StmtNode stmtNode: stmtNodes)
            visitStmt(stmtNode);
    }
    public void visitExpr(ExprNode expr)
    {
        expr.accept(this);
    }
    public void visitExprs(List<? extends ExprNode> exprNodes)
    {
        for (ExprNode node : exprNodes)
            visitExpr(node);
    }
    public void visitDefinition(DefinitionNode definitionNode)
    {
        definitionNode.accept(this);
    }
    public void visitDefinitions(List<? extends DefinitionNode> definitionNodes)
    {
        for (DefinitionNode definitionNode : definitionNodes)
            visitDefinition(definitionNode);
    }

    @Override
    public Void visit(BlockNode node) {
        level++;
        visitStmts(node.getStmts());
        level--;
        return null;
    }

    @Override
    public Void visit(ExprStmtNode node) {
        level++;
        visitExpr(node.getExpr());
        level--;
        return null;
    }

    @Override
    public Void visit(IfNode node) {
        level++;
        visitExpr(node.getCond());
        if (node.getThen_body() != null)
            visitStmt(node.getThen_body());
        if (node.getElse_body() != null)
            visitStmt(node.getElse_body());
        level--;
        return null;
    }

    @Override
    public Void visit(WhileNode node) {
        level++;
        visitExpr(node.getCond());
        if (node.getBody() != null)
            visitStmt(node.getBody());
        level--;
        return null;
    }

    @Override
    public Void visit(ForNode node) {
        level++;
        if (node.getInit() != null)
            visitExpr(node.getInit());
        if (node.getCond() != null)
            visitExpr(node.getCond());
        if (node.getIncr() != null)
            visitExpr(node.getIncr());
        if (node.getBody() != null)
            visitStmt(node.getBody());
        level--;
        return null;
    }

    @Override
    public Void visit(BreakNode node) {
        return null;
    }

    @Override
    public Void visit(ContinueNode node) {
        return null;
    }

    @Override
    public Void visit(ReturnNode node) {
        level++;
        if (node.getExpr() != null)
            visitExpr(node.getExpr());
        level--;
        return null;
    }

    @Override
    public Void visit(VariableDefNode node) {
        level++;
        if (node.getEntity().getInitializer() != null)
            visitExpr(node.getEntity().getInitializer());
        level--;
        return null;
    }

    @Override
    public Void visit(FunctionDefNode node) {
        level++;
        visitStmt(node.getEntity().getBody());
        level--;
        return null;
    }

    @Override
    public Void visit(ClassDefNode node) {
        level++;
        visitStmts(node.getEntity().getMember_variables());
        visitStmts(node.getEntity().getMember_functions());
        level--;
        return null;
    }

    @Override
    public Void visit(VariableDefLineNode node) {
        for (VariableDefNode defNode : node.getVariableDefNodeList())
            visit(defNode);
        return null;
    }

    @Override
    public Void visit(AssignNode node) {
        level++;
        visitExpr(node.getLhs());
        visitExpr(node.getRhs());
        level--;
        return null;
    }

    @Override
    public Void visit(LogicalOrNode node) {
        level++;
        visitExpr(node.getLeft_son());
        visitExpr(node.getRight_son());
        level--;
        return null;
    }

    @Override
    public Void visit(LogicalAndNode node) {
        level++;
        visitExpr(node.getLeft_son());
        visitExpr(node.getRight_son());
        level--;
        return null;
    }

    @Override
    public Void visit(BinaryOpNode node) {
        level++;
        visitExpr(node.getLeft_son());
        visitExpr(node.getRight_son());
        level--;
        return null;
    }

    @Override
    public Void visit(UnaryOpNode node) {
        level++;
        visitExpr(node.getExpr());
        level--;
        return null;
    }

    @Override
    public Void visit(CreatorNode node) {
        level++;
        if (node.getExprs() != null)
            visitExprs(node.getExprs());
        level--;
        return null;
    }

    @Override
    public Void visit(PrefixOpNode node) {
        level++;
        visitExpr(node.getExpr());
        level--;
        return null;
    }

    @Override
    public Void visit(SuffixOpNode node) {
        level++;
        visitExpr(node.getExpr());
        level--;
        return null;
    }

    @Override
    public Void visit(ArefNode node) {
        level++;
        visitExpr(node.getExpr());
        visitExpr(node.getIndx());
        level--;
        return null;
    }

    @Override
    public Void visit(MemberNode node) {
        level++;
        visitExpr(node.getExpr());
        level--;
        return null;
    }

    @Override
    public Void visit(FuncallNode node) {
        level++;
        visitExpr(node.getExpr());
        visitExprs(node.getArgs());
        level--;
        return null;
    }

    @Override
    public Void visit(VariableNode node) {
        return null;
    }

    @Override
    public Void visit(IntegerLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(StringLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(BoolLiteralNode node) {
        return null;
    }
}
