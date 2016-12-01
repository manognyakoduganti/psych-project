package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Constant;
import common.ImageInfo;

public class ImageDAO {
	
	private static Logger slf4jLogger = LoggerFactory.getLogger(ImageDAO.class);
	
	public static boolean createImage(ImageInfo imageInfo){
		
		slf4jLogger.info("Entered into createImage");
		
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
	
	public static boolean updateImage(ImageInfo imageInfo){
		
		slf4jLogger.info("Entered into updateImage");
		
		String insertQuery = "UPDATE IMAGE SET NAME = ?, DESCRIPTION = ? , CATEGORYID = ?, INTENSITY = ? , "
				+ "IMAGETYPE = ?, IMAGELOC = ? where id = ? ";
		
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
			
			preparedStatement.setLong(7, imageInfo.getId());
			
			slf4jLogger.info(preparedStatement.toString());
			int created = preparedStatement.executeUpdate();
			connection.close();
			if(created == 1) {
				slf4jLogger.info("Image details updated successfully");
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
	
	public static ArrayList<ImageInfo> fetchTrainingImageCategoryInfoByTargetGroupId(long tgId){
		
		slf4jLogger.info("Entered into fetchAllImageForTargetGroup");
		String selectQuery = "select tim.imageCategoryId, tim.duration, tim.imageType, fl.fieldName, tim.noOfImages "
				+ "from training as tr join targetgroup as tg on tr.id=tg.trainingId join trainingImageMap "
				+ "as tim on tr.id=tim.trainingId join fieldlookup as "
				+ "fl on fl.id = tim.imageType where tg.id=1;";
		
		ArrayList<ImageInfo> results = new ArrayList<ImageInfo>();
		Connection connection = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			ImageInfo imageInfo = new ImageInfo();
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				imageInfo.setImageCategoryId(rs.getLong("tim.imageCategoryId"));
				imageInfo.setDuration(rs.getLong("tim.duration"));
				imageInfo.setImageTypeId(rs.getLong("tim.imageType"));
				imageInfo.setImageType(rs.getString("fl.fieldName"));
				results.add(imageInfo);
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
		return results;
	}
	
	public static JSONArray fetchTrainingImageInfoByTargetGroupId(long tgId){
		
		slf4jLogger.info("Entered into fetchAllImageForTargetGroup");
		String selectQuery = "select id, imageLoc from image where categoryid = ? and imageType = ?";
		
		ArrayList<ImageInfo> results = ImageDAO.fetchTrainingImageCategoryInfoByTargetGroupId(tgId);
		Connection connection = null;
		JSONArray jsonArray = new JSONArray();
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			PreparedStatement  preparedStatement = connection.prepareStatement(selectQuery);
			int length = results.size();
			
			for(int i=0; i< length; i++){
				
				preparedStatement.setLong(1, results.get(i).getImageCategoryId());
				preparedStatement.setLong(2, results.get(i).getImageTypeId());
				ResultSet rs = preparedStatement.executeQuery();
				
				ArrayList<ImageInfo> images = new ArrayList<ImageInfo>();
				while(rs.next()) {
					ImageInfo imageInfo = new ImageInfo();
					imageInfo.setId(rs.getLong("id"));
					imageInfo.setImageShortPath(rs.getString("imageLoc"));
					images.add(imageInfo);
				}
				
				ArrayList<Integer> imageNumbers = new ArrayList<Integer>();
				int totalImages = images.size();
			    for (int j = 0; j < totalImages; j++) {
			        imageNumbers.add(j);
			    }
				long allowedImages = results.get(i).getNoOfImages();
				Collections.shuffle(imageNumbers);
				
				for(int k =0; k<allowedImages; k++){
					int randomImageIndex = imageNumbers.get(i);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put(Constant.IMAGE_CATEGORY_ID,  results.get(i).getImageCategoryId());
					jsonObject.put(Constant.IMAGE_TYPE, results.get(i).getImageType());
					jsonObject.put(Constant.IMAGE_TYPE_ID, results.get(i).getImageTypeId());
					jsonObject.put(Constant.IMAGE_DISPLAY_DURATION, results.get(i).getDuration());
					jsonObject.put(Constant.IMAGE_ID, images.get(randomImageIndex).getId());
					jsonObject.put(Constant.IMAGE_PATH, images.get(randomImageIndex).getImageShortPath());
					jsonArray.add(jsonObject);
				}
			}
			
			// execute select SQL statement
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
