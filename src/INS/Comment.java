package INS;

import Operand.Reference;

public class Comment extends Instruction
{
	private String comment;

	public Comment(String comment)
	{
		this.comment = comment;
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{

	}

	@Override
	public void replace_def(Reference from, Reference to)
	{

	}

	@Override
	public void calc_def_and_use()
	{

	}

	@Override
	public void replace_all(Reference from, Reference to)
	{

	}

	@Override
	public String toString()
	{
		return comment;
	}
}
