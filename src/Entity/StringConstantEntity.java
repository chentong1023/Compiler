package Entity;

import AST.*;
import Type.*;

public class StringConstantEntity extends Entity {
    private ExprNode expr;
    private String value;
    private String asm_name;
    public static final String STRING_CONSTANT_ASM_LABEL_PREFIX = "__STR_CONST_";

    public StringConstantEntity(Location location, Type type, String name, ExprNode expr)
    {
        super(StringType.STRING_CONSTANT_PREFIX + name, location, type);
        this.expr = expr;
        name.replaceAll("\\\\" + "\"", "\"");
        name.replaceAll("\\\\" + "n", "\n");
        name.replaceAll("\\\\" + "\\\\", "\\\\");
        this.value = name;
    }

    public String getValue() {
        return value;
    }

    public String getAsm_name()
    {
        return asm_name;
    }

    public void setAsm_name(String asm_name)
    {
        this.asm_name = asm_name;
    }

    @Override
    public String toString() {return "Constant Entity: " + getName();}
}