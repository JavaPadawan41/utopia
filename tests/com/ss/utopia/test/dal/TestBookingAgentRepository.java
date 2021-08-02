package com.ss.utopia.test.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ss.dal.factory.BookingAgentFactory;
import com.ss.utopia.dal.BookingAgentRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.BookingAgent;
import com.ss.utopia.model.User;



public class TestBookingAgentRepository
{
	private static Connection con = null;
	private static BookingAgentRepository repo;
	private Integer maxCapacity = 999999;
	private static BookingAgentFactory factory;
	
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
			factory = BookingAgentFactory.getInstance();
		}
		
		repo = new BookingAgentRepository(con, factory);
		
	}
	
	@Test
	public void testInsert() throws SQLException
	{
		
		BookingAgent entity = factory.createNew();
		
		//Test that insertion of null pK fails
		assertThrows(NullPointerException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of duplicate PK fails
		entity.setAgent(new User());
		entity.setBooking(new Booking());
		entity.getAgent().setId(1);
		entity.getBooking().setId(1);
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of invalid user fails
		entity.getAgent().setId(99);
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of a valid user succeeds
		entity.getAgent().setId(2);
		entity.getBooking().setId(10);
		assertDoesNotThrow(() -> repo.insert(entity));
		con.rollback();
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		BookingAgent newEntity = factory.createNew();
		
		//Test that cannot perform  update with a new (keyless) entity
		assertThrows(NullPointerException.class, () -> repo.update(newEntity));
		con.rollback();
		
		//Test that update with invalid PK does nothing
		newEntity.setAgent(new User());
		newEntity.setBooking(new Booking());
		newEntity.getAgent().setId(99);
		newEntity.getBooking().setId(11);
		
		assertEquals(0, repo.update(newEntity).intValue());
		con.rollback();
		
		//Test that update with valid data succeeds
		newEntity.getAgent().setId(2);
		newEntity.getBooking().setId(1);
		assertDoesNotThrow(() -> repo.update(newEntity));
		con.rollback();
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		BookingAgent a = factory.createNew();
		a.setAgent(new User());
		a.setBooking(new Booking());
		int rowsDeleted;
		PreparedStatement ps;
		ResultSet rs;
		
		//Test that deletion of non-existent entity fails
		assertThrows(NullPointerException.class, () -> repo.delete(a));
		con.rollback();

		
		//Test that deletion of an existing entity succeeds
		a.getBooking().setId(1);
		a.getAgent().setId(1);
		
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
			BookingAgent a = repo.get(1);
			assertEquals(a.getAgent().getId(), 1);
			
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
				List<BookingAgent> apt = repo.getAll();
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
