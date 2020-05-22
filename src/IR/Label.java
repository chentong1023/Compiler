package IR;

import Operand.Operand;

public class Label extends IR
{
	private String name;

	public Label(String name)
	{
		this.name = name;
	}

	public Label()
	{
		this.name = null;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Label " + name;
	}

	@Override
	public Operand accept(IRVisitor emitter)
	{
		return emitter.visit(this);
	}
}
