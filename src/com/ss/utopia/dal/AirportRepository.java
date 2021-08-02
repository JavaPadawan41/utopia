package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.AirportFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.Airport;


public class AirportRepository extends RepositoryBase<Airport>
{
	private static final String selectAllQuery = "SELECT iata_id, city FROM airport";
	private static final String getQuery = "SELECT iata_id, city FROM airport WHERE iata_id = ?";
	private static final String insertQuery = "INSERT INTO airport (iata_id, city) VALUES (?, ?)";
	private static final String updateQuery = "UPDATE airport SET city = ? WHERE iata_id = ?";
	private static final String deleteQuery = "DELETE FROM airport WHERE iata_id = ?";
	private static final String selectByCityQuery = "SELECT iata_id, city FROM airport WHERE city = ?";
	
	/***
	 * Creates a new instance of AirportRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public AirportRepository(Connection c, AirportFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public Airport get(Object id, Object...args) throws SQLException
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
	public Airport insert(Airport entity) throws SQLException
	{
		super.upsert(insertQuery, 0, entity.getIataId(), entity.getCity());	
		return entity;
	}
	
	/***
	 * Updates the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(Airport entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getCity(), entity.getIataId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(Airport entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getIataId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<Airport>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<Airport> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}
	
	public List<Airport> getByCity(String city) throws SQLException
	{
		return super.getAll(selectByCityQuery, city);
	}

	@Override
	protected final List<Airport> mapMany(ResultSet r) throws SQLException
	{
		List<Airport> results = new ArrayList<Airport>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final Airport mapResult(ResultSet r) throws SQLException
	{
		Airport at = this.factory.fromResultSet(r);
		
		return at;
	}
}
