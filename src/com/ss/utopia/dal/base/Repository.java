package com.ss.utopia.dal.base;

import java.sql.SQLException;
import java.util.List;

import com.ss.utopia.model.IModel;

public interface Repository<T extends IModel>
{
	public T get(Object id, Object...args) throws SQLException;
	public T insert(T entity)  throws SQLException;
	public Integer update(T entity) throws SQLException;
	public Integer delete(T entity) throws SQLException;
	public List<T> getAll() throws SQLException;
}
