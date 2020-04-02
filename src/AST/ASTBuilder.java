package AST;

import Entity.ClassEntity;
import Entity.FunctionEntity;
import Entity.ParameterEntity;
import Entity.VariableEntity;
import ExceptionS.InternalErrorS;
import ExceptionS.SemanticError;
import Parser.Mx_languageBaseVisitor;
import Parser.Mx_languageParser;
import Type.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.LinkedList;
import java.util.List;

public class ASTBuilder extends Mx_languageBaseVisitor<Void>
{
	private ParseTreeProperty<Object> mmp = new ParseTreeProperty<>();
	private AST ast;

	public ASTBuilder(Mx_languageParser.ProgContext ctx)
	{
		this.visitProg(ctx);
	}

	public AST getAst()
	{
		return ast;
	}

	private ExprNode getExpr(Mx_languageParser.ExpressionContext ctx)
	{
		if (ctx == null) return null;
		return (ExprNode) mmp.get(ctx);
	}

	private StmtNode getStmt(Mx_languageParser.StatementContext ctx)
	{
		if (ctx == null) return null;
		return (StmtNode) mmp.get(ctx);
	}

	@Override
	public Void visitProg(Mx_languageParser.ProgContext ctx)
	{
		List<ClassEntity> classEntities = new LinkedList<>();
		List<FunctionEntity> functionEntities = new LinkedList<>();
		List<VariableEntity> variableEntities = new LinkedList<>();
		List<DefinitionNode> definitionNodes = new LinkedList<>();
		for (ParserRuleContext context : ctx.getRuleContexts(ParserRuleContext.class))
			if (context instanceof Mx_languageParser.Variable_definition_lineContext)
			{
				visitVariable_definition_line((Mx_languageParser.Variable_definition_lineContext) context);
				List<VariableDefNode> variableDefNodes = ((VariableDefLineNode) mmp.get(context)).getVariableDefNodeList();
				for (VariableDefNode node : variableDefNodes)
					variableEntities.add(node.getEntity());
				definitionNodes.addAll(variableDefNodes);
			} else if (context instanceof Mx_languageParser.Function_definitionContext)
			{
				visitFunction_definition((Mx_languageParser.Function_definitionContext) context);
				functionEntities.add(((FunctionDefNode) mmp.get(context)).getEntity());
				definitionNodes.add((FunctionDefNode) mmp.get(context));
			} else if (context instanceof Mx_languageParser.Class_definitionContext)
			{
				visitClass_definition((Mx_languageParser.Class_definitionContext) context);
				classEntities.add(((ClassDefNode) mmp.get(context)).getEntity());
				definitionNodes.add((ClassDefNode) mmp.get(context));
			} else
				throw new InternalErrorS("ASTBuilder@visitProg With undefined context!");
		ast = new AST(classEntities, functionEntities, variableEntities, definitionNodes);
		return null;
	}

	@Override
	public Void visitVariable_definition_line(Mx_languageParser.Variable_definition_lineContext ctx)
	{
		visitVariable_type(ctx.variable_type());
		List<VariableDefNode> variableDefNodes = new LinkedList<>();
		for (Mx_languageParser.Variable_definitionContext context : ctx.variable_definition())
			if (context != null)
			{
				visitVariable_definition(context);
				VariableEntity entity = new VariableEntity(context.Identifier().getText(), new Location(context.Identifier()),
						(Type) mmp.get(ctx.variable_type()), getExpr(context.expression()));
				variableDefNodes.add(new VariableDefNode(entity));
			}
		mmp.put(ctx, new VariableDefLineNode(new Location(ctx), variableDefNodes));
		return null;
	}

	@Override
	public Void visitVariable_definition(Mx_languageParser.Variable_definitionContext ctx)
	{
		if (ctx.expression() != null)
			visitExpression(ctx.expression());
		return null;
	}

