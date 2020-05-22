package RISCV;

import Operand.Operand;

abstract public class RISCV
{
	abstract Operand accept(RISCVVisitor visitor);
}
