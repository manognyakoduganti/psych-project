package report;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import common.Constant;
import common.Sessions;
import dao.ReportDAO;

/**
 * Servlet implementation class ReportServlet
 */
@WebServlet("/report")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", Constant.ACCESS_CONTROL_ALLOW_ORIGIN);
		response.addHeader("Access-Control-Allow-Headers", Constant.ACCESS_CONTROL_ALLOW_HEADERS);
		response.addHeader("Access-Control-Allow-Methods", Constant.ACCESS_CONTROL_ALLOW_METHODS);
		response.addIntHeader("Access-Control-Max-Age", Constant.ACCESS_CONTROL_ALLOW_MAX_AGE);
		response.setContentType("application/json;charset=UTF-8");
		
		JSONObject returnJSON = new JSONObject();
		
		String participant = request.getParameter(Constant.PARTICIPANT);
		String reportType = request.getParameter(Constant.REPORT_TYPE);
		
		HttpSession session = request.getSession(false);
		if(Sessions.isValidGlobalAdminSession(session)){
		
			if (participant != null && reportType != null){
				Long participantId = Long.parseLong(participant);
				if(reportType.equals("1")){
					returnJSON = ReportDAO.getAvgResponseTimeForImageResponses(participantId);
				}
				else{
					returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
					returnJSON.put(Constant.DEVELOPER_MESSAGE, "Report type not found.");
				}
			}
			else{
				returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
				returnJSON.put(Constant.DEVELOPER_MESSAGE, "Paramaters missing.");
			}
		}
		else{
			returnJSON.put(Constant.STATUS, Constant.UNAUTHORIZED_401);
		}
		
		response.getWriter().print(returnJSON);
	}

}
