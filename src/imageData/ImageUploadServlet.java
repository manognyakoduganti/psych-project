package imageData;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.Constant;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/imageUpload")
public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (ServletFileUpload.isMultipartContent(request)) {
            try {
            	/*
            	BufferedReader br = request.getReader();
            	StringBuilder sb = new StringBuilder();
                String str = null;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                
                JSONParser parser = new JSONParser();
        		Object obj;
        		obj = parser.parse(sb.toString());
				
				JSONObject jsonObject = (JSONObject) obj;
            	
        		String field = request.getParameter("model");
        		System.out.println("Field name :"+field + " + "+(String) jsonObject.get("model"));
        		*/
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletContext servletContext = this.getServletConfig().getServletContext();
                File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                factory.setRepository(repository);
                ServletFileUpload fileUpload = new ServletFileUpload(factory);
                List<FileItem> files = fileUpload.parseRequest(request);

                if (files != null && !files.isEmpty()) {
                    for (FileItem item : files) {
                    	if(item.getFieldName().equals("file")){
                    		 System.out.println("* " + item.getName() + " " + item.getSize() + " bytes." + " field name : "+item.getFieldName());
                    	}else{
                    		 System.out.println("* " + item.getName() + " " + item.getSize() + " bytes." 
                             + " field name : "+item.getFieldName());
                    	}
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }else{
        	
        	JSONObject returnJSON = new JSONObject();
    		String field = request.getParameter("model");
        	
        }
	}

}
