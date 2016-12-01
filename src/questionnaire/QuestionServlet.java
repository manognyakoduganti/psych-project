package questionnaire;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

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
import common.Question;
import common.QuestionCategory;
import common.Sessions;
import dao.QuestionCategoryDAO;
import dao.QuestionDAO;
import fieldValidation.QuestionCategoryFieldsVal;

/**
 * Servlet implementation class QuestionServlet
 */
@WebServlet("/question")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", Constant.ACCESS_CONTROL_ALLOW_ORIGIN);
		response.addHeader("Access-Control-Allow-Headers", Constant.ACCESS_CONTROL_ALLOW_HEADERS);
		response.addHeader("Access-Control-Allow-Methods", Constant.ACCESS_CONTROL_ALLOW_METHODS);
		response.addIntHeader("Access-Control-Max-Age", Constant.ACCESS_CONTROL_ALLOW_MAX_AGE);
		
		JSONObject returnJSON = new JSONObject();
		
		HttpSession session = request.getSession(false);
		if(Sessions.isValidGlobalAdminSession(session)){
			String name = request.getParameter("name");
			
			String targetGroupId = request.getParameter(Constant.TARGET_GROUP_ID);
			System.out.println(request.toString());
			System.out.println("question servlet");
			System.out.println(targetGroupId);
			
			if (name != null){
				
				boolean duplicate = QuestionDAO.checkDuplicate(name);
				
				returnJSON.put(Constant.STATUS, Constant.OK_200);
				returnJSON.put(Constant.RESULTS, duplicate);
				returnJSON.put(Constant.USER_MESSAGE, "Searched for duplicate successfully!");
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Searched for duplicate successfully");
			}
			else if(targetGroupId != null){
				returnJSON = QuestionDAO.getAllQuestionsByTargetGroupId(Long.parseLong(targetGroupId));
			}
			else{
				
				returnJSON = QuestionDAO.getAll();
			}
			
			//System.out.println(name);
			
			response.setContentType("application/json;charset=UTF-8");
		}else{
			returnJSON.put(Constant.STATUS, Constant.UNAUTHORIZED_401);
		}
		response.getWriter().print(returnJSON);
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
		
		String newName;
		String newDescrition;
		long newQuestionCategoryId;
		
		HttpSession session = request.getSession(false);
		
		if(Sessions.isValidGlobalAdminSession(session)){
			
			try {
				obj = parser.parse(sb.toString());
				JSONObject jsonObject = (JSONObject) obj;
				newName = (String) jsonObject.get(Constant.NEW_QUESTION_NAME);
				newDescrition = (String) jsonObject.get(Constant.NEW_QUESTION_DESCRIPTION);
				newQuestionCategoryId = (long) jsonObject.get(Constant.NEW_QUESTION_CATEGORY_ID);
				
				if(QuestionCategoryFieldsVal.validateQuestionName(newName) 
						&& QuestionCategoryFieldsVal.validateDescription(newDescrition)){
				
						
					//System.out.println(newFirstName+ ":"+newLastName + ":"+newEmail + ":" + newPassword +" :" + email);
					returnJSON  = QuestionDAO.createQuestion(new Question(1, newName, newDescrition, newQuestionCategoryId));
					//System.out.println("isUpdated : " + isUpdated);
				}else{
					returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
					returnJSON.put(Constant.USER_MESSAGE, "Invalid data in fields");
					returnJSON.put(Constant.DEVELOPER_MESSAGE, "Invalid data in fields");
				}
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
				returnJSON.put(Constant.DEVELOPER_MESSAGE, Constant.JSON_PARSE_ERROR + sb.toString());
			}
			
		}else{
			returnJSON.put(Constant.STATUS, Constant.UNAUTHORIZED_401);
			returnJSON.put(Constant.USER_MESSAGE, Constant.UNAUTHORIZED_401);
			returnJSON.put(Constant.DEVELOPER_MESSAGE, Constant.UNAUTHORIZED_401);
		}
		
		response.getWriter().print(returnJSON);
		response.addHeader("Access-Control-Allow-Origin", Constant.ACCESS_CONTROL_ALLOW_ORIGIN);
		response.addHeader("Access-Control-Allow-Headers", Constant.ACCESS_CONTROL_ALLOW_HEADERS);
		response.addHeader("Access-Control-Allow-Methods", Constant.ACCESS_CONTROL_ALLOW_METHODS);
		response.addIntHeader("Access-Control-Max-Age", Constant.ACCESS_CONTROL_ALLOW_MAX_AGE);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
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
		
		String newName;
		String newDescrition;
		long newQuestionCategoryId;
		long questionId;
		
		HttpSession session = request.getSession(false);
		
		if(Sessions.isValidGlobalAdminSession(session)){
			
			try {
				obj = parser.parse(sb.toString());
				JSONObject jsonObject = (JSONObject) obj;
				newName = (String) jsonObject.get(Constant.QUESTION_NAME);
				newDescrition = (String) jsonObject.get(Constant.QUESTION_DESCRIPTION);
				newQuestionCategoryId = (long) jsonObject.get(Constant.QUESTION_CATEGORY_ID);
				questionId = (long) jsonObject.get(Constant.QUESTION_ID);
				
				if(QuestionCategoryFieldsVal.validateQuestionName(newName) 
						&& QuestionCategoryFieldsVal.validateDescription(newDescrition)){
				
						
					//System.out.println(newFirstName+ ":"+newLastName + ":"+newEmail + ":" + newPassword +" :" + email);
					returnJSON  = QuestionDAO.updateQuestion(new Question(questionId, newName, newDescrition, newQuestionCategoryId));
					//System.out.println("isUpdated : " + isUpdated);
				}else{
					returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
					returnJSON.put(Constant.USER_MESSAGE, "Invalid data in fields");
					returnJSON.put(Constant.DEVELOPER_MESSAGE, "Invalid data in fields");
				}
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
				returnJSON.put(Constant.DEVELOPER_MESSAGE, Constant.JSON_PARSE_ERROR + sb.toString());
			}
			
		}else{
			returnJSON.put(Constant.STATUS, Constant.UNAUTHORIZED_401);
			returnJSON.put(Constant.USER_MESSAGE, Constant.UNAUTHORIZED_401);
			returnJSON.put(Constant.DEVELOPER_MESSAGE, Constant.UNAUTHORIZED_401);
		}
		
		response.getWriter().print(returnJSON);
		response.addHeader("Access-Control-Allow-Origin", Constant.ACCESS_CONTROL_ALLOW_ORIGIN);
		response.addHeader("Access-Control-Allow-Headers", Constant.ACCESS_CONTROL_ALLOW_HEADERS);
		response.addHeader("Access-Control-Allow-Methods", Constant.ACCESS_CONTROL_ALLOW_METHODS);
		response.addIntHeader("Access-Control-Max-Age", Constant.ACCESS_CONTROL_ALLOW_MAX_AGE);
	}

}