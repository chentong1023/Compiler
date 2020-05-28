package INS;

import Operand.Operand;
import Operand.Reference;

public class Not extends Instruction
{
	private Operand operand;

	public Operand getOperand()
	{
		return operand;
	}

	public Not(Operand operand)
	{
		this.operand = operand;
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{
		if (!(operand instanceof Reference))
			operand = operand.replace(from, to);
	}

	@Override
	public void replace_def(Reference from, Reference to)
	{
		if (operand instanceof Reference)
			operand = operand.replace(from, to);
	}

	@Override
	public void replace_all(Reference from, Reference to)
	{
		operand = operand.replace(from, to);
	}

	@Override
	public void calc_def_and_use()
	{
		if (operand instanceof Reference)
			def.addAll(operand.get_all_ref());
		use.addAll(operand.get_all_ref());
		all_ref.addAll(use);
		all_ref.addAll(def);
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "not " + operand;
	}
}
