package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.BookingPaymentFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.BookingPayment;


public class BookingPaymentRepository extends RepositoryBase<BookingPayment>
{
	private static final String selectAllQuery = "SELECT t0.booking_id, t0.stripe_id, t0.refunded " + 
												 "FROM booking_payment t0 ";
	private static final String getQuery = selectAllQuery + " WHERE t0.booking_id = ?";
	private static final String insertQuery = "INSERT INTO booking_payment (booking_id, stripe_id, refunded) VALUES (?, ?, ?)";
	private static final String updateQuery = "UPDATE booking_payment SET stripe_id = ?, refunded = ? WHERE booking_id = ?";
	private static final String deleteQuery = "DELETE FROM booking_payment WHERE booking_id = ?";
	
	/***
	 * Creates a new instance of BookingPaymentRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public BookingPaymentRepository(Connection c, BookingPaymentFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public BookingPayment get(Object bookingId, Object...args) throws SQLException
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
	public BookingPayment insert(BookingPayment entity) throws SQLException
	{

		super.upsert(insertQuery, 0, entity.getBooking().getId(), entity.getStripeId(), entity.isRefunded());

		
		return entity;
	}
	
	/***
	 * Updates the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(BookingPayment entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getStripeId(), entity.isRefunded(), entity.getBooking().getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(BookingPayment entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getBooking().getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<BookingPayment>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<BookingPayment> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<BookingPayment> mapMany(ResultSet r) throws SQLException
	{
		List<BookingPayment> results = new ArrayList<BookingPayment>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final BookingPayment mapResult(ResultSet r) throws SQLException
	{
		BookingPayment at = this.factory.fromResultSet(r);
		
		return at;
	}
}
