package IR;

import Operand.Operand;

abstract public class IR
{
	abstract public Operand accept(IRVisitor emitter);
}
