package Entity;

import AST.*;
import Type.*;

public class StringConstantEntity extends Entity {
    private ExprNode expr;
    private String value;

    public StringConstantEntity(Location location, Type type, String name, ExprNode expr)
    {
        super(StringType.STRING_CONSTANT_PREFIX + name, location, type);
        this.expr = expr;
        this.value = name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {return "Constant Entity: " + getName();}
}