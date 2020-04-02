package AST;

import ExceptionS.InternalErrorS;
import FrontEnd.ASTVisitor;
import Type.Type;

public class BinaryOpNode extends ExprNode
{
	public enum BinaryOp
	{ADD, SUB, MUL, DIV, MOD, LSHIFT, RSHIFT, LT, GT, LE, GE, EQ, NE, BIT_AND, BIT_OR, BIT_XOR, LOGIC_AND, LOGIC_OR}

	protected BinaryOp operator;
	protected ExprNode left_son, right_son;
	protected Type type;

	public BinaryOpNode(ExprNode left_son, BinaryOp op, ExprNode right_son)
	{
		super("Binary operator", left_son.getLocation());
		this.operator = op;
		this.left_son = left_son;
		this.right_son = right_son;
	}

	public BinaryOpNode(Type type, ExprNode left_son, BinaryOp op, ExprNode right_son)
	{
		super("Binary operator", left_son.getLocation());
		this.operator = op;
		this.left_son = left_son;
		this.right_son = right_son;
		this.type = type;
	}

	public Type getType()
	{
		return type != null ? type : left_son.getType();
	}

	public BinaryOp getOperator()
	{
		return operator;
	}

	public void setOperator(BinaryOp operator)
	{
		this.operator = operator;
	}

	public void setType(Type type)
	{
		//if (this.type != null)
		//	throw new InternalErrorS(getLocation(), "setType@BinaryOp called Twice, " + this.type +" to " + type + "\ndebug info : " + this.left_son.getType() + " " + this.right_son.getType());
		this.type = type;
	}

	public ExprNode getLeft_son()
	{
		return left_son;
	}

	public ExprNode getRight_son()
	{
		return right_son;
	}

	public void setLeft_son(ExprNode left_son)
	{
		this.left_son = left_son;
	}

	public void setRight_son(ExprNode right_son)
	{
		this.right_son = right_son;
	}

	@Override
	public Location getLocation()
	{
		return left_son.getLocation();
	}

	@Override
	public <S, E> E accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}