<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Estimulador App </title>
	<c:set var="c" value="${pageContext.request.contextPath}" />

    <!-- Bootstrap -->
    <link href="${c}/resources/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${c}/resources/css/font-awesome.min.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="${c}/resources/css/custom.min.css" rel="stylesheet">
    
    <!-- pNotify -->
    <link href="${c}/resources/css/pnotify.custom.min.css" media="all" rel="stylesheet" type="text/css" />
    
     <!-- INTERNO -->
    <link href="${c}/resources/css/interno/micss.css" rel="stylesheet">
    
    <script>
    	var ctx = "${pageContext.request.contextPath}";
    </script>
  </head>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
          	<img alt="df" src="${c}/resources/imgs/sysdt.png" width="50%" height="50%">
          	<form id="formInc" action="${c}/acceso" method="get"></form>
          	<c:if test="${not empty sesion}">
          		<div id="sesionDiv" class="alert alert-success alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
                    </button>
                    	Sesi&oacute;n cerrada correctamente!
<!--                     <strong>Sesi&oacute;n cerrada correctamente!</strong> -->
                </div>
          	</c:if>
            <form id="formLogin">
              <h1>Acceso al sistema</h1>
              <div>
                <input type="text" id="username" class="form-control req" placeholder="Usuario" value="" />
              </div>
              <div>
                <input type="password" id="password" class="form-control req" placeholder="Password" value=""/>
              </div>
              <div>
                <button type="submit" class="btn btn-dark btn-lg submit">Entrar</button>
<!--                 <a class="reset_pass" href="#">Lost your password?</a> -->
              </div>

              <div class="clearfix"></div>

              <div class="separator">
<!--                 <p class="change_link">New to site? -->
<!--                   <a href="#signup" class="to_register"> Create Account </a> -->
<!--                 </p> -->

                <div class="clearfix"></div>
                <br />

                <div>
                  <h1><i class="fa fa-user"></i> App Estimulador</h1>
                  <p>©2016 Todos Los Derechos Reservados. Desarrollador por SYSDT &#8482;</p>
                </div>
              </div>
            </form>
          </section>
        </div>

      </div>
    </div>
    
    <!-- jQuery -->
    <script src="${c}/resources/js/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="${c}/resources/js/bootstrap.min.js"></script>
    <!-- pNotify -->
    <script src="${c}/resources/js/pnotify.custom.min.js"></script>
    <!-- INTERNO -->
    <script src="${c}/resources/js/interno/login.js"></script>
    
  </body>
</html>