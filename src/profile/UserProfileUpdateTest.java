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

public class UserProfileUpdateTest {
	
	UserProfileUpdate userProfileUpdate;
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  userProfileUpdate = new UserProfileUpdate();
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
		
		jsonObj.put(Constant.NEW_FIRST_NAME, "Darshan");
		jsonObj.put(Constant.NEW_LAST_NAME, "Patel");
		jsonObj.put(Constant.NEW_EMAIL, "patel.dars@husky.neu.edu");
		jsonObj.put(Constant.NEW_PASSWORD, "Abcde@12345");
		jsonObj.put(Constant.EMAIL, "patel.dars@husky.neu.edu");
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		
		userProfileUpdate.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.OK_200);
		
		verify(session).setAttribute(Constant.EMAIL, "patel.dars@husky.neu.edu");
		verify(session).setAttribute(Constant.ROLE, "GlobalAdministrator");
		verify(session).setAttribute(Constant.FIRST_NAME, "Darshan");
		verify(session).setAttribute(Constant.LAST_NAME, "Patel");
		
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
		
		userProfileUpdate.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.UnAuthorized_401);
		
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
		
		userProfileUpdate.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.BadRequest_400);
		
	}
	

}