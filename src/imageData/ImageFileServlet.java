package imageData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.maven.settings.Settings;

/**
 * Servlet implementation class ImageFileServlet
 */
@WebServlet("/imageFileServlet")
public class ImageFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		String imageName = request.getParameter("imageName");
		
		ServletContext ctx = getServletContext();
		File file=new File(ctx.getRealPath("."));
		
		File f = new File(file.getParent()+"/appData/photos/"+imageName);
		FileInputStream fis = new FileInputStream(f);
		int b = 0;
		while ((b = fis.read()) != -1) {
		        response.getOutputStream().write(b);
		}
		
        //response.setHeader("Content-Type", getServletContext().getMimeType(f.));
        response.setHeader("Content-Length", String.valueOf(f.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + f.getName() + "\"");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
