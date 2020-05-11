package INS;

import Entity.FunctionEntity;
import Operand.Operand;
import Operand.Reference;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class InsCall extends Instruction
{
	private FunctionEntity entity;
	private List<Operand> operands;
	private Operand ret;
	private Set<Reference> callorsave;
	private Set<Reference> used_parameter_register;

	public InsCall(FunctionEntity entity, List<Operand> operands)
	{
		this.entity = entity;
		this.operands = operands;
		this.ret = null;
	}

	public FunctionEntity getEntity()
	{
		return entity;
	}

	public List<Operand> getOperands()
	{
		return operands;
	}

	public Operand getRet()
	{
		return ret;
	}

	public void setRet(Operand ret)
	{
		this.ret = ret;
	}

	public Set<Reference> getCallorsave()
	{
		return callorsave;
	}

	public void setCallorsave(Set<Reference> callorsave)
	{
		this.callorsave = callorsave;
	}

	public Set<Reference> getUsed_parameter_register()
	{
		return used_parameter_register;
	}

	public void setUsed_parameter_register(Set<Reference> used_parameter_register)
	{
		this.used_parameter_register = used_parameter_register;
	}

	@Override
	public void replace_use(Reference from, Reference to)
	{
		List<Operand> new_operands = new LinkedList<>();
		for (Operand operand : operands)
			new_operands.add(operand.replace(from, to));
		operands = new_operands;

		Set<Reference> new_para_reg = new HashSet<>();
		for (Reference reference : used_parameter_register)
		{
			new_para_reg.add(((Reference) reference.replace(from, to)));
		}
		used_parameter_register = new_para_reg;
	}

	@Override
	public void replace_def(Reference from, Reference to)
	{
		if (ret != null)
			ret = ret.replace(from, to);
	}

	@Override
	public void calc_def_and_use()
	{
		if (ret != null)
			def.addAll(ret.get_all_ref());
		if (callorsave != null)
			def.addAll(callorsave);
		for (Operand operand : operands)
		{
			use.addAll(operand.get_all_ref());
		}
		if (used_parameter_register != null)
			for (Reference reference : used_parameter_register)
			{
				use.addAll(reference.get_all_ref());
			}
		all_ref.addAll(use); all_ref.addAll(def);
	}
}
