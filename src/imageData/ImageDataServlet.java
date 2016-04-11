package imageData;

import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import authentication.BuildStaticParameters;

/**
 * Servlet implementation class ImageDataServlet
 */
@WebServlet("/ImageDataServlet")
public class ImageDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/teenviolence";
    static final String USER = "root";
    static final String PASS = "msd1234";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageDataServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
			 
			 String correctness;
			 String param[] = request.getParameterValues("param");
			 
			 String isAttempted = param[0]; 
			 String responseTime = param[1];
			 //String responseTime = "0.12";
			 String isPositive = param[2];
			 String bgColor = param[3];
			 String responseAccurate=param[4];
			 String expectedResult = request.getParameter("expected");
			 //String expectedResult = "0";
			 
			 String actualResult = request.getParameter("actual");
			 //String actualResult = "1";
			 if(isAttempted.equalsIgnoreCase("false")){
				 correctness = "not awswered";
			 }else{
				 correctness=responseAccurate.equals("true")?"right":"wrong";
			 }
			 	 
			 String userid = param[5];
			 //String userid = "1";
			 
			 if (BuildStaticParameters.conn == null) {
					BuildStaticParameters.buildConnectionWithSQL();
				}
			 
			 
			 
			 String sql;
			 sql = "insert into response (respCorrectness,respTimeTaken,respActualResult,respUser,respBGColor) values ('"
			 + correctness + "', '"  + responseTime +"','"+ actualResult + "','" + userid + "','" + bgColor +"' ) ";
			 
			 BuildStaticParameters.stmt.executeUpdate(sql);
		
			
			 
			 response.setContentType("application/json");
			 response.setCharacterEncoding("UTF-8");
			 
			 
			 
			 response.getWriter().write("{\"result\":true}");
			
		 } catch(SQLException se){
	         //Handle errors for JDBC
	         se.printStackTrace();
	      }catch(Exception e){
	         //Handle errors for Class.forName
	         e.printStackTrace();
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
