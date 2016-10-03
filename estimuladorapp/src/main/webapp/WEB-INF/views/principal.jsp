<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--  <c:set var="c" value="${pageContext.request.contextPath}" /> --%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>App Estimulador</title>

<c:set var="c" value="${pageContext.request.contextPath}" />

<!-- Bootstrap -->
<link href="${c}/resources/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link href="${c}/resources/css/font-awesome.min.css" rel="stylesheet">
<!-- Custom Theme Style -->
<link href="${c}/resources/css/custom.min.css" rel="stylesheet">
<!-- iCheck -->
<link href="${c}/resources/css/skins-icheck/all.css" rel="stylesheet">
<!-- DataTables y Bootstrap -->
<link href="${c}/resources/css/dataTables.bootstrap.min.css"
	rel="stylesheet">
<link href="${c}/resources/css/responsive.bootstrap.min.css"
	rel="stylesheet">
<link href="${c}/resources/css/scroller.bootstrap.min.css"
	rel="stylesheet">
<link href="${c}/resources/css/buttons.bootstrap.min.css"
	rel="stylesheet">
<!-- jQuery UI -->
<link href="${c}/resources/css/jquery-ui.min.css" rel="stylesheet">
<!-- SWITCHERY -->
<link href="${c}/resources/css/switchery.min.css" rel="stylesheet">
<!-- pNotify -->
<link href="${c}/resources/css/pnotify.custom.min.css" media="all"
	rel="stylesheet" type="text/css" />
<!-- Sweet Alert 2 -->
<link href="${c}/resources/css/sweetalert.css" media="all"
	rel="stylesheet" type="text/css" />
<!-- DateRangePicker -->
<link href="${c}/resources/css/daterangepicker.css" media="all"
	rel="stylesheet" type="text/css" />
<!-- Slide Range -->
<link href="${c}/resources/css/ion.rangeSlider.css" media="all"
	rel="stylesheet" type="text/css" />
<link href="${c}/resources/css/ion.rangeSlider.skinHTML5.css"
	media="all" rel="stylesheet" type="text/css" />

<!-- INTERNO -->
<link href="${c}/resources/css/interno/micss.css" rel="stylesheet">

<script>
	var ctx = "${pageContext.request.contextPath}";
</script>
</head>

<body class="nav-md footer_fixed">

	<c:choose>
		<c:when test="${sessionScope.usuarioDTO.usuario.idTipoUsuario == 1}">
			<c:set var="nombre" value="${sessionScope.usuarioDTO.administrador.nombre}
			${sessionScope.usuarioDTO.administrador.apaterno}"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="nombre" value="Dr. ${sessionScope.usuarioDTO.medico.nombre}
			${sessionScope.usuarioDTO.medico.apaterno}"></c:set>
		</c:otherwise>
	</c:choose>

	<div class="container body">
		<div class="main_container">
			
			<div class="col-md-3 menu_fixed left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="margin-bottom: 25px;">
						<br> <a href="#" class="site_title"><i class="fa fa-low-vision" style="margin-right: 10px;"></i>
								<span>Estimulador</span></a>
					</div>

					<div class="clearfix"></div>

					 <!-- menu profile quick info -->
            		<div class="profile">
              			<div class="profile_pic">
                			<img src="${c}/resources/imgs/sysdt.png" alt="..." class="img-circle profile_img">
              			</div>
              			<div class="profile_info">
<!--                 			<span>Bienvenid@,</span> -->
                			<h2>${nombre}</h2>
                			<br><br>
              			</div>
            		</div>
            <!-- /menu profile quick info -->

            <br />

					<!-- sidebar menu -->
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">
						<div class="menu_section">
							                <h3>OPCIONES</h3>
							<ul class="nav side-menu">
								<li>
									<%--                   	<a href="${c}/historial"><i class="fa fa-home"></i>  --%>
									<!--                   		Pacientes --> <!--                   	</a> -->
									<a id="btnHistorial"><i class="fa fa-user-md"></i> Pacientes </a>
									<!--                     <ul class="nav child_menu"> --> <!--                       <li><a href="index.html">Dashboard</a></li> -->
									<!--                     </ul> -->
								</li>
								<li>
									<a id="btnEstimulacion">
										<i class="fa fa-paper-plane-o"></i>Enviar Estimulaci&oacute;n
									</a> 
								</li>
								<c:if test="${sessionScope.usuarioDTO.usuario.idTipoUsuario == 1}">
									<li>
										<a>
											<i class="fa fa-desktop"></i> Administrador 
											<span class="fa fa-chevron-down"></span>
										</a>
										<ul class="nav child_menu">
											<li><a id="btnMedico">M&eacute;dicos</a></li>
											<li><a id="btnPax">Pacientes</a></li>
											<li><a id="btnEstimulador">Estimuladores</a></li>
										</ul>
									</li>
								</c:if>
							</ul>
						</div>
						<div class="menu_section"></div>

					</div>
					<!-- /sidebar menu -->

					<!-- /menu footer buttons -->
            		<div class="sidebar-footer hidden-small">
