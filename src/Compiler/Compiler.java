/*
*
* Mx*-Compiler, a project of ACM class 2018
* Work in Spring 2020
* Version : 0.1
* Author : Tong, CHEN
*
* */
package Compiler;

import AST.AST;
import AST.ASTBuilder;
import BackEnd.*;
import Entity.*;
import ExceptionS.*;
import FrontEnd.LexerErrorListener;
import FrontEnd.ParserErrorListener;
import Parser.*;
import Utils.*;
import org.antlr.v4.runtime.*;

import java.io.*;
import java.lang.Exception;
import java.util.LinkedList;
import java.util.List;

import Type.*;

import static Type.Type.*;
import static Utils.LibFunction.LIB_PREFIX;
import static java.lang.System.exit;

public class Compiler {
    static private InputStream is;
    static private String input_file = null;
    static private String output_file = null;

    static public void display_help()
    {
        System.out.println("==== Help ====");
        System.out.println("if you only want semantic check, plese use \"./semantic.bash [-i <filename>]\" ");
        System.out.println("if you want compile without optimize, use \"./codegen.bash [-i <filename>] [-o <filename>]\"");
        System.out.println("if you want compile with few optimizes, use \"./optim.bash [-i <filename>] [-o <filename>]\"");
    }

    static public void main(String[] args) throws Exception {
        for (int i = 0; i < args.length; ++i)
        {
            switch (args[i])
            {
                case "-i":
                    if (i + 1 >= args.length)
                        break;
                    else input_file = args[++i];
                    break;
                case "-o":
                    if (i + 1 >= args.length)
                        break;
                    else output_file = args[++i];
                    break;
                case "--help":
                    display_help();
                case "--ir-output":
                    Defines.Output_Tree_IR = true;
            }
        }
        if (input_file == null) is = System.in;
        else is = new FileInputStream(input_file);

        PrintStream os;
        if (output_file == null) os = System.out;
        else os = new PrintStream(new FileOutputStream(output_file));

        try {
            compile(os);
        }
        catch (SemanticError | InternalErrorS error)
        {
            System.err.println(error.getMessage());
            exit(1);
        } catch (Exception error)
        {
            error.printStackTrace();
            exit(1);
        }
        exit(0);
    }

    static private void compile(PrintStream os) throws Exception {
        Mx_languageLexer lexer = new Mx_languageLexer(CharStreams.fromStream((is)));
        lexer.addErrorListener(new LexerErrorListener());
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Mx_languageParser parser = new Mx_languageParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new ParserErrorListener());

        ASTBuilder builder = new ASTBuilder(parser.prog());
        AST ast = builder.getAst();
        ast.load_library(getSystemFunc());
        Type.initialize_builtin_type();
        ast.checkSymbolTable();
        ast.checkType();

        IRBuilder irBuilder = new IRBuilder(ast);
        irBuilder.generate_IR();

        InstructionEmitter emitter = new InstructionEmitter(irBuilder);

         ControlFlowAnalyzer cfg_builder = new ControlFlowAnalyzer(emitter);
         cfg_builder.build_cf();

        RegisterConfig registerConfig = new RegisterConfig();
        Allocator allocator = new Allocator(emitter, registerConfig);
        allocator.allocate();

        Translator translator = new Translator(emitter, registerConfig);
    }

    private static List<Entity> getSystemFunc() {
        List<Entity> func = new LinkedList<>();
        func.add(new LibFunction(voidType, "print", "print", new Type[]{stringType}).getEntity());
        func.add(new LibFunction(voidType, "println", "puts", new Type[]{stringType}).getEntity());
        func.add(new LibFunction(stringType, "getString", null).getEntity());
        func.add(new LibFunction(integerType, "getInt", null).getEntity());
        func.add(new LibFunction(stringType, "toString", new Type[]{integerType}).getEntity());
        func.add(new LibFunction(integerType,   LIB_PREFIX + "printInt", LIB_PREFIX + "printInt", new Type[]{integerType}).getEntity());
        func.add(new LibFunction(integerType,  LIB_PREFIX + "printlnInt", LIB_PREFIX + "printlnInt", new Type[]{integerType}).getEntity());
        func.add(new LibFunction(integerType,  LIB_PREFIX + "malloc", LIB_PREFIX + "malloc",new Type[]{integerType}).getEntity());
        func.add(new VariableEntity("null", null, nullType, null));
        return func;
    }
}