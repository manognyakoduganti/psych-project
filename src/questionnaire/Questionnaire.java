package questionnaire;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import authentication.BuildStaticParameters;
import common.Constant;
import dao.ColorDAO;
import dao.QuestionDAO;
import dao.SessionDAO;
import fieldValidation.CommonFieldsVal;

/**
 * Servlet implementation class Questionnaire
 */
@WebServlet("/Questionnaire")
public class Questionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Questionnaire() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqType = request.getParameter("requestType");
		String sessionQuestion = request.getParameter("questionSession");
		String result = "";
				
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			if (BuildStaticParameters.conn == null) {
				BuildStaticParameters.buildConnectionWithSQL();
			}
			
			if (reqType.equalsIgnoreCase("request")) {
				String sqlRequest = "";
				if (sessionQuestion.equals("0")){ 
					sqlRequest = "select questionText from questionnaire where questionnaireType = 'B'";
				} else if (sessionQuestion.equals("1")){
					sqlRequest = "select questionText from questionnaire where questionnaireType = 'E'";
				}
				ResultSet rs = BuildStaticParameters.stmt.executeQuery(sqlRequest);
				ArrayList<String> questions = new ArrayList<String>();
				while(rs.next()) {
					questions.add(rs.getString(1));
				}
				result = getQuestionJSON(questions);
			} else if (reqType.equalsIgnoreCase("feedback")) {
				String[] questions = request.getParameterValues("question");
				String[] answers = request.getParameterValues("answer");
				String userID = request.getParameter("userID");
				// fix session ID 
				String sessionID = "";
				String sessionSql = "select usSessionNumber from userSession where usUserId = " + userID + " and usSessionDate in (select max(usSessionDate) from userSession where usUserId = " + userID + ")";
				ResultSet rs = BuildStaticParameters.stmt.executeQuery(sessionSql);
				if(rs.next()){
					sessionID = rs.getString(1);
				} else {
					sessionID = "1";
				}
				String sessionDate = request.getParameter("sessionDate");
				
				String questionType = sessionQuestion.equals("0")? "Start" : "End";
				
				String sql = "insert into questAns (qaSessionId, qaSessionDate, qaUserId, qaQuestion, qaAnswer, questionType) values (?,?,?,?,?,?)";
				PreparedStatement updateAnswers = BuildStaticParameters.conn.prepareStatement(sql);
				for (int i = 0; i < questions.length; i++) {
					updateAnswers.setString(1, sessionID);
					updateAnswers.setString(2, sessionDate);
					updateAnswers.setString(3, userID);
					updateAnswers.setString(4, questions[i]);
					updateAnswers.setString(5, answers[i]);
					updateAnswers.setString(6, questionType);
					updateAnswers.executeUpdate();
				}
				result = "{\"save\":successful}";
			}
			response.getWriter().write(result);
		} catch (SQLException se) {
			se.getStackTrace();
			response.getWriter().write("{\"save1\":\"unsuccessful\", \"error\":" + se.getMessage() + " }");
		} catch (Exception e) {
			e.getStackTrace();
			response.getWriter().write("{\"save2\":\"unsuccessful\", \"error\":" + e.getMessage() + " }");
		}
	}

	private String getQuestionJSON(ArrayList<String> questions) {
		String jsonString = "{questions: [";
		int i = 0;
		for (String s:questions) {
			if (i+1 >= questions.size())
				jsonString = jsonString + "{\"question\":\"" + s + "\"}";
			else
				jsonString =  jsonString + "{\"question\":\"" + s + "\"},";
			i++;
		}
		jsonString = jsonString + "]}";
		return jsonString;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json;charset=UTF-8");
		JSONObject returnJSON = new JSONObject();
		try{
	        //String questionSession = request.getParameter("questionSession");
	        String participantId = request.getParameter(Constant.PARTICIPANTID);
	        String sessionIdStr = request.getParameter(Constant.SESSION_ID);
	        String targetGroupId = request.getParameter(Constant.TG_ID);
	        String questionSession = request.getParameter(Constant.QUESTION_SESSION);
	        String[] responses = request.getParameterValues(Constant.RESPONSES);
	        
	       	boolean success = false;
	       	boolean validInput = false;
	       	long sessionId = -1;
	       	long latestSessionNumber = -2;
	       	if(responses.length > 0){
	       		try{
					Gson gson = new Gson();
					ArrayList<HashMap<String, String>> responseList = gson.fromJson(responses[0], new TypeToken<ArrayList<HashMap<String, String>>>() {}.getType());
					if(responseList.size() != 0  && questionSession != null && targetGroupId != null && participantId != null &&
							CommonFieldsVal.validateFieldId(targetGroupId) &&
							CommonFieldsVal.validateFieldId(participantId)){
						validInput = true;
						if(questionSession.equals(Constant.DEFAULT_QUESTION_SESSION)){
							// Generate Session Number
							latestSessionNumber = SessionDAO.getLatestSessionNumber(participantId);
							latestSessionNumber++;
							// Insert into Session Id
							sessionId = SessionDAO.createNewSession(participantId, latestSessionNumber);
							//Fetch two color randomly
							String[] colors = ColorDAO.getTwoRandomColors();
							// Save into parameter table
							boolean parameterCreated = SessionDAO.createNewParameters(sessionId, participantId, colors);
							if(colors.length != 2 || latestSessionNumber < 1 || parameterCreated == false){
								returnJSON.put(Constant.SAVE, Constant.UNSUCCESSFUL);
								return;
							}else{
								System.out.println("save question response");
								// Save the question response in the table.
								success = QuestionDAO.saveQuestionResponse(responseList, sessionId, participantId, Constant.START_STAGE);
								if(success){
									System.out.println("AFter entry session id is:"+sessionId);
									returnJSON.put(Constant.POSITIVE_COLOR, colors[0]);
									returnJSON.put(Constant.NEGATIVE_COLOR, colors[1]);
									returnJSON.put(Constant.SESSION_ID, Long.toString(sessionId));
								}
							}
						}else{
							//Save the question response in the table.
							if(CommonFieldsVal.validateFieldId(sessionIdStr) && sessionIdStr != null){
								validInput = true;
								success =  QuestionDAO.saveQuestionResponse(responseList, Long.parseLong(sessionIdStr), participantId, Constant.END_STAGE);
							}
						}
					}
	       		}catch(Exception e){
	       			e.printStackTrace();
	       			returnJSON.put(Constant.SAVE, Constant.UNSUCCESSFUL);
	       			if(questionSession.equals(Constant.DEFAULT_QUESTION_SESSION) && validInput == true){
	       				SessionDAO.deleteParameters(sessionId, participantId);
	       				SessionDAO.deleteSession(sessionId, latestSessionNumber);
	       			}
	       		}
			}
	       	if(success){
	       		returnJSON.put(Constant.SAVE, Constant.SUCCESSFUL);
			}else{
				returnJSON.put(Constant.SAVE, Constant.UNSUCCESSFUL);
				if(validInput){
					SessionDAO.deleteParameters(sessionId, participantId);
					SessionDAO.deleteSession(sessionId, latestSessionNumber);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			returnJSON.put(Constant.SAVE, Constant.UNSUCCESSFUL);
		}
		response.getWriter().print(returnJSON);
	}

}
