package BackEnd;

import AST.*;
import Entity.*;
import ExceptionS.InternalErrorS;
import FrontEnd.ASTVisitor;
import IR.*;
import IR.Binary.BinaryOp;
import Type.*;
import Utils.Pair;

import java.util.*;

import static Compiler.Defines.*;
import static IR.Binary.BinaryOp.ADD;
import static IR.Binary.BinaryOp.SUB;
import static Type.StringType.*;

public class IRBuilder implements ASTVisitor<Void, Expr>
{
	public List<IR> stmts = new LinkedList<>();
	private AST ast;
	private Stack<Scope> scopeStack = new Stack<>();
	private Scope current_scope;
	private FunctionEntity current_function;
	private List<IR> global_initializer;
	private int expr_depth = 0;
	private int max_depth = 0;

	public List<IR> getGlobal_initializer()
	{
		return global_initializer;
	}
	public Scope global_scope() { return ast.getScope(); }
	public List<FunctionEntity> functionEntities() { return ast.getFunctionEntityList(); }

	public AST getAst()
	{
		return ast;
	}

	static private final IntConst const_pointer_size = new IntConst(4);
	static private final IntConst const_length_size = new IntConst(4);
	static private final IntConst const_1 = new IntConst(1);
	static private final IntConst const_0 = new IntConst(0);

	private final FunctionEntity malloc_function, printint_function, printlnint_function, print_function, println_function;

	public IRBuilder(AST ast)
	{
		this.ast = ast;
		malloc_function = (FunctionEntity) ast.getScope().lookup_current_level("malloc");
		printint_function = (FunctionEntity) ast.getScope().lookup_current_level( "printInt");
		print_function = (FunctionEntity) ast.getScope().lookup_current_level("print");
		println_function = (FunctionEntity) ast.getScope().lookup_current_level("println");
		printlnint_function = (FunctionEntity) ast.getScope().lookup_current_level("printlnInt");
		scopeStack.push(ast.getScope());
	}

	private List<Var> tmp_stack = new LinkedList<>();
	private int tmp_top = 0, new_int_temp_counter = 0;

	public void generate_IR()
	{
		for (ClassEntity entity : ast.getClassEntityList())
		{
			entity.init_offset(CLASS_MEMBER_ALIGNMENT_SIZE);
		}
		if (Enable_Function_Inline)
		{
			for (FunctionEntity functionEntity : ast.getFunctionEntityList())
				functionEntity.check_inlinable();
		}
		for (FunctionEntity entity : ast.getFunctionEntityList())
		{
			tmp_stack = new LinkedList<>();
			tmp_top = 0;
			current_function = entity;
			if (!entity.getName().equals("main"))
				entity.setAsm_name(entity.getName() + "_func__");
			compile_function(entity);
		}
		for (ClassEntity entity : ast.getClassEntityList())
		{
			for (FunctionDefNode node : entity.getMember_functions())
			{
				tmp_stack = new LinkedList<>();
				tmp_top = 0;
				current_function = node.getEntity();
				FunctionEntity function = node.getEntity();
				function.setAsm_name(entity.getName() + "_" + function.getName() + "_func__");
				compile_function(node.getEntity());
			}
		}
	}
	private int label_counter = 0;
	private void add_label(Label label, String name)
	{
		label.setName(name + "_" + label_counter++);
		stmts.add(label);
	}
	private void compile_function(FunctionEntity entity)
	{
		if (entity.is_inlined())
		{
			System.err.println("Optmize Message : " + entity.getAsm_name() + " is inlined");
			return ;
		}
		Label begin = new Label();
		Label end = new Label();

		entity.set_label_IR(begin, end);
		add_label(begin, entity.getName() + "_begin");

		if (entity.getName().equals("main"))
		{
			for (DefinitionNode node : ast.getDefinitionNodeList())
			{
				if (node instanceof VariableDefNode)
					visit((VariableDefNode) node);
			}
		}
		visit(entity.getBody());
		if (!(stmts.get(stmts.size() - 1) instanceof Jump))
			stmts.add(new Jump(end));
		add_label(end, entity.getName() + "_end");
		entity.setIrs(stmts);
		if (Output_Tree_IR)
		{
			System.err.println("===" + entity.getName() + "===");
			for (IR ir : entity.getIrs())
			{
				System.err.println(ir);
			}
		}
		stmts = new LinkedList<>();
	}

