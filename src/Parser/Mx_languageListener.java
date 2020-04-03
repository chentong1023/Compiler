// Generated from D:/2020Spring/Compiler/Compiler/src/Parser\Mx_language.g4 by ANTLR 4.8
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Mx_languageParser}.
 */
public interface Mx_languageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(Mx_languageParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(Mx_languageParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#variable_definition_line}.
	 * @param ctx the parse tree
	 */
	void enterVariable_definition_line(Mx_languageParser.Variable_definition_lineContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#variable_definition_line}.
	 * @param ctx the parse tree
	 */
	void exitVariable_definition_line(Mx_languageParser.Variable_definition_lineContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#variable_definition}.
	 * @param ctx the parse tree
	 */
	void enterVariable_definition(Mx_languageParser.Variable_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#variable_definition}.
	 * @param ctx the parse tree
	 */
	void exitVariable_definition(Mx_languageParser.Variable_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#class_definition}.
	 * @param ctx the parse tree
	 */
	void enterClass_definition(Mx_languageParser.Class_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#class_definition}.
	 * @param ctx the parse tree
	 */
	void exitClass_definition(Mx_languageParser.Class_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition(Mx_languageParser.Function_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition(Mx_languageParser.Function_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#common_type}.
	 * @param ctx the parse tree
	 */
	void enterCommon_type(Mx_languageParser.Common_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#common_type}.
	 * @param ctx the parse tree
	 */
	void exitCommon_type(Mx_languageParser.Common_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#variable_type}.
	 * @param ctx the parse tree
	 */
	void enterVariable_type(Mx_languageParser.Variable_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#variable_type}.
	 * @param ctx the parse tree
	 */
	void exitVariable_type(Mx_languageParser.Variable_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(Mx_languageParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(Mx_languageParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code block_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlock_stmt(Mx_languageParser.Block_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code block_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlock_stmt(Mx_languageParser.Block_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code var_def_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVar_def_stmt(Mx_languageParser.Var_def_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code var_def_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVar_def_stmt(Mx_languageParser.Var_def_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code if_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(Mx_languageParser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code if_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(Mx_languageParser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code for_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_stmt(Mx_languageParser.For_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code for_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitFor_stmt(Mx_languageParser.For_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code while_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stmt(Mx_languageParser.While_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code while_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stmt(Mx_languageParser.While_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExpr_stmt(Mx_languageParser.Expr_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExpr_stmt(Mx_languageParser.Expr_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continue_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterContinue_stmt(Mx_languageParser.Continue_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continue_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitContinue_stmt(Mx_languageParser.Continue_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code break_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBreak_stmt(Mx_languageParser.Break_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code break_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBreak_stmt(Mx_languageParser.Break_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code return_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stmt(Mx_languageParser.Return_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code return_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stmt(Mx_languageParser.Return_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blank_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlank_stmt(Mx_languageParser.Blank_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blank_stmt}
	 * labeled alternative in {@link Mx_languageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlank_stmt(Mx_languageParser.Blank_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#if_block}.
	 * @param ctx the parse tree
	 */
	void enterIf_block(Mx_languageParser.If_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#if_block}.
	 * @param ctx the parse tree
	 */
	void exitIf_block(Mx_languageParser.If_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#while_block}.
	 * @param ctx the parse tree
	 */
	void enterWhile_block(Mx_languageParser.While_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#while_block}.
	 * @param ctx the parse tree
	 */
	void exitWhile_block(Mx_languageParser.While_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#for_block}.
	 * @param ctx the parse tree
	 */
	void enterFor_block(Mx_languageParser.For_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#for_block}.
	 * @param ctx the parse tree
	 */
	void exitFor_block(Mx_languageParser.For_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(Mx_languageParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(Mx_languageParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code aref_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAref_expr(Mx_languageParser.Aref_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code aref_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAref_expr(Mx_languageParser.Aref_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logical_or_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_or_expr(Mx_languageParser.Logical_or_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logical_or_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_or_expr(Mx_languageParser.Logical_or_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code member_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMember_expr(Mx_languageParser.Member_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code member_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMember_expr(Mx_languageParser.Member_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binary_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinary_expr(Mx_languageParser.Binary_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binary_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinary_expr(Mx_languageParser.Binary_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logical_and_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_and_expr(Mx_languageParser.Logical_and_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logical_and_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_and_expr(Mx_languageParser.Logical_and_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffix_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSuffix_expr(Mx_languageParser.Suffix_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffix_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSuffix_expr(Mx_languageParser.Suffix_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code new_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNew_expr(Mx_languageParser.New_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code new_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNew_expr(Mx_languageParser.New_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssign_expr(Mx_languageParser.Assign_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssign_expr(Mx_languageParser.Assign_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefix_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefix_expr(Mx_languageParser.Prefix_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefix_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefix_expr(Mx_languageParser.Prefix_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcall_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncall_expr(Mx_languageParser.Funcall_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcall_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncall_expr(Mx_languageParser.Funcall_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primary_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_expr(Mx_languageParser.Primary_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primary_expr}
	 * labeled alternative in {@link Mx_languageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_expr(Mx_languageParser.Primary_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mx_languageParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void enterExpression_list(Mx_languageParser.Expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mx_languageParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void exitExpression_list(Mx_languageParser.Expression_listContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sub_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterSub_expr(Mx_languageParser.Sub_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sub_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitSub_expr(Mx_languageParser.Sub_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code this_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterThis_expr(Mx_languageParser.This_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code this_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitThis_expr(Mx_languageParser.This_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variable_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterVariable_expr(Mx_languageParser.Variable_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variable_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitVariable_expr(Mx_languageParser.Variable_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literal_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_expr(Mx_languageParser.Literal_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literal_expr}
	 * labeled alternative in {@link Mx_languageParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_expr(Mx_languageParser.Literal_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterInt_const(Mx_languageParser.Int_constContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitInt_const(Mx_languageParser.Int_constContext ctx);
	/**
	 * Enter a parse tree produced by the {@code string_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterString_const(Mx_languageParser.String_constContext ctx);
	/**
	 * Exit a parse tree produced by the {@code string_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitString_const(Mx_languageParser.String_constContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bool_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterBool_const(Mx_languageParser.Bool_constContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bool_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitBool_const(Mx_languageParser.Bool_constContext ctx);
	/**
	 * Enter a parse tree produced by the {@code null_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterNull_const(Mx_languageParser.Null_constContext ctx);
	/**
	 * Exit a parse tree produced by the {@code null_const}
	 * labeled alternative in {@link Mx_languageParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitNull_const(Mx_languageParser.Null_constContext ctx);
	/**
	 * Enter a parse tree produced by the {@code error_creator}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterError_creator(Mx_languageParser.Error_creatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code error_creator}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitError_creator(Mx_languageParser.Error_creatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code array_creator}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterArray_creator(Mx_languageParser.Array_creatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code array_creator}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitArray_creator(Mx_languageParser.Array_creatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constructor}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(Mx_languageParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constructor}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(Mx_languageParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code single_creator}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterSingle_creator(Mx_languageParser.Single_creatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code single_creator}
	 * labeled alternative in {@link Mx_languageParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitSingle_creator(Mx_languageParser.Single_creatorContext ctx);
}