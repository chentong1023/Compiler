package RISCV;

import Operand.Operand;

public class Li extends RISCV
{
	@Override
	Operand accept(RISCVVisitor visitor)
	{
		return visitor.visit(this);
	}
}
