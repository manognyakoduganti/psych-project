package common;

import java.io.InputStream;

public class ImageInfo {
	
	String imageName;
	String imageDesc;
	Long imageTypeId;
	Long imageCategoryId;
	Long imageIntensity;
	String imageFullPath;
	String imageShortPath;
	InputStream inputStream;
	String uuid;
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageDesc() {
		return imageDesc;
	}
	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}
	public Long getImageTypeId() {
		return imageTypeId;
	}
	public void setImageTypeId(Long imageTypeId) {
		this.imageTypeId = imageTypeId;
	}
	public Long getImageCategoryId() {
		return imageCategoryId;
	}
	public void setImageCategoryId(Long imageCategoryId) {
		this.imageCategoryId = imageCategoryId;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public Long getImageIntensity() {
		return imageIntensity;
	}
	public void setImageIntensity(Long imageIntensity) {
		this.imageIntensity = imageIntensity;
	}
	public String getImageFullPath() {
		return imageFullPath;
	}
	public void setImageFullPath(String imageFullPath) {
		this.imageFullPath = imageFullPath;
	}
	public String getImageShortPath() {
		return imageShortPath;
	}
	public void setImageShortPath(String imageShortPath) {
		this.imageShortPath = imageShortPath;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
