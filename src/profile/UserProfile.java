package profile;

import java.io.BufferedReader;
import java.io.IOException;

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
import dao.UserProfileDAO;
import fieldValidation.UserProfileVal;

/**
 * Servlet implementation class UserProfile
 */
@WebServlet("/UserProfile")
public class UserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		String newEmail="";
		String newPassword="";
		String newFirstName="";
		String newLastName="";
		
		HttpSession session = request.getSession(false);
		
		if(session != null && session.getAttribute(Constant.ROLE) != null && 
				(session.getAttribute(Constant.ROLE).equals(Constant.GLOBAL_ADMIN) ||
				(session.getAttribute(Constant.ROLE).equals(Constant.LOCAL_ADMIN)))){
			
			Long userId= (Long)session.getAttribute(Constant.USER_ID);
			
			try {
				obj = parser.parse(sb.toString());
				JSONObject jsonObject = (JSONObject) obj;
				email = (String) jsonObject.get(Constant.EMAIL);
				newEmail = (String) jsonObject.get(Constant.NEW_EMAIL);
				newPassword = (String) jsonObject.get(Constant.NEW_PASSWORD);
				newFirstName = (String) jsonObject.get(Constant.NEW_FIRST_NAME);
				newLastName = (String) jsonObject.get(Constant.NEW_LAST_NAME);
			
				if(UserProfileVal.validateEmail(newEmail) && UserProfileVal.validateName(newFirstName) && 
						UserProfileVal.validateName(newLastName) && UserProfileVal.validatePassword(newPassword)){
					
					boolean isDuplicate = UserProfileDAO.isDuplicateEmail(newEmail, userId);
					System.out.println("isDuplicate : " + isDuplicate);
					if(!isDuplicate){
						
						System.out.println(newFirstName+ ":"+newLastName + ":"+newEmail + ":" + newPassword +" :" + email);
						boolean isUpdated = UserProfileDAO.udpateUserProfile(newFirstName, newLastName, newEmail, newPassword, email);
						System.out.println("isUpdated : " + isUpdated);
						if(isUpdated){
							
							returnJSON.put(Constant.FIRST_NAME, newFirstName);
							returnJSON.put(Constant.LAST_NAME, newLastName);
							returnJSON.put(Constant.EMAIL, newEmail);
							
							session.setAttribute(Constant.FIRST_NAME, newFirstName);
							session.setAttribute(Constant.LAST_NAME, newLastName);
							session.setAttribute(Constant.EMAIL, newEmail);
							
							returnJSON.put(Constant.STATUS, Constant.OK_200);
							
						}else{
							returnJSON.put(Constant.STATUS, Constant.BadRequest_400);
						}
					}else{
						returnJSON.put(Constant.STATUS, Constant.BadRequest_400);
					}
				}else{
					returnJSON.put(Constant.STATUS, Constant.BadRequest_400);
				}
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				returnJSON.put(Constant.STATUS, Constant.BadRequest_400);
			}
			
		}else{
			returnJSON.put(Constant.STATUS, Constant.UnAuthorized_401);
		}
		
		response.getWriter().print(returnJSON);
	}

}
