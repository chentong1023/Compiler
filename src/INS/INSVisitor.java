package INS;

public interface INSVisitor
{
	void visit(Add ins);
	void visit(And ins);
	void visit(Cmp ins);
	void visit(Comment ins);
	void visit(Div ins);
	void visit(InsCall ins);
	void visit(InsLabel ins);
	void visit(InsCJump ins);
	void visit(InsReturn ins);
	void visit(Jmp ins);
	void visit(Lea ins);
	void visit(Mod ins);
	void visit(Move ins);
	void visit(Mul ins);
	void visit(Sal ins);
	void visit(Xor ins);
	void visit(Sar ins);
	void visit(Sub ins);
	void visit(Or ins);
	void visit(Neg ins);
	void visit(Not ins);
	void visit(Push ins);
}
