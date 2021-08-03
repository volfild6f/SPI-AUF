package cl.rhsso.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import cl.rhsso.dao.UserFederationDAO;
import cl.rhsso.utils.ConnectionFactory;
import cl.rhsso.vo.UserInfoVO;

public class UserFederationDAOImpl implements UserFederationDAO {
	
	ConnectionFactory dataConnection;
	
	public UserFederationDAOImpl(Properties properties) {
		dataConnection = ConnectionFactory.getInstance(properties);
	}
	
	
	@Override
	public UserInfoVO getUserByUsername(String username) {
		UserInfoVO userinfo = null;
		
		//Context initContext;
		Connection conn = null;
		ResultSet rs = null;
		try {
			//initContext = new InitialContext();
			 //Context envContext = (Context) initContext.lookup();
			 	System.out.println("en el try catch de getbyusername DAOImpl");
		        //DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MSSQLDS");
		        conn = dataConnection.getConnectionSoftland();
		        System.out.println("DAO Impl --connection username: "+ conn);
		        Statement statement = conn.createStatement();
		        String sql = "select usrID, usrFirstName, usrLastName, usrLogin, usrEmail, usrMobile from DCCPPlatform.dbo.gblUser where usrLogin = '"+ username +"' and  usrIsActive = '1' ";
		        rs = statement.executeQuery(sql);
		        
		        if (rs.next())
		        {
		        	userinfo = new UserInfoVO();
		        	userinfo.setId(rs.getLong(1));
		        	userinfo.setFirstName(rs.getString(2));
		        	userinfo.setLastName(rs.getString(3));
		        	userinfo.setUserName(rs.getString(4));
		        	userinfo.setEmail(rs.getString(5));
		        	userinfo.setAtributo(rs.getString(6));
		        }
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {          //TODO 
			try {
				conn.close();
				rs.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
       
		return userinfo;
	}
	
	@Override
	public boolean isValid(String username, String password) {
		System.out.println("pasando por el is valid DAOImpl");
		//Context initContext;
		Connection conn = null;
		ResultSet rs = null;
		try {
			//initContext = new InitialContext();
			 //Context envContext = (Context) initContext.lookup("java:comp/env");
			 	System.out.println("en el try catch is valid DAOImpl");
		        //DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MSSQLDS");
		        conn = dataConnection.getConnectionSoftland();
		        System.out.println("connection is valid: "+ conn);
		        Statement statement = conn.createStatement();
		        
		        String sqlEncrypt = "Select dccpprocurement.dbo.fn_EncriptarQS ('"+password+"')";
		        rs = statement.executeQuery(sqlEncrypt);
		        String passwordEncriptada = "";
		        if(rs.next())
		        	passwordEncriptada = rs.getString(1);
		        
		        String sql = "select usrID, usrFirstName, usrLastName, usrLogin, usrEmail, usrMobile from DCCPPlatform.dbo.gblUser where usrLogin = '"+ username +"' and usrPassword = '"+passwordEncriptada+"'";
		        //System.out.println("variable sql isValid: " + sql);
		        rs = statement.executeQuery(sql); 
		        
		        if(rs.next())
		        	return true;
		        
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
       
		return false;	
	}

	@Override
	public List<UserInfoVO> getAllUsers(int firstResult,int maxResults) {
		System.out.println("pasando por el getAllUsers DAOImpl");
		//Context initContext;
		List<UserInfoVO> listUserInfo = new ArrayList<UserInfoVO>();
		Connection conn = null;
		ResultSet rs = null;
		try {
			//initContext = new InitialContext();
			 //Context envContext = (Context) initContext.lookup("java:comp/env");
			 	System.out.println("en el try catch getAllUsers DAOImpl");
		        //DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MSSQLDS");
		        conn = dataConnection.getConnectionSoftland();
		        System.out.println("connection is valid: "+ conn);
		        Statement statement = conn.createStatement();
		        String sql = "select usrID, usrFirstName, usrLastName, usrLogin, usrEmail, usrMobile from DCCPPlatform.dbo.gblUser";
		        rs = statement.executeQuery(sql);
		        while(rs.next()) {
		        	UserInfoVO userinfo = new UserInfoVO();
		        	userinfo.setId(rs.getLong(1));
		        	userinfo.setFirstName(rs.getString(2));
		        	userinfo.setLastName(rs.getString(3));
		        	userinfo.setUserName(rs.getString(4));
		        	userinfo.setEmail(rs.getString(5));
		        	userinfo.setAtributo(rs.getString(6));
		        	listUserInfo.add(userinfo);        	
		        }
		        	
		        
		} catch (SQLException e) {
		
			System.out.println("en el catch de sql exception");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return listUserInfo;
	}

	@Override
	public List<UserInfoVO> searchForUser(String username) {
		// 
		System.out.println("pasando por el searchForUser DAOImpl");
		//Context initContext;
		List<UserInfoVO> listUserInfo = new ArrayList<UserInfoVO>();
		Connection conn = null;
		ResultSet rs = null;
		try {
			//initContext = new InitialContext();
			 //Context envContext = (Context) initContext.lookup("java:comp/env");
			 	System.out.println("en el try catch searchForUser DAOImpl");
		        //DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MSSQLDS");
		        conn = dataConnection.getConnectionSoftland();
		        System.out.println("connection is valid: "+ conn);
		        Statement statement = conn.createStatement();
		        String sql = "select usrID, usrFirstName, usrLastName, usrLogin, usrEmail, usrMobile from DCCPPlatform.dbo.gblUser where lower(usrLogin) = '"+username+"'";
		        rs = statement.executeQuery(sql);
		        while(rs.next()) {
		        	UserInfoVO userinfo = new UserInfoVO();
		        	userinfo.setId(rs.getLong(1));
		        	userinfo.setFirstName(rs.getString(2));
		        	userinfo.setLastName(rs.getString(3));
		        	userinfo.setUserName(rs.getString(4));
		        	userinfo.setEmail(rs.getString(5));
		        	userinfo.setAtributo(rs.getString(6));
		        	listUserInfo.add(userinfo);	        	
		        }	
		        
		} catch (SQLException e) {
			// 
			System.out.println("en el catch de sql exception");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				rs.close();
			} catch (SQLException e) {
				// 
				e.printStackTrace();
			}
		}
		return listUserInfo;
	}

	@Override
	public boolean updateCredential(String id, String username, String email, String first_name, String last_name,
			String password) {
		
		//Context initContext;
		Connection conn = null;
		try {
			//initContext = new InitialContext();
			 //Context envContext = (Context) initContext.lookup();
			 	System.out.println("en el try catch de updateCredential DAOImpl");
		        //DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MSSQLDS");
		        conn = dataConnection.getConnectionSoftland();
		        System.out.println("connection username: "+ conn);
		        Statement statement = conn.createStatement();
		        
		        String sql = "UPDATE DCCPPlatform.dbo.gblUser SET usrPassword = '"+password+"' WHERE usrCode = '"+id+"' and usrLogin = '"+username+"'";
		        
//		        String sql = "UPDATE [database].dbo.usuario\n"
//		        		+ "SET first_name='"+first_name+"', last_name='"+last_name+"', password='"+password+"', email='"+email+"', atributo='' where lower(username) = '"+username+"'";
//		        System.out.println("sql: " + sql); TODO  CAMBIAR CON LO DE ENCRIPTAR PASSWORD
		        
		        statement.executeUpdate(sql);
		} catch (SQLException e) {
			// 
			e.printStackTrace();
			return false;
		}      
		return true;
	}


	@Override
	public boolean addUser(String id, String username, String email, String first_name, String last_name,
			String password, String atributo) {
		
		//UserInfoVO userinfo = new UserInfoVO();
		//Context initContext;
		Connection conn = null;

		try {
			//initContext = new InitialContext();
			 //Context envContext = (Context) initContext.lookup();
			 	System.out.println("en el try catch de addUser DAOImpl");
		        //DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MSSQLDS");
		        conn = dataConnection.getConnectionSoftland();
		        System.out.println("connection username: "+ conn);
		        //Statement statement = conn.createStatement();
		        CallableStatement stmt = conn.prepareCall("{call crearUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)} ");
		        stmt.setInt(1,10);
		        stmt.setInt(2,20);
		        stmt.setString(3, username);
		        stmt.setString(4, password);
		        stmt.setDate(5, null);
		        stmt.setInt(6, 60);
		        stmt.setInt(7, 70);
		        stmt.setInt(8, 80);
		        stmt.setInt(9, 90);
		        stmt.setString(10, first_name);
		        stmt.setString(11, last_name);
		        stmt.setString(12, "posicion");
		        stmt.setString(13, email);
		        stmt.setString(14, "emailAlternativo");
		        stmt.setString(15, "telefono");
		        stmt.setString(16, "celular");
		        stmt.setString(17, "fax");
		        stmt.setInt(18, 180);
		        stmt.setInt(19, 190);
		        stmt.setInt(20, 200);
		        stmt.setString(21, "rut");
		        stmt.setString(22, "definicion1");
		        stmt.setString(23, "definicion2");
		        stmt.setString(24, "definicion3");
		        stmt.setInt(25, 250);
		        stmt.setDate(26, null);
		        stmt.setInt(27, 270);
		        
//		        String sql = "INSERT INTO [database].dbo.usuario\n"
//		        		+ "(id, first_name, last_name, username, password, email, atributo)	VALUES('"+id+"', '"+first_name+"', '"+last_name+"', '"+username+"', '"+password+"', '"+email+"', '"+atributo+"'); ";
		        //stmt.executeUpdate(); 
		        stmt.execute();
		        
		      
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return true;
	}

	
	@Override
	public boolean removeUser(String username) {
		
		//Context initContext;
		Connection conn = null;

		try {
			//initContext = new InitialContext();
			 //Context envContext = (Context) initContext.lookup();
			 	System.out.println("en el try catch de removeUser DAOImpl");
		        //DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/MSSQLDS");
		        conn = dataConnection.getConnectionSoftland();
		        System.out.println("connection username: "+ conn);
		        Statement statement = conn.createStatement();
		        System.out.println("llamando al usuario en remove user: " + username);
		        //String sql = "UPDATE DCCPPlatform.dbo.gblUser SET uroIsActive = 0 where usrLogin = '"+username+"' ";
		        String sql = "DELETE FROM [database].dbo.usuario WHERE username='"+username+"' "; //TODO cambiar a update
		        statement.executeUpdate(sql);
		        
		} catch (SQLException e) {
	
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
