package Operand;

import java.util.HashSet;
import java.util.Set;

public class Register extends Operand
{
	private String name;
	private boolean callee_save;
	public Register (String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public boolean isCallee_save()
	{
		return callee_save;
	}

	public void setCallee_save(boolean callee_save)
	{
		this.callee_save = callee_save;
	}

	@Override
	public Operand replace(Operand from, Operand to)
	{
		return this;
	}

	@Override
	public Set<Reference> get_all_ref()
	{
		return new HashSet<>();
	}

	@Override
	public String to_NASM()
	{
		return name;
	}

	@Override
	public boolean is_register()
	{
		return true;
	}

	@Override
	public boolean is_direct()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
