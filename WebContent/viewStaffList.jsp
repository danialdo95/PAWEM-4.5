<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.pawem.model.Staff,java.io.PrintWriter,com.sun.net.httpserver.Filter.Chain" %>
 <%	
	
	if(session.getAttribute("staffRole").equals("Pengerusi")){
		PrintWriter out1 = response.getWriter();
		response.setContentType("text/html");
		out1.print("<script type=\"text/javascript\">");
		out1.print("alert('Anda tiada akses untuk halaman ini. Sila login semula');");
		out1.print("window.location = 'login.jsp';");
		out1.print("</script>");
	}
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Senarai Staf</title>

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
       
        <!-- Page Content -->
        <div id="page-wrapper">
        <br><br><br><br>
         	<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Senarai Staf
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Nama</th>
                                            <th>No. Kad Pengenalan</th>
                                            <th>Peranan</th>
                                            <th colspan="3" style="text-align:center">Tindakan</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${list}" var="staffs" varStatus="status">
                                        <tr>
                                            <td>${status.count}</td>
                                            <td>${staffs.staffName}</td>
                                            <td>${ staffs.staffIC }</td>
                                            <td>${ staffs.staffRole }</td>
                                            <td align="center">
                                            	<a href="/PAWEM/staff?action=viewStaffInfo&&IC=${ staffs.staffIC }" class="label label-warning">
                                        		<i class="fa fa-edit"></i> Kemaskini </a>
                                            </td>
                                            <td align="center">
                                            	<a href="#" onclick="javascript:if(confirm('Adakah anda pasti?')){window.location='/PAWEM/staff?action=deleteStaffInfo&&IC=${ staffs.staffIC }&&role=${ staffs.staffRole }';}" data-toggle="modal" data-target="#delete" class="label label-danger">
                                        		<i class="fa fa-trash-o"></i> Hapus </a>
                                            </td>                                            
                                        </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
			</div>
		</div>
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