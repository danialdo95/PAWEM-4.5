<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.pawem.model.Dropout,com.pawem.model.Active, java.util.List,java.io.PrintWriter" %>
 <%	
	/*
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires",0);
	*/
	 if(session==null){
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

    <title>Senarai Ahli</title>

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
                            Senarai Ahli
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>No. Kad Pengenalan</th>
                                            <th>Nama</th>
                                            <th>No. Keahlian</th>
                                            <th>Tarikh Keahlian</th>
                                            <th colspan="3" style="text-align:center">Tindakan</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<% 
                                    		List<Active> active =(List<Active>)request.getAttribute("activeList");
                                    		List<Dropout> dropout =(List<Dropout>)request.getAttribute("dropoutList");
                                    		for(int i=0; i < active.size() ; i++){
                                    			int count = i;
                                    			count++;
                                    	%>
                                    	
                                        <tr>
                                            <td><% out.print(count); %></td>
                                            <td><% out.print( active.get(i).getMemIC()); %></td>
                                            <td><% out.print( active.get(i).getMemName()); %></td>
                                            <td><% out.print( active.get(i).getMembershipNum()); %></td>
                                            <td><% out.print( active.get(i).getMembershipDate()); %></td>
                                            <td align="center">
                                            	<a href="/PAWEM/member?action=viewMemberInfo&&memID=<% out.print( active.get(i).getMemID()); %>&&type=detail" class="label label-info" >
                                            		<i class="fa fa-info-circle"></i> Info
                                            	</a>
                                            </td>
                                            <% if(staffRole.equals("Penyelaras")){ %>
                                            <td align="center">
                                            	<a href="/PAWEM/member?action=viewMemberInfo&&memID=<% out.print( active.get(i).getMemID()); %>&&type=update" class="label label-warning">
                                        		<i class="fa fa-edit"></i> Kemaskini </a>
                                            </td>
                                            <td align="center">
                                             	<div class="<% out.print( active.get(i).getMemID()); %>" style="display:inline;">
                                             		 <a href="#" onclick="assignID(<% out.print( active.get(i).getMemID()); %>)" data-toggle="modal" data-target="#dropout" class="label label-default">
					                          		<i class="fa fa-trash-o"></i> Tidak Aktif </a>
                                             	</div>
					                         	<div class="<% out.print( active.get(i).getMemID()); %>" style="display:none;">
					                         		<a href="#" onclick="javascript:if(confirm('Adakah anda pasti?')){window.location='/PAWEM/member?action=deleteMemberInfo&&memID=<% out.print( active.get(i).getMemID()); %>';}" class="label label-danger">
					                            	<i class="fa fa-trash-o"></i> Hapus </a>
					                         	</div>
                                            </td>   
                                            <% } %>
                                            </tr>   
                                        <% } %>
                                        <%
                                        	if(dropout != null){
	                                        	for(int outer = 0; outer < active.size() ; outer++){
	                                        		for(int inner = 0; inner < dropout.size(); inner++){
	                                        			if(active.get(outer).getMemID() == dropout.get(inner).getMemID()){
	                                        				out.print("<script type=\"text/javascript\">");
	                                        				out.print("document.getElementsByClassName('"+ dropout.get(inner).getMemID() +"')[0].style.display='none';");
	                                        				out.print("document.getElementsByClassName('"+ dropout.get(inner).getMemID() +"')[1].style.display='inline';");
	                                        				out.print("</script>");
	                                        			}
	                                        		}
	                                        	}
                                        	}
                                        %>
                                    </tbody>
                                </table>
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
 <script>
 	function assignID(id){
 		document.getElementById("id").value=id;
 		//document.getElementsByClassName("id")[0].style.display='inline';
 	}
 </script>
 <div id="dropout" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Sila nyatakan sebab tidak aktif:</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-xs-12">
            <form name="dropoutMember" action="member" method="get">
            <select name="dropoutReason" class="form-control">
              <option value="Meninggal Dunia">Meninggal Dunia</option>
              <option value="Keluar Persatuan">Keluar Persatuan</option>
              <option value="Sakit">Sakit</option>
            </select>
            <input name="memID" value="" id="id" type="hidden" />
            <input name="action" value="dropoutMember" type="hidden" />
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <input type="submit" class="btn btn-default" value="Submit">
      </form>
      </div>
    </div>

  </div>
</div>
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