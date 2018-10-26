<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.pawem.model.Staff,java.io.PrintWriter" %>
 <%	
 String userName = (String) session.getAttribute("staffName");
	if(userName==null){
		PrintWriter out1 = response.getWriter();
		response.setContentType("text/html");
		out1.print("<script type=\"text/javascript\">");
		out1.print("alert('Anda tiada akses untuk halaman ini. Sila login semula');");
		out1.print("window.location = 'login.jsp';");
		out1.print("</script>");
	}
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Kemaskini Staf</title>

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
        <!-- /.navbar-static-side -->
      
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                        <h1 class="page-header">
                            <small> Kemaskini Staf </small>
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="member?action=home">Halaman Utama</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-folder"></i> <a href='/PAWEM/staff?action=viewStaffList'>Senarai Staf</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file"></i> Borang Staf
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
				 <div class="panel panel-yellow">
                            <div class="panel-heading">
                                <h3 class="panel-title">Kemaskini Staf</h3>
                            </div>
						
						<div class="panel-body">	
                        
						<form role="form" name="createStaff" action="staff" onsubmit="return validateStaff()" method="post">
                            <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Nama : </label>
                                        <div class="col-md-10">
                                			<input type="text" name="staffName" value='<%= request.getAttribute("staffName") %>' class="form-control"  >
                                			<p id="message" ></p>
                                		</div>
                                    </div>
                                </div>
                                <br/>
                                
								<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">No. Kad Pengenalan : </label>
                                        <div class="col-md-10">
                                			<input type="text" name="staffIC" class="form-control" class="form-control" value='<%= request.getAttribute("staffIC") %>' >
                                			<p id="message2" ></p>
                                		</div>
                                    </div>
                                </div>
                                <br/>
								

								<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Kata Laluan : </label>
                                        <div class="col-md-10">
                                			<input type="password" name="staffPwd" class="form-control" value='<%= request.getAttribute("staffPwd") %>' >
                                		 	<p id="message3" ></p>
                                		 </div>
                                    </div>
                                </div>
                                <br/>
                                			
                                <input type="hidden" name="staffRole" class="form-control" value='<%= request.getAttribute("staffRole") %>'><br/>
								<input type="hidden" value='<%= request.getAttribute("staffIC") %>' name="currentIC">
								<input type="hidden" value="updateStaffAccount" name="action">							

							<input type="submit" name="submit" class="btn btn-default" value="Kemaskini">
                            <input type="button" onclick="javascript:history.go(-1)" class="btn btn-default" value ="Kembali"/>
								</form>	
							</div> 	
					
            </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        <!-- /#page-wrapper -->

    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="startbootstrap-sb-admin-2-gh-pages/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="startbootstrap-sb-admin-2-gh-pages/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="startbootstrap-sb-admin-2-gh-pages/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="startbootstrap-sb-admin-2-gh-pages/dist/js/sb-admin-2.js"></script>
    <script type="text/javascript">
    
    function validateStaff()
    {	
    	var v = "createStaff";
    	
    	var name = document.forms[v]["staffName"].value;
    	var ic = document.forms[v]["staffIC"].value;
    	var pwd = document.forms[v]["staffPwd"].value;
    	var role = document.forms[v]["staffRole"].value;

    	
    	//Name Validation
    	if (name == "")
    		{
    		    var message = document.getElementById("message");
    		    message.innerHTML = "<font color='red'>Ruangan Nama Staff tidak di-isi.</font>";
    			document.createStaff.staffName.style.borderColor = "#ff0000";
    			document.createStaff.staffName.focus();
    			return false;	
    		}
    	
    	//!/^[a-zA-Z]*$/g.test(name) == true
    	 ///^[a-zA-Z ]+$/
    	 
    	else if (!/^[A-Za-z\s]+$/g.test(document.createStaff.staffName.value)) 
    	/*( !/^[0-9]+$/.test(name) == false)*/
		{
    		var message = document.getElementById("message");
		    message.innerHTML = "<font color='red'>Sila isi ( Huruf sahaja )</font>";
		    document.createStaff.staffName.style.borderColor = "#ff0000";
			document.createStaff.staffName.focus();
			return false;
			
		}
  
    	else {
    		
    		document.createStaff.staffName.style.borderColor = "green";
    		var message = document.getElementById("message");
 		    message.innerHTML = "<font color='Green'>OK</font>";
    	}
    	
    	//IC Validation
    	if (ic == "" )
    		{
    		var message2 = document.getElementById("message2");
		    message2.innerHTML = "<font color='red'>Ruangan No. K/P tidak di-isi. Sila isi ( contoh : 950123149087 ).</font>";
			document.createStaff.staffIC.style.borderColor = "#ff0000";
    		document.createStaff.staffIC.focus();
    		return false;
    		}
    	  	
    	else if (!/^[0-9]+$/.test(ic) == true )
			{
    		var message2 = document.getElementById("message2");
    		message2.innerHTML = "<font color='red'>No. K/P perlu diisi dengan nombor sahaja ( 12 nombor ). Sila isi semula( contoh : 950123149087 ).</font>";
			document.createStaff.staffIC.style.borderColor = "#ff0000";
			document.createStaff.staffIC.focus();
			return false;
			}
    	
    	else if (ic.length != 12 )
		{
			var message2 = document.getElementById("message2");
			message2.innerHTML = "<font color='red'>No. K/P perlu diisi dengan nombor sahaja ( 12 nombor ). Sila isi semula( contoh : 950123149087 ).</font>";
			document.createStaff.staffIC.style.borderColor = "#ff0000";
			document.createStaff.staffIC.focus();
		return false;
		}
    	
    	
    	else {document.createStaff.staffIC.style.borderColor = "green";
    			var message2 = document.getElementById("message2");
		    	message2.innerHTML = "<font color='Green'>OK</font>";}
    	
    	if (pwd == "")
    	{
    		var message3 = document.getElementById("message3");
		    message3.innerHTML = "<font color='red'>Sila Isi Kata Laluan Staff</font>";
			document.createStaff.staffPwd.style.borderColor = "#ff0000";
    		document.createStaff.staffPwd.focus();
    		return false;
    	}
    	
   		 else {
   			{document.createStaff.staffPwd.style.borderColor = "green";
			var message3 = document.getElementById("message3");
	    	message3.innerHTML = "<font color='Green'>OK</font>";}
   		 }
    	
    	if (role == "")
     	{
    		var message4 = document.getElementById("message4");
		    message4.innerHTML = "<font color='red'>Sila Pilih Peranan Staff</font>";
			document.createStaff.staffRole.style.borderColor = "#ff0000";
    		document.createStaff.staffRole.focus();
    		return false;
     	}
     else {
    	 document.createStaff.staffRole.style.borderColor = "green";
			var message4 = document.getElementById("message4");
	    	message4.innerHTML = "<font color='Green'>OK</font>";}
    	 }	
    		   									
    		
    </script>

</body>

</html>
