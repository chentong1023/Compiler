package AST;

import FrontEnd.ASTVisitor;

abstract public class DefinitionNode extends StmtNode {

    public DefinitionNode(String name, Location location)
    {
        super(name, location);
    }

    abstract public <S, E> S accept(ASTVisitor<S, E> visitor);
}