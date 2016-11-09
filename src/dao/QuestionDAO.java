package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.Constant;
import common.Question;

public class QuestionDAO {
	
	public static JSONObject createQuestion(Question q){
		String insertQuery = "INSERT INTO QUESTION (name, description, categoryId) VALUES (?, ?, ?)";
		Connection connection = null;
		JSONObject returnJSON = new JSONObject();
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			
			preparedStatement.setString(1, q.getName());
			preparedStatement.setString(2, q.getDescription());
			preparedStatement.setLong(3, q.getCategoryId());
			
			// execute select SQL stetement
			int rowsAffected = preparedStatement.executeUpdate();
			
			if (rowsAffected == 1){
				
				returnJSON.put(Constant.NEW_QUESTION_NAME, q.getName());
				returnJSON.put(Constant.NEW_QUESTION_DESCRIPTION, q.getDescription());
				returnJSON.put(Constant.NEW_QUESTION_CATEGORY_ID, q.getCategoryId());
				
				returnJSON.put(Constant.STATUS, Constant.OK_200);
				returnJSON.put(Constant.USER_MESSAGE, "Added question successfully!");
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Added question successfully!");
				
			}
			else{
				returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
				returnJSON.put(Constant.USER_MESSAGE, "Could not add to database.");
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Failed at inserting record.");
			}
			connection.close();
			return returnJSON;
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			
			try {
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
			returnJSON.put(Constant.USER_MESSAGE, "Could not insert question");
			returnJSON.put(Constant.DEVELOPER_MESSAGE, "Could not insert question: " + e.getMessage());
			return returnJSON;
		}
	}

	
	public static boolean deleteQuestion(String name){
		
		String deleteQuery = "DELETE FROM QUESTION WHERE NAME = ?";
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
			
			preparedStatement.setString(1, name);
			
			// execute select SQL stetement
			int rowsAffected = preparedStatement.executeUpdate();
			
			connection.close();
			
			if (rowsAffected == 1){
				return true;
			}
			else{
				return false;
			}
			
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


	public static JSONObject updateQuestion(Question question) {
		
		String updateQuery = "UPDATE QUESTION SET name=?, description=?, categoryId=? WHERE ID=?";
		
		JSONObject returnJSON = new JSONObject();

		Connection connection = null;
		
		try{
		
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
			
			preparedStatement.setString(1, question.getName());
			preparedStatement.setString(2, question.getDescription());
			preparedStatement.setLong(3, question.getCategoryId());
			preparedStatement.setLong(4, question.getId());
			
			// execute select SQL stetement
			int rowsAffected = preparedStatement.executeUpdate();
						
			if (rowsAffected == 1){
				JSONObject updated = new JSONObject();
				
				updated.put(Constant.NEW_QUESTION_NAME, question.getName());
				updated.put(Constant.NEW_QUESTION_DESCRIPTION, question.getDescription());
				updated.put(Constant.NEW_QUESTION_CATEGORY_ID, question.getCategoryId());
				updated.put(Constant.QUESTION_ID, question.getId());
				
				returnJSON.put(Constant.RESULTS, updated);
				
				returnJSON.put(Constant.STATUS, Constant.OK_200);
				returnJSON.put(Constant.USER_MESSAGE, "Updated question successfully!");
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Updated question successfully!");
			}
			else{
				returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
				returnJSON.put(Constant.USER_MESSAGE, "Could not update question");
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Could not update question, rowsAffected not 1.");
			}
			
			connection.close();
		
		}catch(SQLException e){
			System.out.println(e.getMessage());
			returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
			returnJSON.put(Constant.USER_MESSAGE, "Could not update question");
			returnJSON.put(Constant.DEVELOPER_MESSAGE, "Could not update question: " + e.getMessage());
			
			try {
				if (connection != null){
					connection.close();
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return returnJSON;
		}
		
		return returnJSON;
	}
	
	
public static JSONObject getAll(){
		
		String selectQuery = "SELECT A.ID AS qId, A.NAME AS qName, A.DESCRIPTION AS qDescription," +
								"B.NAME AS catName " + 
								"FROM psych.QUESTION AS A "+
								"INNER JOIN psych.QUESTIONCATEGORY AS B "+
								"ON A.categoryId = B.id;";
		
		JSONObject returnJSON = new JSONObject();
		JSONArray results = new JSONArray();
		
		
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			// execute select SQL stetement
			ResultSet rows = preparedStatement.executeQuery();
			
			while (rows.next()){
				JSONObject object = new JSONObject();
				
				object.put(Constant.QUESTION_ID, rows.getString("qId"));
				object.put(Constant.QUESTION_NAME, rows.getString("qName"));
				object.put(Constant.QUESTION_DESCRIPTION, rows.getString("qDescription"));
				object.put(Constant.QUESTION_CATEGORY_NAME, rows.getString("catName"));
				
				results.add(object);
			}
			returnJSON.put(Constant.RESULTS, results);
			returnJSON.put(Constant.STATUS, Constant.OK_200);
			returnJSON.put(Constant.USER_MESSAGE, "Successfully retrieved all questions!");
			returnJSON.put(Constant.DEVELOPER_MESSAGE, "Successfully retrieved all questions");
			
			connection.close();
			
			return returnJSON;
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
			returnJSON.put(Constant.USER_MESSAGE, "Error in retrieving all questions!");
			returnJSON.put(Constant.DEVELOPER_MESSAGE, "Error retrieving all questions: " + e.getMessage());
			
			try {
				if (connection != null){
					connection.close();
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return returnJSON;
		}
		
	}


	public static boolean checkDuplicate(String name) {
		String selectQuery = "SELECT * FROM QUESTION WHERE NAME = ?";
		
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			preparedStatement.setString(1, name);
			
			// execute select SQL stetement
			ResultSet rows = preparedStatement.executeQuery();
			int count = 0;
			
			while (rows.next()){
				count++;
			}
			
			connection.close();
			
			return count != 0;
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
}
