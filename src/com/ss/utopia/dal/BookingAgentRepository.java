package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.BookingAgentFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.BookingAgent;


public class BookingAgentRepository extends RepositoryBase<BookingAgent>
{
	private static final String selectAllQuery = "SELECT t0.booking_id, t0.agent_id, t1.is_active, t1.confirmation_code, " + 
												 "t2.role_id, t2.given_name, t2.family_name, t2.username, t2.email, t2.phone, " + 
												 "t3.name " + 
												 "FROM booking_agent t0 " + 
												 "LEFT JOIN booking t1 ON t0.booking_id = t1.id " + 
												 "INNER JOIN user t2 ON t0.agent_id = t2.id " + 
												 "INNER JOIN user_role t3 ON t2.role_id = t3.id";
	private static final String getByAgentQuery = selectAllQuery + " WHERE t0.agent_id = ?";
	private static final String getQuery = selectAllQuery + " WHERE t0.booking_id = ?";
	private static final String insertQuery = "INSERT INTO booking_agent (booking_id, agent_id) VALUES (?, ?)";
	private static final String updateAgentQuery = "UPDATE booking_agent SET agent_id = ? WHERE booking_id = ?";
	private static final String deleteQuery = "DELETE FROM booking_agent WHERE booking_id = ?";
	
	/***
	 * Creates a new instance of BookingAgentRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public BookingAgentRepository(Connection c, BookingAgentFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public BookingAgent get(Object booking_id, Object...args) throws SQLException
	{
		return super.get(getQuery, booking_id);

	}

	/***
	 * Inserts a new record into the airplane_type table
	 * @param entity Data carrier containing data for new record
	 * @return newly inserted entity
	 * @throws SQLException if database access error occurs
	 */
	@Override
	public BookingAgent insert(BookingAgent entity) throws SQLException
	{
		
		super.upsert(insertQuery, 0, entity.getBooking().getId(), entity.getAgent().getId());
		
		return entity;
	}
	
	/***
	 * Updates the agentId in the record associated with entity in the database
	 * @param entity the entity whose record is to be updated
	 * @return number of rows affected
	 * @throws SqlException if a database access error occurs
	 */
	@Override
	public Integer update(BookingAgent entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateAgentQuery, 0, entity.getAgent().getId(), entity.getBooking().getId());
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(BookingAgent entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getBooking().getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<BookingAgent>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<BookingAgent> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<BookingAgent> mapMany(ResultSet r) throws SQLException
	{
		List<BookingAgent> results = new ArrayList<BookingAgent>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final BookingAgent mapResult(ResultSet r) throws SQLException
	{
		BookingAgent at = this.factory.fromResultSet(r);
		
		return at;
	}
}
