$(document).ready(function(){
		
	/******** SECCION INICIALIZACION VARIABLES  **********/
	var NO_TIENE_PARENTESCO = 13;
	var checked = false;
	var indexFila;
	var errorEnForm = false;
	var tiposResponsable;
	var pacienteGuardado;
	var formVinc = $('#formVinc');
	var parentesco = $('#parentesco');
	var checkEditar = $('#checkEditarPax').iCheck({checkboxClass: 'icheckbox_square-purple'});
	var checkNuevo = $('#checkNuevoPax').iCheck({checkboxClass: 'icheckbox_square-purple'});
	var formPax = $('#formPax');
	var telCel = $('#telCel').inputmask('(99) 9999-9999');
	var telCasa = $('#telCasa').inputmask('(99) 9999-9999');
	var telOf = $('#telOf').inputmask('(99) 9999-9999');
	var paxEnabled = document.querySelector('#checkEnabledPax');
	var switcheryCheck = new Switchery(paxEnabled, { size: 'small', disabled: true, color: '#26B99A' });
	
	var tablaPacientes = $('#tablaPacientes').DataTable({
		language: {"emptyTable": "No se encontraron pacientes"},
		lengthMenu: [[10, 15, 20, -1], [10, 15, 20, "Todos"]],
		language: {
            lengthMenu: "Mostrar _MENU_ registros",
            zeroRecords: "No hay pacientes",
            info: "Mostrando p&aacute;gina _PAGE_ de _PAGES_",
            infoEmpty: "No hay registros disponibles",
            infoFiltered: "",
            search: "",
            searchPlaceholder:"Buscar",
            paginate: {
        		previous: 'Anterior',
        		next: 'Siguiente'
        	}
		},
		columns:[
			{data:"id"},{data:"nombre"},{data:"apaterno"},{data:"amaterno"},{data:"enabled"}
		],
		columnDefs: [ {targets: 4,data: "enabled",
		    "render": function ( data, type, full, meta ) {
		    	if(data){
		    		return '<i class="fa fa-check fa-lg" style="color:green;"></i>';
		    		
		    	}else{
//		    		return '<input type="checkbox" class="js-switch" checked />';
		    		return '<i class="fa fa-times fa-lg" style="color:red;"></i>';
		    	}
		    }
		  } ]
	});
	
/******** SECCION INICIALIZACION PAGINA  **********/
	
		
	init();
	cargarPacientes(true);
	cargarTiposResponsable();
	
	
	/********** SECCION EVENTOS *********/
	
	tablaPacientes.on("click", "tr", function(){
		if(!$(this).hasClass("filaActiva")){
			limpiarForm();
			$("#formPax :input").prop('disabled', true);
			checkNuevo.iCheck('uncheck');
			checkEditar.iCheck('uncheck');
			checkEditar.iCheck('enable');
			switcheryCheck.enable();
			var filaSeleccionada = tablaPacientes.row(this).data();console.log("Fila: ");console.log(filaSeleccionada);
			var idPax = filaSeleccionada.id;console.log("idMed: "+idPax);
			indexFila = tablaPacientes.row(this); console.log("idFila: ");
			tablaPacientes.$('tr.filaActiva').removeClass('filaActiva');
			$(this).addClass("filaActiva");
			$.ajax({
				url: ctx+'/paciente/'+filaSeleccionada.idUsuario,
				dataType: 'json',
				data:{},
				success: function(usuarioDTO){console.log(usuarioDTO);
					var enabld = usuarioDTO.pacienteDTO.paciente.enabled?true:false;
					if(enabld !== checked){console.log("Valor enabld: "+enabld);console.log("Valor checked: "+checked);
						switcheryCheck.setPosition(true); 
						checked = enabld;
					}
					var estimulador = usuarioDTO.pacienteDTO.estimulador;
					if(estimulador.id !== null){
						$('#estimPax').val(estimulador.serie);
						if(usuarioDTO.usuario.token !== null && usuarioDTO.usuario.token.length > 0){
							$('#vincPax').val('SI');
							$('#vincPax').removeClass('fondoRojo').addClass('fondoVerde');
						}else{
							$('#vincPax').val('NO');
							$('#vincPax').removeClass('fondoVerde').addClass('fondoRojo');
						}
					}else{
						$('#estimPax').val("NO TIENE");
						$('#vincPax').val('NO');
						$('#vincPax').removeClass('fondoVerde').addClass('fondoRojo');
					}
					
					$('#nombrePax').val(usuarioDTO.pacienteDTO.paciente.nombre);
					$('#apaternoPax').val(usuarioDTO.pacienteDTO.paciente.apaterno);
					$('#amaternoPax').val(usuarioDTO.pacienteDTO.paciente.amaterno);
					$('#direccion').val(usuarioDTO.pacienteDTO.paciente.direccion);
					$('#email').val(usuarioDTO.pacienteDTO.paciente.email);
					var qtyTelefonos = usuarioDTO.telefonos.length;
					$.each(usuarioDTO.telefonos, function(index, telefono){
						if(telefono.idTipoTelefono === 1){
							telCel.val(telefono.numero);
						}else if(telefono.idTipoTelefono === 2){
							telCasa.val(telefono.numero);
						}else{
							telOf.val(telefono.numero);
						}
					});
					$('#usuario').val(usuarioDTO.usuario.usuario);
					$('#password').val(usuarioDTO.usuario.password);
					if(usuarioDTO.pacienteDTO.responsables.length > 0){
						$('#nombreResp').val(usuarioDTO.pacienteDTO.responsables[0].nombre);
						$('#apaternoResp').val(usuarioDTO.pacienteDTO.responsables[0].apaterno);
						$('#amaternoResp').val(usuarioDTO.pacienteDTO.responsables[0].amaterno);
						parentesco.val(usuarioDTO.pacienteDTO.responsables[0].idTipoParentesco);
					}
					pacienteGuardado = usuarioDTO;
				}, error: function(){
					console.log("ENTRO EN ERROR");
				}
			});
			
		}
	});
	
	paxEnabled.onchange = function() {
		console.log("Antes checked: "+checked);
		  if(checked !== paxEnabled.checked){
			  checked = !checked;
			  var datosFila = indexFila.data();console.log("Despues checked: "+checked);
			  $.ajax({
				  url: ctx+'/paciente/estado',
				  type: 'GET',
				  data: {estado: checked, idPaciente: datosFila.id},
				  success: function(){
					  datosFila.enabled = checked;
					  tablaPacientes.row(indexFila).data(datosFila).draw(false);
					  notify("success", "Cambio de Estado", "El paciente fue "+ (checked?'hablitado':'deshabilitado'));
				  }, error: function(){
					  notify("error", "Cambio de Estado", "No fue posible cambiar el estado del paciente");
				  }
			  }); 
		  }
		};
	
	checkEditar.on('ifChecked', function(){console.log("entro a checked");
		$("#formPax :input").prop('disabled', false);
		if(parentesco.val() == NO_TIENE_PARENTESCO){
			$('.opcional').val("");
			$('.opcional').prop('disabled', true);
		}
	});
	
	checkEditar.on('ifUnchecked', function(){console.log("entro a UNchecked");
		$("#formPax :input").prop('disabled', true);
	});
	
	checkNuevo.on('ifChecked', function(){console.log("entro a checked");
		limpiarForm();
		checkEditar.iCheck('uncheck');
		checkEditar.iCheck('disable');
		tablaPacientes.$('tr.filaActiva').removeClass('filaActiva');
		$("#formPax :input").prop('disabled', false);
//		switcheryCheck.setPosition(false);
		switcheryCheck.disable();
	});

	checkNuevo.on('ifUnchecked', function(){console.log("entro a UNchecked");
		limpiarForm();
		checkEditar.iCheck('disable');
		$("#formPax :input").prop('disabled', true);
	});
	
	parentesco.change(function(){
		
		if($(this).val() == NO_TIENE_PARENTESCO){
			$('.opcional').val("");
			$('.opcional').prop('disabled', true);
		}else{
			$('.opcional').prop('disabled', false);
		}
	});
	
	formPax.submit(function(event){
		event.preventDefault();
		if(validarFormulario()){
			swal({
				  title: ' Desea guardar los cambios?',
				  showCancelButton: true,
				  confirmButtonText: 'Guardar',
				  cancelButtonText: 'Cancelar',
				  showLoaderOnConfirm: true,
				  closeOnConfirm: false,
				  type: "warning"
			},function(){
				guardarPaciente();
			});
		}else{
			notify("error", "Cuidado!", "Debe llenar todos los campos obligatorios")
		}
	});
	
	
	/****** SECCION FUNCIONES *******/
	
	function guardarPaciente(){
		
		$('#formPax :input').each(function(){ this.value = this.value.toUpperCase() });
		var usuarioDTO = {usuario:{},telefonos:[{}, {}, {}], pacienteDTO:{paciente:{}, estimulador:{}, responsables:[{}]}};
		usuarioDTO.usuario.usuario = $('#usuario').val();
		usuarioDTO.usuario.password = $('#password').val();
		usuarioDTO.usuario.idTipoUsuario = 3;
		
		usuarioDTO.pacienteDTO.paciente.nombre = $('#nombrePax').val(); 
		usuarioDTO.pacienteDTO.paciente.apaterno = $('#apaternoPax').val(); 
		usuarioDTO.pacienteDTO.paciente.amaterno = $('#amaternoPax').val(); 
		usuarioDTO.pacienteDTO.paciente.direccion = $('#direccion').val(); 
		usuarioDTO.pacienteDTO.paciente.email = $('#email').val(); 
		
		usuarioDTO.telefonos[0].numero = telCel.inputmask('unmaskedvalue');
		usuarioDTO.telefonos[0].idTipoTelefono = 1;
		usuarioDTO.telefonos[1].numero = telCasa.inputmask('unmaskedvalue');
		usuarioDTO.telefonos[1].idTipoTelefono = 2;
		usuarioDTO.telefonos[2].numero = telOf.inputmask('unmaskedvalue');
		usuarioDTO.telefonos[2].idTipoTelefono = 3;
		
		usuarioDTO.pacienteDTO.responsables[0].nombre = $('#nombreResp').val(); 
		usuarioDTO.pacienteDTO.responsables[0].apaterno = $('#apaternoResp').val(); 
		usuarioDTO.pacienteDTO.responsables[0].amaterno = $('#amaternoResp').val(); 
		usuarioDTO.pacienteDTO.responsables[0].idTipoParentesco = $('#parentesco').val(); 
		
		var parametro = "true";
		if(!checkNuevo.is(':checked')){
			parametro = "false";
			var idUser = pacienteGuardado.usuario.id;
			usuarioDTO.usuario.id = idUser;
			usuarioDTO.pacienteDTO.paciente.idUsuario = idUser;
			usuarioDTO.pacienteDTO.paciente.id = pacienteGuardado.pacienteDTO.paciente.id;
			usuarioDTO.telefonos[0].id = pacienteGuardado.telefonos[0].id;
			usuarioDTO.telefonos[0].idUsuario = pacienteGuardado.telefonos[0].idUsuario;
			usuarioDTO.telefonos[1].id = pacienteGuardado.telefonos[1].id;
			usuarioDTO.telefonos[1].idUsuario = pacienteGuardado.telefonos[1].idUsuario;
			usuarioDTO.telefonos[2].id = pacienteGuardado.telefonos[2].id;
			usuarioDTO.telefonos[2].idUsuario = pacienteGuardado.telefonos[2].idUsuario;
			usuarioDTO.pacienteDTO.responsables[0].id = pacienteGuardado.pacienteDTO.responsables[0].id;
		}
		
		$.ajax({
			url: ctx+'/usuario?nuevo='+parametro,
			type: 'POST',
			dataType: 'json',
			data: {usuarioDTO: JSON.stringify(usuarioDTO)},
			success: function(respuesta){
				if(respuesta.codigo === -1){
					swal("Exito", "Los cambios se guardaron correctamente", "success");
					limpiarForm();
					cargarPacientes(false);
				}else{
					swal("Error", respuesta.mensaje, "error");
				}
			}, error: function(){
				swal("Error", "No fue posible guardar la informacion, Intente mas tarde.", "error");
			}
		});
	}
	
	function validarFormulario(){
		var formValido = true;
//		$("#formPax :input").removeClass('bordeRojo');
		$("#formPax *").filter('.form-group').removeClass('has-error');
		$('#formPax *').filter(':input').each(function(){
			var inpt = $(this);
		    if(inpt.hasClass('editar')){
		    	if(inpt.val() === ""){
//		    		inpt.addClass('bordeRojo');
		    		inpt.closest('.form-group').addClass('has-error');
		    		formValido = false;
		    	}
		    }else if(inpt.hasClass('opcional')){
		    	if($('#parentesco').val() != NO_TIENE_PARENTESCO){console.log("NO ES 13");
			    	if(inpt.val() === ""){
			    		inpt.closest('.form-group').addClass('has-error');
//			    		inpt.addClass('bordeRojo');
			    		formValido = false;
			    	}
		    	}else{
		    		inpt.val("");
		    	}
		    }
		});
		return formValido;
	}
	
	function cargarPacientes(recarga){
		$.ajax({
			url: ctx+'/paciente',
			type: 'GET',
			dataType: 'json',
			success: function(pacientes){
				tablaPacientes.clear();
				tablaPacientes.rows.add(pacientes).draw(recarga);
			}, error: function(){
				
			}
		});
	}
	
	function cargarTiposResponsable(){
		$.ajax({
			url: ctx+'/catalogo/tiposParentescos',
			type: 'GET',
			dataType: 'json',
			success: function(parentescos){
				$.each(parentescos, function (i, parent) {
				    parentesco.append($('<option>', { 
				        value: parent.id,
				        text : parent.tipo 
				    }));
				});
			}, error: function(){
				
			}
		});
	}
	
	function limpiarForm(){
		errorEnForm = false;
		formPax.trigger("reset");
		formVinc.trigger("reset");
		$('#formVinc :input').removeClass('fondoRojo fondoVerde');
		$("#formPax :input").removeClass('bordeRojo');
	}
	
	function init(){
		checkEditar.iCheck('disable');
		$("#formPax :input").prop('disabled', true);
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