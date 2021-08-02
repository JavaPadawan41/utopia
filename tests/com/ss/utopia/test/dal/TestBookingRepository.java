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
import org.junit.BeforeClass;
import org.junit.Test;
import com.ss.dal.factory.BookingFactory;
import com.ss.utopia.dal.BookingRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.User;



public class TestBookingRepository
{
	private static Connection con = null;
	private static BookingRepository repo;
	private static BookingFactory factory;
	
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
			factory = BookingFactory.getInstance();
		}
		
		repo = new BookingRepository(con, factory);
		
	}
	
	@Test
	public void testInsert() throws SQLException
	{
		
		Booking b = factory.createNew();
		
		//Test that insertion of null fields
		assertThrows(NullPointerException.class, () -> repo.insert(new Booking()));
		con.rollback();
				
		
		//Test that insertion of a valid user succeeds
		assertDoesNotThrow(() -> 
		{
			Booking b3 = factory.createNew();
			b3.setActive(true);
			b3.setConfirmationCode("TEST");
			b3 = repo.insert(b3);
			assertNotNull(b3.getId());
			con.rollback();
		});
		
		
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		Booking b = factory.createNew();
		
		//Test that cannot perform  update with a new (keyless) entity
		assertThrows(NullPointerException.class, () -> repo.update(b));
		con.rollback();
		
		//Test that update with invalid PK does nothing
		b.setId(99);
		b.setConfirmationCode("Toasty");
		b.setActive(false);
		
		assertEquals(0, repo.update(b).intValue());
		con.rollback();
		
		//Test that update with valid data succeeds
		b.setId(1);
		assertEquals(1, repo.update(b).intValue());
		con.rollback();
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		Booking b = factory.createNew();
		PreparedStatement p;
		ResultSet r;

		
		//Test that deletion of non-existent entity fails
		assertThrows(NullPointerException.class, () -> repo.delete(b));
		con.rollback();

		//Test that deletion of an existing entity succeeds
		b.setId(4);
		
		assertEquals(1, repo.delete(b).intValue());
		con.rollback();
		
		//Test that deletion cascades
		b.setId(1);
		assertDoesNotThrow(() -> repo.delete(b));
		
		p = con.prepareStatement("SELECT COUNT(*) FROM booking_agent WHERE booking_id = ?");
		p.setObject(1, b.getId());
		r = p.executeQuery();
		
		assertTrue(r.next());
		assertEquals(r.getInt(1), 0);
		r.close();
		
		p = con.prepareStatement("SELECT COUNT(*) FROM booking_payment WHERE booking_id = ?");
		p.setObject(1, b.getId());
		r = p.executeQuery();
		
		assertTrue(r.next());
		assertEquals(r.getInt(1), 0);
		r.close();
		con.rollback();
		
		b.setId(4);
		assertDoesNotThrow(() -> repo.delete(b));
		p = con.prepareStatement("SELECT COUNT(*) FROM booking_user WHERE booking_id = ?");
		p.setObject(1, b.getId());
		r = p.executeQuery();
		
		assertTrue(r.next());
		assertEquals(r.getInt(1), 0);
		r.close();
	}
	
	@Test
	public void testGet() throws SQLException
	{
		
		//Test fetching a single entity by id
		assertDoesNotThrow(() -> repo.get(1));
		
		//Assert that fields are properly set on retrieved instance
		final Booking b = repo.get(1);
		
		assertEquals(b.getBookingAgent().getId(), 1);
		assertNull(b.getBookingGuest().getEmail());
		assertThrows(NullPointerException.class, () -> b.getBookingUser().getId());
		assertEquals(b.getConfirmationCode(), "yldpVEoJjenCTadFRisAkWkPt");
		
		//Test that booking_guest and booking_user set as appropriate as well
		Booking b2 = repo.get(4);
		assertEquals(b2.getBookingUser().getId(), 2);
		
		b2 = repo.get(7);
		assertEquals(b2.getBookingGuest().getEmail(), "kGMVMLYTGV@example.com");
		
	}
	
	public void testGetAll() throws SQLException
	{
		//Test that get retrieves all members of the underlying table
		try
		{
			assertDoesNotThrow(() -> 
			{
				List<Booking> apt = repo.getAll();
				assertEquals(apt.size(), 10);
			}
			);
		}
		finally
		{
			con.rollback();
		}
		
	}
}
