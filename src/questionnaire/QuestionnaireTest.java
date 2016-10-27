package questionnaire;

import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class QuestionnaireTest {
	
	Questionnaire ques = new Questionnaire();
	
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		request = mock(HttpServletRequest.class);       
	    response = mock(HttpServletResponse.class);
	    
	    assertEquals(null, response.getContentType());
	}

}
