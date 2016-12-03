package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.Constant;
import edu.emory.mathcs.backport.java.util.Arrays;

public class ReportDAO {
	
	public static JSONObject getAvgResponseTimeForImageResponses(Long participantId){
		
		JSONObject returnJSON = new JSONObject();
		
		JSONObject results = new JSONObject();
		
		try{
			JSONObject avgCorrect = getAvgResponseTimeForImageResponsesHelper(participantId, 1);
			JSONObject avgWrong = getAvgResponseTimeForImageResponsesHelper(participantId, 0);
			
			results.put(Constant.AVG_IMAGE_RESPONSE_CORRECT, avgCorrect.get(Constant.RESULTS));
			results.put(Constant.AVG_IMAGE_RESPONSE_WRONG, avgWrong.get(Constant.RESULTS));
			returnJSON.put(Constant.RESULTS, results);
			returnJSON.put(Constant.STATUS, Constant.OK_200);
			returnJSON.put(Constant.USER_MESSAGE, "Successfully retrieved all average response times for image responses!");
			returnJSON.put(Constant.DEVELOPER_MESSAGE, "Successfully retrieved all average response times for image responses");
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
			returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
			returnJSON.put(Constant.USER_MESSAGE, "Error in retrieving average response times for image responses!");
			returnJSON.put(Constant.DEVELOPER_MESSAGE, "Error retrieving average response times for image responses!: " + e.getMessage());
		}
		
		return returnJSON;
	}
	
	
	public static JSONObject getAvgResponseTimeForImageResponsesHelper(Long pId, int correctness) throws SQLException{
		
		String selectQuery = "select a.sessionId, IFNULL(d.average, 0) as average, "
				+ "concat(c.name, ', ', e.fieldName) as imageCategoryAndType "
				+ "from psych.imageResponse as a "
				+ "left join psych.image as b on a.imageId = b.id "
				+ "left join psych.imageCategory as c on c.id=b.categoryId "
				+ "left join psych.fieldLookup as e on e.id=b.imageType "
				+ "left join "
				+ "(select c.sessionId, c.participantId, AVG(c.timeTaken) as average, e.name, f.fieldName "
				+ "from psych.imageResponse as c "
				+ "inner join psych.image as b on c.imageId = b.id "
				+ "inner join psych.imageCategory as e on e.id=b.categoryId "
				+ "inner join psych.fieldLookup as f on f.id=b.imageType "
				+ "where c.participantId = ? and c.isAttempted = 1 and c.correctness = ? "
				+ "group by c.sessionId, f.fieldName, e.name) as d on d.sessionId = a.sessionId "
				+ "and d.participantId = a.participantId and c.name=d.name and e.fieldName=d.fieldName "
				+ "where a.participantId = ? group by a.sessionId, d.average, c.name, e.fieldName;";
		
		JSONObject returnJSON = new JSONObject();
		JSONArray series = new JSONArray();
		JSONArray data = new JSONArray();
		
		
		Connection connection = null;
			
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setLong(1, pId);
			preparedStatement.setInt(2, correctness);
			preparedStatement.setLong(3, pId);
			
			// execute select SQL statement
			ResultSet rows = preparedStatement.executeQuery();
			
			String prevSeries = "start";
			
			String prevX = "start";
			
			
			JSONArray Y = new JSONArray();
			
			while (rows.next()){
				String currentX = rows.getString("sessionId");
				Double avg = rows.getDouble("average");
				String currentSeries = rows.getString("imageCategoryAndType");
				
				
				if(!currentX.equals(prevX) && !prevX.equals("start")){
					
					if(series.indexOf(prevSeries) == -1){
						series.add(prevSeries);
					}
					
					JSONObject object = new JSONObject();
					object.put("x", "SessionId: " + prevX);
					object.put("y", Y);
					data.add(object);
					
					Y = new JSONArray();
				}
				
				Y.add(avg);
				
				prevSeries = currentSeries;
				prevX = currentX;
			}
			
			JSONObject object = new JSONObject();
			object.put("x", "SessionId: " + prevX);
			object.put("y", Y);
			data.add(object);
			
			JSONObject results = new JSONObject();
			
			results.put(Constant.SERIES, series);
			results.put(Constant.DATA, data);
			
			returnJSON.put(Constant.RESULTS, results);
			connection.close();
			
			return returnJSON;
		
	}

}
