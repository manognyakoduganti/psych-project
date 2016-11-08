package profile;

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

public class UserProfileServletTest {
	
	UserProfileServlet userProfileUpdate;
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  userProfileUpdate = new UserProfileServlet();
	}
	
	@Test
	public void testValidUserProfileUpdate() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		
		String newFirstName = "Darshan";
		String newLastName = "Patel";
		String newEmail = "patel.dars@husky.neu.edu";
		String newPassword = "Abcde@12345";
		
		jsonObj.put(Constant.NEW_FIRST_NAME, newFirstName);
		jsonObj.put(Constant.NEW_LAST_NAME, newLastName);
		jsonObj.put(Constant.NEW_EMAIL, newEmail);
		jsonObj.put(Constant.NEW_PASSWORD, newPassword);
		jsonObj.put(Constant.EMAIL, "patel.dars@husky.neu.edu");
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		
		userProfileUpdate.doPut(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.OK_200);
		assertEquals((String) jsonObject.get(Constant.EMAIL), newEmail);
		assertEquals((String) jsonObject.get(Constant.FIRST_NAME), newFirstName);
		assertEquals((String) jsonObject.get(Constant.LAST_NAME), newLastName);
		
		verify(session).setAttribute(Constant.EMAIL, newEmail);
		verify(session).setAttribute(Constant.FIRST_NAME, newFirstName);
		verify(session).setAttribute(Constant.LAST_NAME, newLastName);
		
	}
	
	@Test
	public void testInvalidSessionUserProfileUpdate() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put(Constant.NEW_FIRST_NAME, "David");
		jsonObj.put(Constant.NEW_LAST_NAME, "Martin");
		jsonObj.put(Constant.NEW_EMAIL, "patel.dars@husky.neu.edu");
		jsonObj.put(Constant.NEW_PASSWORD, "23jsdf@awAer");
		jsonObj.put(Constant.EMAIL, "patel.dars@husky.neu.edu");
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		//Not Valid Session 
		when(request.getSession(false)).thenReturn(session);
		
		userProfileUpdate.doPut(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.UNAUTHORIZED_401);
		
	}
	
	@Test
	public void testInvalidUserProfileUpdate() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put(Constant.NEW_FIRST_NAME, "Martin");
		jsonObj.put(Constant.NEW_LAST_NAME, "Martin");
		jsonObj.put(Constant.NEW_EMAIL, "sujtih.dars@husky.neu.edu");
		jsonObj.put(Constant.NEW_PASSWORD, "23jsdf@awAer");
		jsonObj.put(Constant.EMAIL, "sujith.mah@husky.neu.edu");
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		//Not Valid Session 
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("sujith.mah@husky.neu.edu");
		
		userProfileUpdate.doPut(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.BADREQUEST_400);
		
	}
	

}
