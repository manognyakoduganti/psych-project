package authentication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

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
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(false)).thenReturn(session);
		when(request.getParameter("email")).thenReturn("patel.dars@husky.neu.edu");
		when(request.getParameter("password")).thenReturn("abcd");
		
		adminAuthentication.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get("verified"), "1");
		
		verify(session).setAttribute("email", "patel.dars@husky.neu.edu");
		verify(session).setAttribute("role", "GlobalAdministrator");
		verify(session).setAttribute("firstname", "Darshan");
		verify(session).setAttribute("lastname", "Patel");
		
	}
	
	@Test
	public void testInvalidAdminLoginAuthentication() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(false)).thenReturn(session);
		when(request.getParameter("email")).thenReturn("woeura#$%#$nssknlkj");
		when(request.getParameter("password")).thenReturn("weoiru2l3kn4234908");
		
		adminAuthentication.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get("verified"), "0");
		
	}
	

}
