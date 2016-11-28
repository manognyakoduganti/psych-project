package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Constant;
import common.ImageInfo;

public class ImageDAO {
	
	private static Logger slf4jLogger = LoggerFactory.getLogger(ImageDAO.class);
	
	public static boolean createImage(ImageInfo imageInfo){
		
		slf4jLogger.info("Entered into createImageCategory");
		
		String insertQuery = "INSERT INTO IMAGE (NAME, DESCRIPTION, CATEGORYID, INTENSITY, IMAGETYPE, IMAGELOC) "
				+ "values (?, ?, ?, ?, ?, ?)";
		
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			
			preparedStatement.setString(1, imageInfo.getImageName());
			preparedStatement.setString(2, imageInfo.getImageDesc());
			preparedStatement.setLong(3, imageInfo.getImageCategoryId());
			preparedStatement.setLong(4, imageInfo.getImageIntensity());
			preparedStatement.setLong(5, imageInfo.getImageTypeId());
			preparedStatement.setString(6, imageInfo.getImageShortPath());
			
			slf4jLogger.info(preparedStatement.toString());
			int created = preparedStatement.executeUpdate();
			connection.close();
			if(created == 1) {
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

	public static Long getImageIdByImageName(String name){
		
		slf4jLogger.info("Entered into getImageIdByImageName");
		
		String selectQuery = "SELECT id FROM image WHERE name = ?";
		Connection connection = null;
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, name);
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			Long id = null;
			if(rs.first()) {
				id = rs.getLong("id");
			}
			connection.close();
			return id;
		}catch(SQLException e){
			try {
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	public static void deleteImageById(Long id){
		
		slf4jLogger.info("Entered into deleteImageById");
		String selectQuery = "DELETE FROM image WHERE id = ?";
		
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setLong(1, id);
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
	
	public static boolean isDuplicateImage(String name){
		
		slf4jLogger.info("Entered into isDuplicateImage");
		
		String selectQuery;
		selectQuery = "SELECT * FROM image WHERE NAME = ?";
		Connection connection = null;
		
		boolean isDuplicate = false;
		
		try{
			connection = DBSource.getConnectionPool().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, name);
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.first()) {
				isDuplicate = true;
			}else{
				isDuplicate = false;
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
			isDuplicate = false;
		}
		
		return isDuplicate;
	}
	
	public static JSONArray fetchAllImage(){
		
		slf4jLogger.info("Entered into fetchAllImage");
		String selectQuery = "SELECT im.id, im.name, im.description, im.categoryId, im.intensity, "
				+ "im.imagetype, im.imageloc, fl.fieldName, ic.name FROM image as im join fieldlookup "
				+ "as fl on im.imageType=fl.id join imagecategory as ic on ic.id =  im.categoryId;";
		
		Connection connection = null;
		JSONArray jsonArray = new JSONArray();
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(Constant.IMAGE_ID, rs.getLong("im.id"));
				jsonObject.put(Constant.IMAGE_NAME, rs.getString("im.name"));
				jsonObject.put(Constant.IMAGE_DESCRIPTION, rs.getString("im.description"));
				jsonObject.put(Constant.IMAGE_CATEGORY_ID,rs.getLong("im.categoryId"));
				jsonObject.put(Constant.IMAGE_INTENSITY,rs.getLong("im.intensity"));
				jsonObject.put(Constant.IMAGE_TYPE_ID,rs.getLong("im.imagetype"));
				jsonObject.put(Constant.IMAGE_PATH,rs.getString("im.imageloc"));
				jsonObject.put(Constant.IMAGE_CATEGORY, rs.getString("fl.fieldName"));
				jsonObject.put(Constant.IMAGE_TYPE, rs.getString("ic.name"));
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
}
