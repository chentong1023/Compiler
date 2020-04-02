package FrontEnd;

import AST.*;
import Entity.*;
import ExceptionS.SemanticError;
import Type.*;

import java.util.Set;
import java.util.Stack;

public class SymbolTableChecker extends ASTBaseVisitor
{
	private Stack<Scope> stack = new Stack<>();
	private Scope topScope;
	private Scope currentScope;
	private ClassEntity currentClass = null;
	private ParameterEntity currentThis = null;
	private Set<String> class_sets;

	public SymbolTableChecker(Scope scope)
	{
		this.topScope = scope;
		this.currentScope = scope;
		stack.push(currentScope);
	}

	public SymbolTableChecker(Scope topScope, Set<String> class_sets)
	{
		this.topScope = topScope;
		this.class_sets = class_sets;
		this.currentScope = topScope;
		stack.push(currentScope);
	}

	private void enterScope()
	{
		currentScope = new Scope(currentScope);
		stack.push(currentScope);
	}

	private void exitScope()
	{
		stack.pop();
		currentScope = stack.peek();
	}

	private void enterClass(ClassEntity entity)
	{
		currentClass = entity;
		enterScope();
		entity.setScope(currentScope);
	}

	private void exitClass()
	{
		exitScope();
		currentClass = null;
	}

	private boolean checkType(Type type)
	{
		if (type instanceof ClassType)
		{
			ClassType t = (ClassType) type;
			Entity entity = currentScope.lookup(t.getName());
			if (!(entity instanceof ClassEntity))
				return false;
			t.setEntity((ClassEntity) entity);
		} else if (type instanceof FunctionType)
		{
			FunctionType t = (FunctionType) type;
			Entity entity = currentScope.lookup(t.getName());
			if (!(entity instanceof FunctionEntity))
				return false;
			t.setEntity((FunctionEntity) entity);
		} else if (type instanceof ArrayType)
		{
			ArrayType t = (ArrayType) type;
			return checkType(((ArrayType) type).dfs_type());
		}
		return true;
	}

	@Override
	public Void visit(ClassDefNode node)
	{
		ClassEntity entity = node.getEntity();
		enterClass(entity);
		for (VariableDefNode variableDefNode : entity.getMember_variables())
			currentScope.insert(new MemberEntity(variableDefNode.getEntity()));
		for (FunctionDefNode functionDefNode : entity.getMember_functions())
			currentScope.insert(functionDefNode.getEntity());

		visitStmts(entity.getMember_variables());
		visitStmts(entity.getMember_functions());
		exitClass();
		return null;
	}

	@Override
	public Void visit(FunctionDefNode node)
	{
		FunctionEntity entity = node.getEntity();
		enterScope();
		entity.setScope(currentScope);
		if (!checkType(entity.getReturn_type()))
			throw new SemanticError(node.getLocation(), "Return type is invalid" + entity.getReturn_type());

		if (currentClass != null)
			currentThis = entity.add_this_parameter(node.getLocation(), currentClass);
		for (ParameterEntity parameterEntity : entity.getParameterEntityList())
		{
			currentScope.insert(parameterEntity);
			if (!checkType(parameterEntity.getType()))
				throw new SemanticError(node.getLocation(), "Parameter type is invalid" + parameterEntity.getType());
		}
		visit(entity.getBody());
		exitScope();
		return null;
	}

	@Override
	public Void visit(VariableDefNode node)
	{
		VariableEntity entity = node.getEntity();
		if (class_sets.contains(node.getName()))
			throw new SemanticError(node.getLocation(), "duplicate variable and class name " + node.getName());
		if (!checkType(entity.getType()))
			throw new SemanticError(node.getLocation(), "Variable type is invalid" + entity.getType());
		if (currentClass == null || currentClass.getScope() != currentScope)
		{
			if (entity.getInitializer() != null)
				visitExpr(entity.getInitializer());
			currentScope.insert(entity);
		}

		return null;
	}

	@Override
	public Void visit(CreatorNode node)
	{
		if (!checkType(node.getType()))
			throw new SemanticError(node.getLocation(), "Creator type is invalid :" + node.getType());
		if (node.getExprs() != null)
			visitExprs(node.getExprs());
		return null;
	}

	@Override
	public Void visit(BlockNode node)
	{
		enterScope();
		node.setScope(currentScope);
		visitStmts(node.getStmts());
		exitScope();
		return null;
	}

	@Override
	public Void visit(VariableNode node)
	{
		Entity entity = currentScope.lookup(node.getName());
		if (entity == null)
			throw new SemanticError(node.getLocation(), "No definition of " + node.getName());
		node.setEntity(entity);

		if (currentClass != null && currentClass.getScope().lookup_current_level(node.getName()) != null)
			node.setThis_pointer(currentThis);
		return null;
	}

	@Override
	public Void visit(StringLiteralNode node)
	{
		Entity entity = topScope.lookup_current_level(StringType.STRING_CONSTANT_PREFIX + node.getValue());
		if (entity == null)
		{
			entity = new StringConstantEntity(node.getLocation(), new StringType(), node.getValue(), node);
			topScope.insert(entity);
		}
		node.setEntity((StringConstantEntity) entity);
		return null;
	}
}