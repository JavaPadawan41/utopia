package com.ss.dal.builder.mapping.table.base;


import java.util.HashMap;
import java.util.Map;

import com.ss.dal.builder.mapping.column.IColumnMap;
import com.ss.utopia.model.IModel;

public abstract class TableMap<T extends IModel>
{
	protected final Map<String, IColumnMap<T, ?>> lookup;
	
	protected TableMap()
	{
		lookup = new HashMap<String, IColumnMap<T, ?>>();
	}
	
	@SuppressWarnings("unchecked")
	public <T2> void setProperty(String sqlColumn, T instance, T2 value)
	{
		if (!this.lookup.containsKey(sqlColumn))
			throw new IllegalArgumentException("column " + sqlColumn + " not found in lookup");
		
		IColumnMap<T, T2> setter = (IColumnMap<T, T2>)this.lookup.get(sqlColumn);
		setter.accept(instance, value);
	}
	
	
	public <T2> Class<?> getCastType(String sqlColumn)
	{
		if (!this.lookup.containsKey(sqlColumn))
			throw new IllegalArgumentException("column " + sqlColumn + " not found in lookup");
		
		IColumnMap<T, ?> setter = (IColumnMap<T, ?>)this.lookup.get(sqlColumn);
		return setter.getCastType();
	}
}
