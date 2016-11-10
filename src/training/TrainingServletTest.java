package training;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import common.Constant;
import location.LocationServlet;

public class TrainingServletTest {
	
	TrainingServlet trainingServlet;
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  trainingServlet = new TrainingServlet();
	}
	
	@Test
	public void testInValidSessionTrainingDropDownRequest() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(false)).thenReturn(null);
		
		trainingServlet.doGet(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals("System should have blocked the request with invalid session"
				,Constant.UNAUTHORIZED_401, (String) jsonObject.get(Constant.STATUS));
		
	}
	
	
	@Test
	public void testValidTrainingDropDownRequest() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		when(session.getAttribute(Constant.USER_ID)).thenReturn(1);
		when(request.getParameter(Constant.TRAINING_DROPDOWN)).thenReturn(Constant.YES);
		
		trainingServlet.doGet(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals("System should have successfully processed the search request with valid session and search parameter",
				Constant.OK_200, (String) jsonObject.get(Constant.STATUS));
		
		assertTrue("System should have returned at least one location information.",((JSONArray)jsonObject.get(Constant.RESULTS)).size() >= 1);
		
	}

}