	@Override
	public Void visitClass_definition(Mx_languageParser.Class_definitionContext ctx)
	{
		List<VariableDefNode> variableDefNodes = new LinkedList<>();
		List<FunctionDefNode> functionDefNodes = new LinkedList<>();

		String name = ctx.name.getText();

		for (Mx_languageParser.Variable_definition_lineContext context : ctx.variable_definition_line())
		{
			visitVariable_definition_line(context);
			List<VariableDefNode> variableDefNodes1 = ((VariableDefLineNode) mmp.get(context)).getVariableDefNodeList();
			variableDefNodes.addAll(variableDefNodes1);
		}

		FunctionEntity constructor = null;
		for (Mx_languageParser.Function_definitionContext context : ctx.function_definition())
		{
			visitFunction_definition(context);
			FunctionDefNode node = (FunctionDefNode) mmp.get(context);
			functionDefNodes.add(node);
			FunctionEntity entity = node.getEntity();
			if (entity.is_constructor())
			{
				constructor = entity;
				if (!entity.getName().equals(ClassType.CONSTRUCTOR_NAME + name))
					throw new SemanticError(new Location(ctx.name), "Wrong name of constructor : " + entity.getName() + " and " + ClassType.CONSTRUCTOR_NAME + name);
			}
		}
		ClassEntity entity1 = new ClassEntity(name, new Location(ctx.name), variableDefNodes, functionDefNodes);
		entity1.setConstructor(constructor);
		mmp.put(ctx, new ClassDefNode(entity1));

		return null;
	}

	@Override
	public Void visitFunction_definition(Mx_languageParser.Function_definitionContext ctx)
	{
		List<ParameterEntity> parameterEntities = new LinkedList<>();
		for (Mx_languageParser.ParameterContext context : ctx.parameter())
		{
			visitParameter(context);
			parameterEntities.add((ParameterEntity) mmp.get(context));
		}
		visitBlock(ctx.block());
		FunctionEntity entity;
		if (ctx.return_type == null)
		{
			entity = new FunctionEntity(ClassType.CONSTRUCTOR_NAME + ctx.name.getText(), new Location(ctx.name),
					new ClassType(ctx.name.getText()), parameterEntities, (BlockNode) mmp.get(ctx.block()));
			entity.setIs_constructor(true);
		} else
		{
			visitVariable_type(ctx.return_type);
			entity = new FunctionEntity(ctx.name.getText(), new Location(ctx.name), (Type) mmp.get(ctx.return_type),
					parameterEntities, (BlockNode) mmp.get(ctx.block()));
		}
		mmp.put(ctx, new FunctionDefNode(entity));

		return null;
	}

	@Override
	public Void visitVariable_type(Mx_languageParser.Variable_typeContext ctx)
	{
		Type baseType;
		if (ctx.Identifier() != null)
			baseType = new ClassType(ctx.Identifier().getText());
		else
		{
			visitCommon_type(ctx.common_type());
			baseType = (Type) mmp.get(ctx.common_type());
		}

		int dimension = (ctx.getChildCount() - 1) / 2;
		if (dimension == 0)
			mmp.put(ctx, baseType);
		else
		{
			if (baseType instanceof VoidType)
				throw new SemanticError(new Location(ctx), "Use void as array!");
			mmp.put(ctx, new ArrayType(baseType, dimension));
		}
		return null;
	}

	@Override
	public Void visitCommon_type(Mx_languageParser.Common_typeContext ctx)
	{
		Type type;
		switch (ctx.type.getText())
		{
			case "bool":
				type = new BoolType();
				break;
			case "int":
				type = new IntegerType();
				break;
			case "void":
				type = new VoidType();
				break;
			case "string":
				type = new StringType();
				break;
			default:
				throw new InternalErrorS("ASTBuilder@visitCommon_type Invalid Type" + ctx.type.getText());
		}
		mmp.put(ctx, type);
		return null;
	}

