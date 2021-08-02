package com.ss.utopia.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ss.dal.factory.UserFactory;
import com.ss.utopia.dal.base.RepositoryBase;
import com.ss.utopia.model.User;


public class UserRepository extends RepositoryBase<User>
{
	private static final String selectAllQuery = "SELECT `user`.id, `user`.role_id, `user`.given_name, `user`.family_name, `user`.username, `user`.password, `user`.email, `user`.phone " + 
												 "FROM `user` " + 
												 "JOIN user_role ON `user`.role_id = user_role.id ";
	private static final String getQuery = selectAllQuery + " WHERE `user`.id = ?";
	private static final String insertQuery = "INSERT INTO `user` (role_id, given_name, family_name, username, email, password, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String updateQuery = "UPDATE `user` SET role_id = ?, given_name = ?, family_name = ?, username = ?, email = ?, password = ?, phone = ? WHERE id = ?";
	private static final String deleteQuery = "DELETE FROM `user` WHERE id = ?";
	
	/***
	 * Creates a new instance of UserRepository
	 * @param c COnnection to the database
	 * @throws SQLException if Database access error occurs
	 */
	public UserRepository(Connection c, UserFactory factory) throws SQLException
	{
		super(c, factory);
	}

	@Override
	public User get(Object id, Object...args) throws SQLException
	{
		return super.get(getQuery, id);

	}

	/***
	 * Inserts a new record into the airplane_type table
	 * @param entity Data carrier containing data for new record
	 * @return newly inserted entity with PK set
	 * @throws SQLException if database access error occurs
	 */
	@Override
	public User insert(User entity) throws SQLException
	{
		User inserted = new User();
		Integer newId = super.upsert(insertQuery, Statement.RETURN_GENERATED_KEYS, 
				entity.getRole().getId(), entity.getGivenName(), entity.getFamilyName(), entity.getUserName(), entity.getEmail(),
				entity.getPassword(), entity.getPhone());
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
	public Integer update(User entity) throws SQLException, NullPointerException
	{
		return super.upsert(updateQuery, 0, entity.getRole().getId(), entity.getGivenName(), entity.getFamilyName(), entity.getUserName(), entity.getEmail(),
				entity.getPassword(), entity.getPhone(), entity.getId());	
	}
	
	/***
	 * Deletes the record associated with entity from the underlying store
	 * @param entity THe entity to be deleted
	 * @return The number of affected rows
	 * @throws SqlException if database access error occurs
	 */
	@Override
	public Integer delete(User entity) throws SQLException
	{
		return super.delete(deleteQuery, entity.getId());
	}

	/***
	 * Gets all records from the associated table
	 * @return List<User>
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<User> getAll() throws SQLException
	{
		return super.getAll(selectAllQuery);
	}

	@Override
	protected final List<User> mapMany(ResultSet r) throws SQLException
	{
		List<User> results = new ArrayList<User>();
		while (r.next())
		{
			results.add(mapResult(r));
		}
		
		return results;
	}
	
	@Override
	protected final User mapResult(ResultSet r) throws SQLException
	{
		User at = this.factory.fromResultSet(r);
		
		return at;
	}
}
