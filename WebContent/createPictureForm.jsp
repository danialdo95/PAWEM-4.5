<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
                        <form role="form"  action="eventReport" method="post" enctype="multipart/form-data">
						<div class="panel panel-default"><br/>
                            <div class="panel-body">
                               
                               <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Pilih Gambar : </label>
                                        <div class="col-md-10">
                                        <input type="hidden" name="eventID" class="form-control" value="<%= request.getAttribute("eventID") %>" required><br/>
                                        <input type="file" name="photo" accept="image/*" required><br/>
                                    </div>
                                    </div>
                                </div>
                                <br/>
                               <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Keterangan Gambar : </label>
                                        <div class="col-md-6">
                                        <textarea  name="title" class="form-control" ></textarea><br/>
                                    </div>
                                    </div>
                                </div>
                                <br/>
                        
                        <!-- input type="hidden" name="action" value="addPicture" /-->
						<input type="submit" name="submit" class="btn btn-default" value="Hantar Gambar" />
                        <a href="/PAWEM/eventReport?action=viewPicture&&eventID=<%= request.getAttribute("eventID") %>"><input type="button" class="btn btn-default" value ="Kembali"/></a>
                       
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

</body>

</html>
