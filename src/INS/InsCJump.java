package INS;

import Operand.Operand;
import Operand.Reference;

import java.util.Set;

public class InsCJump extends Instruction
{
	private Operand cond;
	private InsLabel true_label, false_label;
	private InsLabel fall_through;
	private Operand left, right;
	private Set<Reference> bring_out;

	public enum Type {EQ, NE, GT, GE, LT, LE, BOOL}
	Type type;

	public InsCJump(Operand cond, InsLabel true_label, InsLabel false_label)
	{
		this.type = Type.BOOL;
		this.cond = cond;
		this.true_label = true_label;
		this.false_label = false_label;
	}

	public InsCJump(InsLabel true_label, InsLabel false_label, Operand left, Operand right, Type type)
	{
		this.true_label = true_label;
		this.false_label = false_label;
		this.left = left;
		this.right = right;
		this.type = type;
	}

	public void setTrue_label(InsLabel true_label)
	{
		this.true_label = true_label;
	}

	public void setFalse_label(InsLabel false_label)
	{
		this.false_label = false_label;
	}

	public Set<Reference> getBring_out()
	{
		return bring_out;
	}

	public void setBring_out(Set<Reference> bring_out)
	{
		this.bring_out = bring_out;
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
}
