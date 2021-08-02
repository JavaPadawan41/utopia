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
import org.junit.BeforeClass;
import org.junit.Test;
import com.ss.dal.factory.BookingPaymentFactory;
import com.ss.utopia.dal.BookingPaymentRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.BookingPayment;
import com.ss.utopia.model.User;



public class TestBookingPaymentRepository
{
	private static Connection con = null;
	private static BookingPaymentRepository repo;
	private static BookingPaymentFactory factory;
	
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
			factory = BookingPaymentFactory.getInstance();
		}
		
		repo = new BookingPaymentRepository(con, factory);
		
	}
	
	@Test
	public void testInsert() throws SQLException
	{
		
		BookingPayment entity = factory.createNew();
		
		//Test that insertion of null pK fails
		assertThrows(NullPointerException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of null fields wth valid PK fails
		entity.setBooking(new Booking());
		entity.getBooking().setId(1);
		assertThrows(NullPointerException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of duplicate PK fails
		entity.setRefunded(Boolean.FALSE);
		entity.setStripeId("asdfasdf");
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of invalid user fails
		entity.getBooking().setId(99);
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of entity with nulls fails
		entity.getBooking().setId(10);
		entity.setRefunded(null);
		entity.setStripeId(null);
		
		assertThrows(NullPointerException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test that insertion of valid user succeeds
		entity.setRefunded(Boolean.FALSE);
		entity.setStripeId("asdfasdfasdf");
		assertDoesNotThrow(() -> repo.insert(entity));
		con.rollback();
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		BookingPayment newEntity = factory.createNew();
		
		//Test that cannot perform  update with a new (keyless) entity
		assertThrows(NullPointerException.class, () -> repo.update(newEntity));
		con.rollback();
		
		//Test that update with invalid PK does nothing
		newEntity.setBooking(new Booking());
		newEntity.getBooking().setId(11);
		newEntity.setRefunded(Boolean.FALSE);
		newEntity.setStripeId("asdfasdf");
		
		assertEquals(0, repo.update(newEntity).intValue());
		con.rollback();
		
		//Test that update with valid data succeeds
		newEntity.getBooking().setId(2);
		assertEquals(1, repo.update(newEntity).intValue());
		con.rollback();
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		BookingPayment a = factory.createNew();
		a.setBooking(new Booking());

		
		//Test that deletion of null entity fails
		assertThrows(NullPointerException.class, () -> repo.delete(a));
		con.rollback();
		
		//Test that deletion of a non-existent entity fails
		a.getBooking().setId(99);
		assertEquals(repo.delete(a).intValue(), 0);

		
		//Test that deletion of an existing entity succeeds
		a.getBooking().setId(4);
		
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
			BookingPayment a = repo.get(4);
			assertEquals(a.getStripeId(), "lpTAunTHGjLiobByGi");
			
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
				List<BookingPayment> apt = repo.getAll();
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
