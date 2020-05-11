package INS;

import Operand.Operand;
import Operand.Reference;

import java.sql.Ref;

public class Move extends Instruction
{
	private Operand dest, src;

	public Move(Operand dest, Operand src)
	{
		this.dest = dest;
		this.src = src;
	}

	public Operand getDest()
	{
		return dest;
	}

	public void setDest(Operand dest)
	{
		this.dest = dest;
	}

	public Operand getSrc()
	{
		return src;
	}

	public void setSrc(Operand src)
	{
		this.src = src;
	}

	public boolean is_ref_move()
	{
		if (dest instanceof Reference && src instanceof Reference)
		{
			Reference.Type type1 = ((Reference) src).getType();
			Reference.Type type2 = ((Reference) dest).getType();
			return (type1 == Reference.Type.UNKNOWN || type1 == Reference.Type.REG) && (type2 == Reference.Type.UNKNOWN || type2 == Reference.Type.REG);
		}
		return false;
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{
		src = src.replace(from, to);
		if (!(dest instanceof Reference))
			dest = dest.replace(from, to);
	}

	@Override
	public void replace_def(Reference from, Reference to)
	{
		dest = dest.replace(from, to);
	}

	@Override
	public void calc_def_and_use()
	{
		if (dest instanceof Reference)
		{
			def.addAll(dest.get_all_ref());
			use.addAll(src.get_all_ref());
		}
		else
		{
			use.addAll(dest.get_all_ref());
			use.addAll(src.get_all_ref());
		}
		all_ref.addAll(use);
		all_ref.addAll(def);
	}

	@Override
	public void replace_all(Reference from, Reference to)
	{
		src = src.replace(from, to);
		dest = dest.replace(from, to);
	}

	@Override
	public String toString()
	{
		return "Mov " + dest +
				", " + src
				;
	}
}
