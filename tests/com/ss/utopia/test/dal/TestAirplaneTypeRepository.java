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

import com.ss.dal.factory.AirplaneTypeFactory;
import com.ss.utopia.dal.AirplaneTypeRepository;
import com.ss.utopia.model.AirplaneType;


public class TestAirplaneTypeRepository
{
	private static Connection con = null;
	private static AirplaneTypeRepository repo;
	private Integer maxCapacity = 999999;
	private static AirplaneTypeFactory factory;
	
	@BeforeClass
	public static void setUpClass() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, NoSuchMethodException, SecurityException
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
			factory = AirplaneTypeFactory.getInstance();
		}
		
		repo = new AirplaneTypeRepository(con, factory);
		
	}
	
	@Test
	public void testInsert() throws SQLException
	{
		
		AirplaneType entity = new AirplaneType();
		
		
		
		//Test insertion of invalid values fails
		entity.setMaxCapacity(-999);
		assertThrows(SQLException.class, () -> {repo.insert(entity);});
		con.rollback();
		
		//Test insertion of valid data succeeds
		assertDoesNotThrow(() -> 
		{
			AirplaneType newEntity;
			PreparedStatement ps;
			ResultSet rs;
			
			entity.setMaxCapacity(maxCapacity);
			newEntity = repo.insert(entity);
			
			//Test that only one row inserted and that max capacity is correct
			
			ps = con.prepareStatement("SELECT max_capacity FROM airplane_type WHERE id = ?");
			ps.setLong(1, newEntity.getId());
			rs = ps.executeQuery();
			
			assertTrue(rs.next());
			assertEquals(newEntity.getMaxCapacity(), rs.getInt(1));
			assertFalse(rs.next());
			
			//test that ID was correct
			ps = con.prepareStatement("SELECT id FROM airplane_type WHERE max_capacity = ?");
			ps.setInt(1, newEntity.getMaxCapacity());
			rs = ps.executeQuery();
			
			
			assertTrue(rs.next());
			assertEquals(newEntity.getId().longValue(), rs.getLong(1));
			assertFalse(rs.next());
			
			ps.close();
			
		}
		);
		con.rollback();
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		AirplaneType newEntity = new AirplaneType();
		newEntity.setMaxCapacity(maxCapacity);
		Integer originalCapacity = 280;
		
		//Test that cannot perform  update with a new (keyless) entity
		assertEquals(0, repo.update(newEntity).intValue());
		
		newEntity.setId(1);
		
		//test that update succeeds
		assertDoesNotThrow(() -> {repo.update(newEntity);});
		assertDoesNotThrow(() -> 
		{
			
			PreparedStatement ps2 = con.prepareStatement("SELECT max_capacity FROM airplane_type WHERE id = ?");
			ps2.setLong(1, newEntity.getId());
			ResultSet rs = ps2.executeQuery();
			assertTrue(rs.next());
			assertEquals(rs.getInt(1), newEntity.getMaxCapacity());
		});
		
		//Revert the changes made and confirm
		newEntity.setMaxCapacity(originalCapacity);
		assertDoesNotThrow(() -> repo.update(newEntity));
		
		assertDoesNotThrow(() -> 
		{
			
			PreparedStatement ps2 = con.prepareStatement("SELECT max_capacity FROM airplane_type WHERE id = ?");
			ps2.setLong(1, newEntity.getId());
			ResultSet rs = ps2.executeQuery();
			assertTrue(rs.next());
			assertEquals(rs.getInt(1), 280);
		});
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		AirplaneType apt = new AirplaneType();
		apt.setId(0);
		apt.setMaxCapacity(maxCapacity);
		int rowsDeleted;
		
		//Test that deletion of non-existent entity does nothing
		try
		{
			rowsDeleted = repo.delete(apt);
			assertEquals(rowsDeleted, 0);
		}
		finally
		{
			con.rollback();
		}
		
		//Test that deletion of an existing entity with a referenced airplane fails
		apt.setId(1);
		apt.setMaxCapacity(280);
		
		try
		{
			assertThrows(SQLIntegrityConstraintViolationException.class, () -> {repo.delete(apt);});
		}
		finally
		{
			con.rollback();
		}
		
		//Test that deletion of an existing entity without a referenced airplane
		apt.setId(2);
		apt.setMaxCapacity(250);
		
		try
		{
			rowsDeleted = repo.delete(apt);
			assertEquals(rowsDeleted, 1);
		}
		finally
		{
			con.rollback();
		}
	}
	
	@Test
	public void testGet() throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		
		
		//Test fetching a single entity by id
		try
		{
			//Test that valid entity is fetched
			AirplaneType apt = repo.get(1);
			assertEquals(apt.getMaxCapacity(), 280);
			
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
				List<AirplaneType> apt = repo.getAll();
				assertEquals(apt.size(), 27);
			}
			);
		}
		finally
		{
			con.rollback();
		}
		
	}
}
