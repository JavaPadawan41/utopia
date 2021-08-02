package com.ss.dal.factory.base;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.IModel;

public abstract class FactoryBase<T extends IModel> implements EntityFactory<T>
{
	protected final TableMap<T> map;
	
	public FactoryBase(TableMap<T> map)
	{
		if (map == null)
			throw new NullPointerException("map");
		
		this.map = map;
			
	}
	
	abstract public T createNew();
		
	/***
	 * Constructs a new instance of T from the ResultSet r
	 * @param r ResultSet containing data from which to construct new instance
	 * @throws SQLException 
	 */
	public T fromResultSet(ResultSet r) throws SQLException
	{
		T instance = this.createNew();
		ResultSetMetaData meta = r.getMetaData();
		
		for (int i = 1; i <= meta.getColumnCount(); i++)
		{
			String key = String.format("%s.%s", meta.getTableName(i), meta.getColumnLabel(i));
			if (r.getObject(i) != null)
				this.map.setProperty(key, instance, r.getObject(i, this.map.getCastType(key)));
		}
		
		return instance;
	}
}
