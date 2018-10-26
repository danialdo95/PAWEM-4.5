package com.pawem.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.pawem.DA.attendanceDA;
import com.pawem.DA.memberDA;

public class Member {

	private int memID;
	private String memIC;
	private String memName;
	private String memGender;
	private String memMarriageStatus;
	private String memAddress;
	private String memSpouseName;
	private String memLivingWith;
	private String memHomeNo;
	private String memMobileNo;
	private String memAcademic;
	private String memCurrentJob;
	private String memLastJob;
	private String memLastWorkplace;
	private String memRace;
	
	
	
	public Member(int memID, String memIC, String memName, String memGender,
			String memMarriageStatus, String memAddress, String memSpouseName,
			String memLivingWith, String memHomeNo, String memMobileNo,
			String memAcademic, String memCurrentJob, String memLastJob,
			String memLastWorkplace, String memRace) {
		this.memID = memID;
		this.memIC = memIC;
		this.memName = memName;
		this.memGender = memGender;
		this.memMarriageStatus = memMarriageStatus;
		this.memAddress = memAddress;
		this.memSpouseName = memSpouseName;
		this.memLivingWith = memLivingWith;
		this.memHomeNo = memHomeNo;
		this.memMobileNo = memMobileNo;
		this.memAcademic = memAcademic;
		this.memCurrentJob = memCurrentJob;
		this.memLastJob = memLastJob;
		this.memLastWorkplace = memLastWorkplace;
		this.memRace = memRace;
	}
	
	public int getMemID() {
		return memID;
	}

	public void setMemID(int memID) {
		this.memID = memID;
	}