	private void add_cjump(ExprNode condition, Label true_label, Label false_label)
	{
		if (condition instanceof BinaryOpNode)
		{
			BinaryOpNode node = (BinaryOpNode) condition;
			Label go_on = new Label();
			switch (node.getOperator())
			{
				case LOGIC_AND:
					add_cjump(node.getLeft_son(), go_on, false_label);
					add_label(go_on, "go_on");
					add_cjump(node.getRight_son(), true_label, false_label);
					break;
				case LOGIC_OR:
					add_cjump(node.getLeft_son(), true_label, go_on);
					add_label(go_on, "go_on");
					add_cjump(node.getRight_son(), true_label, false_label);
					break;
				default:
					refresh_tmp_stack();
					expr_depth++;
					stmts.add(new CJump(visit_expr(condition), true_label, false_label));
					expr_depth--;
			}
		}
		else if (condition instanceof UnaryOpNode && ((UnaryOpNode) condition).getOperator() == UnaryOpNode.UnaryOp.LOGIC_NOT)
			add_cjump(((UnaryOpNode) condition).getExpr(), false_label, true_label);
		else if (condition instanceof BoolLiteralNode)
		{
			if (((BoolLiteralNode) condition).getValue())
				stmts.add(new Jump(true_label));
			else
				stmts.add(new Jump(false_label));
		}
		else
		{
			refresh_tmp_stack();
			expr_depth++;
			stmts.add(new CJump(visit_expr(condition), true_label, false_label));
			expr_depth--;
		}
	}

	public Expr visit_expr(ExprNode node)
	{
		refresh_tmp_stack();
		if (node == null)
			return null;
		expr_depth++;
		if (max_depth < expr_depth)
			max_depth = expr_depth;
		Expr expr = node.accept(this);
		expr_depth--;
		return expr;
	}

	public void visit_stmt(StmtNode node)
	{
		node.accept(this);
	}

	private void refresh_tmp_stack()
	{
		if (expr_depth == 0)
		{
			tmp_top = 0;
			max_depth = 0;
		}
	}

	private int inline_mode = 0;
	private Stack<Map<Entity, Entity>> inline_map = new Stack<>();
	private int inline_cnt = 0;
	private Var inline_no_use = new Var(new VariableEntity(null, null, null, null));
	private Stack<Label> inline_return_label = new Stack<>();
	private Stack<Var> inline_return_var = new Stack<>();
	private void inline_function(FunctionEntity entity, Var return_var, List<Expr> args)
	{
		Label return_label = new Label();
		Map<Entity, Entity> map = new HashMap<>();
		inline_map.push(map);
		inline_return_label.push(return_label);
		inline_return_var.push(return_var);
		Scope scope = new Scope(current_scope);
		Iterator<Expr> iterator = args.iterator();
		for (ParameterEntity parameterEntity : entity.getParameterEntityList())
		{
			VariableEntity variableEntity = new VariableEntity(parameterEntity.getName() + "_inline_" + inline_cnt++, parameterEntity.getLocation(), parameterEntity.getType(), null);
			scope.insert(variableEntity);
			map.put(parameterEntity, variableEntity);
			add_assign(new Var(variableEntity), iterator.next());
		}
		current_scope = scope;
		scopeStack.push(current_scope);

		inline_mode++;
		visit(entity.getBody());
		add_label(return_label, "inline_return_" + entity.getName());
		inline_mode--;
		scopeStack.pop();
		current_scope = scopeStack.peek();
		inline_map.pop();
		inline_return_label.pop();
		inline_return_var.pop();
	}

	@Override
	public Void visit(BlockNode node)
	{
		Scope new_scope = node.getScope();
		if (inline_mode > 0)
		{
			new_scope = new Scope(current_scope);
			Map<Entity, Entity> map = inline_map.peek();
			for (Entity entity : node.getScope().getEntities().values())
			{
				if (entity instanceof VariableEntity)
				{
					VariableEntity buffer = ((VariableEntity) entity).copy();
					new_scope.insert(buffer);
					map.put(entity, buffer);
				}
			}
		}
		current_scope = new_scope;
		scopeStack.push(current_scope);
		for (StmtNode stmt : node.getStmts())
		{
			stmt.accept(this);
		}
		scopeStack.pop();
		current_scope = scopeStack.peek();
		return null;
	}

