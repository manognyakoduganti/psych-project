package fieldValidation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import common.Constant;

public class CommonFieldsTest {
	
	CommonFieldsVal commonFields;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  commonFields = new CommonFieldsVal();
	}
	
	@Test
	public void testValidateDesription(){
		
		String goodDescription1 = "This is a descprition. All special !@# are allowed";
		String goodDescription2 = "Check it";
		
		
		String empty1 = "";
		String empty2 = " ";
		char[] chars = new char[1020];
		// Optional step - unnecessary if you're happy with the array being full of \0
		Arrays.fill(chars, 'f');
		String bigString = new String(chars);
	
		assertEquals(true, CommonFieldsVal.validateDescription(goodDescription1));
		assertEquals(true, CommonFieldsVal.validateDescription(goodDescription2));
		
		assertEquals(false, CommonFieldsVal.validateDescription(empty1));
		assertEquals(false, CommonFieldsVal.validateDescription(empty2));
		assertEquals(false, CommonFieldsVal.validateDescription(bigString));
		
	}
	
	@Test
	public void testValidateKeywords(){
		
		String[] goodKeywords1 = new String[] { "ABC 213", "123", "ABCD" };
		String[] goodKeywords2 = new String[] { "SMITH ASDF", "aASDFLAKSDLFAJSDF", "123oi12o3iuoiu" };
		
		String[] specialChars = new String[] { "!@#", "ABCDES", "ASDF!@#SASF" };
		String[] empty = new String[] { "", " ", "    " };		
	
		assertEquals(true, CommonFieldsVal.validateKeywords(String.join(Constant.KEYWORD_SEPERATOR, goodKeywords1)));
		assertEquals(true, CommonFieldsVal.validateKeywords(String.join(Constant.KEYWORD_SEPERATOR, goodKeywords2)));
		
		assertEquals(true, CommonFieldsVal.validateKeywords(String.join(Constant.KEYWORD_SEPERATOR, specialChars)));
		assertEquals(true, CommonFieldsVal.validateKeywords(String.join(Constant.KEYWORD_SEPERATOR, empty)));
		
	}
	
	

}
