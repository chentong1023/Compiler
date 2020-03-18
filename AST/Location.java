package AST;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.TerminalNode;

public class Location {
    public int line, column;

    public Location() {line = -1; column = -1;}

    public Location(int _line, int _column) {line = _line; column = _column;}

    public Location(Token token) {line = token.getline(); column = token.getCharPositionInLine();}

    public Location(ParserRuleContext ctx) {this(ctx.start);}

    public Location(TerminalNode terminalNode) {this(terminalNode.getSymbol());}

    public int line() {return line;}

    public int column() {return column;}

    public String toString() {return "line" + line + ":" + column + " ";}
}