	@Override
	public Void visit(ExprStmtNode node)
	{
		visit_expr(node.getExpr());
		return null;
	}

	@Override
	public Void visit(IfNode node)
	{
		Label then_label = new Label();
		Label else_label = new Label();
		Label end_label = new Label();
		if (node.getElse_body() == null)
		{
			add_cjump(node.getCond(), then_label, end_label);
			add_label(then_label, "If_then");
			if (node.getThen_body() != null)
				visit_stmt(node.getThen_body());
		}
		else
		{
			add_cjump(node.getCond(), then_label, else_label);
			add_label(then_label, "If_then");
			if (node.getThen_body() != null)
				visit_stmt(node.getThen_body());
			stmts.add(new Jump(end_label));
			add_label(else_label, "If_else");
			visit_stmt(node.getElse_body());
		}
		add_label(end_label, "If_end");
		return null;
	}

	private Stack<Label> cond_label_stack = new Stack<>();
	private Stack<Label> incr_label_stack = new Stack<>();
	private Stack<Label> end_label_stack = new Stack<>();

	@Override
	public Void visit(WhileNode node)
	{
		if (!node.isIs_output_irrelevant())
			visit_loop(null, node.getCond(), null, node.getBody());
		return null;
	}

	@Override
	public Void visit(ForNode node)
	{
		if (!node.isIs_output_irrelevant())
			visit_loop(node.getInit(), node.getCond(), node.getIncr(), node.getBody());
		return null;
	}

	private Map<Integer, Entity> assign_table = new HashMap<>();
	private Set<Entity> in_dependency = new HashSet<>();
	private void clear_assign_table()
	{
		assign_table = new HashMap<>();
		in_dependency = new HashSet<>();
	}

	private void visit_loop(ExprNode init, ExprNode cond, ExprNode incr, StmtNode body)
	{
		clear_assign_table();
		if (init != null)
			visit_expr(init);

		Label cond_label = new Label();
		Label body_label = new Label();
		Label incr_label = new Label();
		Label end_label = new Label();
		add_label(cond_label, "Loop_cond");

		if (cond != null)
			add_cjump(cond, body_label, end_label);
		else
			stmts.add(new Jump(body_label));
		add_label(body_label, "Loop_body");

		cond_label_stack.push(cond_label);
		end_label_stack.push(end_label);
		incr_label_stack.push(incr_label);
		if (body != null)
			visit_stmt(body);
		add_label(incr_label, "Loop_incr");
		if (incr != null)
			visit_expr(incr);

		cond_label_stack.pop();
		incr_label_stack.pop();
		end_label_stack.pop();
		stmts.add(new Jump(cond_label));

		add_label(end_label, "Loop_end");
	}

	private Expr get_address(Expr expr)
	{
		if (expr instanceof Var)
			return new Addr(((Var) expr).getEntity());
		else if (expr instanceof Mem)
			return ((Mem) expr).getExpr();
		else
			throw new InternalErrorS("Get address on an invalid type " + expr);
	}

	private void add_assign(Expr lhs, Expr rhs)
	{
		if (lhs instanceof Var)
		{
			if (in_dependency.contains(((Var) lhs).getEntity()))
				clear_assign_table();
		}
		stmts.add(new Assign(get_address(lhs), rhs));
	}

	@Override
	public Expr visit(BoolLiteralNode node)
	{
		return new IntConst(node.getValue() ? 1 : 0);
	}

	@Override
	public Expr visit(StringLiteralNode node)
	{
		if (ast.getScope().lookup(node.getEntity().getName()) == null)
			ast.getScope().insert(node.getEntity());
		return new StrConst(node.getEntity());
	}

	@Override
	public Expr visit(IntegerLiteralNode node)
	{
		return new IntConst((int) node.getValue());
	}

