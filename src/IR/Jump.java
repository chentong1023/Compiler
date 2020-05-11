package IR;

public class Jump extends IR
{
	Label label;

	public Jump(Label label)
	{
		this.label = label;
	}

	public Label getLabel()
	{
		return label;
	}

	@Override
	public String toString()
	{
		return "Jump -> " + label;
	}
}
