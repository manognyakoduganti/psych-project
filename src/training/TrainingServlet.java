package training;

import java.io.BufferedReader;
import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Constant;
import common.Location;
import common.Sessions;
import dao.LocationDAO;
import dao.TargetGroupDAO;
import dao.TrainingDAO;
import fieldValidation.CommonFieldsVal;
import fieldValidation.LocationFieldsVal;
import location.LocationServlet;

/**
 * Servlet implementation class TrainingServlet
 */
@WebServlet("/training")
public class TrainingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger slf4jLogger = LoggerFactory.getLogger(TrainingServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		slf4jLogger.info("Entered in doGet method of TargetGroupServlet");
		JSONObject returnJSON = new JSONObject();
		
		HttpSession session = request.getSession(false);
		if(Sessions.isValidGlobalAdminSession(session)){
			if((request.getParameter(Constant.TRAINING_DROPDOWN) != null && 
					request.getParameter(Constant.TRAINING_DROPDOWN).equals(Constant.YES))){
				
				returnJSON.put(Constant.RESULTS, TrainingDAO.fetchAllTrainingName());
				returnJSON.put(Constant.STATUS, Constant.OK_200);
				
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
		
	}
	
}
