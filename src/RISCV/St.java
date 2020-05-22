package RISCV;

import Operand.Operand;

public class St extends RISCV
{
	@Override
	Operand accept(RISCVVisitor visitor)
	{
		return visitor.visit(this);
	}
}
