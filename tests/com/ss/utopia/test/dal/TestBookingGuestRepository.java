package com.ss.utopia.test.dal;

import static org.junit.Assert.assertEquals;
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
import com.ss.dal.factory.BookingGuestFactory;
import com.ss.utopia.dal.BookingGuestRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.BookingGuest;
import com.ss.utopia.model.User;



public class TestBookingGuestRepository
{
	private static Connection con = null;
	private static BookingGuestRepository repo;
	private static BookingGuestFactory factory;
	
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
			factory = BookingGuestFactory.getInstance();
		}
		
		repo = new BookingGuestRepository(con, factory);
		
	}
	
	@Test
	public void testInsert() throws SQLException
	{
		
		BookingGuest entity = factory.createNew();
		
		//Test that insertion of null pK fails
		assertThrows(NullPointerException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of duplicate PK fails
		entity.setBooking(new Booking());
		entity.getBooking().setId(7);
		entity.setEmail("asdfasdf");
		entity.setPhone("asdfasdf");
		
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of non-existing PK fails
		entity.getBooking().setId(99);
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of nulls fails
		entity.setEmail(null);
		entity.setPhone(null);
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of a valid user succeeds
		entity.getBooking().setId(1);
		entity.setEmail("asdfasdf");
		entity.setPhone("asdfasdf");
		assertDoesNotThrow(() -> repo.insert(entity));
		con.rollback();
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		BookingGuest newEntity = factory.createNew();
		PreparedStatement ps;
		ResultSet rs;
		
		//Test that cannot perform  update with a new (keyless) entity
		assertThrows(NullPointerException.class, () -> repo.update(newEntity));
		con.rollback();
		
		//Test that update with invalid PK does nothing
		newEntity.setBooking(new Booking());
		newEntity.getBooking().setId(99);
		newEntity.setEmail("asdfasdf");
		newEntity.setPhone("asdfasdf");
		assertEquals(0, repo.update(newEntity).intValue());
		con.rollback();
		
		//Test that update with valid data succeeds
		newEntity.getBooking().setId(10);
		assertDoesNotThrow(() -> repo.update(newEntity));
		
		ps = con.prepareStatement("SELECT contact_email, contact_phone FROM booking_guest WHERE booking_id = ?");
		ps.setObject(1, newEntity.getBooking().getId());
		rs = ps.executeQuery();
		
		assertTrue(rs.next());
		assertEquals(rs.getString(1), newEntity.getEmail());
		assertEquals(rs.getString(2), newEntity.getPhone());
		
		con.rollback();
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		BookingGuest a = factory.createNew();
		
		//Test that deletion of non-existent PK entity fails
		assertThrows(NullPointerException.class, () -> repo.delete(a));
		con.rollback();
		
		//Test that deletion of entity with invalid PK has no effect
		a.getBooking().setId(99);
		assertEquals(0, repo.delete(a).intValue());
		con.rollback();
		
		//Test that deletion of an existing entity succeeds
		a.getBooking().setId(10);
		
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
			BookingGuest a = repo.get(7);
			assertEquals(a.getPhone(), "555-521-1806");
			
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
				List<BookingGuest> apt = repo.getAll();
				assertEquals(apt.size(), 4);
			}
			);
		}
		finally
		{
			con.rollback();
		}
		
	}
}
