package com.ss.dal.builder.mapping.column;

import java.util.function.BiConsumer;

import com.ss.utopia.model.IModel;

public final class IntegerColumn<M extends IModel> extends ColumnMap<M, Integer>
{
	
	public IntegerColumn(BiConsumer<M, Integer> setter)
	{
		super(setter, Integer.class);
	}
	
	@Override
	public void accept(M t, Integer u)
	{
		this.setter.accept(t, u);
	}
}


