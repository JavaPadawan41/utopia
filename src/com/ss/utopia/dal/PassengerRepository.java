package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.PassengerFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.Passenger;


public class PassengerRepository extends RepositoryBase<Passenger>
{
	private static final String selectAllQuery = "SELECT passenger.id, passenger.booking_id, passenger.given_name, passenger.family_name, " + 
											     "passenger.dob, passenger.gender, passenger.address " + 
											     "FROM passenger";
	private static final String getQuery = selectAllQuery + " WHERE passenger.id = ?";
	private static final String insertQuery = "INSERT INTO passenger (booking_id, given_name, family_name, dob, gender, address) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String updateQuery = "UPDATE passenger SET booking_id = ?, given_name = ?, family_name = ?, dob = ?, gender = ?, address = ? WHERE id = ?";
	private static final String deleteQuery = "DELETE FROM passenger WHERE id = ?";
	
	/***
	 * Creates a new instance of PassengerRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public PassengerRepository(Connection c, PassengerFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public Passenger get(Object id, Object...params) throws SQLException
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
	public Passenger insert(Passenger entity) throws SQLException
	{
		Passenger inserted = new Passenger();
		Integer newId = super.upsert(insertQuery, Statement.RETURN_GENERATED_KEYS, entity.getBooking().getId(), entity.getGivenName(), entity.getFamilyName(), entity.getDob(),
				entity.getGender(), entity.getAddress());
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
	public Integer update(Passenger entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getBooking().getId(), entity.getGivenName(), entity.getFamilyName(), entity.getDob(),
				entity.getGender(), entity.getAddress(), entity.getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(Passenger entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<Passenger>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<Passenger> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<Passenger> mapMany(ResultSet r) throws SQLException
	{
		List<Passenger> results = new ArrayList<Passenger>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final Passenger mapResult(ResultSet r) throws SQLException
	{
		Passenger at = this.factory.fromResultSet(r);
		
		return at;
	}
}
