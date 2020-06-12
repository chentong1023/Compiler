package BackEnd;

import AST.AST;
import AST.FuncallNode;
import AST.FunctionDefNode;
import Entity.ClassEntity;
import Entity.FunctionEntity;
import FrontEnd.ASTBaseVisitor;

import java.util.LinkedList;

public class Inline_checker extends ASTBaseVisitor
{
	AST ast;
	public Inline_checker(AST ast)
	{
		this.ast = ast;
	}

	FunctionEntity current_function;

	public void check_inline()
	{
		for (FunctionEntity entity : ast.getFunctionEntityList())
		{
			current_function = entity;
			visit(entity.getBody());
		}
		for (ClassEntity entity : ast.getClassEntityList())
		{
			for (FunctionDefNode node : entity.getMember_functions())
			{
				current_function = node.getEntity();
				visit(node.getEntity().getBody());
			}
		}
	}

	@Override
	public Void visit(FuncallNode node)
	{
		current_function.add_call(node.function_type().getEntity());
		visitExpr(node.getExpr());
		visitExprs(node.getArgs());
		return null;
	}
}
