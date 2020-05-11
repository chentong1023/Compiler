package Entity;

import AST.*;
import IR.IR;
import IR.Label;
import Type.*;

import java.util.*;

public class FunctionEntity extends Entity {
    private boolean is_constructor = false;
    private boolean is_lib_function = false;
    private boolean is_inlined = false;

    private List<ParameterEntity> parameterEntityList;
    private BlockNode body;
    private Type return_type;

    public FunctionEntity(String name, Location location, Type return_type, List<ParameterEntity> parameterEntityList, BlockNode body)
    {
        super(name, location, new FunctionType(name));
        this.parameterEntityList = parameterEntityList;
        this.body = body;
        this.return_type = return_type;
        ((FunctionType)this.type).setEntity(this);
    }

    public BlockNode getBody() {
        return body;
    }

    public List<ParameterEntity> getParameterEntityList() {
        return parameterEntityList;
    }

    public Type getReturn_type() {
        return return_type;
    }

    public boolean is_constructor() {
        return is_constructor;
    }

    public boolean is_inlined() {
        return is_inlined;
    }

    public boolean is_lib_function() {
        return is_lib_function;
    }

    public void setIs_constructor(boolean is_constructor) {
        this.is_constructor = is_constructor;
    }

    public void setIs_inlined(boolean is_inlined) {
        this.is_inlined = is_inlined;
    }

    public void setIs_lib_function(boolean is_lib_function) {
        this.is_lib_function = is_lib_function;
    }

    private String asm_name;

    public String getAsm_name()
    {
        return asm_name == null ? getName() : asm_name;
    }

    public void setAsm_name(String asm_name)
    {
        this.asm_name = asm_name;
    }

    public String toString() {return "Function Entity:" + getName();}

    private Scope scope;

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public ParameterEntity add_this_parameter(Location location, ClassEntity entity)
    {
        ParameterEntity thisVariable = new ParameterEntity("this", location, entity.getType());
        thisVariable.setIs_this();
        parameterEntityList.add(0, thisVariable);
        return thisVariable;
    }

    private Set<FunctionEntity> calls = new HashSet<>();
    public void add_call(FunctionEntity entity) {calls.add(entity);}

    public Set<FunctionEntity> getCalls()
    {
        return calls;
    }

    private Label begin_label_IR, end_label_IR;
    public void set_label_IR(Label begin_label_IR, Label end_label_IR)
    {
        this.begin_label_IR = begin_label_IR;
        this.end_label_IR = end_label_IR;
    }

    public Label getBegin_label_IR()
    {
        return begin_label_IR;
    }

    public Label getEnd_label_IR()
    {
        return end_label_IR;
    }

    private List<IR> irs;

    public List<IR> getIrs()
    {
        return irs;
    }

    public void setIrs(List<IR> irs)
    {
        this.irs = irs;
    }


}