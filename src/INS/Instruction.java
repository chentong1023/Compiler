package INS;

import Operand.Reference;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

abstract public class Instruction
{
	protected List<Instruction> predecessor = new LinkedList<>();
	protected List<Instruction> successor = new LinkedList<>();

	protected Set<Reference> in = new HashSet<>();
	protected Set<Reference> out = new HashSet<>();

	protected Set<Reference> use;
	protected Set<Reference> def;
	protected Set<Reference> all_ref;
	protected Set<Reference> live;

	abstract public void replace_use(Reference from, Reference to);
	abstract public void replace_def(Reference from, Reference to);

	public void replace_all(Reference from, Reference to)
	{
		this.replace_use(from, to);
		this.replace_def(from, to);
	}

	public List<Instruction> getPredecessor()
	{
		return predecessor;
	}

	public List<Instruction> getSuccessor()
	{
		return successor;
	}

	public Set<Reference> getIn()
	{
		return in;
	}

	public Set<Reference> getOut()
	{
		return out;
	}

	public Set<Reference> getUse()
	{
		if (use == null)
		{
			init_def_and_use();
			this.calc_def_and_use();
		}
		return use;
	}

	abstract public void calc_def_and_use();

	public void init_def_and_use()
	{
		use = new HashSet<>();
		def = new HashSet<>();
		all_ref = new HashSet<>();
	}

	public Set<Reference> getDef()
	{
		if (def == null)
		{
			init_def_and_use();
			this.calc_def_and_use();
		}
		return def;
	}

	public Set<Reference> getAll_ref()
	{
		if (all_ref == null)
		{
			init_def_and_use();
			this.calc_def_and_use();
		}
		return all_ref;
	}

	public Set<Reference> getLive()
	{
		if (live == null)
		{
			live = new HashSet<>();
			for (Reference reference : in)
			{
				if (reference.alias != null)
					live.add(reference.alias);
				else
					live.add(reference);
			}
			for (Reference reference : out)
			{
				if (reference.alias != null)
					live.add(reference.alias);
				else
					live.add(reference);
			}
		}
		return live;
	}

	public void setIn(Set<Reference> in)
	{
		this.in = in;
	}

	public void setOut(Set<Reference> out)
	{
		this.out = out;
	}

	public void setLive(Set<Reference> live)
	{
		this.live = live;
	}

	abstract public void accept(INSVisitor visitor);
}
