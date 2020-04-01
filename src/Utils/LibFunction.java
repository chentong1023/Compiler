package Utils;

import Entity.FunctionEntity;
import Entity.ParameterEntity;
import Type.Type;

import java.util.LinkedList;
import java.util.List;

public class LibFunction {
    public static final String LIB_PREFIX = "__lib_";

    private FunctionEntity entity;

    public LibFunction(Type return_Type, String name, Type[] paramTypes)
    {
        List<ParameterEntity> parameterEntityList = new LinkedList<>();
        if (paramTypes != null)
            for (Type paraType : paramTypes)
                parameterEntityList.add(new ParameterEntity(null, null, paraType));
        entity = new FunctionEntity(name, null, return_Type, parameterEntityList, null);
        entity.setIs_lib_function(true);
    }

    public FunctionEntity getEntity() {
        return entity;
    }
}