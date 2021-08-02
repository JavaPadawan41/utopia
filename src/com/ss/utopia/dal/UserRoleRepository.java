package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.UserRoleFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.UserRole;


public class UserRoleRepository extends RepositoryBase<UserRole>
{
	private static final String selectAllQuery = "SELECT id, name FROM user_role ";
	private static final String getQuery = selectAllQuery + " WHERE id = ?";
	private static final String insertQuery = "INSERT INTO user_role (name) VALUES (?)";
	private static final String updateQuery = "UPDATE user_role SET name = ? WHERE id = ?";
	private static final String deleteQuery = "DELETE FROM user_role WHERE id = ?";
	
	/***
	 * Creates a new instance of UserRoleRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public UserRoleRepository(Connection c, UserRoleFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public UserRole get(Object id, Object...params) throws SQLException
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
	public UserRole insert(UserRole entity) throws SQLException
	{
		UserRole inserted = UserRoleFactory.getInstance().createNew();
		Integer newId = super.upsert(insertQuery, Statement.RETURN_GENERATED_KEYS, entity.getName());
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
	public Integer update(UserRole entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getName(), entity.getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(UserRole entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<UserRole>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<UserRole> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<UserRole> mapMany(ResultSet r) throws SQLException
	{
		List<UserRole> results = new ArrayList<UserRole>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final UserRole mapResult(ResultSet r) throws SQLException
	{
		UserRole at = this.factory.fromResultSet(r);
		
		return at;
	}
}
