package fieldValidation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImageFieldsTest {
	
	@Test
	public void testValidateLocationName(){
		
		String badCharacter = "Thomas __ &(!@ ASDF";
		String bigName = "Thomaskjasdlkfok asldfiuk Sdfjaosiduf osudflkjasdfliu ASDKFJlasdjflksjdlfkj "
				+ "lkasjdflkjlkj ksdjf lkjasd fjalksdjflkajsdlkfj lskjdflksjdl flskjflkjsdlkfj aklsjdflksj";
		String emtpyName = "";
		String empty2 = " ";
		
		String numbers = "asd012987 laksjdf7034";
		String correctName1 = "John & John Corp.";
		String correctName2 = "Martin Luther King, Jr.";
		String correctName3 = "Hector Sausage-Hausen";
	
		
		assertEquals(true, ImageFieldsVal.validateImageCategoryName(correctName1));
		assertEquals(true, ImageFieldsVal.validateImageCategoryName(correctName2));
		assertEquals(true, ImageFieldsVal.validateImageCategoryName(correctName3));
		assertEquals(true,  ImageFieldsVal.validateImageCategoryName(numbers));
		
		assertEquals(false,  ImageFieldsVal.validateImageCategoryName(empty2));
		assertEquals(false,  ImageFieldsVal.validateImageCategoryName(badCharacter));
		assertEquals(false,  ImageFieldsVal.validateImageCategoryName(bigName));
		assertEquals(false,  ImageFieldsVal.validateImageCategoryName(emtpyName));
		
		
	}

}
