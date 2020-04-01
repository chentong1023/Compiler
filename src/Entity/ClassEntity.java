package Entity;

import AST.*;
import Type.ClassType;

import java.util.List;

public class ClassEntity extends Entity {
    List<VariableDefNode> member_variables;
    List<FunctionDefNode> member_functions;
    private FunctionEntity constructor;

    public ClassEntity(String name, Location location, List<VariableDefNode> member_variables, List<FunctionDefNode> member_functions)
    {
        super(name, location, new ClassType(name));
        this.member_functions = member_functions;
        this.member_variables = member_variables;
        this.scope = null;
        this.constructor = null;
        ((ClassType)this.type).setEntity(this);
    }

    public FunctionEntity getConstructor() {
        return constructor;
    }

    public void setConstructor(FunctionEntity constructor) {
        this.constructor = constructor;
    }

    public List<FunctionDefNode> getMember_functions() {
        return member_functions;
    }

    public List<VariableDefNode> getMember_variables() {
        return member_variables;
    }

    private int size;
    @Override
    public int getSize() {return size;}

    @Override
    public String toString() {return "Class Entity:" + getName();}

    private Scope scope;

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
    public void init_offset(int alignment)
    {
        this.size = scope.locate_member(alignment);
    }
}