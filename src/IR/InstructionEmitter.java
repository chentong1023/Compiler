package IR;

import AST.FunctionDefNode;
import Entity.*;
import INS.Move;
import RISCV.*;
import Operand.*;
import Utils.AddressTuple;
import Utils.Triple;

import java.util.*;

public class InstructionEmitter implements IRVisitor
{
	private List<IR> global_initializer;
	private Scope global_scope;
	private List<FunctionEntity> functionEntities;

	private List<RISCV> ins;
	private FunctionEntity currentFunction;
	private boolean is_inleaf;

	public InstructionEmitter(IRBuilder irBuilder)
	{
		this.global_scope = irBuilder.global_scope();
		this.functionEntities = new LinkedList<>(irBuilder.functionEntities());
		this.global_initializer = irBuilder.getGlobal_initializer();
		for (ClassEntity entity : irBuilder.getAst().getClassEntityList())
		{
			for (FunctionDefNode functionDefNode : entity.getMember_functions())
				this.functionEntities.add(functionDefNode.getEntity());
		}
	}

	private int expr_depth = 0;
	private Map<Entity, Entity> global_local_map = new HashMap<>();
	private Set<Entity> used_global;

	private Entity trans_entity(Entity entity)
	{
		if (is_inleaf)
		{
			Entity ret = global_local_map.get(entity);
			if (ret != null)
			{
				used_global.add(entity);
				return ret;
			}
		}
		return entity;
	}

	private boolean check_num(Expr expr)
	{
		if (expr instanceof IntConst)
		{
			int x = ((IntConst) expr).getValue();
			return x == 1 || x == 2 || x == 4 || x == 8;
		}
		return false;
	}

	private Triple<Expr, Expr, Integer> match_base_indx_mul(Expr expr)
	{
		if (!(expr instanceof Binary))
			return null;
		Binary binary = ((Binary) expr);
		Expr base = null, indx = null;
		int mul = 0;
		boolean matched = false;
		if (binary.getOperator() == Binary.BinaryOp.ADD)
		{
			if (binary.getRight() instanceof Binary && ((Binary) binary.getRight()).getOperator() == Binary.BinaryOp.MUL)
			{
				base = binary.getLeft();
				Binary right = ((Binary) binary.getRight());
				if (check_num(right.getRight()))
				{
					indx = right.getLeft();
					mul = ((IntConst) right.getRight()).getValue();
					matched = true;
				}
				else if (check_num(right.getLeft()))
				{
					indx = right.getRight();
					mul = ((IntConst) right.getLeft()).getValue();
					matched = true;
				}
			}
			else if (binary.getLeft() instanceof Binary && ((Binary) binary.getLeft()).getOperator() == Binary.BinaryOp.MUL)
			{
				base = binary.getRight();
				Binary left = ((Binary) binary.getLeft());
				if (check_num(left.getRight()))
				{
					indx = left.getRight();
					mul = ((IntConst) left.getLeft()).getValue();
					matched = true;
				}
			}
		}
		if (matched)
			return new Triple<>(base, indx, mul);
		else
			return null;
	}

	private AddressTuple match_address(Expr expr)
	{
		if (!(expr instanceof Binary))
			return null;
		Binary bin = ((Binary) expr);
		Expr base = null, indx = null;
		int mul = 1, add = 0;
		boolean matched = false;
		Triple<Expr, Expr, Integer> base_indx_mul = null;
		if (bin.getOperator() == Binary.BinaryOp.ADD)
		{
			if (bin.getRight() instanceof IntConst)
			{
				add = ((IntConst) bin.getRight()).getValue();
				if ((base_indx_mul = match_base_indx_mul(bin.getLeft())) != null)
					matched = true;
				else
					base = bin.getLeft();
			}
			else if (bin.getLeft() instanceof IntConst)
			{
				add = ((IntConst) bin.getLeft()).getValue();
				if ((base_indx_mul = match_base_indx_mul(bin.getRight())) != null)
					matched = true;
				else
					base = bin.getRight();
			}
			else if ((base_indx_mul = match_base_indx_mul(bin)) != null)
				matched = true;
		}

		if (base_indx_mul != null)
		{
			base = base_indx_mul.first;
			indx = base_indx_mul.second;
			mul = base_indx_mul.third;
		}
		if (matched)
		{
			if (base != null && match_address(base) != null)
				return null;
			if (base != null && match_address(indx) != null)
				return null;
			return new AddressTuple(base, indx, mul, add);
		}
		return null;
	}

	private Operand eliminate_address(Operand operand)
	{
		if (operand instanceof Address || (operand instanceof Reference && ((Reference) operand).getType() == Reference.Type.GLOBAL))
		{
			Reference tmp = get_tmp();
			ins.add(new Move(tmp, operand));
			return tmp;
		}
	}

	public Operand visit_expr(Expr ir)
	{
		boolean matched = false;
		Operand ret = null;
		expr_depth++;
		AddressTuple addressTuple;
		if ((addressTuple = match_address(ir)) != null)
		{
			Operand base = visit_expr(addressTuple.base);
			Operand indx = null;
			if (addressTuple.indx != null)
			{
				indx = visit_expr(addressTuple.indx);
				indx = eliminate_address(indx);
			}
		}
	}

	@Override
	public Operand visit(Addr node)
	{
		return null;
	}

	@Override
	public Operand visit(Assign node)
	{
		Operand dest = null;
		if (node.getLeft() instanceof Addr)
			dest = trans_entity(((Addr) node.getLeft()).getEntity()).getReference();
		else
		{
			Operand lhs = visit_expr(node.getLeft());
		}
		return null;
	}

	@Override
	public Operand visit(Binary node)
	{
		return null;
	}

	@Override
	public Operand visit(Call node)
	{
		return null;
	}

	@Override
	public Operand visit(CJump node)
	{
		return null;
	}

	@Override
	public Operand visit(IntConst node)
	{
		return null;
	}

	@Override
	public Operand visit(Jump node)
	{
		return null;
	}

	@Override
	public Operand visit(Label node)
	{
		return null;
	}

	@Override
	public Operand visit(Mem node)
	{
		return null;
	}

	@Override
	public Operand visit(Return node)
	{
		return null;
	}

	@Override
	public Operand visit(StrConst node)
	{
		return null;
	}

	@Override
	public Operand visit(Unary node)
	{
		return null;
	}

	@Override
	public Operand visit(Var node)
	{
		return null;
	}
}
