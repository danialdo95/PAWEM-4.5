<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.pawem.model.Member,com.pawem.model.Photos,java.util.List,java.io.PrintWriter"%>
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
    <title>Kemaskini Aktiviti</title>
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
                            Kemaskini Aktiviti
                            <!--<small>Subheading</small>-->
                        </h1>
                       <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="member?action=home">Halaman Utama</a>
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
                                <h3 class="panel-title">Kemaskini Aktiviti </h3>
                            </div>
                        <form role="form" action="event" name="createEvent" method="post" onsubmit="return validateActivity()">
						<div class="panel panel-default"><br/>
                            <div class="panel-body">
                            	<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Nama : </label>
                                        <div class="col-md-10">
                                        	<input name="eventName" value="<%= request.getAttribute("eventName") %>" class="form-control"  >
                                        	<p id="message" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                	<div class="form-group">
                                        <label class="col-md-2">Tenaga Penggerak : </label>
                                		<div class="col-md-10">
                                        	<input name="eventCommInvolved" value="<%= request.getAttribute("eventCommInvolve") %>" class="form-control"  >
                                        	<p id="message2" ></p>
                                        </div>
                                	</div>
                                </div>
                                <br/>
                                <div class="row">
                                	<div class="form-group">
                                		<label class="col-md-2">Tarikh : </label>
                                		<div class="col-md-4">
                                        	<input type="date" name="eventDate" value="<%= request.getAttribute("updateDate") %>"  class="form-control"  >
                                        	<p id="message3" ></p>
                                        </div>
                                        <label class="col-md-2">Sesi : </label> 
                                        <div class="col-md-4">
                                        	<select class="form-control" name="eventTime">
	                                            <option value="">-- Pilih --</option>
	                                            <option value="Pagi" <% if(request.getAttribute("eventTime").equals("Pagi")){ out.print("selected");} %>>Pagi</option>
	                                           <option value="Petang" <% if(request.getAttribute("eventTime").equals("Petang")){ out.print("selected");} %>>Petang</option>
                                       </select>
                                       <p id="message4" ></p>
                                        </div>
                                	</div>
                                </div>
                                <br/>
                                <div class="row">
                                	<div class="form-group">
                                		<label class="col-md-2">Kos Aktiviti : </label> 
                                		<div class="col-md-4">
                                			<div class="form-group input-group">
	                                            <span class="input-group-addon">RM</span>
	                                            <input name="eventCost" type="number" step="0.01" value="<%= request.getAttribute("eventCost") %>" placeholder="00.00"min="00.00"class="form-control" oninput="validity.valid||(value='');">
	                                       		
	                                        </div><p id="message5" ></p>
	                                    </div>
	                                    <label class="col-md-2">Jenis Aktiviti : </label> 
	                                    <div class="col-md-4">
	                                    	<select class="form-control" name="eventType" >
	                                        	<option value="">-- Pilih --</option>
	                                            <option value="A" <% if(request.getAttribute("eventType").equals("A")){ out.print("selected");} %>>A: Program Pembelajaran Sepanjang Hayat</option>
	                                            <option value="B" <% if(request.getAttribute("eventType").equals("B")){ out.print("selected");} %>>B: Program Pemerkasaan Ekonomi</option>
	                                         </select>
	                                         <p id="message6" ></p>
                                        </div>
                                	</div>
                                </div>
                                <br/>
                                <div class="row">
                                	<div class="form-group">
                                		<label class="col-md-2">Tempat Aktiviti : </label>
                                		<div class="col-md-10">
                                			<input name="eventVenue" value="<%= request.getAttribute("eventVenue")%>"  class="form-control"  >
                                			 <p id="message7" ></p>
                                		</div>
                                	</div>
                                </div>
                                <br/>
                                 <div class="row">
                                	<div class="form-group">
                                		 <label class="col-md-2">Keterangan Aktiviti : </label>
                                		 <div class="col-md-10">
                                        	<textarea name="eventDescription" class="form-control"  ><%= request.getAttribute("eventDescription") %></textarea>
                                        	 <p id="message8" ></p>
                                        </div>
                                	</div>
                                </div>
                                <br/>
                            <input type="hidden" name="action" value="updateEventInfo" />
                            <input type="hidden" name="eventID" value="<%= request.getAttribute("eventID")%>" />
						<input type="submit" name="submit" class="btn btn-default" value="Hantar Borang" />
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
    <script>
	function validateActivity()
{	
	
	
	var e = "createEvent";
	
	
	var name = document.forms[e]["eventName"].value;
	var comm = document.forms[e]["eventCommInvolved"].value;
	var date = document.forms[e]["eventDate"].value;
	var session = document.forms[e]["eventTime"].value;
	var cost = document.forms[e]["eventCost"].value;
	var type = document.forms[e]["eventType"].value;
	var place = document.forms[e]["eventVenue"].value;
	var desc = document.forms[e]["eventDescription"].value;
	
	
	//Name Validation
	if (name == "")
		{
		    var message = document.getElementById("message");
		    message.innerHTML = "<font color='red'>Sila Isi Nama Aktiviti</font>";
			document.createEvent.eventName.style.borderColor = "#ff0000";
			document.createEvent.eventName.focus();
			return false;
			
		}
	else {
		document.createEvent.eventName.style.borderColor = "green";
		var message = document.getElementById("message");
		message.innerHTML = "<font color='Green'>OK</font>";
		//document.createEvent.eventName.style.background="";}
	}
	
	//Committee Involved Validation
	if (comm == "")
	{
		var message2 = document.getElementById("message2");
	    message2.innerHTML = "<font color='red'>Sila Isi Nama Tenaga Pengerak</font>";
		document.createEvent.eventCommInvolved.style.borderColor = "#ff0000";
		document.createEvent.eventCommInvolved.focus();
		return false;
	}
	
	else if (!/^[A-Za-z\s]+$/g.test(document.createEvent.eventCommInvolved.value))
	{
		var message2 = document.getElementById("message2");
	    message2.innerHTML = "<font color='red'>Sila Isi Nama Tenaga Pengerak (huruf sahaja)</font>";
		document.createEvent.eventCommInvolved.style.borderColor = "#ff0000";
		document.createEvent.eventCommInvolved.focus();
		return false;
	}
	
	else {
		document.createEvent.eventCommInvolved.style.borderColor = "green";
		var message2 = document.getElementById("message2");
		message2.innerHTML = "<font color='Green'>OK</font>";
		//document.createEvent.eventCommInvolved.style.background="";}
	}
	
	//Date Validation
	
	if (date == "")
	{
		var message3 = document.getElementById("message3");
	    message3.innerHTML = "<font color='red'>Sila pilih Tarikh Aktiviti</font>";
		document.createEvent.eventDate.style.borderColor = "#ff0000";
		document.createEvent.eventDate.focus();
		return false;
	}
	else {
		document.createEvent.eventDate.style.borderColor = "Green";
		var message3 = document.getElementById("message3");
		message3.innerHTML = "<font color='Green'>OK</font>";
		//document.createEvent.eventCommInvolved.style.background="";}
	}
		//document.createEvent.eventDate.style.background="";}

	//Time Session Validation
	if (session == "")
	{
		var message4 = document.getElementById("message4");
	    message4.innerHTML = "<font color='red'>Sila pilih Sesi Aktiviti</font>";
		document.createEvent.eventTime.style.borderColor = "#ff0000";
		document.createEvent.eventTime.focus();
		return false;
	}
	else {
		document.createEvent.eventTime.style.borderColor = "green";
		var message4 = document.getElementById("message4");
		message4.innerHTML = "<font color='Green'>OK</font>";
	}

	//Cost Validation
	if (cost == "")
	{
		var message5 = document.getElementById("message5");
	    message5.innerHTML = "<font color='red'>Sila Isi Kos Aktiviti</font>";
		document.createEvent.eventCost.style.borderColor = "#ff0000";
		document.createEvent.eventCost.focus();
		return false;
	}
	else {
		document.createEvent.eventCost.style.borderColor = "green";
		var message5 = document.getElementById("message5");
		message5.innerHTML = "<font color='Green'>OK</font>";
	}	
	//Type Validation
	if (type == "")
	{
		var message6 = document.getElementById("message6");
	    message6.innerHTML = "<font color='red'>Sila Pilih Jenis Aktiviti</font>";
		document.createEvent.eventType.style.borderColor = "#ff0000";
		document.createEvent.eventType.focus();
		return false;
	}
	else {
		document.createEvent.eventType.style.borderColor = "green";
		var message6 = document.getElementById("message6");
		message6.innerHTML = "<font color='Green'>OK</font>";
	}
	
	//Place Validation	
	if (place == "")
	{
		var message7 = document.getElementById("message7");
	    message7.innerHTML = "<font color='red'>Sila Isi Tempat Aktiviti</font>";
		document.createEvent.eventVenue.style.borderColor = "#ff0000";
		document.createEvent.eventVenue.focus();
		return false;
	}
	else {
		document.createEvent.eventVenue.style.borderColor = "green";
		var message7 = document.getElementById("message7");
		message7.innerHTML = "<font color='Green'>OK</font>";
	}
	
	//Description Validation	
	if (desc == "")
	{
		var message8 = document.getElementById("message8");
	    message8.innerHTML = "<font color='red'>Sila Isi Keterangan atau Butiran Aktiviti</font>";
		document.createEvent.eventDescription.style.borderColor = "#ff0000";
		document.createEvent.eventDescription.focus();
		return false;
	}
	else {
		document.createEvent.eventDescription.style.borderColor = "green";
		var message8 = document.getElementById("message8");
		message8.innerHTML = "<font color='Green'>OK</font>";
	}
	
	/* OLD VALIDATION
	//Name Validation
	if (name == "")
		{
			alert("Sila Isi Nama Aktiviti");
			document.createEvent.eventName.style.background="yellow";
			document.createEvent.eventName.focus();
			return false;
			
		}
	else {document.createEvent.eventName.style.background="";}
	
	
	//Committee Involved Validation
	if (comm == "")
	{
		alert("Sila Isi Nama Tenaga Pengerak");
		document.createEvent.eventCommInvolved.style.background="yellow";
		document.createEvent.eventCommInvolved.focus();
		return false;
		
	}
else {document.createEvent.eventCommInvolved.style.background="";}
	
	//Date Validation
	
	if (date == "")
	{
	alert("Sila pilih Tarikh Aktiviti");
	document.createEvent.eventDate.style.background="yellow";
	document.createEvent.eventDate.focus();
	return false;
	}
else {document.createEvent.eventDate.style.background="";}

//Time Session Validation
	if (session == "")
	{
	alert("Sila pilih Sesi Aktiviti");
	document.createEvent.eventTime.style.background="yellow";
	document.createEvent.eventTime.focus();
	return false;
	}
else {document.createEvent.eventTime.style.background="";}

//Cost Validation
	if (cost == "")
	{
	alert("Sila isi kos Aktiviti");
	document.createEvent.eventCost.style.background="yellow";
	document.createEvent.eventCost.focus();
	return false;
	}
else {document.createEvent.eventCost.style.background="";}
//Type Validation
	if (type == "")
	{
	alert("Sila pilih Jenis Aktiviti");
	document.createEvent.eventType.style.background="yellow";
	document.createEvent.eventType.focus();
	return false;
	}
else {document.createEvent.eventType.style.background="";}
//Place Validation	
	if (place == "")
	{
	alert("Sila pilih Sesi Aktiviti");
	document.createEvent.eventVenue.style.background="yellow";
	document.createEvent.eventVenue.focus();
	return false;
	}
else {document.createEvent.eventVenue.style.background="";}
//Description Validation	
	if (desc == "")
	{
	alert("Sila pilih Sesi Aktiviti");
	document.createEvent.eventDescription.style.background="yellow";
	document.createEvent.eventDescription.focus();
	return false;
	}
else {document.createEvent.eventDescription.style.background="";}
	
*/
	
}</script>
</body>

</html>
