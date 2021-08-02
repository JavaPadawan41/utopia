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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ss.dal.factory.AirportFactory;
import com.ss.utopia.dal.AirportRepository;
import com.ss.utopia.model.Airport;



public class TestAirportRepository
{
	private static Connection con = null;
	private static AirportRepository repo;
	private Integer maxCapacity = 999999;
	private static AirportFactory factory;
	
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
			factory = AirportFactory.getInstance();
		}
		
		repo = new AirportRepository(con, factory);
		
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
		
		Airport entity = factory.createNew();
		
		
		//Test insertion invalid FK entity fails
		assertThrows(SQLException.class, () -> {repo.insert(entity);});
		con.rollback();
		
		//Test insertion of duplicate FK fails
		entity.setIataId("ANB");
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test insertion of null city fails
		entity.setIataId("TST");
		assertThrows(SQLException.class, () -> repo.insert(entity));
		con.rollback();
		
		//Test insertion of valid data succeeds
		assertDoesNotThrow(() -> 
		{
			Airport newEntity = factory.createNew();
			PreparedStatement ps;
			ResultSet rs;
			

			newEntity.setIataId("TST");
			newEntity.setCity("Test City");
			newEntity = repo.insert(newEntity);
			
			//Test that only one row inserted and that max capacity is correct
			
			ps = con.prepareStatement("SELECT CITY FROM airport WHERE iata_id = ?");
			ps.setString(1, newEntity.getIataId());
			rs = ps.executeQuery();
			
			assertTrue(rs.next());
			assertEquals(newEntity.getCity(), rs.getString(1));
			assertFalse(rs.next());
			
			ps.close();
			
		}
		);
		con.rollback();
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		Airport newEntity = factory.createNew();
		
		//Test that cannot perform  update with a new (keyless) entity
		assertEquals(0, repo.update(newEntity).intValue());
		
		newEntity.setIataId("ANB");
		newEntity.setCity("TEST");
		
		//test that update succeeds with valid data
		assertDoesNotThrow(() -> {repo.update(newEntity);});

		assertDoesNotThrow(() -> 
		{
			
			PreparedStatement ps2 = con.prepareStatement("SELECT city FROM airport WHERE iata_id = ?");
			ps2.setString(1, newEntity.getIataId());
			ResultSet rs = ps2.executeQuery();
			assertTrue(rs.next());
			assertEquals(rs.getString(1), newEntity.getCity());
			
			con.rollback();
		});
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		Airport a = factory.createNew();
		a.setIataId("RBG");
		int rowsDeleted;
		PreparedStatement ps;
		ResultSet rs;
		
		//Test that deletion of non-existent entity does nothing
		try
		{
			rowsDeleted = repo.delete(a);
			assertEquals(rowsDeleted, 0);
		}
		finally
		{
			con.rollback();
		}
		
		//Test that deletion of an existing entity cascades
		a.setIataId("BFF");

		assertDoesNotThrow(() -> {repo.delete(a);});
		ps = con.prepareStatement("SELECT COUNT(*) FROM route WHERE route.origin_id  = ? OR route.destination_id = ?");
		ps.setObject(1, a.getIataId());
		ps.setObject(2, a.getIataId());
		rs = ps.executeQuery();
		assertTrue(rs.next());
		assertEquals(rs.getInt(1), 0);
		con.rollback();
	}
	
	@Test
	public void testGet() throws SQLException
	{
		
		
		//Test fetching a single entity by id
		try
		{
			//Test that valid entity is fetched
			Airport a = repo.get("ANB");
			assertEquals(a.getCity(), "Anniston");
			
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
				List<Airport> apt = repo.getAll();
				assertEquals(apt.size(), 50);
			}
			);
		}
		finally
		{
			con.rollback();
		}
		
	}
}
