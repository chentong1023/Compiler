package BackEnd;

import Entity.FunctionEntity;
import Entity.ParameterEntity;
import INS.InsCJump;
import INS.InsLabel;
import INS.Instruction;
import INS.Jmp;

import java.io.PrintStream;
import java.util.*;

public class ControlFlowAnalyzer
{
	private List<FunctionEntity> functionEntityList;
	private int block_cnter;

	public ControlFlowAnalyzer(InstructionEmitter emitter)
	{
		functionEntityList = emitter.getFunctionEntities();
	}

	public void build_cf()
	{
		for (FunctionEntity functionEntity : functionEntityList)
		{
			if (functionEntity.is_inlined())
				continue;
			build_basic_block(functionEntity);
			build_cfg(functionEntity);
			Optimaize(functionEntity);
			layoutBasicBlock(functionEntity);
		}
	}

	private void build_basic_block(FunctionEntity entity)
	{
		List<BasicBlock> basicBlocks = new LinkedList<>();
		BasicBlock newBlock = null;
		for (Instruction ins : entity.getIns())
		{
			if (newBlock == null && !(ins instanceof InsLabel))
			{
				InsLabel label = new InsLabel("cfg_added_" + block_cnter++);
				newBlock = new BasicBlock(label);
				newBlock.getIns().add(label);
			}
			if (ins instanceof InsLabel)
			{
				if (newBlock != null)
				{
					newBlock.getJump_to().add(((InsLabel) ins));
					newBlock.getIns().add(new Jmp(((InsLabel) ins)));
					basicBlocks.add(newBlock);
				}
				newBlock = new BasicBlock(((InsLabel) ins));
				newBlock.getIns().add(ins);
			}
			else
			{
				newBlock.getIns().add(ins);
				if (ins instanceof Jmp)
				{
					newBlock.getJump_to().add(((Jmp) ins).getDest());
					basicBlocks.add(newBlock);
					newBlock = null;
				}
				else if (ins instanceof InsCJump)
				{
					newBlock.getJump_to().add(((InsCJump) ins).getTrue_label());
					newBlock.getJump_to().add(((InsCJump) ins).getFalse_label());
					basicBlocks.add(newBlock);
					newBlock = null;
				}
			}
		}
		if (newBlock != null)
			basicBlocks.add(newBlock);
		for (BasicBlock basicBlock : basicBlocks)
		{
			for (InsLabel label : basicBlock.getJump_to())
			{
				basicBlock.getSuccessor().add(label.getBasicBlock());
				label.getBasicBlock().getPredecessor().add(basicBlock);
			}
		}

		entity.setBasicBlocks(basicBlocks);
		entity.setIns(null);
	}

	private void build_cfg(FunctionEntity entity)
	{
		for (BasicBlock basicBlock : entity.getBasicBlocks())
		{
			List<Instruction> ins = basicBlock.getIns();
			Iterator<Instruction> iter = ins.iterator();
			if (iter.hasNext())
			{
				Instruction pre = iter.next();
				while (iter.hasNext())
				{
					Instruction now = iter.next();
					pre.getSuccessor().add(now);
					now.getPredecessor().add(pre);
					pre = now;
				}
			}
			Instruction first = ins.get(0);
			for (BasicBlock pre : basicBlock.getPredecessor())
				pre.getIns().get(pre.getIns().size()-1).getSuccessor().add(first);
			Instruction last = ins.get(ins.size() - 1);
			for (BasicBlock suc : basicBlock.getSuccessor())
				suc.getIns().get(0).getPredecessor().add(last);
		}
	}

