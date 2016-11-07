package fieldValidation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class UserProfileTest {
	
	UserProfileVal userProfile;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  userProfile = new UserProfileVal();
	}
	
	@Test
	public void testFirstLastNameFieldValidation(){
		
		String badCharacter = "Thomas __ &(!@ ASDF";
		String bigName = "Thomaskjasdlkfok asldfiuk Sdfjaosiduf osudflkjasdfliu ASDKFJlasdjflksjdlfkj "
				+ "lkasjdflkjlkj ksdjf lkjasd fjalksdjflkajsdlkfj lskjdflksjdl flskjflkjsdlkfj aklsjdflksj df";
		
		String numbers = "asd012987 laksjdf7034";
		String correctName1 = "Mathias d'Arras";
		String correctName2 = "Martin Luther King, Jr.";
		String correctName3 = "Hector Sausage-Hausen";
	
		
		assertEquals(true, UserProfileVal.validateName(correctName1) );
		assertEquals(true, UserProfileVal.validateName(correctName2));
		assertEquals(true, UserProfileVal.validateName(correctName3));
		
		assertEquals(false, UserProfileVal.validateName(badCharacter));
		assertEquals(false, UserProfileVal.validateName(bigName));
		assertEquals(false, UserProfileVal.validateName(numbers));
		
	}
	
	@Test
	public void testPasswordValidation(){
		
		String noUpperCase = "abcde&2";
		String noNumber = "Alaksdf&";
		String noSpecialChar = "Alkad234";
		
		String correctPwd1 = "Abcde@12345";
		String correctPwd2 = "Psych@C.M.1";
		
		assertEquals(true, UserProfileVal.validatePassword(correctPwd1));
		assertEquals(true, UserProfileVal.validatePassword(correctPwd2));
		
		assertEquals(false, UserProfileVal.validatePassword(noUpperCase));
		assertEquals(false, UserProfileVal.validatePassword(noNumber));
		assertEquals(false, UserProfileVal.validatePassword(noSpecialChar));
		
	}
	
	@Test
	public void testEmailValidation(){
		
		String normalName = "abcdef";
		String noDotCom = "abcder@asdfsdf";
		String noAt = "asdkflj.google.com";
		
		String validEmail1 = "abcd@google.com";
		String validEmail2 = "something@neu.edu";
		String validEmail3 = "something.123_123@google.com";
		
		assertEquals(true, UserProfileVal.validateEmail(validEmail1));
		assertEquals(true, UserProfileVal.validateEmail(validEmail2));
		assertEquals(true, UserProfileVal.validateEmail(validEmail3));
		
		assertEquals(false, UserProfileVal.validateEmail(normalName));
		assertEquals(false, UserProfileVal.validateEmail(noDotCom));
		assertEquals(false, UserProfileVal.validateEmail(noAt));
	}
}