	public String getMemIC() {
		return memIC;
	}
	public void setMemIC(String memIC) {
		this.memIC = memIC;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemGender() {
		return memGender;
	}
	public void setMemGender(String memGender) {
		this.memGender = memGender;
	}
	public String getMemMarriageStatus() {
		return memMarriageStatus;
	}
	public void setMemMarriageStatus(String memMarriageStatus) {
		this.memMarriageStatus = memMarriageStatus;
	}
	public String getMemAddress() {
		return memAddress;
	}
	public void setMemAddress(String memAddress) {
		this.memAddress = memAddress;
	}
	public String getMemSpouseName() {
		return memSpouseName;
	}
	public void setMemSpouseName(String memSpouseName) {
		this.memSpouseName = memSpouseName;
	}
	public String getMemLivingWith() {
		return memLivingWith;
	}
	public void setMemLivingWith(String memLivingWith) {
		this.memLivingWith = memLivingWith;
	}
	public String getMemHomeNo() {
		return memHomeNo;
	}
	public void setMemHomeNo(String memHomeNo) {
		this.memHomeNo = memHomeNo;
	}
	public String getMemMobileNo() {
		return memMobileNo;
	}
	public void setMemMobileNo(String memMobileNo) {
		this.memMobileNo = memMobileNo;
	}
	public String getMemAcademic() {
		return memAcademic;
	}
	public void setMemAcademic(String memAcademic) {
		this.memAcademic = memAcademic;
	}
	public String getMemCurrentJob() {
		return memCurrentJob;
	}
	public void setMemCurrentJob(String memCurrentJob) {
		this.memCurrentJob = memCurrentJob;
	}
	public String getMemLastJob() {
		return memLastJob;
	}
	public void setMemLastJob(String memLastJob) {
		this.memLastJob = memLastJob;
	}
	public String getMemLastWorkplace() {
		return memLastWorkplace;
	}
	public void setMemLastWorkplace(String memLastWorkplace) {
		this.memLastWorkplace = memLastWorkplace;
	}
	
	public String getMemRace() {
		return memRace;
	}

	public void setMemRace(String memRace) {
		this.memRace = memRace;
	}

	public boolean checkMember(Member aM) {
		String checkMemberSQL = "SELECT MEMIC FROM MEMBER WHERE MEMIC='" + aM.getMemIC() +"'";
		return memberDA.checkMember(checkMemberSQL);
	}
	
	public boolean createMemberInfo(Active aM, String staffIC) {
		String name = aM.getMemName();
		String ic = aM.getMemIC();
		String gender = aM.getMemGender();
		String marriageStatus = aM.getMemMarriageStatus();
		String spouseName = aM.getMemSpouseName();
		String living = aM.getMemLivingWith();
		String address = aM.getMemAddress();
		String homeNo = aM.getMemHomeNo();
		String mobileNo = aM.getMemMobileNo();
		String academic = aM.getMemAcademic();
		String job = aM.getMemCurrentJob();
		String lastJob = aM.getMemLastJob();
		String lastWorkplace = aM.getMemLastWorkplace();
		String memRace = aM.getMemRace();
		java.sql.Date membershipDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		String insertTableSQL = "INSERT INTO MEMBER"
				+ "(MEMID, MEMIC, MEMNAME, MEMGENDER, MEMMARRIAGESTATUS, MEMADDRESS, MEMSPOUSENAME, MEMLIVINGWITH, MEMHOMENO, MEMMOBILENO, MEMACADEMIC, MEMJOB, MEMLASTJOB, MEMLASTWORKPLACE, MEMRACE, STAFFIC) " + "VALUES"
				+ "( SEQ_MEMID.nextval,'" + ic + "','" + name + "','" + gender + "','" + marriageStatus + "','" + address  + "','" + spouseName  + "','" + living  + "','" + homeNo  + "','" + mobileNo  + "','" + academic  + "','" + job  + "','" + lastJob  + "','" + lastWorkplace  + "','" + memRace  + "','" + staffIC + "')";
		String insertActive = "INSERT INTO ACTIVE"
				+ "(MEMBERSHIPNUM,MEMBERSHIPDATE, MEMID) " + "VALUES"
				+ "(CONCAT('PAWEM',SEQ_MEMID.currval), TO_DATE('" +membershipDate+ "','YYYY-MM-DD' ),SEQ_MEMID.currval)";
		System.out.println("Query: " + insertTableSQL);
		boolean mem = memberDA.createMemberInfo(insertTableSQL, insertActive);
		if(mem){
			return true;
		}else{
			return false;
		}
	}
	public static List<Object> viewMemberList(List<Object> holder) {
		List<Active> activeList = new ArrayList<Active>();
		activeList = Active.viewActiveList(activeList);
		
		List<Dropout> dropoutList = new ArrayList<Dropout>();
		dropoutList = Dropout.viewDropoutList(dropoutList);
		
		
		holder.add(0,activeList);
		if(dropoutList.size() > 0){
			holder.add(1,dropoutList);
		}
		
		return holder;
	}
	
	public static boolean updateMemberInfo(Dropout aD, Active aM, String staffIC, int memID) {
		String name = aM.getMemName();
		String ic = aM.getMemIC();
		String gender = aM.getMemGender();
		String marriageStatus = aM.getMemMarriageStatus();
		String spouseName = aM.getMemSpouseName();
		String living = aM.getMemLivingWith();
		String address = aM.getMemAddress();
		String homeNo = aM.getMemHomeNo();
		String mobileNo = aM.getMemMobileNo();
		String academic = aM.getMemAcademic();
		String job = aM.getMemCurrentJob();
		String lastJob = aM.getMemLastJob();
		String lastWorkplace = aM.getMemLastWorkplace();
		String memRace = aM.getMemRace();
		
		
		String updateTableSQL = "UPDATE MEMBER"
				+ " SET MEMIC = '" + ic + "', MEMNAME = '" + name + "', MEMGENDER = '" + gender + "', MEMMARRIAGESTATUS = '" + marriageStatus + "', MEMADDRESS = '" + address + "', MEMSPOUSENAME = '" + spouseName + "', MEMLIVINGWITH = '" + living + "', MEMHOMENO = '" + homeNo + "', MEMMOBILENO = '" + mobileNo + "', MEMACADEMIC = '" + academic + "', MEMJOB = '" + job + "', MEMLASTJOB = '" + lastJob + "', MEMLASTWORKPLACE = '" + lastWorkplace + "', MEMRACE = '" + memRace + "', STAFFIC = '" + staffIC 
				+ "' WHERE MEMID = " + memID + "";

		System.out.println("Query: " + updateTableSQL);
		boolean check = memberDA.updateMemberInfo(updateTableSQL);
		if(check){
				aD.updateDropoutInfo(aD);
			return true;
			
		}else{
			return false;
		}
	}
	public static boolean deleteMemberInfo(int memID) {
		String deleteMemQuery = "DELETE FROM MEMBER WHERE MEMID ='" + memID +"'";
		boolean drop = Dropout.deleteDropoutInfo(memID);
		if(drop){
			boolean active = Active.deleteActiveInfo(memID);
			if(active){
				return memberDA.deleteMemberInfo(deleteMemQuery);
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public static List<Object> viewMemberInfo(List<Object> holder,int memID) {
		Active activeList = null;
		activeList = Active.viewActiveInfo(activeList, memID);
		
		Dropout dropoutList = null;
		dropoutList = Dropout.viewDropoutInfo(dropoutList, memID);
		
		List<Event> eventList = new ArrayList<Event>();
		eventList = Event.viewEventAttendance(eventList, memID);
		
		holder.add(0,activeList);
		holder.add(1,dropoutList);
		holder.add(2, eventList);
		
		return holder;
	}

	public static List<Member> viewAttendanceInfo(List<Member> attendanceList, int eventID) {
			
			String attendanceQuery = "SELECT M.MEMID, M.MEMIC, M.MEMNAME FROM MEMBER M JOIN ATTENDANCE A ON (M.MEMID = A.MEMID) JOIN EVENT E ON (A.EVENTID = E.EVENTID)  WHERE E.EVENTID = " + eventID ;
			return memberDA.viewAttendanceInfo(attendanceList, attendanceQuery);
		}

	public static List<Member> getActiveMembers(List<Member> activeList) {
		String getQuery = "SELECT MEMID, MEMIC, MEMNAME FROM MEMBER WHERE MEMID NOT IN (SELECT MEMID FROM DROPOUT)";
		return memberDA.getActiveMembers(activeList, getQuery);
	}

	public static int calculateTotalActiveMember() {
		String calQuery = "SELECT COUNT(MEMID) AS NUM FROM MEMBER WHERE MEMID NOT IN (SELECT MEMID FROM DROPOUT)";
		return memberDA.calculateTotalActiveMember(calQuery);
	}

	public static List<Member> calculateMember(String category, int year, int month) {
		String sql = "";
		if(category.equals("awalBulan")){
			sql = "SELECT M.MEMID, M.MEMGENDER, M.MEMRACE FROM MEMBER M JOIN ACTIVE A ON (M.MEMID = A.MEMID) WHERE MEMBERSHIPDATE < TO_DATE('01-" + month +"-" + year + "','DD-MM-YYYY')";
			
		}else if(category.equals("bulanSemasa")){
			sql = "SELECT M.MEMID, M.MEMGENDER, M.MEMRACE FROM MEMBER M JOIN ACTIVE A ON (M.MEMID = A.MEMID) WHERE EXTRACT(MONTH FROM MEMBERSHIPDATE) = " + month +" AND  EXTRACT(YEAR FROM MEMBERSHIPDATE) = " + year;
			
		}else if(category.equals("awalBulanKehadiran")){
			sql = "SELECT DISTINCT M.MEMID, M.MEMGENDER, M.MEMRACE FROM MEMBER M JOIN ATTENDANCE A ON (M.MEMID = A.MEMID) JOIN EVENT E ON(A.EVENTID = E.EVENTID) WHERE E.EVENTDATE < TO_DATE('01-" + month +"-" + year + "','DD-MM-YYYY')";
			
		}else if(category.equals("bulanSemasaKehadiran")){
			sql = "SELECT DISTINCT M.MEMID, M.MEMGENDER, M.MEMRACE FROM MEMBER M JOIN ATTENDANCE A ON (M.MEMID = A.MEMID) JOIN EVENT E ON(A.EVENTID = E.EVENTID) WHERE EXTRACT(MONTH FROM E.EVENTDATE) = " + month +" AND  EXTRACT(YEAR FROM E.EVENTDATE) = " + year;
			
		}
		List<Member> list = new ArrayList<Member>();
		return memberDA.calculateMember(list,sql);
	}

	public static List<Member> calculateAttendance(int eventID) {
		String sql ="SELECT DISTINCT M.MEMID,M.MEMGENDER, M.MEMRACE FROM MEMBER M JOIN ATTENDANCE A ON (M.MEMID = A.MEMID) JOIN EVENT E ON(A.EVENTID = E.EVENTID) WHERE E.EVENTID =" + eventID;
		List<Member> list = new ArrayList<Member>();
		return memberDA.calculateAttendance(list,sql);
	}

	public static int getMemberID(String memIC) {
		String sql = "SELECT MEMID FROM MEMBER WHERE MEMIC='"+memIC+"'";
		return memberDA.getMemberID(sql);
	}
		
}
