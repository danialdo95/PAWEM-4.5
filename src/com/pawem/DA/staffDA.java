package com.pawem.DA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pawem.model.Staff;
import com.pawem.util.ConnectionManager;

public class staffDA {
	static Connection currentCon = null;
	static ResultSet rs = null;

	public static boolean login(String searchQuery, Staff aS){
		System.out.println("Inside login method");
		Statement stmt = null;
		
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();
			if(!more){
				System.out.println("Sorry, u not a registered user! Please signup first");
				return false;
			}else if(more){
				String name = rs.getString("STAFFNAME");
				String ic = rs.getString("STAFFIC");
				String role = rs.getString("STAFFROLE");
				String pwd = rs.getString("STAFFPWD");
				System.out.println("Welcome "+ name);
				aS.setStaffIC(ic);
				aS.setStaffName(name);
				aS.setStaffRole(role);
				aS.setStaffPwd(pwd);
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
	
	/*
	 * Create Staff Account 
	 */
	public static int checkStaff(String checkNumberStaffSQL){
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(checkNumberStaffSQL);
			int numberStaff = 0;
			while(rs.next()){
				++numberStaff;
			}
			return numberStaff;
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
	
	public static boolean createStaffAccount(String insertTableSQL){
		System.out.println("Inside createStaffAccount method");
		Statement stmt = null;

		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean check = stmt.execute(insertTableSQL);
			if(check){
				System.out.println("Sorry, cannot register staff!");
				return false;
			}else{
				System.out.println("Inserted!");
				return true;
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
	
	public static List<Staff> viewStaffList(String listQuery){
		List<Staff> staffList = new ArrayList<Staff>();
		Statement stmt = null;
		
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(listQuery);
			while(rs.next()){
				Staff s = new Staff(null, null, null, null, null);
				s.setStaffIC(rs.getString("staffIC"));
				s.setStaffName(rs.getString("staffName"));
				s.setStaffPwd(rs.getString("staffPwd"));
				s.setStaffRole(rs.getString("staffRole"));
				
				staffList.add(s);
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
		
		return staffList;
		
	}
	
	public static Staff viewStaffInfo(Staff aS, String selectQuery){
		System.out.println("Inside viewStaffInfo method");
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(selectQuery);
			boolean more = rs.next();
			if(!more){
				System.out.println("Sorry, no user found");
			}else if(more){
				
				String name = rs.getString("STAFFNAME");
				String kp = rs.getString("STAFFIC");
				String role = rs.getString("STAFFROLE");
				String pwd = rs.getString("STAFFPWD");
				aS.setStaffIC(kp);
				aS.setStaffName(name);
				aS.setStaffRole(role);
				aS.setStaffPwd(pwd);
				System.out.println(role);
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
		return aS;
		
	}
	
	public static boolean updateStaffAccount(String updateTableSQL){
		System.out.println("Inside updateStaffAccount method");
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean check = stmt.execute(updateTableSQL);
			
			if(check){
				System.out.println("Sorry, cannot update staff!");
				return false;
			}else{
				System.out.println("Updated!");
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
	
	public static boolean deleteStaffInfo(String deleteQuery){
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean more = stmt.execute(deleteQuery);
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
}
