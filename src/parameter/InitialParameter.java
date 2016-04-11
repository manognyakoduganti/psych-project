package parameter;

import authentication.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InitialParameter
 */
@WebServlet("/InitialParameter")
public class InitialParameter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitialParameter() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String userID = request.getParameter("userID");
		
		if (BuildStaticParameters.conn == null) {
			BuildStaticParameters.buildConnectionWithSQL();
		}
		
		String sql = "select count(*) from background";
		
		try {
			ResultSet rs = BuildStaticParameters.stmt.executeQuery(sql);
			int maximum_number = 0;
			while(rs.next()) {
				maximum_number = rs.getInt(1);
			}
			Set<Integer> uniqueSet = new HashSet<Integer>();
			uniqueSet = generateRandomUniqueNumber(maximum_number);
			String numberColor = "";
			int j = 0;
			for (Integer i : uniqueSet) {
				if (j == 1)
					numberColor = numberColor + i.toString();
				else
					numberColor = numberColor + i.toString() + ", ";
			}
			
			String sqlColor = "select bgcolor from background where backgroundID in ("
								+ numberColor + ")";
			
			ResultSet rs1 = BuildStaticParameters.stmt.executeQuery(sqlColor);
			String color[] = new String[2];
			int i = 0;
			while(rs1.next()){
				color[i] = rs.getString(1);
				i++;
			}
			
			String sql3 = "select userAge from user where userId = '" + userID + "'"; 
			ResultSet rs2 = BuildStaticParameters.stmt.executeQuery(sql3);
			int age = 0;
			while (rs2.next()){
				age = rs2.getInt(1);
			}
			Float timeInterval = getTimeInterval(age);
			
			String sql4 = "select usSessionNumber from userSession where "
					+ "usUserId ='" + userID + "'";
			
			ResultSet rs3 = BuildStaticParameters.stmt.executeQuery(sql4);
			Date sessionDate = new Date();
			int sessionID = 1;
			if(!rs3.next()) {
				sql4 = "insert into userSession (usUserId, usSessionNumber, usSessionDate) values ('" +
						userID + "', '" + sessionID + "', '" + sessionDate.toString() + "'";
				BuildStaticParameters.stmt.executeUpdate(sql4);
			} else {
				while (rs3.next()){
					sessionID = rs3.getInt("usSessionNumber");
				}
				sql4 = "insert into userSession (usUserId, usSessionNumber, usSessionDate) values ('" +
						userID + "', '" + sessionID++ + "', '" + sessionDate.toString() + "'";
				BuildStaticParameters.stmt.executeUpdate(sql4);
			}
			String result = getJSONStringParameters(color, timeInterval, sessionID);
			response.getWriter().write(result);
			
			BuildStaticParameters.stmt.close();
			BuildStaticParameters.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private float getTimeInterval(int age) {
		return 10;
	}

	private String getJSONStringParameters(String[] colors, Float timeInterval, int sessionId) {
		String result = "";
		String time = timeInterval.toString();
			result = "{\"positiveColor\":" + colors[0] + 
					"\"negativeColor\":" + colors[1] + 
					",\"timeInterval\":" + time +   
					",\"totalGames\": 20" +
					",\"sessionID\":" + sessionId + "}";
		return result;
	}

	private Set<Integer> generateRandomUniqueNumber(int maximum_number) {
		int set_size_required = 2;
		int set_maximum_size = maximum_number;
		
		Random randomGen = new Random();
		Set<Integer> set = new HashSet<Integer>(set_size_required);
		
		while(set.size() < set_size_required) {
			while(set.add(randomGen.nextInt(set_maximum_size))!= true);
		}
		return set;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
