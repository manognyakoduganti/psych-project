package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.AdminDetails;


public class AuthenticationDAO{
	
	public static AdminDetails validateUser(String email, String password){
		
		String selectQuery = "SELECT ID, FIRSTNAME, LASTNAME, EMAIL, ROLE FROM ADMIN WHERE EMAIL = ? AND PASSWORD = ?";
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.first()) {
				AdminDetails adminDetails = new AdminDetails();
				adminDetails.setFirstName(rs.getString("FIRSTNAME"));
				adminDetails.setLastName(rs.getString("LASTNAME"));
				adminDetails.setUserId(rs.getLong("ID"));
				
				int roleFieldID = rs.getInt("ROLE");
				
				String roleValue = AuthenticationDAO.getFieldNameByFieldId(roleFieldID);
				adminDetails.setRole(roleValue);
				return adminDetails;
				
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		} 
		catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		} 
		return null;
		
	}
	
	public static String getFieldNameByFieldId(int fieldId) throws SQLException{
		
		
		Connection connection = DBSource.getConnectionPool().getConnection();
		
		PreparedStatement preparedStatement = null;
		
		String selectQuery = "SELECT FIELDNAME FROM FIELDLOOKUP WHERE ID = ?";

		preparedStatement = connection.prepareStatement(selectQuery);
		preparedStatement.setInt(1, fieldId);
		
		// execute select SQL statement
		ResultSet rs = preparedStatement.executeQuery();
		//System.out.println(rs.getFetchSize());
		String fieldName = null;
		while (rs.next()) {
			fieldName = rs.getString("FIELDNAME");
		}
		return fieldName;
	}

}