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
	
		
		assertEquals(UserProfileVal.validateName(correctName1), true);
		assertEquals(UserProfileVal.validateName(correctName2), true);
		assertEquals(UserProfileVal.validateName(correctName3), true);
		
		assertEquals(UserProfileVal.validateName(badCharacter), false);
		assertEquals(UserProfileVal.validateName(bigName), false);
		assertEquals(UserProfileVal.validateName(numbers), false);
		
	}
	
	@Test
	public void testPasswordValidation(){
		
		String noUpperCase = "abcde&2";
		String noNumber = "Alaksdf&";
		String noSpecialChar = "Alkad234";
		
		String correctPwd1 = "Abcde@12345";
		String correctPwd2 = "Psych@C.M.1";
		
		assertEquals(UserProfileVal.validatePassword(correctPwd1), true);
		assertEquals(UserProfileVal.validatePassword(correctPwd2), true);
		
		assertEquals(UserProfileVal.validatePassword(noUpperCase), false);
		assertEquals(UserProfileVal.validatePassword(noNumber), false);
		assertEquals(UserProfileVal.validatePassword(noSpecialChar), false);
		
	}
	
	@Test
	public void testEmailValidation(){
		
		String normalName = "abcdef";
		String noDotCom = "abcder@asdfsdf";
		String noAt = "asdkflj.google.com";
		
		String validEmail1 = "abcd@google.com";
		String validEmail2 = "something@neu.edu";
		String validEmail3 = "something.123_123@google.com";
		
		assertEquals(UserProfileVal.validateEmail(validEmail1), true);
		assertEquals(UserProfileVal.validateEmail(validEmail2), true);
		assertEquals(UserProfileVal.validateEmail(validEmail3), true);
		
		assertEquals(UserProfileVal.validateEmail(normalName), false);
		assertEquals(UserProfileVal.validateEmail(noDotCom), false);
		assertEquals(UserProfileVal.validateEmail(noAt), false);
	}
}
