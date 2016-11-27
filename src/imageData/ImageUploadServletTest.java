package imageData;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
	FileItem imageName;
	DiskFileItemFactory diskFileItemFactory;
	
	@Before
	public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	  imageUploadServlet = new ImageUploadServlet();
	}
	
	@Test
	public void testValidImageUploadRequest() throws Exception {
	
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
		File repository = new File("/tmp");
		when(servletContext.getAttribute("javax.servlet.context.tempdir")).thenReturn(repository);
		
        ServletFileUpload fileUpload = mock(ServletFileUpload.class);
        List<FileItem> files = new ArrayList<FileItem>();
        
        FileItem imageFileItem = mock(FileItem.class);
        when(imageFileItem.getName()).thenReturn("");
        when(imageFileItem.getFieldName()).thenReturn(Constant.IMAGE_FILE);
        when(imageFileItem.isFormField()).thenReturn(false);
        when(imageFileItem.getInputStream()).thenReturn(new FileInputStream("/opt/test.jpg"));
        
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
        imageUploadServlet.setFileUpload(fileUpload);
        imageUploadServlet.doPost(request, response);
        
        JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(Constant.OK_200, (String) jsonObject.get(Constant.STATUS));

	}
	
	
	@Test
	public void testInValidImageCreateRequest() throws Exception {
	
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
		File repository = new File("/opt");
		when(servletContext.getAttribute("javax.servlet.context.tempdir")).thenReturn(repository);
		
        ServletFileUpload fileUpload = mock(ServletFileUpload.class);
        List<FileItem> files = new ArrayList<FileItem>();
        
        FileItem imageFileItem = mock(FileItem.class);
        when(imageFileItem.getName()).thenReturn("");
        when(imageFileItem.getFieldName()).thenReturn(Constant.IMAGE_FILE);
        when(imageFileItem.isFormField()).thenReturn(false);
        when(imageFileItem.getInputStream()).thenReturn(null);
        
        FileItem imageName = mock(FileItem.class);
        when(imageName.getName()).thenReturn("Test Iamge");
        when(imageName.getFieldName()).thenReturn(Constant.IMAGE_NAME);
        when(imageName.isFormField()).thenReturn(true);
        
        FileItem imageDescription = mock(FileItem.class);
        when(imageDescription.getName()).thenReturn("Test Iamge");
        when(imageDescription.getFieldName()).thenReturn(Constant.IMAGE_DESCRIPTION);
        when(imageDescription.isFormField()).thenReturn(true);
        
        FileItem imageCategoryId = mock(FileItem.class);
        when(imageCategoryId.getName()).thenReturn("Test Iamge");
        when(imageCategoryId.getFieldName()).thenReturn(Constant.IMAGE_CATEGORY_ID);
        when(imageCategoryId.isFormField()).thenReturn(true);
        
        FileItem imageIntensityId = mock(FileItem.class);
        when(imageIntensityId.getName()).thenReturn("Test Iamge");
        when(imageIntensityId.getFieldName()).thenReturn(Constant.IMAGE_INTENSITY);
        when(imageIntensityId.isFormField()).thenReturn(true);
        
        FileItem imageTypeId = mock(FileItem.class);
        when(imageTypeId.getName()).thenReturn("Test Iamge");
        when(imageTypeId.getFieldName()).thenReturn(Constant.IMAGE_TYPE_ID);
        when(imageTypeId.isFormField()).thenReturn(true);
        
        files.add(imageFileItem);
        files.add(imageName);
        files.add(imageDescription);
        files.add(imageCategoryId);
        files.add(imageIntensityId);
        files.add(imageTypeId);
        
        when(fileUpload.parseRequest(request)).thenReturn(files);
        imageUploadServlet.setFileUpload(fileUpload);
        imageUploadServlet.doPost(request, response);
        
        JSONParser parser = new JSONParser();
		Object obj = parser.parse(stringWriter.getBuffer().toString());
		JSONObject jsonObject = (JSONObject) obj;
		
		assertEquals(Constant.BADREQUEST_400, (String) jsonObject.get(Constant.STATUS));

	}

}
