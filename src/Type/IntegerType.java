package Type;

public class IntegerType extends Type {
    static final int DEFAULT_SIZE = 4;

    @Override
    public boolean is_int() {return true;}

    @Override
    public boolean is_compatible(Type other) {return other.is_int();}
    @Override
    public boolean is_full_comparable() {return true;}
    @Override
    public boolean is_half_comparable() {return true;}

    @Override
    public int getSize() {return DEFAULT_SIZE;}

    @Override
    public String toString() {return "int";}
}