package IR;

import Operand.Operand;

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

	@Override
	public Operand accept(IRVisitor emitter)
	{
		return emitter.visit(this);
	}
}
