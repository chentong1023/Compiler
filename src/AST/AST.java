package AST;

import Entity.*;
import ExceptionS.SemanticError;
import FrontEnd.*;

import javax.sound.midi.ShortMessage;
import java.util.List;
import java.util.Set;

public class AST
{

	private List<ClassEntity> classEntityList;
	private List<FunctionEntity> functionEntityList;
	private List<VariableEntity> variableEntityList;
	private List<DefinitionNode> definitionNodeList;
	private Set<String> classNameSet;
	private Scope scope;

	public AST(List<ClassEntity> classEntities, List<FunctionEntity> functionEntities, List<VariableEntity> variableEntities, List<DefinitionNode> definitionNodes, Set<String> classNameSet)
	{
		this.classEntityList = classEntities;
		this.functionEntityList = functionEntities;
		this.variableEntityList = variableEntities;
		this.definitionNodeList = definitionNodes;
		this.classNameSet = classNameSet;
		this.scope = new Scope(true);
	}

	public void load_library(List<Entity> entities)
	{
		for (Entity entity : entities)
			scope.insert(entity);
	}

	public void checkSymbolTable()
	{
		for (ClassEntity entity : classEntityList)
			scope.insert(entity);
		for (VariableEntity entity : variableEntityList)
		{
			if (scope.lookup(entity.getName()) != null)
				throw new SemanticError(entity.getLocation(), "duplicate variable and class name " + entity.getName());
		}
		for (FunctionEntity entity : functionEntityList)
			scope.insert(entity);
		SymbolTableChecker builder = new SymbolTableChecker(scope, classNameSet);
		builder.visitDefinitions(definitionNodeList);
	}

	public void checkType()
	{
		TypeCheck check = new TypeCheck(scope);
		check.visitDefinitions(definitionNodeList);
		FunctionEntity mainFunc = (FunctionEntity) scope.lookup("main");
		if (mainFunc == null)
			throw new SemanticError(new Location(0, 0), "main function is not found");
		if (!mainFunc.getReturn_type().is_int())
			throw new SemanticError(new Location(0, 0), "main function must be an integer");
		if (mainFunc.getParameterEntityList().size() > 0)
			throw new SemanticError(new Location(0, 0), "main function should not have parameters.");
	}

	public List<ClassEntity> getClassEntityList()
	{
		return classEntityList;
	}

	public List<FunctionEntity> getFunctionEntityList()
	{
		return functionEntityList;
	}

	public List<VariableEntity> getVariableEntityList()
	{
		return variableEntityList;
	}

	public List<DefinitionNode> getDefinitionNodeList()
	{
		return definitionNodeList;
	}

	public Scope getScope()
	{
		return scope;
	}
}