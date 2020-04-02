package Type;

public class NullType extends ClassType
{
	public NullType()
	{
		super("null");
	}

	@Override
	public boolean is_null()
	{
		return true;
	}

	@Override
	public boolean is_half_comparable()
	{
		return true;
	}

	@Override
	public boolean is_compatible(Type other)
	{
		return other.is_array() || other.is_class() || other.is_null();
	}
}