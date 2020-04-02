package Type;

public class BoolType extends Type
{

	static final int DEFAULT_SIZE = 8;

	@Override
	public boolean is_bool()
	{
		return true;
	}

	@Override
	public boolean is_compatible(Type Other)
	{
		return Other.is_bool();
	}

	@Override
	public boolean is_half_comparable()
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
		return "bool";
	}
}