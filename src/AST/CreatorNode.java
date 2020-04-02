package AST;

import FrontEnd.ASTVisitor;
import Type.Type;

import java.util.List;

public class CreatorNode extends ExprNode
{
	private Type type;
	private List<ExprNode> exprs;
	private int total;

	public CreatorNode(Location location, Type type, List<ExprNode> exprs, int total)
	{
		super("Creator", location);
		this.type = type;
		this.exprs = exprs;
		this.total = total;
	}

	@Override
	public Type getType()
	{
		return type;
	}

	public List<ExprNode> getExprs()
	{
		return exprs;
	}

	public void setExprs(List<ExprNode> exprs)
	{
		this.exprs = exprs;
	}

	public int getTotal()
	{
		return total;
	}

	@Override
	public <S, E> E accept(ASTVisitor<S, E> vistor)
	{
		return vistor.visit(this);
	}
}