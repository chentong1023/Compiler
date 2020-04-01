package AST;

import FrontEnd.ASTVisitor;

import java.util.List;

public class VariableDefLineNode extends StmtNode {
    private List<VariableDefNode> variableDefNodeList;

    public VariableDefLineNode(Location location, List<VariableDefNode> variableDefNodeList) {
        super("Var_Def_stmt", location);
        this.variableDefNodeList = variableDefNodeList;
    }

    public List<VariableDefNode> getVariableDefNodeList() {
        return this.variableDefNodeList;
    }

    @Override
    public <S, E> S accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
