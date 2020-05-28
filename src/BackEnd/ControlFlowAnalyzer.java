package BackEnd;

import Entity.FunctionEntity;
import INS.InsCJump;
import INS.InsLabel;
import INS.Instruction;
import INS.Jmp;

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

}
