package imageData;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
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
import org.apache.commons.io.IOUtils;
import common.Constant;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/imageUpload")
public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletFileUpload fileUpload;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
	public ServletFileUpload getFileUpload() {
		return fileUpload;
	}


	public void setFileUpload(ServletFileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(repository);
        fileUpload = new ServletFileUpload(factory);
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
		//if (ServletFileUpload.isMultipartContent(request)) {
            try {
            	JSONObject returnJSON = new JSONObject();
            	
                List<FileItem> files = fileUpload.parseRequest(request);

                if (files != null && !files.isEmpty()) {
                    for (FileItem item : files) {
                    	
                    	if(item.isFormField()){
                    		 System.out.println("* " + item.getName() + " " + item.getSize() + " bytes." 
                                     + " field name : "+item.getFieldName());
                    	}else{
                    		//if(item.getFieldName().equals("file")){
                    		System.out.println("** " + item.getSize() + " bytes." + " field name : "+item.getFieldName() +" : "+ item.getInputStream().toString());
                    		
                    		InputStream in = new BufferedInputStream(item.getInputStream());
                	        // Read the image and close the stream
                    		OutputStream out = new FileOutputStream(new File("/Users/Darshan/Desktop/sample.jpg"));
                    		IOUtils.copy(in,out);
                    		in.close();
                    		out.close();
                    	}
                    		 
                    }
                }
                response.getWriter().print(returnJSON);
                
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
	}

}
