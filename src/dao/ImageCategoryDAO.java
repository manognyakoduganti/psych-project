package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageCategoryDAO {

	private static Logger slf4jLogger = LoggerFactory.getLogger(ImageCategoryDAO.class);
	public static boolean deleteImageCategory(String name){
		
		String deleteQuery = "DELETE FROM IMAGECATEGORY WHERE NAME = ?";
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
	
	public static Long getImageCategoryIdByName(String name){
		
		String selectQuery = "SELECT id FROM imagecategory WHERE name = ?";
		
		Connection connection = null;
		Long id = null;
		
		try{
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			preparedStatement.setString(1, name);
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.first()) {
				id = rs.getLong("id");
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
		}
		return id;
	}
}
