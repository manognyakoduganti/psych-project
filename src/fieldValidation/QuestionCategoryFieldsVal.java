package fieldValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionCategoryFieldsVal {
	
	static String nameRegex = "[a-zA-Z0-9\\s,.'-]{1,50}$";
	static Pattern namePattern = Pattern.compile(nameRegex);
	
	static String descriptionRegex = ".{1,1000}$";
	static Pattern descriptionPattern = Pattern.compile(descriptionRegex);
	
	static String labelRegex = "[a-zA-Z0-9\\s]{1,30}$";
	static Pattern labelPattern = Pattern.compile(labelRegex);
	
	public static boolean validateName(String name){
		
		Matcher matcher = namePattern.matcher(name.trim());
		return matcher.matches();
	}
	
	public static boolean validateDescription(String desc){
		Matcher matcher = descriptionPattern.matcher(desc.trim());
		return matcher.matches();
	}
	
	public static boolean validateLabel(String label){
		Matcher matcher = labelPattern.matcher(label.trim());
		return matcher.matches();
	}
}