	@Override
	public Void visitBlock(Mx_languageParser.BlockContext ctx)
	{
		List<StmtNode> stmtNodes = new LinkedList<>();
		for (Mx_languageParser.StatementContext context : ctx.statement())
		{
			visitStatement(context);
			StmtNode stmtNode = getStmt(context);
			if (stmtNode != null)
				stmtNodes.add(stmtNode);
		}
		mmp.put(ctx, new BlockNode(new Location(ctx), stmtNodes));
		return null;
	}

	private void visitStatement(Mx_languageParser.StatementContext context)
	{
		if (context == null) return;
		if (context instanceof Mx_languageParser.Block_stmtContext)
			visitBlock_stmt((Mx_languageParser.Block_stmtContext) context);
		else if (context instanceof Mx_languageParser.Var_def_stmtContext)
			visitVar_def_stmt((Mx_languageParser.Var_def_stmtContext) context);
		else if (context instanceof Mx_languageParser.If_stmtContext)
			visitIf_stmt((Mx_languageParser.If_stmtContext) context);
		else if (context instanceof Mx_languageParser.For_stmtContext)
			visitFor_stmt((Mx_languageParser.For_stmtContext) context);
		else if (context instanceof Mx_languageParser.While_stmtContext)
			visitWhile_stmt((Mx_languageParser.While_stmtContext) context);
		else if (context instanceof Mx_languageParser.Expr_stmtContext)
			visitExpr_stmt((Mx_languageParser.Expr_stmtContext) context);
		else if (context instanceof Mx_languageParser.Continue_stmtContext)
			visitContinue_stmt((Mx_languageParser.Continue_stmtContext) context);
		else if (context instanceof Mx_languageParser.Break_stmtContext)
			visitBreak_stmt((Mx_languageParser.Break_stmtContext) context);
		else if (context instanceof Mx_languageParser.Return_stmtContext)
			visitReturn_stmt((Mx_languageParser.Return_stmtContext) context);
		else if (context instanceof Mx_languageParser.Blank_stmtContext)
			visitBlank_stmt((Mx_languageParser.Blank_stmtContext) context);
		else
			throw new InternalErrorS("ASTBuilder@visitBlock Invalid statement" + context.getText());
	}

	@Override
	public Void visitBlock_stmt(Mx_languageParser.Block_stmtContext ctx)
	{
		visitBlock(ctx.block());
		mmp.put(ctx, mmp.get(ctx.block()));
		return null;
	}

	@Override
	public Void visitVar_def_stmt(Mx_languageParser.Var_def_stmtContext ctx)
	{
		visitVariable_definition_line(ctx.variable_definition_line());
		mmp.put(ctx, mmp.get(ctx.variable_definition_line()));
		return null;
	}

	@Override
	public Void visitIf_stmt(Mx_languageParser.If_stmtContext ctx)
	{
		visitIf_block(ctx.if_block());
		mmp.put(ctx, mmp.get(ctx.if_block()));
		return null;
	}

	@Override
	public Void visitFor_stmt(Mx_languageParser.For_stmtContext ctx)
	{
		visitFor_block(ctx.for_block());
		mmp.put(ctx, mmp.get(ctx.for_block()));
		return null;
	}

	@Override
	public Void visitWhile_stmt(Mx_languageParser.While_stmtContext ctx)
	{
		visitWhile_block(ctx.while_block());
		mmp.put(ctx, mmp.get(ctx.while_block()));
		return null;
	}

	@Override
	public Void visitExpr_stmt(Mx_languageParser.Expr_stmtContext ctx)
	{
		visitExpression(ctx.expression());
		mmp.put(ctx, new ExprStmtNode(new Location(ctx), getExpr(ctx.expression())));
		return null;
	}

	@Override
	public Void visitContinue_stmt(Mx_languageParser.Continue_stmtContext ctx)
	{
		mmp.put(ctx, new ContinueNode(new Location(ctx)));
		return null;
	}

	@Override
	public Void visitBreak_stmt(Mx_languageParser.Break_stmtContext ctx)
	{
		mmp.put(ctx, new BreakNode(new Location(ctx)));
		return null;
	}

