package RISCV;

import Operand.Operand;

public class IType extends RISCV
{

	@Override
	Operand accept(RISCVVisitor visitor)
	{
		return visitor.visit(this);
	}
}
