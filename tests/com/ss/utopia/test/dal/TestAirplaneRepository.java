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

import com.ss.dal.factory.AirplaneFactory;
import com.ss.dal.factory.AirplaneTypeFactory;
import com.ss.utopia.dal.AirplaneRepository;
import com.ss.utopia.dal.AirplaneTypeRepository;
import com.ss.utopia.model.Airplane;
import com.ss.utopia.model.AirplaneType;


public class TestAirplaneRepository
{
	private static Connection con = null;
	private static AirplaneRepository repo;
	private Integer maxCapacity = 999999;
	private static AirplaneFactory factory;
	
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
			factory = AirplaneFactory.getInstance();
		}
		
		repo = new AirplaneRepository(con, factory);
		
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
		
		Airplane entity = factory.createNew();
		
		
		//Test insertion invalid FK entity fails
		entity.setType(AirplaneTypeFactory.getInstance().createNew());

		assertThrows(SQLException.class, () -> {repo.insert(entity);});
		con.rollback();
		
		//Test insertion of valid data succeeds
		assertDoesNotThrow(() -> 
		{
			Airplane newEntity;
			PreparedStatement ps;
			ResultSet rs;
			
			entity.setType(AirplaneTypeFactory.getInstance().createNew());
			entity.getType().setId(1);
			entity.getType().setMaxCapacity(280);
			newEntity = repo.insert(entity);
			
			//Test that only one row inserted and that max capacity is correct
			
			ps = con.prepareStatement("SELECT type_id FROM airplane WHERE id = ?");
			ps.setLong(1, newEntity.getId());
			rs = ps.executeQuery();
			
			assertTrue(rs.next());
			assertEquals(newEntity.getType().getId().intValue(), rs.getInt(1));
			assertFalse(rs.next());
			
			ps.close();
			
		}
		);
		con.rollback();
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		Airplane newEntity = factory.createNew();
		
		//Test that cannot perform  update with a new (keyless) entity
		assertEquals(0, repo.update(newEntity).intValue());
		
		newEntity.setId(1);
		newEntity.getType().setId(5);
		
		//test that update succeeds
		assertDoesNotThrow(() -> {repo.update(newEntity);});

		assertDoesNotThrow(() -> 
		{
			
			PreparedStatement ps2 = con.prepareStatement("SELECT type_id FROM airplane WHERE id = ?");
			ps2.setLong(1, newEntity.getId());
			ResultSet rs = ps2.executeQuery();
			assertTrue(rs.next());
			assertEquals(rs.getInt(1), newEntity.getType().getId().intValue());
			
			con.rollback();
		});
		
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
		Airplane a = factory.createNew();
		a.setId(0);
		int rowsDeleted;
		
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
		
		//Test that deletion of an existing entity with a referenced airplane fails
		a.setId(16);

		assertThrows(SQLIntegrityConstraintViolationException.class, () -> {repo.delete(a);});
		con.rollback();

		
		//Test that deletion of an existing entity without a referenced airplane
		a.setId(2);
		
		try
		{
			rowsDeleted = repo.delete(a);
			assertEquals(rowsDeleted, 1);
		}
		finally
		{
			con.rollback();
		}
	}
	
	@Test
	public void testGet() throws SQLException
	{
		
		
		//Test fetching a single entity by id
		try
		{
			//Test that valid entity is fetched
			Airplane a = repo.get(1);
			assertEquals(a.getType().getId().intValue(), 12);
			
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
				List<Airplane> apt = repo.getAll();
				assertEquals(apt.size(), 0);
			}
			);
		}
		finally
		{
			con.rollback();
		}
		
	}
}