	@Override
	public Expr visit(VariableNode node)
	{
		if (node.is_member())
		{
			Expr base = new Var(node.getThis_pointer());
			int offset = node.getEntity().getOffset();

			if (offset == 0)
				return new Mem(base);
			else
				return new Mem(new Binary(base, new IntConst(offset), ADD));
		}
		else
		{
			if (inline_mode > 0)
			{
				Entity entity = inline_map.peek().get(node.getEntity());
				return new Var(entity == null ? node.getEntity() : entity);
			}
			else
				return new Var(node.getEntity());
		}
	}

	@Override
	public Expr visit(FuncallNode node)
	{
		clear_assign_table();
		FunctionEntity entity = node.function_type().getEntity();
		if (current_function != null) current_function.add_call(entity);
		if (entity.getName().equals("print"))
		{
			expand_print(node.getArgs().get(0), false, true);
			return null;
		}
		else if (entity.getName().equals("println"))
		{
			expand_print(node.getArgs().get(0), true, true);
			return null;
		}
		List<Expr> args = new LinkedList<>();
		for (ExprNode arg : node.getArgs())
		{
			args.add(visit_expr(arg));
		}
		if (Enable_Function_Inline && entity.is_inlined() || (Enable_Self_Inline && entity == current_function && entity.self_inline(inline_mode)))
		{
			if (expr_depth > 1)
			{
				Var tmp = new_int_tmp();
				inline_function(entity, tmp, args);
				return tmp;
			}
			else
				inline_function(entity, inline_no_use, args);
		}
		else
		{
			if (expr_depth > 1)
			{
				if (not_use_tmp)
					return new Call(entity, args);
				else
				{
					Var tmp = new_int_tmp();
					add_assign(tmp, new Call(entity, args));
					return tmp;
				}
			} else
				stmts.add(new Call(entity, args));
		}
		return null;
	}

	@Override
	public Expr visit(MemberNode node)
	{
		Expr base = visit_expr(node.getExpr());
		int offset = node.getEntity().getOffset();
		if (offset == 0)
			return new Mem(base);
		else
			return new Mem(new Binary(base, new IntConst(offset), ADD));
	}

	@Override
	public Expr visit(ArefNode node)
	{
		Expr base = visit_expr(node.getExpr());
		Expr indx = visit_expr(node.getIndx());
		ArrayType type = (((ArrayType) node.getExpr().getType()));
		int sizeof = type.getBase_type().getSize();
		if (indx instanceof IntConst)
			return new Mem(new Binary(base, new IntConst(sizeof * ((IntConst) indx).getValue()), ADD));
		else
			return new Mem(new Binary(base, new Binary(indx, new IntConst(sizeof), BinaryOp.MUL), ADD));
	}

	@Override
	public Expr visit(SuffixOpNode node)
	{
		return visit(((UnaryOpNode) node));
	}

	@Override
	public Expr visit(PrefixOpNode node)
	{
		return visit(((UnaryOpNode) node));
	}

