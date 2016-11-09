package location;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Constant;
import common.Location;
import common.Sessions;
import dao.DBSourceRegistrationListener;
import dao.LocationDAO;
import fieldValidation.CommonFieldsVal;
import fieldValidation.LocationFieldsVal;
import fieldValidation.UserProfileFieldsVal;

/**
 * Servlet implementation class LocationServlet
 */
@WebServlet("/location")
public class LocationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger slf4jLogger = LoggerFactory.getLogger(LocationServlet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		boolean checkLocationName = false;
		boolean checkLocaitonCode = false;
		JSONObject returnJSON = new JSONObject();
		
		if((request.getParameter(Constant.LOCATION_NAME) != null) || (request.getParameter(Constant.LOCATION_CODE) != null)){
			if(request.getParameter(Constant.LOCATION_NAME) != null){
				checkLocationName= true;
			}else{
				checkLocaitonCode = true;
			}
		}
		
		if(checkLocationName){
			
			String locationName = request.getParameter(Constant.LOCATION_NAME);
			returnJSON.put(Constant.RESULTS, LocationDAO.isDuplicateLocation(locationName));
			
		}else if(checkLocaitonCode){
			
			String locationCode = request.getParameter(Constant.LOCATION_CODE);
			slf4jLogger.info("locationCode : "+locationCode + LocationDAO.isDuplicateLocationCode(locationCode));
			returnJSON.put(Constant.RESULTS, LocationDAO.isDuplicateLocationCode(locationCode));
			
		}else{
			
		}
		
		response.getWriter().print(returnJSON);
		response.addHeader("Access-Control-Allow-Origin", Constant.ACCESS_CONTROL_ALLOW_ORIGIN);
		response.addHeader("Access-Control-Allow-Headers", Constant.ACCESS_CONTROL_ALLOW_HEADERS);
		response.addHeader("Access-Control-Allow-Methods", Constant.ACCESS_CONTROL_ALLOW_METHODS);
		response.addIntHeader("Access-Control-Max-Age", Constant.ACCESS_CONTROL_ALLOW_MAX_AGE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		slf4jLogger.info("Entered in doPos method of LocationServlet");
		response.setContentType("application/json;charset=UTF-8");
		
		
		JSONObject returnJSON = new JSONObject();
		
		StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        
        JSONParser parser = new JSONParser();
		Object obj;
		
		HttpSession session = request.getSession(false);
		
		if(Sessions.isValidGlobalAdminSession(session)){
			
			try {
				
				obj = parser.parse(sb.toString());
				
				JSONObject jsonObject = (JSONObject) obj;
				boolean created = false;
				if(isValidInputData(jsonObject)){
					String locationName =  ((String) jsonObject.get(Constant.LOCATION_NAME)).trim();
					boolean duplicated = LocationDAO.isDuplicateLocation(locationName);
					if(!duplicated){
						Location location = parseLocation(jsonObject);
						created = LocationDAO.createLocation(location);
						returnJSON.put(Constant.STATUS, Constant.OK_200);
					}
				}
				if(!created){
					
					returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
					returnJSON.put(Constant.DEVELOPER_MESSAGE, "Input is not valid or issue with database insert");
				}
				
			}catch(ParseException e){
				e.printStackTrace();
				returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Issue with Parsing");
			}
		
		}else{
			returnJSON.put(Constant.STATUS, Constant.UNAUTHORIZED_401);
		}
		
		response.getWriter().print(returnJSON);
		response.addHeader("Access-Control-Allow-Origin", Constant.ACCESS_CONTROL_ALLOW_ORIGIN);
		response.addHeader("Access-Control-Allow-Headers", Constant.ACCESS_CONTROL_ALLOW_HEADERS);
		response.addHeader("Access-Control-Allow-Methods", Constant.ACCESS_CONTROL_ALLOW_METHODS);
		response.addIntHeader("Access-Control-Max-Age", Constant.ACCESS_CONTROL_ALLOW_MAX_AGE);
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}
	
	private boolean isValidInputData(JSONObject jsonObject){
		
		slf4jLogger.info("Validate Input");
		try{
		String name = ((String) jsonObject.get(Constant.LOCATION_NAME)).trim();
		slf4jLogger.info("name"+name+" : "+LocationFieldsVal.validateName(name));
		String desc = ((String) jsonObject.get(Constant.LOCATION_DESCRIPTION)).trim();
		slf4jLogger.info("desc"+desc + ":"+CommonFieldsVal.validateDescription(desc));
		String keywords = ((String) jsonObject.get(Constant.LOCATION_KEYWORDS)).trim();
		slf4jLogger.info("keywords"+keywords+":" + CommonFieldsVal.validateKeywords(keywords));
		String addressLine1 = ((String) jsonObject.get(Constant.LOCATION_ADDRESS_LINE_1)).trim();
		String addressLine2 = ((String) jsonObject.get(Constant.LOCATION_ADDRESS_LINE_2)).trim();
		String city = ((String) jsonObject.get(Constant.LOCATION_CITY)).trim();
		String code = ((String) jsonObject.get(Constant.LOCATION_CODE)).trim();
		String state = ((String) jsonObject.get(Constant.LOCATION_STATE)).trim();
		String zipcode = ((String) jsonObject.get(Constant.LOCATION_ZIPCODE)).trim();
		String phoneNumber = ((String) jsonObject.get(Constant.LOCATION_PHONE_NUMBER)).trim();
		String faxNumber = ((String) jsonObject.get(Constant.LOCATION_FAX_NUMBER)).trim();
		String email = ((String) jsonObject.get(Constant.LOCATION_EMAIL)).trim();

		if(LocationFieldsVal.validateName(name) && CommonFieldsVal.validateDescription(desc) && 
				CommonFieldsVal.validateKeywords(keywords) && LocationFieldsVal.validateAddressLine1(addressLine1) && 
				LocationFieldsVal.validateLocationCode(code) && CommonFieldsVal.validateFieldId(state) && 
				LocationFieldsVal.validateAddressLine2(addressLine2) && LocationFieldsVal.validateCity(city) &&
				LocationFieldsVal.validateZipCode(zipcode) && LocationFieldsVal.validatePhoneNumber(phoneNumber) &&
				LocationFieldsVal.validateFaxNumber(faxNumber) && LocationFieldsVal.validateEmail(email)){
			slf4jLogger.info("Location accepted");
			return true;
		}
		return false;
		}catch(Exception e){
			e.printStackTrace();
			slf4jLogger.info("Parsing issue **********");
			return false;
		}
		
	}
	
	private Location parseLocation(JSONObject jsonObject){
		
		Location location = new Location();
		
		slf4jLogger.info("PArsing locaiton values");
		
		String name = ((String) jsonObject.get(Constant.LOCATION_NAME)).trim();
		String desc = ((String) jsonObject.get(Constant.LOCATION_DESCRIPTION)).trim();
		String keywords = ((String) jsonObject.get(Constant.LOCATION_KEYWORDS)).trim();
		String addressLine1 = ((String) jsonObject.get(Constant.LOCATION_ADDRESS_LINE_1)).trim();
		String addressLine2 = ((String) jsonObject.get(Constant.LOCATION_ADDRESS_LINE_2)).trim();
		String city = ((String) jsonObject.get(Constant.LOCATION_CITY)).trim();
		String code = ((String) jsonObject.get(Constant.LOCATION_CODE)).trim();
		String state = ((String) jsonObject.get(Constant.LOCATION_STATE)).trim();
		String zipcode = ((String) jsonObject.get(Constant.LOCATION_ZIPCODE)).trim();
		String phoneNumber = ((String) jsonObject.get(Constant.LOCATION_PHONE_NUMBER)).trim();
		String faxNumber = ((String) jsonObject.get(Constant.LOCATION_FAX_NUMBER)).trim();
		String email = ((String) jsonObject.get(Constant.LOCATION_EMAIL)).trim();
		
		//Mandatory Fields
		location.setName(name);
		location.setAddressLine1(addressLine1);
		location.setCity(city);
		location.setState(Long.parseLong(state));
		location.setZipCode(Long.parseLong(zipcode));
		location.setPhoneNumber(Long.parseLong(phoneNumber));
		location.setCode(code);
		location.setDesc(desc);
		location.setKeywords(keywords);
		location.setAddressLine2(addressLine2);
		location.setFaxNumber(Long.parseLong(faxNumber));
		location.setEmail(email);
		
		slf4jLogger.info("PArsing locaiton values done");
		return location;
	}

}
