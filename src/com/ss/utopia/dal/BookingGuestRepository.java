package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.BookingGuestFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.BookingGuest;


public class BookingGuestRepository extends RepositoryBase<BookingGuest>
{
	private static final String selectAllQuery = "SELECT t0.booking_id, t0.contact_email, t0.contact_phone, t1.is_active, t1.confirmation_code " +
												 "FROM booking_guest t0 " + 
												 "LEFT JOIN booking t1 ON t0.booking_id = t1.id ";
	private static final String getByEmailQuery = selectAllQuery + " WHERE t0.contact_email = ?";
	private static final String getByPhoneQuery = selectAllQuery + " WHERE t0.contact_phone = ?";
	private static final String getQuery = selectAllQuery + " WHERE t0.booking_id = ?";
	private static final String insertQuery = "INSERT INTO booking_guest (booking_id, contact_email, contact_phone) VALUES (?, ?, ?)";
	private static final String updateQuery = "UPDATE booking_guest SET contact_email = ?, contact_phone = ? WHERE booking_id = ?";
	private static final String deleteQuery = "DELETE FROM booking_guest WHERE booking_id = ?";
	
	/***
	 * Creates a new instance of BookingGuestRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public BookingGuestRepository(Connection c, BookingGuestFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public BookingGuest get(Object id, Object...args) throws SQLException
	{
		return super.get(getQuery, id);

	}
	
	public List<BookingGuest> getByEmail(String email) throws SQLException
	{
		return super.getAll(getByEmailQuery, email);
	}
	
	public List<BookingGuest> getByPhone(String phone) throws SQLException
	{
		return super.getAll(getByPhoneQuery, phone);
	}

	/***
	 * Inserts a new record into the airplane_type table
	 * @param entity Data carrier containing data for new record
	 * @return newly inserted entity
	 * @throws SQLException if database access error occurs
	 */
	@Override
	public BookingGuest insert(BookingGuest entity) throws SQLException
	{
		super.upsert(insertQuery, 0, entity.getBooking().getId(), entity.getEmail(), entity.getPhone());
		
		return entity;
	}
	
	/***
	 * Updates the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(BookingGuest entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getEmail(), entity.getPhone(), entity.getBooking().getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(BookingGuest entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getBooking().getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<BookingGuest>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<BookingGuest> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<BookingGuest> mapMany(ResultSet r) throws SQLException
	{
		List<BookingGuest> results = new ArrayList<BookingGuest>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final BookingGuest mapResult(ResultSet r) throws SQLException
	{
		BookingGuest at = this.factory.fromResultSet(r);
		
		return at;
	}
}