	private void expand_creator(List<ExprNode> exprs, Expr base, int now, Type type, FunctionEntity constructor)
	{
		Var tmpS = new_int_tmp();
		Var tmpI = new_int_tmp();
		IntConst sizeof = new IntConst(type.getSize());

		add_assign(tmpS, visit_expr(exprs.get(now)));
		add_assign(base, new Call(malloc_function, new LinkedList<Expr>(){{add(new Binary(new Binary(tmpS, sizeof, BinaryOp.MUL), const_length_size, ADD));}}));
		add_assign(new Mem(base), tmpS);
		add_assign(base, new Binary(base, const_length_size, ADD));
		if (exprs.size() > now + 1)
		{
			add_assign(tmpI, const_0);
			Label testLabel = new Label();
			Label beginLabel = new Label();
			Label endLabel = new Label();

			stmts.add(new Jump(testLabel));
			add_label(beginLabel, "creator_loop_begin");
			expand_creator(exprs, new Mem(new Binary(base, new Binary(tmpI, sizeof, BinaryOp.MUL), ADD)), now + 1, ((ArrayType) type).getBase_type(), constructor);
			add_assign(tmpI, new Binary(tmpI, const_1, ADD));

			add_label(testLabel, "creator_loop_test");
			stmts.add(new CJump(new Binary(tmpI, tmpS, BinaryOp.LT), beginLabel, endLabel));
			add_label(endLabel, "creator_loop_end");
		}
		else if (exprs.size() == now + 1 && type instanceof ClassType)
		{
			add_assign(tmpI, const_0);
			Label testLabel = new Label();
			Label beginLabel = new Label();
			Label endLabel = new Label();

			stmts.add(new Jump(testLabel));
			add_label(beginLabel, "creator_loop_begin");

			Var tmp_address = new_int_tmp();
			add_assign(tmp_address, new Binary(base, new Binary(tmpI, sizeof, BinaryOp.MUL), ADD));
			add_assign(new Mem(tmp_address), new Call(malloc_function, new LinkedList<>(){{add(sizeof);}}));
			if (constructor != null)
				stmts.add(new Call(constructor, new LinkedList<Expr>(){{add(new Mem(tmp_address));}}));
			add_assign(tmpI, new Binary(tmpI, const_1, ADD));

			add_label(testLabel, "creator_loop_test");
			stmts.add(new CJump(new Binary(tmpI, tmpS, BinaryOp.LT), beginLabel, endLabel));
			add_label(endLabel, "creator_loop_end");
		}
	}
	@Override
	public Expr visit(CreatorNode node)
	{
		clear_assign_table();

		if (node.getType() instanceof ArrayType)
		{
			Type baseType = ((ArrayType) node.getType()).getBase_type();
			Type deepType = ((ArrayType) node.getType()).dfs_type();
			Var pointer = new_int_tmp();

			FunctionEntity constructor = null;
			if (node.getExprs().size() == node.getTotal() && deepType instanceof ClassType)
				constructor = ((ClassType) deepType).getEntity().getConstructor();
			expand_creator(node.getExprs(), pointer, 0, baseType, constructor);
			return (expr_depth > 1 ? pointer : null);
		}
		else
		{
			ClassEntity entity = ((ClassType) node.getType()).getEntity();
			Var tmp = new_int_tmp();
			add_assign(tmp, new Call(malloc_function, new LinkedList<Expr>(){{add(new IntConst(entity.getSize()));}}));
			if (entity.getConstructor() != null)
				stmts.add(new Call(entity.getConstructor(), new LinkedList<Expr>(){{add(tmp);}}));
			return expr_depth > 1 ? tmp : null;
		}
	}

	@Override
	public Expr visit(UnaryOpNode node)
	{
		switch (node.getOperator())
		{
			case ADD:
				if (node.getExpr() instanceof IntegerLiteralNode)
					return new IntConst((int) ((IntegerLiteralNode) node.getExpr()).getValue());
				else
					return visit_expr(node.getExpr());
			case MINUS:
				if (node.getExpr() instanceof IntegerLiteralNode)
					return new IntConst(-(int) ((IntegerLiteralNode) node.getExpr()).getValue());
				else
					return new Unary(visit_expr(node.getExpr()), Unary.UnaryOp.MINUS);
			case BIT_NOT:
				if (node.getExpr() instanceof IntegerLiteralNode)
					return new IntConst(~(int) ((IntegerLiteralNode) node.getExpr()).getValue());
				else
					return new Unary(visit_expr(node.getExpr()), Unary.UnaryOp.BIT_NOT);
			case LOGIC_NOT:
				if (node.getExpr() instanceof BoolLiteralNode)
					return new IntConst(((BoolLiteralNode) node.getExpr()).getValue() ? 0 : 1);
				else
					return new Unary(visit_expr(node.getExpr()), Unary.UnaryOp.LOGIC_NOT);
			case PRE_DEC:
			case PRE_INC:
			{
				BinaryOp op = node.getOperator() == UnaryOpNode.UnaryOp.PRE_INC ? ADD : SUB;
				Expr expr = visit_expr(node.getExpr());
				add_assign(expr, new Binary(expr, const_1, op));
				return expr_depth > 1 ? expr : null;
			}
			case SUF_DEC:
			case SUF_INC:
			{
				BinaryOp op = node.getOperator() == UnaryOpNode.UnaryOp.SUF_DEC ? SUB : ADD;
				Expr expr = visit_expr(node.getExpr());
				if (expr_depth > 1)
				{
					Var tmp = new_int_tmp();
					add_assign(tmp, expr);
					add_assign(expr, new Binary(expr, const_1, op));
					return tmp;
				}
				else
				{
					add_assign(expr, new Binary(expr, const_1, op));
					return null;
				}
			}
			default:
				throw new InternalErrorS(node.getLocation(), "Invalid operator " + node.getOperator());
		}
	}

