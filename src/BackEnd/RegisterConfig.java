package BackEnd;

import Operand.Register;

import java.util.ArrayList;
import java.util.List;

public class RegisterConfig
{
	private List<Register> registers = new ArrayList<>();
	private List<Register> para_registers = new ArrayList<>();

	public RegisterConfig()
	{
		registers.add(new Register("zero")); // hard-wired zero(x0)
		registers.add(new Register("ra")); // return address(x1)
		registers.add(new Register("sp")); // stack pointer(x2)
		registers.add(new Register("gp")); // global pointer(never used)(x3)
		registers.add(new Register("tp")); // thread pointer(never used)(x4)
		for (int i = 0; i < 3; ++i) registers.add(new Register("t" + i)); // Temporaries(x5-7)
		for (int i = 0; i < 2; ++i) registers.add(new Register("s" + i)); // saved register(x8-9)
		for (int i = 0; i < 8; ++i) registers.add(new Register("a" + i)); // Function arguments(x10-x17)
		for (int i = 2; i < 12; ++i) registers.add(new Register("s" + i)); // saved register(x18-27)
		for (int i = 3; i < 7; ++i) registers.add(new Register("t" + i)); // Temporaries(x28-31)

		int[] calleeSave = {2, 8, 9, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
		for (int x : calleeSave)
		{
			registers.get(x).setCallee_save(true);
//			System.err.println(registers.get(x));
		}

		for (int i = 10; i < 18; ++i)
		{
			para_registers.add(registers.get(i));
		}
	}

	public List<Register> getRegisters()
	{
		return registers;
	}

	public List<Register> getPara_registers()
	{
		return para_registers;
	}
}
