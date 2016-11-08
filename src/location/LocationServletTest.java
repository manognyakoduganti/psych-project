package location;

import static org.junit.Assert.assertEquals;
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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import common.Constant;

public class LocationServletTest {
	
	LocationServlet locationServlet;
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  locationServlet = new LocationServlet();
	}
	
	@Test
	public void testValidLocationCreateRequest() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Constant.LOCATION_NAME, "Northeastern University TEST");
		jsonObj.put(Constant.LOCATION_DESCRIPTION, "Northeastern University is a private "
				+ "institution that was founded in 1898. It has a total undergraduate enrollment of 13,697, "
				+ "its setting is urban, and the campus size is 73 acres. It utilizes a semester-based academic "
				+ "calendar. Northeastern University's ranking in the 2017 edition of Best Colleges is National "
				+ "Universities, 39. Its tuition and fees are $47,655 (2016-17).");
		String[] locationKeywords = new String[] { "Northeastern", "Psychology"};
		jsonObj.put(Constant.LOCATION_KEYWORDS, String.join(Constant.KEYWORD_SEPERATOR, locationKeywords));
		jsonObj.put(Constant.LOCATION_ADDRESS_LINE_1, "360 Huntington Avenue");
		jsonObj.put(Constant.LOCATION_ADDRESS_LINE_2, "");
		jsonObj.put(Constant.LOCATION_CITY, "Boston");
		jsonObj.put(Constant.LOCATION_STATE, "MA");
		jsonObj.put(Constant.LOCATION_ZIPCODE, "02115");
		jsonObj.put(Constant.LOCATION_PHONE_NUMBER, "1234567891");
		jsonObj.put(Constant.LOCATION_FAX_NUMBER, "1234567891");
		jsonObj.put(Constant.LOCATION_EMAIL, "northeastern@neu.edu");
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(true)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		
		locationServlet.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(Constant.OK_200, (String) jsonObject.get(Constant.STATUS));
		
		// Delete the records after test is completed
		
	}
	
	@Test
	public void testInValidLocationCreateRequestMissingMandatoryFields() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Constant.LOCATION_NAME, "");
		jsonObj.put(Constant.LOCATION_DESCRIPTION, "");
		jsonObj.put(Constant.LOCATION_KEYWORDS, "");
		jsonObj.put(Constant.LOCATION_ADDRESS_LINE_1, "");
		jsonObj.put(Constant.LOCATION_ADDRESS_LINE_2, "");
		jsonObj.put(Constant.LOCATION_CITY, "");
		jsonObj.put(Constant.LOCATION_STATE, "");
		jsonObj.put(Constant.LOCATION_ZIPCODE, "");
		jsonObj.put(Constant.LOCATION_PHONE_NUMBER, "");
		jsonObj.put(Constant.LOCATION_FAX_NUMBER, "");
		jsonObj.put(Constant.LOCATION_EMAIL, "");
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(true)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		
		locationServlet.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(Constant.BADREQUEST_400, (String) jsonObject.get(Constant.STATUS));
		
		// Delete the records after test is completed
		
	}
	
	@Test
	public void testInValidSessionLocationCreateRequest() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Constant.LOCATION_NAME, "Northeastern University TEST");
		jsonObj.put(Constant.LOCATION_DESCRIPTION, "Northeastern University is a private "
				+ "institution that was founded in 1898. It has a total undergraduate enrollment of 13,697, "
				+ "its setting is urban, and the campus size is 73 acres. It utilizes a semester-based academic "
				+ "calendar. Northeastern University's ranking in the 2017 edition of Best Colleges is National "
				+ "Universities, 39. Its tuition and fees are $47,655 (2016-17).");
		String[] locationKeywords = new String[] { "Northeastern", "Psychology"};
		jsonObj.put(Constant.LOCATION_KEYWORDS, String.join(Constant.KEYWORD_SEPERATOR, locationKeywords));
		jsonObj.put(Constant.LOCATION_ADDRESS_LINE_1, "360 Huntington Avenue");
		jsonObj.put(Constant.LOCATION_ADDRESS_LINE_2, "");
		jsonObj.put(Constant.LOCATION_CITY, "Boston");
		jsonObj.put(Constant.LOCATION_STATE, "MA");
		jsonObj.put(Constant.LOCATION_ZIPCODE, "02115");
		jsonObj.put(Constant.LOCATION_PHONE_NUMBER, "1234567891");
		jsonObj.put(Constant.LOCATION_FAX_NUMBER, "1234567891");
		jsonObj.put(Constant.LOCATION_EMAIL, "northeastern@neu.edu");
		
		when(bufferedReader.readLine()).thenReturn(jsonObj.toString()).thenReturn(null);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(true)).thenReturn(null);
		
		locationServlet.doPost(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(Constant.UNAUTHORIZED_401, (String) jsonObject.get(Constant.STATUS));
		
		// Delete the records after test is completed
		
	}

}
