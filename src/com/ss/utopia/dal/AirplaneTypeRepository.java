package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.AirplaneTypeFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.AirplaneType;


public class AirplaneTypeRepository extends RepositoryBase<AirplaneType>
{
	private static final String selectAllQuery = "SELECT id, max_capacity FROM airplane_type";
	private static final String getQuery = selectAllQuery + " WHERE id = ?";
	private static final String insertQuery = "INSERT INTO airplane_type (max_capacity) VALUES (?)";
	private static final String updateQuery = "UPDATE airplane_type SET max_capacity = ? WHERE id = ?";
	private static final String deleteQuery = "DELETE FROM airplane_type WHERE id = ?";
	
	/***
	 * Creates a new instance of AirplaneTypeRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public AirplaneTypeRepository(Connection c, AirplaneTypeFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public AirplaneType get(Object id, Object...args) throws SQLException
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
	public AirplaneType insert(AirplaneType entity) throws SQLException
	{
		AirplaneType inserted = new AirplaneType();
		Integer newId = super.upsert(insertQuery, Statement.RETURN_GENERATED_KEYS, entity.getMaxCapacity());
		inserted.setId(newId);	
		inserted.setMaxCapacity(entity.getMaxCapacity());
		
		return inserted;
	}
	
	/***
	 * Updates the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(AirplaneType entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getMaxCapacity(), entity.getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(AirplaneType entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<AirplaneType>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<AirplaneType> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<AirplaneType> mapMany(ResultSet r) throws SQLException
	{
		List<AirplaneType> results = new ArrayList<AirplaneType>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final AirplaneType mapResult(ResultSet r) throws SQLException
	{
		AirplaneType at = this.factory.fromResultSet(r);
		
		return at;
	}
}
