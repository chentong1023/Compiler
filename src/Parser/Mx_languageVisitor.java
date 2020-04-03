// Generated from D:/2020Spring/Compiler/Compiler/src/Parser\Mx_language.g4 by ANTLR 4.8
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Mx_languageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Mx_languageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(Mx_languageParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#variable_definition_line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_definition_line(Mx_languageParser.Variable_definition_lineContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#variable_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_definition(Mx_languageParser.Variable_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#class_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_definition(Mx_languageParser.Class_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#function_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition(Mx_languageParser.Function_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#common_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommon_type(Mx_languageParser.Common_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#variable_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_type(Mx_languageParser.Variable_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(Mx_languageParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code block_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock_stmt(Mx_languageParser.Block_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code var_def_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_def_stmt(Mx_languageParser.Var_def_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(Mx_languageParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code for_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_stmt(Mx_languageParser.For_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stmt(Mx_languageParser.While_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_stmt(Mx_languageParser.Expr_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continue_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_stmt(Mx_languageParser.Continue_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code break_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_stmt(Mx_languageParser.Break_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code return_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(Mx_languageParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blank_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlank_stmt(Mx_languageParser.Blank_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#if_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_block(Mx_languageParser.If_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#while_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_block(Mx_languageParser.While_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#for_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_block(Mx_languageParser.For_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(Mx_languageParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code aref_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAref_expr(Mx_languageParser.Aref_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logical_or_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_or_expr(Mx_languageParser.Logical_or_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code member_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMember_expr(Mx_languageParser.Member_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binary_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinary_expr(Mx_languageParser.Binary_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logical_and_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_and_expr(Mx_languageParser.Logical_and_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suffix_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffix_expr(Mx_languageParser.Suffix_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code new_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNew_expr(Mx_languageParser.New_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_expr(Mx_languageParser.Assign_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefix_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefix_expr(Mx_languageParser.Prefix_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcall_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncall_expr(Mx_languageParser.Funcall_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primary_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_expr(Mx_languageParser.Primary_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mx_languageParser#expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression_list(Mx_languageParser.Expression_listContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sub_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSub_expr(Mx_languageParser.Sub_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code this_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThis_expr(Mx_languageParser.This_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variable_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_expr(Mx_languageParser.Variable_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literal_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral_expr(Mx_languageParser.Literal_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt_const(Mx_languageParser.Int_constContext ctx);
	/**
	 * Visit a parse tree produced by the {@code string_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_const(Mx_languageParser.String_constContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bool_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_const(Mx_languageParser.Bool_constContext ctx);
	/**
	 * Visit a parse tree produced by the {@code null_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNull_const(Mx_languageParser.Null_constContext ctx);
	/**
	 * Visit a parse tree produced by the {@code error_creator}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitError_creator(Mx_languageParser.Error_creatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code array_creator}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_creator(Mx_languageParser.Array_creatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constructor}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructor(Mx_languageParser.ConstructorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code single_creator}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_creator(Mx_languageParser.Single_creatorContext ctx);
}