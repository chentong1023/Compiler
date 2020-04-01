package Type;

abstract public class Type {
    private String name = "";
    static public BoolType boolType = new BoolType();
    static public IntegerType integerType = new IntegerType();
    static public VoidType voidType = new VoidType();
    static public StringType stringType = new StringType();
    static public NullType nullType = new NullType();

    Type() {}
    Type(String name) {this.name = name;}

    public String getName() {return name;}
    abstract public int getSize();
    abstract public String toString();

    public boolean is_int() {return false;}
    public boolean is_void() {return false;}
    public boolean is_bool() {return false;}
    public boolean is_string() {return false;}
    public boolean is_array() {return false;}
    public boolean is_class() {return false;}
    public boolean is_function() {return false;}
    public boolean is_null() {return false;}
    public boolean is_compatible(Type type) {return false;}
    public boolean is_half_comparable() {return false;}
    public boolean is_full_comparable() {return false;}
    public long allocSize() {return getSize();}

    static public void initialize_builtin_type()
    {
        StringType.initialize_builtin_function();
        ArrayType.initialize_builtin_function();
    }
}