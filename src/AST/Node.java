package AST;

import java.io.PrintStream;

abstract public class Node
{
	private String name = "";
	private Location location = new Location();

	public Node(String name, Location location)
	{
		if (name == null)
			name = "null";
		this.name = name;
		this.location = location;
	}

	public Location getLocation()
	{
		return location;
	}

	public String getName()
	{
		return name;
	}

	public void print(int offset)
	{
		String out = "";
		for (int i = 0; i < offset; ++i)
			out += " ";
		System.out.println(out + this.getName() + " " + this.getLocation().toString());
	}

	private boolean is_output_irrelevant = false;

	public boolean isIs_output_irrelevant()
	{
		return is_output_irrelevant;
	}

	public void setIs_output_irrelevant(boolean is_output_irrelevant)
	{
		this.is_output_irrelevant = is_output_irrelevant;
	}
}