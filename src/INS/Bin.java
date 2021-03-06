package INS;

import Operand.*;

abstract public class Bin extends Instruction
{
	private Operand left, right;

	public Bin(Operand left, Operand right)
	{
		this.left = left;
		this.right = right;
	}

	public Operand getLeft()
	{
		return left;
	}

	public void setLeft(Operand left)
	{
		this.left = left;
	}

	public Operand getRight()
	{
		return right;
	}

	public void setRight(Operand right)
	{
		this.right = right;
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{
		right = right.replace(from, to);
		if (left != from)
			left = left.replace(from, to);
	}

	@Override
	public void replace_def(Reference from, Reference to)
	{
		left = left.replace(from, to);
	}

	@Override
	public void replace_all(Reference from, Reference to)
	{
		left = left.replace(from, to);
		right = right.replace(from, to);
	}

	@Override
	public void calc_def_and_use()
	{
		if (left instanceof Reference) def.addAll(left.get_all_ref());
		use.addAll(left.get_all_ref());
		use.addAll(right.get_all_ref());
		all_ref.addAll(use);
		all_ref.addAll(def);
	}

	abstract public String name();
	abstract public void accept(INSVisitor visitor);

	public String toString() {return this.name() + " " + left + ", " + right;}
}