	@Override
	public Void visitReturn_stmt(Mx_languageParser.Return_stmtContext ctx)
	{
		if (ctx.expression() != null)
			visitExpression(ctx.expression());
		mmp.put(ctx, new ReturnNode(new Location(ctx), getExpr(ctx.expression())));
		return null;
	}

	@Override
	public Void visitBlank_stmt(Mx_languageParser.Blank_stmtContext ctx)
	{
		mmp.put(ctx, null);
		return null;
	}

	@Override
	public Void visitIf_block(Mx_languageParser.If_blockContext ctx)
	{
		visitExpression(ctx.expression());
		visitStatement(ctx.statement(0));
		if (ctx.statement(1) != null)
			visitStatement(ctx.statement(1));
		mmp.put(ctx, new IfNode(new Location(ctx), getExpr(ctx.expression()), getStmt(ctx.statement(0)), getStmt(ctx.statement(1))));
		return null;
	}

	@Override
	public Void visitWhile_block(Mx_languageParser.While_blockContext ctx)
	{
		visitExpression(ctx.expression());
		visitStatement(ctx.statement());
		mmp.put(ctx, new WhileNode(new Location(ctx), getExpr(ctx.expression()), getStmt(ctx.statement())));
		return null;
	}

	@Override
	public Void visitFor_block(Mx_languageParser.For_blockContext ctx)
	{
		visitExpression(ctx.init);
		visitExpression(ctx.cond);
		visitExpression(ctx.incr);
		visitStatement(ctx.statement());
		mmp.put(ctx, new ForNode(new Location(ctx), getExpr(ctx.init), getExpr(ctx.cond), getExpr(ctx.incr), getStmt(ctx.statement())));
		return null;
	}

	@Override
	public Void visitParameter(Mx_languageParser.ParameterContext ctx)
	{
		visitVariable_type(ctx.variable_type());
		mmp.put(ctx, new ParameterEntity(ctx.Identifier().getText(), new Location(ctx), (Type) mmp.get(ctx.variable_type())));
		return null;
	}

