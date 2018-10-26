package com.pawem.DA;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.pawem.model.Photos;
import com.pawem.util.ConnectionManager;

public class photosDA {

	static Connection currentCon = null;
	static ResultSet rs = null;
	public static List<Photos> viewPhotoInfo(List<Photos> picList, String picQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(picQuery);
			while(rs.next()){
				picList.add(new Photos(Integer.parseInt(rs.getString("id")), rs.getString("title"),rs.getBinaryStream("PHOTO"),0, Integer.parseInt(rs.getString("eventID"))));
			}
		}catch(Exception ex){
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		}finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}
			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}
				currentCon = null;
			}
		}
		
		return picList;
	}
	public static boolean createPhotoInfo(Photos aP) {
		Statement stmt = null;
		try{
			System.out.println("dalam DA create");
			currentCon = ConnectionManager.getConnection();
			currentCon.setAutoCommit(false);
			System.out.println("after commit set false");
		    PreparedStatement ps = currentCon.prepareStatement("insert into photos (id,title,photo,eventid) values(seq_picid.nextval,?,?,?)");
		    System.out.println("hi");
		    ps.setString(1, aP.getPhotoDescription());
		    // size must be currentConverted to int otherwise it results in error
		    ps.setBlob(2, aP.getPhotoContent());
		    ps.setString(3, Integer.toString(aP.getEventID()));
		    System.out.println(ps);
		    ResultSet rs = ps.executeQuery();
            currentCon.commit();
		    if (rs.next()) {
		    	return true;
			}else{
				return false;
			}	    
		    
		}catch(Exception ex){
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		}finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}
			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}
				currentCon = null;
			}
		}
		
		return false;
	}
	public static int deletePhotoInfo(Photos aP) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt= currentCon.createStatement();
         ResultSet res = stmt.executeQuery("delete from photos where id = "+aP.getPhotoID());
         if(res!=null){
        	 while(res.next())
	         {
	        	ResultSet res1 = stmt.executeQuery("select id from photos where eventid = "+aP.getEventID());
	        	if(res1.next()){
	        		return 1;
	        	}else{
	 				return 2;
	        	}
	         	
	         }
         }else{
        	 return 0;
         }
		}catch(Exception ex){
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		}finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}
			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}
				currentCon = null;
			}
		}
		
		return 0;
	}
	
	
}
