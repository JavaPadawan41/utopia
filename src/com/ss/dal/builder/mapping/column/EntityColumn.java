package com.ss.dal.builder.mapping.column;

import java.util.function.BiConsumer;

import com.ss.utopia.model.IModel;

public abstract class EntityColumn<M extends IModel> extends ColumnMap<M, IModel>
{
	
	public EntityColumn(BiConsumer<M, IModel> setter)
	{
		super(setter, IModel.class);
	}
	
	@Override
	public void accept(M t, IModel u)
	{
		this.setter.accept(t, u);
	}
}


