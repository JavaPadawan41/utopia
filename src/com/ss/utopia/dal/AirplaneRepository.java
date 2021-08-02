package com.ss.utopia.dal;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.AirplaneFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.Airplane;
import com.ss.utopia.model.AirplaneType;


public class AirplaneRepository extends RepositoryBase<Airplane>
{
	private static final String selectAllQuery = "SELECT airplane.id, airplane.type_id, airplane_type.max_capacity " + 
												  "FROM airplane " + 
												  "JOIN airplane_type ON airplane.type_id = airplane_type.id";
	private static final String getQuery = selectAllQuery + " WHERE airplane.id = ?";
	private static final String insertQuery = "INSERT INTO airplane (type_id) VALUES (?)";
	private static final String updateQuery = "UPDATE airplane SET type_id = ? WHERE id = ?";
	private static final String deleteQuery = "DELETE FROM airplane WHERE id = ?";
	/***
	 * Creates a new instance of AirplaneRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public AirplaneRepository(Connection c, AirplaneFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public Airplane get(Object id, Object...args) throws SQLException
	{
		return super.get(getQuery, id);
	}

	/***
	 * Inserts a new record into the airplane_type table
	 * @param entity Data carrier containing data for new record
	 * @return newly inserted entity
	 * @throws SQLException if database access error occurs
	 */
	@Override
	public Airplane insert(Airplane entity) throws SQLException
	{
		Airplane inserted = new Airplane();
		Integer newId = super.upsert(insertQuery, Statement.RETURN_GENERATED_KEYS, entity.getType().getId());
		inserted.setId(newId);	
		inserted.setType(AirplaneType.copy(entity.getType()));;
		
		return inserted;
	}
	
	/***
	 * Updates the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(Airplane entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getType().getId(), entity.getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(Airplane entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<Airplane>
	 * @throws SQLException if a database access error occurs
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@Override
	public List<Airplane> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<Airplane> mapMany(ResultSet r) throws SQLException
	{
		List<Airplane> results = new ArrayList<Airplane>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final Airplane mapResult(ResultSet r) throws SQLException
	{
		Airplane at = this.factory.fromResultSet(r);
		
		return at;
	}
}
