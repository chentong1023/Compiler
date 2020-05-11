package BackEnd;

import AST.FunctionDefNode;
import Entity.*;
import IR.IR;
import Operand.Reference;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InstructionEmitter
{
	private List<FunctionEntity> functionEntityList;
	private Scope global_scope;
	private List<IR> global_initializer;

	private List<FunctionEntity> functionEntities;
	private FunctionEntity current_function;
	private boolean is_inleaf;

	public InstructionEmitter(IRBuilder irBuilder)
	{
		this.global_scope = irBuilder.global_scope();
		this.functionEntities = new LinkedList<>(irBuilder.functionEntities());
		for (ClassEntity classEntity : irBuilder.getAst().getClassEntityList())
		{
			for (FunctionDefNode memberFunction : classEntity.getMember_functions())
			{
				this.functionEntities.add(memberFunction.getEntity());
			}
		}
		this.global_initializer = irBuilder.getGlobal_initializer();
	}

	private List<Reference> tmp_stack;
	private int tmp_top = 0;
	private int tmp_counter = 0;
	public Reference getTmp()
	{
		if (tmp_top >= tmp_stack.size())
			tmp_stack.add(new Reference("ref_" + tmp_counter, Reference.Type.UNKNOWN));
		return tmp_stack.get(tmp_top++);
	}
	public void emit()
	{
		int string_counter = 1;
		for (Entity entity : global_scope.getEntities().values())
		{
			if (entity instanceof VariableEntity)
				entity.setReference(new Reference(entity.getName(), Reference.Type.GLOBAL));
			else if (entity instanceof StringConstantEntity)
			{
				((StringConstantEntity) entity).setAsm_name(StringConstantEntity.STRING_CONSTANT_ASM_LABEL_PREFIX + string_counter);
			}
		}
		for (FunctionEntity functionEntity : functionEntities)
		{
			current_function = functionEntity;
			tmp_stack = new ArrayList<>();
		}
	}
}
