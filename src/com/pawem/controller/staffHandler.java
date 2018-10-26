package com.pawem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pawem.model.Staff;

/**
 * Servlet implementation class staffHandler
 */
@WebServlet("/staffHandler")
public class staffHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("logout")){
			System.out.println("inside logout");
			HttpSession session = request.getSession(true);
			session.setAttribute("staffIC", null);
			session.setAttribute("staffRole", null);
			session.setAttribute("staffName", null);
			session.invalidate();
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}else if(action.equals("viewStaffList")){
			try {
				List<Staff> staffList = Staff.viewStaffList();
				if (staffList!=null) {
					System.out.println("Succeed getting list");
					request.setAttribute("list",staffList);
					request.getRequestDispatcher("viewStaffList.jsp").forward(request,response);
				}
				else
					System.out.println("No data"); // error page
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}else if(action.equals("viewStaffInfo")){
			try {
				String staffIC=request.getParameter("IC");
				Staff aS = new Staff(staffIC, null, null, null, (String) request.getSession().getAttribute("staffIC"));
				aS=aS.viewStaffInfo(aS);
				if (aS.getStaffName() != null) {
					System.out.println("Succeed selecting");
					System.out.println(aS.getStaffName());
					request.setAttribute("staffName",aS.getStaffName());
					request.setAttribute("staffIC",aS.getStaffIC());
					request.setAttribute("staffPwd",aS.getStaffPwd());
					request.setAttribute("staffRole",aS.getStaffRole());
					request.getRequestDispatcher("updateStaffForm.jsp").forward(request,response);
				}
				else
					System.out.println("error insert"); // error page
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}else if(action.equals("deleteStaffInfo")){
			String staffIC=request.getParameter("IC");
			String staffRole=request.getParameter("role");
			Staff aS = new Staff(null, null, null, null, (String) request.getSession().getAttribute("staffIC"));
			aS.setStaffIC(request.getParameter("staffIC"));
			aS.setStaffRole(staffRole);
			int check = aS.checkStaff(aS,"checkPenyelaras");
			if(check > 1){
				boolean success = Staff.deleteStaffInfo(staffIC,staffRole);
				if(success){
					if(staffIC.equals(request.getSession().getAttribute("staffIC"))){
						PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.print("<script type=\"text/javascript\">");
						out.print("alert('Penghapusan staf berjaya.');");
						out.print("window.location = '/PAWEM/staff?action=logout';");
						out.print("</script>");// logged-in page
					}else{
						PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.print("<script type=\"text/javascript\">");
						out.print("alert('Penghapusan staf berjaya.');");
						out.print("window.location = '/PAWEM/staff?action=viewStaffList';");
						out.print("</script>");// logged-in page			
					}
					
				}else{
					PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.print("<script type=\"text/javascript\">");
					out.print("alert('Penghapusan pengerusi tidak berjaya. Maklumat pengerusi hanya boleh dikemaskini. Sila cuba lagi.');");
					out.print("window.location = '/PAWEM/staff?action=viewStaffList';");
					out.print("</script>");// logged-in page
				}
			}else{
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Penghapusan staf tidak berjaya. Sila cuba lagi.');");
				out.print("window.location = '/PAWEM/staff?action=viewStaffList';");
				out.print("</script>");// logged-in page
			}
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		if(action.equals("login")){
			try {
				Staff aS = new Staff(null, null, null, null, (String) request.getSession().getAttribute("staffIC"));
				aS.setStaffIC(request.getParameter("staffIC"));
				aS.setStaffPwd(request.getParameter("staffPwd"));
				boolean result = aS.login(aS);
				if (result) {
					System.out.println("inside servlet");
					HttpSession session = request.getSession(true);
					session.setAttribute("staffIC", aS.getStaffIC());
					session.setAttribute("staffRole", aS.getStaffRole());
					session.setAttribute("staffName", aS.getStaffName());
					response.sendRedirect("/PAWEM/member?action=home"); // logged-in page
				}
				else{
					PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.print("<script type=\"text/javascript\">");
					out.print("alert('Log masuk tidak berjaya. Sila cuba lagi.');");
					out.print("window.location = 'login.jsp';");
					out.print("</script>");
				}
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}else if(action.equals("createStaff")){
			try {
				Staff aS = new Staff(null, null, null, null, (String) request.getSession().getAttribute("staffIC"));
				aS.setStaffIC(request.getParameter("staffIC"));
				aS.setStaffPwd(request.getParameter("staffPwd"));
				aS.setStaffName(request.getParameter("staffName"));
				aS.setStaffRole(request.getParameter("staffRole"));
				if(aS.getStaffRole().equals("Penyelaras")){
					boolean result = aS.createStaffAccount(aS);
					if (result) {
						PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.print("<script type=\"text/javascript\">");
						out.print("alert('Pendaftaran staf berjaya.');");
						out.print("window.location = '/PAWEM/staff?action=viewStaffList';");
						out.print("</script>");// logged-in page
					}
					else{
						PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.print("<script type=\"text/javascript\">");
						out.print("alert('Pendaftaran staf tidak berjaya. Sila cuba lagi.');");
						out.print("window.location = '/PAWEM/staff?action=viewStaffList';");
						out.print("</script>");
					}
				}else{
					int check = aS.checkStaff(aS, "checkPengerusi");
					if(check > 0){
						PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.print("<script type=\"text/javascript\">");
						out.print("alert('Pendaftaran staf tidak berjaya. Hanya seorang sahaja boleh memegang"
								+ " jawatan ini. Sila buang dahulu dan cuba lagi.');");
						out.print("window.location = '/PAWEM/staff?action=viewStaffList';");
						out.print("</script>");
					}else{
						boolean result = aS.createStaffAccount(aS);
						if (result) {
							PrintWriter out = response.getWriter();
							response.setContentType("text/html");
							out.print("<script type=\"text/javascript\">");
							out.print("alert('Pendaftaran staf berjaya.');");
							out.print("window.location = '/PAWEM/staff?action=viewStaffList';");
							out.print("</script>");// logged-in page
						}
						else{
							PrintWriter out = response.getWriter();
							response.setContentType("text/html");
							out.print("<script type=\"text/javascript\">");
							out.print("alert('Pendaftaran staf tidak berjaya. Sila cuba lagi.');");
							out.print("window.location = '/PAWEM/staff?action=viewStaffList';");
							out.print("</script>");
					}
				}
				}
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}else if(action.equals("updateStaffAccount")){
			try {
				Staff aS = new Staff(null, null, null, null, (String) request.getSession().getAttribute("staffIC"));
				String staffIC = request.getParameter("staffIC");
				String staffName = request.getParameter("staffName");
				String staffPwd = request.getParameter("staffPwd");
				String staffRole = request.getParameter("staffRole");
				String currentIC = request.getParameter("currentIC");
				aS.setStaffIC(staffIC);
				aS.setStaffName(staffName);
				aS.setStaffPwd(staffPwd);
				aS.setStaffRole(staffRole);
				boolean result = aS.updateStaffAccount(aS,currentIC);
				
				if (result) {
					System.out.println("Succeed update");
					if(currentIC.equals(request.getSession().getAttribute("staffIC"))){
						response.sendRedirect("/PAWEM/staff?action=logout");
					}else{
						response.sendRedirect("/PAWEM/staff?action=viewStaffList");				
					}
					
				}
				else
					System.out.println("error insert"); // error page
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}
	}

}
