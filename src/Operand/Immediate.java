package Operand;

import ExceptionS.InternalErrorS;

import java.util.HashSet;
import java.util.Set;

public class Immediate extends Operand
{
	public enum Type {LABEL, INTEGER}
	private int value;
	private String label;
	private Type type;

	public Immediate(int value)
	{
		this.value = value;
		this.type = Type.INTEGER;
	}

	public Immediate(String label)
	{
		this.label = label;
		this.type = Type.LABEL;
	}

	public int getValue()
	{
		return value;
	}

	public String getLabel()
	{
		return label;
	}

	public Type getType()
	{
		return type;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	@Override
	public int hashCode()
	{
		switch (type)
		{
			case LABEL: return label.hashCode();
			case INTEGER: return value;
			default: throw new InternalErrorS("Invalid type of immediate@hashcode");
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Immediate)
		{
			switch (((Immediate) obj).getType())
			{
				case INTEGER: return value == ((Immediate) obj).getValue();
				case LABEL: return label.equals(((Immediate) obj).getLabel());
				default: throw new InternalErrorS("Invalid type of immediate@equals");
			}
		}
		return false;
	}

	@Override
	public Operand replace(Operand from, Operand to)
	{
		return this;
	}

	@Override
	public boolean is_direct()
	{
		return true;
	}

	@Override
	public boolean is_const_int()
	{
		return type == Type.INTEGER;
	}

	@Override
	public Set<Reference> get_all_ref()
	{
		return new HashSet<>();
	}

	@Override
	public String to_NASM()
	{
		if (type == Type.INTEGER)
			return Integer.toString(value);
		else
			return label;
	}

	@Override
	public String toString()
	{
		if (type == Type.INTEGER)
			return Integer.toString(value);
		else
			return label;
	}
}
