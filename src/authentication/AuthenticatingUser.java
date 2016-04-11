package authentication;

import java.io.IOException;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import com.google.gson.Gson;
//import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

//import com.google.gson.Gson;

/**
 * Servlet implementation class AuthenticatingUser
 */
@WebServlet("/AuthenticatingUser")
public class AuthenticatingUser extends HttpServlet {
	
	
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthenticatingUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String tryToLogin(HttpServletRequest request) throws SQLException {
		
		String username = request.getParameter("username");

		String password = request.getParameter("password");
		String result = "0";
		String sql;
		sql = "SELECT userLoginId FROM userLogin where ulUserName = '" + username + "'and ulPassword = '" + password + "'";
		ResultSet rs = BuildStaticParameters.stmt.executeQuery(sql);
		String id="-1";
		while(rs.next()){
			result = "1";
			id=rs.getString(1);
		}
		
		return ("{\"success\":"+result+",\"userId\":"+id+"}");
	}
	
	public String loginOrInsertRecord(HttpServletRequest request) throws SQLException{
		String result = "";
		String check = request.getParameter("queryType");
		if (check.equalsIgnoreCase("login")) {
			result = tryToLogin(request);
		}
		if (check.equalsIgnoreCase("register")) {
			result=insertUserRecord(request);
		}
		return result;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.setContentType("text/html");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			if (BuildStaticParameters.conn == null) {
				BuildStaticParameters.buildConnectionWithSQL();
			}
			response.setCharacterEncoding("UTF-8");
			String result=loginOrInsertRecord(request);
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String insertUserRecord(HttpServletRequest request){
		try{
		String age =request.getParameter("");
		String gender=request.getParameter("");
		String Ethnicity=request.getParameter("");
		String disability=request.getParameter("");
		String education=request.getParameter("");
		String mobExp=request.getParameter("");
		String anyMedi=request.getParameter("");
		String PsycothereputicMedications=request.getParameter(""); 
		String Colorblind=request.getParameter("");
		/*
		String sql = "INSERT INTO user(userAge,userGender,userEnthicity,userDisability,userEducation,"
				+ "userMobileHandlingExperience,userAnyMedications,"
				+ "userPsycothereputicMedications,userColorblind,) " + "VALUES ("+age+","+gender+",'"+Ethnicity+"','"+
				disability+"','"+education+"','"+mobExp+"','"+
				anyMedi+"',"+PsycothereputicMedications+","+Colorblind+")";
		BuildStaticParameters.stmt.executeQuery(sql);
			return "done";*/
			return ("{\"status\":1,\"message\":success}");
		}catch(Exception e){
			return ("{\"status\":0,\"message\":"+e.getMessage()+"}");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
