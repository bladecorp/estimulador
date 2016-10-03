//$(document).ready(function(){ 
$(window).load(function(){
	console.log("RECARGA");
	var container = $('#container');
	var btnPax = $('#btnPax');
	var btnEstimulacion = $('#btnEstimulacion');
	var btnHistorial = $('#btnHistorial');
	var btnMedico = $('#btnMedico');
	var btnEstimulador = $('#btnEstimulador');
//	var logout = $('#logout');
	
	init();
	
//	logout.click(function(event){
//		event.preventDefault();
//		$.ajax({
//			url: ctx+'/logout',
//			type: 'POST'
//		});
//	});
	
	btnHistorial.click(function(){
//		event.preventDefault();
		$('body').plainOverlay('show');
		quitarImagenPrincipal();
		container.load(ctx+"/historial", function(){
			corregirArchivosJS('medico.js');
			$('body').plainOverlay('hide');
		});
	});
	
	btnEstimulacion.click(function(){
//		event.preventDefault();
		$('body').plainOverlay('show');
		quitarImagenPrincipal();
		container.load(ctx+"/estimulacion", function(){
			corregirArchivosJS('estimulacion.js');
			$('body').plainOverlay('hide');
		});
	});
	
	btnPax.click(function(){
//		event.preventDefault();
		$('body').plainOverlay('show');
		quitarImagenPrincipal();
		container.load(ctx+"/adminpaciente", function(){
			corregirArchivosJS('admin_paciente.js');
			$('body').plainOverlay('hide');
		});
	});
	
	btnMedico.click(function(){
//		event.preventDefault();
		$('body').plainOverlay('show');
		quitarImagenPrincipal();
		container.load(ctx+"/adminmedico", function(){
			corregirArchivosJS('admin_medico.js');
			$('body').plainOverlay('hide');
		});
	});
	
	btnEstimulador.click(function(){
//		event.preventDefault();
		$('body').plainOverlay('show');
		quitarImagenPrincipal();
		container.load(ctx+"/adminestimulador", function(){
			corregirArchivosJS('admin_estimulador.js');
			$('body').plainOverlay('hide');
		});
	});
	
	function init(){
		PNotify.prototype.options.styling = "bootstrap3";
		swal.setDefaults({
			allowOutsideClick: false,
			confirmButtonColor: '#169F85',
		});
	}
	
	function corregirArchivosJS(archivo){
		var ruta = ctx+"/resources/js/interno/";
		var l = $("script[src='"+ruta+"admin_estimulador.js']").length;
		if(l > 0){
			$("script[src='"+ruta+"admin_estimulador.js']").remove();
		}
		l = $("script[src='"+ruta+"admin_medico.js']").length;
		if(l > 0){
			$("script[src='"+ruta+"admin_medico.js']").remove();
		}
		l = $("script[src='"+ruta+"admin_paciente.js']").length;
		if(l > 0){
			$("script[src='"+ruta+"admin_paciente.js']").remove();
		}
		l = $("script[src='"+ruta+"estimulacion.js']").length;
		if(l > 0){
			$("script[src='"+ruta+"estimulacion.js']").remove();
		}
		l = $("script[src='"+ruta+"medico.js']").length;
		if(l > 0){
			$("script[src='"+ruta+"medico.js']").remove();
		}
		
	    $('<script/>').attr('src', ruta+archivo).appendTo('body');
	}
	
	function quitarImagenPrincipal(){
		var img = $("#imgPrincipal").length;console.log(img);
		if(img > 0){
			$("#imgPrincipal").remove();
		}
	}
	
});