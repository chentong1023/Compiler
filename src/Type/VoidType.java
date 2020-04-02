package Type;

public class VoidType extends Type
{
	static final int DEFAULT_SIZE = 0;

	@Override
	public boolean is_void()
	{
		return true;
	}

	@Override
	public int getSize()
	{
		return DEFAULT_SIZE;
	}

	@Override
	public String toString()
	{
		return "void";
	}

	@Override
	public boolean is_compatible(Type other)
	{
		return other.is_void();
	}
}