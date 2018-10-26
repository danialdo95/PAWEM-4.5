<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "com.pawem.model.Member,com.pawem.model.Photos,java.util.List,java.io.PrintWriter"%>
<%@ page import="java.io.*, java.util.*, java.sql.*" %>
	<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
	<%@ page import="javax.servlet.annotation.MultipartConfig" %>
	<%@ page import="java.io.InputStream" %>
	<%@page import="java.sql.PreparedStatement" %>
	
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Maklumat Ahli</title>

    <!-- Bootstrap Core CSS -->
    <link href="startbootstrap-sb-admin-2-gh-pages/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="startbootstrap-sb-admin-2-gh-pages/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="startbootstrap-sb-admin-2-gh-pages/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="startbootstrap-sb-admin-2-gh-pages/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">
		
		<%@include file="sidebar.jsp"%>
		<br><br>
        <div id="page-wrapper">
	
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Maklumat Aktiviti
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i> <a href="member?action=home">Halaman Utama</a> 
                            </li>
                            <li>
                                <i class="fa fa-file"></i>  <a href='/PAWEM/event?action=viewEventList&&type=list'>Senarai Aktiviti</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file"></i> Maklumat Aktiviti
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                <div class="col-lg-12">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover table-striped">
                                <tr>
                                    <th style="background-color:#f2f2f2;">Nama Aktiviti</th>
                                    <td><%= request.getAttribute("eventName") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Tarikh Aktiviti</th>
                                    <td><%= request.getAttribute("eventDate") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Sesi Aktiviti</th>
                                    <td><%= request.getAttribute("eventTime") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Tempat Aktiviti</th>
                                    <td><%= request.getAttribute("eventVenue") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Keterangan Aktiviti</th>
                                    <td><%= request.getAttribute("eventDescription") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Jenis Aktiviti</th>
                                    <td><% if(request.getAttribute("eventType").equals("A")){out.print("A: Program Pembelajaran Sepanjang Hayat");} else{ out.print("B: Program Pemerkasaan Ekonomi");} %></td>
                                </tr>       
                                <tr>
                                    <th style="background-color:#f2f2f2;">Kos Aktiviti</th>
                                    <td>RM <%= request.getAttribute("eventCost" ) %></td>
                                </tr>  
                                <tr>
                                    <th style="background-color:#f2f2f2;">Tenaga Penggerak</th>
                                    <td><%= request.getAttribute("eventCommInvolve" ) %></td>
                                </tr>          
                                <% 
                                	if(request.getAttribute("from").equals("detail")){
                                		
                                	
                                	List<Member> members = (List<Member>) request.getAttribute("members");
                                	if(members.size() > 0){
                                	%>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Ahli Terlibat</th>
                                    <td>
                                    <% for(int i=0; i < members.size(); i++){
                                    	out.print(members.get(i).getMemIC() + " - " + members.get(i).getMemName()  + "<br/>"); 
                                    } %>
                                    </td>
                                </tr>   
                                <% } 
                                	  List<Photos> pic = (List<Photos>)request.getAttribute("photoList");
   	                              		 if(pic.size()>0){
                                %>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Gambar</th>
                                    <td>
                                    <div align="center">
	                                    <%
	                                    for(int i = 0; i<pic.size();i++){
				                    	
                               %>
                               		
                               		
                               		
                               		<img width="400"  src="eventReport?action=displayPhoto&&id=<%= pic.get(i).getPhotoID()%> "></img>
                               		<% if(pic.get(i).getPhotoDescription()!=null){%>
	                                    	
	                                    	<br><%= pic.get(i).getPhotoDescription()%><% } %><br/><hr>
                               		<%} }%>
                               		</div>
                                    </td>
                                </tr>  
                                <% if(staffRole.equals("Penyelaras")){ %>
                                <tr>
                                    <td colspan="2" align="center">
                                   		<a href="/PAWEM/event?action=viewEventInfo&&eventID=<%= request.getAttribute("eventID") %>&&type=attendance" class="btn btn-default">
	                                    <i class="fa fa-users"></i> Kemaskini Kehadiran </a> &nbsp;&nbsp;
                                        <a href="/PAWEM/eventReport?action=viewPicture&&eventID=<%= request.getAttribute("eventID") %>" class="btn btn-default">
	                                    <i class="fa fa-image"></i> Kemaskini Gambar </a> &nbsp;&nbsp;
                                    </td>
                                </tr>
                                <% } } %>
                            </table>
                            
                        </div>
                    </div>
						<br>
						<div align="center">
							<% if(request.getAttribute("from").equals("detail")){ %>
                                        <a href="/PAWEM/event?action=viewEventList&&type=list" class="btn btn-default">
                                        <i class="fa fa-check"></i> Kembali </a> &nbsp;&nbsp;
                                    <% }else if(request.getAttribute("from").equals("createForm") || request.getAttribute("from").equals("updateForm")){%>
                                    
                                    	<a href="/PAWEM/event?action=viewEventInfo&&eventID=<%= request.getAttribute("eventID") %>&&type=update" class="btn btn-default">
                                        <i class="fa fa-edit"></i> Kemaskini </a> &nbsp;&nbsp;
                                        <a href='/PAWEM/event?action=viewEventList&&type=list' class="btn btn-default">
                                        <i class="fa fa-list"></i> Seterusnya </a> &nbsp;&nbsp;
                                        <% } %>
						</div>
							
                            
                </div>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
   
 <script src="startbootstrap-sb-admin-2-gh-pages/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="startbootstrap-sb-admin-2-gh-pages/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="startbootstrap-sb-admin-2-gh-pages/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="startbootstrap-sb-admin-2-gh-pages/dist/js/sb-admin-2.js"></script>

</body>

</html>
    