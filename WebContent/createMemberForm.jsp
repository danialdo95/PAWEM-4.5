<%@ page language="java"
 contentType="text/html; charset=ISO-8859-1"
 pageEncoding="windows-1256" %>
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

    <title>Borang Ahli</title>

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
        <!-- Page Content -->
        
         <br><br>
        <div id="page-wrapper">
            <div class="container-fluid">
                    <!-- /.col-lg-12 -->
                   <br>
                    <div class="row">
                    <h1 class="page-header">
                            Borang Ahli <small> </small>
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
                                <h3 class="panel-title">Borang Ahli Baru</h3>
                            </div>
							
                        <form role="form" name="createMemberForm" action="member" method="post" onsubmit="return validate()">
						<div class="panel panel-default"><br/>
                            <div class="panel-body">
                            	 <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Nama : </label>
                                        <div class="col-md-10">
                                        	<input class="form-control" name="nama" value=""  >
                                        	 <p id="message" ></p>
                                        </div>
                                       
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">No K/P [Baru] :</label>
                                        <div class="col-md-10">
                                        	<input class="form-control" name="kp" value=""  >
                                        	<p id="message2" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Jantina : </label>
                                        <div class="col-md-4">
                                        	<select class="form-control" name="gender"  >
                              					<option value="">-- Pilih --</option>	
                                   				<option value="Lelaki">Lelaki</option>
												<option value="Perempuan">Perempuan</option>
											 </select>
											 <p id="message3" ></p>
                                        </div>
                                        <label class="col-md-2">Kaum : </label>
                                        <div class="col-md-4">
                                        	<select class="form-control" name="race"  >
                              					<option value="">-- Pilih --</option>	
                                   				<option value="Melayu">Melayu</option>
												<option value="Cina">Cina</option>
												<option value="India">India</option>
												<option value="Lain-Lain">Lain-Lain</option>
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
                                        	<select class="form-control" onchange="showfield2(this.options[this.selectedIndex].value)" name="marriageStat"  >
                              				 	    <option value="">-- Pilih --</option>	
                                                    <option value="Bujang">Bujang</option>
                									<option value="Berkahwin">Berkahwin</option>
                								</select>
                								<p id="message5" ></p>
                                        </div>
                                        <div id="div2"></div>
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
                                        	<textarea class="form-control" name="address"  ></textarea>
                                        	<p id="message7" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Tinggal Bersama : </label>
                                        <div class="col-md-10">
                                        	<select class="form-control" name="living"  >
												<option value="">-- Pilih --</option>	
			                                    <option value="Keluarga">Keluarga</option>
												<option value="Anak-Anak">Anak-Anak</option>
												<option value="Sendiri">Sendiri</option>
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
                                        	<input type="text" class="form-control" value="" name="homeNo">
                                        	<p id="message9" ><span class="help-block"><font color = "#ff9900" >** Jika tiada , tinggalkan ruangan ini ( cth : 0392834048 ) ** </font></span></p>
                                        	 
                                        </div>
                                        <label class="col-md-2">No Telefon Bimbit :  </label>
                                        <div class="col-md-4">
                                        	<input type="text" class="form-control" value="" name="mobileNo">
                                        	<p id="message10" ><span class="help-block"><font color = "#ff9900" >** Jika tiada , tinggalkan ruangan ini ( cth: 0123456789 ) ** </font></span></p>
                                        	                           
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="form-group"> 
                                        <label class="col-md-2">Kelulusan Akademik : </label>
                                        <div class="col-md-10">
                                        	<select class="form-control" name="academic"  >
		                                            <option value="">-- Pilih --</option>	 
		                                            <option value="UPSR">UPSR</option>
		                                            <option value="PMR">PMR</option>
		                                            <option value="SPM">SPM</option>
		                                            <option value="Pengajian Tinggi">Pengajian Tinggi</option>
		                                            <option value="Tiada">Tiada</option>
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
                                        	<select class="form-control"  onchange="showfield(this.options[this.selectedIndex].value)" name="job"  >
		                                            <option value="">-- Pilih --</option>	 
		                                            <option value="Cikgu">Cikgu</option>
		                                            <option value="Kerani">Kerani</option>
		                                            <option value="Polis">Polis</option>
		                                            <option value="Peguam">Peguam</option>
		                                            <option value="Bersara">Bersara</option>
		                                            <option value="Lain-lain">Lain-lain</option>
		                                        </select>
		                                        <p id="message12" ></p>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div id="div1"></div>
                                <script type="text/javascript">
                                  function showfield(name){
                                       if(name=='Bersara')
                                        {
                                            document.getElementById('div1').innerHTML='<div class="row"><div class="form-group"><label class="col-md-2">Pekerjaan Terakhir : </label><div class="col-md-10"><input class="form-control" value="" name="lastJob" placeholder="" ><p id="message13" ></p></div></div></div><br/><div class="row"><div class="form-group"><label class="col-md-2">Tempat Pekerjaan Terakhir: </label><div class="col-md-10"><input class="form-control" value="" name="lastWorkplace" placeholder="" ><p id="message14" ></p><br></div></div></div>';
                                        }
                                        else if(name=='Lain-lain')
                                        {
                                            document.getElementById('div1').innerHTML='<div class="row"><div class="form-group"><label class="col-md-2">Sila Nyatakan Pekerjaan : </label><div class="col-md-10"><input class="form-control" name="lainlain" placeholder="Sila masukkan pekerjaan anda" ><p id="message15" ></div></div></div><input name="lastJob" value="" readonly style="display:none;" ><input  name="lastWorkplace" value="" style="display:none" > ';
                                        }
                                      else
                                      {
                                          document.getElementById('div1').innerHTML='<input name="lastJob" value="" readonly style="display:none" ><input name="lastWorkplace" value="" readonly style="display:none" >';
                                      }
                                    }
								   </script>
                            <input type="hidden" name="action" value="createMemberInfo" />
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
    
     <!-- Validation JavaScript -->
    <script>
    function validate()
    {	      	
    	var v = "createMemberForm";
    	   	
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
    	 ///^[a-zA-Z ]+$/
    	else if (!/^[A-Za-z\s]+$/g.test(document.createMemberForm.nama.value)) 
    	/*( !/^[0-9]+$/.test(name) == false)*/
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
			     		message6.innerHTML = "<font color='Green'>OK</font>";
			     		
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
		message9.innerHTML = "<font color='red'>Nombor telefon tidak boleh mengandungi huruf.  ** Jika tiada , tinggalkan ruangan ini ( cth : 0392834048 ) **  </font>";
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
 		message10.innerHTML = "<font color='red'>Nombor telefon bimbit tidak boleh mengandungi huruf. ** Jika tiada , tinggalkan ruangan ini ( cth : 0123456789 ) ** </font>";
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
	


    }	
    </script>

</body>

</html>
