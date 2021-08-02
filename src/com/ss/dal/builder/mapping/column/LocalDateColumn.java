package com.ss.dal.builder.mapping.column;

import java.util.function.BiConsumer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.lang.reflect.ParameterizedType;

import com.ss.utopia.model.IModel;

public final class LocalDateColumn<M extends IModel> extends ColumnMap<M, LocalDate>
{
	
	public LocalDateColumn(BiConsumer<M, LocalDate> setter)
	{
		super(setter, LocalDate.class);
	}
	
	@Override
	public void accept(M t, LocalDate u)
	{
		this.setter.accept(t, u);
	}
}


