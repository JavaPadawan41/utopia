package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory. FlightBookingFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model. FlightBooking;


public class FlightBookingRepository extends RepositoryBase< FlightBooking>
{
	private static final String selectAllQuery = "SELECT t0.flight_id, t0.booking_id FROM flight_bookings t0 ";
	private static final String getQuery = selectAllQuery + " WHERE flight_bookings.booking_id = ?";
	private static final String insertQuery = "INSERT INTO flight_bookings (flight_id, booking_id) VALUES (?, ?)";
	private static final String updateQuery = "UPDATE flight_bookings SET flight_id = ?, booking_id = ? WHERE flight_id = ? AND booking_id = ?";
	private static final String deleteQuery = "DELETE FROM flight_bookings WHERE flight_id = ? AND booking_id = ?";
	
	/***
	 * Creates a new instance of  FlightBookingRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public FlightBookingRepository(Connection c,  FlightBookingFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public  FlightBooking get(Object bookingId, Object...params) throws SQLException
	{
		return super.get(getQuery, bookingId);
	}

	/***
	 * Inserts a new record into the airplane_type table
	 * @param entity Data carrier containing data for new record
	 * @return newly inserted entity
	 * @throws SQLException if database access error occurs
	 */
	@Override
	public  FlightBooking insert(FlightBooking entity) throws SQLException
	{

		super.upsert(insertQuery, 0, entity.getFlight().getId(), entity.getBooking().getId());

		return entity;
	}
	
	/***
	 * Updates the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(FlightBooking entity) throws SQLException, NullPointerException
	{
		throw new UnsupportedOperationException();	
	}
	
	public Integer update(FlightBooking oldFb, FlightBooking newFb) throws NullPointerException, SQLException
	{
		return super.upsert(updateQuery, 0, newFb.getFlight().getId(), newFb.getBooking().getId(), oldFb.getFlight().getId(), oldFb.getBooking().getId());
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete( FlightBooking entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getFlight().getId(), entity.getBooking().getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List< FlightBooking>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List< FlightBooking> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List< FlightBooking> mapMany(ResultSet r) throws SQLException
	{
		List< FlightBooking> results = new ArrayList< FlightBooking>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final  FlightBooking mapResult(ResultSet r) throws SQLException
	{
		 FlightBooking at = this.factory.fromResultSet(r);
		
		return at;
	}
}
