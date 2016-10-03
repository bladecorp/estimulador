$(document).ready(function(){
	
	/********** SECCION VARIABLES GLOBALES *********/
	var repeticiones = 5;
	var tiempo = 3;
	var frecuencia = 10.6;
	var amplitud = 20;
	var tipoOnda;
	var fechaIsep;
	var fechaFsep;
	var inicioDes;
	var finDes;
	$('input[name=tipoB]').iCheck({radioClass: 'iradio_flat-green'});
	var paxSel = $('#paxSel');
	var idPacienteSel = undefined;
	var idEstimulador = undefined;
	var isVinculado = false;
	var isActivado = false;
	var estimSel = $('#estimSel');
	var vinculado = $('#vinculado');
	var buscarPax = $('#buscarPax').autocomplete({
		 source: function (request, response) {
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
				 response("");
			 }
	    },
	    select: function (event, ui) {
	    	var paxSelect = ui.item.label;
	    	$(this).val("");
	    	paxSel.text(paxSelect);
	    	console.log(paxSel);
	    	idPacienteSel = ui.item.value;
	    	obtenerEstimulador();
	        event.preventDefault();
	    }
	});
	
	var formEstimulacion = $('#formEstimulacion');
	var tiposOnda = $('#tiposOnda');
	var horaInicio = "00:00";
	var horaFin = "23:59";
	var fechaInicio = moment();
	var fechaFin = moment();
	var fechaInicioFormat = moment().format('DD/MM/YYYY');
	var fechaFinFormat = moment().format('DD/MM/YYYY');
	
	$("#repeticiones").knob({
		min:1,
		max:50,
		displayPrevious: true,
		width:140,
		height:140,
        draw : function () { $(this.i).val(this.cv + ' rep').css('font-size', '17pt'); },
        change: function(v){
        	console.log(v);
//        	console.log(this.cv);
        	console.log(Math.round(v));
        	repeticiones = Math.round(v);
        }
	});
	$("#tiempo").knob({
		min:1,
		max:50,
		displayPrevious: true,
		width:140,
		height:140,
        draw : function () { $(this.i).val(this.cv + ' min').css('font-size', '17pt'); },
        change: function(v){
        	tiempo = Math.round(v);
        }
	});
	$("#frecuencia").knob({
		min:0.1,
		step:0.1,
		displayPrevious: true,
		width:140,
		height:140,
        draw : function () { $(this.i).val(this.cv + ' hz').css('font-size', '16pt'); },
        change: function(v){
        	frecuencia = v.toFixed(1);
        	console.log(frecuencia);
        }
	});
	$("#amplitud").knob({
		min:1,
		displayPrevious: true,
		width:140,
		height:140,
        draw : function () { $(this.i).val(this.cv + '%').css('font-size', '17pt'); },
        change: function(v){
        	amplitud = Math.round(v);
        }
	});
	
	var fechas = $('#fechas').daterangepicker({
	    "locale": {
	        "format": "DD/MM/YYYY",
	        "separator": " al ",
	        "applyLabel": "Guardar",
	        "cancelLabel": "Cancelar",
	        "fromLabel": "De",
	        "toLabel": "A",
	        "customRangeLabel": "Custom",
	        "weekLabel": "S",
	        "daysOfWeek": ["Do","Lu","Ma","Mi","Ju","Vi","Sa"],
	        "monthNames": ["Enero","Febrero","Marzo","Abril","Mayo","Junio",
	            "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"],
	        "firstDay": 1
	    },
	    "opens": "left",
	    "drops": "up",
	    startDate: fechaInicio,
        endDate: fechaFin
	}, function(start, end, label) {
		fechaInicioFormat = start.format('DD/MM/YYYY');
		fechaFinFormat = end.format('DD/MM/YYYY');
	  console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
	});
	
	var rangoHora = $("#rangoHora").ionRangeSlider({
		type: 'double',
//	    min: +moment().subtract(12, "hours").format("X"),
		min: moment().startOf('day'),
//	    max: +moment().format("X"),
		max: moment().endOf('day'),
//	    from: +moment().subtract(6, "hours").format("X"),
	    grid: true,
	    keyboard: true,
	    keyboard_step: 0.06,
	    force_edges: true,
	    prettify: function (num) {
	    	return moment(num).format("LT");
//	        var m = new Date(num); 
//	        var min = m.getMinutes();
//	        return m.getHours()+":"+(min > 9?min:"0"+min);
	    }
	});
	
	/************* SECCION EVENTOS *********/
	
	formEstimulacion.submit(function(event){
		event.preventDefault();
		console.log(repeticiones+", "+tiempo+", "+frecuencia+", "+amplitud);
		var tipoOndaTexto = $('#tiposOnda :selected').text();
		tipoOnda = tiposOnda.val();
		var value = $('#rangoHora').prop("value").split(";"); console.log(value);console.log(parseInt(value[0]));
		var inicio = moment(parseInt(value[0])).format("HH:mm"); inicioDes = inicio.split(":");
		var fin = moment(parseInt(value[1])).format("HH:mm"); finDes = fin.split(":");
		console.log(inicio);console.log(fin);
		fechaIsep = fechaInicioFormat.split("/");
		fechaFsep = fechaFinFormat.split("/");
		if(validarEstimulacion()){
			if(value[0] !== value[1]){
				swal({
					  title: 'Enviar estimulaci&oacute;n?',
					  showCancelButton: true,
					  confirmButtonText: 'Enviar',
					  cancelButtonText: 'Cancelar',
					  showLoaderOnConfirm: true,
					  closeOnConfirm: false,
					  html: true,
					  text: "<div><div style='text-align:left;margin:0 auto;width:75%;'> " +
					  			"<table><tbody> " +
					  			"<tr> <td>Paciente:</td> <td style='font-weight:bold;'>"+paxSel.text()+"</td> </tr>"+
					  			"<tr> <td>Repeticiones:</td> <td style='font-weight:bold;'>"+repeticiones+"</td> </tr>"+
					  			"<tr> <td>Tiempo:</td> <td style='font-weight:bold;'>"+tiempo+" Minutos</td> </tr>"+
					  			"<tr> <td>Frecuencia:</td><td style='font-weight:bold;'>"+frecuencia+" Hz</td> </tr>"+
					  			"<tr> <td>Amplitud:</td><td style='font-weight:bold;'>"+amplitud+" %</td> </tr>"+
					  			"<tr> <td>Tipo de Onda:</td><td style='font-weight:bold;'>"+tipoOndaTexto+"</td> </tr>"+
					  			"<tr> <td>Fechas:</td><td style='font-weight:bold;'>Del "+fechaInicioFormat+" al "+fechaFinFormat+"</td> </tr>"+
					  			"<tr> <td>Horarios:</td><td style='font-weight:bold;'>Entre las "+inicio+" y las "+fin+"</td> </tr>"+
					  			"</tbody></table> "+
					  		"</div></div>"
				},function(){
					enviarEstimulacion();
				});
			}else{
				notify("warning", "Cuidado!", "El rango de tiempo no puede tener la misma hora de inicio y termino");
			}
		}
	});
	
	/************** SECCION INICIALIZACION DE PAGINA **********/
	init();
	
	/************* SECCION FUNCIONES ******************/
	
	function obtenerEstimulador(){
		$.ajax({
			url: ctx+'/paciente/pax/'+idPacienteSel,
			type: 'GET',
			dataType: 'json',
			success: function(pacienteDTO){console.log(pacienteDTO);
				var estimulador = pacienteDTO.estimulador;
				isActivado = false;
				if(pacienteDTO.paciente.enabled){
					isActivado = true;
				}
				if(estimulador.id !== null){
					idEstimulador = estimulador.id;
					estimSel.val(estimulador.serie);
					if(pacienteDTO.vinculado){
						isVinculado = true;
						vinculado.val('SI');
						vinculado.removeClass('fondoRojo').addClass('fondoVerde');
					}else{
						isVinculado = false;
						vinculado.val('NO');
						vinculado.removeClass('fondoVerde').addClass('fondoRojo');
					}
				}else{
					isVinculado = false;
					idEstimulador = undefined;
					estimSel.val("NO TIENE");
					vinculado.val('NO');
					vinculado.removeClass('fondoVerde').addClass('fondoRojo');
				}
				notify("info", "Exito", "Se obtuvo la informaci&oacute;n del paciente de forma correcta");
			}, error: function(){
				idPacienteSel = undefined;
				idEstimulador = undefined;
				notify("error", "Error", "No se pudo obtener la informaci&oacute;n del paciente");
			}
			
		});
	}
	
	function enviarEstimulacion(){
		$.ajax({
			url: ctx+'/estimulador/estimular',
			type:'POST',
			dataType: 'json',
			data: {
				estimulacion: amplitud+";"+frecuencia+";"+tiempo+";"+repeticiones+";"+fechaIsep[2]+";"+
					fechaFsep[2]+";"+fechaIsep[1]+";"+fechaFsep[1]+";"+fechaIsep[0]+";"+fechaFsep[0]+";"+
					inicioDes[0]+";"+finDes[0]+";"+inicioDes[1]+";"+finDes[1]+";"+tipoOnda,
				idPaciente: idPacienteSel
			},
			success: function(resp){
				if(resp.codigo === -1){
					swal("Exito", "Estimulacion enviada exitosamente", "success");
				}else{
					swal("Error", resp.mensaje, "error");
				}
			}, error: function(){
				swal("Error", "No fue posible enviar la estimulación. Intente mas tarde", "error");
			}
		});
	}
	
	function validarEstimulacion(){
		var exito = true;
		if(idPacienteSel === undefined){
			notify("error", "Cuidado!", "Primero debe seleccionar un paciente");
			exito = false;
		}else if(!isActivado){
			notify("error", "Lo siento!", "No se puede enviar la estimulaci&oacute;n porque la cuenta del paciente est&aacute; deshabilitada");
			exito = false;
		}else if(idEstimulador === undefined){
			notify("error", "Lo siento!", "No se puede enviar la estimulaci&oacute;n porque el paciente no tiene un estimulador asociado");
			exito = false;
		}else if(!isVinculado){
			notify("error", "Lo siento!", "No se puede enviar la estimulaci&oacute;n porque el paciente a&uacute;n no est&aacute; vinculado");
			exito = false;
		}
		return exito;
	}
	
	function init(){
//		swal.setDefaults({
//			allowOutsideClick: false,
//			confirmButtonColor: '#169F85',
//		});
//		PNotify.prototype.options.styling = "bootstrap3";
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