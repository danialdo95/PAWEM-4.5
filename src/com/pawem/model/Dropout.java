package com.pawem.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;








import com.pawem.DA.dropoutDA;

public class Dropout extends Member {

	private String dropoutDate;
	private String dropoutReason;
	public Dropout(int memID,String memIC, String memName, String memGender,
			String memMarriageStatus, String memAddress, String memSpouseName,
			String memLivingWith, String memHomeNo, String memMobileNo,
			String memAcademic, String memCurrentJob, String memLastJob,
			String memLastWorkplace,String memRace, String dropoutDate,String dropoutReason) {
		super(memID,memIC, memName, memGender, memMarriageStatus, memAddress, memSpouseName,
				memLivingWith, memHomeNo, memMobileNo, memAcademic, memCurrentJob,
				memLastJob, memLastWorkplace, memRace);
		this.dropoutDate = dropoutDate;
		this.dropoutReason = dropoutReason;
	}
	public String getDropoutDate() {
		return dropoutDate;
	}
	public void setDropoutDate(String dropoutDate) {
		this.dropoutDate = dropoutDate;
	}
	public String getDropoutReason() {
		return dropoutReason;
	}
	public void setDropoutReason(String dropoutReason) {
		this.dropoutReason = dropoutReason;
	}
	
	public boolean createDropoutInfo(Dropout aD) {
		java.sql.Date dropoutDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		String insertTableSQL = "INSERT INTO DROPOUT"
				+ "(DROPOUTREASON, DROPOUTDATE, MEMID) " + "VALUES"
				+ "('" + aD.getDropoutReason() + "', TO_DATE('" + dropoutDate+ "','YYYY-MM-DD' ),'" + aD.getMemID() + "')";

		System.out.println("Query: " + insertTableSQL);

		return dropoutDA.createDropoutInfo(insertTableSQL);
	}
	public static List<Dropout> viewDropoutList(List<Dropout> dropoutList) {
		String selectDROPOUTQuery = "SELECT D.MEMID"
				+ " FROM MEMBER M JOIN DROPOUT D "
				+ " ON (M.MEMID = D.MEMID)";
		
		return dropoutDA.viewDropoutList(dropoutList, selectDROPOUTQuery);
	}
	
	/*public Dropout viewMemberInfo(Dropout aD) {
		String viewQuery = "SELECT M.MEMIC, M.MEMNAME,MEMACADEMIC,MEMADDRESS,MEMJOB,MEMGENDER,MEMHOMENO,MEMMOBILENO,MEMLASTJOB,MEMLASTWORKPLACE,MEMLIVINGWITH,MEMMARRIAGESTATUS,MEMSPOUSENAME, TO_CHAR(D.DROPOUTDATE, 'DD MON YYYY') AS DROPOUTDATE, D.DROPOUTREASON"
				+ " FROM MEMBER M JOIN DROPOUT D "
				+ " ON (M.MEMIC = D.MEMIC)"
				+ " WHERE M.MEMIC = '"+ aD.getMemIC() +"'";
		aD = dropoutDA.viewMemberInfo(aD,viewQuery);
		return aD;
	}*/
	public static boolean checkYearDropout(int ID) {
		System.out.println(ID);
		String checkQuery = "SELECT MONTHS_BETWEEN(SYSDATE, DROPOUTDATE) AS GAP FROM DROPOUT WHERE MEMID ="+ ID +"";
		System.out.println(checkQuery);
		double gap = dropoutDA.checkYearDropout(checkQuery);
		System.out.println(gap);
		if(gap >=120){
			return true;
		}else{
			return false;
		}
	}
	public static boolean deleteDropoutInfo(int ID) {
		String deleteDropQuery = "DELETE FROM DROPOUT WHERE MEMID="+ ID +"";
		return dropoutDA.deleteDropoutInfo(deleteDropQuery);
	}
	public static Dropout viewDropoutInfo(Dropout dropoutList, int MEMID) {
	String selectDROPOUTQuery = "SELECT M.MEMID, M.MEMIC, M.MEMNAME,MEMACADEMIC,MEMADDRESS,MEMJOB,MEMGENDER,MEMHOMENO,MEMMOBILENO,MEMLASTJOB,MEMLASTWORKPLACE,MEMRACE,MEMLIVINGWITH,MEMMARRIAGESTATUS,MEMSPOUSENAME, TO_CHAR(D.DROPOUTDATE, 'DD MON YYYY') AS DROPOUTDATE, D.DROPOUTREASON"
				+ " FROM MEMBER M JOIN DROPOUT D "
				+ " ON (M.MEMID = D.MEMID)"
				+ " WHERE M.MEMID = " + MEMID +"";
	System.out.println(selectDROPOUTQuery);
		return dropoutDA.viewDropoutInfo(dropoutList, selectDROPOUTQuery);
	}
	public boolean updateDropoutInfo(Dropout aD) {
		String updateQuery = "UPDATE DROPOUT SET DROPOUTREASON ='"+ aD.getDropoutReason() +"' WHERE MEMID = "+ aD.getMemID() +"";
		
		return dropoutDA.updateDropoutInfo(updateQuery);
	}
	public static List<Dropout> calculateDropoutMember(String string, int year, int month) {
		String sql = "SELECT M.MEMID,M.MEMNAME, M.MEMIC, M.MEMGENDER, M.MEMRACE, D.DROPOUTREASON FROM MEMBER M JOIN DROPOUT D ON (M.MEMID = D.MEMID) WHERE EXTRACT(MONTH FROM DROPOUTDATE) = " + month +" AND  EXTRACT(YEAR FROM DROPOUTDATE) = " + year;		
		List<Dropout> list = new ArrayList<Dropout>();
		return dropoutDA.calculateDropoutMember(list, sql);
	}
}
