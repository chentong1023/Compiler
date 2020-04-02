package Entity;

import AST.Location;
import Type.Type;

public class ParameterEntity extends Entity {
    private boolean is_this = false;
    public void setIs_this() {this.is_this = true;}

    public boolean isIs_this()
    {
        return is_this;
    }

    public ParameterEntity(String name, Location location, Type type)
    {
        super(name, location, type);
    }
}