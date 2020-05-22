package INS;

import Operand.Operand;
import Operand.Reference;

public class InsReturn extends Instruction
{
	private Operand ret;

	public InsReturn(Operand ret)
	{
		this.ret = ret;
	}

	public Operand getRet()
	{
		return ret;
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{
		if (ret != null)
			ret = ret.replace(from, to);
	}

	@Override
	public void replace_def(Reference from, Reference to)
	{
	}

	@Override
	public void calc_def_and_use()
	{
		if (ret != null)
		{
			use.addAll(ret.get_all_ref());
			all_ref.addAll(use);
		}
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "ret " + ret;
	}
}
