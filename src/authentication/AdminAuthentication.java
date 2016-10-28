package authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.Constant;
import common.DatabaseQuery;

/**
 * Servlet implementation class AdminAuthentication
 */
@WebServlet("/AdminAuthentication")
public class AdminAuthentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAuthentication() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		JSONObject returnJSON = new JSONObject();
		//String email = request.getParameter(Constant.EMAIL);
		//String password = request.getParameter(Constant.PASSWORD);
		
		StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        
        JSONParser parser = new JSONParser();
		Object obj;
		String email="";
		String password="";
		
		try {
			obj = parser.parse(sb.toString());
			JSONObject jsonObject = (JSONObject) obj;
			email = (String) jsonObject.get(Constant.EMAIL);
			password = (String) jsonObject.get(Constant.PASSWORD);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		response.setContentType("application/json;charset=UTF-8");
		String selectQuery = "SELECT FIRSTNAME, LASTNAME, EMAIL, ROLE FROM ADMIN WHERE EMAIL = ? AND PASSWORD = ?";
		
		try{
			
			dbConnection = BuildStaticParameters.getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectQuery);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.first()) {
				String firstName = rs.getString("FIRSTNAME");
				String lastName = rs.getString("LASTNAME");
				int roleFieldID = rs.getInt("ROLE");
				String roleValue = DatabaseQuery.getFieldNameByFieldId(roleFieldID, dbConnection);
				
				HttpSession session = request.getSession(true);
				session.setAttribute(Constant.FIRST_NAME, firstName);
				session.setAttribute(Constant.LAST_NAME, lastName);
				session.setAttribute(Constant.ROLE, roleValue);
				session.setAttribute(Constant.EMAIL, email);
				
				returnJSON.put(Constant.VERIFIED, Constant.YES);
			}else{
				returnJSON.put(Constant.VERIFIED, Constant.NO);
			}
			response.getWriter().print(returnJSON);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		} finally {
			
			try{
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (dbConnection != null) {
					dbConnection.close();
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
