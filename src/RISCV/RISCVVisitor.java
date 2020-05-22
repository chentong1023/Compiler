package RISCV;

import Operand.Operand;

public interface RISCVVisitor
{
	Operand visit(IType ins);
	Operand visit(RType ins);
	Operand visit(Br ins);
	Operand visit(Cl ins);
	Operand visit(Jp ins);
	Operand visit(La ins);
	Operand visit(Ld ins);
	Operand visit(Li ins);
	Operand visit(Lui ins);
	Operand visit(Mv ins);
	Operand visit(Ret ins);
	Operand visit(St ins);
}
