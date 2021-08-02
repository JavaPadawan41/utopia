package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.RouteFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.Route;


public class RouteRepository extends RepositoryBase<Route>
{
	private static final String selectAllQuery = "WITH origin(route_id, origin_iata_id, origin_city)   " + 
			"AS (" + 
			"SELECT route.id, airport.iata_id, airport.city   " + 
			"FROM airport   " + 
			"JOIN route on airport.iata_id = route.origin_id  " + 
			"),   " + 
			"destination (route_id, dest_iata_id, dest_city)   " + 
			"AS  ( " + 
			"SELECT route.id, airport.iata_id, airport.city   " + 
			"FROM airport   " + 
			"JOIN route on airport.iata_id = route.destination_id " + 
			")   " + 
			"SELECT origin.route_id, origin.origin_iata_id, origin.origin_city,   " + 
			"destination.dest_iata_id, destination.dest_city   " + 
			"FROM origin   " + 
			"JOIN destination ON origin.route_id = destination.route_id ";
	private static final String getQuery = selectAllQuery + " WHERE origin.route_id = ?";
	private static final String insertQuery = "INSERT INTO route (origin_id, destination_id) VALUES (?, ?)";
	private static final String updateQuery = "UPDATE route SET origin_id = ?, destination_id = ? WHERE id = ?";
	private static final String deleteQuery = "DELETE FROM route WHERE id = ?";
	
	/***
	 * Creates a new instance of RouteRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public RouteRepository(Connection c, RouteFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public Route get(Object id, Object...args) throws SQLException
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
	public Route insert(Route entity) throws SQLException
	{
		Route inserted = new Route();
		Integer newId = super.upsert(insertQuery, Statement.RETURN_GENERATED_KEYS, entity.getOrigin().getIataId(), entity.getDestination().getIataId());
		inserted.setId(newId);	
		
		return inserted;
	}
	
	/***
	 * Updates the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(Route entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getOrigin().getIataId(), entity.getDestination().getIataId(), entity.getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(Route entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<Route>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<Route> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<Route> mapMany(ResultSet r) throws SQLException
	{
		List<Route> results = new ArrayList<Route>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final Route mapResult(ResultSet r) throws SQLException
	{
		Route at = this.factory.fromResultSet(r);
		
		return at;
	}
}
