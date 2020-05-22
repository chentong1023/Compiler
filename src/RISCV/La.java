package RISCV;

import Operand.Operand;

public class La extends RISCV
{
	@Override
	Operand accept(RISCVVisitor visitor)
	{
		return visitor.visit(this);
	}
}
