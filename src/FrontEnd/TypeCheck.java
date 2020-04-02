package FrontEnd;

import AST.*;
import Entity.*;
import ExceptionS.InternalErrorS;
import ExceptionS.SemanticError;
import Parser.*;
import Type.*;

import java.util.List;

public class TypeCheck extends ASTBaseVisitor
{
	private Scope scope;
	private static FunctionEntity currentFunction = null;
	static final private Type boolType = new BoolType();
	static final private Type intType = new IntegerType();
	static final private Type stringType = new StringType();
	private int loopDepth = 0;
	private int funcDepth = 0;

	private void checkCompatibility(Location location, Type type, Type type1, boolean b)
	{
		if (!type.is_compatible(type1))
		{
			String message;
			if (b)
				message = "Invalid type " + type + ", expecting " + type1;
			else
				message = "Incompatible type " + type + " to " + type1;
			throw new SemanticError(location, message);
		}
	}

	public TypeCheck(Scope scope)
	{
		this.scope = scope;
	}

	@Override
	public Void visit(FunctionDefNode node)
	{
		level++;
		currentFunction = node.getEntity();
		if (!currentFunction.is_constructor() && currentFunction.getReturn_type() == null)
			throw new SemanticError(node.getLocation(), "expect a return type");
		visitStmt(node.getEntity().getBody());
		currentFunction = null;
		level--;
		return null;
	}

	@Override
	public Void visit(VariableDefNode node)
	{
		level++;
		ExprNode init = node.getEntity().getInitializer();
		if (init != null)
		{
			visitExpr(init);
			checkCompatibility(node.getLocation(), init.getType(), node.getEntity().getType(), false);
		}
		if (node.getEntity().getType().is_void())
			throw new SemanticError(node.getLocation(), "use void as variable");
		level--;
		return null;
	}

	@Override
	public Void visit(IfNode node)
	{
		visitExpr(node.getCond());
		if (node.getThen_body() != null)
			visitStmt(node.getThen_body());
		if (node.getElse_body() != null)
			visitStmt(node.getElse_body());
		checkCompatibility(node.getLocation(), node.getCond().getType(), boolType, true);
		return null;
	}

	@Override
	public Void visit(WhileNode node)
	{
		visitExpr(node.getCond());
		if (node.getBody() != null)
		{
			loopDepth++;
			visitStmt(node.getBody());
			loopDepth--;
		}
		checkCompatibility(node.getLocation(), node.getCond().getType(), boolType, true);
		return null;
	}

	@Override
	public Void visit(ForNode node)
	{
		if (node.getInit() != null)
			visitExpr(node.getInit());
		if (node.getCond() != null)
		{
			visitExpr(node.getCond());
			checkCompatibility(node.getLocation(), node.getCond().getType(), boolType, true);
		}
		if (node.getIncr() != null)
			visitExpr(node.getIncr());
		if (node.getBody() != null)
		{
			loopDepth++;
			visitStmt(node.getBody());
			loopDepth--;
		}
		return null;
	}

	@Override
	public Void visit(BreakNode node)
	{
		if (loopDepth <= 0)
			throw new SemanticError(node.getLocation(), "unexpected break");
		return null;
	}

	@Override
	public Void visit(ContinueNode node)
	{
		if (loopDepth <= 0)
			throw new SemanticError(node.getLocation(), "unexpected continue");
		return null;
	}

	@Override
	public Void visit(ReturnNode node)
	{
		level++;
		if (currentFunction == null)
			throw new SemanticError(node.getLocation(), "cannot return outside function");

		if (currentFunction.is_constructor())
		{
			if (node.getExpr() != null)
				throw new SemanticError(node.getLocation(), "cannot return in constructor");
		} else
		{
			if (node.getExpr() != null)
			{
				visitExpr(node.getExpr());
				checkCompatibility(node.getLocation(), node.getExpr().getType(), currentFunction.getReturn_type(), true);
			} else
			{
				if (!currentFunction.getReturn_type().is_void())
					throw new SemanticError(node.getLocation(), "cannot return to void");
			}
		}
		level--;
		return null;
	}

	@Override
	public Void visit(AssignNode node)
	{
		level++;
		visitExpr(node.getLhs());
		visitExpr(node.getRhs());

		if (!node.getLhs().is_assignable())
			throw new SemanticError(node.getLocation(), "LHS of '=' is not assignable");
		checkCompatibility(node.getLocation(), node.getLhs().getType(), node.getRhs().getType(), false);
		level--;
		return null;
	}

	@Override
	public Void visit(UnaryOpNode node)
	{
		visitExpr(node.getExpr());
		Type expect;
		switch (node.getOperator())
		{
			case PRE_INC:
			case PRE_DEC:
			case SUF_INC:
			case SUF_DEC:
			case MINUS:
			case ADD:
			case BIT_NOT:
				expect = intType;
				break;
			case LOGIC_NOT:
				expect = boolType;
				break;
			default:
				throw new InternalErrorS("TypeCheck@visit_UnaryOp Invalid operator");
		}
		checkCompatibility(node.getLocation(), node.getExpr().getType(), expect, true);
		return null;
	}

	@Override
	public Void visit(PrefixOpNode node)
	{
		visit((UnaryOpNode) node);
		if (node.getOperator() == UnaryOpNode.UnaryOp.PRE_INC || node.getOperator() == UnaryOpNode.UnaryOp.PRE_DEC)
			node.setIs_assignable(true);
		return null;
	}

	@Override
	public Void visit(SuffixOpNode node)
	{
		visit((UnaryOpNode) node);
		if (!node.getExpr().is_assignable())
			throw new SemanticError(node.getLocation(), "lvalue is needed");
		return null;
	}

