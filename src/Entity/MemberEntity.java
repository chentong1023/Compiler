package Entity;

public class MemberEntity extends VariableEntity
{
	public MemberEntity(VariableEntity entity)
	{
		super(entity.getName(), entity.getLocation(), entity.getType(), entity.getInitializer());
	}
}