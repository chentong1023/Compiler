package AST;

import java.io.PrintStream;

abstract public class Node {
    private String name = "";
    private Location location = new Location();

    public Node(String name, Location location)
    {
        if (name == null)
            name = "null";
        this.name = name;
        this.location = location;
    }
    public Location location()
    {
        return location;
    }
    public String name()
    {
        return name;
    }
    public void print(int offset)
    {
        String out = "";
        for (int i = 0; i < offset; ++i)
            out += " ";
        System.out.println(out + this.name() + " " + this.location().toString());
    }
    private boolean is_output_irrelevant = false;
    public boolean Is_output_irrelevant() {return is_output_irrelevant;}
    public void setIs_output_irrelevant(boolean is_output_irrelevant) {this.is_output_irrelevant = is_output_irrelevant;}
}