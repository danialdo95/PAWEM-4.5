package com.pawem.DA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.pawem.model.Member;
import com.pawem.util.ConnectionManager;

public class attendanceDA {
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	public static void createAttendanceInfo(String insertQuery) {
		Statement stmt = null;

		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			stmt.execute(insertQuery);
		}catch(Exception ex){
			System.out.println("Create account failed: An Exception has occurred! " + ex);
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
		
	}
	public static void updateAttendanceInfo(String updateQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			stmt.execute(updateQuery);
		}catch(Exception ex){
			System.out.println("Update account failed: An Exception has occurred! " + ex);
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
	}
	public static void deleteAllAttendanceInfo(String deleteQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean more = stmt.execute(deleteQuery);
			if(more){
				System.out.println("delete failed");
			}else{
				System.out.println("delete succeed.");
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
		
	}
	public static int calculateAttendance(String count) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(count);
			boolean more = rs.next();
			if(!more){
				return 0;
			}else if(more){
				return Integer.parseInt(rs.getString("NUM"));
			}
		}catch(Exception ex){
			System.out.println("Create account failed: An Exception has occurred! " + ex);
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
