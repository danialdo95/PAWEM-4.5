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
                                <h3 class="panel-title">Laporan Aktiviti</h3>
                            </div>
                            <br>
                            <table style="text-align:center; width:90%; ">
                            <tr>
                            	<td><label>Nama Aktiviti : </label> <%= request.getAttribute("eventName") %></td>
                            	<td><label>Tarikh Aktiviti : </label> <%= request.getAttribute("eventDate") %></td>
                            	<td><label>Sesi Aktiviti : </label> <%= request.getAttribute("eventTime") %></td>
                            </tr>
                            </table>
                        	<br>
                        <form role="form" action="event" method="post">
						<div class="panel panel-default">
                            <div class="panel-body">
                                 <div class="row">
                                  <%
	                                List<Member> active = (List<Member>) request.getAttribute("active");
                                	List<Member> members = (List<Member>) request.getAttribute("members");
	                           		if(members.size()>0){
                                %>
                            <div class="panel panel-yellow">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Senarai Ahli Terlibat </h3>
                                </div>
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <div id="dynamicInput">
                                                <label>Ahli Terlibat : </label>
                                                <div class="form-group input-group col-lg-3 "> 
                                                       <%	
                                                       		for(int i=0; i < active.size(); i++){  
		                                                    	    %>
		                                                    		  <input type="checkbox" value="<%= active.get(i).getMemID() %>" id="<%= active.get(i).getMemID() %>"  name="members"> <%= active.get(i).getMemName() %>  (<%= active.get(i).getMemIC() %>)<br>
		                                                    	  <% 		                                                    	   
		                                                       	
		                                                       }
						                                	
						                                	%>
						                                	<script>
						                                	
						                                		<%
							                                		for(int i=0; i < active.size(); i++){
							                                			for(int j=0; j<members.size(); j++){ 
																			if(active.get(i).getMemID() == members.get(j).getMemID()){ %> 
																				document.getElementById(<%= active.get(i).getMemID() %>).checked = true;
																			 
																			<% } 
																			}
																		}
						                                		%>
						                                	
						                                	</script>
                                                </div>
                                            </div> 
                                        </div>
                                    </div>
                        </div>  
                        <% } else{%>
                            <div class="panel panel-yellow">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Senarai Ahli Terlibat </h3>
                                </div>
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <div id="dynamicInput">
                                                <label>Ahli Terlibat : </label>
                                                <div class="form-group input-group col-lg-3 "> 
                                                       <%	
		                                                       for(int i=0; i < active.size(); i++){
		                                                    	   out.print("<input type=\"checkbox\" value=\""+ active.get(i).getMemID()+"\" name=\"members\"> "+active.get(i).getMemName() +" ("+active.get(i).getMemIC()+")<br>");
		                                                       	
		                                                       }
						                                	
						                                	%>
                                                </div>
                                            </div> 
                                        </div>
                                    </div>
                                    <% } %>
                        </div>   
                            
                            
                            <input type="hidden" name="eventID" value="<%= request.getAttribute("eventID") %>" />
                            <input type="hidden" name="action" value="attendance" />
						<input type="submit" name="submit" class="btn btn-default" value="Hantar Borang" />
                        <input type="button" onclick="javascript:history.go(-1)" class="btn btn-default" value ="Kembali"/>
                       </div>
                       </div>
						</form>	
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
        function addTentative(divName2)
            {
                var newdiv = document.createElement('div');
                newdiv.innerHTML ='<div class="row"><div class="col-lg-3 "><div class="panel panel-default"><div class="panel-body"><label>Mula : </label><input type="time" name ="tent_startTime[]" class="form-control" value="" required></br></div></div></div><div class="col-lg-3 "><div class="panel panel-default"> <div class="panel-body"><label>Tamat : </label><input type="time" name ="tent_endTime[]" class="form-control" value="" required></br></div></div></div><div class="col-lg-6 "><div class="panel panel-default"> <div class="panel-body"><label>Diskripsi : </label><input type ="text" name="tent_name[]"  class="form-control" value="" required></br></div></div></div></div>';
                document.getElementById(divName2).appendChild(newdiv);
            }    
    </script>
</body>

</html>