	@Override
	public Void visit(BinaryOpNode node)
	{
		visitExpr(node.getLeft_son());
		visitExpr(node.getRight_son());

		Type ltype = node.getLeft_son().getType(), rtype = node.getRight_son().getType();
		switch (node.getOperator())
		{
			case MUL:
			case DIV:
			case MOD:
			case SUB:
			case LSHIFT:
			case RSHIFT:
			case BIT_AND:
			case BIT_XOR:
			case BIT_OR:
				checkCompatibility(node.getLeft_son().getLocation(), ltype, intType, true);
				checkCompatibility(node.getRight_son().getLocation(), rtype, intType, true);
				node.setType(ltype);
				break;
			case GT:
			case LE:
			case GE:
			case LT:
				checkCompatibility(node.getLeft_son().getLocation(), ltype, rtype, false);
				if (!ltype.is_full_comparable() && !rtype.is_full_comparable())
					throw new SemanticError(node.getLocation(), "Cannot compare these two " + ltype);
				node.setType(boolType);
				break;
			case EQ:
			case NE:
				checkCompatibility(node.getLocation(), ltype, rtype, true);
				if(!ltype.is_half_comparable() && !rtype.is_half_comparable())
					throw new SemanticError(node.getLocation(), "Cannot compare two " + ltype);
				node.setType(boolType);
				break;
			case LOGIC_OR:
			case LOGIC_AND:
				checkCompatibility(node.getLeft_son().getLocation(), ltype, boolType, true);
				checkCompatibility(node.getRight_son().getLocation(), rtype, boolType, true);
				node.setType(ltype);
				break;
			case ADD:
				checkCompatibility(node.getLocation(), ltype, rtype, true);
				if (!ltype.is_int() && !ltype.is_string())
					throw new SemanticError(node.getLocation(), "Cannot add two " + ltype);
				node.setType(ltype);
				break;
			default:
				throw new InternalErrorS("TypeCheck@visit_BinaryOp Invalid op " + node.getOperator());
		}
		return null;
	}

	@Override
	public Void visit(LogicalOrNode node)
	{
		level++;
		visit((BinaryOpNode) node);
		level--;
		return null;
	}

	@Override
	public Void visit(LogicalAndNode node)
	{
		level++;
		visit((BinaryOpNode) node);
		level--;
		return null;
	}

	@Override
	public Void visit(FuncallNode node)
	{
		level++;
		visitExpr(node.getExpr());
		Type type = node.getExpr().getType();
		if (!type.is_function())
			throw new SemanticError(node.getLocation(), "Invalid type : " + type + " expecting function");
		FunctionEntity entity = ((FunctionType) type).getEntity();

		List<ParameterEntity> parameterEntities = entity.getParameterEntityList();
		List<ExprNode> exprNodes = node.getArgs();
		int base = 0;
		if (node.getExpr() instanceof MemberNode || (node.getExpr() instanceof VariableNode && ((VariableNode) node.getExpr()).is_member()))
			base = 1;

		if (parameterEntities.size() - base != exprNodes.size())
			throw new SemanticError(node.getLocation(), "Incompatible parameter number: " + exprNodes.size() +
					", expecting " + (parameterEntities.size() - base));

		for (int i = base; i < parameterEntities.size(); ++i)
		{
			ExprNode exprNode = exprNodes.get(i - base);
			visitExpr(exprNode);
			checkCompatibility(exprNode.getLocation(), exprNode.getType(), parameterEntities.get(i).getType(), true);
		}

		if (base != 0)
		{
			if (node.getExpr() instanceof MemberNode)
				node.add_this_pointer(((MemberNode) node.getExpr()).getExpr());
			else
				node.add_this_pointer(new VariableNode(currentFunction.getParameterEntityList().get(0)));
		}
		level--;
		return null;
	}

	@Override
	public Void visit(ArefNode node)
	{
		level++;
		visitExpr(node.getExpr());
		visitExpr(node.getIndx());
		if (!node.getExpr().getType().is_array())
			throw new SemanticError(node.getLocation(), "Invalid reference of " + node.getExpr().getType() + ", expecting an array");
		checkCompatibility(node.getIndx().getLocation(), node.getIndx().getType(), intType, true);
		node.setType(((ArrayType) node.getExpr().getType()).getBase_type());
		level--;
		return null;
	}

	@Override
	public Void visit(CreatorNode node)
	{
		level++;
		if (node.getExprs() != null)
		{
			for (ExprNode exprNode : node.getExprs())
			{
				visitExpr(exprNode);
				checkCompatibility(exprNode.getLocation(), exprNode.getType(), intType, true);
			}
		}
		level--;
		return null;
	}

	@Override
	public Void visit(MemberNode node)
	{
		level++;
		visitExpr(node.getExpr());
		Type type = node.getExpr().getType();

		if (type.is_class())
		{
			ClassEntity entity = ((ClassType) type).getEntity();
			Entity member = entity.getScope().lookup_current_level(node.getMember());
			if (member == null)
				throw new SemanticError(node.getLocation(), "Not Find member named \"" + node.getMember() + "\"");
			node.setEntity(member);
			node.setType(member.getType());
		}
		else if (type.is_array() || type.is_string())
		{
			Entity member;
			if (type.is_array())
				member = ArrayType.getScope().lookup_current_level(node.getMember());
			else
				member = StringType.getScope().lookup_current_level(node.getMember());
			if (member == null)
				throw new SemanticError(node.getLocation(), "Not Find member named \"" + node.getMember() + "\"");
			node.setEntity(member);
			node.setType(member.getType());
		}
		else
			throw new SemanticError(node.getLocation(), "Invalid type to use member operation " + node.getExpr().getType() + ", expecting class, array or string!");
		level--;
		return null;
	}
}