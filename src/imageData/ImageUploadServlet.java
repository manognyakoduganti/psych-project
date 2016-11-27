package imageData;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;

import common.Constant;
import common.ImageInfo;
import common.Sessions;
import dao.ImageDAO;
import fieldValidation.CommonFieldsVal;
import fieldValidation.ImageFieldsVal;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/imageUpload")
public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletFileUpload fileUpload;
	String imageFolder;
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
	
	public String getImageFolder() {
		return imageFolder;
	}

	public void setImageFolder(String imageFolder) {
		this.imageFolder = imageFolder;
	}

	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(repository);
        fileUpload = new ServletFileUpload(factory);
        imageFolder = servletContext.getInitParameter(Constant.IMAGE_FOLDER);
      }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//if (ServletFileUpload.isMultipartContent(request)) {
		JSONObject returnJSON = new JSONObject();
        try {
        	HttpSession session = request.getSession(false);
        	if(Sessions.isValidGlobalAdminSession(session)){
	        	ImageInfo imageInfo = new ImageInfo();
	            List<FileItem> files = fileUpload.parseRequest(request);
	            boolean badInput = false;
	            boolean folderCreationIssue = false;
	            if (files != null && !files.isEmpty()) {
	            	
	                for (FileItem item : files) {
	                	
	                	if(!validateInput(item.getFieldName(), item.getName())){
	            			badInput = false;
	            			break;
	            		}
	                	if(item.isFormField()){
	                		setInputInfo(item.getFieldName(), item.getName(), imageInfo);
	                	}else{
	                		Random random = new Random();
	                    	int folderId = random.nextInt(20);
	                    	if(!validateFolder(Integer.toString(folderId))){
	                    		folderCreationIssue = true;
	                    		break;
	                    	}
	                    	String extension = FilenameUtils.getExtension(item.getName());
	                		UUID uuid = UUID.randomUUID();
	                		
	                		String filePath = Integer.toString(folderId) +"/"+ uuid.toString()+"."+extension;
	                		imageInfo.setUuid(uuid.toString());
	                		imageInfo.setInputStream(item.getInputStream());
	                		imageInfo.setImageShortPath(filePath);
	                		imageInfo.setImageFullPath(imageFolder+"/"+filePath);
	                	}
	                }
	            }
	            if(!badInput && !folderCreationIssue){
	            	// First save file on disk
	            	if(saveInputFile(imageInfo)){
	            		//Second save into database
	            		boolean created = ImageDAO.createImage(imageInfo);
	            		if(created){
	            			returnJSON.put(Constant.STATUS, Constant.OK_200);
	            			returnJSON.put(Constant.IMAGE_PATH, imageInfo.getImageShortPath());
	            			returnJSON.put(Constant.IMAGE_UUID, imageInfo.getUuid());
	            			response.getWriter().print(returnJSON);
	            			return;
	            		}else{
	            			returnJSON.put(Constant.DEVELOPER_MESSAGE, "Issue while saving the image info in database");
	            		}
	            	}else{
	            		returnJSON.put(Constant.DEVELOPER_MESSAGE, "Issue in saving the file");
	            	}
	            	
	            }else if(badInput){
	            	returnJSON.put(Constant.DEVELOPER_MESSAGE, "Bad input data");
	            }else if(folderCreationIssue){
	            	returnJSON.put(Constant.DEVELOPER_MESSAGE, "Issue is creating folder");
	            }
	            returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
	            response.getWriter().print(returnJSON);
				return;
        	}else{
        		returnJSON.put(Constant.STATUS, Constant.UNAUTHORIZED_401);
	            response.getWriter().print(returnJSON);
        	}
            
        } catch (FileUploadException e) {
            e.printStackTrace();
            returnJSON.put(Constant.STATUS, Constant.BADREQUEST_400);
            returnJSON.put(Constant.DEVELOPER_MESSAGE, "Issue in Parsing the input data");
            response.getWriter().print(returnJSON);
			return;
        }
	}
	
	private boolean validateInput(String fieldName, String fieldValue){
		
		if(fieldName.equals(Constant.IMAGE_NAME)){
			return ImageFieldsVal.validateImageName(fieldValue);
		}else if(fieldName.equals(Constant.IMAGE_DESCRIPTION)){
			return CommonFieldsVal.validateDescription(fieldValue);
		}else if(fieldName.equals(Constant.IMAGE_CATEGORY_ID)){
			return CommonFieldsVal.validateFieldId(fieldValue);
		}else if(fieldName.equals(Constant.IMAGE_INTENSITY)){
			return ImageFieldsVal.validateImageIntensity(fieldValue);
		}else if(fieldName.equals(Constant.IMAGE_TYPE_ID)){
			return CommonFieldsVal.validateFieldId(fieldValue);
		}else if(fieldName.equals(Constant.IMAGE_FILE)){
			String extension = FilenameUtils.getExtension(fieldValue);
			if(extension.toLowerCase().equals("jpeg") || extension.toLowerCase().equals("png") ||
					extension.toLowerCase().equals("jpg")){
				return true;
			}
		}
		return false;
	}
	
	private void setInputInfo(String fieldName, String fieldValue, ImageInfo imageInfo){
		if(fieldName.equals(Constant.IMAGE_NAME)){
			imageInfo.setImageName(fieldValue);
		}else if(fieldName.equals(Constant.IMAGE_DESCRIPTION)){
			imageInfo.setImageDesc(fieldValue);
		}else if(fieldName.equals(Constant.IMAGE_CATEGORY_ID)){
			imageInfo.setImageCategoryId(Long.parseLong(fieldValue));
		}else if(fieldName.equals(Constant.IMAGE_INTENSITY)){
			imageInfo.setImageIntensity(Long.parseLong(fieldValue));
		}else if(fieldName.equals(Constant.IMAGE_TYPE_ID)){
			imageInfo.setImageTypeId(Long.parseLong(fieldValue));
		}
	}
	
	private boolean saveInputFile(ImageInfo imageInfo){
		
		InputStream in = new BufferedInputStream(imageInfo.getInputStream());
		try {
			OutputStream out = new FileOutputStream(new File(imageInfo.getImageFullPath()));
			IOUtils.copy(in,out);
			in.close();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	private boolean validateFolder(String folderName){
		File f = new File(imageFolder+"/"+folderName);
		try{
		  if(f.mkdir()) { 
		    return true;
		  } else {
		      return true;
		  }
		 } catch(Exception e){
		  e.printStackTrace();
		}
		return false;
	}

}
