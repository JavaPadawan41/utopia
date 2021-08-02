package com.ss.dal.builder.mapping.column;

import java.util.function.BiConsumer;
import com.ss.utopia.model.IModel;


public interface IColumnMap<M extends IModel, T> extends BiConsumer<M, T>
{
	public Class<T> getCastType();
}
