package AST;

import FrontEnd.ASTVisitor;

public class LogicalAndNode extends BinaryOpNode
{
	public LogicalAndNode(ExprNode left_son, ExprNode right_son)
	{
		super(left_son, BinaryOp.LOGIC_AND, right_son);
	}

	@Override
	public <S, E> E accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}
