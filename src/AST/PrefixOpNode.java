package AST;

import FrontEnd.ASTVisitor;

public class PrefixOpNode extends UnaryOpNode
{
	public PrefixOpNode(UnaryOp op, ExprNode exprNode)
	{
		super(op, exprNode);
	}

	@Override
	public <S, E> E accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}