	@Override
	public Expr visit(BinaryOpNode node)
	{
		Expr lhs = visit_expr(node.getLeft_son()), rhs = visit_expr(node.getRight_son());
		if (expr_depth <= 1) return null;
		if (lhs instanceof IntConst && rhs instanceof IntConst)
		{
			int lvalue = ((IntConst) lhs).getValue(), rvalue = ((IntConst) rhs).getValue();
			switch (node.getOperator())
			{
				case ADD: return new IntConst(lvalue + rvalue);
				case SUB: return new IntConst(lvalue - rvalue);
				case MUL: return new IntConst(lvalue * rvalue);
				case DIV: return new IntConst(lvalue / rvalue);
				case MOD: return new IntConst(lvalue % rvalue);
				case LSHIFT: return new IntConst(lvalue << rvalue);
				case RSHIFT: return new IntConst(lvalue >> rvalue);
				case EQ: return new IntConst(lvalue == rvalue ? 1 : 0);
				case GE: return new IntConst(lvalue >= rvalue ? 1 : 0);
				case GT: return new IntConst(lvalue > rvalue ? 1 : 0);
				case LE: return new IntConst(lvalue <= rvalue ? 1 : 0);
				case LT: return new IntConst(lvalue < rvalue ? 1 : 0);
				case NE: return new IntConst(lvalue != rvalue ? 1 : 0);
				case BIT_OR: return new IntConst(lvalue | rvalue);
				case BIT_AND:return new IntConst(lvalue & rvalue);
				case BIT_XOR:return new IntConst(lvalue ^ rvalue);
				default:
					throw new InternalErrorS(node.getLocation(), "unsupported operator for integer : " + node.getOperator());
			}
		}
		if (lhs instanceof StrConst && rhs instanceof StrConst)
		{
			StringConstantEntity lvalue = ((StrConst) lhs).getEntity(), rvalue = ((StrConst) rhs).getEntity();
			switch (node.getOperator())
			{
				case ADD:
				{
					String join = lvalue.getValue() + rvalue.getValue();
					StringConstantEntity entity = ((StringConstantEntity) ast.getScope().lookup(STRING_CONSTANT_PREFIX + join));
					if (entity == null)
					{
						entity = new StringConstantEntity(node.getLocation(), Type.stringType, join, null);
						ast.getScope().insert(entity);
					}
					return new StrConst(entity);
				}
				case EQ: return new IntConst(lvalue.getValue().compareTo(rvalue.getValue()) == 0 ? 1 : 0);
				case NE: return new IntConst(lvalue.getValue().compareTo(rvalue.getValue()) != 0 ? 1 : 0);
				case GT: return new IntConst(lvalue.getValue().compareTo(rvalue.getValue()) > 0 ? 1 : 0);
				case GE: return new IntConst(lvalue.getValue().compareTo(rvalue.getValue()) >= 0 ? 1 : 0);
				case LT: return new IntConst(lvalue.getValue().compareTo(rvalue.getValue()) < 0 ? 1 : 0);
				case LE: return new IntConst(lvalue.getValue().compareTo(rvalue.getValue()) <= 0 ? 1 : 0);
				default:
					throw new InternalErrorS(node.getLocation(), "Unsupported operator for String : " + node.getOperator());
			}
		}

		if (node.getLeft_son().getType().is_string())
		{
			switch (node.getOperator())
			{
				case EQ: return new Call(operatorEQ, new LinkedList<>(){{add(lhs); add(rhs); }});
				case NE: return new Call(operatorNE, new LinkedList<>(){{add(lhs); add(rhs); }});
				case GT: return new Call(operatorGT, new LinkedList<>(){{add(lhs); add(rhs); }});
				case LT: return new Call(operatorLT, new LinkedList<>(){{add(lhs); add(rhs); }});
				case LE: return new Call(operatorLE, new LinkedList<>(){{add(lhs); add(rhs); }});
				case GE: return new Call(operatorGE, new LinkedList<>(){{add(lhs); add(rhs); }});
				case ADD: return new Call(operatorADD, new LinkedList<>(){{add(lhs); add(rhs); }});
				default:
					throw new InternalErrorS(node.getLocation(), "Invalid operator" + node.getOperator());
			}
		}
		else
		{
			Binary.BinaryOp op;
			switch (node.getOperator())
			{
				case ADD: op = ADD; break;
				case SUB: op = BinaryOp.SUB; break;
				case EQ: op = BinaryOp.EQ; break;
				case BIT_XOR: op = BinaryOp.BIT_XOR; break;
				case BIT_AND: op = BinaryOp.BIT_AND; break;
				case BIT_OR: op = BinaryOp.BIT_OR; break;
				case NE: op = BinaryOp.NE; break;
				case LT: op = BinaryOp.LT; break;
				case LE: op = BinaryOp.LE; break;
				case GT: op = BinaryOp.GT; break;
				case GE: op = BinaryOp.GE; break;
				case MOD: op = BinaryOp.MOD; break;
				case DIV: op = BinaryOp.DIV; break;
				case MUL: op = BinaryOp.MUL; break;
				case LSHIFT: op = BinaryOp.LSHIFT; break;
				case RSHIFT: op = BinaryOp.RSHIFT; break;
				case LOGIC_OR: op = BinaryOp.LOGIC_OR; break;
				case LOGIC_AND: op = BinaryOp.LOGIC_AND; break;
				default:
					throw new InternalErrorS(node.getLocation(), "unsupported operator for int : " + node.getOperator());
			}
			return new Binary(lhs, rhs, op);
		}
	}

