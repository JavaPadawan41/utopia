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
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.ss.dal.factory.FlightFactory;
import com.ss.dal.factory.UserFactory;
import com.ss.utopia.dal.FlightRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.Flight;
import com.ss.utopia.model.User;



public class TestFlightRepository
{
	private static Connection con = null;
	private static FlightRepository repo;
	private static FlightFactory factory;
	
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
			factory = FlightFactory.getInstance();
		}
		
		repo = new FlightRepository(con, factory);
		
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
		
		Flight b = factory.createNew();
		
		//Test that insertion of null fields
		assertThrows(NullPointerException.class, () -> repo.insert(new Flight()));
		con.rollback();
		
		//Test insertion of duplicate PK fails
		assertThrows(SQLException.class, () -> 
		{
			Flight b3 = factory.createNew();
			b3.setId(181);
			b3.setReservedSeats(0);
			b3.setDepartureTime(ZonedDateTime.now());
			b3.getRoute().setId(3);
			b3.getPlane().setId(1);
			b3.setSeatPrice(100);
			b3 = repo.insert(b3);
			assertNotNull(b3.getId());
			con.rollback();
		});
	
		
		//Test that insertion of a valid entity succeeds
		assertDoesNotThrow(() -> 
		{
			Flight b3 = factory.createNew();
			b3.setId(9999);
			b3.setReservedSeats(0);
			b3.setDepartureTime(ZonedDateTime.now());
			b3.getRoute().setId(3);
			b3.getPlane().setId(1);
			b3.setSeatPrice(100);
			b3 = repo.insert(b3);
			assertNotNull(b3.getId());
			con.rollback();
		});
		
		
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		Flight f = factory.createNew();
		PreparedStatement p;
		ResultSet r;
		
		//Test that cannot perform  update with a new (keyless) entity
		assertThrows(NullPointerException.class, () -> repo.update(factory.createNew()));
		con.rollback();
		
		f = repo.get(181);
		
		//Test that update with invalid PK does nothing
		f.setId(99999);
		
		assertEquals(0, repo.update(f).intValue());
		con.rollback();
		
		//Test that update with valid data succeeds
		f = repo.get(181);
		f.setReservedSeats(0);
		
		assertEquals(1, repo.update(f).intValue());
		p = con.prepareStatement("SELECT reserved_seats FROM flight WHERE id = ?");
		p.setObject(1, f.getId());
		r = p.executeQuery();
		
		assertTrue(r.next());
		assertEquals(r.getInt(1), f.getReservedSeats());
		
		con.rollback();
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		Flight f = factory.createNew();
		PreparedStatement p;
		ResultSet r;

		
		//Test that deletion of non-existent entity fails
		assertThrows(NullPointerException.class, () -> repo.delete(f));
		con.rollback();
		
		//Test that deletion of bad PK does nothing
		f.setId(9999);
		assertEquals(0, repo.delete(f).intValue());
		con.rollback();
		

		//Test that deletion of an existing entity succeeds
		f.setId(181);
		
		assertEquals(1, repo.delete(f).intValue());
		con.rollback();
	}
	
	@Test
	public void testGet() throws SQLException
	{
		
		//Test fetching a single entity by id
		assertDoesNotThrow(() -> 
		{
			Flight b = repo.get(181);
			assertEquals(b.getReservedSeats(), 21);
			assertEquals(b.getSeatPrice(), 206.66, 0.001);
			assertEquals(b.getRoute().getId().intValue(), 8);
			assertEquals(b.getPlane().getId().intValue(), 16);
		});
		
		//Test retrieving an invalid instance yields null
		Flight b = repo.get(99);
		assertNull(b);
		
	}
	
	@Test
	public void testGetAll() throws SQLException
	{
		//Test that get retrieves all members of the underlying table
		try
		{
			assertDoesNotThrow(() -> 
			{
				List<Flight> apt = repo.getAll();
				assertEquals(apt.size(), 10);
			}
			);
		}
		finally
		{
			con.rollback();
		}
	}
	
	@Test
	public void testGetByUser() throws SQLException
	{
		User u = UserFactory.getInstance().createNew();
		u.setId(2);
		List<Flight> managedFlights;
		
		//Test that all managed flights for valid user returned
		managedFlights = repo.getManagedFlights(u);
		
		assertEquals(managedFlights.size(), 3);
		
		//Test that no results returned for invalid suer
		u.setId(4);
		managedFlights = repo.getManagedFlights(u);
		
		assertEquals(managedFlights.size(), 0);
		
	}
}
