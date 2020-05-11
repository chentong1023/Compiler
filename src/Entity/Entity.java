package Entity;

import AST.Location;
import Operand.Reference;
import Type.Type;

import java.util.HashSet;
import java.util.Set;

abstract public class Entity {
    protected Type type;
    private int offset; //

    private String name;
    private Location location;

    public Entity(String name, Location location, Type type)
    {
        this.name = name;
        this.location = location;
        this.type = type;
    }

    public int getOffset() { return offset;}

    public void setOffset(int offset) {this.offset = offset;}

    public Type getType() {return type;}

    public int getSize() {return type.getSize();}

    public String getName() {return name;}

    public Location getLocation() {return location; }

    private Reference reference;

    public Reference getReference()
    {
        return reference;
    }

    public void setReference(Reference reference)
    {
        this.reference = reference;
    }

    private boolean is_output_irrelevant = false;

    public boolean isIs_output_irrelevant()
    {
        return is_output_irrelevant;
    }

    public void setIs_output_irrelevant(boolean is_output_irrelevant)
    {
        this.is_output_irrelevant = is_output_irrelevant;
    }
}