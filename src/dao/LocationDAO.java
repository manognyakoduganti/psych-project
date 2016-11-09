package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.Location;

public class LocationDAO {
	
	public static boolean createLocation(Location location){
		
		
		String insertQuery = "INSERT INTO LOCATION (location.locCode, location.locName, location.description, location.keywords, "
				+ "location.addressLine1, location.addressLine2,  location.city, location.state, location.zipcode, location.phoneNumber, "
				+ "location.faxNumber, location.email)  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			
			preparedStatement.setString(1, location.getCity());
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
			
			int updated = preparedStatement.executeUpdate();
			
			if(updated == 1) {
				return true;
			}
			return false;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		} 
		catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		} 
		
	}
	
	
	public static boolean isDuplicateLocation(String name){
		
		String selectQuery = "SELECT * FROM LOCATION WHERE LOCATIONNAME = ?";
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			preparedStatement.setString(1, name);
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.first()) {
				return true;
			}
			return false;
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return true;
		}
	}
	
	public static void deleteLocation(String name){
		
		String selectQuery = "DELETE FROM LOCATION WHERE LOCATIONNAME = ?";
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, name);
			preparedStatement.executeUpdate();
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}


}
