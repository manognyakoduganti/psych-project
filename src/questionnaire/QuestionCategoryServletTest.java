package questionnaire;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import common.Constant;
import dao.QuestionCategoryDAO;

public class QuestionCategoryServletTest {
	
	QuestionCategoryServlet questionCategoryServlet;
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  questionCategoryServlet = new QuestionCategoryServlet();
	}
	
	
	@Test
	public void testValidQuestionCreate() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		
		String newQuestionCategoryName = "Fitness";
		String newnewQuestionCategoryDescription = "";
		long newResponseType = 9;
		String newStartLabel = "Very sad";
		String newEndLabel = "Very happy";
		
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_NAME, newQuestionCategoryName);
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_NAME_CATEGORY, newnewQuestionCategoryDescription);
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_RESPONSE_TYPE, newResponseType);
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_START_LABEL, newStartLabel);
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_END_LABEL, newEndLabel);
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.USER_ID)).thenReturn(4l);
		
		questionCategoryServlet.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		System.out.println(jsonObject.get(Constant.DEVELOPER_MESSAGE));
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.OK_200);
		
		boolean deleted = QuestionCategoryDAO.deleteQuestionCategory(jsonObject.get(Constant.NEW_QUESTION_CATETORY_NAME).toString());
		
		assertEquals(true, deleted);
	}
	
	@Test
	public void testInvalidQuestionCreate() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		
		String newQuestionCategoryName = "";
		String newnewQuestionCategoryDescription = "Designed for fitness group.";
		long newResponseType = 9;
		String newStartLabel = "Very sad";
		String newEndLabel = "Very happy";
		
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_NAME, newQuestionCategoryName);
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_NAME_CATEGORY, newnewQuestionCategoryDescription);
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_RESPONSE_TYPE, newResponseType);
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_START_LABEL, newStartLabel);
		jsonObj.put(Constant.NEW_QUESTION_CATETORY_END_LABEL, newEndLabel);
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.USER_ID)).thenReturn(4l);
		
		questionCategoryServlet.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		System.out.println(jsonObject.get(Constant.DEVELOPER_MESSAGE));
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.BADREQUEST_400);
	}

}
