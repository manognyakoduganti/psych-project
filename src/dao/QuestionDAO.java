package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.Question;

public class QuestionDAO {
	
	public static boolean createQuestion(Question q){
		String insertQuery = "INSERT INTO QUESTION (name, description, categoryId) VALUES (?, ?, ?)";
		
		
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			
			preparedStatement.setString(1, q.getName());
			preparedStatement.setString(2, q.getDescription());
			preparedStatement.setLong(3, q.getCategoryId());
			
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
