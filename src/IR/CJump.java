package IR;

public class CJump extends IR
{
	private Expr condition;
	private Label true_label, false_label;

	public CJump(Expr condition, Label true_label, Label false_label)
	{
		this.condition = condition;
		this.true_label = true_label;
		this.false_label = false_label;
	}

	public Expr getCondition()
	{
		return condition;
	}

	public Label getTrue_label()
	{
		return true_label;
	}

	public Label getFalse_label()
	{
		return false_label;
	}

	@Override
	public String toString()
	{
		return "CJump : " + condition + " -> " + true_label + " | " + false_label;
	}
}
