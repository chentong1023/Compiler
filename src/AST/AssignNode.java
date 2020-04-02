package AST;

import FrontEnd.ASTVisitor;
import Type.Type;

public class AssignNode extends ExprNode
{
	private ExprNode lhs, rhs;

	public AssignNode(ExprNode lhs, ExprNode rhs)
	{
		super("Assign", lhs.getLocation());
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public ExprNode getLhs()
	{
		return lhs;
	}

	public ExprNode getRhs()
	{
		return rhs;
	}

	public void setLhs(ExprNode lhs)
	{
		this.lhs = lhs;
	}

	public void setRhs(ExprNode rhs)
	{
		this.rhs = rhs;
	}

	@Override
	public Type getType()
	{
		return lhs.getType();
	}

	@Override
	public Location getLocation()
	{
		return lhs.getLocation();
	}

	@Override
	public <S, E> E accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}