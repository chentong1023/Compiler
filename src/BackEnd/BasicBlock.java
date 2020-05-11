package BackEnd;

import INS.InsLabel;
import INS.Instruction;
import Operand.Reference;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BasicBlock
{
	private List<BasicBlock> predecessor = new LinkedList<>();
	private List<BasicBlock> successor = new LinkedList<>();
	private InsLabel label;
	private List<Instruction> ins = new LinkedList<>();
	private List<InsLabel> jump_to = new LinkedList<>();
	private boolean layouted = false;

	private Set<Reference> use = new HashSet<>();
	private Set<Reference> def = new HashSet<>();

	private Set<Reference> live_in = new HashSet<>();
	private Set<Reference> live_out = new HashSet<>();

	public BasicBlock(InsLabel label)
	{
		this.label = label;
	}

	public List<BasicBlock> getPredecessor()
	{
		return predecessor;
	}

	public void setPredecessor(List<BasicBlock> predecessor)
	{
		this.predecessor = predecessor;
	}

	public List<BasicBlock> getSuccessor()
	{
		return successor;
	}

	public void setSuccessor(List<BasicBlock> successor)
	{
		this.successor = successor;
	}

	public InsLabel getLabel()
	{
		return label;
	}

	public void setLabel(InsLabel label)
	{
		this.label = label;
	}

	public List<Instruction> getIns()
	{
		return ins;
	}

	public void setIns(List<Instruction> ins)
	{
		this.ins = ins;
	}

	public List<InsLabel> getJump_to()
	{
		return jump_to;
	}

	public void setJump_to(List<InsLabel> jump_to)
	{
		this.jump_to = jump_to;
	}

	public boolean isLayouted()
	{
		return layouted;
	}

	public void setLayouted(boolean layouted)
	{
		this.layouted = layouted;
	}

	public Set<Reference> getUse()
	{
		return use;
	}

	public void setUse(Set<Reference> use)
	{
		this.use = use;
	}

	public Set<Reference> getDef()
	{
		return def;
	}

	public void setDef(Set<Reference> def)
	{
		this.def = def;
	}

	public Set<Reference> getLive_in()
	{
		return live_in;
	}

	public void setLive_in(Set<Reference> live_in)
	{
		this.live_in = live_in;
	}

	public Set<Reference> getLive_out()
	{
		return live_out;
	}

	public void setLive_out(Set<Reference> live_out)
	{
		this.live_out = live_out;
	}

	public String toString() {return label.toString();}
}
