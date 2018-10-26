package com.pawem.DA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.pawem.model.Dropout;
import com.pawem.util.ConnectionManager;

public class dropoutDA {
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	public static boolean createDropoutInfo(String insertTableSQL){
		Statement stmt = null;
		
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean check = stmt.execute(insertTableSQL);
				if(check){
					System.out.println("Sorry, cannot dropout member!");
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
	public static double checkYearDropout(String checkQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(checkQuery);
			boolean more = rs.next();
			if(!more){
				return 0.0;
			}else if(more){
				return Double.parseDouble(rs.getString("GAP"));
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
		return 0.0;
	}

	public static boolean deleteDropoutInfo(String deleteDropQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean more = stmt.execute(deleteDropQuery);
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
	public static List<Dropout> viewDropoutList(List<Dropout> dropoutList, String listDROPOUTQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(listDROPOUTQuery);
			while(rs.next()){
				dropoutList.add(new Dropout(Integer.parseInt(rs.getString("MEMID")), null,null,null, null, null, null, null, null, null, null, null, null, null,null,null, null));
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
		
		return dropoutList;
	}


	public static Dropout viewDropoutInfo(Dropout dropoutList, String selectDROPOUTQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(selectDROPOUTQuery);
			boolean more = rs.next();
			if(!more){
				dropoutList = new Dropout(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
			}else if(more){
				dropoutList = new Dropout(Integer.parseInt(rs.getString("MEMID")), rs.getString("MEMIC"),rs.getString("MEMNAME"),rs.getString("MEMGENDER"), rs.getString("MEMMARRIAGESTATUS"), rs.getString("MEMADDRESS"), rs.getString("MEMSPOUSENAME"), rs.getString("MEMLIVINGWITH"), rs.getString("MEMHOMENO"), rs.getString("MEMMOBILENO"), rs.getString("MEMACADEMIC"), rs.getString("MEMJOB"), rs.getString("MEMLASTJOB"), rs.getString("MEMLASTWORKPLACE"),rs.getString("MEMRACE"),rs.getString("DROPOUTDATE"),rs.getString("DROPOUTREASON") );
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
		
		return dropoutList;
	}
	public static boolean updateDropoutInfo(String updateQuery) {
Statement stmt = null;
		
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean check = stmt.execute(updateQuery);
				if(check){
					System.out.println("Sorry, cannot register member!");
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
	public static List<Dropout> calculateDropoutMember(List<Dropout> list, String sql) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				list.add(new Dropout(Integer.parseInt(rs.getString("MEMID")), rs.getString("MEMIC"), rs.getString("MEMNAME"), rs.getString("MEMGENDER"), null, null, null, null, null, null, null, null, null, null, rs.getString("MEMRACE"), null, rs.getString("DROPOUTREASON")));
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
}
