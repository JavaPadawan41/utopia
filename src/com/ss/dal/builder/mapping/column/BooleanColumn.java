package com.ss.dal.builder.mapping.column;

import java.util.function.BiConsumer;

import com.ss.utopia.model.IModel;

public final class BooleanColumn<M extends IModel> extends ColumnMap<M, Boolean>
{
	
	public BooleanColumn(BiConsumer<M, Boolean> setter)
	{
		super(setter, Boolean.class);
	}
	
	@Override
	public void accept(M t, Boolean u)
	{
		this.setter.accept(t, u);
	}
}


