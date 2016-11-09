package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.UserProfile;

public class UserProfileDAO {
	
	public static boolean udpateUserProfile(UserProfile userProfile, String currentEmail){
		
		String selectQuery = "UPDATE ADMIN SET FIRSTNAME = ?, LASTNAME = ?,  EMAIL= ?,  PASSWORD = ? WHERE EMAIL = ?";
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			preparedStatement.setString(1, userProfile.getFirstName());
			preparedStatement.setString(2, userProfile.getLastName());
			preparedStatement.setString(3, userProfile.getEmail());
			preparedStatement.setString(4, userProfile.getPassword());
			preparedStatement.setString(5, currentEmail);
			
			// execute select SQL statement
			int affectedLines = preparedStatement.executeUpdate();
			
			if(affectedLines == 1) {
				return true;
			}
			else{
				return false;
			}
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		} 
		
	}
	
	public static boolean isDuplicateEmail(String email){
		
		
		String selectQuery = "SELECT * FROM ADMIN WHERE EMAIL = ?";
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			preparedStatement.setString(1, email);
			
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

}
