package com.pawem.controller;

import java.sql.Statement;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pawem.model.Photos;
import com.pawem.util.ConnectionManager;

/**
 * Servlet implementation class eventReportHandler
 */
@WebServlet("/eventReportHandler")
@MultipartConfig(maxFileSize = 16177215) 
public class eventReportHandler extends HttpServlet {
	static final long serialVersionUID = -6556345043246714958L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public eventReportHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String action = request.getParameter("action");
		        
			if(action.equals("viewPicture")){
				try {
					int eventID= Integer.parseInt(request.getParameter("eventID"));
					List<Photos> photoList = new ArrayList<Photos>();
					photoList = Photos.viewPhotoInfo(photoList, eventID);
					request.setAttribute("photoList", photoList);
					request.setAttribute("eventID",eventID);
					request.getRequestDispatcher("viewPicture.jsp").forward(request,response);
					
				}catch(Exception ex) {
		            System.out.println( "Error --> " + ex.getMessage());
		        }
			}
			else if(action.equals("addphoto")){
				try {
					String eventID=request.getParameter("eventID");
		            request.setAttribute("eventID",eventID);
					request.getRequestDispatcher("createPictureForm.jsp").forward(request,response);
		            
		        }//close try
		        catch(Exception ex) {
		            System.out.println( "Error --> " + ex.getMessage());
		        }
			}//if=addphoto
			
			else if(action.equals("deletephoto")){
				try {
					int eventID=Integer.parseInt(request.getParameter("eventID"));
					int picID=Integer.parseInt(request.getParameter("picID"));
					Photos aP = new Photos(picID, null, null, 0, eventID);
					int delete = Photos.deletePhotoInfo(aP);
					if(delete == 1){
						PrintWriter out = response.getWriter();
		 				response.setContentType("text/html");
		 				out.print("<script type=\"text/javascript\">");
		 				out.print("alert('Penghapusan gambar berjaya.');");
		 				out.print("window.location = '/PAWEM/eventReport?action=viewPicture&&eventID="+eventID+"';");
		 				out.print("</script>");
					}else if(delete== 2){
						PrintWriter out = response.getWriter();
		 				response.setContentType("text/html");
		 				out.print("<script type=\"text/javascript\">");
		 				out.print("alert('Penghapusan gambar berjaya.');");
		 				out.print("window.location = '/PAWEM/event?action=viewEventInfo&&eventID="+ eventID+"&&type=detail';");
		 				out.print("</script>");
					}else{
						PrintWriter out = response.getWriter();
		 				response.setContentType("text/html");
		 				out.print("<script type=\"text/javascript\">");
		 				out.print("alert('Penghapusan gambar tidak berjaya.');");
		 				out.print("window.location = '/PAWEM/eventReport?action=viewPicture&&eventID="+eventID+"';");
		 				out.print("</script>");
					}
				}catch(Exception ex) {
		            System.out.println( "Error --> " + ex.getMessage());
		        }
			}else if(action.equals("displayPhoto")){
				try {
					Connection currentCon = ConnectionManager.getConnection();
					PreparedStatement ps = currentCon.prepareStatement("select photo from photos where id = ?");
		            String id = request.getParameter("id");
		            ps.setString(1,id);
		            ResultSet rs = ps.executeQuery();
		            rs.next();
		            Blob  b = rs.getBlob("photo");
		            response.setContentType("image/*");
		            response.setContentLength( (int) b.length());
		            InputStream is = b.getBinaryStream();
		            OutputStream os = response.getOutputStream();
		            byte buf[] = new byte[(int) b.length()];
		            is.read(buf);
		            os.write(buf);
		            os.close();
		        }
		        catch(Exception ex) {
		             System.out.println(ex.getMessage());
		        }
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//if(action.equals("addPicture")){
			try {
		    // Apache Commons-Fileupload library classes
		    DiskFileItemFactory factory = new DiskFileItemFactory();
		    ServletFileUpload sfu = new ServletFileUpload(factory);
		
		    if (! ServletFileUpload.isMultipartContent(request)) {
		        System.out.println("sorry. No file uploaded");
		        return;
		    }
		    
		    // parse request
		    //String eventID=request.getParameter("eventID");
			List<FileItem> items = sfu.parseRequest(request);
		    
		    
		    FileItem diskripsi = (FileItem) items.get(2);
		    String   phototitle =  diskripsi.getString();
		    
		    FileItem eventid = (FileItem) items.get(0);
		    int   eventID =  Integer.parseInt(eventid.getString());
		
		    // get uploaded file
		    FileItem file = (FileItem) items.get(1);
		    InputStream photoContent = file.getInputStream();
		    int fileSize = (int) file.getSize();
		    Photos aP = new Photos(0, phototitle, photoContent, fileSize , eventID);
		    boolean create = Photos.createPhotoInfo(aP);
		    if (create) {
		    	out = response.getWriter();
				response.setContentType("text/html");
				out.print("<script type=\"text/javascript\">");
				out.print("alert('Kemaskini gambar berjaya.');");
				out.print("window.location = '/PAWEM/eventReport?action=viewPicture&&eventID="+eventID+"';");
				out.print("</script>");
			}
		    
			}
			catch(Exception ex) {
	            out.println( "Error --> " + ex.getMessage());
	            ex.printStackTrace();
			//}
		}//close if
	}
	
}
