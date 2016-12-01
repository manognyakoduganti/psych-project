package questionnaire;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import common.Constant;
import registration.Register;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class QuestionnaireTest {

	Questionnaire question;
	String sessionID;

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  question = new Questionnaire();
	  sessionID = "";
	}

	@Test
	public void testInOrderQuestionResponseSaveReqeust() throws IOException, ServletException, ParseException{
		testValidStartQuestionResponseSaveRequest();
		testValidEndQuestionResponseSaveRequest();
	}
	
	public void testValidStartQuestionResponseSaveRequest() throws IOException, ServletException, ParseException {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		when(response.getWriter()).thenReturn(printWriter);
		
		when(request.getParameter(Constant.PARTICIPANTID)).thenReturn("1");
		when(request.getParameter(Constant.SESSION_ID)).thenReturn("-1");
		when(request.getParameter(Constant.TG_ID)).thenReturn("1");
		when(request.getParameter(Constant.RESPONSES)).thenReturn(mockResponseData());

		question.doPost(request, response);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals("System should have processed the request to insert question response", Constant.SUCCESSFUL,
				(String) jsonObject.get(Constant.SAVE));
		
		sessionID = (String) jsonObject.get(Constant.SESSION_ID);
		assertTrue(sessionID!=null);
		assertTrue(!sessionID.equals("-1"));
	}

	public void testValidEndQuestionResponseSaveRequest() throws IOException, ServletException, ParseException {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		when(response.getWriter()).thenReturn(printWriter);

		when(request.getParameter(Constant.PARTICIPANTID)).thenReturn("1");
		when(request.getParameter(Constant.SESSION_ID)).thenReturn(sessionID);
		when(request.getParameter(Constant.TG_ID)).thenReturn("1");
		when(request.getParameter(Constant.RESPONSES)).thenReturn(mockResponseData());

		question.doPost(request, response);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		assertEquals("System should have processed the request to insert question response", Constant.SUCCESSFUL,
				(String) jsonObject.get(Constant.SAVE));
	}

	@Test
	public void testInvalidQuestionResoponseSaveRequest() throws IOException, ServletException, ParseException {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		when(request.getParameter(Constant.PARTICIPANTID)).thenReturn("1");
		when(request.getParameter(Constant.SESSION_ID)).thenReturn("-1");
		when(request.getParameter(Constant.TG_ID)).thenReturn("1");
		when(request.getParameter(Constant.RESPONSES)).thenReturn("");

		when(response.getWriter()).thenReturn(printWriter);

		question.doPost(request, response);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		assertEquals("System should not processess the request to insert question response", Constant.UNSUCCESSFUL,
				(String) jsonObject.get(Constant.SAVE));
	}
	
	@Test
	public void testInvalidQuestionResoponseSaveRequestWhenTargetIdIsInvalid() throws IOException, ServletException, ParseException {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		when(response.getWriter()).thenReturn(printWriter);

		when(request.getParameter(Constant.PARTICIPANTID)).thenReturn("1");
		when(request.getParameter(Constant.SESSION_ID)).thenReturn("-1");
		when(request.getParameter(Constant.TG_ID)).thenReturn("");
		when(request.getParameter(Constant.RESPONSES)).thenReturn(mockResponseData());

		question.doPost(request, response);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		assertEquals("System should not processess the request to insert question response when target id is invalid", Constant.UNSUCCESSFUL,
				(String) jsonObject.get(Constant.SAVE));
	}	
	
	@Test
	public void testInvalidQuestionResoponseSaveRequestWhenParticipantIdIsInvalid() throws IOException, ServletException, ParseException {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		when(response.getWriter()).thenReturn(printWriter);

		when(request.getParameter(Constant.PARTICIPANTID)).thenReturn("");
		when(request.getParameter(Constant.SESSION_ID)).thenReturn("-1");
		when(request.getParameter(Constant.TG_ID)).thenReturn("1");
		when(request.getParameter(Constant.RESPONSES)).thenReturn(mockResponseData());

		question.doPost(request, response);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		assertEquals("System should not processess the request to insert question response when participant id is invalid", Constant.UNSUCCESSFUL,
				(String) jsonObject.get(Constant.SAVE));
	}
	
	public static String mockResponseData(){
		
		JSONObject obj1 = new JSONObject();
		obj1.put("responseType", "Continuous");
		obj1.put("response", "3");
		obj1.put("questionId", "1");

		JSONObject obj2 = new JSONObject();
		obj2.put("responseType", "Categorical");
		obj2.put("response", "StartLabel2");
		obj2.put("questionId", "2");

		JSONObject obj3 = new JSONObject();
		obj3.put("responseType", "Categorical");
		obj3.put("response", "Very Happy");
		obj3.put("questionId", "3");
		
		JSONObject obj4 = new JSONObject();
		obj4.put("responseType", "Categorical");
		obj4.put("response", "Very Happy");
		obj4.put("questionId", "4");
		
		JSONObject obj5 = new JSONObject();
		obj5.put("responseType", "Categorical");
		obj5.put("response", "Very Happy");
		obj5.put("questionId", "5");
		
		JSONObject obj6 = new JSONObject();
		obj6.put("responseType", "Categorical");
		obj6.put("response", "Very Happy");
		obj6.put("questionId", "6");
		
		JSONObject obj7 = new JSONObject();
		obj7.put("responseType", "Categorical");
		obj7.put("response", "EndLabel2");
		obj7.put("questionId", "7");
		
		JSONObject obj8 = new JSONObject();
		obj8.put("responseType", "Categorical");
		obj8.put("response", "Very Happy");
		obj8.put("questionId", "8");
		
		JSONObject obj9 = new JSONObject();
		obj9.put("responseType", "Categorical");
		obj9.put("response", "EndLabel2");
		obj9.put("questionId", "9");	
		
		JSONObject obj10 = new JSONObject();
		obj10.put("responseType", "Categorical");
		obj10.put("response", "Very Happy");
		obj10.put("questionId", "10");
		
		JSONObject obj11 = new JSONObject();
		obj11.put("responseType", "Categorical");
		obj11.put("response", "Very Happy");
		obj11.put("questionId", "11");
		
		ArrayList<String> aList = new ArrayList<>();
		aList.add(obj1.toString());
		aList.add(obj2.toString());
		aList.add(obj3.toString());
		aList.add(obj4.toString());
		aList.add(obj5.toString());
		aList.add(obj6.toString());
		aList.add(obj7.toString());
		aList.add(obj8.toString());
		aList.add(obj9.toString());
		aList.add(obj10.toString());
		aList.add(obj11.toString());
			
		//System.out.println(aList.toString());
		
		return aList.toString();	
		
	}
}
