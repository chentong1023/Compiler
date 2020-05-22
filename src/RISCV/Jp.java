package RISCV;

import Operand.Operand;

public class Jp extends RISCV
{
	@Override
	Operand accept(RISCVVisitor visitor)
	{
		return visitor.visit(this);
	}
}
