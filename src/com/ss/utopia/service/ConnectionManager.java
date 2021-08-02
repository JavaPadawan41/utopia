package com.ss.utopia.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager
{
	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException
	{
		try(InputStream in = new FileInputStream("resources/db.properties"))
		{
			Properties p = new Properties();
			p.load(in);
			Class.forName(p.getProperty("driver"));
			
			Connection con = DriverManager.getConnection(p.getProperty("url"), p.getProperty("uid"), p.getProperty("pwd"));
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			
			return con;
		}
	}
}
