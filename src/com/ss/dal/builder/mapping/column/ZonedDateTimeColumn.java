package com.ss.dal.builder.mapping.column;

import java.util.function.BiConsumer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.lang.reflect.ParameterizedType;

import com.ss.utopia.model.IModel;

public final class ZonedDateTimeColumn<M extends IModel> extends ColumnMap<M, ZonedDateTime>
{
	
	public ZonedDateTimeColumn(BiConsumer<M, ZonedDateTime> setter)
	{
		super(setter, ZonedDateTime.class);
	}
	
	@Override
	public void accept(M t, ZonedDateTime u)
	{
		this.setter.accept(t, u);
	}
}


