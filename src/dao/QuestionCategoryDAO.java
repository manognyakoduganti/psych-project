package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.QuestionCategory;

public class QuestionCategoryDAO {
	
	public static boolean createQuestionCategory(QuestionCategory qc){
		
		String insertQuery = "INSERT INTO QUESTIONCATEGORY "
								+ "(name, description, responseType, startLabel, endLabel) "
								+ "VALUES (?, ?, ?, ?, ?)";
		
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			
			preparedStatement.setString(1, qc.getName());
			preparedStatement.setString(2, qc.getDescription());
			preparedStatement.setLong(3, qc.getResponseType());
			preparedStatement.setString(4, qc.getStartLabel());
			preparedStatement.setString(5, qc.getEndLabel());
			
			// execute select SQL stetement
			int rowsAffected = preparedStatement.executeUpdate();
			
			if (rowsAffected == 1){
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
	
public static boolean deleteQuestionCategory(String name){
		
		String deleteQuery = "DELETE FROM QUESTIONCATEGORY WHERE NAME = ?";
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
			
			preparedStatement.setString(1, name);
			
			// execute select SQL stetement
			int rowsAffected = preparedStatement.executeUpdate();
			
			if (rowsAffected == 1){
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

}
