package com.pawem.DA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pawem.model.Event;
import com.pawem.util.ConnectionManager;

public class eventDA {
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	public static int createEventInfo(Event aE, String insertQuery) {
		PreparedStatement stmt = null;
		try{
			
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			stmt.execute();
			PreparedStatement ps = currentCon.prepareStatement("SELECT SEQ_EVENTID.CURRVAL FROM DUAL");
			rs = ps.executeQuery();
				if(rs.next()){
					return (int) rs.getLong(1);
				}else{
					return 0;
				}
		}catch(Exception ex){
			System.out.println("Create event failed: An Exception has occurred! " + ex);
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

	public static List<Event> viewEventList(String listQuery) {
		List<Event> eventList = new ArrayList<Event>();
		Statement stmt = null;
		
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(listQuery);
			while(rs.next()){
				Event aE = new Event(0, null, null, null, null, 0, null, null, null, null);
				aE.setEventID(Integer.parseInt(rs.getString("EVENTID")));
				aE.setEventName(rs.getString("EVENTNAME"));
				aE.setEventDate(rs.getString("EVENTDATE"));
				aE.setEventTime(rs.getString("EVENTTIME"));
				
				eventList.add(aE);
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
		
		return eventList;
	}

	public static boolean deleteEventInfo(String deleteQuery) {
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

	public static Event viewEventInfo(Event eventList, String eventQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(eventQuery);
			boolean more = rs.next();
			if(!more){
				System.out.println("Sorry, no user found");
			}else if(more){
				eventList = new Event(Integer.parseInt(rs.getString("EVENTID")), rs.getString("EVENTNAME"),rs.getString("EVENTDATE"),rs.getString("EVENTDESCRIPTION"), rs.getString("EVENTTYPE"), Float.valueOf(rs.getString("EVENTCOST")), rs.getString("EVENTVENUE"), rs.getString("EVENTCOMMINVOLVE"), null, rs.getString("EVENTTIME"));
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
		
		return eventList;
	}

	public static boolean updateEventInfo(String updateQuery) {
		Statement stmt = null;
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			boolean check = stmt.execute(updateQuery);
			if(check){
				return false;
			}else{
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

	public static int calculateTotalEvent(String calQuery) {
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

	public static List<Event> getEventList(String sql, List<Event> list) {
		Statement stmt = null;
		
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Event aE = new Event(0, null, null, null, null, 0, null, null, null, null);
				aE.setEventID(Integer.parseInt(rs.getString("EVENTID")));
				aE.setEventName(rs.getString("EVENTNAME"));
				aE.setEventDate(rs.getString("EVENTDATE"));
				aE.setEventTime(rs.getString("EVENTTIME"));
				aE.setEventVenue(rs.getString("EVENTVENUE"));
				aE.setEventCost(Float.parseFloat(rs.getString("EVENTCOST")));
				list.add(aE);
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
		return list;
	}

	public static List<Event> viewEventAttendance(String sql, List<Event> eventList) {
Statement stmt = null;
		
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Event aE = new Event(0, null, null, null, null, 0, null, null, null, null);
				aE.setEventName(rs.getString("EVENTNAME"));
				aE.setEventDate(rs.getString("EVENTDATE"));
				aE.setEventTime(rs.getString("EVENTTIME"));
				eventList.add(aE);
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
		return eventList;
	}

	public static List<Event> getEvenInfo(String sql, List<Event> eventList) {
Statement stmt = null;
		
		try{
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Event aE = new Event(0, null, null, null, null, 0, null, null, null, null);
				aE.setEventID(Integer.parseInt(rs.getString("EVENTID")));
				aE.setEventName(rs.getString("EVENTNAME"));
				aE.setEventTime(rs.getString("EVENTTIME"));
				aE.setEventCommInvolved(rs.getString("EVENTCOMMINVOLVE"));
				aE.setEventCost(Float.parseFloat(rs.getString("EVENTCOST")));
				eventList.add(aE);
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
		return eventList;
	}


}
