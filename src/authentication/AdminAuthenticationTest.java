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
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("email")).thenReturn("darshan");
		when(request.getParameter("password")).thenReturn("abcd");
		
		adminAuthentication.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get("verified"), "1");
		assertEquals((String) jsonObject.get("error"), "0");
		
		verify(session).setAttribute("email", "darshan");
		verify(session).setAttribute("role", "GlobalAdministrator");
		
	}
	
	@Test
	public void testInvalidAdminLoginAuthentication() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("email")).thenReturn("darshan");
		when(request.getParameter("password")).thenReturn("password1");
		
		adminAuthentication.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((String) jsonObject.get("verified"), "0");
		
	}
	

}
