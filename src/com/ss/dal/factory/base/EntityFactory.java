package com.ss.dal.factory.base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.ss.utopia.model.IModel;

public interface EntityFactory<T extends IModel>
{
	public T fromResultSet(ResultSet r) throws SQLException;
	
	public abstract T createNew();
}
