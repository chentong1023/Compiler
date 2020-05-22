package RISCV;

import Operand.Operand;

public class Br extends RISCV
{
	@Override
	Operand accept(RISCVVisitor visitor)
	{
		return visitor.visit(this);
	}
}
