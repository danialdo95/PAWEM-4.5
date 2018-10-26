package com.pawem.model;

import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import com.pawem.DA.photosDA;

public class Photos {

	private int photoID;
	private String photoDescription;
	private InputStream photoContent;
	private int fileSize;
	private int eventID;
	
	public Photos(int photoID, String photoDescription, InputStream photoContent, int fileSize, int eventID) {
		
		this.photoID = photoID;
		this.photoDescription = photoDescription;
		this.photoContent = photoContent;
		this.eventID = eventID;
	}
	
	
	public int getPhotoID() {
		return photoID;
	}


	public void setPhotoID(int photoID) {
		this.photoID = photoID;
	}


	public String getPhotoDescription() {
		return photoDescription;
	}


	public void setPhotoDescription(String photoDescription) {
		this.photoDescription = photoDescription;
	}


	public InputStream getPhotoContent() {
		return photoContent;
	}


	public void setPhotoContent(InputStream photoContent) {
		this.photoContent = photoContent;
	}
	
	
	public int getFileSize() {
		return fileSize;
	}


	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}


	public int getEventID() {
		return eventID;
	}


	public void setEventID(int eventID) {
		this.eventID = eventID;
	}


	public static List<Photos> viewPhotoInfo(List<Photos> picList, int eventID) {
		String picQuery = "select * from photos where eventid =  " + eventID;
		return photosDA.viewPhotoInfo(picList, picQuery);
	}
	public static boolean createPhotoInfo(Photos aP){
		System.out.println("dalam create");
		return photosDA.createPhotoInfo(aP);
	}
	public static int deletePhotoInfo(Photos aP){
		return photosDA.deletePhotoInfo(aP);
	}


}
