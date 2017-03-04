package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorDAO {
	
	private static Logger slf4jLogger = LoggerFactory.getLogger(ColorDAO.class);

	public static String[] getTwoRandomColors(){
		
		String selectQuery = "select count(*) from background";
		String color[] = new String[3];
		
		Connection connection = null;
		Object[] uniqueSet = null;
		slf4jLogger.info("Entered into getTwoRandomColors");
		
		try{
			connection = DBSource.getConnectionPool().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			
			int maxColors = 0;
			while(rs.next()) {
				maxColors = rs.getInt(1);
			}
			uniqueSet = generateRandomUniqueNumber(maxColors);
System.out.println("checking uniqueset1 in colourdao "+uniqueSet[0]);
System.out.println("checking uniqueset2 in colourdao "+uniqueSet[1]);
System.out.println("checking uniqueset3 in colourdao "+uniqueSet[2]);
			PreparedStatement pstmt =  connection.prepareStatement("select * from background where backgroundId in (?, ?, ?)");
			//Long[] values = new Long[] {, (Long) uniqueSet[1]};
			//System.out.println("values :"+values.toString());
			//Array array = connection.createArrayOf("BIGINT", values);
			//System.out.println("array : "+array);
			pstmt.setLong(1, (Long) uniqueSet[0]);
			pstmt.setLong(2, (Long) uniqueSet[1]);
                        pstmt.setLong(3, (Long) uniqueSet[2]);
			int i = 0;
			System.out.println("Color query :"+pstmt.toString());
			ResultSet rs1 = pstmt.executeQuery();
			while(rs1.next()){
				color[i] = rs1.getString(2);
System.out.println("rs1.get string in colourdao "+rs1.getString(2));
System.out.println("colour in colourdao "+color[i]);
				i++;
			}
			connection.close();
			
			return color;
			
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
		}
		return color;
		
	}
	
	public static Object[] generateRandomUniqueNumber(int maxColor) {
		int setSizeRequired = 3;
		
		Random randomGen = new Random();
		Set<Long> set = new HashSet<Long>(setSizeRequired);
		while(set.size() < setSizeRequired) {
			int i = randomGen.nextInt(maxColor);
			if (i != 0)
				set.add((long)i);
		}
		return set.toArray();
	}
}
