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
import com.ss.dal.factory.RouteFactory;
import com.ss.utopia.dal.RouteRepository;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.Route;
import com.ss.utopia.model.User;



public class TestRouteRepository
{
	private static Connection con = null;
	private static RouteRepository repo;
	private static RouteFactory factory;
	
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
			factory = RouteFactory.getInstance();
		}
		
		repo = new RouteRepository(con, factory);
		
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
		
		Route r = factory.createNew();
		
		//Test that insertion of null fields
		assertThrows(NullPointerException.class, () -> repo.insert(new Route()));
		con.rollback();
		
		//Test that insertion of bad PK fails
		r.getOrigin().setIataId("TEST1");
		r.getDestination().setIataId("TEST2");
		
		assertThrows(SQLException.class, () -> repo.insert(r));
		con.rollback();
		
		//Test that insertion of a valid entity succeeds
		assertDoesNotThrow(() -> 
		{
			Route r3 = factory.createNew();
			r3.getOrigin().setIataId("CDW");
			r3.getDestination().setIataId("KEB");
			repo.insert(r3);
			con.rollback();
		});
		
		
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		Route r = factory.createNew();
		final Route r2 = repo.get(1);
		PreparedStatement ps;
		ResultSet rs;
		String originalId;
		
		//Test that cannot perform  update with a new (keyless) entity
		assertEquals(0, repo.update(factory.createNew()).intValue());
		con.rollback();
		
		r = repo.get(1);
		originalId = r.getOrigin().getIataId();
		
		//Test that update with invalid FK does nothing
		r2.getOrigin().setIataId("TEST");
		assertThrows(SQLException.class, () -> repo.update(r2));
		con.rollback();

		
		//Test that update with valid data succeeds
		r = repo.get(1);
		r.getOrigin().setIataId("INL");
		
		assertEquals(1, repo.update(r).intValue());
		ps = con.prepareStatement("SELECT origin_id FROM Route WHERE id = ?");
		ps.setObject(1, r.getId());
		rs = ps.executeQuery();
		
		assertTrue(rs.next());
		assertEquals("INL", rs.getString(1));
		
		con.rollback();
	}
	
	private void arssertThrows(Class<SQLException> class1, Object object)
	{
		// TODO Auto-generated method stub
		
	}

	@Test
	public void testDelete() throws SQLException
	{
		Route r = factory.createNew();

		
		//Test that deletion of non-existent does nothing
		assertEquals(0,  repo.delete(r).intValue());
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
			Route r = repo.get(1);
			assertEquals(r.getDestination().getIataId(), "MRY");
			assertEquals(r.getOrigin().getIataId(), "VCV");
			con.rollback();
		});
		
		//Test retrieving an invalid instance yields null
		Route b = repo.get(99);
		assertNull(b);
		
	}
}