	private void visitExpression(Mx_languageParser.ExpressionContext ctx)
	{
		if (ctx == null) return;
		if (ctx instanceof Mx_languageParser.Primary_exprContext)
			visitPrimary_expr((Mx_languageParser.Primary_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.Member_exprContext)
			visitMember_expr((Mx_languageParser.Member_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.New_exprContext)
			visitNew_expr((Mx_languageParser.New_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.Aref_exprContext)
			visitAref_expr((Mx_languageParser.Aref_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.Funcall_exprContext)
			visitFuncall_expr((Mx_languageParser.Funcall_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.Suffix_exprContext)
			visitSuffix_expr((Mx_languageParser.Suffix_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.Prefix_exprContext)
			visitPrefix_expr((Mx_languageParser.Prefix_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.Binary_exprContext)
			visitBinary_expr((Mx_languageParser.Binary_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.Logical_and_exprContext)
			visitLogical_and_expr((Mx_languageParser.Logical_and_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.Logical_or_exprContext)
			visitLogical_or_expr((Mx_languageParser.Logical_or_exprContext) ctx);
		else if (ctx instanceof Mx_languageParser.Assign_exprContext)
			visitAssign_expr((Mx_languageParser.Assign_exprContext) ctx);
		else
			throw new InternalErrorS("ASTBuilder@visitExpression Invalid Context : " + ctx.getText());
	}

	@Override
	public Void visitPrimary_expr(Mx_languageParser.Primary_exprContext ctx)
	{
		visitPrimary(ctx.primary());
		mmp.put(ctx, mmp.get(ctx.primary()));
		return null;
	}

	@Override
	public Void visitMember_expr(Mx_languageParser.Member_exprContext ctx)
	{
		visitExpression(ctx.expression());
		mmp.put(ctx, new MemberNode(getExpr(ctx.expression()), ctx.Identifier().getText()));
		return null;
	}

	@Override
	public Void visitNew_expr(Mx_languageParser.New_exprContext ctx)
	{
		visitCreator(ctx.creator());
		mmp.put(ctx, mmp.get(ctx.creator()));
		return null;
	}

	@Override
	public Void visitAref_expr(Mx_languageParser.Aref_exprContext ctx)
	{
		visitExpression(ctx.expression(0));
		visitExpression(ctx.expression(1));
		mmp.put(ctx, new ArefNode(getExpr(ctx.expression(0)), getExpr(ctx.expression(1))));
		return null;
	}

	@Override
	public Void visitFuncall_expr(Mx_languageParser.Funcall_exprContext ctx)
	{
		visitExpression(ctx.expression());
		List<ExprNode> args;
		if (ctx.expression_list() == null)
			args = new LinkedList<>();
		else
		{
			visitExpression_list(ctx.expression_list());
			args = (List<ExprNode>) mmp.get(ctx.expression_list());
		}
		mmp.put(ctx, new FuncallNode(getExpr(ctx.expression()), args));
		return null;
	}

	@Override
	public Void visitSuffix_expr(Mx_languageParser.Suffix_exprContext ctx)
	{
		visitExpression(ctx.expression());
		UnaryOpNode.UnaryOp op;
		switch (ctx.op.getText())
		{
			case "++":
				op = UnaryOpNode.UnaryOp.SUF_INC;
				break;
			case "--":
				op = UnaryOpNode.UnaryOp.SUF_DEC;
				break;
			default:
				throw new InternalErrorS("ASTBuilder@visitSuffix_expr Invalid op : " + ctx.op.getText());
		}
		mmp.put(ctx, new SuffixOpNode(op, getExpr(ctx.expression())));
		return null;
	}

	@Override
	public Void visitPrefix_expr(Mx_languageParser.Prefix_exprContext ctx)
	{
		visitExpression(ctx.expression());
		UnaryOpNode.UnaryOp op;
		switch (ctx.op.getText())
		{
			case "+":
				op = UnaryOpNode.UnaryOp.ADD;
				break;
			case "-":
				op = UnaryOpNode.UnaryOp.MINUS;
				break;
			case "++":
				op = UnaryOpNode.UnaryOp.PRE_INC;
				break;
			case "--":
				op = UnaryOpNode.UnaryOp.PRE_DEC;
				break;
			case "~":
				op = UnaryOpNode.UnaryOp.BIT_NOT;
				break;
			case "!":
				op = UnaryOpNode.UnaryOp.LOGIC_NOT;
				break;
			default:
				throw new InternalErrorS("ASTBuild@visitPrefix_expr Invalid op : " + ctx.op.getText());
		}
		mmp.put(ctx, new PrefixOpNode(op, getExpr(ctx.expression())));
		return null;
	}

	@Override
	public Void visitBinary_expr(Mx_languageParser.Binary_exprContext ctx)
	{
		visitExpression(ctx.expression(0));
		visitExpression(ctx.expression(1));
		BinaryOpNode.BinaryOp op;
		switch (ctx.op.getText())
		{
			case "*":
				op = BinaryOpNode.BinaryOp.MUL;
				break;
			case "/":
				op = BinaryOpNode.BinaryOp.DIV;
				break;
			case "%":
				op = BinaryOpNode.BinaryOp.MOD;
				break;
			case "+":
				op = BinaryOpNode.BinaryOp.ADD;
				break;
			case "-":
				op = BinaryOpNode.BinaryOp.SUB;
				break;
			case "<<":
				op = BinaryOpNode.BinaryOp.LSHIFT;
				break;
			case ">>":
				op = BinaryOpNode.BinaryOp.RSHIFT;
				break;
			case "<":
				op = BinaryOpNode.BinaryOp.LT;
				break;
			case ">":
				op = BinaryOpNode.BinaryOp.GT;
				break;
			case "<=":
				op = BinaryOpNode.BinaryOp.LE;
				break;
			case ">=":
				op = BinaryOpNode.BinaryOp.GE;
				break;
			case "==":
				op = BinaryOpNode.BinaryOp.EQ;
				break;
			case "!=":
				op = BinaryOpNode.BinaryOp.NE;
				break;
			case "&":
				op = BinaryOpNode.BinaryOp.BIT_AND;
				break;
			case "|":
				op = BinaryOpNode.BinaryOp.BIT_OR;
				break;
			case "^":
				op = BinaryOpNode.BinaryOp.BIT_XOR;
				break;
			default:
				throw new InternalErrorS("ASTBuilder@visitBinary_expr Invalid op : " + ctx.op.getText());
		}
		mmp.put(ctx, new BinaryOpNode(getExpr(ctx.expression(0)), op, getExpr(ctx.expression(1))));
		return null;
	}

	@Override
	public Void visitLogical_and_expr(Mx_languageParser.Logical_and_exprContext ctx)
	{
		visitExpression(ctx.expression(0));
		visitExpression(ctx.expression(1));
		mmp.put(ctx, new LogicalAndNode(getExpr(ctx.expression(0)), getExpr(ctx.expression(1))));
		return null;
	}

	@Override
	public Void visitLogical_or_expr(Mx_languageParser.Logical_or_exprContext ctx)
	{
		visitExpression(ctx.expression(0));
		visitExpression(ctx.expression(1));
		mmp.put(ctx, new LogicalOrNode(getExpr(ctx.expression(0)), getExpr(ctx.expression(1))));
		return null;
	}

	@Override
	public Void visitAssign_expr(Mx_languageParser.Assign_exprContext ctx)
	{
		visitExpression(ctx.expression(0));
		visitExpression(ctx.expression(1));
		mmp.put(ctx, new AssignNode(getExpr(ctx.expression(0)), getExpr(ctx.expression(1))));
		return null;
	}

	@Override
	public Void visitExpression_list(Mx_languageParser.Expression_listContext ctx)
	{
		List<ExprNode> exprNodes = new LinkedList<>();
		for (Mx_languageParser.ExpressionContext context : ctx.expression())
		{
			visitExpression(context);
			exprNodes.add(getExpr(context));
		}
		mmp.put(ctx, exprNodes);
		return null;
	}

	private void visitPrimary(Mx_languageParser.PrimaryContext primary)
	{
		if (primary instanceof Mx_languageParser.Sub_exprContext)
			visitSub_expr((Mx_languageParser.Sub_exprContext) primary);
		else if (primary instanceof Mx_languageParser.This_exprContext)
			visitThis_expr((Mx_languageParser.This_exprContext) primary);
		else if (primary instanceof Mx_languageParser.Variable_exprContext)
			visitVariable_expr((Mx_languageParser.Variable_exprContext) primary);
		else if (primary instanceof Mx_languageParser.Literal_exprContext)
			visitLiteral_expr((Mx_languageParser.Literal_exprContext) primary);
		else
			throw new InternalErrorS("ASTBuilder@visitPrimary Invalid context : " + primary.getText());
	}

	@Override
	public Void visitSub_expr(Mx_languageParser.Sub_exprContext ctx)
	{
		visitExpression(ctx.expression());
		mmp.put(ctx, mmp.get(ctx.expression()));
		return null;
	}

	@Override
	public Void visitThis_expr(Mx_languageParser.This_exprContext ctx)
	{
		mmp.put(ctx, new VariableNode(new Location(ctx), "this"));
		return null;
	}

	@Override
	public Void visitVariable_expr(Mx_languageParser.Variable_exprContext ctx)
	{
		mmp.put(ctx, new VariableNode(new Location(ctx.Identifier()), ctx.Identifier().getText()));
		return null;
	}

	@Override
	public Void visitLiteral_expr(Mx_languageParser.Literal_exprContext ctx)
	{
		visitLiteral(ctx.literal());
		mmp.put(ctx, mmp.get(ctx.literal()));
		return null;
	}

	private void visitLiteral(Mx_languageParser.LiteralContext literal)
	{
		if (literal instanceof Mx_languageParser.Int_constContext)
			visitInt_const((Mx_languageParser.Int_constContext) literal);
		else if (literal instanceof Mx_languageParser.String_constContext)
			visitString_const((Mx_languageParser.String_constContext) literal);
		else if (literal instanceof Mx_languageParser.Bool_constContext)
			visitBool_const((Mx_languageParser.Bool_constContext) literal);
		else if (literal instanceof Mx_languageParser.Null_constContext)
			visitNull_const((Mx_languageParser.Null_constContext) literal);
		else
			throw new InternalErrorS("ASTBuilder@visitLiteral Invalid Context" + literal.getText());
	}

	@Override
	public Void visitInt_const(Mx_languageParser.Int_constContext ctx)
	{
		mmp.put(ctx, new IntegerLiteralNode(new Location(ctx), Long.parseLong(ctx.DecimalInteger().getText())));
		return null;
	}

	@Override
	public Void visitString_const(Mx_languageParser.String_constContext ctx)
	{
		String value = ctx.StringLiteral().getText();
		value = value.substring(1, value.length() - 1);
		mmp.put(ctx, new StringLiteralNode(new Location(ctx), value));
		return null;
	}

	@Override
	public Void visitBool_const(Mx_languageParser.Bool_constContext ctx)
	{
		mmp.put(ctx, new BoolLiteralNode(new Location(ctx), ctx.getText().equals("true")));
		return null;
	}

	@Override
	public Void visitNull_const(Mx_languageParser.Null_constContext ctx)
	{
		mmp.put(ctx, new VariableNode(new Location(ctx), "null"));
		return null;
	}

	private void visitCreator(Mx_languageParser.CreatorContext creator)
	{
		if (creator instanceof Mx_languageParser.Array_creatorContext)
			visitArray_creator((Mx_languageParser.Array_creatorContext) creator);
		else if (creator instanceof Mx_languageParser.ConstructorContext)
			visitConstructor((Mx_languageParser.ConstructorContext) creator);
		else if (creator instanceof Mx_languageParser.Single_creatorContext)
			visitSingle_creator((Mx_languageParser.Single_creatorContext) creator);
		else throw new InternalErrorS("ASTBuilder@visitCreator Invalid context : " + creator.getText());
	}

	@Override
	public Void visitArray_creator(Mx_languageParser.Array_creatorContext ctx)
	{
		Type baseType;
		if (ctx.Identifier() != null)
			baseType = new ClassType(ctx.Identifier().getText());
		else
		{
			visitCommon_type(ctx.common_type());
			baseType = (Type) mmp.get(ctx.common_type());
		}
		List<Mx_languageParser.ExpressionContext> expressionContexts = ctx.expression();
		int dimension = (ctx.getChildCount() - 1 - expressionContexts.size()) / 2;
		if (baseType instanceof VoidType)
			throw new SemanticError(new Location(ctx), "Use void as array!");
		Type type = new ArrayType(baseType, dimension);
		List<ExprNode> exprNodes = new LinkedList<>();
		for (Mx_languageParser.ExpressionContext context : expressionContexts)
		{
			visitExpression(context);
			exprNodes.add(getExpr(context));
		}
		mmp.put(ctx, new CreatorNode(new Location(ctx), type, exprNodes, dimension));
		return null;
	}

	@Override
	public Void visitConstructor(Mx_languageParser.ConstructorContext ctx)
	{
		Type type;
		type = new ClassType(ctx.Identifier().getText());
		mmp.put(ctx, new CreatorNode(new Location(ctx), type, null, 0));
		return null;
	}

	@Override
	public Void visitSingle_creator(Mx_languageParser.Single_creatorContext ctx)
	{
		Type type;
		type = new ClassType(ctx.Identifier().getText());
		mmp.put(ctx, new CreatorNode(new Location(ctx), type, null, 0));
		return null;
	}
}