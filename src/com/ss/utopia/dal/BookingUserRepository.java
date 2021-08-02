package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.BookingUserFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.BookingUser;


public class BookingUserRepository extends RepositoryBase<BookingUser>
{
	private static final String selectAllQuery = "SELECT t0.booking_id, t0.user_id " + 
			 "FROM booking_user t0 ";
	private static final String getQuery = selectAllQuery + " WHERE t0.booking_id = ?";
	private static final String insertQuery = "INSERT INTO booking_user (booking_id, user_id) VALUES (?, ?)";
	private static final String updateQuery = "UPDATE booking_user SET user_id = ? WHERE booking_id = ?";
	private static final String deleteQuery = "DELETE FROM booking_user WHERE booking_id = ?";
	
	/***
	 * Creates a new instance of BookingUserRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public BookingUserRepository(Connection c, BookingUserFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public BookingUser get(Object id, Object...params) throws SQLException
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
	public BookingUser insert(BookingUser entity) throws SQLException
	{
		BookingUser inserted = new BookingUser();
		super.upsert(insertQuery, 0, entity.getBooking().getId(), entity.getUser().getId());
		
		return inserted;
	}
	
	/***
	 * Updates the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(BookingUser entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getUser().getId(), entity.getBooking().getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(BookingUser entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getBooking().getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<BookingUser>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<BookingUser> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<BookingUser> mapMany(ResultSet r) throws SQLException
	{
		List<BookingUser> results = new ArrayList<BookingUser>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final BookingUser mapResult(ResultSet r) throws SQLException
	{
		BookingUser at = this.factory.fromResultSet(r);
		
		return at;
	}
}
