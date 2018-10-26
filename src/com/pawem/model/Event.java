package com.pawem.model;

import java.util.ArrayList;
import java.util.List;

import com.pawem.DA.eventDA;


public class Event {
	
	private int eventID;
	private String eventName;
	private String eventDate;
	private String eventDescription;
	private String eventType;
	private String eventTime;
	private float eventCost;
	private String eventVenue;
	private String eventCommInvolved;
	private String staffIC;
	

	public Event(int eventID, String eventName, String eventDate,
			String eventDescription, String eventType, float eventCost,
			String eventVenue, String eventCommInvolved, String staffIC, String eventTime) {
		this.eventID = eventID;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventDescription = eventDescription;
		this.eventType = eventType;
		this.eventCost = eventCost;
		this.eventVenue = eventVenue;
		this.eventCommInvolved = eventCommInvolved;
		this.staffIC = staffIC;
		this.eventTime = eventTime;
	}

	public String getEventTime() {
		return eventTime;
	}


	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	public int getEventID() {
		return eventID;
	}


	public void setEventID(int eventID) {
		this.eventID = eventID;
	}


	public String getEventName() {
		return eventName;
	}


	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public String getEventDate() {
		return eventDate;
	}


	public void setEventDate(String date) {
		this.eventDate = date;
	}