	private Var new_int_tmp()
	{
		if (Enable_Global_Register_Allocation)
		{
			VariableEntity tmp = new VariableEntity("tmp" + new_int_temp_counter++, null, new IntegerType(), null);
			current_function.getScope().insert(tmp);
			return new Var(tmp);
		}
		else
		{
			if (tmp_top >= tmp_stack.size())
			{
				VariableEntity tmp = new VariableEntity("tmp" + tmp_top, null, new IntegerType(), null);
				current_function.getScope().insert(tmp);
				tmp_stack.add(new Var(tmp));
			}
			return tmp_stack.get(tmp_top++);
		}
	}

	@Override
	public Expr visit(LogicalAndNode node)
	{
		Label go_on = new Label();
		Label end = new Label();

		Var tmp = new_int_tmp();
		add_assign(tmp, visit_expr(node.getLeft_son()));
		stmts.add(new CJump(tmp, go_on, end));
		add_label(go_on, "go_on");
		add_assign(tmp, visit_expr(node.getRight_son()));
		add_label(end, "end");
		return (expr_depth > 1) ? tmp : null;
	}

	@Override
	public Expr visit(LogicalOrNode node)
	{
		Label go_on = new Label();
		Label end = new Label();

		Var tmp = new_int_tmp();
		add_assign(tmp, visit_expr(node.getLeft_son()));
		stmts.add(new CJump(tmp, go_on, end));
		add_label(go_on, "go_on");
		add_assign(tmp, visit_expr(node.getRight_son()));
		add_label(end, "end");
		return (expr_depth > 1) ? tmp : null;
	}

	private void expand_print(ExprNode arg, boolean newline, boolean last)
	{
		if (arg instanceof FuncallNode && ((FuncallNode) arg).function_type().getEntity().getName().equals("toString"))
		{
			Expr x = visit_expr(((FuncallNode) arg).getArgs().get(0));
			stmts.add(new Call(newline && last ? printlnint_function : printint_function, new LinkedList<>(){{add(x);}}));
		}
		else if (arg instanceof BinaryOpNode && ((BinaryOpNode) arg).getOperator() == BinaryOpNode.BinaryOp.ADD)
		{
			expand_print(((BinaryOpNode) arg).getLeft_son(), newline, false);
			expand_print(((BinaryOpNode) arg).getRight_son(), newline, last);
		}
		else
		{
			Expr x = visit_expr(arg);
			stmts.add(new Call(newline && last ? println_function : print_function, new LinkedList<>(){{add(x);}}));
		}
	}

