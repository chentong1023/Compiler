package Type;

import Entity.Scope;
import ExceptionS.SemanticError;
import Utils.LibFunction;

public class ArrayType extends Type {
    static final int DEFAULT_SIZE = 8;
    private Type base_type;

    public ArrayType(Type base_type) {this.base_type = base_type;}

    public ArrayType(Type _base_type, int dimension)
    {
        if (dimension == 1)
            base_type = _base_type;
        else
            base_type = new ArrayType(_base_type, dimension - 1);
    }

    static private ArrayType magicArray = new ArrayType(nullType);
    static public void initialize_builtin_function()
    {
        scope = new Scope(true);
        scope.insert(new LibFunction(integerType, "size", LibFunction.LIB_PREFIX + "array_size", new Type[]{magicArray}).getEntity());
    }

    static private Scope scope;
    static public Scope getScope() {return scope;}
    public Type dfs_type() {return base_type instanceof ArrayType ? ((ArrayType) base_type).dfs_type() : base_type;}
    public Type getBase_type() {return base_type;}

    @Override
    public int getSize() {return DEFAULT_SIZE;}

    @Override
    public String toString() {return base_type.toString() + "[]";}
    @Override
    public boolean is_array() {return true;}
    @Override
    public boolean is_half_comparable() {return true;}

    @Override
    public boolean is_compatible(Type other)
    {
        if (other.is_null()) return true;
        if (!other.is_array()) return false;
        return base_type.is_compatible(((ArrayType)other).getBase_type());
    }
}