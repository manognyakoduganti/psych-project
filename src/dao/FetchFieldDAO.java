package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.CommonFields;

public class FetchFieldDAO {
	
	public static List<CommonFields> extractFieldValues(String fieldName){
		String selectQuery = "SELECT ID, FIELDNAME FROM FIELDLOOKUP WHERE GROUPNAME = ?";
		
		List<CommonFields> fieldValueList = new ArrayList<CommonFields>();
		try{
			
			Connection connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			
			preparedStatement.setString(1, fieldName);
			
			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				CommonFields commonFields = new CommonFields();
				commonFields.setFieldId(rs.getLong("ID"));
				commonFields.setFieldValue(rs.getString("FIELDNAME"));
				fieldValueList.add(commonFields);
			}
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return fieldValueList;
		} 
		catch(Exception e){
			System.out.println(e.getMessage());
			return fieldValueList;
		} 
		return fieldValueList;
	}

}
