package ExceptionS;

import AST.Location;

public class InternalErrorS extends Error
{
	public InternalErrorS(String message)
	{
		super(message);
	}

	public InternalErrorS(Location location, String message)
	{
		super(location.toString() + message);
	}
}