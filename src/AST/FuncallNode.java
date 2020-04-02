package AST;

import FrontEnd.ASTVisitor;
import Type.*;

import java.util.List;

public class FuncallNode extends ExprNode
{
	private ExprNode expr;
	private List<ExprNode> args;

	public FuncallNode(ExprNode expr, List<ExprNode> args)
	{
		super("Funcall", expr.getLocation());
		this.expr = expr;
		this.args = args;
	}

	public ExprNode getExpr()
	{
		return expr;
	}

	public List<ExprNode> getArgs()
	{
		return args;
	}

	public void add(ExprNode expr)
	{
		args.add(0, expr);
	}

	public void add_this_pointer(ExprNode expr)
	{
		args.add(0, expr);
	}

	public FunctionType function_type()
	{
		return (FunctionType) expr.getType();
	}

	@Override
	public Type getType()
	{
		return function_type().getEntity().getReturn_type();
	}

	@Override
	public Location getLocation()
	{
		return expr.getLocation();
	}

	@Override
	public <S, E> E accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}