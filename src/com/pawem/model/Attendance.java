package com.pawem.model;

import java.util.List;

import com.pawem.DA.attendanceDA;

public class Attendance {

	private int memID;
	private int eventID;
	public Attendance(int memID, int eventID) {
		this.memID = memID;
		this.eventID = eventID;
	}
	public int getMemID() {
		return memID;
	}
	public void setMemID(int memID) {
		this.memID = memID;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	
	public static void createAttendanceInfo(Attendance attendance) {
		String insertQuery = "INSERT INTO ATTENDANCE (MEMID , EVENTID) VALUES ("+ attendance.getMemID() +", "+ attendance.getEventID() +")";
		System.out.println(insertQuery);
		attendanceDA.createAttendanceInfo(insertQuery);
	}
	public static void deleteAllAttendanceInfo(int eventID) {
		String deleteQuery = "DELETE FROM ATTENDANCE WHERE EVENTID = " + eventID;
		System.out.println(deleteQuery);
		attendanceDA.deleteAllAttendanceInfo(deleteQuery);
	}
	public static int calculateAttendance(int eventID) {
		String count = "SELECT COUNT(MEMID) AS NUM FROM ATTENDANCE WHERE EVENTID ="+eventID;
		return attendanceDA.calculateAttendance(count);
	}
	
}
