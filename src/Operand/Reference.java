package Operand;

import Entity.Entity;
import ExceptionS.InternalErrorS;
import INS.Move;

import java.util.HashSet;
import java.util.Set;

public class Reference extends Operand
{
	public enum Type {GLOBAL, OFFSET, REG, UNKNOWN, UNUSED, CANNOT_COLOR, SPECIAL}

	private Type type;
	private String name;
	private int offset;
	private Entity entity;
	private Register register;

	public Reference(String name, Type type)
	{
		this.name = name;
		this.type = type;
	}

	public Reference(Register register)
	{
		set_register(register);
		this.name = register.getName();
	}

	public void set_register(Register register)
	{
		this.register = register;
		this.type = Type.REG;
	}

	public Reference(int offset, Register register)
	{
		set_offset(offset, register);
	}

	public void set_offset(int offset, Register register)
	{
		this.offset = offset;
		this.register = register;
		this.type = Type.OFFSET;
	}

	public Reference(Entity entity)
	{
		this.name = entity.getName();
		this.entity = entity;
		this.type = Type.UNKNOWN;
	}

	public String getName()
	{
		return name;
	}

	public Type getType()
	{
		return type;
	}

	public int getOffset()
	{
		return offset;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public Register getRegister()
	{
		return register;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public void setOffset(int offset, Register register)
	{
		this.offset = offset;
		this.register = register;
		this.type = Type.OFFSET;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}

	public void setRegister(Register register)
	{
		this.register = register;
	}

	@Override
	public Operand replace(Operand from, Operand to)
	{
		return this == from ? to : this;
	}

	@Override
	public Set<Reference> get_all_ref()
	{
		Set<Reference> references = new HashSet<>();
		if (this.type != Type.GLOBAL && this.type != Type.CANNOT_COLOR && this.type != Type.SPECIAL)
			references.add(this);
		return references;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public String to_NASM()
	{
		try {
			switch (type) {
				case GLOBAL: return "." + name;
				case OFFSET: return offset + "("+ register.getName() +")";
				case REG:    return register.getName();
				case SPECIAL:return name;
				case UNUSED:
				case UNKNOWN:
				default:
					throw new Exception();
					//throw new InternalError("Unallocated reference " + this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean is_register()
	{
		return type == Type.REG;
	}

	@Override
	public boolean is_direct()
	{
		return true;
	}

	@Override
	public boolean is_address()
	{
		return type == Type.GLOBAL || type == Type.OFFSET || type == Type.CANNOT_COLOR;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Reference)
		{
			Reference other = (Reference) obj;
			if (type != other.type)
				return false;
			switch (type)
			{
				case REG: return register == other.register;
				case OFFSET:
					return register == other.register && offset == other.offset;
				case GLOBAL:
					return name == other.getName();
				case UNKNOWN:
					return this == other;
				default:
					throw new InternalErrorS("Unhandled case in Reference.equals()");
			}
		}
		return false;
	}

	public Reference alias;
	public boolean can_be_accumulator() { return type == Type.UNKNOWN && entity == null;}

	public boolean is_precolored;
	public Register color;
	public int ref_times;
	public Set<Reference> adj_list = new HashSet<>();
	public int degree;
	public Set<Move> move_list = new HashSet<>();
	public boolean is_spilled;

	public void reset()
	{
		ref_times = 0;
		move_list = new HashSet<>();
		adj_list = new HashSet<>();
		if (!is_precolored)
		{
			color = null;
			degree = 0;
		}
		else
			degree = Integer.MAX_VALUE;
		alias = null;
		is_spilled = false;
	}

	public void add_ref_time() { ref_times++; }
	public boolean is_unknown() {return type == Type.UNKNOWN && color == null;}
}
