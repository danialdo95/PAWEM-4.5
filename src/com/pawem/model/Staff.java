package com.pawem.model;

import java.util.List;

import com.pawem.DA.staffDA;

public class Staff {
	private String staffIC;
	private String staffName;
	private String staffPwd;
	private String staffRole;
	private String managingStaffIC;
	
	public Staff(String staffIC, String staffName, String staffPwd, String staffRole, String managingStaffIC) {
		this.staffIC = staffIC;
		this.staffName = staffName;
		this.staffPwd = staffPwd;
		this.staffRole = staffRole;
		this.managingStaffIC = managingStaffIC;
	}
	public String getStaffIC() {
		return staffIC;
	}
	public void setStaffIC(String staffIC) {
		this.staffIC = staffIC;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffPwd() {
		return staffPwd;
	}
	public void setStaffPwd(String staffPwd) {
		this.staffPwd = staffPwd;
	}
	public String getStaffRole() {
		return staffRole;
	}
	public void setStaffRole(String staffRole) {
		this.staffRole = staffRole;
	}
	public String getManagingStaffIC() {
		return managingStaffIC;
	}
	public void setManagingStaffIC(String managingStaffIC) {
		this.managingStaffIC = managingStaffIC;
	}
	public boolean login(Staff aS){
		String searchQuery = "select * from STAFF where STAFFIC='" + aS.getStaffIC() + "' AND STAFFPWD='" + aS.getStaffPwd() + "'";
		System.out.println("Query: " + searchQuery);
		
		return staffDA.login(searchQuery, aS);
	}
	
	public static List<Staff> viewStaffList(){
		String listQuery = "SELECT * FROM STAFF";
		List<Staff> staffList = staffDA.viewStaffList(listQuery);
		return staffList;
	}
	public int checkStaff(Staff aS, String type){
		String checkNumberStaffSQL = "";
		if(type.equals("checkPengerusi")){
			checkNumberStaffSQL = "SELECT STAFFIC FROM STAFF WHERE STAFFROLE='Pengerusi'";
		}else{
			checkNumberStaffSQL = "SELECT STAFFIC FROM STAFF WHERE STAFFROLE='"+ aS.getStaffRole() +"'";
		}
		
		return staffDA.checkStaff(checkNumberStaffSQL);
	}
	public boolean createStaffAccount(Staff aS){
		String staffIC = aS.getStaffIC();
		String staffpwd = aS.getStaffPwd();
		String staffname = aS.getStaffName();
		String staffRole = aS.getStaffRole();
		
		String insertTableSQL = "INSERT INTO STAFF"
				+ "(STAFFIC, STAFFPWD, STAFFROLE, STAFFNAME, MANAGINGSTAFFIC) " + "VALUES"
				+ "('" + staffIC + "','" + staffpwd + "','" + staffRole + "','" + staffname + "','" + managingStaffIC + "')";

		System.out.println("Query: " + insertTableSQL);
		return staffDA.createStaffAccount(insertTableSQL);
		
	}
	
	public Staff viewStaffInfo(Staff aS){
		String selectQuery = "SELECT * FROM STAFF WHERE STAFFIC ='" + aS.getStaffIC() +"'";
		return staffDA.viewStaffInfo(aS,selectQuery);
	}
	public boolean updateStaffAccount(Staff aS, String currentIC) {
		String staffIC = aS.getStaffIC();
		String staffpwd = aS.getStaffPwd();
		String staffname = aS.getStaffName();
		String staffRole = aS.getStaffRole();
		String updateTableSQL = "UPDATE STAFF"
				+ " SET STAFFIC ='" + staffIC 
				+ "', STAFFPWD ='" + staffpwd
				+ "', STAFFROLE ='" + staffRole
				+ "', STAFFNAME ='" + staffname
				+ "' WHERE STAFFIC='" + currentIC +"'";
				 

		System.out.println("Query: " + updateTableSQL);
		return staffDA.updateStaffAccount(updateTableSQL);
	}
	public static boolean deleteStaffInfo(String staffIC, String staffRole) {
		String deleteQuery = "DELETE FROM STAFF WHERE STAFFIC ='" + staffIC +"'";
		if(staffRole.equals("Pengerusi")){
			System.out.println("Pengerusi takleh");
			return false;
		}else{
			return staffDA.deleteStaffInfo(deleteQuery);
		}
	}
}
