package Type;

import Entity.*;
import Utils.LibFunction;

public class StringType extends Type {
    static final int DEFAULT_SIZE = 4;
    public static final String STRING_CONSTANT_PREFIX = "__STR_CONST_";
    static public FunctionEntity operatorADD, operatorEQ, operatorNE, operatorLT, operatorGT, operatorLE, operatorGE;
    @Override
    public boolean is_string() {return true;}
    @Override
    public int getSize() {return DEFAULT_SIZE;}

    @Override
    public String toString() {return "String";}

    @Override
    public boolean is_compatible(Type other) {return other.is_string();}

    @Override
    public boolean is_full_comparable() {return true;}
    @Override
    public boolean is_half_comparable() {return true;}

    static private Scope scope;
    static public void initialize_builtin_function()
    {
        operatorADD = new LibFunction(stringType, "string_add", new Type[] {stringType, stringType}).getEntity();
        operatorEQ = new LibFunction(stringType, "string_eq", new Type[] {stringType, stringType}).getEntity();
        operatorNE = new LibFunction(stringType, "string_ne", new Type[] {stringType, stringType}).getEntity();
        operatorLT = new LibFunction(stringType, "string_lt", new Type[] {stringType, stringType}).getEntity();
        operatorGT = new LibFunction(stringType, "string_gt", new Type[] {stringType, stringType}).getEntity();
        operatorLE = new LibFunction(stringType, "string_le", new Type[] {stringType, stringType}).getEntity();
        operatorGE = new LibFunction(stringType, "string_ge", new Type[] {stringType, stringType}).getEntity();

        scope = new Scope(true);
        scope.insert(new LibFunction(integerType, "length", "string_length", new Type[] {stringType}).getEntity());
        scope.insert(new LibFunction(stringType, "substring", "string_substring", new Type[] {stringType, integerType, integerType}).getEntity());
        scope.insert(new LibFunction(integerType, "parseInt", "string_parseInt", new Type[] {stringType}).getEntity());
        scope.insert(new LibFunction(integerType, "ord", "string_ord", new Type[] {stringType, integerType}).getEntity());

        scope.insert(operatorADD);
        scope.insert(operatorLT);
        scope.insert(operatorEQ);
    }

    static public Scope getScope() {return scope;}
}