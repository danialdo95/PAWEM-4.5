package com.pawem.DA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.pawem.model.Member;
import com.pawem.util.ConnectionManager;

public class memberDA {

	static Connection currentCon = null;
	static ResultSet rs = null;

	public static boolean checkMember(String checkMemberSQL){
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(checkMemberSQL);
			boolean more = rs.next();
			if(!more){
				return true;
			}else if(more){
				System.out.println("Member already exist!");
				return false;
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
		return false;
	}
	public static boolean createMemberInfo(String insertTableSQL, String insertActive){
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			currentCon.setAutoCommit(false);
			stmt.addBatch(insertTableSQL);
			stmt.addBatch(insertActive);
			stmt.executeBatch();
			currentCon.commit();	
			return true;
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
		return false;
	}
	
	
	public static boolean updateMemberInfo(String updateTableSQL) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
				boolean check = stmt.execute(updateTableSQL);
				if(check){
					System.out.println("Sorry, cannot register staff!");
					return false;
				}else{
					System.out.println("Inserted!");
					return true;
				}
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
		return false;
	}
	public static boolean deleteMemberInfo(String deleteMemQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean more = stmt.execute(deleteMemQuery);
			if(more){
				System.out.println("delete failed");
				return false;
			}else{
				System.out.println("delete succeed.");
				return true;
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
	public static List<Member> getActiveMembers(List<Member> activeList,String getQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(getQuery);
			while(rs.next()){
				activeList.add(new Member(Integer.parseInt(rs.getString("MEMID")), rs.getString("MEMIC"),rs.getString("MEMNAME"),null, null, null, null, null, null, null, null, null, null, null, null));
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
		
		return activeList;
	}
	public static int calculateTotalActiveMember(String calQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(calQuery);
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
	public static List<Member> calculateMember(List<Member> list, String sql) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				list.add(new Member(Integer.parseInt(rs.getString("MEMID")), null, null, rs.getString("MEMGENDER"), null, null, null, null, null, null, null, null, null, null, rs.getString("MEMRACE")));
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
		return list;
	}
	public static List<Member> calculateAttendance(List<Member> list, String sql) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				list.add(new Member(Integer.parseInt(rs.getString("MEMID")), null, null, rs.getString("MEMGENDER"), null, null, null, null, null, null, null, null, null, null, rs.getString("MEMRACE")));
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
		return list;
	}
	public static int getMemberID(String sql) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(sql);
			boolean more = rs.next();
			if(!more){
				return 0;
			}else if(more){
				return Integer.parseInt(rs.getString("MEMID"));
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
	public static List<Member> viewAttendanceInfo(List<Member> attendanceList,String attendanceQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(attendanceQuery);
			while(rs.next()){
				attendanceList.add(new Member(Integer.parseInt(rs.getString("MEMID")), rs.getString("MEMIC"),rs.getString("MEMNAME"),null, null, null, null, null, null, null, null, null, null, null, null));
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
		
		return attendanceList;
	}
}
