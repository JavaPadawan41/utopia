package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.BookingFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.Booking;


public class BookingRepository extends RepositoryBase<Booking>
{
	private static final String selectAllQuery = 
			"SELECT booking.id, booking.is_active, booking.confirmation_code, booking_agent.agent_id, booking_user.user_id, booking_guest.contact_email " + 
			"FROM booking " + 
			"LEFT JOIN booking_agent ON booking.id = booking_agent.booking_id " + 
			"LEFT JOIN booking_user ON booking.id = booking_user.booking_id " + 
			"LEFT JOIN booking_guest ON booking.id = booking_guest.booking_id";
	

	private static final String getQuery = selectAllQuery + " WHERE id = ?";
	private static final String insertQuery = "INSERT INTO booking (is_active, confirmation_code) VALUES (?, ?)";
	private static final String updateQuery = "UPDATE booking SET is_active = ?, confirmation_code = ? WHERE id = ?";
	private static final String deleteQuery = "DELETE FROM booking WHERE id = ?";
	
	/***
	 * Creates a new instance of BookingRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public BookingRepository(Connection c, BookingFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public Booking get(Object id, Object...params) throws SQLException
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
	public Booking insert(Booking entity) throws SQLException
	{
		Booking inserted = new Booking();
		Integer newId = super.upsert(insertQuery, Statement.RETURN_GENERATED_KEYS, entity.isActive(), entity.getConfirmationCode());
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
	public Integer update(Booking entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.isActive(), entity.getConfirmationCode(), entity.getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(Booking entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<Booking>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<Booking> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<Booking> mapMany(ResultSet r) throws SQLException
	{
		List<Booking> results = new ArrayList<Booking>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final Booking mapResult(ResultSet r) throws SQLException
	{
		Booking at = this.factory.fromResultSet(r);
		
		return at;
	}
}
