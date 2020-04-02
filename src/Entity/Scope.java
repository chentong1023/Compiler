package Entity;

import java.util.*;

import ExceptionS.*;

public class Scope {
    private Map<String, Entity> entities = new HashMap<>();
    private List<Scope> children = new ArrayList<>();
    private Scope parent;
    private boolean isTopLevel;

    public Scope(boolean isTopLevel) {this.isTopLevel = isTopLevel;}

    public Scope(Scope parent)
    {
        this.parent = parent;
        this.isTopLevel = (parent == null);
        if (this.parent != null)
            this.parent.add_child(this);
    }

    public void insert(Entity entity)
    {
        if (entities.containsKey(entity.getName()))
            throw new SemanticError(entity.getLocation(), "duplicated symbol : " + entity.getName());
        entities.put(entity.getName(), entity);
    }

    public Entity lookup(String name)
    {
        Entity entity = entities.get(name);
        if (entity == null)
            return isTopLevel ? null : parent.lookup(name);
        else return entity;
    }
    public Entity lookup_current_level(String name) {return entities.get(name);}

    public Map<String, Entity> getEntities() {
        return entities;
    }

    public List<Scope> getChildren() {
        return children;
    }

    public void add_child(Scope scope) {children.add(scope);}
    public Scope getParent() {return isTopLevel ? null : parent;}
    public boolean isTopLevel() {return isTopLevel;}

    public int locate_local_variable(int base, int align)
    {
        int offset = 0;
        for (Entity entity : entities.values())
        {
            if (entity instanceof VariableEntity)
            {
                offset += entity.type.getSize();
                entity.setOffset(offset + base);
                offset += (align - offset % align) % align;
            }
        }
        int maxi = 0;
        for (Scope child : children)
        {
            int tmp = child.locate_local_variable(base + offset, align);
            if (tmp > maxi) maxi = tmp;
        }
        return offset + maxi;
    }

    public int locate_member(int align)
    {
        int offset = 0;
        for (Entity entity: entities.values())
            if (!(entity instanceof FunctionEntity))
            {
                entity.setOffset(offset);
                offset += entity.getSize();
                offset += (align - offset % align) % align;
            }
        return offset;
    }
    public List<VariableEntity> all_local_variables() {
        List<VariableEntity> ret = new LinkedList<>();
        for (Entity entity : entities.values())
            if (entity instanceof VariableEntity)
                ret.add((VariableEntity) entity);
        for (Scope child : children)
            ret.addAll(child.all_local_variables());
        return ret;
    }
    public Set<Entity> gather_all()
    {
        Set<Entity> ret = new HashSet<> ();

        for (Entity entity : entities.values())
        {
            if (entity instanceof FunctionEntity)
                if (!((FunctionEntity) entity).is_lib_function())
                    ret.addAll(((FunctionEntity) entity).getParameterEntityList());
            ret.add(entity);
        }

        for (Scope child : children)
            ret.addAll(child.all_local_variables());
        return ret;
    }
}