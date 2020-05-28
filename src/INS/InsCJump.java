package INS;

import ExceptionS.InternalErrorS;
import Operand.Operand;
import Operand.Reference;

import java.util.HashSet;
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

	public InsCJump(Operand left, Operand right, InsLabel true_label, InsLabel false_label, Type type)
	{
		this.true_label = true_label;
		this.false_label = false_label;
		this.left = left;
		this.right = right;
		this.type = type;
	}

	public Operand getCond()
	{
		return cond;
	}

	public InsLabel getTrue_label()
	{
		return true_label;
	}

	public void setTrue_label(InsLabel true_label)
	{
		this.true_label = true_label;
	}

	public InsLabel getFalse_label()
	{
		return false_label;
	}

	public void setFalse_label(InsLabel false_label)
	{
		this.false_label = false_label;
	}

	public InsLabel getFall_through()
	{
		return fall_through;
	}

	public void setFall_through(InsLabel fall_through)
	{
		this.fall_through = fall_through;
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

	public Set<Reference> getBring_out()
	{
		return bring_out;
	}

	public void setBring_out(Set<Reference> bring_out)
	{
		this.bring_out = bring_out;
	}

	public Type getType()
	{
		return type;
	}

	public String name()
	{
		switch (type)
		{
			case EQ: return "je";
			case NE: return "jne";
			case GT: return "jg";
			case GE: return "jge";
			case LT: return "jl";
			case LE: return "jle";
			default:
				throw new InternalErrorS("Invalid compare operator");
		}
	}

	public String get_not_name(String raw)
	{
		switch (raw)
		{
			case "je":  return "jne";
			case "jne": return "je";
			case "jg":  return "jle";
			case "jge": return "jl";
			case "jl":  return "jge";
			case "jle": return "jg";
			default:
				throw new InternalErrorS("invalid compare operator");
		}
	}

	static public String get_reflect(String raw)
	{
		switch (raw)
		{
			case "je" : return "je";
			case "jne": return "jne";
			case "jg":  return "jlt";
			case "jge": return "jle";
			case "jl":  return "jgt";
			case "jle": return "jge";
			default:
				throw new InternalError("invalid compare operator");
		}
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{
		if (bring_out != null && bring_out.contains(from))
		{
			Set<Reference> new_bring_out = new HashSet<>();
			for (Reference reference : bring_out)
				new_bring_out.add(((Reference) reference.replace(from, to)));
			bring_out = new_bring_out;
		}

		if (type == Type.BOOL)
			cond = cond.replace(from, to);
		else
		{
			left = left.replace(from, to);
			right = right.replace(from, to);
		}
	}

	@Override
	public void replace_def(Reference from, Reference to)
	{

	}

	@Override
	public void calc_def_and_use()
	{
		if (type == Type.BOOL)
			use.addAll(cond.get_all_ref());
		else
		{
			use.addAll(left.get_all_ref());
			use.addAll(right.get_all_ref());
		}
		if (bring_out != null)
			use.addAll(bring_out);
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
		if (type == Type.BOOL)
			return "CJump " + cond + ", " + true_label + ", " + false_label;
		else
			return name() + " " + left + " " + right + ", " + true_label + ", " + false_label;
	}
}
