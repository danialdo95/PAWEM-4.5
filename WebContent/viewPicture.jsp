<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "com.pawem.model.Photos,java.util.List,java.io.PrintWriter"%>
      <%	
	if(session.getAttribute("staffRole").equals("Pengerusi")){
		out.print("<script type=\"text/javascript\">");
		out.print("alert('Anda tiada akses untuk halaman ini. Sila login semula');");
		out.print("window.location = 'login.jsp';");
		out.print("</script>");
	}
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Borang Aktiviti</title>
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
	<%@ page import="java.io.*, java.util.*, java.sql.*" %>
	<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
	<%@ page import="javax.servlet.annotation.MultipartConfig" %>
	<%@ page import="java.io.InputStream" %>
	<%@page import="java.sql.PreparedStatement" %>
	
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
                            PAWEM Borang Aktiviti
                            <!--<small>Subheading</small>-->
                        </h1>
                       <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="/PAWEM/home">Halaman Utama</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-folder"></i> <a href='/PAWEM/event?action=viewEventList&&type=list'>Senarai Aktiviti</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file"></i> Borang Aktiviti
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
			<div class="row">
				<div class="panel panel-yellow">
                            <div class="panel-heading">
                                <h3 class="panel-title">Gambar Aktiviti </h3>
                            </div>
                        <!-- form role="form" action="addphoto" method="post" -->
						<div class="panel panel-default"><br/>
                            <div class="panel-body">
                               <%
                               		
	                               List<Photos> pic = (List<Photos>)request.getAttribute("photoList");
	                               if(pic.size()>0){
	                            	   
                               %>
                               
                                <div class="row">
			                		<div class="col-lg-12">
			                        <div class="table-responsive">
			                            <table class="table table-bordered table-hover table-striped">
				                        <tr>
	                                    	<td>Keterangan Gambar</td>
	                                    	<td>Gambar Aktiviti	</td>
	                                    	<td>Tindakan</td>
	                                    </tr>
	                                    <% for(int i = 0; i<pic.size();i++){ %>
	                                    <tr>
	                                    	<% if(pic.get(i).getPhotoDescription()!=null){%>
	                                    	
	                                    	<td><%= pic.get(i).getPhotoDescription()%></td> <% }else{  %> <td> &nbsp; </td> <% } %>
	                                    	<td><img width="600"  src="eventReport?action=displayPhoto&&id=<%= pic.get(i).getPhotoID()%> "></img></td>
	                                        <td>
	                                        	<a onclick="javascript:if(confirm('Adakah anda pasti?')){window.location='/PAWEM/eventReport?action=deletephoto&&eventID=<%= pic.get(i).getEventID() %>&&picID=<%= pic.get(i).getPhotoID()%>';}"  class="btn btn-default">
	                                        <i class="fa fa-plus"></i> Hapus Gambar </a> &nbsp;&nbsp;
	                                        </td>
	                                    </tr>
                                   <% } }else{
                                	   out.print("Tiada gambar.<br/><br/>");
                                	   }%>
                                   <tr>
                                    <td colspan="3" align="center">
	                                        <a href="/PAWEM/eventReport?action=addphoto&&eventID=<%= request.getAttribute("eventID") %>" class="btn btn-default">
	                                        <i class="fa fa-plus"></i> Tambah Gambar </a> &nbsp;&nbsp;
                                        <a href="/PAWEM/event?action=viewEventInfo&&eventID=<%= request.getAttribute("eventID") %>&&type=detail" class="btn btn-default">
                                        <i class="fa fa-check"></i> Kembali </a> &nbsp;&nbsp;
                                    </td>
                                </tr>
                                   </table>
                                </div>
                                </div>
                                </div>
                       
						</div>
                            </div>
            </div>
             
            </div>
            
            </div>
            <br>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="startbootstrap-sb-admin-2-gh-pages/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="startbootstrap-sb-admin-2-gh-pages/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="startbootstrap-sb-admin-2-gh-pages/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="startbootstrap-sb-admin-2-gh-pages/dist/js/sb-admin-2.js"></script>

</body>

</html>
