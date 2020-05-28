package INS;

import Operand.Operand;
import Operand.Reference;

public class Push extends Instruction
{
	private Operand operand;

	public Push(Operand operand)
	{
		this.operand = operand;
	}

	public Operand getOperand()
	{
		return operand;
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{
		operand.replace(from, to);
	}

	@Override
	public void replace_def(Reference from, Reference to)
	{
		operand.replace(from, to);
	}

	@Override
	public void replace_all(Reference from, Reference to)
	{
		operand.replace(from, to);
	}

	@Override
	public void calc_def_and_use()
	{
		use.addAll(operand.get_all_ref());
		all_ref.addAll(use);
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "push " + operand;
	}
}
