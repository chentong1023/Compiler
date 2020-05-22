package INS;

import Operand.Address;
import Operand.Immediate;
import Operand.Reference;

public class Lea extends Instruction
{
	private Reference dest;
	private Address addr;

	public Lea(Reference dest, Address addr)
	{
		this.dest = dest;
		this.addr = addr;
	}

	public Reference getDest()
	{
		return dest;
	}

	public Address getAddr()
	{
		return addr;
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{
		addr = addr.replace(from, to);
		if (dest != from)
			dest = ((Reference) dest.replace(from, to));
	}

	@Override
	public void replace_def(Reference from, Reference to)
	{
		dest = ((Reference) dest.replace(from, to));
	}

	@Override
	public void replace_all(Reference from, Reference to)
	{
		dest = ((Reference) dest.replace(from, to));
		addr = addr.replace(from, to);
	}

	@Override
	public void calc_def_and_use()
	{
		if (dest instanceof Reference)
			def.addAll(dest.get_all_ref());
		use.addAll(addr.get_all_ref());
		all_ref.addAll(use);
		all_ref.addAll(def);
	}

	@Override
	public void accept(INSVisitor visitor)
	{
		visitor.visit(this);
	}

	public String toString() {return "lea " + dest + ", " + addr;}
}
