package com.ss.dal.builder.mapping.column;

import java.util.function.BiConsumer;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

import com.ss.utopia.model.IModel;

public final class StringColumn<M extends IModel> extends ColumnMap<M, String>
{
	
	public StringColumn(BiConsumer<M, String> setter)
	{
		super(setter, String.class);
	}
	
	@Override
	public void accept(M t, String u)
	{
		this.setter.accept(t, u);
	}
	
	 @SuppressWarnings ("unchecked")
	 public Class<M> getTypeParameterClass(){
	        Type type = getClass().getGenericSuperclass();
	        ParameterizedType paramType = (ParameterizedType) type;
	        return (Class<M>) paramType.getActualTypeArguments()[0];
	    }
}


