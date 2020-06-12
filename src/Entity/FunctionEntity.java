package Entity;

import AST.*;
import BackEnd.BasicBlock;
import INS.InsLabel;
import INS.Instruction;
import IR.IR;
import IR.Label;
import Operand.Reference;
import Operand.Register;
import Type.*;

import java.util.*;

public class FunctionEntity extends Entity {
    private boolean is_constructor = false;
    private boolean is_lib_function = false;
    private boolean is_inlined = false;

    private final List<ParameterEntity> parameterEntityList;
    private final BlockNode body;
    private final Type return_type;

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

    private Map<FunctionEntity, Boolean> visited;
    private int stmt_size;
    public void check_inlinable()
    {
        if (getName().equals("main"))
            is_inlined = false;
        else
        {
            visited = new HashMap<>();
            is_inlined = !find_loop(this, this);
            stmt_size = stmtSize(body);
        }
    }

    private int stmtSize(StmtNode body)
    {
        int ret = 0;
        if (body == null) return 0;
        if (body instanceof BlockNode)
        {
            for (StmtNode stmt : ((BlockNode) body).getStmts())
            {
                if (stmt instanceof BlockNode)
                    ret += stmtSize(stmt);
                else if (stmt instanceof ForNode)
                    ret += 3 + stmtSize(((ForNode) stmt).getBody());
                else if (stmt instanceof IfNode)
                    ret += 1 + stmtSize(((IfNode) stmt).getThen_body()) + stmtSize(((IfNode) stmt).getElse_body());
                else ret += 1;
            }
        }
        else return 1;
        return ret;
    }

    private boolean find_loop(FunctionEntity called, FunctionEntity root)
    {
        if (visited.containsKey(called))
            return called == root;
        visited.put(called, true);
        for (FunctionEntity call : called.calls)
        {
            if (find_loop(call, root))
                return true;
        }
        return false;
    }

    public boolean self_inline(int depth)
    {
        if (depth >= 3)
            return false;
        int cnt = 1;
        for (int i = 0; i < depth + 1; ++i)
        {
            cnt *= stmt_size;
            if (cnt >= 40) return false;
        }
        return true;
    }

    private final Set<FunctionEntity> calls = new HashSet<>();
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

    private List<Instruction> ins;

    public List<Instruction> getIns()
    {
        return ins;
    }

    public void setIns(List<Instruction> ins)
    {
        this.ins = ins;
    }

    private List<Reference> tmp_stack;

    public List<Reference> getTmp_stack()
    {
        return tmp_stack;
    }

    public void setTmp_stack(List<Reference> tmp_stack)
    {
        this.tmp_stack = tmp_stack;
    }

    public List<VariableEntity> all_local_variables() {return scope.all_local_variables();}

    private InsLabel begin_label_INS, end_label_INS;

    public InsLabel getBegin_label_INS()
    {
        return begin_label_INS;
    }

    public InsLabel getEnd_label_INS()
    {
        return end_label_INS;
    }

    public void set_label_INS(InsLabel begin_label_INS, InsLabel end_label_INS)
    {
        this.begin_label_INS = begin_label_INS;
        this.end_label_INS = end_label_INS;
    }

    private List<BasicBlock> basicBlocks;

    public List<BasicBlock> getBasicBlocks()
    {
        return basicBlocks;
    }

    public void setBasicBlocks(List<BasicBlock> basicBlocks)
    {
        this.basicBlocks = basicBlocks;
    }

    private Set<Reference> all_reference;
    private List<Register> reg_used;
    private int local_variable_offset;

    public Set<Reference> getAll_reference()
    {
        return all_reference;
    }

    public void setAll_reference(Set<Reference> all_reference)
    {
        this.all_reference = all_reference;
    }

    public List<Register> getReg_used()
    {
        return reg_used;
    }

    public void setReg_used(List<Register> reg_used)
    {
        this.reg_used = reg_used;
    }

    public int getLocal_variable_offset()
    {
        return local_variable_offset;
    }

    public void setLocal_variable_offset(int local_variable_offset)
    {
        this.local_variable_offset = local_variable_offset;
    }

    private int frame_size;

    public int getFrame_size()
    {
        return frame_size;
    }

    public void setFrame_size(int frame_size)
    {
        this.frame_size = frame_size;
    }
}