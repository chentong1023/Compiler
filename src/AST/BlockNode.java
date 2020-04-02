package AST;

import Entity.Scope;
import FrontEnd.ASTVisitor;

import java.util.LinkedList;
import java.util.List;

public class BlockNode extends StmtNode
{
	private List<StmtNode> stmts;

	public BlockNode(Location location, List<StmtNode> stmts)
	{
		super("Block", location);
		this.stmts = stmts;
	}

	public static BlockNode wrapBlock(StmtNode node)
	{
		if (node == null)
			return null;
		if (node instanceof BlockNode)
			return (BlockNode) node;
		else return new BlockNode(node.getLocation(), new LinkedList<StmtNode>()
		{{
			add(node);
		}});
	}

	public List<StmtNode> getStmts()
	{
		return stmts;
	}

	private Scope scope;

	public Scope getScope()
	{
		return scope;
	}

	public void setScope(Scope scope)
	{
		this.scope = scope;
	}

	@Override
	public <S, E> S accept(ASTVisitor<S, E> visitor)
	{
		return visitor.visit(this);
	}
}