<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%
 response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
 response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
 response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
 response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
 String user = (String) session.getAttribute("staffName");
 if (null == user) {
    request.setAttribute("Error", "Session has ended.  Please login.");
    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
    rd.forward(request, response);
 }
 %>
<%
	String staffIC = (String) session.getAttribute("staffIC");
	String staffName = (String) session.getAttribute("staffName");
	String staffRole = (String) session.getAttribute("staffRole");
%>
<!-- Navigation -->
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">PAWEM</a>
            </div>
            <!-- /.navbar-header -->
						 
            <ul class="nav navbar-top-links navbar-right">
            	<li>Selamat Datang <%= staffName%></li>
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="/PAWEM/staff?action=viewStaffInfo&&IC=<%= staffIC %>"><i class="fa fa-gear fa-fw"></i> Tetapan Profil</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="/PAWEM/staff?action=logout"><i class="fa fa-sign-out fa-fw"></i> Log Keluar</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="member?action=home"><i class="fa fa-dashboard fa-fw"></i> Halaman Utama</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-users"></i> Ahli <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                            	<% if(staffRole.equals("Penyelaras")){ %>
                                <li>
                                    <a href="createMemberForm.jsp">Tambah Ahli</a>
                                </li>
                                <% } %>
                                <li>
                                    <a href='/PAWEM/member?action=viewMemberList&&type=list'>Senarai Ahli</a>
                                </li>
                                
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
						<li>
                            <a href="#"><i class="fa fa-book"></i> Aktiviti <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                            <% if(staffRole.equals("Penyelaras")){ %>
                                <li>
                                    <a href="createEventForm.jsp">Tambah Aktiviti</a>
                                </li>
                            <% } %>
                                <li>
                                    <a href='/PAWEM/event?action=viewEventList&&type=list'>Senarai Aktiviti</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <% if(staffRole.equals("Penyelaras")){ %>
						<li>
                            <a href="#"><i class="fa fa-briefcase"></i> Staf <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="createStaffForm.jsp">Tambah Staf</a>
                                </li>
                                <li>
                                    <a href='/PAWEM/staff?action=viewStaffList'>Senarai Staf</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>    
                        <li>
	                        <a href="reportForm.jsp"><i class="fa fa-file-text"></i> Laporan Bulanan </a>
	                    </li>
                        <% } %>               
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>