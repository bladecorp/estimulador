$(document).ready(function(){
	
	var listaMedicos;
	var medicoGuardado;
	var checked = false;
	var checaUsuario = false;
	var indexFila;
	var errorEnForm = false;
	
	var checkEditar = $('#checkEditarMedico').iCheck({checkboxClass: 'icheckbox_square-purple'});
	var checkNuevo = $('#checkNuevoMedico').iCheck({checkboxClass: 'icheckbox_square-purple'});
	var formMedico = $('#formMedico');
	var telCel = $('#telCel').inputmask('(99) 9999-9999');
	var telCasa = $('#telCasa').inputmask('(99) 9999-9999');
	var telOf = $('#telOf').inputmask('(99) 9999-9999');
	var medicoEnabled = document.querySelector('#checkEnabled');
	var switcheryCheck = new Switchery(medicoEnabled, { size: 'small', disabled: true, color: '#26B99A' });
	var tablaMedicos = $('#tablaMedicos').DataTable({
		language: {"emptyTable": "No se encontraron medicos"},
		lengthMenu: [[10, 15, 20, -1], [10, 15, 20, "Todos"]],
		language: {
            lengthMenu: "Mostrar _MENU_ registros",
            zeroRecords: "No hay medicos",
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
	
	
	init();
	cargarTablaMedicos(true);
	
	/***** SELECCIONAR FILA ****/
	tablaMedicos.on("click", "tr", function(){
		if(!$(this).hasClass("filaActiva")){
			limpiarForm();
			$("#formMedico :input").prop('disabled', true);
			checkNuevo.iCheck('uncheck');
			checkEditar.iCheck('uncheck');
			checkEditar.iCheck('enable');
			checaUsuario = false;
			switcheryCheck.enable();
//			switcheryCheck.setPosition(!checked);
			var filaSeleccionada = tablaMedicos.row(this).data();console.log("Fila: ");console.log(filaSeleccionada);
			var idMedico = filaSeleccionada.id;console.log("idMed: "+idMedico);
			indexFila = tablaMedicos.row(this); console.log("idFila: ");
			tablaMedicos.$('tr.filaActiva').removeClass('filaActiva');
			$(this).addClass("filaActiva");
			$.ajax({
				url: ctx+'/medico/'+idMedico,
				dataType: 'json',
				data:{},
				success: function(medicoDTO){console.log(medicoDTO);
					var enabld = medicoDTO.medico.enabled?true:false;
					if(enabld !== checked){console.log("Valor enabld: "+enabld);console.log("Valor checked: "+checked);
						switcheryCheck.setPosition(true); 
						checked = enabld;
					}
					
					$('#nombreMed').val(medicoDTO.medico.nombre);
					$('#apaternoMed').val(medicoDTO.medico.apaterno);
					$('#amaternoMed').val(medicoDTO.medico.amaterno);
					$('#institucion').val(medicoDTO.medico.institucion);
					$('#consultorio').val(medicoDTO.medico.consultorio);
					$('#especialidad').val(medicoDTO.medico.especialidad);
					$('#cedula').val(medicoDTO.medico.cedula);
					var qtyTelefonos = medicoDTO.telefonos.length;
					$.each(medicoDTO.telefonos, function(index, telefono){
						if(telefono.idTipoTelefono === 1){
							telCel.val(telefono.numero);
						}else if(telefono.idTipoTelefono === 2){
							telCasa.val(telefono.numero);
						}else{
							telOf.val(telefono.numero);
						}
					});
					$('#usuario').val(medicoDTO.usuario.usuario);
					$('#password').val(medicoDTO.usuario.password);
					medicoGuardado = medicoDTO;
				}, error: function(){
					console.log("ENTRO EN ERROR");
				}
			});
			
		}
	});
	medicoEnabled.onchange = function() {
		console.log("Antes checked: "+checked);
		  if(checked !== medicoEnabled.checked){
			  checked = !checked;
			  var datosFila = indexFila.data();console.log("Despues checked: "+checked);
			  $.ajax({
				  url: ctx+'/medico/estado',
				  data: {estado: checked, idMedico: datosFila.id},
				  success: function(){
					  datosFila.enabled = checked;
					  tablaMedicos.row(indexFila).data(datosFila).draw(false);
					  notify("success", "Cambio de Estado", "El m&eacute;dico fue "+ (checked?'hablitado':'deshabilitado'));
				  }
			  }); 
		  }
		};
	
	formMedico.submit(function(event){
		event.preventDefault();
		validarFormulario();
		if(!errorEnForm){
			swal({
				  title: ' Desea guardar los cambios?',
				  showCancelButton: true,
				  confirmButtonText: 'Guardar',
				  cancelButtonText: 'Cancelar',
				  showLoaderOnConfirm: true,
				  closeOnConfirm: false,
				  type: "warning"
			},function(){
				enviarMedico();
			});
		}else{
			notify("error", "Cuidado!", "Debe llenar todos los campos obligatorios")
		}
	});
	
	checkEditar.on('ifChecked', function(){console.log("entro a checked");
	$("#formMedico :input").prop('disabled', false);
	});
	
	checkEditar.on('ifUnchecked', function(){console.log("entro a UNchecked");
	$("#formMedico :input").prop('disabled', true);
	});
	
	checkNuevo.on('ifChecked', function(){console.log("entro a checked");
		limpiarForm();
		checkEditar.iCheck('uncheck');
		checkEditar.iCheck('disable');
		tablaMedicos.$('tr.filaActiva').removeClass('filaActiva');
		$("#formMedico :input").prop('disabled', false);
//		switcheryCheck.setPosition(false);
		switcheryCheck.disable();
	});

	checkNuevo.on('ifUnchecked', function(){console.log("entro a UNchecked");
		limpiarForm();
		checkEditar.iCheck('disable');
		$("#formMedico :input").prop('disabled', true);
	});
	
	function enviarMedico(){
		$(':input').each(function(){ this.value = this.value.toUpperCase() });
		var usuario = {}; usuario.medico = {}; usuario.telefonos = []; usuario.usuario = {};
		usuario.medico.nombre = $('#nombreMed').val();
		usuario.medico.apaterno = $('#apaternoMed').val(); 
		usuario.medico.amaterno = $('#amaternoMed').val();
		usuario.medico.institucion = $('#institucion').val();
		usuario.medico.consultorio = $('#consultorio').val();
		usuario.medico.especialidad = $('#especialidad').val();
		usuario.medico.cedula = $('#cedula').val();
		var t1 = {}, t2 = {}, t3 = {};
		t1.numero = telCel.inputmask('unmaskedvalue'); t1.idTipoTelefono = 1;
		t2.numero = telCasa.inputmask('unmaskedvalue'); 
		t3.numero = telOf.inputmask('unmaskedvalue'); 
		usuario.telefonos.push(t1);
		t2.idTipoTelefono = 2;
		usuario.telefonos.push(t2);
		t3.idTipoTelefono = 3;
		usuario.telefonos.push(t3);
		usuario.usuario.usuario = $('#usuario').val();
		usuario.usuario.password = $('#password').val();
		usuario.usuario.idTipoUsuario = 2;
		console.log(usuario);
		var parametro = "true";
		if(!checkNuevo.is(':checked')){
			parametro = "false";
			var idUser = medicoGuardado.usuario.id;;
			usuario.usuario.id = idUser;
			usuario.medico.id = medicoGuardado.medico.id;
			usuario.medico.idUsuario = idUser;
			t1.id = medicoGuardado.telefonos[0].id;
			t1.idUsuario = idUser;
			t2.id = medicoGuardado.telefonos[1].id;
			t2.idUsuario = idUser;
			t3.id = medicoGuardado.telefonos[2].id;
			t3.idUsuario = idUser;
		}
		var json = JSON.stringify(usuario);console.log(json);	
		$.ajax({
			url: ctx+'/usuario?nuevo='+parametro,
			type: 'POST',
//			contentType:'application/json',
			dataType: 'json',
			data: {usuarioDTO: json},
			success: function(respuesta){
				if(respuesta.codigo === -1){
					swal("Exito", "Los cambios se guardaron correctamente", "success");
					limpiarForm();
					cargarTablaMedicos(false);
				}else{
					swal("Error", respuesta.mensaje, "error");
				}
			}, error: function(){
				swal("Error", "No fue posible guardar la informacion, Intente mas tarde.", "error");
			}
		});
	}
	
	function cargarTablaMedicos(isRecarga){
		$.ajax({
			url: ctx+'/medico/all',
			type: 'GET',
			dataType: 'json',
			data: {},
			success: function(medicos){
				listaMedicos = medicos;
				tablaMedicos.clear();
				$.each(listaMedicos, function(index, medico){
					medico.enabledT = medico.enabled?'ACTIVO':'INACTIVO';
				});  console.log(medicos);
				tablaMedicos.rows.add(listaMedicos).draw(isRecarga);
			}, error:function(){
				
			}
			
		});
	}
	
	function validarFormulario(){ console.log("entro a validacion");
		errorEnForm = false;
//		$("#formMedico :input").removeClass('bordeRojo');
		$("#formMedico *").filter('.form-group').removeClass('has-error');
		$('#formMedico *').filter(':input').each(function(){
			var inpt = $(this);
		    if(inpt.hasClass('editar')){
		    	if(inpt.val() === ""){
//		    		inpt.addClass('bordeRojo');
		    		inpt.closest('.form-group').addClass('has-error');
		    		errorEnForm = true;
		    	}
		    }
		});
	}
	
	function limpiarForm(){
		errorEnForm = false;
		formMedico.trigger("reset");
		$("#formMedico :input").removeClass('bordeRojo');
	}
	
	function init(){
		checkEditar.iCheck('disable');
		$("#formMedico :input").prop('disabled', true);
		console.log("Contexto");
		console.log(ctx);
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




