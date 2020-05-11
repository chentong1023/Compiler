package Operand;

import ExceptionS.InternalErrorS;

import java.util.HashSet;
import java.util.Set;

public class Address extends Operand
{
	private Operand base = null, index = null;
	private int mul = 1, add = 0;
	private boolean show_size = true;
	private Operand base_NASM, index_NASM;

	public Address(Operand base)
	{
		this.base = base;
		if (base instanceof Address)
			throw new InternalErrorS("Invalid address : nested address");
	}

	public Address(Operand base, Operand index, int mul, int add)
	{
		this.base = base;
		this.index = index;
		this.mul = mul;
		this.add = add;

		if (base instanceof Address || index instanceof Address)
			throw new InternalErrorS("Invalid address : nested address");
	}

	@Override
	public Operand replace(Operand from, Operand to)
	{
		if (base != null)
			base = base.replace(from, to);
		if (index != null)
			index = index_NASM.replace(from, to);
		return this;
	}

	@Override
	public Set<Reference> get_all_ref()
	{
		Set<Reference> references = new HashSet<>();
		if (base != null)
			references.addAll(base.get_all_ref());
		if (index != null)
			references.addAll(index.get_all_ref());
		return references;
	}

	public Operand getBase()
	{
		return base;
	}

	public Operand getIndex()
	{
		return index;
	}

	public int getMul()
	{
		return mul;
	}

	public int getAdd()
	{
		return add;
	}

	public Operand getBase_NASM()
	{
		return base_NASM;
	}

	public Operand getIndex_NASM()
	{
		return index_NASM;
	}

	public void setBase(Operand base)
	{
		this.base = base;
	}

	public void setIndex(Operand index)
	{
		this.index = index;
	}

	public void setMul(int mul)
	{
		this.mul = mul;
	}

	public void setAdd(int add)
	{
		this.add = add;
	}

	public void setShow_size(boolean show_size)
	{
		this.show_size = show_size;
	}

	public void setBase_NASM(Operand base_NASM)
	{
		this.base_NASM = base_NASM;
	}

	public void setIndex_NASM(Operand index_NASM)
	{
		this.index_NASM = index_NASM;
	}

	public boolean base_only() { return base != null && index != null && mul == 1 && add == 0;}

	@Override
	public boolean is_direct()
	{
		if (base == null)
			return index.is_register();
		if (index == null)
			return base.is_register();
		else
			return index.is_register() && base.is_register();
	}

	@Override
	public boolean is_address()
	{
		return true;
	}

	@Override
	public String to_NASM()
	{
		String ret = show_size ? "qword" + "[" : "[";
		String gap = "";
		if (base != null)
		{
			ret += gap + base_NASM.to_NASM();
			gap = " + ";
		}
		if (index != null)
		{
			ret += gap + index_NASM.to_NASM();
			gap = " + ";
			if (mul != 1)
				ret += " * " + mul;
		}
		if (add != 0)
			ret += gap + add;
		return ret + "]";
	}

	@Override
	public int hashCode()
	{
		int hash = 0x93;
		if (base != null)
			hash *= base.hashCode();
		if (index != null)
			hash *= index.hashCode();
		hash = hash * mul + add;
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Address)
		{
			return base == ((Address) obj).getBase() && index == ((Address) obj).getIndex() && mul == ((Address) obj).getMul() && add == ((Address) obj).getAdd();
		}
		return false;
	}

	@Override
	public String toString()
	{
		String str = "";
		String gap = "";
		if (base != null)
		{
			str += gap + base;
			gap = " + ";
		}
		if (index != null)
		{
			str += gap + index;
			gap = " + ";
			if (mul != 1)
				str += " * " + mul;
		}
		if (add != 0)
			str += gap + add;
		return "[" + str + "]";
	}
}
