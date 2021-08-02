package com.ss.dal.builder.mapping.column;

import java.util.function.BiConsumer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.lang.reflect.ParameterizedType;

import com.ss.utopia.model.IModel;

public final class LocalDateTimeColumn<M extends IModel> extends ColumnMap<M, LocalDateTime>
{
	
	public LocalDateTimeColumn(BiConsumer<M, LocalDateTime> setter)
	{
		super(setter, LocalDateTime.class);
	}
	
	@Override
	public void accept(M t, LocalDateTime u)
	{
		this.setter.accept(t, u);
	}
}