	public String getEventDescription() {
		return eventDescription;
	}


	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}


	public String getEventType() {
		return eventType;
	}


	public void setEventType(String eventType) {
		this.eventType = eventType;
	}


	public float getEventCost() {
		return eventCost;
	}


	public void setEventCost(float eventCost) {
		this.eventCost = eventCost;
	}


	public String getEventVenue() {
		return eventVenue;
	}


	public void setEventVenue(String eventVenue) {
		this.eventVenue = eventVenue;
	}


	public String getEventCommInvolved() {
		return eventCommInvolved;
	}


	public void setEventCommInvolved(String eventCommInvolved) {
		this.eventCommInvolved = eventCommInvolved;
	}


	public String getStaffIC() {
		return staffIC;
	}


	public void setStaffIC(String staffIC) {
		this.staffIC = staffIC;
	}


	public int createEventInfo(Event aE) {
		String insertQuery = " INSERT INTO EVENT (EVENTID, EVENTNAME, EVENTDATE, EVENTDESCRIPTION, EVENTTYPE,"
				+ "EVENTCOST, EVENTVENUE, EVENTCOMMINVOLVE, STAFFIC, EVENTTIME)"
				+ " VALUES ( SEQ_EVENTID.nextval, '"+ aE.getEventName() +"', TO_DATE('"+ aE.getEventDate() +"', 'YYYY-MM-DD'),'"+ aE.getEventDescription() +"','"+ aE.getEventType() +"',"
						+ "'"+ aE.getEventCost() +"','"+ aE.getEventVenue() +"','"+ aE.getEventCommInvolved() +"','"+ aE.getStaffIC() +"','"+ aE.getEventTime() +"')";
		System.out.println(insertQuery);
		return eventDA.createEventInfo(aE, insertQuery);
	}

	public static List<Event> viewEventList() {
		String listQuery = "SELECT EVENTID, EVENTNAME, TO_CHAR(EVENTDATE, 'DD MON YYYY') AS EVENTDATE, EVENTTIME FROM EVENT ORDER BY EVENTID DESC";
		List<Event> eventList = eventDA.viewEventList(listQuery);
		return eventList;
	}

	public static boolean deleteEventInfo(String eventID) {
		String deleteQuery = "DELETE FROM EVENT WHERE EVENTID ='" + eventID +"'";
		return eventDA.deleteEventInfo(deleteQuery);
	}

	public static List<Object> viewEventInfo(List<Object> holder, int eventID) {
		
		String eventQuery = "SELECT EVENTID, EVENTNAME, TO_CHAR(EVENTDATE, 'DD MON YYYY') AS EVENTDATE, EVENTDESCRIPTION, EVENTTYPE, EVENTCOST, EVENTVENUE, EVENTCOMMINVOLVE, EVENTTIME "
				+ "FROM EVENT "
				+ "WHERE EVENTID = " + eventID + "";
		Event eventList = null;
		eventList = eventDA.viewEventInfo(eventList, eventQuery);
		
		List<Photos> picList = new ArrayList<Photos>();
		picList = Photos.viewPhotoInfo(picList, eventID);
		
		List<Member> attendanceList = new ArrayList<Member>();
		attendanceList = Member.viewAttendanceInfo(attendanceList, eventID);
		
		List<Member> activeList = new ArrayList<Member>();
		activeList = Member.getActiveMembers(activeList);
		
		holder.add(0,eventList);
		holder.add(1,picList);
		holder.add(2,attendanceList);
		holder.add(3,activeList);
		
		return holder;
	}

	public boolean updateEventInfo(Event aE) {
		String updateQuery = "UPDATE EVENT SET EVENTNAME ='" + aE.getEventName() +"', EVENTDATE = TO_DATE('"+ aE.getEventDate() +"', 'YYYY-MM-DD'), EVENTDESCRIPTION ='" + aE.getEventDescription() + "', EVENTTYPE ='" + aE.getEventType() + "', EVENTCOST = '" + aE.getEventCost() + "', EVENTVENUE = '" + aE.getEventVenue() + "', EVENTCOMMINVOLVE = '" + aE.getEventCommInvolved() + "', EVENTTIME = '" + aE.getEventTime() + "' WHERE EVENTID = " + aE.getEventID() + ""; 
		System.out.println(updateQuery);
		return eventDA.updateEventInfo(updateQuery);
	}

	public static int calculateTotalEvent() {
		String calQuery = "SELECT COUNT(EVENTID) AS NUM FROM EVENT";
		return eventDA.calculateTotalEvent(calQuery);
	}

	public static List<Event> getEventList(String type, int month, int year) {
		String sql = "";
		if(type.equals("A")){
			sql = "SELECT EVENTID, EVENTNAME, TO_CHAR(EVENTDATE, 'DD MON YYYY') AS EVENTDATE, EVENTTIME, EVENTVENUE, EVENTCOST FROM EVENT WHERE EVENTTYPE = 'A' AND EXTRACT(MONTH FROM EVENTDATE) = " + month +" AND  EXTRACT(YEAR FROM EVENTDATE) = " + year;
		}else if(type.equals("B")){
			sql = "SELECT EVENTID, EVENTNAME, TO_CHAR(EVENTDATE, 'DD MON YYYY') AS EVENTDATE, EVENTTIME, EVENTVENUE, EVENTCOST FROM EVENT WHERE EVENTTYPE = 'B' AND EXTRACT(MONTH FROM EVENTDATE) = " + month +" AND  EXTRACT(YEAR FROM EVENTDATE) = " + year;
		}
		List<Event> list = new ArrayList<Event>();
		return eventDA.getEventList(sql, list);
	}

	public static List<Event> viewEventAttendance(List<Event> eventList,int memID) {
		String sql ="SELECT E.EVENTNAME, TO_CHAR(E.EVENTDATE, 'DD MON YYYY') AS EVENTDATE, E.EVENTTIME FROM EVENT E JOIN ATTENDANCE A ON(E.EVENTID = A.EVENTID) JOIN MEMBER M ON(A.MEMID=M.MEMID) WHERE M.MEMID =" +memID;
		return eventDA.viewEventAttendance(sql, eventList);
	}

	public static List<Event> getEventInfo(String dateToday, List<Event> aE) {
		String sql = "SELECT EVENTID, EVENTNAME, EVENTTIME, EVENTCOMMINVOLVE, EVENTCOST FROM EVENT WHERE EVENTDATE = TO_DATE('"+dateToday+"','DD-MM-YYYY')";
		System.out.println(sql);
		return eventDA.getEvenInfo(sql, aE);
	}
}
