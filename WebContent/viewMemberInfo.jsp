<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "com.pawem.model.Event,java.util.List,java.io.PrintWriter"%>

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
                            Maklumat Ahli
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i> <a href="member?action=home">Halaman Utama</a>
                            </li>
                            <li>
                                <i class="fa fa-file"></i> <a href='/PAWEM/member?action=viewMemberList&&type=list'>Senarai Ahli</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file"></i> Maklumat Ahli
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
                                    <th style="background-color:#f2f2f2;">Name</th>
                                    <td><%= request.getAttribute("memName") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">No. K/P</th>
                                    <td><%= request.getAttribute("memIC") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Jantina</th>
                                    <td><%= request.getAttribute("memGender") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Kaum</th>
                                    <td><%= request.getAttribute("memRace") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Alamat Rumah</th>
                                    <td><%= request.getAttribute("memAddress") %></td>
                                </tr>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Status Perkahwinan</th>
                                    <td><%= request.getAttribute("memMarriageStatus") %></td>
                                </tr>  
                                <% if(request.getAttribute("memMarriageStatus").equals("Berkahwin")){ %>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Nama Pasangan</th>
                                    <td><%= request.getAttribute("memSpouseName") %></td>
                                </tr>
                                <% } %>        
                                <tr>
                                    <th style="background-color:#f2f2f2;">Tinggal Bersama</th>
                                    <td><%= request.getAttribute("memLivingWith" ) %></td>
                                </tr>          
                                <% if(request.getAttribute("memHomeNo") != null){ %>
                                <tr>
                                    <th style="background-color:#f2f2f2;">No Telefon Rumah</th>
                                    <td><%= request.getAttribute("memHomeNo") %></td>
                                </tr>   
                                <% } %>
                                <% if(request.getAttribute("memMobileNo") != null){ %>
                                <tr>
                                    <th style="background-color:#f2f2f2;">No Telefon Bimbit</th>
                                    <td><%= request.getAttribute("memMobileNo") %></td>
                                </tr>  
                                <% } %>     
                                <tr>
                                    <th style="background-color:#f2f2f2;">Kelulusan Akademik</th>
                                    <td><%= request.getAttribute("memAcademic") %></td>
                                </tr>  
                                <tr>
                                    <th style="background-color:#f2f2f2;">Pekerjaan Sekarang</th>
                                    <td><%= request.getAttribute("memCurrentJob") %></td>
                                </tr>
                                <% if(request.getAttribute("memCurrentJob").equals("Bersara")){ %>
                                    <tr>
                                        <th style="background-color:#f2f2f2;">Pekerjaan Terakhir</th>
                                        <td><%= request.getAttribute("memLastJob") %></td>
                                    </tr>
                                    <tr>
                                        <th style="background-color:#f2f2f2;">Tempat Pekerjaan Terakhir</th>
                                        <td><%= request.getAttribute("memLastWorkplace") %></td>
                                    </tr>
                                <% } %>
                                <% if(request.getAttribute("membershipNum") != null){ %>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Bil Keahlian</th>
                                    <td><%= request.getAttribute("membershipNum") %></td>
                                </tr> 
                                <tr>
                                    <th style="background-color:#f2f2f2;">Tarikh Keahlian</th>
                                    <td><%= request.getAttribute("membershipDate") %></td>
                                </tr> 
                                 <% } %>
                                 <% 
                                 
                                 	if(request.getAttribute("dropoutDate") != null){ %>
                                <!--  buat if dropout -->
                                <tr>
                                    <th style="background-color:#f2f2f2;">Tarikh dropout</th>
                                    <td><%=  request.getAttribute("dropoutDate") %></td>
                                </tr> 
                                <tr>
                                    <th style="background-color:#f2f2f2;">Sebab dropout</th>
                                    <td><%= request.getAttribute("dropoutReason") %></td>
                                </tr>  
                                <% } %>
                                <tr>
                                    <th style="background-color:#f2f2f2;">Aktiviti Terlibat:</th>
                                    <td>
                                    	<% 
                                    		List<Event> list = (List<Event>) request.getAttribute("eventList");
                                    		if(list.size()>0){
                                    			out.print("<ul>");
                                    		for(int i=0; i < list.size(); i++){
                                    			out.print("<li>"+list.get(i).getEventName() + " - Tarikh: " + list.get(i).getEventDate()  + " - Sesi: " + list.get(i).getEventTime()  + "</li>");
                                    		}
                                    		out.print("</ul>");
                                    			}else{
                                    				out.print("Tiada");
                                    			}%>
                                    		
                                    </td>
                                </tr>     
                                <tr>
                                    <td colspan="2" align="center">
                                    <% if(request.getAttribute("from").equals("detail")){ %>
                                        <a href="#" onclick="javascript:history.go(-1)" class="btn btn-default">
                                        <i class="fa fa-check"></i> Kembali </a> &nbsp;&nbsp;
                                    <% }else if(request.getAttribute("from").equals("createForm") || request.getAttribute("from").equals("updateForm")){ %>
                                    
                                    	<a href="/PAWEM/member?action=viewMemberInfo&&memID=<%= request.getAttribute("memID") %>&&type=update" class="btn btn-default">
                                        <i class="fa fa-edit"></i> Kemaskini </a> &nbsp;&nbsp;
                                        <a href='/PAWEM/member?action=viewMemberList&&type=list' class="btn btn-default">
                                        <i class="fa fa-list"></i> Seterusnya </a> &nbsp;&nbsp;
                                        <% } %>
                                    </td>
                                </tr>
                            </table>
                        </div>
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
    