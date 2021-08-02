package com.ss.utopia.dal.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.IModel;

public abstract class RepositoryBase<T extends IModel> implements Repository<T>
{
	protected static Connection connection = null;
	protected final FactoryBase<T> factory;
	
	/***
	 * Creates a new instance of Repository base
	 * @param c java.sql.Connection object with which to connect to the db
	 * @throws SQLException if DB access error occurs
	 */
	public RepositoryBase(Connection c, FactoryBase<T> f) throws SQLException
	{
		if(f == null)
			throw new NullPointerException("f");
		
		this.factory = f;
		
		if (connection == null || connection.isClosed())
			connection = c;
	}

	public T get(String query, Object id, Object...args) throws SQLException
	{
		T entity = null;
		int i = 2;
		
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setObject(1, id);
		
		if (args != null)
			for (Object o: args)
			{
				ps.setObject(i++, o);
			}
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			entity = mapResult(rs);
		
		return entity;	

	}
	
	
	@Override
	public abstract T insert(T entity) throws SQLException;

	@Override
	public abstract Integer update(T entity) throws SQLException, NullPointerException;

	@Override
	public abstract Integer delete(T entity) throws SQLException;

	@Override
	public abstract List<T> getAll() throws SQLException;
	
	/***
	 * Performs an upsert operation using the provided statement and params
	 * @param statement the SQL statement to execute
	 * @param flag Logical OR of one or more java.sql.Statment flags
	 * @param params Parameters for the query
	 * @return PK of inserted entity if flag == Statement.RETURN_GENERATED_KEYS or number of rows affected otherwise
	 * @throws SQLException
	 * @throws NullPointerException if attempting to update or delete with null value on PK field
	 * @throws ClassNotFoundException
	 */
	protected Integer upsert(String statement, int flag, Object...params) throws SQLException, NullPointerException
	{
		Integer toReturn;
		Integer rowsAffected;
		PreparedStatement ps = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
		int i = 1;
		
		//Query needs to be parameterized before 
		if (params != null)
			for (Object o: params)
				ps.setObject(i++, o);
		
		//Get the rows affected in case that's what needs to be returned
		rowsAffected = ps.executeUpdate();
		
		//Determine whether to return generated keys or to return rows affected
		ResultSet results = ps.getGeneratedKeys();
		toReturn = flag == Statement.RETURN_GENERATED_KEYS && results.next() ? 
				results.getInt(1) : rowsAffected;
				
		return toReturn;
	}
	
	/***
	 * Returns all instances from executing query against the database
	 * @param query Query to execute
	 * @return List<T>
	 * @throws SQLException if dababase access error occurs, or if executed on closed connection
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException
	 */
	protected List<T> getAll(String query, Object...args) throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement(query);
		int i = 1;
		
		//Query needs to be parameterized before 
		if (args != null)
			for (Object o: args)
				ps.setObject(i++, o);
				
				
		ResultSet rs = ps.executeQuery();
		return mapMany(rs);
	}
	
	/***
	 * Gets the instance of T selected by query, identified by ID in the underlying table
	 * @param query The query to execute
	 * @param id the value for the PK in the underlying table
	 * @return T if entity found, null otherwise
	 * @throws SQLException if a database access error occursor this method is called on a closed connection
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	protected T get(String query, Object id) throws SQLException
	{
		T entity = null;
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setObject(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			entity = mapResult(rs);
		
		return entity;	
	}
	
	/***
	 * Performs Deletes entities using query, identified by id
	 * @param query the delete query to execute
	 * @param id the ID of the entity to delete
	 * @return Number of rows affected
	 * @throws SQLException If database access error occurs
	 */
	protected Integer delete(String query, Object id, Object...args) throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setObject(1, id);
		
		int i = 2;
		//Query needs to be parameterized before 
		if (args != null)
			for (Object o: args)
				ps.setObject(i++, o);
		
		
		return ps.executeUpdate();
	}
	
	protected abstract T mapResult(ResultSet r) throws SQLException;
	
	protected abstract List<T> mapMany(ResultSet r) throws SQLException;
}
