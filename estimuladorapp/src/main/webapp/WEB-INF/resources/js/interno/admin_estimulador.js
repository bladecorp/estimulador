$(document).ready(function(){
	
	
	
	/****** SECCION INICIALIZACION VARIABLES ****/
	var indexFila;
	var estimSeleccionado;
	var checked = false;
	var errorEnFormEstim = false;
	var idPacienteSel;
	
	var checkEditar = $('#checkEditarEstim').iCheck({checkboxClass: 'icheckbox_square-purple'});
	var checkNuevo = $('#checkNuevoEstim').iCheck({checkboxClass: 'icheckbox_square-purple'});
	$('input[name=tipoB]').iCheck({radioClass: 'iradio_flat-green'});
	var formEstim = $('#formEstim');
	var serieEstim = $('#serieEstim');
	var paxEstim = $('#paxEstim');
	var formVincular = $('#formVincular');
	var liberarEstim = document.querySelector('#checkEstimEnabled');
	var estimVinc = $('#estimVinc');
	var paxVinc = $('#paxVinc');
	var btnVincular = $('#btnVincular');
	var switcheryCheck = new Switchery(liberarEstim, { size: 'small', disabled: true, color: '#26B99A' });
	
	var paxAutocomplete = $('#paxAutocomplete').autocomplete({
		 source: function (request, response) {
			 btnVincular.prop('disabled',false);
			 var tipoBusqueda = $('input[name=tipoB]:checked').val();
			 var valor = request.term;
			 var avanza = true;
			 if(tipoBusqueda === "1" && isNaN(valor)){
				 avanza = false;
			 }
			 
			 if(avanza){
		         $.ajax({
		             url: ctx+"/paciente/filtro",
		             type: "GET",
		             data: {term: valor, tipoFiltro: tipoBusqueda},
		             success: function (data) {
		                 response($.map(data, function (paciente) {
		                     return {
		                         label: paciente.id+" "+paciente.nombre+" "+paciente.apaterno+" "+paciente.amaterno,
		                         value: paciente.id
		                     };
		                 }));
		             }
		         });
			 }else{
				 notify("warning", "Cuidado!", "El ID debe ser num&eacute;rico");
				 btnVincular.prop('disabled',true);
				 response("");
			 }
	    },
	    select: function (event, ui) {
	        // Prevent value from being put in the input:
//	        this.value = ui.item.label;
	        // Set the next input's value to the "value" of the item.
//	        $(this).next("input").value(ui.item.value);
	    	var paxSel = ui.item.label;
	    	$(this).val("");
	    	paxVinc.val(paxSel);
	    	console.log(paxSel);
	    	idPacienteSel = ui.item.value;
	        event.preventDefault();
	    }
	});
	
	var tablaEstimuladores = $('#tablaEstimuladores').DataTable({
		language: {"emptyTable": "No se encontraron estimuladores"},
		lengthMenu: [[10, 15, 20, -1], [10, 15, 20, "Todos"]],
		language: {
            lengthMenu: "Mostrar _MENU_ registros",
            zeroRecords: "No hay estimuladores",
            info: "Mostrando p&aacute;gina _PAGE_ de _PAGES_",
            infoEmpty: "No hay registros disponibles",
            infoFiltered: "",
            search: "",
            responsive: true,
            searchPlaceholder:"Busca",
            paginate: {
        		previous: 'Anterior',
        		next: 'Siguiente'
        	}
		},
		columns:[
			{data:"id"},{data:"serie"},{data:"idPaciente"}
		],
		columnDefs: [ {targets: 2,data: "idPaciente",
		    "render": function ( data, type, full, meta ) {
		    	if(data === null){
		    		return '<i class="fa fa-unlock fa-lg" style="color:green;"></i>';
		    		
		    	}else{
//		    		return '<input type="checkbox" class="js-switch" checked />';
		    		return '<i class="fa fa-lock fa-lg" style="color:red;"></i>';
		    	}
		    }
		  } ]
	});
	
	
	/****** SECCION CARGA INICIAL ****/
	
	init();
	cargarEstimuladores(true);
	
	
	/****** SECION EVENTOS *******/
	
	/***** SELECCIONAR FILA ****/
	tablaEstimuladores.on("click", "tr", function(){
		if(!$(this).hasClass("filaActiva")){
			checkNuevo.iCheck('uncheck');
			checkEditar.iCheck('uncheck');
			checkEditar.iCheck('enable');
			estimSeleccionado = tablaEstimuladores.row(this).data();console.log("Fila: ");console.log(estimSeleccionado);
			var idEstim = estimSeleccionado.id;console.log("idEstim: "+idEstim);
			indexFila = tablaEstimuladores.row(this);
			tablaEstimuladores.$('tr.filaActiva').removeClass('filaActiva');
			$(this).addClass("filaActiva");
			serieEstim.val(estimSeleccionado.serie);
			estimVinc.val(estimSeleccionado.serie);
			if(liberarEstim.checked){switcheryCheck.setPosition(true);}
			if(estimSeleccionado.idPaciente !== null){
				switcheryCheck.enable();
				obtenerPacienteVinculado(estimSeleccionado.idPaciente)
			}else{
				switcheryCheck.disable();
				paxEstim.val("");
			}
		}
	});
	
	checkEditar.on('ifChecked', function(){console.log("entro a checked");
		$("#formEstim .edit").prop('disabled', false);
	});
	
	checkEditar.on('ifUnchecked', function(){console.log("entro a UNchecked");
		$("#formEstim .edit").prop('disabled', true);
	});
	
	checkNuevo.on('ifChecked', function(){console.log("entro a checked");
		serieEstim.val("");
		paxEstim.val("");
		checkEditar.iCheck('uncheck');
		checkEditar.iCheck('disable');
		tablaEstimuladores.$('tr.filaActiva').removeClass('filaActiva');
		$("#formEstim .edit").prop('disabled', false);
		switcheryCheck.disable();
	});
	
	checkNuevo.on('ifUnchecked', function(){
		serieEstim.val("");
		paxEstim.val("");
		$("#formEstim .edit").prop('disabled', true);
	});
	
	liberarEstim.onchange = function() {
		if(liberarEstim.checked){
			swal({
				  title: ' Desea desvincular el estimulador?',
				  showCancelButton: true,
				  confirmButtonText: 'Aceptar',
				  cancelButtonText: 'Cancelar',
				  showLoaderOnConfirm: true,
				  closeOnConfirm: false,
				  type: "warning"
			},function(){
				desvincularEstimulador();
			});
			switcheryCheck.setPosition(true);
		}else{
			console.log("NOT SO FAST");
		}
	};
		
		formEstim.submit(function(event){
			event.preventDefault();
			validarFormEstimulador();
			if(!errorEnFormEstim){
				swal({
					  title: ' Desea guardar los cambios?',
					  showCancelButton: true,
					  confirmButtonText: 'Guardar',
					  cancelButtonText: 'Cancelar',
					  showLoaderOnConfirm: true,
					  closeOnConfirm: false,
					  type: "warning"
				},function(){
					guardarEstimulador();
				});
			}else{
				serieEstim.closest('.form-group').addClass('has-error');
				notify("error", "Cuidado!", "Debe indicar el n&uacute;mero de serie del estimulador");
			}
		});
		
	$("input[name=tipoB]:radio").on('ifChecked',function(){
		console.log($(this).val());
		paxAutocomplete.val("");
	});
	
	formVincular.submit(function(event){
		if(estimSeleccionado !== undefined && idPacienteSel !== undefined){
			var titulo;
			var mensaje;
			if(estimSeleccionado.idPaciente === null){
				titulo = "Atenci&oacute;n";
				mensaje = "Informaci&oacute;n de vinculaci&oacute;n <br>";
			}else{
				titulo = "Cuidado!";
				mensaje = "<span style='color:red;'>Este estimulador ya est&aacute; vinculado a un paciente.</span> <br>Desea actualizar?<br>";
			}
			mensaje += "<br> " +
					"<table style='padding:0;margin:0 auto;'> <tbody> " +
						"<tr><td style='text-align:right;'>Paciente: </td><td style='font-weight:bold;text-align:left;'> "+paxVinc.val()+"</td></tr> " +
						"<tr><td style='text-align:right;'>Estimulador: </td><td style='font-weight:bold;text-align:left;'> "+estimSeleccionado.serie+"</td></tr> " +
					"</tbody> </table>"
//			mensaje += "<br> " +
//			"	<span>Paciente: </span><span style='font-weight:bold;'>"+paxVinc.val()+"</span>" +
//			"<br>" +
//			"	<span>Estimulador: </span><span style='font-weight:bold;'>"+estimSeleccionado.serie+"</span>"; 
			swal({
				  title: titulo,
				  html: true,
				  text: mensaje,
				  showCancelButton: true,
				  confirmButtonText: 'Vincular',
				  cancelButtonText: 'Cancelar',
				  showLoaderOnConfirm: true,
				  closeOnConfirm: false,
				  type: "warning"
			},function(){
				$.ajax({
					url: ctx+'/estimulador/vincular',
					type: 'POST',
					dataType: 'json',
					data: {idEstimulador: estimSeleccionado.id, serie: estimSeleccionado.serie, idPaciente: idPacienteSel},
					success: function(resp){console.log(resp);
						if(resp.codigo === -1){
							cargarEstimuladores(false);
							serieEstim.val("");
							paxEstim.val("");
							swal("Exito", "El estimulador fue vinculado exitosamente", "success");
						}else{
							swal("Error", resp.mensaje, "error");
						}
					}, error: function(resp){
						
					}
						
				});
			});
		}else{
			notify("error", "Error", "Debe seleccionar un paciente y un estimulador");
		}
		event.preventDefault();
	});
	
	/****** SECCION FUNCIONES  *******/
		
	function guardarEstimulador(){
		var isNuevo = true;
		var estimulador = {};
		if(!checkNuevo.is(':checked')){
			estimulador = estimSeleccionado;
			isNuevo = false;
		}	
		estimulador.serie = serieEstim.val();
		$.ajax({
			url: ctx+'/estimulador',
			type: 'POST',
			data: {estimulador: JSON.stringify(estimulador), nuevo: isNuevo},
			success: function(respuesta){
				if(respuesta.codigo === -1){
					cargarEstimuladores(false);
					swal("Exito", "La informacion se guardo exitosamente", "success");
				}else{
					swal("Error", respuesta.mensaje, "error");
				}
			},
			error: function(){
				swal("Error", "No fue posible guardar la informacion del estimulador", "error");
			}
		});
	}
	
	function vincularEstimulador(){
		console.log("ENTRA A VINCULAR ESTIMULADOR");
		$.ajax({
			url: ctx+'/estimulador/vincular',
			type: 'POST',
			dataType: 'json',
			data: {idEstimulador: estimSeleccionado.id, serie: estimSeleccionado.serie, idPaciente: idPacienteSel},
			success: function(resp){
				if(resp.codigo === -1){
					swal("Exito", "El estimulador fue vinculado exitosamente", "success");
				}else{
					swal("Error", resp.mensaje, "error");
				}
			}, error: function(resp){
				
			}
				
		});
	}
	
	function desvincularEstimulador(){
		$.ajax({
			url: ctx+'/estimulador/desvincular',
			type: 'POST', /*contentType: 'application/json',*/
			dataType: 'json',
			data: {estim: JSON.stringify(estimSeleccionado)},
			success: function(exito){
				if(exito === -1){
					estimSeleccionado.idPaciente = null;
					paxEstim.val("");
					tablaEstimuladores.row(indexFila).data(estimSeleccionado).draw(false);
					switcheryCheck.disable();
					swal("Exito", "El estimulador fue desvinculado exitosamente", "success");
				}else{
					swal("Error", "No fue posible desvincular el estimulador", "error");
				}
			},
			error: function(){
				swal("Error", "No fue posible desvincular el estimulador", "error");
			}
		});
	}
		
	function obtenerPacienteVinculado(idPaciente){
		$.ajax({
			url: ctx+'/estimulador/pax/'+idPaciente,
			type:'GET',
			dataType:'json',
			success: function(paciente){
				paxEstim.val(paciente.nombre+" "+paciente.apaterno+" "+paciente.amaterno);
			}
		});
	}
	
	function cargarEstimuladores(recargar){
		console.log("ajax");
		tablaEstimuladores.clear();
		$.ajax({
			url: ctx+"/estimulador",
			type: "GET",
			dataType: "json",
			success: function(estimuladores){
				tablaEstimuladores.rows.add(estimuladores).draw(recargar);
			},
			error: function(){}
		});
	}
	
	function validarFormEstimulador(){
		$("#formEstim *").filter('.form-group').removeClass('has-error');
		errorEnFormEstim = serieEstim.val() === ""?true:false;
	}
	
	function init(){
		$('#formEstim .edit').prop('disabled', true);
		checkEditar.iCheck('disable');
		switcheryCheck.disable();
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