<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.PrintWriter" %>
  <%	
	if(session.getAttribute("staffRole").toString().equalsIgnoreCase("Pengerusi")){
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

    <title>Member Form</title>

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
        <br><br>
         
        <div id="page-wrapper">
            <div class="container-fluid">
                    <!-- /.col-lg-12 -->
                   <br>
                    <div class="row">
                    <h1 class="page-header">
                            Borang Kemaskini Ahli <small> </small>
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="member?action=home">Halaman Utama</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-folder"></i> <a href='/PAWEM/member?action=viewMemberList&&type=list'>Senarai Ahli</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file"></i> Borang Ahli
                            </li>
                        </ol>
                    
				<div class="panel panel-yellow">
                            <div class="panel-heading">
                                <h3 class="panel-title">Borang Kemaskini Ahli</h3>
                            </div>
							
                        <form role="form" name="createMemberForm" action="member" onsubmit="return validate()" method="post">
						<div class="panel panel-default"><br/>
                            <div class="panel-body">
                            	<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Nama : </label>
                                        <div class="col-md-10">
                                        	<input class="form-control" name="nama" value="<%= request.getAttribute("memName") %>" >
                                       		<p id="message" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">No K/P [Baru] :</label>
                                        <div class="col-md-10">
                                        	<input class="form-control" name="kp" value="<%= request.getAttribute("memIC") %>" />
                                       		<p id="message2" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Jantina : </label>
                                        <div class="col-md-4">
                                        	<select class="form-control" name="gender" >
                              				 	<option value="" >-- Pilih --</option>	
                                   				<option value="Lelaki" <% if(request.getAttribute("memGender").toString().equalsIgnoreCase("Lelaki")){ out.print("Selected");} %>>Lelaki</option>
												<option value="Perempuan" <% if(request.getAttribute("memGender").toString().equalsIgnoreCase("Perempuan")){ out.print("Selected");} %>>Perempuan</option>
											</select>
											<p id="message3" ></p>
                                        </div>
                                        <label class="col-md-2">Kaum : </label>
                                        <div class="col-md-4">
                                        	<select class="form-control" name="race" >
                              				 	     <option value="">-- Pilih --</option>	
                                   					  <option value="Melayu" <% if(request.getAttribute("memRace").toString().equalsIgnoreCase("Melayu")){ out.print("Selected");} %>>Melayu</option>
													  <option value="Cina" <% if(request.getAttribute("memRace").toString().equalsIgnoreCase("Cina")){ out.print("Selected");} %>>Cina</option>
													  <option value="India" <% if(request.getAttribute("memRace").toString().equalsIgnoreCase("India")){ out.print("Selected");} %>>India</option>
													  <option value="Lain-Lain" <% if(request.getAttribute("memRace").toString().equalsIgnoreCase("Lain-Lain")){ out.print("Selected");} %>>Lain-Lain</option>
													  </select>
													  <p id="message4" ></p>
                                        </div>
                                        
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Taraf Perkahwinan: </label>
                                        <div class="col-md-4">
                                        	<select class="form-control" onchange="showfield2(this.options[this.selectedIndex].value)" name="marriageStat" >
                              				 	    <option value="">-- Pilih --</option>	
                                                    <option value="Bujang" <% if(request.getAttribute("memMarriageStatus").toString().equalsIgnoreCase("Bujang")){ out.print("selected");} %>>Bujang</option>
                									<option value="Berkahwin" <% if(request.getAttribute("memMarriageStatus").toString().equalsIgnoreCase("Berkahwin")){ out.print("selected");} %>>Berkahwin</option>
                								</select>
                								<p id="message5" ></p>
                                        </div>
                                        <% if(request.getAttribute("memMarriageStatus").toString().equalsIgnoreCase("Berkahwin")){  %>
		            						<div id="div2">
				            					<label class="col-md-2">Nama Pasangan: </label>
		                                        <div class="col-md-4">
			            							<input class="form-control" name="spouseName" value="<%= request.getAttribute("memSpouseName") %>"><br/>
			            							<p id="message6" ></p>
			            						</div>
		            						</div>
		            					<%	} else{%>
		            						<div id="div2"></div>
		            					<% } %>
                                    </div>
                                </div>
                                <br/>
            					<script type="text/javascript">
                                  function showfield2(name){
                                	 
                                       if(name=='Berkahwin')
                                        {
                                            document.getElementById('div2').innerHTML='<label class="col-md-2">Nama Pasangan: </label><div class="col-md-4"><input class="form-control" name="spouseName" value=""><p id="message6" ></p></div></br>';
                                        }else{
                                        	document.getElementById('div2').innerHTML='<input class="form-control" name="spouseName" value="" style="display:none;"></br>';
                                        }
                                    }
								   </script>
								 <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Alamat : </label>
                                        <div class="col-md-10">
                                        	<textarea class="form-control" name="address"  ><%= request.getAttribute("memAddress") %></textarea>
                                        	<p id="message7" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Tinggal Bersama : </label>
                                        <div class="col-md-10">
                                        	<select class="form-control" name="living" >
												<option value="">-- Pilih --</option>	
				                                    <option value="Keluarga" <% if(request.getAttribute("memLivingWith" ).toString().equalsIgnoreCase("Keluarga")){ out.print("Selected");} %>>Keluarga</option>
													<option value="Anak-Anak" <% if(request.getAttribute("memLivingWith" ).toString().equalsIgnoreCase("Anak-Anak")){ out.print("Selected");} %>>Anak-Anak</option>
													<option value="Sendiri" <% if(request.getAttribute("memLivingWith" ).toString().equalsIgnoreCase("Sendiri")){ out.print("Selected");} %>>Sendiri</option>
												</select>
												<p id="message8" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">No Telefon Rumah : </label>
                                        <div class="col-md-4">
                                        	<input type="text" class="form-control" <% if(request.getAttribute("memHomeNo") != null){out.print("value='"+request.getAttribute("memHomeNo")+"'");}else{out.print("value=''");} %> name="homeNo">
                                        	<p id="message9" ></p>
                                        </div>
                                        <label class="col-md-2">No Telefon Bimbit :  </label>
                                        <div class="col-md-4">
                                        	<input type="text" class="form-control" <% if(request.getAttribute("memMobileNo") != null){out.print("value='"+request.getAttribute("memMobileNo")+"'");}else{out.print("value=''");} %> name="mobileNo">
                                        	<p id="message10" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Kelulusan Akademik : </label>
                                        <div class="col-md-10">
                                        	<select class="form-control" name="academic" >
		                                            <option value="">-- Pilih --</option>	 
		                                            <option value="UPSR" <% if(request.getAttribute("memAcademic" ).toString().equalsIgnoreCase("UPSR")){ out.print("Selected");} %>>UPSR</option>
		                                            <option value="PMR" <% if(request.getAttribute("memAcademic" ).toString().equalsIgnoreCase("PMR")){ out.print("Selected");} %>>PMR</option>
		                                            <option value="SPM" <% if(request.getAttribute("memAcademic" ).toString().equalsIgnoreCase("SPM")){ out.print("Selected");} %>>SPM</option>
		                                            <option value="Pengajian Tinggi" <% if(request.getAttribute("memAcademic" ).toString().equalsIgnoreCase("Pengajian Tinggi")){ out.print("Selected");} %>>Pengajian Tinggi</option>
		                                            <option value="Tiada" <% if(request.getAttribute("memAcademic" ).toString().equalsIgnoreCase("Tiada")){ out.print("Selected");} %>>Tiada</option>
		                                        </select>
		                                        <p id="message11" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Pekerjaan Sekarang : </label>
                                        <div class="col-md-10">
                                        	<select class="form-control"  onchange="showfield(this.options[this.selectedIndex].value)" name="job" >
		                                            <option>-- Pilih --</option>	 
		                                            <option value="Cikgu" <% if(request.getAttribute("memCurrentJob" ).toString().equalsIgnoreCase("Cikgu")){ out.print("Selected");} %>>Cikgu</option>
		                                            <option value="Kerani" <% if(request.getAttribute("memCurrentJob" ).toString().equalsIgnoreCase("Kerani")){ out.print("Selected");} %>>Kerani</option>
		                                            <option value="Polis" <% if(request.getAttribute("memCurrentJob" ).toString().equalsIgnoreCase("Polis")){ out.print("Selected");} %>>Polis</option>
		                                            <option value="Peguam" <% if(request.getAttribute("memCurrentJob" ).toString().equalsIgnoreCase("Peguam")){ out.print("Selected");} %>>Peguam</option>
		                                            <option value="Bersara" <% if(request.getAttribute("memCurrentJob" ).toString().equalsIgnoreCase("Bersara")){ out.print("Selected");} %>>Bersara</option>
		                                            <option value="Lain-lain" <% if(!((request.getAttribute("memCurrentJob").toString().equalsIgnoreCase("Bersara")) || (request.getAttribute("memCurrentJob").toString().equalsIgnoreCase("Cikgu")) || (request.getAttribute("memCurrentJob").toString().equalsIgnoreCase("Kerani")) || (request.getAttribute("memCurrentJob").toString().equalsIgnoreCase("Polis")) ||(request.getAttribute("memCurrentJob").toString().equalsIgnoreCase("Peguan")) )){ out.print("Selected");} %>>Lain-lain</option>
		                                        </select>
		                                        <p id="message12" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <%
									String memCurrentJob = request.getAttribute("memCurrentJob" ).toString();
									if(memCurrentJob.equalsIgnoreCase("Bersara")){
								%>
                                	<div id="div1">
                                		<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Pekerjaan Terakhir : </label>
                                        <div class="col-md-10">
                                			<input class="form-control" name="lastJob" value="<%= request.getAttribute("memLastJob") %>" placeholder=""><br>
                                			 <p id="message13" ></p>
                                			 </div>
	                                    </div>
	                                </div>
	                                <br/>	
                                		<div id="div1">
                                		<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Tempat Pekerjaan Terakhir : </label>
                                			<div class="col-md-10">
                                				<input class="form-control" name="lastWorkplace" value="<%= request.getAttribute("memLastWorkplace") %>" placeholder=""><br>
                                				<p id="message12" ></p>
                                				</div>
	                                    </div>
	                                </div>
	                                <br/>	
                                	</div>
                                <% }else if(!((memCurrentJob.equalsIgnoreCase("Bersara")) || (memCurrentJob.equalsIgnoreCase("Cikgu")) || (memCurrentJob.equalsIgnoreCase("Kerani")) || (memCurrentJob.equalsIgnoreCase("Polis")) ||(memCurrentJob.equalsIgnoreCase("Peguan")) )){ %>
                                	
                                	<div id="div1">
                                	<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Sila Nyatakan Pekerjaan : </label>
                                			<div class="col-md-10">
                                		<input class="form-control" name="lainlain" value="<%= request.getAttribute("memCurrentJob") %>" placeholder="Sila masukkan pekerjaan anda">
                                		<p id="message15" ></p>
                                		</div>
	                                    </div>
	                                </div>
	                                <br/>	
                                	</div>
                                <% } else{ %>
                                	<div id="div1"></div>
                                <% } %>
                            <script type="text/javascript">
                                  function showfield(name){
                                       if(name=='Bersara')
                                        {
                                            document.getElementById('div1').innerHTML='<div class="row"><div class="form-group"><label class="col-md-2">Pekerjaan Terakhir : </label><div class="col-md-10"><input class="form-control" value="" name="lastJob" placeholder=""><p id="message13" ></p></div></div></div><br/><div class="row"><div class="form-group"><label class="col-md-2">Tempat Pekerjaan Terakhir: </label><div class="col-md-10"><input class="form-control" value="" name="lastWorkplace" placeholder="" ><p id="message14" ></p><br></div></div></div>';
                                        }
                                        else if(name=='Lain-lain')
                                        {
                                            document.getElementById('div1').innerHTML='<div class="row"><div class="form-group"><label class="col-md-2">Sila Nyatakan Pekerjaan : </label><div class="col-md-10"><input class="form-control" name="lainlain" placeholder="Sila masukkan pekerjaan anda"><p id="message15" ></p></div></div></div><input name="lastJob" value="" readonly style="display:none;"><input  name="lastWorkplace" value="" style="display:none"> ';
                                        }
                                      else
                                      {
                                          document.getElementById('div1').innerHTML='<input name="lastJob" value="" readonly style="display:none"><input name="lastWorkplace" value="" readonly style="display:none">';
                                      }
                                    }
								   </script>
								
                                <% if(request.getAttribute("dropoutReason" ) != null){%>
							 <div id="div1">
                                	<div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Sebab Tarik Diri: </label>
                                			<div class="col-md-10">
                                                <select name="dropoutReason" class="form-control">
									              <option value="Meninggal Dunia" <% if(request.getAttribute("dropoutReason" ).toString().equalsIgnoreCase("Meninggal Dunia")){ out.print("Selected");} %>>Meninggal Dunia</option>
									              <option value="Keluar Persatuan" <% if(request.getAttribute("dropoutReason" ).toString().equalsIgnoreCase("Keluar Persatuan")){ out.print("Selected");} %>>Keluar Persatuan</option>
									              <option value="Sakit" <% if(request.getAttribute("dropoutReason" ).toString().equalsIgnoreCase("Sakit")){ out.print("Selected");} %>>Sakit</option>
									            </select>   
									            <input type="hidden" name="dropout" value="yes" />
									          </div>
	                                    </div>
	                                </div>
	                                <br/>	
                                    <% } %>
                                    <input type="hidden" name="dropout" value="no" />
                             </div>
                             
                            <input type="hidden" name="action" value="updateMemberInfo" />
                            <input type="hidden" name="memID" value="<%= request.getAttribute("memID") %>" />
                            <input type="submit" name="submit" class="btn btn-default" value="Hantar Borang"/>
                            <input type="button" onclick="javascript:history.go(-1)" class="btn btn-default" value ="Kembali"/>
							</div></div> 	
						</form>	
            </div>
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
	<script>
	function validate()
	{	
		var v = "createMemberForm";
		
		/*
		var name = document.forms[v]["nama"].value;
		var ic = document.forms[v]["kp"].value;
		var gender = document.forms[v]["gender"].value;
		var marry = document.forms[v]["marriageStat"].value;
		var alamat = document.forms[v]["address"].value;
		var living =  document.forms[v]["living"].value;
		var academic = document.forms[v]["academic"].value;
		var job = document.forms[v]["job"].value;
		*/
		
		var name = document.forms[v]["nama"].value;
    	var ic = document.forms[v]["kp"].value;
    	var gender = document.forms[v]["gender"].value;
    	var race = document.forms[v]["race"].value;
    	var marry = document.forms[v]["marriageStat"].value;
    	var alamat = document.forms[v]["address"].value;
    	var living =  document.forms[v]["living"].value;
    	var homeNo = document.forms[v]["homeNo"].value;
    	var mobileNo = document.forms[v]["mobileNo"].value;
    	var academic = document.forms[v]["academic"].value;
    	var job = document.forms[v]["job"].value;
		
		
    	//Name Validation
    	if (name == "")
    		{
    		    var message = document.getElementById("message");
    		    message.innerHTML = "<font color='red'>Ruangan Nama Ahli tidak di-isi.</font>";
    			document.createMemberForm.nama.style.borderColor = "#ff0000";
    			document.createMemberForm.nama.focus();
    			return false;
    			
    		}
    	
    	  	
    	//!/^[a-zA-Z]*$/g.test(name) == true
    	
    	else if (!/^[A-Za-z\s]+$/g.test(document.createMemberForm.nama.value)) 
		{
    	
    		var message = document.getElementById("message");
		    message.innerHTML = "<font color='red'>Sila isi ( Huruf sahaja )</font>";
		    document.createMemberForm.nama.style.borderColor = "#ff0000";
			document.createMemberForm.nama.focus();
			return false;
			
		}
  
    	
    	else {
    		
    		document.createMemberForm.nama.style.borderColor = "green";
    		var message = document.getElementById("message");
 		    message.innerHTML = "<font color='Green'>OK</font>";
    	
    	}
    	
    	
    	//IC Validation
    	if (ic == "" )
    		{
    		var message2 = document.getElementById("message2");
		    message2.innerHTML = "<font color='red'>Ruangan No. K/P tidak di-isi. Sila isi ( contoh : 950123149087 ).</font>";
			document.createMemberForm.kp.style.borderColor = "#ff0000";
    		document.createMemberForm.kp.focus();
    		return false;
    		}
    	  	
    	else if (!/^[0-9]+$/.test(ic) == true )
			{
    		var message2 = document.getElementById("message2");
    		message2.innerHTML = "<font color='red'>No. K/P perlu diisi dengan nombor sahaja ( 12 nombor ). Sila isi semula( contoh : 950123149087 ).</font>";
			document.createMemberForm.kp.style.borderColor = "#ff0000";
			document.createMemberForm.kp.focus();
			return false;
			}
    	
    	else if (ic.length != 12 )
		{
			var message2 = document.getElementById("message2");
			message2.innerHTML = "<font color='red'>No. K/P perlu diisi dengan nombor sahaja ( 12 nombor ). Sila isi semula( contoh : 950123149087 ).</font>";
			document.createMemberForm.kp.style.borderColor = "#ff0000";
			document.createMemberForm.kp.focus();
		return false;
		}
    	
    	
    	else {document.createMemberForm.kp.style.borderColor = "green";
    			var message2 = document.getElementById("message2");
		    	message2.innerHTML = "<font color='Green'>OK</font>";}
    
    	//gender Validation
    	if (gender == "")
    	{
    		var message3 = document.getElementById("message3");
        	message3.innerHTML = "<font color='red'>Sila pilih Jantina Ahli.</font>";
        	document.createMemberForm.gender.style.borderColor = "#ff0000";
    		document.createMemberForm.gender.focus();
    		return false;
    		
    	}
    	
    	else {
    		document.createMemberForm.gender.style.borderColor = "green";
    		var message3 = document.getElementById("message3");
		     message3.innerHTML = "<font color='Green'>OK</font>";
		     }
    	
    	
    	//Race Validation
    	
    	if (race == "")
    	{	
    		var message4 = document.getElementById("message4");
        	message4.innerHTML = "<font color='red'>Sila pilih Kaum.</font>";
        	document.createMemberForm.race.style.borderColor = "#ff0000";
    		document.createMemberForm.race.focus();
    		return false;
    	
    	}
    	else {
    		document.createMemberForm.race.style.borderColor = "green";
    		var message4 = document.getElementById("message4");
		     message4.innerHTML = "<font color='Green'>OK</font>";
		     }
    	
    	
    	//Marriage Validation
    	
    	if (marry == "")
    	{	
    		var message5 = document.getElementById("message5");
        	message5.innerHTML = "<font color='red'>Sila pilih Taraf Perkahwinan Ahli.</font>";
        	document.createMemberForm.marriageStat.style.borderColor = "#ff0000";
    		document.createMemberForm.marriageStat.focus();
    		return false;
    	}
    	
    	else if (marry == "Berkahwin"){
    		
    	
    			var spouseName = document.forms[v]["spouseName"].value;
			
		   			if (spouseName == "") 
		    		{
		    			var message6 = document.getElementById("message6");
	        			message6.innerHTML = "<font color='red'>Sila isi nama pasangan.</font>";
	        			document.createMemberForm.spouseName.style.borderColor = "#ff0000";
	    				document.createMemberForm.spouseName.focus();
	    				return false;
		    		}
		   			
		   			else if (!/^[A-Za-z\s]+$/g.test(document.createMemberForm.spouseName.value)) 
		   		    	//( !/^[0-9]+$/.test(name) == false)
		   				{
		   		    	
			   				var message6 = document.getElementById("message6");
		        			message6.innerHTML = "<font color='red'>Sila isi nama pasangan dengan betul (huruf sahaja).</font>";
		        			document.createMemberForm.spouseName.style.borderColor = "#ff0000";
		    				document.createMemberForm.spouseName.focus();
		    				return false;
		   					
		   				}
		   			
				    else {
	    				document.createMemberForm.spouseName.style.borderColor = "green";
	    				var message6 = document.getElementById("message6");
			     		message6.innerHTML = "<fwont color='Green'>OK</font>";
			     		
			     		document.createMemberForm.marriageStat.style.borderColor = "green";
			    		var message5 = document.getElementById("message5");
					     message5.innerHTML = "<font color='Green'>OK</font>";
	    			}
		   	
    	}
    	else {
    		document.createMemberForm.marriageStat.style.borderColor = "green";
    		var message5 = document.getElementById("message5");
		     message5.innerHTML = "<font color='Green'>OK</font>";
    		}
    	
    	
    	
    	//Address Validation
    	if (alamat == "")
    	{
    		
    		var message7 = document.getElementById("message7");
			message7.innerHTML = "<font color='red'>Sila isi alamat ahli</font>";
			document.createMemberForm.address.style.borderColor = "#ff0000";
			document.createMemberForm.address.focus();
			return false;
    	
    	}
    	else {
    		document.createMemberForm.address.style.borderColor = "green";
    		var message7 = document.getElementById("message7");
		     message7.innerHTML = "<font color='Green'>OK</font>";
    		}
    	
 	
    //living Validation
    if (living == "")
    	{
    	var message8 = document.getElementById("message8");
		message8.innerHTML = "<font color='red'>Sila pilih ahli tinggal bersama siapa</font>";
		document.createMemberForm.living.style.borderColor = "#ff0000";
		document.createMemberForm.living.focus();
		return false;
		
  
    	}
    else {
		document.createMemberForm.living.style.borderColor = "green";
		var message8 = document.getElementById("message8");
	     message8.innerHTML = "<font color='Green'>OK</font>";
		}

    //Home Number Validation
	
    if ( isNaN(homeNo) )
			{
    	
    	var message9 = document.getElementById("message9");
		message9.innerHTML = "<font color='red'>Nombor telefon tidak boleh mengandungi huruf. Sila isi semula</font>";
		document.createMemberForm.homeNo.style.borderColor = "#ff0000";
		document.createMemberForm.homeNo.focus();
		return false;  
    	  
			}
	    else {
			document.createMemberForm.homeNo.style.borderColor = "green";
			var message9 = document.getElementById("message9");
		     message9.innerHTML = "<font color='Green'>OK</font>";
			}
    
    
    //Mobile Number Validation
    
     if ( isNaN(mobileNo) )
			{
    	 var message10 = document.getElementById("message10");
 		message10.innerHTML = "<font color='red'>Nombor telefon bimbit tidak boleh mengandungi huruf. Sila isi semula</font>";
 		document.createMemberForm.mobileNo.style.borderColor = "#ff0000";
 		document.createMemberForm.mobileNo.focus();
 		return false;   
			}
     else {
			document.createMemberForm.mobileNo.style.borderColor = "green";
			var message10 = document.getElementById("message10");
		     message10.innerHTML = "<font color='Green'>OK</font>";
			}
    
    
    	//academic validation
    	if ( academic == "")
    	{
    		
    	    	var message11 = document.getElementById("message11");
    	 		message11.innerHTML = "<font color='red'>Sila pilih Kelulusan Akademik Ahli</font>";
    	 		document.createMemberForm.academic.style.borderColor = "#ff0000";
    	 		document.createMemberForm.academic.focus();
    	 		return false;   
    			
    	}
    	 else {
 			document.createMemberForm.academic.style.borderColor = "green";
 			var message11 = document.getElementById("message11");
 		     message11.innerHTML = "<font color='Green'>OK</font>";
 			}
    	
    //Job Validation	
    	if (job == "")
    	{	
    		var message12 = document.getElementById("message12");
	 		message12.innerHTML = "<font color='red'>Sila pilih pekerjaan Ahli</font>";
	 		document.createMemberForm.job.style.borderColor = "#ff0000";
	 		document.createMemberForm.job.focus();
	 		return false;  
    	}
    	else if (job == "Bersara")
    		{
    		 	var lastJob = document.forms[v]["lastJob"].value;
    		 	var lastWorkplace = document.forms[v]["lastWorkplace"].value;
    		 	
    		 	if (lastJob == "")
            	{	
    		 		var message13 = document.getElementById("message13");
    	 			message13.innerHTML = "<font color='red'>Sila isi pekerjaan Terakhir Ahli</font>";
    	 			document.createMemberForm.lastJob.style.borderColor = "#ff0000";
    	 			document.createMemberForm.lastJob.focus();
    	 		return false;  
            	}
    		 	else {
    	 			document.createMemberForm.lastJob.style.borderColor = "green";
    	 			var message13 = document.getElementById("message13");
    	 		     message13.innerHTML = "<font color='Green'>OK</font>";
    	 		     
    	 		    document.createMemberForm.job.style.borderColor = "green";
    	 			var message12 = document.getElementById("message12");
    	 		     message12.innerHTML = "<font color='Green'>OK</font>";
    	 			}
    		 	if (lastWorkplace == "")
    		 		{
    		 		var message14 = document.getElementById("message14");
    	 			message14.innerHTML = "<font color='red'>Sila isi tempat pekerjaan Terakhir Ahli</font>";
    	 			document.createMemberForm.lastWorkplace.style.borderColor = "#ff0000";
    	 			document.createMemberForm.lastWorkplace.focus();
    	 			return false; 
    		 		}
    	 			else{
    	 				document.createMemberForm.lastWorkplace.style.borderColor = "green";
        	 			var message14 = document.getElementById("message13");
        	 		     message14.innerHTML = "<font color='Green'>OK</font>";
        	 		     
        	 		    document.createMemberForm.job.style.borderColor = "green";
        	 			var message12 = document.getElementById("message12");
        	 		     message12.innerHTML = "<font color='Green'>OK</font>";
    	 			}
    	 			
    		 }
    	else if (job == "Lain-lain")
    		{
    			
    			var lain = document.forms[v]["lainlain"].value;    	
    			
    			if (lain == "")
    			{
		 			var message15 = document.getElementById("message15");
	 				message15.innerHTML = "<font color='red'>Sila isi tempat pekerjaan Terakhir Ahli</font>";
	 				document.createMemberForm.lainlain.style.borderColor = "#ff0000";
	 				document.createMemberForm.lainlain.focus();
	 				return false; 
		 		}
	 			else{
	 				document.createMemberForm.lainlain.style.borderColor = "green";
    	 			var message15 = document.getElementById("message15");
    	 		     message15.innerHTML = "<font color='Green'>OK</font>";
    	 		     
    	 		    document.createMemberForm.job.style.borderColor = "green";
    	 			var message12 = document.getElementById("message12");
    	 		    message12.innerHTML = "<font color='Green'>OK</font>";
	 			}
    		
    	}
	 		
	 		else {
	 			document.createMemberForm.job.style.borderColor = "green";
 				var message12 = document.getElementById("message12");
		     	message12.innerHTML = "<font color='Green'>OK</font>";
		     	}
    	
    	
    	
    	
    	
    	
    	/* OLD VALIDATION 
		//Name Validation
		if (name == "")
			{
				alert("Sila Isi Nama Ahli dengan betul");
				document.createMemberForm.nama.style.background="yellow";
				document.createMemberForm.nama.focus();
				return false;
				
			}
		else {document.createMemberForm.nama.style.background="";}
		
		
		//IC Validation
		if (ic == "" || !/^[0-9]+$/.test(ic) || ic.length != 12  )
			{
			alert("Sila isi Nombor Kad Pengenalan Ahli dengan format yg betul  cth: 951124014092 '");
			document.createMemberForm.kp.style.background="yellow";
			document.createMemberForm.kp.focus();
			return false;
			}
		
		else {document.createMemberForm.kp.style.background="";}
		
		//gender Validation
		if (gender == "")
		{
			alert("Sila pilih Jantina Ahli");
			document.createMemberForm.gender.style.background="yellow";
			document.createMemberForm.gender.focus();
			return false;
			
		}
		
		else {document.createMemberForm.gender.style.background="";}
			
		//Marriage Validation
		
		if (marry == "")
		{	
			
			alert("Sila pilih Taraf Perkahwinan Ahli");
			document.createMemberForm.marriageStat.style.background="yellow";
			document.createMemberForm.marriageStat.focus();
			return false;
		}
		else {document.createMemberForm.marriageStat.style.background="";}
		
		//Address Validation
		if (alamat == "")
		{
		alert("Sila isi alamat ahli");
		document.createMemberForm.address.style.background="yellow";
		document.createMemberForm.address.focus();
		return false;
		}
	 	else {document.createMemberForm.address.style.background="";}
		

		
	//living Validation
	if (living == "")
		{
		alert("Sila pilih ahli tinggal bersama siapa");
		document.createMemberForm.living.style.background="yellow";
		document.createMemberForm.living.focus();
		return false;
		}
	 else {document.createMemberForm.living.style.background="";}
		
		
		//academic validation
		if ( academic == "")
		{
		alert("Sila pilih Kelulusan Akademik Ahli");
		document.createMemberForm.academic.style.background="yellow";
		document.createMemberForm.academic.focus();
		return false;
		}
	 else {document.createMemberForm.academic.style.background="";}
	//Job Validation	
		if (job == "")
		{	
			
			alert("Sila pilih pekerjaan Ahli");
			document.createMemberForm.job.style.background="yellow";
			document.createMemberForm.job.focus();
			return false;
		}
		else {document.createMemberForm.job.style.background="";}

	*/
	}	
			
	</script>
</body>

</html>
