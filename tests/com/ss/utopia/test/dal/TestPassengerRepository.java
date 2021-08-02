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
import com.ss.dal.factory.PassengerFactory;
import com.ss.utopia.dal.PassengerRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.Passenger;
import com.ss.utopia.model.User;



public class TestPassengerRepository
{
	private static Connection con = null;
	private static PassengerRepository repo;
	private static PassengerFactory factory;
	
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
			factory = PassengerFactory.getInstance();
		}
		
		repo = new PassengerRepository(con, factory);
		
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
		
		Passenger p = factory.createNew();
		
		//Test that insertion of null fields
		assertThrows(NullPointerException.class, () -> repo.insert(new Passenger()));
		con.rollback();
		
		//Test that insertion of a valid entity succeeds
		assertDoesNotThrow(() -> 
		{
			Passenger b3 = factory.createNew();
			b3.setId(9999);
			b3.getBooking().setId(1);
			b3.setGivenName("First");
			b3.setFamilyName("First");
			b3.setDob(LocalDate.of(2000, 7, 1));
			b3.setGender("F");
			b3.setAddress("123 Test STreet");
			repo.insert(b3);
			con.rollback();
		});
		
		
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		Passenger p = factory.createNew();
		PreparedStatement ps;
		ResultSet r;
		
		//Test that cannot perform  update with a new (keyless) entity
		assertThrows(NullPointerException.class, () -> repo.update(factory.createNew()));
		con.rollback();
		
		p = repo.get(1);
		
		//Test that update with invalid PK does nothing
		p.setId(99999);
		
		assertEquals(0, repo.update(p).intValue());
		con.rollback();
		
		//Test that update with valid data succeeds
		p = repo.get(1);
		p.setGivenName("Toasty");
		
		assertEquals(1, repo.update(p).intValue());
		ps = con.prepareStatement("SELECT given_name FROM Passenger WHERE id = ?");
		ps.setObject(1, p.getId());
		r = ps.executeQuery();
		
		assertTrue(r.next());
		assertEquals("Toasty", p.getGivenName());
		
		con.rollback();
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		Passenger f = factory.createNew();
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
		f.setId(1);
		
		assertEquals(1, repo.delete(f).intValue());
		con.rollback();
	}
	
	@Test
	public void testGet() throws SQLException
	{
		
		//Test fetching a single entity by id
		assertDoesNotThrow(() -> 
		{
			Passenger p = repo.get(1);
			assertEquals(p.getBooking().getId().intValue(), 4);
			assertEquals(p.getGivenName(), "Victor");
			assertEquals(p.getFamilyName(), "Rackham");
			assertEquals(p.getGender(), "M");
		});
		
		//Test retrieving an invalid instance yields null
		Passenger b = repo.get(99);
		assertNull(b);
		
	}
}
