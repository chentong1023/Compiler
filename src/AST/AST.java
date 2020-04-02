package AST;

import Entity.*;
import FrontEnd.*;

import java.util.List;

public class AST
{

	private List<ClassEntity> classEntityList;
	private List<FunctionEntity> functionEntityList;
	private List<VariableEntity> variableEntityList;
	private List<DefinitionNode> definitionNodeList;
	private Scope scope;

	public AST(List<ClassEntity> classEntities, List<FunctionEntity> functionEntities, List<VariableEntity> variableEntities, List<DefinitionNode> definitionNodes)
	{
		this.classEntityList = classEntities;
		this.functionEntityList = functionEntities;
		this.variableEntityList = variableEntities;
		this.definitionNodeList = definitionNodes;
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
		for (FunctionEntity entity : functionEntityList)
			scope.insert(entity);
		SymbolTableChecker builder = new SymbolTableChecker(scope);
		builder.visitDefinitions(definitionNodeList);
	}

	public void checkType()
	{
		TypeCheck check = new TypeCheck(scope);
		check.visitDefinitions(definitionNodeList);
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