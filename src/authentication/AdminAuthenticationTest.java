package authentication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

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

public class AdminAuthenticationTest {
	
	AdminAuthentication adminAuthentication;
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  adminAuthentication = new AdminAuthentication();
	}
	
	@Test
	public void testValidAdminLoginAuthentication() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Constant.EMAIL, "patel.dars@husky.neu.edu");
		jsonObj.put(Constant.PASSWORD, "Abcd@1234");
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(true)).thenReturn(session);
		//when(request.getParameter(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		//when(request.getParameter(Constant.PASSWORD)).thenReturn("abcd");
		
		adminAuthentication.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(Constant.OK_200, (String) jsonObject.get(Constant.STATUS));
		
		verify(session).setAttribute(Constant.EMAIL, "patel.dars@husky.neu.edu");
		verify(session).setAttribute(Constant.ROLE, "GlobalAdministrator");
		verify(session).setAttribute(Constant.FIRST_NAME, "Darshan");
		verify(session).setAttribute(Constant.LAST_NAME, "Patel");
		
	}
	
	@Test
	public void testInvalidAdminLoginAuthentication() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(true)).thenReturn(session);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Constant.EMAIL, "woeura#$%#$nssknlkj");
		jsonObj.put(Constant.PASSWORD, "weoiru2l3kn4234908");
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		//when(request.getParameter(Constant.EMAIL)).thenReturn("woeura#$%#$nssknlkj");
		//when(request.getParameter(Constant.PASSWORD)).thenReturn("weoiru2l3kn4234908");
		
		adminAuthentication.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(Constant.UnAuthorized_401, (String) jsonObject.get(Constant.STATUS));
		
	}
	

}