	private void layoutBasicBlock(FunctionEntity entity)
	{
		List<BasicBlock> basicBlocks = entity.getBasicBlocks();
		Queue<BasicBlock> queue = new ArrayDeque<>();

		queue.addAll(basicBlocks);
		List<BasicBlock> newBBs = new LinkedList<>();
		while (!queue.isEmpty())
		{
			BasicBlock top = queue.remove();
			while (top != null && !top.isLayouted())
			{
				BasicBlock next = null;
				top.setLayouted(true);
				for (BasicBlock suc : top.getSuccessor())
				{
					if (!suc.isLayouted())
					{
						Instruction last = top.getIns().get(top.getIns().size() - 1);
						if (last instanceof Jmp)
							top.getIns().remove(top.getIns().size()-1);
						else if (last instanceof InsCJump)
							((InsCJump) last).setFall_through(suc.getLabel());
						next = suc;
						break;
					}
				}
				newBBs.add(top);
				top = next;
			}
		}
		entity.setBasicBlocks(newBBs);
		entity.setIns(null);
	}

	private void Optimaize(FunctionEntity entity)
	{
		boolean modified = true;
		while (modified)
		{
			modified = false;
			BasicBlock now;
			for (BasicBlock basicBlock : entity.getBasicBlocks())
			{
				if (basicBlock.getSuccessor().size() == 1 && basicBlock.getSuccessor().get(0).getPredecessor().size() == 1)
				{
					now = basicBlock;
					BasicBlock next = now.getSuccessor().get(0);
					if (next.getSuccessor().size() != 0)
					{
						modified = true;
						for (BasicBlock next_next : next.getSuccessor())
						{
							next_next.getPredecessor().remove(next);
							next_next.getPredecessor().add(now);
							now.getSuccessor().add(next_next);
						}
						next.getIns().remove(0);
						now.getIns().remove(now.getIns().size() - 1);
						now.getIns().addAll(next.getIns());
						entity.getBasicBlocks().remove(next);
						now.getSuccessor().remove(next);
						break;
					}
				}
			}
			List<BasicBlock> useless_basic_block = new LinkedList<>();
			for (BasicBlock to_remove : entity.getBasicBlocks())
			{
				if (to_remove.getIns().size() < 2)
					continue;
				Instruction last = to_remove.getIns().get(1);
				if (to_remove.getIns().size() == 2 && last instanceof Jmp)
				{
					List<BasicBlock> backup = new LinkedList<>(to_remove.getPredecessor());
					for (BasicBlock pre : backup)
					{
						Instruction jump = pre.getIns().get(pre.getIns().size() - 1);
						if (jump instanceof Jmp)
						{
							modified = true;
							((Jmp) jump).setDest(((Jmp) last).getDest());
						}
						else if (jump instanceof InsCJump)
						{
							modified = true;
							if (((InsCJump) jump).getTrue_label() == to_remove.getLabel())
								((InsCJump) jump).setTrue_label(((Jmp) last).getDest());
							if (((InsCJump) jump).getFalse_label() == to_remove.getLabel())
								((InsCJump) jump).setFalse_label(((Jmp) last).getDest());
						}
						pre.getSuccessor().remove(to_remove);
						useless_basic_block.add(to_remove);
						if (to_remove.getSuccessor().size() == 1)
						{
							BasicBlock suc = to_remove.getSuccessor().get(0);
							pre.getSuccessor().add(suc);
							suc.getPredecessor().add(pre);
						}
					}
					if (modified)
						break;
				}
			}
			for (BasicBlock basicBlock : entity.getBasicBlocks())
			{
				if (basicBlock.getPredecessor().size() == 0 && basicBlock.getLabel() != entity.getBegin_label_INS())
				{
					modified = true;
					useless_basic_block.add(basicBlock);
				}
			}
			entity.getBasicBlocks().removeAll(useless_basic_block);
		}
	}

	public void print_self(PrintStream out)
	{
		for (FunctionEntity functionEntity : functionEntityList)
		{
			out.println("=======" + functionEntity.getAsm_name() + "=======");
			for (BasicBlock basicBlock : functionEntity.getBasicBlocks())
			{
				out.println("------- b -------" + "jump to:");
				for (InsLabel label : basicBlock.getJump_to())
					out.print("   " + label.getName());
				out.println();
				for (Instruction in : basicBlock.getIns())
					out.println(in.toString());
			}
		}
	}
}
