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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.ss.dal.factory.UserFactory;
import com.ss.utopia.dal.UserRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.User;
import com.ss.utopia.model.User;



public class TestUserRepository
{
	private static Connection con = null;
	private static UserRepository repo;
	private static UserFactory factory;
	
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
			factory = UserFactory.getInstance();
		}
		
		repo = new UserRepository(con, factory);
		
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
		
		User r = factory.createNew();
		
		//Test that insertion of null fields
		assertThrows(NullPointerException.class, () -> repo.insert(new User()));
		con.rollback();
		
		//Test that insertion of bad PK fails
		r.getRole().setId(99);
		r.setFamilyName("TEST");
		r.setGivenName("TEST");
		r.setEmail("TEST");
		r.setPassword("TEST");
		r.setPhone("TEST");
		
		assertThrows(SQLException.class, () -> repo.insert(r));
		con.rollback();
		
		//Test that insertion of a valid entity succeeds
		assertDoesNotThrow(() -> 
		{
			User u3 = factory.createNew();
			u3.getRole().setId(1);
			u3.setFamilyName("TEST");
			u3.setGivenName("TEST");
			u3.setEmail("TEST");
			u3.setPassword("TEST");
			u3.setUserName("TEST");
			u3.setPhone("TEST");
			repo.insert(u3);
			con.rollback();
		});
		
		
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		User u = factory.createNew();
		final User r2 = repo.get(1);
		PreparedStatement ps;
		ResultSet rs;
		String originalId;
		
		//Test that cannot perform  update with a new (keyless) entity
		assertThrows(NullPointerException.class, () -> repo.update(factory.createNew()).intValue());
		con.rollback();
		
		u = repo.get(1);
		
		//Test that update with invalid FK fails
		r2.getRole().setId(99);
		assertThrows(SQLException.class, () -> repo.update(r2));
		con.rollback();

		
		//Test that update with valid data succeeds
		u = repo.get(1);
		u.setGivenName("TEST");
		
		assertEquals(1, repo.update(u).intValue());
		ps = con.prepareStatement("SELECT given_name FROM User WHERE id = ?");
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
		User r = factory.createNew();

		
		//Test that deletion of non-existent does nothing
		assertThrows(NullPointerException.class,  () -> repo.delete(r).intValue());
		con.rollback();
		
		//Test that deletion of bad PK does nothing
		r.setId(9999);
		assertEquals(0, repo.delete(r).intValue());
		con.rollback();
		

		//Test that deletion of an existing entity succeeds
		r.setId(1);
		
		assertEquals(1, repo.delete(r).intValue());
		con.rollback();
	}
	
	@Test
	public void testGet() throws SQLException
	{
		
		//Test fetching a single entity by id
		assertDoesNotThrow(() -> 
		{
			User u = repo.get(1);
			assertEquals(u.getGivenName(), "Reed");
			assertEquals(u.getRole().getId().intValue(), 1);
			con.rollback();
		});
		
		//Test retrieving an invalid instance yields null
		User b = repo.get(99);
		assertNull(b);
		
	}
}
