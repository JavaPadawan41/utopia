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
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.ss.dal.factory.FlightBookingFactory;
import com.ss.utopia.dal.FlightBookingRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.Flight;
import com.ss.utopia.model.FlightBooking;

import com.ss.utopia.model.User;



public class TestFlightBookingRepository
{
	private static Connection con = null;
	private static FlightBookingRepository repo;
	private static FlightBookingFactory factory;
	
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
			factory = FlightBookingFactory.getInstance();
		}
		
		repo = new FlightBookingRepository(con, factory);
		
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
		
		FlightBooking b = factory.createNew();
		
		//Test that insertion of null fields
		assertThrows(NullPointerException.class, () -> repo.insert(new FlightBooking()));
		con.rollback();
		
		//Test that insertion of duplicate PK fails
		b.getBooking().setId(1);
		b.getFlight().setId(181);		
		
		//Test that insertion of a valid user succeeds
		assertDoesNotThrow(() -> 
		{
			FlightBooking b3 = factory.createNew();
			b3.getBooking().setId(3);
			b3.getFlight().setId(371);	
			b3 = repo.insert(b3);
			assertNotNull(b3.getBooking());
			con.rollback();
		});
		
		
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		FlightBooking b = factory.createNew();
		
		//Test that cannot perform  update with a new (keyless) entity
		assertThrows(NullPointerException.class, () -> repo.update(b));
		con.rollback();
		
		//Test that update with invalid PK does nothing
		b.getBooking().setId(99);;
		b.getFlight().setId(99);
		
		assertEquals(0, repo.update(b).intValue());
		con.rollback();
		
		//Test that update with valid data succeeds
		b.getBooking().setId(1);;
		b.getFlight().setId(181);
		
		assertEquals(1, repo.update(b).intValue());
		con.rollback();
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		FlightBooking b = factory.createNew();
		PreparedStatement p;
		ResultSet r;

		
		//Test that deletion of non-existent entity fails
		assertThrows(NullPointerException.class, () -> repo.delete(b));
		con.rollback();

		//Test that deletion of an existing entity succeeds
		b.setFlight(new Flight());
		b.setBooking(new Booking());
		b.getBooking().setId(1);
		b.getFlight().setId(181);
		
		assertEquals(1, repo.delete(b).intValue());
		con.rollback();
	}
	
	@Test
	public void testGet() throws SQLException
	{
		
		//Test fetching a single entity by id
		assertDoesNotThrow(() -> 
		{
			FlightBooking b = repo.get(1);
			assertEquals(b.getFlight().getId(), 181);
		});
		
		//Test retrieving an invalid instance yields null
		FlightBooking b = repo.get(99);
		assertNull(b);
		
	}
	
	public void testGetAll() throws SQLException
	{
		//Test that get retrieves all members of the underlying table
		try
		{
			assertDoesNotThrow(() -> 
			{
				List<FlightBooking> apt = repo.getAll();
				assertEquals(apt.size(), 9);
			}
			);
		}
		finally
		{
			con.rollback();
		}
		
	}
}
