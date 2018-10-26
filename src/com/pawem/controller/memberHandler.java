package com.pawem.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




















import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.image.Image;


import com.pawem.model.Active;
import com.pawem.model.Attendance;
import com.pawem.model.Dropout;
import com.pawem.model.Event;
import com.pawem.model.Member;
import com.pawem.util.ConnectionManager;

/**
 * Servlet implementation class memberHandler
 */
@WebServlet("/memberHandler")
public class memberHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("viewMemberList")){
			try {
				List<Object> memberList = new ArrayList<Object>();
				memberList = Member.viewMemberList(memberList);
				request.setAttribute("activeList",memberList.get(0));
				if(memberList.size() > 1){
					request.setAttribute("dropoutList", memberList.get(1));
				}else{
					request.setAttribute("dropoutList", null);
				}
				
				request.getRequestDispatcher("viewMemberList.jsp").forward(request,response);
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}else if(action.equals("viewMemberInfo")){
			try {
				int memID=Integer.parseInt(request.getParameter("memID"));
				String type=request.getParameter("type");
				List<Object> memberList = new ArrayList<Object>();
				memberList = Member.viewMemberInfo(memberList, memID);
				request.setAttribute("memID",((Active) memberList.get(0)).getMemID());
				request.setAttribute("memName",((Active) memberList.get(0)).getMemName());
				request.setAttribute("memIC",((Active) memberList.get(0)).getMemIC());
				request.setAttribute("memGender",((Active) memberList.get(0)).getMemGender());
				request.setAttribute("memMarriageStatus",((Active) memberList.get(0)).getMemMarriageStatus());
				request.setAttribute("memAddress",((Active) memberList.get(0)).getMemAddress());
				request.setAttribute("memSpouseName",((Active) memberList.get(0)).getMemSpouseName());
				request.setAttribute("memLivingWith",((Active) memberList.get(0)).getMemLivingWith());
				request.setAttribute("memHomeNo",((Active) memberList.get(0)).getMemHomeNo());
				request.setAttribute("memMobileNo",((Active) memberList.get(0)).getMemMobileNo());
				request.setAttribute("memAcademic",((Active) memberList.get(0)).getMemAcademic());
				request.setAttribute("memCurrentJob",((Active) memberList.get(0)).getMemCurrentJob());
				request.setAttribute("memLastJob",((Active) memberList.get(0)).getMemLastJob());
				request.setAttribute("memLastWorkplace",((Active) memberList.get(0)).getMemLastWorkplace());
				request.setAttribute("membershipNum",((Active) memberList.get(0)).getMembershipNum());
				request.setAttribute("membershipDate",((Active) memberList.get(0)).getMembershipDate());
				request.setAttribute("memRace",((Active) memberList.get(0)).getMemRace());
				request.setAttribute("dropoutDate",((Dropout) memberList.get(1)).getDropoutDate());
				request.setAttribute("dropoutReason",((Dropout) memberList.get(1)).getDropoutReason());
				request.setAttribute("eventList", (memberList.get(2)));
			
				request.setAttribute("from", type);
				if(type.equals("update")){
					request.getRequestDispatcher("updateMemberForm.jsp").forward(request,response);
				}else{
					request.getRequestDispatcher("viewMemberInfo.jsp").forward(request,response);
				}				
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}else if(action.equals("deleteMemberInfo")){
			int memID=Integer.parseInt(request.getParameter("memID"));
			boolean checkYear = Dropout.checkYearDropout(memID);
			if (checkYear){
				boolean result = Member.deleteMemberInfo(memID);
				if(result){
					PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.print("<script type=\"text/javascript\">");
					out.print("alert('Pembuangan ahli berjaya.');");
					out.print("window.location = '/PAWEM/member?action=viewMemberList';");
					out.print("</script>");
				}
			}else{
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Pembuangan ahli tidak berjaya. Ahli masih diaktif bawah dari 10 tahun');");
				out.print("window.location = '/PAWEM/member?action=viewMemberList';");
				out.print("</script>");
			}
		}else if(action.equals("dropoutMember")){
			int memID=Integer.parseInt(request.getParameter("memID"));
			String dropoutReason = request.getParameter("dropoutReason");
			Dropout aD = new Dropout(memID, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
			aD.setDropoutReason(dropoutReason);
			boolean result = aD.createDropoutInfo(aD);
			if(result){
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Ahli telah diaktif.');");
				out.print("window.location = '/PAWEM/member?action=viewMemberList';");
				out.print("</script>");
			}else{
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Ahli tidak berjaya diaktif.');");
				out.print("window.location = '/PAWEM/member?action=viewMemberList';");
				out.print("</script>");
			}
		}else if(action.equals("home")){
			int totalAhli = Member.calculateTotalActiveMember();
			request.setAttribute("totalAhli", totalAhli);
			request.getRequestDispatcher("/event?action=home").forward(request,response);
		}else if(action.equals("monthlyReport")){
			String month = request.getParameter("month");
			String year = request.getParameter("year");
			String[] arrMonth = {"MONTH","JANUARI", "FEBRUARI", "MAC", "APRIL", "MEI", "JUN", "JULAI", "OGOS", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DECEMBER"};
			String monthName = arrMonth[Integer.parseInt(month)];
	        String fileName = "Laporan Bulanan ("+ monthName + " " + year  +").pdf"; // name of our file
	        int intYear = Integer.parseInt(year);
	        int intMonth = Integer.parseInt(month);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try{
				PDDocument doc = new PDDocument(); // creating instance of pdfDoc
				InputStream imageStream = getServletContext().getResourceAsStream("/IMAGES/logo.jpg");
		        BufferedImage buffImage = ImageIO.read(imageStream);
		        PDImageXObject ximage = JPEGFactory.createFromImage(doc, buffImage);    
				
		        PDPage page3 = new PDPage();
		        doc.addPage(page3);
		        PDPageContentStream content3 = new PDPageContentStream(doc, page3);
		      //Header - start
		        content3.drawImage(ximage, 70, 650,190, 85);
		        content3.beginText();
			      //Setting the leading
			        content3.setLeading(14.5f);
			        //Setting the position for the line
			        content3.newLineAtOffset(280, 715);
			        content3.setFont(PDType1Font.HELVETICA_BOLD, 10);
			        content3.showText("JABATAN KEBAJIKAN MASYARAKAT MALAYSIA");
		        content3.endText();
		        content3.beginText();
			        //Setting the position for the line
			        content3.newLineAtOffset(280, 703);
			        content3.setFont(PDType1Font.HELVETICA, 10);
			        content3.showText("BAHAGIAN WARGA EMAS");
			    content3.endText();
		        
			    content3.beginText();
			        //Setting the position for the line
			        content3.newLineAtOffset(280, 691);
			        content3.setFont(PDType1Font.HELVETICA, 10);
			        content3.showText("ARAS 11, NO. 55, PERSIARAN PERDANA");
			    content3.endText();
			    
			    content3.beginText();
			        //Setting the position for the line
			        content3.newLineAtOffset(280, 679);
			        content3.setFont(PDType1Font.HELVETICA, 10);
			        content3.showText("PRESINT 4, 62100 PUTRAJAYA");
			    content3.endText();
			    
			    
			    content3.beginText();
			        //Setting the position for the line
			        content3.newLineAtOffset(280, 667);
			        content3.setFont(PDType1Font.HELVETICA, 10);
			        content3.showText("NO. TEL: 03-8000 8000 / 03-83232294");
			    content3.endText();
			    
			    content3.beginText();
			        //Setting the position for the line
			        content3.newLineAtOffset(280, 655);
			        content3.setFont(PDType1Font.HELVETICA, 10);
			        content3.showText("NO. FAX: 03-8323 2056");
			    content3.endText();
			    content3.setStrokingColor(Color.BLACK);
			    content3.addRect(270, 650, 310, 80);
			    content3.closeAndStroke();
			    //Header - end

			  //Start content
			    float margin3 = 30;
			    float yStartNewPage3 = page3.getMediaBox().getHeight() - (2 * margin3);

				// Initialize table
				float tableWidth3 = 550;
				boolean drawContent3 = true;
				boolean drawLine3 = false;
				float yStart3 = 630;
				float bottomMargin3 = 70;
				BaseTable table3 = new BaseTable(yStart3, yStartNewPage3, bottomMargin3, tableWidth3, margin3, doc, page3, drawLine3,
						drawContent3);
							// Create Fact header row
							Row<PDPage> row3 = table3.createRow(10f);
							Cell<PDPage> cell3 = row3.createCell(100 , "LAPORAN BULANAN PUSAT AKTIVITI WARGA EMAS (PAWE)");
							cell3.setFont(PDType1Font.HELVETICA_BOLD);
							cell3.setFontSize(10);
							cell3.setFillColor(Color.LIGHT_GRAY);
							cell3.setAlign(HorizontalAlignment.CENTER);
							
							
							row3 = table3.createRow(15f);
							cell3 = row3.createCell(10, "BULAN : ");
							cell3.setFont(PDType1Font.HELVETICA_BOLD);
							cell3.setFontSize(10);
							cell3 = row3.createCell(40, monthName);
							cell3.setFont(PDType1Font.HELVETICA);
							cell3.setFontSize(10);
							
							cell3 = row3.createCell(10, "TAHUN : ");
							cell3.setFont(PDType1Font.HELVETICA_BOLD);
							cell3.setFontSize(10);
							cell3 = row3.createCell(40, year);
							cell3.setFont(PDType1Font.HELVETICA);
							cell3.setFontSize(10);

							row3 = table3.createRow(15f);
							cell3 = row3.createCell(10, "PAWE : ");
							cell3.setFont(PDType1Font.HELVETICA_BOLD);
							cell3.setFontSize(10);
							cell3 = row3.createCell(90, "KM4, PT. KEROMA, JALAN ABDUL RAHMAN, 84000 MUAR, JOHOR");
							cell3.setFont(PDType1Font.HELVETICA);
							cell3.setFontSize(10);
							
							row3 = table3.createRow(15f);
							cell3 = row3.createCell((float)10.5, "NO. TEL : ");
							cell3.setFont(PDType1Font.HELVETICA_BOLD);
							cell3.setFontSize(10);
							cell3 = row3.createCell((float)39.5, "06 - 9538131");
							cell3.setFont(PDType1Font.HELVETICA);
							cell3.setFontSize(10);
							
							cell3 = row3.createCell((float)10.5, "NO. FAX : ");
							cell3.setFont(PDType1Font.HELVETICA_BOLD);
							cell3.setFontSize(10);
							cell3 = row3.createCell((float)39.5, "06 - 9538131");
							cell3.setFont(PDType1Font.HELVETICA);
							cell3.setFontSize(10);
							
							table3.draw();
							
							//horizontal line
						    content3.addRect(30, 530, 550, (float)0.5);
						    content3.fill();
							
						    //Initialize table 4
						    float tableWidth4 = 550;
							boolean drawContent4 = true;
							boolean drawLine4 = true;
							float yStart4 = 520;
							float bottomMargin4 = 70;
						    BaseTable table4 = new BaseTable(yStart4, yStartNewPage3, bottomMargin4, tableWidth4, margin3, doc, page3, drawLine4, drawContent4);
								// Create Fact header row
								Row<PDPage> row4 = table4.createRow(15f);
								Cell<PDPage> cell4 = row4.createCell(100 , "1. MAKLUMAT JUMLAH AHLI YANG BERDAFTAR");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setFillColor(Color.LIGHT_GRAY);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								
								//HEADING START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , "KATEGORI");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "JANTINA/ KAUM");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								//MELAYU
								cell4 = row4.createCell((float)8.75 , "M");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								cell4 = row4.createCell((float)8.75 , "C");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , "I");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "PSM");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "PSB");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "PSW");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , "LL");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , "J");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//HEADING END HERE
								
								
								//DATA 1.A.L START HERE
								List<Member> listAwal = Member.calculateMember("awalBulan", intYear, intMonth);
								int MM=0, MC = 0, MI = 0, ML = 0, PM=0, PC = 0, PI = 0, PL = 0;  
								for(int i =0; i<listAwal.size();i++){
									if(listAwal.get(i).getMemGender().equals("Lelaki")){
										if(listAwal.get(i).getMemRace().equals("Melayu")){
											MM++;
										}else if(listAwal.get(i).getMemRace().equals("Cina")){
											MC++;
										}else if(listAwal.get(i).getMemRace().equals("India")){
											MI++;
										}else if(listAwal.get(i).getMemRace().equals("Lain-Lain")){
											ML++;
										}
									}else if(listAwal.get(i).getMemGender().equals("Perempuan")){
										if(listAwal.get(i).getMemRace().equals("Melayu")){
											PM++;
										}else if(listAwal.get(i).getMemRace().equals("Cina")){
											PC++;
										}else if(listAwal.get(i).getMemRace().equals("India")){
											PI++;
										}else if(listAwal.get(i).getMemRace().equals("Lain-Lain")){
											PL++;
										}
									}
								}
								List<Member> listSemasa = Member.calculateMember("bulanSemasa", intYear, intMonth);
								int MM1=0, MC1 = 0, MI1 = 0, ML1 = 0, PM1=0, PC1 = 0, PI1 = 0, PL1 = 0;  
								for(int i =0; i<listSemasa.size();i++){
									if(listSemasa.get(i).getMemGender().equals("Lelaki")){
										if(listSemasa.get(i).getMemRace().equals("Melayu")){
											MM1++;
										}else if(listSemasa.get(i).getMemRace().equals("Cina")){
											MC1++;
										}else if(listSemasa.get(i).getMemRace().equals("India")){
											MI1++;
										}else if(listSemasa.get(i).getMemRace().equals("Lain-Lain")){
											ML1++;
										}
									}else if(listSemasa.get(i).getMemGender().equals("Perempuan")){
										if(listSemasa.get(i).getMemRace().equals("Melayu")){
											PM1++;
										}else if(listSemasa.get(i).getMemRace().equals("Cina")){
											PC1++;
										}else if(listSemasa.get(i).getMemRace().equals("India")){
											PI1++;
										}else if(listSemasa.get(i).getMemRace().equals("Lain-Lain")){
											PL1++;
										}
									}
								}
								List<Dropout> listDrop = Dropout.calculateDropoutMember("dropout", intYear, intMonth);
								int MM2=0, MC2 = 0, MI2 = 0, ML2 = 0, PM2=0, PC2 = 0, PI2 = 0, PL2 = 0;  
								String dropReason = "<ul>";
								for(int i =0; i<listDrop.size();i++){
									if(listDrop.get(i).getMemGender().equals("Lelaki")){
										if(listDrop.get(i).getMemRace().equals("Melayu")){
											MM2++;
										}else if(listDrop.get(i).getMemRace().equals("Cina")){
											MC2++;
										}else if(listDrop.get(i).getMemRace().equals("India")){
											MI2++;
										}else if(listDrop.get(i).getMemRace().equals("Lain-Lain")){
											ML2++;
										}
									}else if(listDrop.get(i).getMemGender().equals("Perempuan")){
										if(listDrop.get(i).getMemRace().equals("Melayu")){
											PM2++;
										}else if(listDrop.get(i).getMemRace().equals("Cina")){
											PC2++;
										}else if(listDrop.get(i).getMemRace().equals("India")){
											PI2++;
										}else if(listDrop.get(i).getMemRace().equals("Lain-Lain")){
											PL2++;
										}
									}
									dropReason += "<li>" + listDrop.get(i).getMemName() + " (" + listDrop.get(i).getMemIC() + ") - " + listDrop.get(i).getDropoutReason() + "</li>";
									
								}
								dropReason += "</ul>";
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "LELAKI");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);							
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString(MM));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(MC));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(MI));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								int MPSM = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSM));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								int MPSB = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSB));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								int MPSW = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSW));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(ML));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int AMjumlah = MM + MC + MI + ML + MPSM + MPSB + MPSW;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AMjumlah));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);							
								//DATA 1.A.L END HERE
								
								//DATA 1.A.P START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " A) BIL AHLI PADA AWAL BULAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								cell4 = row4.createCell(15 , "PEREMPUAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//MELAYU
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(PM));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//CINA
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(PC));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(PI));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//PAWE SEMENANJUNG MALAYSIA
								int PPSM = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSM));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								int PPSB = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSB));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								int PPSW = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSW));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(PL));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int APjumlah = PM + PC + PI + PL + PPSM + PPSB + PPSW;
								cell4 = row4.createCell((float)8.75 , Integer.toString(APjumlah));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.C.P END HERE
								
								//DATA 1.A.J START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "JUMLAH");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								int AM = MM + PM ;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AM));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA	
								int AC = MC + PC ;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AC));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								int AI = MI + PI ;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AI));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , Integer.toString(0));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , Integer.toString(0));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , Integer.toString(0));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								int AL = ML + PL ;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AL));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int allA =  AM + AC + AI + AL;
								cell4 = row4.createCell((float)8.75 , Integer.toString(allA));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.A.J END HERE
								//DATA 1.A.L START HERE
								
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "LELAKI");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);							
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(MM1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(MC1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(MI1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								int MPSM1 = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSM1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								int MPSB1 = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSB1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								int MPSW1 = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSW1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(ML1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int AMjumlah1 = MM1 + MC1 + MI1 + ML1 + MPSM1 + MPSB1 + MPSW1;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AMjumlah1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);							
								//DATA 1.A.L END HERE
								
								//DATA 1.A.P START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " B) BIL AHLI PADA BULAN SEMASA");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								cell4 = row4.createCell(15 , "PEREMPUAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//MELAYU
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(PM1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//CINA
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(PC1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								
								cell4 = row4.createCell((float)8.75 , Integer.toString(PI1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//PAWE SEMENANJUNG MALAYSIA
								int PPSM1 = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSM1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								int PPSB1 = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSB1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								int PPSW1 = 0;
								cell4 = row4.createCell((float)8.75 , Integer.toString(MPSW1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString(PL1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int APjumlah1 = PM1 + PC1 + PI1 + PL1 + PPSM1 + PPSB1 + PPSW1;
								cell4 = row4.createCell((float)8.75 , Integer.toString(APjumlah1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.C.P END HERE
								
								//DATA 1.A.J START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "JUMLAH");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								int AM1 = MM1 + PM1 ;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AM1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA	
								int AC1 = MC1 + PC1 ;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AC1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								int AI1 = MI1 + PI1 ;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AI1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , Integer.toString(0));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , Integer.toString(0));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , Integer.toString(0));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								int AL1 = ML1 + PL1 ;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AL1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int allA1 =  AM1 + AC1 + AI1 + AL1;
								cell4 = row4.createCell((float)8.75 , Integer.toString(allA1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.A.J END HERE	
								//DATA 1.C.L START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "LELAKI");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString(MM2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString(MC2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString(MI2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString(ML2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int AMJumlah2 = MM2 + MC2 + MI2 + ML2;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AMJumlah2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);							
								//DATA 1.C.L END HERE
								
								//DATA 1.C.P START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " C) BIL AHLI 'DROP-OUT' PADA BULAN SEMASA");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								cell4 = row4.createCell(15 , "PEREMPUAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString(PM2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString(PC2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString(PI2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString(PL2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int APJumlah2 = PM2 + PC2 + PI2 + PL2;
								cell4 = row4.createCell((float)8.75 , Integer.toString(APJumlah2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.C.P END HERE
								
								//DATA 1.C.J START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setTopBorderStyle(null);
								cell4.setBottomBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "JUMLAH");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								int AM2 = MM2 + PM2;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AM2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA	
								int AC2 = MC2 + PC2;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AC2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								int AI2 = MI2 + PI2;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AI2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								int AL2 = ML2 + PL2;
								cell4 = row4.createCell((float)8.75 , Integer.toString(AL2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int allA2 = AM2 + AC2 + AI2 + AL2;
								cell4 = row4.createCell((float)8.75 , Integer.toString(allA2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.C.J END HERE
								
								
								//DATA 1.C.ALASAN START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "NYATAKAN ALASAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//ALASAN
								
								cell4 = row4.createCell(70 , dropReason);
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//DATA 1.C.ALASAN END HERE
								
								//DATA 1.C.L START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "LELAKI");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString((MM+MM1)-MM2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString((MC+MC1)-MC2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString((MI+MI1)-MI2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString((ML+ML1)-ML2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int totL = ((MM+MM1)-MM2)+ (((MC+MC1)-MC2)) + (((MI+MI1)-MI2)) + (((ML+ML1)-ML2));
								cell4 = row4.createCell((float)8.75 , Integer.toString(totL));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);							
								//DATA 1.D.L END HERE
								
								//DATA 1.D.P START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " D) BIL AHLI PADA AKHIR BULAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								cell4 = row4.createCell(15 , "PEREMPUAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString((PM + PM1)-PM2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString((PC + PC1)-PC2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString((PI + PI1)-PI2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString((PL + PL1)-PL2));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								int totP = ((PM+PM1)-PM2) + ((PC+PC1)-PC2) + ((PI+PI1)-PI2) + ((PL+PL1)-PL2);
								cell4 = row4.createCell((float)8.75 , Integer.toString( totP ));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);	
								//DATA 1.D.P END HERE
								
								//DATA 1.D.J START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "JUMLAH");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString(((AM+AM1) - AM2)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA	
								cell4 = row4.createCell((float)8.75 , Integer.toString(((AC+AC1) - AC2)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString(((AI+AI1) - AI2)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString(((AL+AL1) - AL2)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString(totP + totL));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.D.J END HERE
								
								
								
								//2 STARTS
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(100 , "2. MAKLUMAT JUMLAH KEHADIRAN AHLI KE PAWE:");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setFillColor(Color.LIGHT_GRAY);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								
								//HEADING START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , "KATEGORI");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "JANTINA/ KAUM");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								//MELAYU
								cell4 = row4.createCell((float)8.75 , "M");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								cell4 = row4.createCell((float)8.75 , "C");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , "I");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "PSM");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "PSB");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "PSW");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , "LL");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , "J");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//HEADING END HERE
								
								
								List<Member> listKAwal = Member.calculateMember("awalBulanKehadiran", intYear, intMonth);
								int KMM=0, KMC = 0, KMI = 0, KML = 0, KPM=0, KPC = 0, KPI = 0, KPL = 0;  
								for(int i =0; i<listKAwal.size();i++){
									if(listKAwal.get(i).getMemGender().equals("Lelaki")){
										if(listKAwal.get(i).getMemRace().equals("Melayu")){
											KMM++;
										}else if(listKAwal.get(i).getMemRace().equals("Cina")){
											KMC++;
										}else if(listKAwal.get(i).getMemRace().equals("India")){
											KMI++;
										}else if(listKAwal.get(i).getMemRace().equals("Lain-Lain")){
											KML++;
										}
									}else if(listKAwal.get(i).getMemGender().equals("Perempuan")){
										if(listKAwal.get(i).getMemRace().equals("Melayu")){
											KPM++;
										}else if(listKAwal.get(i).getMemRace().equals("Cina")){
											KPC++;
										}else if(listKAwal.get(i).getMemRace().equals("India")){
											KPI++;
										}else if(listKAwal.get(i).getMemRace().equals("Lain-Lain")){
											KPL++;
										}
									}
								}
								List<Member> listKSemasa = Member.calculateMember("bulanSemasaKehadiran", intYear, intMonth);
								int KMM1=0, KMC1 = 0, KMI1 = 0, KML1 = 0, KPM1=0, KPC1 = 0, KPI1 = 0, KPL1 = 0;  
								for(int i =0; i<listKSemasa.size();i++){
									if(listKSemasa.get(i).getMemGender().equals("Lelaki")){
										if(listKSemasa.get(i).getMemRace().equals("Melayu")){
											KMM1++;
										}else if(listKSemasa.get(i).getMemRace().equals("Cina")){
											KMC1++;
										}else if(listKSemasa.get(i).getMemRace().equals("India")){
											KMI1++;
										}else if(listKSemasa.get(i).getMemRace().equals("Lain-Lain")){
											KML1++;
										}
									}else if(listKSemasa.get(i).getMemGender().equals("Perempuan")){
										if(listKSemasa.get(i).getMemRace().equals("Melayu")){
											KPM1++;
										}else if(listKSemasa.get(i).getMemRace().equals("Cina")){
											KPC1++;
										}else if(listKSemasa.get(i).getMemRace().equals("India")){
											KPI1++;
										}else if(listKSemasa.get(i).getMemRace().equals("Lain-Lain")){
											KPL1++;
										}
									}
								}
								
								//DATA 1.A.L START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "LELAKI");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString(KMM));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString(KMC));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString(KMI));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString(KML));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString((KMM+KMC+KMI+KML)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);							
								//DATA 1.A.L END HERE
								
								//DATA 1.A.P START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " A) BIL KEHADIRAN PADA AWAL BULAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								cell4 = row4.createCell(15 , "PEREMPUAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPM));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPC));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPI));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPL));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPM+KPC+KPI+KPL)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.C.P END HERE
								
								//DATA 1.A.J START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "JUMLAH");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								int KPMM = KMM + KPM;
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPMM));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA	
								int KPMC = KMC + KPC;
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPMC));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								int KPMI = KMI + KPI;
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPMI));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								int KPML = KML + KPL;
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPML));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPMM+KPMC+KPMI+KPML)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.B.J END HERE
								
								//DATA 1.B.L START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "LELAKI");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString(KMM1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString(KMC1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString(KMI1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString(KML1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString((KMM1+KMC1+KMI1+KML1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.C.P END HERE
								//DATA 1.B.P START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " B) BIL KEHADIRAN BULAN SEMASA");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								cell4 = row4.createCell(15 , "PEREMPUAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPM1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPC1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPI1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPL1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPM1+KPC1+KPI1+KPL1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.C.P END HERE
								
								//DATA 1.B.J START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "JUMLAH");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								int KPMM1 = KMM1 + KPM1;
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPMM1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA	
								int KPMC1 = KMC1 + KPC1;
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPMC1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								int KPMI1 = KMI1 + KPI1;
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPMI1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								int KPML1 = KML1 + KPL1;
								cell4 = row4.createCell((float)8.75 , Integer.toString(KPML1));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPMM1+KPMC1+KPMI1+KPML1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.B.J END HERE
								
								//DATA 1.D.L START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "LELAKI");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString((KMM+KMM1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString((KMC+KMC1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString((KMI+KMI1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString((KML+KML1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString((KMM+KMM1) + (KMC+KMC1) + (KMI+KMI1) +(KML+KML1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);							
								//DATA 1.D.L END HERE
								
								//DATA 1.D.P START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " JUMLAH KEHADIRAN PADA AKHIR BULAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setBottomBorderStyle(null);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								cell4 = row4.createCell(15 , "PEREMPUAN");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPM+KPM1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPC+KPC1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPI+KPI1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPL+KPL1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPM+KPM1) + (KPC+KPC1) + (KPI+KPI1) +(KPL+KPL1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);							
								//DATA 1.D.L END HERE
								
								//DATA 1.D.J START HERE
								row4 = table4.createRow(15f);
								cell4 = row4.createCell(15 , " ");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setTopBorderStyle(null);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								cell4 = row4.createCell(15 , "JUMLAH");
								cell4.setFont(PDType1Font.HELVETICA_BOLD);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//MELAYU
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPMM + KPMM1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//CINA	
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPMC + KPMC1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//INDIA
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPMI + KPMI1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);	
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SEMENANJUNG MALAYSIA
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SABAH
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//PAWE SARAWAK
								cell4 = row4.createCell((float)8.75 , "0");
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//LAIN-LAIN
								cell4 = row4.createCell((float)8.75 , Integer.toString((KPML + KPML1)));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//JUMLAH
								cell4 = row4.createCell((float)8.75 , Integer.toString(((KPMM + KPMM1) + (KPMC + KPMC1) + (KPMI + KPMI1) + (KPML + KPML1))));
								cell4.setFont(PDType1Font.HELVETICA);
								cell4.setFontSize(10);
								cell4.setAlign(HorizontalAlignment.CENTER);
								cell4.setValign(VerticalAlignment.MIDDLE);
								//DATA 1.D.J END HERE
							table4.draw();
				content3.close();
		        
				PDPage pageAct = new PDPage();
		        doc.addPage(pageAct);
		        PDPageContentStream act = new PDPageContentStream(doc, pageAct);
		      //Start content
			    float marginAct = 30;
			    float yStartNewPageAct = pageAct.getMediaBox().getHeight() - (2 * marginAct);

				// Initialize table
				float tableWidthAct = 550;
				boolean drawContentAct = true;
				boolean drawLineAct = true;
				float yStartAct = 703;
				float bottomMarginAct = 70;
				BaseTable tableAct = new BaseTable(yStartAct, yStartNewPageAct, bottomMarginAct, tableWidthAct, marginAct, doc, pageAct, drawLineAct, drawContentAct);
							// Create Fact header row
							Row<PDPage> row = tableAct.createRow(10f);
							Cell<PDPage> cell = row.createCell(100 , "PROGRAM DAN AKTIVITI YANG DIJALANKAN DALAM BULAN SEMASA:");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setTopBorderStyle(null);
							cell.setRightBorderStyle(null);
							cell.setLeftBorderStyle(null);
							cell.setAlign(HorizontalAlignment.CENTER);
							//////////////////////////////////////////////////////////////////
							row = tableAct.createRow(15f);
							cell = row.createCell(100 , "A: PROGRAM PEMBELAJARAN SEPANJANG HAYAT <br/>" 
									+ "(cth: kelas mengaji/ kelas tarian/ kelas masakan dll)");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setFillColor(Color.LIGHT_GRAY);
							cell.setAlign(HorizontalAlignment.CENTER);	
							cell.setValign(VerticalAlignment.MIDDLE);
							////////////////////////////////////////////////////////////////////////
							row = tableAct.createRow(10f);
							cell = row.createCell(6 , "BIL");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(23 , "NAMA AKTIVITI/ KELAS");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(15 , "TARIKH");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(14 , "SESI");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(22 , "TEMPAT AKTIVITI");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(9 , "KOS (RM)");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(11 , "BIL<br>PESERTA<br>TERLIBAT");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							////////////////////////////////////////////////////////////////////////
							List<Event> listA = Event.getEventList("A", intMonth, intYear);
							if(listA.size()>0){
								for(int i=0; i < listA.size(); i++){
									row = tableAct.createRow(10f);
									cell = row.createCell(6 , Integer.toString((i+1)));
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(23 , listA.get(i).getEventName());
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(15 , listA.get(i).getEventDate());
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(14 , listA.get(i).getEventTime());
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(22 , listA.get(i).getEventVenue());
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(9 , Float.toString(listA.get(i).getEventCost()));
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									int count = Attendance.calculateAttendance(listA.get(i).getEventID());
									cell = row.createCell(11 , Integer.toString(count));
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
								
								}
							}else{
								row = tableAct.createRow(10f);
								cell = row.createCell(6 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(23 , "TIADA");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(15 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(14 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(22 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(9 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(11 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
							}					
							//////////////////////////////////////////////////////////////////
							row = tableAct.createRow(10f);
							cell = row.createCell(100 , "B: PROGRAM PEMERKASAAN EKONOMI <br/>" 
							+ "(cth: penghasilan produk seperti bunga telur, kerepek, gubahan bunga dll)");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setFillColor(Color.LIGHT_GRAY);
							cell.setAlign(HorizontalAlignment.CENTER);
							////////////////////////////////////////////////////////////////////////
							row = tableAct.createRow(10f);
							cell = row.createCell(6 , "BIL");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(23 , "NAMA PRODUK");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(15 , "TARIKH");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(14 , "SESI");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(22 , "TEMPAT AKTIVITI");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(9 , "KOS (RM)");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							cell = row.createCell(11 , "BIL<br>PESERTA<br>TERLIBAT");
							cell.setFont(PDType1Font.HELVETICA_BOLD);
							cell.setFontSize(10);
							cell.setAlign(HorizontalAlignment.CENTER);
							cell.setValign(VerticalAlignment.MIDDLE);
							
							////////////////////////////////////////////////////////////////////////
							List<Event> listB = Event.getEventList("B", intMonth, intYear);
							if(listB.size()>0){
								for(int i=0; i < listB.size(); i++){
									row = tableAct.createRow(10f);
									cell = row.createCell(6 , Integer.toString((i+1)));
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(23 , listB.get(i).getEventName());
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(15 , listB.get(i).getEventDate());
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(14 , listB.get(i).getEventTime());
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(22 , listB.get(i).getEventVenue());
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									cell = row.createCell(9 , Float.toString(listB.get(i).getEventCost()));
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
									
									int count = Attendance.calculateAttendance(listB.get(i).getEventID());
									cell = row.createCell(11 , Integer.toString(count));
									cell.setFont(PDType1Font.HELVETICA);
									cell.setFontSize(10);
									cell.setAlign(HorizontalAlignment.CENTER);
									cell.setValign(VerticalAlignment.MIDDLE);
								
								}
							}else{
								row = tableAct.createRow(10f);
								cell = row.createCell(6 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(23 , "TIADA");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(15 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(14 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(22 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(9 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
								
								cell = row.createCell(11 , "");
								cell.setFont(PDType1Font.HELVETICA_BOLD);
								cell.setFontSize(10);
								cell.setAlign(HorizontalAlignment.CENTER);
								cell.setValign(VerticalAlignment.MIDDLE);
							}			
							
							row=tableAct.createRow(100f);
							cell = row.createCell(100 , "");
							cell.setBorderStyle(null);
							row=tableAct.createRow(100f);
							cell = row.createCell(100 , "");
							cell.setBorderStyle(null);
							DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
							Date today = new Date();
							
							row = tableAct.createRow(40f);
							cell = row.createCell( 50, "Disediakan Oleh:<br>Tandatangan:     ..............................<br>Nama: ABDUL RAHMAN ISMAIL<br>No. K/P: 480428-01-5437<br>Jawatan: Setiausaha<br>Tarikh: "+ date.format(today));
							cell.setFont(PDType1Font.HELVETICA);
							cell.setFontSize(10);
							cell.setLineSpacing((float)1.6);
							cell.setBorderStyle(null);
							
							cell = row.createCell( 50, "Disahkan Oleh:<br>Tandatangan:     ..............................<br>Nama: HJ. JAAFAR MOHD TAP<br>No. K/P: 390704-01-5363<br>Jawatan: Pengerusi<br>Tarikh: "+ date.format(today));
							cell.setFont(PDType1Font.HELVETICA);
							cell.setFontSize(10);
							cell.setLineSpacing((float)1.6);
							cell.setBorderStyle(null);
							
				tableAct.draw();
		        act.close();
		        
		        /////////////////////////////////////////////////////////////////////
		        
		        
		        YearMonth yearMonthObject = YearMonth.of(intYear, intMonth);
		        int totalDays = yearMonthObject.lengthOfMonth();
		        
		        
		    
		        PDPage page = new PDPage();
		        doc.addPage(page);
		        PDPageContentStream c = new PDPageContentStream(doc, page);
		      //Start content
			    float margin = 30;
			    float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

				// Initialize table
				float tableWidth = 550;
				boolean drawContent = true;
				boolean drawLine = true;
				float yStart= 703;
				float bottomMargin = 70;
				BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, drawLine, drawContent);
							// Create Fact header row
							Row<PDPage> rowA = table.createRow(10f);
							Cell<PDPage> cellA = rowA.createCell(100 , "AKTIVITI-AKTIVITI SEPANJANG BULAN TERKINI");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setTopBorderStyle(null);
							cellA.setRightBorderStyle(null);
							cellA.setLeftBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							////////////////////////////////////////////////////////////////////////
							rowA = table.createRow(10f);
							cellA = rowA.createCell(11 , "HARI/<br>TARIKH");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setBottomBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.BOTTOM);
							
							cellA = rowA.createCell(20 , "AKTIVITI");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setBottomBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.BOTTOM);
							
							cellA = rowA.createCell(10 , "SESI");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setBottomBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.BOTTOM);
							
							cellA = rowA.createCell(15 , "TENAGA PENGGERAK");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setBottomBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.BOTTOM);
							
							cellA = rowA.createCell(24, "BANGSA");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell( 13 , "JUMLAH<br>KEHADIRAN");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell(7 , "KOS (RM)");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setBottomBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.BOTTOM);
							/////////////////////////////////////////////////////////////
							
							rowA = table.createRow(5f);
							cellA = rowA.createCell(11 , "");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setTopBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell(20 , "");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setTopBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell(10 , "");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setTopBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell(15 , "");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setTopBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell(6, "M");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell(6, "C");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell(6, "I");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell(6, "LL");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell( (float)6.5 , "L");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell( (float)6.5 , "P");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							
							cellA = rowA.createCell(7 , "");
							cellA.setFont(PDType1Font.HELVETICA_BOLD);
							cellA.setFontSize(10);
							cellA.setTopBorderStyle(null);
							cellA.setAlign(HorizontalAlignment.CENTER);
							cellA.setValign(VerticalAlignment.MIDDLE);
							/////////////////////////////////////////////////////////////
							intMonth= intMonth-1;
							for(int i=0; i<totalDays; i++){
								Calendar cal = Calendar.getInstance();
						        cal.set(Calendar.DATE, (i+1));
						        cal.set(Calendar.MONTH, intMonth);
						        cal.set(Calendar.YEAR, intYear);
						        Date firstDay = cal.getTime();
						        DateFormat sdf = new SimpleDateFormat("EEEEEEEE");
						        String day = sdf.format(firstDay);
						        
						        DateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
						        String dateToday = dt.format(firstDay);

						        List<Event> aE = new ArrayList<Event>();
						        aE = Event.getEventInfo(dateToday, aE);
						        
						        
						        if(day.equals("Monday")){	day="Isnin";}
								else if(day.equals("Tuesday")){ day="Selasa";}
								else if(day.equals("Wednesday")){ day="Rabu";}
								else if(day.equals("Thursday")){ day="Khamis";}
								else if(day.equals("Friday")){ day="Jumaat";}
								else if(day.equals("Saturday")){ day="Sabtu";}
								else if(day.equals("Sunday")){ day="Ahad";}
						        if (aE.size()>0){
						        for(int a=0; a<aE.size(); a++){
						        	rowA = table.createRow(5f);
									cellA = rowA.createCell(11 , (i+1) + "<br>" + day);
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(20 , aE.get(a).getEventName());
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(10 , aE.get(a).getEventTime());
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(15 , aE.get(a).getEventCommInvolved());
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									List<Member> listmem = Member.calculateAttendance(aE.get(a).getEventID());
									int M =0, C=0, I=0, LL=0, P=0, L=0;
									if(listmem.size()>0){
										for(int k=0;k<listmem.size();k++){
											if(listmem.get(k).getMemGender().equals("Lelaki")){
												L++;
											}else if(listmem.get(k).getMemGender().equals("Perempuan")){
												P++;
											}
											if(listmem.get(k).getMemRace().equals("Melayu")){
												M++;
											}else if(listmem.get(k).getMemRace().equals("Cina")){
												C++;
											}else if(listmem.get(k).getMemRace().equals("India")){
												I++;
											}else if(listmem.get(k).getMemRace().equals("Lain-Lain")){
												LL++;
											}
										}
									}
									
									cellA = rowA.createCell(6, Integer.toString(M));
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(6, Integer.toString(C));
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(6, Integer.toString(I));
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(6, Integer.toString(LL));
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell( (float)6.5 , Integer.toString(L));
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell( (float)6.5 , Integer.toString(P));
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(7 , Float.toString(aE.get(a).getEventCost()));
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
						        }
						        
						        }else{
						        	rowA = table.createRow(5f);
									cellA = rowA.createCell(11 , (i+1) + "<br>" + day);
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(20 , "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(10 , "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(15 , "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(6, "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(6, "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(6, "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(6, "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell( (float)6.5 , "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell( (float)6.5 , "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									cellA = rowA.createCell(7 , "");
									cellA.setFont(PDType1Font.HELVETICA);
									cellA.setFontSize(10);
									cellA.setTopBorderStyle(null);
									cellA.setAlign(HorizontalAlignment.CENTER);
									cellA.setValign(VerticalAlignment.MIDDLE);
									
									
						        }
						        
							}
					        
				table.draw();
				c.close();
				
				PDPage picpage = new PDPage();
		        doc.addPage(picpage);
		        PDPageContentStream cp = new PDPageContentStream(doc, picpage);
		      //Start content
			    float marginP = 30;
			    float yStartNewPageP = page.getMediaBox().getHeight() - (2 * margin);

				// Initialize table
				float tableWidthP = 550;
				boolean drawContentP = true;
				boolean drawLineP = true;
				float yStartP= 703;
				float bottomMarginP = 70;
				BaseTable tableP = new BaseTable(yStartP, yStartNewPageP, bottomMarginP, tableWidthP, marginP, doc, picpage, drawLineP, drawContentP);
							// Create Fact header row
							Row<PDPage> rowP = tableP.createRow(10f);
							Cell<PDPage> cellP = rowP.createCell(100 , "GAMBAR AKTIVITI-AKTIVITI SEPANJANG BULAN TERKINI");
							cellP.setBorderStyle(null);
							cellP.setFont(PDType1Font.HELVETICA_BOLD);
							cellP.setFontSize(10);
							cellP.setAlign(HorizontalAlignment.CENTER);
							
							Statement stmt = null;
							try{
								currentCon = ConnectionManager.getConnection();
								stmt= currentCon.createStatement();
			                    ResultSet res = stmt.executeQuery("select id, photo, title, e.eventid, eventname, to_char(eventdate,'DD/MM/YYYY')as eventdate from photos p join event e on(p.eventid = e.eventid) where EXTRACT(MONTH FROM EVENTDATE) = " + month +" AND  EXTRACT(YEAR FROM EVENTDATE) = " + year);
			                    if(res!=null){
				                    while(res.next())
				                    {
				                    	if(res.getString("title")!=null){
				                    		PreparedStatement ps = currentCon.prepareStatement("select photo from photos where id = ?");
				        		            String id = res.getString("id");
				        		            ps.setString(1,id);
				        		            ResultSet rs = ps.executeQuery();
				        		            rs.next();
				        		            InputStream is = rs.getBinaryStream("photo");
				        		            BufferedImage salamBuff = ImageIO.read(is);
				        			        Image img = new Image(salamBuff,280);
				                    		rowP = tableP.createRow(10f);
											cellP = rowP.createImageCell(100, img, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
											cellP.setBorderStyle(null);
											is.close();
											rowP = tableP.createRow(3);
											cellP = rowP.createCell(100 , res.getString("eventname")+" ("+res.getString("eventdate")+"): " + res.getString("title"));
											cellP.setRightBorderStyle(null);
											cellP.setTopBorderStyle(null);
											cellP.setLeftBorderStyle(null);
											cellP.setFont(PDType1Font.HELVETICA);
											cellP.setFontSize(10);
											cellP.setAlign(HorizontalAlignment.CENTER);
											
				                    	}else{
				                    		PreparedStatement ps = currentCon.prepareStatement("select photo from photos where id = ?");
				        		            String id = res.getString("id");
				        		            ps.setString(1,id);
				        		            ResultSet rs = ps.executeQuery();
				        		            rs.next();
				        		            InputStream is = rs.getBinaryStream("photo");
				        		            BufferedImage salamBuff = ImageIO.read(is);
				        			        Image img = new Image(salamBuff,280);
				                    		rowP = tableP.createRow(10f);
											cellP = rowP.createImageCell(100, img, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
											cellP.setBorderStyle(null);
											is.close();
											rowP = tableP.createRow(3);
											cellP = rowP.createCell(100 , res.getString("eventname")+" ("+res.getString("eventdate")+")");
											cellP.setRightBorderStyle(null);
											cellP.setTopBorderStyle(null);
											cellP.setLeftBorderStyle(null);
											cellP.setFont(PDType1Font.HELVETICA);
											cellP.setFontSize(10);
											cellP.setAlign(HorizontalAlignment.CENTER);
				                    	}
				                    }
			                    }else{
			                    	rowP = tableP.createRow(10f);
									cellP = rowP.createCell(100 , "Tiada Gambar");
									cellP.setFont(PDType1Font.HELVETICA_BOLD);
									cellP.setFontSize(10);
									cellP.setAlign(HorizontalAlignment.CENTER);
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
							
				tableP.draw();
				cp.close();
				doc.save(out); // saving as pdf file with name perm 

				doc.close(); // cleaning memory 
			}catch(IOException e){
		        System.out.println(e.getMessage());
		    }finally{
		    	response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "inline; filename=" + fileName);
				response.getOutputStream().write(out.toByteArray());
		    	out.close();
		    } 
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("createMemberInfo")){
			try {
				Active aM = new Active(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
				aM.setMemName(request.getParameter("nama"));
				aM.setMemIC(request.getParameter("kp"));
				aM.setMemGender(request.getParameter("gender"));
				aM.setMemMarriageStatus(request.getParameter("marriageStat"));
				if(request.getParameter("marriageStat").equals("Berkahwin")){
					aM.setMemSpouseName(request.getParameter("spouseName"));
				}else{
					aM.setMemSpouseName(null);
				}
				aM.setMemRace(request.getParameter("race"));
				aM.setMemLivingWith(request.getParameter("living"));
				aM.setMemAddress(request.getParameter("address"));
				aM.setMemHomeNo(request.getParameter("homeNo"));
				aM.setMemMobileNo(request.getParameter("mobileNo"));
				aM.setMemAcademic(request.getParameter("academic"));
				String lain = request.getParameter("lainlain");
				String job = request.getParameter("job");
				if(job.equals("Lain-lain")){
					job = lain;
				}
				aM.setMemCurrentJob(job);
				aM.setMemLastJob(request.getParameter("lastJob"));
				aM.setMemLastWorkplace(request.getParameter("lastWorkplace"));
				
				boolean check = aM.checkMember(aM);
				if(check){
					boolean mem = aM.createMemberInfo(aM, (String) request.getSession().getAttribute("staffIC"));
					if (mem) {
						int ID = Member.getMemberID(aM.getMemIC());
						PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.print("<script type=\"text/javascript\">");
						out.print("alert('Pendaftaran ahli berjaya dimasukkan.');");
						out.print("window.location = '/PAWEM/member?action=viewMemberInfo&&type=createForm&&memID="+ID+"';");
						out.print("</script>");
					}
					else{
						PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.print("<script type=\"text/javascript\">");
						out.print("alert('Pendaftaran ahli tidak berjaya dimasukkan. Sila cuba lagi');");
						out.print("window.location = '/PAWEM/member?action=viewMemberList';");
						out.print("</script>");
					}
				}else{
					PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.print("<script type=\"text/javascript\">");
					out.print("alert('Pendaftaran ahli tidak berjaya. Ahli sudah wujud di dalam sistem');");
					out.print("window.location = '/PAWEM/member?action=viewMemberList';");
					out.print("</script>");
				}
				
				
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}else if(action.equals("updateMemberInfo")){
			try {
				Active aM = new Active(0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
				aM.setMemID(Integer.parseInt(request.getParameter("memID")));
				aM.setMemName(request.getParameter("nama"));
				aM.setMemIC(request.getParameter("kp"));
				aM.setMemGender(request.getParameter("gender"));
				aM.setMemMarriageStatus(request.getParameter("marriageStat"));
				if(request.getParameter("marriageStat").equals("Berkahwin")){
					aM.setMemSpouseName(request.getParameter("spouseName"));
				}else{
					aM.setMemSpouseName(null);
				}
				aM.setMemLivingWith(request.getParameter("living"));
				aM.setMemRace(request.getParameter("race"));
				aM.setMemAddress(request.getParameter("address"));
				aM.setMemHomeNo(request.getParameter("homeNo"));
				aM.setMemMobileNo(request.getParameter("mobileNo"));
				aM.setMemAcademic(request.getParameter("academic"));
				String lain = request.getParameter("lainlain");
				String job = request.getParameter("job");
				if(job.equals("Lain-lain")){
					job = lain;
				}
				aM.setMemCurrentJob(job);
				aM.setMemLastJob(request.getParameter("lastJob"));
				aM.setMemLastWorkplace(request.getParameter("lastWorkplace"));
				Dropout aD = new Dropout(Integer.parseInt(request.getParameter("memID")),null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,request.getParameter("dropoutReason"));
				
				boolean mem = Member.updateMemberInfo(aD, aM, (String) request.getSession().getAttribute("staffIC"), Integer.parseInt(request.getParameter("memID")));
				if (mem) {
					PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.print("<script type=\"text/javascript\">");
					out.print("alert('Kemaskini ahli berjaya.');");
					out.print("window.location = '/PAWEM/member?action=viewMemberInfo&&type=updateForm&&memID="+request.getParameter("memID")+"';");
					out.print("</script>");
				}
				else{
					PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.print("<script type=\"text/javascript\">");
					out.print("alert('Kemasini ahli tidak berjaya. Sila cuba lagi');");
					out.print("window.location = '/PAWEM/member?action=viewMemberList';");
					out.print("</script>");
				}
					
			}
			catch (Throwable theException) {
				System.out.println(theException);
			}
		}
	}

}
