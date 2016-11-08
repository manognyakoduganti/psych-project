package autoFill;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.CommonFields;
import common.Constant;
import dao.FetchFieldDAO;
import dao.UserProfileDAO;
import fieldValidation.UserProfileFieldsVal;

/**
 * Servlet implementation class FetchCommonFieldServlet
 */
@WebServlet("/FetchCommonFieldServlet")
public class FetchCommonFieldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchCommonFieldServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
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
		String fieldName="";
		
		HttpSession session = request.getSession(false);
		
		if(session != null && session.getAttribute(Constant.ROLE) != null &&
				session.getAttribute(Constant.EMAIL) != null &&
				session.getAttribute(Constant.USER_ID) != null &&
				(session.getAttribute(Constant.ROLE).equals(Constant.GLOBAL_ADMIN) ||
				(session.getAttribute(Constant.ROLE).equals(Constant.LOCAL_ADMIN)))){
			
			try {
				obj = parser.parse(sb.toString());
				JSONObject jsonObject = (JSONObject) obj;
				fieldName = ((String) jsonObject.get(Constant.FIELD_NAME)).trim();
			
				if(fieldName.equals("")){
					returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
				}else{
					List<CommonFields> fieldValueList = FetchFieldDAO.extractFieldValues(fieldName);
					JSONArray fieldArray = new JSONArray();
					int length = fieldValueList.size();
					for(int i = 0; i< length; i++){
						JSONObject commonFieldsJSON = new JSONObject();
						CommonFields commonFields= fieldValueList.get(i);
						commonFieldsJSON.put(Constant.FIELD_ID, commonFields.getFieldId());
						commonFieldsJSON.put(Constant.FIELD_VALUE, commonFields.getFieldValue());
						fieldArray.add(commonFieldsJSON);
					}
					
					returnJSON.put(Constant.STATUS, Constant.OK_200);
					returnJSON.put(Constant.RESULTS, fieldArray);
				}
				
			}catch(ParseException e){
				e.printStackTrace();
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
