<%@ page import ="java.io.*, java.util.*, java.sql.*" %>
<%@ page import ="javax.servlet.http.*, javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
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
                            PAWEM Borang Laporan
                            <!--<small>Subheading</small>-->
                        </h1>
                       <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="member?action=home">Halaman Utama</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file"></i> Borang Laporan
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
			<div class="row">
				<div class="panel panel-yellow">
                            <div class="panel-heading">
                                <h3 class="panel-title">Aktiviti Baru </h3>
                            </div>
                        <form name="monthyear" action="member" method="get" role="form" >
						<div class="panel panel-default"><br/>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-1">Bulan: </label>
                                        <div class="col-md-5">
                                        <select name="month" class="form-control">
							              <option value="1">JANUARI</option>
							              <option value="2">FEBRUARI</option>
							              <option value="3">MAC</option>
							              <option value="4">APRIL</option>
							              <option value="5">MEI</option>
							              <option value="6">JUN</option>
							              <option value="7">JULAI</option>
							              <option value="8">OGOS</option>
							              <option value="9">SEPTEMBER</option>
							              <option value="10">OKTOBER</option>
							              <option value="11">NOVEMBER</option>
							              <option value="12">DISEMBER</option>
							            </select>
                                    </div>
                                     <label class="col-md-1">Tahun: </label>
                                        <div class="col-md-5">
                                        <sql:setDataSource var="years" driver="oracle.jdbc.driver.OracleDriver" url="jdbc:oracle:thin:@localhost:1521:xe" user="pawem" password="system"/>
								          <sql:query dataSource="${years}" var="y">
								          	SELECT DISTINCT EXTRACT(YEAR FROM EVENTDATE) AS YEAR FROM EVENT ORDER BY EXTRACT(YEAR FROM EVENTDATE) DESC
											</sql:query>
                                        <select name="year" class="form-control">
							              <c:forEach var="row" items="${y.rows}">
							                  <option value="<c:out value="${row.YEAR}"/>"><c:out value="${row.YEAR}"/></option>
							              </c:forEach>
							            </select>
                                    </div>
                                    </div>
                                </div>
                                    <br/>
                            
                            <input type="hidden" name="action" value="monthlyReport" />
       						<input type="submit" class="btn btn-default" value="Hantar">
                        	<input type="button" onclick="javascript:history.go(-1)" class="btn btn-default" value ="Kembali"/>
                       
						</form>	
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
    
     <!-- Validation JavaScript -->


</body>

</html>