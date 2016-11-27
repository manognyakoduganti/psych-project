package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
