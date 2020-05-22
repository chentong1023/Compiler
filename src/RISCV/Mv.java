package RISCV;

import Operand.*;

public class Mv extends RISCV
{
	public Register rs, rd;

	public Mv(Register rs, Register rd)
	{
		this.rs = rs;
		this.rd = rd;
	}

	@Override
	Operand accept(RISCVVisitor visitor)
	{
		return visitor.visit(this);
	}
}
