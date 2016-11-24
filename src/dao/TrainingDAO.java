package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Statement;

import common.Constant;
import common.Training;
import common.TrainingImage;

public class TrainingDAO {

	private static Logger slf4jLogger = LoggerFactory.getLogger(TrainingDAO.class);
	
	public static JSONArray fetchAllTrainingName(){
		
		slf4jLogger.info("Entered into fetchAllTrainingName");
		
		String selectQuery = "SELECT * FROM training";
		
		Connection connection = null;
		JSONArray jsonArray = new JSONArray();
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(Constant.TRAINING_NAME,rs.getString("name"));
				jsonObject.put(Constant.TRAINING_ID,Long.toString(rs.getLong("id")));
				jsonArray.add(jsonObject);
			}
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
		return jsonArray;
	}
	
	public static boolean deleteTraining(long trainingId){
		
		//deleteTrainingImageMap(trainingId);
		//deleteTrainingQuestionMap(trainingId);
		
		String deleteQuery = "DELETE FROM TRAINING WHERE ID = ?;";
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
			
			preparedStatement.setLong(1, trainingId);
			
			// execute select SQL stetement
			int rowsAffected = preparedStatement.executeUpdate();
			
			connection.close();
			//System.out.println(rowsAffected);
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
	
	
	public static boolean deleteTrainingQuestionMap(long trainingId){
		
		String deleteQuery = "DELETE FROM TRAININGQUESTIONMAP WHERE TRAININGID = ?";
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
			
			preparedStatement.setLong(1, trainingId);
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			connection.close();
			
			if (rowsAffected >= 1){
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
		
		
	public static boolean deleteTrainingImageMap(long trainingId){
		
		String deleteQuery = "DELETE FROM TRAININGIMAGEMAP WHERE TRAININGID = ?";
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
			
			preparedStatement.setLong(1, trainingId);
			
			// execute select SQL stetement
			int rowsAffected = preparedStatement.executeUpdate();
			
			connection.close();
			
			if (rowsAffected >= 1){
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
	
	public static JSONObject createTraining(Training training){
		String insertSelect = "INSERT INTO TRAINING (NAME, DESCRIPTION, KEYWORDS) VALUES (?,?,?);";
		
		Connection connection = null;
		JSONObject returnJSON = new JSONObject();
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(insertSelect, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, training.getName());
			preparedStatement.setString(2, training.getDescription());
			preparedStatement.setString(3, training.getKeywords());
			
			 int affectedRows = preparedStatement.executeUpdate();
			 
			 if (affectedRows == 0){
				 throw new SQLException("Creating training failed, no rows affected.");
			 }
			 Long lastId = (long) 0;
			 
			 try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                lastId = generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Creating training failed, no ID obtained.");
		            }
		        }
			 			
			if (lastId != 0){
				
				returnJSON.put(Constant.STATUS, Constant.OK_200);
				returnJSON.put(Constant.USER_MESSAGE, "Added training successfully!");
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Added training successfully!");
				returnJSON.put(Constant.NEW_TRAINING_ID, lastId);
				returnJSON.put(Constant.TRG_NEW_NAME, training.getName());
				returnJSON.put(Constant.TRG_NEW_DESCRIPTION, training.getDescription());
				
				boolean insertTrainingQuestionMap = createTrainingQuestionMap(training, lastId);
				boolean insertTrainingImageMap = createTrainingImageMap(training, lastId);
				
				if (!insertTrainingQuestionMap || !insertTrainingImageMap){
					returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
					returnJSON.put(Constant.USER_MESSAGE, "Could not add to database.");
					returnJSON.put(Constant.DEVELOPER_MESSAGE, "Failed at inserting Training map records.");
				}
			}
			else{
				returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
				returnJSON.put(Constant.USER_MESSAGE, "Could not add to database.");
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Failed at inserting training record.");
			}
			connection.close();
			
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
		
		
		return returnJSON;
	}
	
	
	public static boolean createTrainingQuestionMap(Training training, Long trainingId){
		Connection connection = null;
		
		for(Long questionId: training.getQuestions()){
			String insertQuery = "INSERT INTO TRAININGQUESTIONMAP (TRAININGID, QUESTIONID) VALUES (?, ?)";
			
			PreparedStatement preparedStatement1;
			try {
				connection = DBSource.getConnectionPool().getConnection();
				
				preparedStatement1 = connection.prepareStatement(insertQuery);
				preparedStatement1.setLong(1, trainingId);
				preparedStatement1.setLong(2, questionId);
				
				int rowsAffected = preparedStatement1.executeUpdate();
				
				connection.close();
				
				if (rowsAffected != 1){			
					return false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return false;
				}
				return false;
			}
			
		}
		return true;
	}
	
	
	public static boolean createTrainingImageMap(Training training, Long trainingId){
		Connection connection = null;
		try {
			connection = DBSource.getConnectionPool().getConnection();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return false;
		}
		
		for(TrainingImage image: training.getImages()){
			String insertQuery = "INSERT INTO TRAININGIMAGEMAP (TRAININGID, IMAGECATEGORYID, DURATION, NOOFIMAGES, IMAGETYPE) "
					+ "VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStatement2;
			try {
				
				preparedStatement2 = connection.prepareStatement(insertQuery);
				
				preparedStatement2.setLong(1, trainingId);
				preparedStatement2.setLong(2, image.getImageCategory());
				preparedStatement2.setLong(3, image.getDuration());
				preparedStatement2.setLong(4, image.getNoOfImages());
				preparedStatement2.setLong(5, image.getImageType());
				
				// execute select SQL stetement
				int rowsAffected = preparedStatement2.executeUpdate();
				
				if (rowsAffected != 1){
					connection.close();
					return false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return false;
				}
				e.printStackTrace();
			}
		}
		try {
			if (connection != null){
				connection.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
		return true;
	}
	
	
	
	public static JSONObject updateTraining(Training training){
		
		deleteTrainingQuestionMap(training.getId());
		deleteTrainingImageMap(training.getId());
		
		String updateStatement = "UPDATE TRAINING SET NAME = ?, DESCRIPTION = ?, KEYWORDS = ? WHERE ID = ?";
		
		Connection connection = null;
		JSONObject returnJSON = new JSONObject();
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
			
			preparedStatement.setString(1, training.getName());
			preparedStatement.setString(2, training.getDescription());
			preparedStatement.setString(3, training.getKeywords());
			preparedStatement.setLong(4, training.getId());
			
			 int affectedRows = preparedStatement.executeUpdate();
			 
			 if (affectedRows == 0){
				 throw new SQLException("Update training failed, no rows affected.");
			 }
			 
			returnJSON.put(Constant.STATUS, Constant.OK_200);
			returnJSON.put(Constant.USER_MESSAGE, "Updated training successfully!");
			returnJSON.put(Constant.DEVELOPER_MESSAGE, "Updated training successfully!");
			returnJSON.put(Constant.NEW_TRAINING_ID, training.getId());
			returnJSON.put(Constant.TRG_NEW_NAME, training.getName());
			returnJSON.put(Constant.TRG_NEW_DESCRIPTION, training.getDescription());
			
			boolean insertTrainingQuestionMap = createTrainingQuestionMap(training, training.getId());
			boolean insertTrainingImageMap = createTrainingImageMap(training, training.getId());
			
			if (!insertTrainingQuestionMap || !insertTrainingImageMap){
				returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
				returnJSON.put(Constant.USER_MESSAGE, "Could not update training.");
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Failed at updating Training map records.");
			}
			connection.close();
			
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
		
		return returnJSON;
	}
	
}

