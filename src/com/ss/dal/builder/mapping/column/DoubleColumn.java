package com.ss.dal.builder.mapping.column;

import java.util.function.BiConsumer;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

import com.ss.utopia.model.IModel;

public final class DoubleColumn<M extends IModel> extends ColumnMap<M, Double>
{
	
	public DoubleColumn(BiConsumer<M, Double> setter)
	{
		super(setter, Double.class);
	}
	
	@Override
	public void accept(M t, Double u)
	{
		this.setter.accept(t, u);
	}
}


