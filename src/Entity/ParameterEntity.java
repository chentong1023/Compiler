package Entity;

import AST.Location;
import Type.Type;

public class ParameterEntity extends Entity {
    public ParameterEntity(String name, Location location, Type type)
    {
        super(name, location, type);
    }
}