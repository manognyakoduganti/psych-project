package fieldValidation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class LocationTest {
	
	LocationVal location;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  location = new LocationVal();
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
	
		
		assertEquals(true, LocationVal.validateName(correctName1));
		assertEquals(true, LocationVal.validateName(correctName2));
		assertEquals(true, LocationVal.validateName(correctName3));
		assertEquals(true, LocationVal.validateName(numbers));
		
		assertEquals(false,  LocationVal.validateName(empty2));
		assertEquals(false,  LocationVal.validateName(badCharacter));
		assertEquals(false,  LocationVal.validateName(bigName));
		assertEquals(false,  LocationVal.validateName(emtpyName));
		
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
	
		
		assertEquals(true, LocationVal.validateName(goodCode1));
		assertEquals(true, LocationVal.validateName(goodCode2));
		assertEquals(true, LocationVal.validateName(goodCode3));
		
		assertEquals(false,  LocationVal.validateName(badCodeSpace));
		assertEquals(false,  LocationVal.validateName(emtpyName));
		assertEquals(false,  LocationVal.validateName(bigCode));
		assertEquals(false,  LocationVal.validateName(specialChar));
		assertEquals(false,  LocationVal.validateName(empty2));
		
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
		
		assertEquals(true, LocationVal.validateName(goodCode1));
		assertEquals(true, LocationVal.validateName(goodCode2));
		
		assertEquals(false,  LocationVal.validateName(bigAddressLine1));
		assertEquals(false,  LocationVal.validateName(emtpyName));
		assertEquals(false,  LocationVal.validateName(empty2));
		
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
	
		
		assertEquals(true, LocationVal.validateName(goodCode1));
		assertEquals(true, LocationVal.validateName(goodCode2));
		assertEquals(true,  LocationVal.validateName(emtpyName));
		assertEquals(true,  LocationVal.validateName(empty2));
		
		assertEquals(false,  LocationVal.validateName(bigAddressLine2));
		
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
	
		
		assertEquals(true, LocationVal.validateName(goodCode1));
		assertEquals(true, LocationVal.validateName(goodCode2));
		
		assertEquals(false,  LocationVal.validateName(emtpyName));
		assertEquals(false,  LocationVal.validateName(empty2));
		assertEquals(false,  LocationVal.validateName(cityWithNumber));
		assertEquals(false,  LocationVal.validateName(specialChars));
		assertEquals(false,  LocationVal.validateName(bigName));
		
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
	
		
		assertEquals(true, LocationVal.validateName(goodCode1));
		assertEquals(true, LocationVal.validateName(goodCode2));
		
		assertEquals(false,  LocationVal.validateName(emtpyName));
		assertEquals(false,  LocationVal.validateName(empty2));
		assertEquals(false,  LocationVal.validateName(number));
		assertEquals(false,  LocationVal.validateName(specialChars));
		assertEquals(false,  LocationVal.validateName(bigName));
		
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
	
		assertEquals(true, LocationVal.validateName(goodCode1));
		assertEquals(true, LocationVal.validateName(goodCode2));
		
		assertEquals(false,  LocationVal.validateName(longNumber));
		assertEquals(false,  LocationVal.validateName(specialChars));
		assertEquals(false,  LocationVal.validateName(emtpyName));
		assertEquals(false,  LocationVal.validateName(empty2));
		assertEquals(false,  LocationVal.validateName(withChars));
		
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
	
		assertEquals(true, LocationVal.validateName(goodCode1));
		assertEquals(true, LocationVal.validateName(goodCode2));
		
		assertEquals(false,  LocationVal.validateName(withSpec));
		assertEquals(false,  LocationVal.validateName(digit11));
		assertEquals(false,  LocationVal.validateName(digit9));
		assertEquals(false,  LocationVal.validateName(emtpyName));
		assertEquals(false,  LocationVal.validateName(empty2));
		assertEquals(false,  LocationVal.validateName(withChars));
		
	}
	
}
