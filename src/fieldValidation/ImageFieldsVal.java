package fieldValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageFieldsVal {

	static String imageCategoryNameRegex = "^[a-zA-Z0-9 ,.'&-]{1,30}$";
	static Pattern imageCategoryNamePattern = Pattern.compile(imageCategoryNameRegex);
	public static boolean validateImageCategoryName(String locName){
		Matcher matcher = imageCategoryNamePattern.matcher(locName.trim());
		return matcher.matches();
	}
}
