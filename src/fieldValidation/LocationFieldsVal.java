package fieldValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationFieldsVal {
	
	static String locationCodeRegex = "[a-zA-Z0-9]{6}$";
	static Pattern locationCodePattern = Pattern.compile(locationCodeRegex);
	
	public static boolean validateLocationCode(String locationCode){
		Matcher matcher = locationCodePattern.matcher(locationCode.trim());
		return matcher.matches();
	}
	
	public static boolean validateAddressLine1(String addressLine1){
		return false;
	}
	
	public static boolean validateAddressLine2(String addressLine2){
		return false;
	}
	
	public static boolean validateName(String name){
		return false;
	}
	
	public static boolean validatePhoneNumber(String phoneNumber){
		return false;
		
	}
	
	public static boolean validateFaxNumber(String faxNumber){
		return false;
	}
	
	public static boolean validateZipCode(String zipCode){
		return false;
	}
	
	public static boolean validateState(String state){
		return false;
	}
	
	public static boolean validateCity(String city){
		return false;
	}

}
