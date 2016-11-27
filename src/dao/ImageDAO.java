package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

}