<!--               			<a data-toggle="tooltip" data-placement="top" title="Settings"> -->
<!--                 			<span class="glyphicon glyphicon-cog" aria-hidden="true"></span> -->
<!--               			</a> -->
<!--               			<a data-toggle="tooltip" data-placement="top" title="FullScreen"> -->
<!--                 			<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span> -->
<!--               			</a> -->
<!--               			<a data-toggle="tooltip" data-placement="top" title="Lock"> -->
<!--                 			<span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span> -->
<!--               			</a> -->
              			<a href="logout" data-toggle="tooltip" data-placement="top" title="Logout">
               				<span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              			</a>
            		</div>
            		<!-- /menu footer buttons -->
				</div>
			</div>

			<!-- top navigation -->
			<div class="top_nav">
				<div class="nav_menu">
					<nav class="" role="navigation">
						<div class="nav toggle">
							<a id="menu_toggle"><i class="fa fa-bars"></i></a>
						</div>
						<!-- 			<img style="margin-top:5px;position: absolute;" alt="rf" src="resources/imgs/sysdt.png" width="6%" height="8%"> -->

						<ul class="nav navbar-nav navbar-right">
							<li class="">
								
								<a href="javascript:;" 
								class="user-profile dropdown-toggle" data-toggle="dropdown"
								aria-expanded="false"> <img
									src="${c}/resources/imgs/sysdt.png" alt="" width="6%"
									height="8%">${nombre} <span
									class=" fa fa-angle-down"></span></a>
							
		                  		<ul class="dropdown-menu dropdown-usermenu pull-right">
			                    	<li class="sesion"><a id="logout" href="logout" style="color:white;" >
			                    			<i class="fa fa-sign-out pull-right"></i> 
			                    				Cerrar Sesi&oacute;n
			                    		</a>
			                    	</li>
		                  		</ul>
		                	</li>

						</ul>
					</nav>
				</div>
			</div>
			<!-- /top navigation -->

			<!-- INICIA BLOQUE DE CONTENIDO DE PAGINA -->
			<div id="cont" class="right_col" role="main">
				<div class="">
					<div class="clearfix"></div>
					<div id="container" class="row"></div>
					<!-- .FIN ROW PRINCIPAL-->
						<div id="imgPrincipal" class="row" style="text-align: center;margin-top: 10%;">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<img alt="rf" src="${c}/resources/imgs/sysdt.png" width="70%" height="130%">
							</div>
						</div>
						 

					<!-- TERMINA BLOQUE DE CONTENIDO DE PAGINA -->
				</div>
			</div>
			<!-- /page content -->

			<div class="clearfix"></div>
			<!-- footer content -->
			<footer >
				<div class="pull-right">
					&#174; Desarrollado por <span style="font-weight: bold;">SYSDT
						&#8482; </span>
				</div>
				<div class="clearfix"></div>
			</footer>
			<!-- /footer content -->
		</div>
	</div>

	<!-- jQuery -->
	<script src="/estimuladorapp/resources/js/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="/estimuladorapp/resources/js/bootstrap.min.js"></script>
	<!-- FastClick -->
	<script src="/estimuladorapp/resources/js/fastclick.js"></script>
	<!-- NProgress -->
	<script src="/estimuladorapp/resources/js/nprogress.js"></script>
	<!-- Custom Theme Scripts -->
	<script src="/estimuladorapp/resources/js/custom.min.js"></script>
	<!-- iCheck -->
	<script src="/estimuladorapp/resources/js/icheck.min.js"></script>
	<!-- jQuery DataTables  -->
	<script src="/estimuladorapp/resources/js/jquery.dataTables.min.js"></script>
	<!-- jQuery DataTables Scroller -->
	<script src="/estimuladorapp/resources/js/dataTables.scroller.min.js"></script>
	<!-- jQuery DataTables Responsive -->
	<script src="/estimuladorapp/resources/js/dataTables.responsive.min.js"></script>
	<!-- jQuery DataTables Botones -->
	<script
		src="/estimuladorapp/resources/js/dtBotones/dataTables.buttons.min.js"></script>

	<!-- DataTables y Bootstrap -->
	<script src="/estimuladorapp/resources/js/dataTables.bootstrap.min.js"></script>
	<script src="/estimuladorapp/resources/js/responsive.bootstrap.js"></script>
	<script
		src="/estimuladorapp/resources/js/dtBotones/buttons.bootstrap.min.js"></script>

	<!-- SWITCHERY -->
	<script src="/estimuladorapp/resources/js/switchery.min.js"></script>

	<!-- InputMask -->
	<script src="/estimuladorapp/resources/js/jquery.inputmask.bundle.js"></script>

	<!-- notify -->
	<script src="/estimuladorapp/resources/js/notify.min.js"></script>

	<!-- pNotify -->
	<script src="/estimuladorapp/resources/js/pnotify.custom.min.js"></script>

	<!-- Sweet Alert 2 -->
	<script src="/estimuladorapp/resources/js/sweetalert.min.js"></script>

	<!-- Google Maps -->
	<script
		src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDg8fMqj67nLiW_ZZcd_RTp_bzS-w0rHx0"></script>


	<!-- jQuery Knob -->
	<script src="/estimuladorapp/resources/js/jquery.knob.min.js"></script>
	<!-- moment JS -->
	<script src="/estimuladorapp/resources/js/moment-with-locales.min.js"></script>
	<!-- Slide Range Picker -->
	<script src="/estimuladorapp/resources/js/ion.rangeSlider.min.js"></script>
	<!-- Date Range Picker -->
	<script src="/estimuladorapp/resources/js/daterangepicker.js"></script>
	<!-- jQuery UI -->
	<script src="/estimuladorapp/resources/js/jquery-ui.min.js"></script>
	<!-- jQuery Plain Overlay -->
	<script src="/estimuladorapp/resources/js/jquery.plainoverlay.min.js"></script>
	
	<!-- GENERAL -->
	<script src="/estimuladorapp/resources/js/interno/general.js"></script>

</body>
</html>