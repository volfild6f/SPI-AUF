package cl.rhsso.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ConnectionFactory {
	
	private static ConnectionFactory singleInstance = null;
	private static Properties properties;
	
	static Logger logger = Logger.getLogger(ConnectionFactory.class.getName());


	public static ConnectionFactory getInstance(Properties properties) {
		ConnectionFactory.properties = properties;
		if (null == singleInstance) {
			singleInstance = new ConnectionFactory();
		}
		return singleInstance; 
	}
	
	public Connection getConnectionSoftland() throws SQLException{
		Connection con = null;
		
		String jdbcUrl = properties.getProperty("jdbcUrl");
		String driver = properties.getProperty("driver");
		String userName = properties.getProperty("userName");
		String password = properties.getProperty("password");
		
		logger.info("jdbcUrl: "+jdbcUrl);
		logger.info("driver: "+driver);
		logger.info("userName: "+userName);
		logger.info("password: "+password);
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(jdbcUrl,userName,password);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return con;
	}
}
