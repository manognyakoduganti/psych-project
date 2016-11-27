package imageData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import common.Constant;


public class ImageUploadServletTest {
	

	ImageUploadServlet imageUploadServlet;
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	ServletContext servletContext;
	DiskFileItemFactory diskFileItemFactory;
	String inputFolder = "/tmp/imageInput";
	String outputFolder = "/tmp/imageOutput";
	String name;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  imageUploadServlet = new ImageUploadServlet();
	  createFolder(inputFolder);
	  createFolder(outputFolder);
	  name = "Test Iamge";
	}
	
	@Test
	public void testValidImageUploadRequest() throws Exception {
	
		generateTestImage();
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		when(session.getAttribute(Constant.USER_ID)).thenReturn(1l);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(printWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		servletContext = mock(ServletContext.class);
		diskFileItemFactory = mock(DiskFileItemFactory.class);
		
		ServletConfig config = mock(ServletConfig.class);
		when(config.getServletContext()).thenReturn(servletContext);
        ServletFileUpload fileUpload = mock(ServletFileUpload.class);
        List<FileItem> files = new ArrayList<FileItem>();
        
        FileItem imageFileItem = mock(FileItem.class);
        when(imageFileItem.getName()).thenReturn("");
        when(imageFileItem.getFieldName()).thenReturn(Constant.IMAGE_FILE);
        when(imageFileItem.isFormField()).thenReturn(false);
        when(imageFileItem.getInputStream()).thenReturn(new FileInputStream(inputFolder+"/test.jpg"));
        when(imageFileItem.getName()).thenReturn("test.jpg");
        when(servletContext.getMimeType("test.jpg")).thenReturn("image/");
        
        FileItem imageName = mock(FileItem.class);
        when(imageName.getName()).thenReturn(name);
        when(imageName.getFieldName()).thenReturn(Constant.IMAGE_NAME);
        when(imageName.isFormField()).thenReturn(true);
        
        FileItem imageDescription = mock(FileItem.class);
        when(imageDescription.getName()).thenReturn("Test Image Description");
        when(imageDescription.getFieldName()).thenReturn(Constant.IMAGE_DESCRIPTION);
        when(imageDescription.isFormField()).thenReturn(true);
        
        FileItem imageCategoryId = mock(FileItem.class);
        when(imageCategoryId.getName()).thenReturn("1");
        when(imageCategoryId.getFieldName()).thenReturn(Constant.IMAGE_CATEGORY_ID);
        when(imageCategoryId.isFormField()).thenReturn(true);
        
        FileItem imageIntensityId = mock(FileItem.class);
        when(imageIntensityId.getName()).thenReturn("2");
        when(imageIntensityId.getFieldName()).thenReturn(Constant.IMAGE_INTENSITY);
        when(imageIntensityId.isFormField()).thenReturn(true);
        
        FileItem imageTypeId = mock(FileItem.class);
        when(imageTypeId.getName()).thenReturn("55");
        when(imageTypeId.getFieldName()).thenReturn(Constant.IMAGE_TYPE_ID);
        when(imageTypeId.isFormField()).thenReturn(true);
        
        files.add(imageFileItem);
        files.add(imageName);
        files.add(imageDescription);
        files.add(imageCategoryId);
        files.add(imageIntensityId);
        files.add(imageTypeId);
        
        when(fileUpload.parseRequest(request)).thenReturn(files);
        imageUploadServlet.setImageFolder(outputFolder);
        imageUploadServlet.setFileUpload(fileUpload);
        imageUploadServlet.doPost(request, response);
        
        JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(Constant.OK_200, (String) jsonObject.get(Constant.STATUS));
		assertTrue(doesFileExists((outputFolder+"/"+(String) jsonObject.get(Constant.IMAGE_PATH))));
		
		deleteFile(outputFolder+"/"+(String) jsonObject.get(Constant.IMAGE_PATH));
		deleteFile(inputFolder+"/test.jpg");
	}
	
	@Test
	public void testInValidImageCreateRequest() throws Exception {
	
		generateTestImage();
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		when(session.getAttribute(Constant.USER_ID)).thenReturn(1l);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(printWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		servletContext = mock(ServletContext.class);
		diskFileItemFactory = mock(DiskFileItemFactory.class);
		
		ServletConfig config = mock(ServletConfig.class);
		when(config.getServletContext()).thenReturn(servletContext);
        ServletFileUpload fileUpload = mock(ServletFileUpload.class);
        List<FileItem> files = new ArrayList<FileItem>();
        
        FileItem imageFileItem = mock(FileItem.class);
        when(imageFileItem.getName()).thenReturn("");
        when(imageFileItem.getFieldName()).thenReturn(Constant.IMAGE_FILE);
        when(imageFileItem.isFormField()).thenReturn(false);
        when(imageFileItem.getInputStream()).thenReturn(null);
        when(imageFileItem.getName()).thenReturn("test.jpg");
        when(servletContext.getMimeType("test.jpg")).thenReturn("image/");
        
        FileItem imageName = mock(FileItem.class);
        when(imageName.getName()).thenReturn("Test Iamge");
        when(imageName.getFieldName()).thenReturn(Constant.IMAGE_NAME);
        when(imageName.isFormField()).thenReturn(true);
        
        FileItem imageDescription = mock(FileItem.class);
        when(imageDescription.getName()).thenReturn("Test Image Description");
        when(imageDescription.getFieldName()).thenReturn(Constant.IMAGE_DESCRIPTION);
        when(imageDescription.isFormField()).thenReturn(true);
        
        FileItem imageCategoryId = mock(FileItem.class);
        when(imageCategoryId.getName()).thenReturn("1");
        when(imageCategoryId.getFieldName()).thenReturn(Constant.IMAGE_CATEGORY_ID);
        when(imageCategoryId.isFormField()).thenReturn(true);
        
        FileItem imageIntensityId = mock(FileItem.class);
        when(imageIntensityId.getName()).thenReturn("2");
        when(imageIntensityId.getFieldName()).thenReturn(Constant.IMAGE_INTENSITY);
        when(imageIntensityId.isFormField()).thenReturn(true);
        
        FileItem imageTypeId = mock(FileItem.class);
        when(imageTypeId.getName()).thenReturn("55");
        when(imageTypeId.getFieldName()).thenReturn(Constant.IMAGE_TYPE_ID);
        when(imageTypeId.isFormField()).thenReturn(true);
        
        files.add(imageFileItem);
        files.add(imageName);
        files.add(imageDescription);
        files.add(imageCategoryId);
        files.add(imageIntensityId);
        files.add(imageTypeId);
        
        when(fileUpload.parseRequest(request)).thenReturn(files);
        imageUploadServlet.setImageFolder(outputFolder);
        imageUploadServlet.setFileUpload(fileUpload);
        imageUploadServlet.doPost(request, response);
        
        JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		deleteFile(inputFolder+"/test.jpg");
		
		assertEquals(Constant.BADREQUEST_400, (String) jsonObject.get(Constant.STATUS));

	}
	
	@Test
	public void testInValidSessionImageUploadRequest() throws Exception {
	
		generateTestImage();
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		when(request.getSession(false)).thenReturn(null);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(printWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		servletContext = mock(ServletContext.class);
		diskFileItemFactory = mock(DiskFileItemFactory.class);
		
		ServletConfig config = mock(ServletConfig.class);
		when(config.getServletContext()).thenReturn(servletContext);
        ServletFileUpload fileUpload = mock(ServletFileUpload.class);
        List<FileItem> files = new ArrayList<FileItem>();
        
        FileItem imageFileItem = mock(FileItem.class);
        when(imageFileItem.getName()).thenReturn("");
        when(imageFileItem.getFieldName()).thenReturn(Constant.IMAGE_FILE);
        when(imageFileItem.isFormField()).thenReturn(false);
        when(imageFileItem.getInputStream()).thenReturn(new FileInputStream(inputFolder+"/test.jpg"));
        when(imageFileItem.getName()).thenReturn("test.jpg");
        when(servletContext.getMimeType("test.jpg")).thenReturn("image/");
        
        FileItem imageName = mock(FileItem.class);
        when(imageName.getName()).thenReturn("Test Iamge");
        when(imageName.getFieldName()).thenReturn(Constant.IMAGE_NAME);
        when(imageName.isFormField()).thenReturn(true);
        
        FileItem imageDescription = mock(FileItem.class);
        when(imageDescription.getName()).thenReturn("Test Image Description");
        when(imageDescription.getFieldName()).thenReturn(Constant.IMAGE_DESCRIPTION);
        when(imageDescription.isFormField()).thenReturn(true);
        
        FileItem imageCategoryId = mock(FileItem.class);
        when(imageCategoryId.getName()).thenReturn("1");
        when(imageCategoryId.getFieldName()).thenReturn(Constant.IMAGE_CATEGORY_ID);
        when(imageCategoryId.isFormField()).thenReturn(true);
        
        FileItem imageIntensityId = mock(FileItem.class);
        when(imageIntensityId.getName()).thenReturn("2");
        when(imageIntensityId.getFieldName()).thenReturn(Constant.IMAGE_INTENSITY);
        when(imageIntensityId.isFormField()).thenReturn(true);
        
        FileItem imageTypeId = mock(FileItem.class);
        when(imageTypeId.getName()).thenReturn("55");
        when(imageTypeId.getFieldName()).thenReturn(Constant.IMAGE_TYPE_ID);
        when(imageTypeId.isFormField()).thenReturn(true);
        
        files.add(imageFileItem);
        files.add(imageName);
        files.add(imageDescription);
        files.add(imageCategoryId);
        files.add(imageIntensityId);
        files.add(imageTypeId);
        
        when(fileUpload.parseRequest(request)).thenReturn(files);
        imageUploadServlet.setImageFolder(outputFolder);
        imageUploadServlet.setFileUpload(fileUpload);
        imageUploadServlet.doPost(request, response);
        
        JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(Constant.UNAUTHORIZED_401, (String) jsonObject.get(Constant.STATUS));
	}
	
	public void generateTestImage(){
		
		try {
			int width = 100;
			int height = 100;
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        // Create a graphics which can be used to draw into the buffered image
	        Graphics2D g2d = bufferedImage.createGraphics();
	        // fill all the image with white
	        g2d.setColor(Color.white);
	        g2d.fillRect(0, 0, width, height);
	        // create a circle with black
	        g2d.setColor(Color.black);
	        g2d.fillOval(0, 0, width, height);
	        // create a string with yellow
	        g2d.setColor(Color.yellow);
	        g2d.drawString("Psych Psych", 50, 120);
	 
	        // Disposes of this graphics context and releases any system resources that it is using.
	        g2d.dispose();
	 
	        // Save as JPEG
	        File file = new File(inputFolder+"/test.jpg");
			ImageIO.write(bufferedImage, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void createFolder(String folderPath){
		File f = new File(folderPath);
		if(f.mkdir()){
			System.out.println(folderPath + " Directory Created");
		}
	}
	
	public boolean doesFileExists(String filePath){
		File f = new File(filePath);
		return f.exists();
	}
	
	public void deleteFile(String filePath){
		File fInput = new File(filePath);
		fInput.delete();
	}
	
	
	public void testValidImageDuplicateSearchRequest() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		when(response.getWriter()).thenReturn(printWriter);
		
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		when(session.getAttribute(Constant.USER_ID)).thenReturn(1);
		
		when(request.getParameter(Constant.IMAGE_NAME)).thenReturn(name);
		imageUploadServlet.doGet(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((Boolean) jsonObject.get(Constant.RESULTS), true);
		assertEquals(Constant.OK_200, (String) jsonObject.get(Constant.STATUS));
	}
	
	public void testInValidImageCategoryDuplicateSearchRequest() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		when(response.getWriter()).thenReturn(printWriter);
		
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		when(session.getAttribute(Constant.USER_ID)).thenReturn(1l);
		
		when(request.getParameter(Constant.IMAGE_NAME)).thenReturn("#$%@DSSFD");
		imageUploadServlet.doGet(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals((Boolean) jsonObject.get(Constant.RESULTS), false);
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.OK_200);
	}
	
	@Test
	public void testGetAllImages() throws ServletException, IOException, ParseException{
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(request.getReader()).thenReturn(bufferedReader);
		
		when(response.getWriter()).thenReturn(printWriter);
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute(Constant.ROLE)).thenReturn(Constant.GLOBAL_ADMIN);
		when(session.getAttribute(Constant.EMAIL)).thenReturn("patel.dars@husky.neu.edu");
		when(session.getAttribute(Constant.USER_ID)).thenReturn(1l);
		
		imageUploadServlet.doGet(request, response);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONArray results = (JSONArray) jsonObject.get(Constant.RESULTS);
		assertTrue(results.size() >= 0);
		assertEquals((String) jsonObject.get(Constant.STATUS), Constant.OK_200);
	}

}
