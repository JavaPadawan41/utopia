package com.ss.utopia.test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.ss.dal.factory.BookingUserFactory;
import com.ss.utopia.dal.BookingUserRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.BookingUser;
import com.ss.utopia.model.User;



public class TestBookingUserRepository
{
	private static Connection con = null;
	private static BookingUserRepository repo;
	private static BookingUserFactory factory;
	
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
			factory = BookingUserFactory.getInstance();
		}
		
		repo = new BookingUserRepository(con, factory);
		
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
		
		BookingUser entity = factory.createNew();
		
		//Test that insertion of null pK fails
		assertThrows(NullPointerException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of duplicate PK fails
		entity.setUser(new User());
		entity.setBooking(new Booking());
		entity.getUser().setId(4);
		entity.getBooking().setId(2);
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of invalid user fails
		entity.getUser().setId(99);
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of a valid user succeeds
		entity.getUser().setId(2);
		entity.getBooking().setId(10);
		assertDoesNotThrow(() -> repo.insert(entity));
		con.rollback();
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		BookingUser newEntity = factory.createNew();
		
		//Test that cannot perform  update with a new (keyless) entity
		assertThrows(NullPointerException.class, () -> repo.update(newEntity));
		con.rollback();
		
		//Test that update with invalid PK does nothing
		newEntity.setUser(new User());
		newEntity.setBooking(new Booking());
		newEntity.getUser().setId(99);
		newEntity.getBooking().setId(11);
		
		assertEquals(0, repo.update(newEntity).intValue());
		con.rollback();
		
		//Test that update with valid data succeeds
		newEntity.getUser().setId(2);
		newEntity.getBooking().setId(1);
		assertDoesNotThrow(() -> repo.update(newEntity));
		con.rollback();
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		BookingUser a = factory.createNew();
		a.setUser(new User());
		a.setBooking(new Booking());

		
		//Test that deletion of non-existent entity fails
		assertEquals(0, repo.delete(a).intValue());
		con.rollback();

		
		//Test that deletion of an existing entity succeeds
		a.getBooking().setId(4);
		a.getUser().setId(2);
		
		assertEquals(1, repo.delete(a).intValue());
		con.rollback();

	}
	
	@Test
	public void testGet() throws SQLException
	{
		
		//Test fetching a single entity by id
		try
		{
			//Test that valid entity is fetched
			BookingUser a = repo.get(4);
			assertEquals(a.getUser().getId(), 2);
			
			//Test that invalid entity returns null
			assertNull(repo.get(99999));
		}
		finally
		{
			con.rollback();
		}
	}
	
	public void testGetAll() throws SQLException
	{
		//Test that get retrieves all members of the underlying table
		try
		{
			assertDoesNotThrow(() -> 
			{
				List<BookingUser> apt = repo.getAll();
				assertEquals(apt.size(), 2);
			}
			);
		}
		finally
		{
			con.rollback();
		}
		
	}
}
