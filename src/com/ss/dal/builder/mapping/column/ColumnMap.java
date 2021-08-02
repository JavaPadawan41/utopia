package com.ss.dal.builder.mapping.column;


import java.util.function.BiConsumer;

import com.ss.utopia.model.IModel;

public abstract class ColumnMap<M extends IModel, T> implements IColumnMap<M, T>
{
	protected BiConsumer<M, T> setter;
	protected Class<T> cast;

	
	public ColumnMap(BiConsumer<M, T> toEntity, Class<T> castType)
	{
		if (toEntity == null)
			throw new NullPointerException("toEntity");
		
		this.setter = toEntity;
		this.cast = castType;
	}
	
	public Class<T> getCastType()
	{
		return cast;
	}
}
