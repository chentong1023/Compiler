package IR;

import Operand.Operand;

public class Binary extends Expr
{

	public enum BinaryOp {ADD, SUB, MUL, DIV, MOD, LSHIFT, RSHIFT, LT, GT, LE, GE, EQ, NE, BIT_AND, BIT_XOR, BIT_OR, LOGIC_AND, LOGIC_OR}
	private Expr left, right;
	private BinaryOp operator;

	public Binary(Expr left, Expr right, BinaryOp operator)
	{
		this.left = left;
		this.right = right;
		this.operator = operator;
	}

	public Expr getLeft()
	{
		return left;
	}

	public Expr getRight()
	{
		return right;
	}

	public BinaryOp getOperator()
	{
		return operator;
	}

	@Override
	public String toString()
	{
		return "(" + getLeft() + " " + operator + " " + getRight() + ")";
	}


	@Override
	public Operand accept(IRVisitor emitter)
	{
		return emitter.visit(this);
	}
}
