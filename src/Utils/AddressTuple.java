package Utils;

import IR.Expr;

public class AddressTuple
{
	public Expr base, indx;
	public int mul, add;

	public AddressTuple(Expr base, Expr indx, int mul, int add)
	{
		this.base = base;
		this.indx = indx;
		this.mul = mul;
		this.add = add;
	}
}
