package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Location;

public class LocationDAO {
	
	private static Logger slf4jLogger = LoggerFactory.getLogger(LocationDAO.class);
	
	public static boolean createLocation(Location location){
			
		slf4jLogger.info("Entered into createLocation");
		
		String insertQuery = "INSERT INTO LOCATION (location.locCode, location.locName, location.description, location.keywords, "
				+ "location.addressLine1, location.addressLine2,  location.city, location.state, location.zipcode, location.phoneNumber, "
				+ "location.faxNumber, location.email) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			
			preparedStatement.setString(1, location.getCode());
			preparedStatement.setString(2, location.getName());
			preparedStatement.setString(3, location.getDesc());
			preparedStatement.setString(4, location.getKeywords());
			preparedStatement.setString(5, location.getAddressLine1());
			preparedStatement.setString(6, location.getAddressLine2());
			preparedStatement.setString(7, location.getCity());
			preparedStatement.setLong(8, location.getState());
			preparedStatement.setLong(9, location.getZipCode());
			preparedStatement.setLong(10, location.getPhoneNumber());
			preparedStatement.setLong(11, location.getFaxNumber());
			preparedStatement.setString(12, location.getEmail());
			
			slf4jLogger.info(preparedStatement.toString());
			int updated = preparedStatement.executeUpdate();
			connection.close();
			if(updated == 1) {
				return true;
			}
			return false;
		}catch(SQLException e){
			slf4jLogger.info("SQL Exception while extracting field information");
			slf4jLogger.info(e.getMessage());
			try {
				if (connection != null){
					connection.close();
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
	}
	
	
	public static boolean isDuplicateLocation(String name){
		
		slf4jLogger.info("Entered into isDuplicateLocation");
		
		String selectQuery = "SELECT * FROM LOCATION WHERE LOCNAME = ?";
		
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, name);
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.first()) {
				connection.close();
				return true;
			}
			connection.close();
			return false;
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			try {
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
		
	}
	
	public static void deleteLocation(String name){
		
		slf4jLogger.info("Entered into deleteLocation");
		String selectQuery = "DELETE FROM LOCATION WHERE LOCNAME = ?";
		
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, name);
			preparedStatement.executeUpdate();
			connection.close();
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			try {
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static boolean isDuplicateLocationCode(String code){
		
		slf4jLogger.info("Entered into isDuplicateLocationCode");
		String selectQuery = "SELECT * FROM LOCATION WHERE LOCCODE = ?";
		
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			preparedStatement.setString(1, code);
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.first()) {
				connection.close();
				return true;
			}
			connection.close();
			return false;
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			try {
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
		
	}

}
