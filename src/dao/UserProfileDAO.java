package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileDAO {
	
	public static boolean udpateUserProfile(String newFirstName, String newLastName, String newEmail, String newPassword, String email){
		
		String selectQuery = "UPDATE ADMIN SET FIRSTNAME = ?, LASTNAME = ?,  EMAIL= ?,  PASSWORD = ? WHERE EMAIL = ?";
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			preparedStatement.setString(1, newFirstName);
			preparedStatement.setString(2, newLastName);
			preparedStatement.setString(3, newEmail);
			preparedStatement.setString(4, newPassword);
			preparedStatement.setString(5, email);
			
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
				return false;
			}
			return false;
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return true;
		} 
	}

}
