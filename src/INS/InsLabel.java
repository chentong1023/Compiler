package INS;

import BackEnd.BasicBlock;
import Operand.Reference;

import java.util.HashSet;
import java.util.Set;

public class InsLabel extends Instruction
{
	private String name;
	private BasicBlock basicBlock;
	private Set<Reference> bring_use = new HashSet<>();

	public InsLabel(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public Set<Reference> getBring_use()
	{
		return bring_use;
	}

	public void setBring_use(Set<Reference> bring_use)
	{
		this.bring_use = bring_use;
	}

	public BasicBlock getBasicBlock()
	{
		return basicBlock;
	}

	public void setBasicBlock(BasicBlock basicBlock)
	{
		this.basicBlock = basicBlock;
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
	public void replace_all(Reference from, Reference to)
	{
		replace_def(from, to);
	}

	@Override
	public void calc_def_and_use()
	{

	}

	public String toString() {return name;}
}
