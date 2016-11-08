package fieldValidation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class LocationFieldsTest {
	
	LocationFieldsVal location;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  location = new LocationFieldsVal();
	}
	
	@Test
	public void testValidateLocationName(){
		
		String badCharacter = "Thomas __ &(!@ ASDF";
		String bigName = "Thomaskjasdlkfok asldfiuk Sdfjaosiduf osudflkjasdfliu ASDKFJlasdjflksjdlfkj "
				+ "lkasjdflkjlkj ksdjf lkjasd fjalksdjflkajsdlkfj lskjdflksjdl flskjflkjsdlkfj aklsjdflksj df" 
				+ "lkasjdflkjlkj ksdjf lkjasd fjalksdjflkajsdlkfj lskjdflksjdl flskjflkjsdlkfj aklsjdflksj df"
				+ "lkasjdflkjlkj ksdjf lkjasd fjalksdjflkajsdlkfj lskjdflksjdl flskjflkjsdlkfj aklsjdflksj df"
				+ "lkasjdflkjlkj ksdjf lkjasd fjalksdjflkajsdlkfj lskjdflksjdl flskjflkjsdlkfj aklsjdflksj df";
		String emtpyName = "";
		String empty2 = " ";
		
		String numbers = "asd012987 laksjdf7034";
		String correctName1 = "John & John Corp.";
		String correctName2 = "Martin Luther King, Jr.";
		String correctName3 = "Hector Sausage-Hausen";
	
		
		assertEquals(true, LocationFieldsVal.validateName(correctName1));
		assertEquals(true, LocationFieldsVal.validateName(correctName2));
		assertEquals(true, LocationFieldsVal.validateName(correctName3));
		assertEquals(true, LocationFieldsVal.validateName(numbers));
		
		assertEquals(false,  LocationFieldsVal.validateName(empty2));
		assertEquals(false,  LocationFieldsVal.validateName(badCharacter));
		assertEquals(false,  LocationFieldsVal.validateName(bigName));
		assertEquals(false,  LocationFieldsVal.validateName(emtpyName));
		
	}
	
	@Test
	public void testValidateLocationCode(){
		
		String badCodeSpace = "ABC 123";
		String bigCode ="ASDFW1233";
		String specialChar = "!@#$@#";
		String emtpyName = "";
		String empty2 = " ";
		
		String goodCode1 = "ABC123";
		String goodCode2 = "CHARAC";
		String goodCode3 = "123456";
	
		
		assertEquals(true, LocationFieldsVal.validateName(goodCode1));
		assertEquals(true, LocationFieldsVal.validateName(goodCode2));
		assertEquals(true, LocationFieldsVal.validateName(goodCode3));
		
		assertEquals(false,  LocationFieldsVal.validateName(badCodeSpace));
		assertEquals(false,  LocationFieldsVal.validateName(emtpyName));
		assertEquals(false,  LocationFieldsVal.validateName(bigCode));
		assertEquals(false,  LocationFieldsVal.validateName(specialChar));
		assertEquals(false,  LocationFieldsVal.validateName(empty2));
		
	}
	
	@Test
	public void testValidateAddressLine1(){
		
		String bigAddressLine1 = "HANDUNL ALSKDJFL ASLKF ASDKJFALKSJDF  ASLKDJFLASKJDF ALKSJDFL ASLKDFJ"
				+ "ASLKDJFALSKFDJLA ALSJKD FLKASJ DFLK SLKFJ LKAJS DFLKJA SLKDFJ LAKSJ DF"
				+ "KLSAJDFLKJSDLKFJ ALKSDJFLKAJ SDFLKJ ALSKDJ FLKASJD FLKJ ALKSDJF LKAJSDF ALSDFJ"
				+ "LAKSJDF LKAJSDFL ALKSJDF LKAJ DFLK ALKSDJF LKASJFD  ALKSJF LKASJ FD";
		String emtpyName = "";
		
		String goodCode1 = "360 HUNTINGTON AVE";
		String goodCode2 = "12 - B @ Chapper hill";
		String empty2 = " ";
		
		assertEquals(true, LocationFieldsVal.validateName(goodCode1));
		assertEquals(true, LocationFieldsVal.validateName(goodCode2));
		
		assertEquals(false,  LocationFieldsVal.validateName(bigAddressLine1));
		assertEquals(false,  LocationFieldsVal.validateName(emtpyName));
		assertEquals(false,  LocationFieldsVal.validateName(empty2));
		
	}
	
	@Test
	public void testValidateAddressLine2(){
		
		String bigAddressLine2 = "HANDUNL ALSKDJFL ASLKF ASDKJFALKSJDF  ASLKDJFLASKJDF ALKSJDFL ASLKDFJ"
				+ "ASLKDJFALSKFDJLA ALSJKD FLKASJ DFLK SLKFJ LKAJS DFLKJA SLKDFJ LAKSJ DF"
				+ "KLSAJDFLKJSDLKFJ ALKSDJFLKAJ SDFLKJ ALSKDJ FLKASJD FLKJ ALKSDJF LKAJSDF ALSDFJ"
				+ "LAKSJDF LKAJSDFL ALKSJDF LKAJ DFLK ALKSDJF LKASJFD  ALKSJF LKASJ FD";
		String emtpyName = "";
		String empty2 = " ";
		
		String goodCode1 = "OPP. place";
		String goodCode2 = "12 - B @ Chapper hill";
	
		
		assertEquals(true, LocationFieldsVal.validateName(goodCode1));
		assertEquals(true, LocationFieldsVal.validateName(goodCode2));
		assertEquals(true,  LocationFieldsVal.validateName(emtpyName));
		assertEquals(true,  LocationFieldsVal.validateName(empty2));
		
		assertEquals(false,  LocationFieldsVal.validateName(bigAddressLine2));
		
	}
	
	@Test
	public void testValidateCity(){
		
		String cityWithNumber = "Boston 123";
		String specialChars = "Boston !!";
		String emtpyName = "";
		String empty2 = " ";
		String bigName = "HANDUNL ALSKDJFL ASLKF ASDKJFALKSJDF  ASLKDJFLASKJDF ALKSJDFL ASLKDFJ"
				+ "ASLKDJFALSKFDJLA ALSJKD FLKASJ DFLK SLKFJ LKAJS DFLKJA SLKDFJ LAKSJ DF"
				+ "KLSAJDFLKJSDLKFJ ALKSDJFLKAJ SDFLKJ ALSKDJ FLKASJD FLKJ ALKSDJF LKAJSDF ALSDFJ"
				+ "LAKSJDF LKAJSDFL ALKSJDF LKAJ DFLK ALKSDJF LKASJFD  ALKSJF LKASJ FD";
		
		String goodCode1 = "Boston";
		String goodCode2 = "New York";
	
		
		assertEquals(true, LocationFieldsVal.validateName(goodCode1));
		assertEquals(true, LocationFieldsVal.validateName(goodCode2));
		
		assertEquals(false,  LocationFieldsVal.validateName(emtpyName));
		assertEquals(false,  LocationFieldsVal.validateName(empty2));
		assertEquals(false,  LocationFieldsVal.validateName(cityWithNumber));
		assertEquals(false,  LocationFieldsVal.validateName(specialChars));
		assertEquals(false,  LocationFieldsVal.validateName(bigName));
		
	}
	
	@Test
	public void testValidateState(){
		
		String number = "New York 123";
		String specialChars = "Colorado !!";
		String emtpyName = "";
		String empty2 = " ";
		String bigName = "HANDUNL ALSKDJFL ASLKF ASDKJFALKSJDF  ASLKDJFLASKJDF ALKSJDFL ASLKDFJ"
				+ "ASLKDJFALSKFDJLA ALSJKD FLKASJ DFLK SLKFJ LKAJS DFLKJA SLKDFJ LAKSJ DF"
				+ "KLSAJDFLKJSDLKFJ ALKSDJFLKAJ SDFLKJ ALSKDJ FLKASJD FLKJ ALKSDJF LKAJSDF ALSDFJ"
				+ "LAKSJDF LKAJSDFL ALKSJDF LKAJ DFLK ALKSDJF LKASJFD  ALKSJF LKASJ FD";
		
		String goodCode1 = "Georgia";
		String goodCode2 = "California";
	
		
		assertEquals(true, LocationFieldsVal.validateName(goodCode1));
		assertEquals(true, LocationFieldsVal.validateName(goodCode2));
		
		assertEquals(false,  LocationFieldsVal.validateName(emtpyName));
		assertEquals(false,  LocationFieldsVal.validateName(empty2));
		assertEquals(false,  LocationFieldsVal.validateName(number));
		assertEquals(false,  LocationFieldsVal.validateName(specialChars));
		assertEquals(false,  LocationFieldsVal.validateName(bigName));
		
	}
	
	@Test
	public void testValidateZipCode(){
		
		String longNumber = "123923097";
		String specialChars = "12345!";
		String emtpyName = "";
		String empty2 = " ";
		String withChars = "12345ABCD";
		
		String goodCode1 = "02120";
		String goodCode2 = "12345";
	
		assertEquals(true, LocationFieldsVal.validateName(goodCode1));
		assertEquals(true, LocationFieldsVal.validateName(goodCode2));
		
		assertEquals(false,  LocationFieldsVal.validateName(longNumber));
		assertEquals(false,  LocationFieldsVal.validateName(specialChars));
		assertEquals(false,  LocationFieldsVal.validateName(emtpyName));
		assertEquals(false,  LocationFieldsVal.validateName(empty2));
		assertEquals(false,  LocationFieldsVal.validateName(withChars));
		
	}
	
	
	@Test
	public void testValidateFaxPhoneNumber(){
		
		String digit11 = "12345678911";
		String digit9 = "123456789";
		String emtpyName = "";
		String empty2 = " ";
		String withChars = "123456789A";
		String withSpec = "123-456-7891";
		
		String goodCode1 = "1234567891";
		String goodCode2 = "2167864567";
	
		assertEquals(true, LocationFieldsVal.validateName(goodCode1));
		assertEquals(true, LocationFieldsVal.validateName(goodCode2));
		
		assertEquals(false,  LocationFieldsVal.validateName(withSpec));
		assertEquals(false,  LocationFieldsVal.validateName(digit11));
		assertEquals(false,  LocationFieldsVal.validateName(digit9));
		assertEquals(false,  LocationFieldsVal.validateName(emtpyName));
		assertEquals(false,  LocationFieldsVal.validateName(empty2));
		assertEquals(false,  LocationFieldsVal.validateName(withChars));
		
	}
	
}
