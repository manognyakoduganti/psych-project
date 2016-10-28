package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQuery {
	
	public static String getFieldNameByFieldId(int fieldId, Connection dbConnection) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		
		String selectQuery = "SELECT FIELDNAME FROM FIELDLOOKUP WHERE ID = ?";

		preparedStatement = dbConnection.prepareStatement(selectQuery);
		preparedStatement.setInt(1, fieldId);
		
		// execute select SQL statement
		ResultSet rs = preparedStatement.executeQuery();
		//System.out.println(rs.getFetchSize());
		String fieldName = null;
		while (rs.next()) {
			fieldName = rs.getString("FIELDNAME");
		}
		return fieldName;
	}

}
