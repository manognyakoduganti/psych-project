package fieldValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.Constant;

public class CommonFieldsVal {

	public static boolean validateDescription(String description){
		int length = description.trim().length();
		if(length <= 1000){
			return true;
		}
		return false;
	}
	
	static String keywordsRegex = "^[a-zA-Z0-9 ]{1,30}$";
	static Pattern keywordPattern = Pattern.compile(keywordsRegex);
	public static boolean validateKeywords(String keywords){
		String[] parts = keywords.split(Constant.KEYWORD_SEPERATOR_PARSER);
		for(String part: parts){
			Matcher matcher = keywordPattern.matcher(part.trim());
			if(!matcher.find()){
				return false;
			}
		}
		return true;
	}
	
}
