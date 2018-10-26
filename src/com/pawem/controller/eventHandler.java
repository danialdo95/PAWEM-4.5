package com.pawem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pawem.model.Attendance;
import com.pawem.model.Event;
import com.pawem.model.Member;

@WebServlet("/eventHandler")
public class eventHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		if(action.equals("viewEventList")){
			try {
				List<Event> eventList = Event.viewEventList();
				if (eventList!=null) {
					System.out.println("Succeed getting list");
					request.setAttribute("list",eventList);
					request.getRequestDispatcher("viewEventList.jsp").forward(request,response);
				}
				else
					System.out.println("No data"); // error page
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}else if(action.equals("deleteEventInfo")){
			String eventID=request.getParameter("eventID");
			boolean success = Event.deleteEventInfo(eventID);
			if(success){
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Pembuangan aktiviti berjaya.');");
				out.print("window.location = '/PAWEM/event?action=viewEventList';");
				out.print("</script>");// logged-in page
			}else{
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Pembuangan aktiviti tidak berjaya. Sila cuba lagi.');");
				out.print("window.location = '/PAWEM/event?action=viewEventList';");
				out.print("</script>");// logged-in page
			}
		}else if(action.equals("viewEventInfo")){
			try {
				int eventID=Integer.parseInt(request.getParameter("eventID"));
				String type=request.getParameter("type");
				List<Object> eventList = new ArrayList<Object>();
				eventList = Event.viewEventInfo(eventList, eventID);
				System.out.println("lepas object");
				request.setAttribute("eventID",((Event) eventList.get(0)).getEventID());
				request.setAttribute("eventName",((Event) eventList.get(0)).getEventName());
				request.setAttribute("eventDate",((Event) eventList.get(0)).getEventDate());
				request.setAttribute("eventDescription",((Event) eventList.get(0)).getEventDescription());
				request.setAttribute("eventType",((Event) eventList.get(0)).getEventType());
				request.setAttribute("eventCost",((Event) eventList.get(0)).getEventCost());
				request.setAttribute("eventVenue",((Event) eventList.get(0)).getEventVenue());
				request.setAttribute("eventCommInvolve",((Event) eventList.get(0)).getEventCommInvolved());
				request.setAttribute("eventTime",((Event) eventList.get(0)).getEventTime());
				request.setAttribute("members", eventList.get(2));
				request.setAttribute("photoList", eventList.get(1));
				request.setAttribute("from", type);
				String oridate = ((Event) eventList.get(0)).getEventDate();
				DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
				Date date = formatter.parse(oridate);
				SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
				String updateDate = newFormat.format(date);
				request.setAttribute("updateDate", updateDate);
				if(type.equals("update")){
					request.getRequestDispatcher("updateEventForm.jsp").forward(request,response);
				}else if(type.equals("detail")|| type.equals("updateForm") ||type.equals("createForm")){
					request.getRequestDispatcher("viewEventInfo.jsp").forward(request,response);
				}else if(type.equals("attendance")){
					request.setAttribute("active",eventList.get(3));
					request.getRequestDispatcher("manageAttendance.jsp").forward(request,response);
				}				
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}else if(action.equals("home")){
			int totalAhli = (int) request.getAttribute("totalAhli");
			System.out.println(totalAhli);
			int totalAktiviti = Event.calculateTotalEvent();
			System.out.println(totalAktiviti);
			request.setAttribute("totalAhli", totalAhli);
			request.setAttribute("totalAktiviti", totalAktiviti);
			request.getRequestDispatcher("home.jsp").forward(request,response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("createEventInfo")){
			Event aE = new Event(0, null, null, null, null, 0, null, null, null, null);
			aE.setEventName(request.getParameter("eventName"));
			aE.setEventDate(request.getParameter("eventDate"));
			aE.setEventCommInvolved(request.getParameter("eventCommInvolved"));
			aE.setEventCost(Float.valueOf(request.getParameter("eventCost")));
			aE.setEventDescription(request.getParameter("eventDescription"));
			aE.setEventType(request.getParameter("eventType"));
			aE.setEventTime (request.getParameter("eventTime"));
			aE.setEventVenue(request.getParameter("eventVenue"));
			aE.setStaffIC((String) request.getSession().getAttribute("staffIC"));
			int ID = aE.createEventInfo(aE);
			if (ID != 0) {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Pendaftaran aktiviti berjaya.');");
				out.print("window.location = '/PAWEM/event?action=viewEventInfo&&type=createForm&&eventID="+ ID +"';");
				out.print("</script>");
			}
			else{
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Pendaftaran aktiviti tidak berjaya. Ahli sudah wujud di dalam sistem');");
				out.print("window.location = '/PAWEM/event?action=viewEventList';");
				out.print("</script>");
			}
		}else if(action.equals("attendance")){
			String members[] = request.getParameterValues("members");
			int eventID = Integer.parseInt(request.getParameter("eventID"));
			Attendance.deleteAllAttendanceInfo(eventID);
			if(members != null && members.length != 0){
				Attendance.deleteAllAttendanceInfo(eventID);
				Attendance att[] = new Attendance[members.length];
				List<Member> attList = new ArrayList<Member>();
				attList = Member.viewAttendanceInfo(attList, eventID);
				for(int i = 0; i < members.length; i++){
					int memID = Integer.parseInt(members[i]);
					att[i] = new Attendance(memID, eventID);
					boolean exist = false;
					for(int j = 0; j<attList.size(); j++ ){
						if(memID == attList.get(j).getMemID()){
							exist = true;
						}
					}
					if(!exist){
						Attendance.createAttendanceInfo(att[i]);
					}
				}
			}
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.print("<script type=\"text/javascript\">");
			out.print("alert('Penambahan laporan aktiviti berjaya.');");
			out.print("window.location = '/PAWEM/event?action=viewEventList';");
			out.print("</script>");
			
			
		}else if(action.equals("updateEventInfo")){
			Event aE = new Event(0, null, null, null, null, 0, null, null, null, null);
			aE.setEventID(Integer.parseInt(request.getParameter("eventID")));
			aE.setEventName(request.getParameter("eventName"));
			aE.setEventDate(request.getParameter("eventDate"));
			aE.setEventCommInvolved(request.getParameter("eventCommInvolved"));
			aE.setEventCost(Float.valueOf(request.getParameter("eventCost")));
			aE.setEventDescription(request.getParameter("eventDescription"));
			aE.setEventType(request.getParameter("eventType"));
			aE.setEventTime (request.getParameter("eventTime"));
			aE.setEventVenue(request.getParameter("eventVenue"));
			boolean update = aE.updateEventInfo(aE);
			
			
			if(update){
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Kemaskini aktiviti berjaya.');");
				out.print("window.location = '/PAWEM/event?action=viewEventInfo&&type=updateForm&&eventID="+ aE.getEventID() +"';");
				out.print("</script>");
			}else{
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Kemaskini aktiviti tidak berjaya.');");
				out.print("window.location = '/PAWEM/event?action=viewEventList';");
				out.print("</script>");
			}
		}
	}

}