	private boolean not_use_tmp = false;
	@Override
	public Expr visit(AssignNode node)
	{
		Expr lhs = visit_expr(node.getLhs());
		Expr rhs = null;
		if (expr_depth <= 1 && node.isIs_output_irrelevant())
			return null;
		if (lhs instanceof Var)
		{
			Entity entity = ((Var) lhs).getEntity();
			Pair<Boolean, Integer> ret = expr_hash(node.getRhs());
			if (ret.first && !(node.getRhs() instanceof IntegerLiteralNode))
			{
				Entity same = assign_table.get(ret.second);
				if (same == null)
				{
					in_dependency.addAll(getIn_dependency(node.getRhs()));
					assign_table.put(ret.second, entity);
				}
				else
					rhs = new Var(same);
			}
		}
		if (rhs == null)
		{
			not_use_tmp = true;
			rhs = visit_expr(node.getRhs());
			not_use_tmp = false;
		}
		add_assign(lhs, rhs);
		return lhs;
	}

	private Pair<Boolean, Integer> expr_hash(ExprNode node)
	{
		if (node instanceof BinaryOpNode)
		{
			Pair<Boolean, Integer> left = expr_hash(((BinaryOpNode) node).getLeft_son());
			Pair<Boolean, Integer> right = expr_hash(((BinaryOpNode) node).getRight_son());
			if (left.first && right.first)
			{
				int hash = ((BinaryOpNode) node).getOperator().hashCode();
				hash += left.second;
				hash += right.second ^ 0x5D;
				return new Pair<>(true, hash);
			}
			else
				return new Pair<>(false, 0);
		}
		else if (node instanceof VariableNode)
			return new Pair<>(true, ((VariableNode) node).getEntity().hashCode());
		else if (node instanceof IntegerLiteralNode)
			return new Pair<>(true, ((int) ((IntegerLiteralNode) node).getValue()));
		else
			return new Pair<>(false, 0);
	}

	private Set<Entity> getIn_dependency(ExprNode node)
	{
		Set<Entity> ret = new HashSet<>();
		if (node instanceof BinaryOpNode)
		{
			ret.addAll(getIn_dependency(((BinaryOpNode) node).getLeft_son()));
			ret.addAll(getIn_dependency(((BinaryOpNode) node).getRight_son()));
		}
		else if (node instanceof VariableNode)
			ret.add(((VariableNode) node).getEntity());
		return ret;
	}

	@Override
	public Void visit(VariableDefLineNode node)
	{
		for (VariableDefNode defNode : node.getVariableDefNodeList())
		{
			visit(defNode);
		}
		return null;
	}

	@Override
	public Void visit(ClassDefNode node)
	{
		throw new InternalErrorS("Invalid call to visit ClassDefNode in IRBuilder");
	}

	@Override
	public Void visit(FunctionDefNode node)
	{
		throw new InternalErrorS("Invalid call to visit FunctionDefNode in IRBuilder");
	}

	@Override
	public Void visit(VariableDefNode node)
	{
		ExprNode init = node.getEntity().getInitializer();
		if (init != null && !node.getEntity().isIs_output_irrelevant())
		{
			ExprStmtNode assign = new ExprStmtNode(node.getLocation(), new AssignNode(new VariableNode(node.getEntity(), node.getLocation()), init));
			visit(assign);
		}
		return null;
	}

	@Override
	public Void visit(ReturnNode node)
	{
		clear_assign_table();
		refresh_tmp_stack();
		expr_depth++;
		if (inline_mode > 0)
		{
			if (node.getExpr() != null && inline_return_var.peek() != inline_no_use)
				add_assign(inline_return_var.peek(), visit_expr(node.getExpr()));
			stmts.add(new Jump(inline_return_label.peek()));
		}
		else
		{
			if (node.getExpr() == null)
				stmts.add(new Return(null));
			else
				stmts.add(new Return(visit_expr(node.getExpr())));
			stmts.add(new Jump(current_function.getEnd_label_IR()));
		}
		expr_depth--;
		return null;
	}

	@Override
	public Void visit(ContinueNode node)
	{
		clear_assign_table();
		stmts.add(new Jump(incr_label_stack.peek()));
		return null;
	}

	@Override
	public Void visit(BreakNode node)
	{
		clear_assign_table();
		stmts.add(new Jump(end_label_stack.peek()));
		return null;
	}
}
