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

import common.AdminDetails;
import common.Constant;
import dao.Authentication;

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
		response.setContentType("application/json;charset=UTF-8");
		JSONObject returnJSON = new JSONObject();
		
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
		
		String selectQuery = "SELECT FIRSTNAME, LASTNAME, EMAIL, ROLE FROM ADMIN WHERE EMAIL = ? AND PASSWORD = ?";
		
		AdminDetails adminDetails = Authentication.validateUser(email, password);
		if(adminDetails != null){
			HttpSession session = request.getSession(true);
			session.setAttribute(Constant.FIRST_NAME, adminDetails.getFirstName());
			session.setAttribute(Constant.LAST_NAME, adminDetails.getLastName());
			session.setAttribute(Constant.ROLE, adminDetails.getRole());
			session.setAttribute(Constant.USER_ID, adminDetails.getUserId());
			session.setAttribute(Constant.EMAIL, email);
			
			returnJSON.put(Constant.FIRST_NAME, adminDetails.getFirstName());
			returnJSON.put(Constant.LAST_NAME, adminDetails.getLastName());
			returnJSON.put(Constant.ROLE, adminDetails.getRole());
			returnJSON.put(Constant.EMAIL, email);
			
			returnJSON.put(Constant.VERIFIED, Constant.OK_200);
		}else{
			returnJSON.put(Constant.VERIFIED, Constant.UnAuthorized_401);
		}
		response.getWriter().print(returnJSON);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
