package fieldValidation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class UserProfileTest {
	
	UserProfile userProfile;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  userProfile = new UserProfile();
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
	
		
		assertEquals(UserProfile.validateName(correctName1), true);
		assertEquals(UserProfile.validateName(correctName2), true);
		assertEquals(UserProfile.validateName(correctName3), true);
		
		assertEquals(UserProfile.validateName(badCharacter), false);
		assertEquals(UserProfile.validateName(bigName), false);
		assertEquals(UserProfile.validateName(numbers), false);
		
	}
	
	@Test
	public void testPasswordValidation(){
		
		String noUpperCase = "abcde&2";
		String noNumber = "Alaksdf&";
		String noSpecialChar = "Alkad234";
		
		String correctPwd1 = "Abcde@12345";
		String correctPwd2 = "Psych@C.M.1";
		
		assertEquals(UserProfile.validatePassword(correctPwd1), true);
		assertEquals(UserProfile.validatePassword(correctPwd2), true);
		
		assertEquals(UserProfile.validatePassword(noUpperCase), false);
		assertEquals(UserProfile.validatePassword(noNumber), false);
		assertEquals(UserProfile.validatePassword(noSpecialChar), false);
		
	}
	
	@Test
	public void testEmailValidation(){
		
		String normalName = "abcdef";
		String noDotCom = "abcder@asdfsdf";
		String noAt = "asdkflj.google.com";
		
		String validEmail1 = "abcd@google.com";
		String validEmail2 = "something@neu.edu";
		String validEmail3 = "something.123_123@google.com";
		
		assertEquals(UserProfile.validateEmail(validEmail1), true);
		assertEquals(UserProfile.validateEmail(validEmail2), true);
		assertEquals(UserProfile.validateEmail(validEmail3), true);
		
		assertEquals(UserProfile.validateEmail(normalName), false);
		assertEquals(UserProfile.validateEmail(noDotCom), false);
		assertEquals(UserProfile.validateEmail(noAt), false);
	}
}
