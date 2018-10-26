package com.pawem.DA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.pawem.model.Active;
import com.pawem.util.ConnectionManager;

public class activeDA {
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	
	public static boolean deleteActiveInfo(String deleteActQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean more = stmt.execute(deleteActQuery);
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

	public static List<Active> viewActiveList(List<Active> activeList, String listACTIVEQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(listACTIVEQuery);
			while(rs.next()){
				activeList.add(new Active(Integer.parseInt(rs.getString("MEMID")), rs.getString("MEMIC"),rs.getString("MEMNAME"),null, null, null, null, null, null, null, null, null, null, null,rs.getString("MEMBERSHIPNUM"),rs.getString("MEMBERSHIPDATE"), null));
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

	public static Active viewActiveInfo(Active activeList, String selectACTIVEQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(selectACTIVEQuery);
			boolean more = rs.next();
			if(!more){
				System.out.println("Sorry, no user found");
			}else if(more){
				activeList = new Active(Integer.parseInt(rs.getString("MEMID")), rs.getString("MEMIC"),rs.getString("MEMNAME"),rs.getString("MEMGENDER"), rs.getString("MEMMARRIAGESTATUS"), rs.getString("MEMADDRESS"), rs.getString("MEMSPOUSENAME"), rs.getString("MEMLIVINGWITH"), rs.getString("MEMHOMENO"), rs.getString("MEMMOBILENO"), rs.getString("MEMACADEMIC"), rs.getString("MEMJOB"), rs.getString("MEMLASTJOB"), rs.getString("MEMLASTWORKPLACE"),rs.getString("MEMBERSHIPNUM"),rs.getString("MEMBERSHIPDATE"), rs.getString("MEMRACE"));
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
	
}
