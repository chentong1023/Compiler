package IR;

import AST.AssignNode;
import IR.Addr;
import IR.Binary;
import Operand.Operand;

public interface IRVisitor
{
	Operand visit(Addr node);
	Operand visit(Assign node);
	Operand visit(Binary node);
	Operand visit(Call node);
	Operand visit(CJump node);
	Operand visit(IntConst node);
	Operand visit(Jump node);
	Operand visit(Label node);
	Operand visit(Mem node);
	Operand visit(Return node);
	Operand visit(StrConst node);
	Operand visit(Unary node);
	Operand visit(Var node);
}
