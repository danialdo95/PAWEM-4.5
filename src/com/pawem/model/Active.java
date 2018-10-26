package com.pawem.model;

import java.util.List;

import com.pawem.DA.activeDA;


public class Active extends Member {
	
	private String membershipNum;
	private String membershipDate;
	
	public Active(int memID, String memIC, String memName, String memGender,
			String memMarriageStatus, String memAddress, String memSpouseName,
			String memLivingWith, String memHomeNo, String memMobileNo,
			String memAcademic, String memCurrentJob, String memLastJob,
			String memLastWorkplace, String membershipNum, String membershipDate, String memRace) {
		super(memID, memIC, memName, memGender, memMarriageStatus, memAddress, memSpouseName,
				memLivingWith, memHomeNo, memMobileNo, memAcademic, memCurrentJob,
				memLastJob, memLastWorkplace, memRace);
		this.membershipNum = membershipNum;
		this.membershipDate = membershipDate;
	}

	public String getMembershipNum() {
		return membershipNum;
	}

	public void setMembershipNum(String membershipNum) {
		this.membershipNum = membershipNum;
	}

	public String getMembershipDate() {
		return membershipDate;
	}

	public void setMembershipDate(String membershipDate) {
		this.membershipDate = membershipDate;
	}


	

	
	public static boolean deleteActiveInfo(int ID) {
		String deleteActQuery = "DELETE FROM ACTIVE WHERE MEMID ='" + ID +"'";
		return activeDA.deleteActiveInfo(deleteActQuery);
	}

	public static List<Active> viewActiveList(List<Active> activeList) {
		String selectACTIVEQuery = "SELECT M.MEMID, M.MEMIC, M.MEMNAME, A.MEMBERSHIPNUM, TO_CHAR(A.MEMBERSHIPDATE, 'DD MON YYYY') AS MEMBERSHIPDATE"
				+ " FROM MEMBER M JOIN ACTIVE A "
				+ " ON (M.MEMID = A.MEMID) ORDER BY M.MEMID DESC";
		
		return activeDA.viewActiveList(activeList, selectACTIVEQuery);
	}

	public static Active viewActiveInfo(Active activeList, int ID) {
		String selectACTIVEQuery = "SELECT M.MEMID, M.MEMIC, M.MEMNAME,MEMACADEMIC,MEMADDRESS,MEMJOB,MEMGENDER,MEMHOMENO,MEMMOBILENO,MEMLASTJOB,MEMLASTWORKPLACE,MEMRACE,MEMLIVINGWITH,MEMMARRIAGESTATUS,MEMSPOUSENAME,MEMBERSHIPNUM, TO_CHAR(A.MEMBERSHIPDATE, 'DD MON YYYY') AS MEMBERSHIPDATE"
				+ " FROM MEMBER M JOIN ACTIVE A "
				+ " ON (M.MEMID = A.MEMID)"
				+ " WHERE M.MEMID = '" + ID +"'";
		System.out.println(selectACTIVEQuery);
		return activeDA.viewActiveInfo(activeList, selectACTIVEQuery);
	}



}