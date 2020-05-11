package INS;

import Operand.Operand;
import Operand.Reference;

public class Cmp extends Instruction
{
	public enum Operator {
		EQ, NE, GE, LE, LT
	}
	private Operand left, right;
	private Operator operator;

	public Cmp(Operand left, Operand right, Operator operator)
	{
		this.left = left;
		this.right = right;
		this.operator = operator;
	}

	public Operand getLeft()
	{
		return left;
	}

	public Operand getRight()
	{
		return right;
	}

	public Operator getOperator()
	{
		return operator;
	}

	public void setLeft(Operand left)
	{
		this.left = left;
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
			left.replace(from, to);
	}

	@Override
	public void replace_def(Reference from, Reference to)
	{
		left = left.replace(from, to);
		right = right.replace(from, to);
	}

	@Override
	public void calc_def_and_use()
	{
		if (left instanceof Reference)
			def.addAll(left.get_all_ref());
		use.addAll(left.get_all_ref());
		use.addAll(right.get_all_ref());
		all_ref.addAll(use);
		all_ref.addAll(def);
	}

	@Override
	public String toString()
	{
		return "cmp" +
				left +
				", " + right;
	}
}
