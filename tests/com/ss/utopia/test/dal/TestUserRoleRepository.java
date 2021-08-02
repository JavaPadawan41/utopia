package com.ss.utopia.test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.ss.dal.factory.UserRoleFactory;
import com.ss.utopia.dal.UserRoleRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.UserRole;




public class TestUserRoleRepository
{
	private static Connection con = null;
	private static UserRoleRepository repo;
	private static UserRoleFactory factory;
	
	@BeforeClass
	public static void setUpClass() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException
	{
		String driver;
		String url;
		String uid;
		String pwd;
		
		try(InputStream in = new FileInputStream("resources/db.properties"))
		{
			Properties p = new Properties();
			p.load(in);
			driver = p.getProperty("driver");
			url = p.getProperty("url");
			uid = p.getProperty("uid");
			pwd = p.getProperty("pwd");
			
			Class.forName(driver);
			
			con = DriverManager.getConnection(url, uid, pwd);
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			factory = UserRoleFactory.getInstance();
		}
		
		repo = new UserRoleRepository(con, factory);
		
	}
	
	@AfterClass
	public static void tearDownClass() throws SQLException
	{
		con.rollback();
		con.close();	
	}
	
	@Test
	public void testInsert() throws SQLException
	{
		
		UserRole r = factory.createNew();
		
		//Test that insertion of null fields
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.insert(new UserRole()));
		con.rollback();
		
		//Test that insertion of bad data fails
		r.setName("Administrator");
		
		assertThrows(SQLException.class, () -> repo.insert(r));
		con.rollback();
		
		//Test that insertion of a valid entity succeeds
		assertDoesNotThrow(() -> 
		{
			UserRole u3 = factory.createNew();
			u3.setName("TEST");
			con.rollback();
		});
		
		
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		UserRole u = factory.createNew();
		final UserRole r2 = repo.get(1);
		PreparedStatement ps;
		ResultSet rs;
		String originalId;
		
		//Test that cannot perform  update with a new (keyless) entity
		assertEquals(0, repo.update(factory.createNew()).intValue());
		con.rollback();
		
		u = repo.get(1);
		
		//Test that update with invalid PK fails
		r2.setId(99);
		assertEquals(0, repo.update(r2).intValue());
		con.rollback();

		
		//Test that update with valid data succeeds
		u = repo.get(1);
		u.setName("TEST");
		
		assertEquals(1, repo.update(u).intValue());
		ps = con.prepareStatement("SELECT name FROM user_role WHERE id = ?");
		ps.setObject(1, u.getId());
		rs = ps.executeQuery();
		
		assertTrue(rs.next());
		assertEquals("TEST", rs.getString(1));
		
		con.rollback();
	}
	
	private void arssertThrows(Class<SQLException> class1, Object object)
	{
		// TODO Auto-generated method stub
		
	}

	@Test
	public void testDelete() throws SQLException
	{
		UserRole r = factory.createNew();

		
		//Test that deletion of non-existent does nothing
		assertEquals(0, repo.delete(r).intValue());
		con.rollback();
		
		//Test that deletion of bad PK does nothing
		r.setId(9999);
		assertEquals(0, repo.delete(r).intValue());
		con.rollback();
		

		//Test that cannot delete role with users attached
		r.setId(1);
		
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.delete(r).intValue());
		con.rollback();
	}
	
	@Test
	public void testGet() throws SQLException
	{
		
		//Test fetching a single entity by id
		assertDoesNotThrow(() -> 
		{
			UserRole u = repo.get(1);
			assertEquals(u.getName(), "user/agent");
			con.rollback();
		});
		
		//Test retrieving an invalid instance yields null
		UserRole b = repo.get(99);
		assertNull(b);
		
	}
}
