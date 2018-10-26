<%@ page language="java"
 contentType="text/html; charset=ISO-8859-1"
 pageEncoding="windows-1256"
 import="com.pawem.model.Staff,java.io.PrintWriter" %>
 <%	
	if(session.getAttribute("staffRole").equals("Pengerusi")){
		PrintWriter out1 = response.getWriter();
		out1.print("<script type=\"text/javascript\">");
		out1.print("alert('Anda tiada akses untuk halaman ini. Sila login semula');");
		out1.print("window.location = 'login.jsp';");
		out1.print("</script>");
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

    <title>Daftar Staf</title>

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
                            <small> Daftar Staf </small>
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
                                <h3 class="panel-title">Daftar Staf</h3>
                            </div>
						
						<div class="panel-body">	
                        
						<form role="form" name="createStaff" action="staff" method="post" onsubmit="return validateStaff()">
                            
							<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Nama : </label>
                                        <div class="col-md-10">
                                			<input type="text" name="staffName" value="" class="form-control"  >
                                			<p id="message" ></p>
                                		</div>
                                    </div>
                                </div>
                                <br/>
                                
								<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">No.Kad Pengenalan: </label>
                                        <div class="col-md-10">
                                			<input type="text" name="staffIC" class="form-control" value=""  >
                                			<p id="message2" ></p>
                                		</div>
                                    </div>
                                </div>
                                <br/>
								

								<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Kata Laluan : </label>
                                        <div class="col-md-10">
                                			<input type="password" name="staffPwd" class="form-control" value=""  >
                                		 	<p id="message3" ></p>
                                		 </div>
                                    </div>
                                </div>
                                <br/>
                                			
                                			
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Peranan : </label>
                                        <div class="col-md-10">
			                                <select class="form-control" name="staffRole"  >
			                                	<option value="">---Pilih satu---</option>
			                                	<option value="Penyelaras">Penyelaras</option>
												<option value="Pengerusi">Pengerusi</option>
											</select>
											<p id="message4" ></p>
                                 </div>
                                    </div>
                                </div>
                                <br/>
							<input type="hidden" name="action" value="createStaff" />
							<input type="submit" name="submit" class="btn btn-default" value="Hantar">
                            <input type="reset" class="btn btn-default" value="Batal">
								</form>	
							</div> 	
					
            </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
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
    <script>
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
