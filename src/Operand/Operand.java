package Operand;

import java.util.Set;

abstract public class Operand
{
	public String to_NASM() {return "operand";}
	abstract public Operand replace(Operand from, Operand to);
	abstract public Set<Reference> get_all_ref();
	public boolean is_register() {return false;}
	public boolean is_direct() {return false;}
	public boolean is_address() {return false;}
	public boolean is_const_int() {return false;}
}
