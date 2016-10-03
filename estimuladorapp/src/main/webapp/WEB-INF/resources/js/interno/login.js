$(document).ready(function(){
	PNotify.prototype.options.styling = "bootstrap3";
	var formLogin = $('#formLogin');
	var formInc = $('#formInc');
	var username = $('#username');
	var password = $('#password');
	
	init()
	
	formLogin.submit(function(event){
		event.preventDefault();
		if(validarFormulario()){
			enviarFormulario();
		}
	});
	
	function enviarFormulario(){ console.log(ctx);
		$.ajax({
			url: ctx+'/login',
			type: 'POST',
			dataType: 'json',
			data:{username: username.val().trim(), password: password.val().trim()},
			success: function(resp){
				if(resp.codigo === -1){
					formInc.submit();
				}else{
					notify("error", "Error", resp.mensaje);
				}
			}, error: function(){
				notify("error", "Error", "No fue posible validar la informaci&oacute;n");
			}
		});
	}
		
	function validarFormulario(){
		var exito = true;
		username.removeClass('bordeRojo');
		password.removeClass('bordeRojo');
		if(username.val().trim() === ""){
			exito = false;
			notify("error", "Cuidado!", "Debe indicar el nombre de usuario");
			username.addClass('bordeRojo');
		}
		if(password.val().trim() === ""){
			exito = false;
			notify("error", "Cuidado!", "Debe indicar su password");
			password.addClass('bordeRojo');
		}
		return exito;
	}
	
	function init(){
		username.val("");
		password.val("");
		window.setTimeout(function() {
			  $("#sesionDiv").fadeTo(500, 0).slideUp(500, function(){
			    $(this).remove(); 
			  });
			}, 2000);
	}
	
	function notify(tipo, titulo, mensaje){
		new PNotify({
		     title: titulo,
		     text: mensaje,
		     type: tipo,
		     delay: 2000
		  }); 
	}
});