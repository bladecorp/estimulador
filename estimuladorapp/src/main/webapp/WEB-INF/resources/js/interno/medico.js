$(document).ready(function(){
	
	/******** 	SECCION INICIALIZACION VARIABLES ****/
	
	// lat = 19.4362086, long = -99.1546298
	var latitud = 0;
	var longitud = 0;
	var idPacienteSel = undefined;
	var idEstimulador = undefined;
	var tiposOnda;
	var indexFila;
	var map;
	$('input[name=tipoB]').iCheck({radioClass: 'iradio_flat-green'});
	var paxSel = $('#paxSel');
	var estimSel = $('#estimSel');
	var vinculado = $('#vinculado');
	var mesSelect = $('#mesSelect');
	var anioSelect = $('#anioSelect');
	var formHistorico = $('#formHistorico');
	var tablaHistorico = $('#tablaHistorico').DataTable({
		language: {"emptyTable": "No se encontraron historicos"},
		lengthMenu: [[6, 15, 20, -1], [6, 15, 20, "Todos"]],
		language: {
            lengthMenu: "Mostrar _MENU_ registros",
            zeroRecords: "No hay historicos",
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
			{data:"fechaInicio"},{data:"fechaFin"},{data:"tiempo"},{data:"frecuencia"},
			{data:"amplitud"},{data:"tipoOnda"},{data:"estado"}
		],
		columnDefs: [{targets: 6,data: "estado",
		    "render": function ( data, type, full, meta ) {
		    	if(data === 1){
		    		return '<i class="fa fa-check fa-lg" style="color:green;"></i>';
		    	}else{
		    		return '<i class="fa fa-times fa-lg" style="color:red;"></i>';
		    	}
		    }
		  },{targets: 0,data: "fechaInicio",
			    "render": function ( data, type, full, meta ) {
			    	var f = new Date(data);
			    	return f.getDate()+"/"+(f.getMonth()+1)+"/"+f.getFullYear()+" "+f.getHours()+":"+f.getMinutes();
			    }
		  },{targets: 1,data: "fechaFin",
			    "render": function ( data, type, full, meta ) {
			    	var f = new Date(data);
			    	return f.getDate()+"/"+(f.getMonth()+1)+"/"+f.getFullYear()+" "+f.getHours()+":"+f.getMinutes();
			    }
		  },{targets: 2,data: "tiempo",
			    "render": function ( data, type, full, meta ) {
			    	return data + " mins";
			    }
		  },{targets: 3,data: "frecuencia",
			    "render": function ( data, type, full, meta ) {
			    	return data + " Hz";
			    }
		  },{targets: 4,data: "amplitud",
			    "render": function ( data, type, full, meta ) {
			    	return data + " %";
			    }
		  },{targets: 5,data: "tipoOnda",
			    "render": function ( data, type, full, meta ) {
			    	var tipoOnda = "";
			    	$.each(tiposOnda, function(i, tipoO){
			    		if(data === tipoO.id){console.log("Vuelta: "+i);
			    			tipoOnda = tipoO.tipo;
			    			return false;
			    		}
			    	});
			    	return tipoOnda;
			    }
		  }]
	});
	
	var paxAutocomplete = $('#paxAutocomplete').autocomplete({
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
	    	var itemSel = ui.item.label;
	    	$(this).val("");
	    	console.log(itemSel);
	    	idPacienteSel = ui.item.value;
	    	obtenerEstimulador();
	    	paxSel.text(itemSel);
	    	tablaHistorico.clear().draw();
	        event.preventDefault();
	    }
	});
	
	/******** 	SECCION INICIO PAGINA ****/
	
	init();
	
	/******** 	SECCION EVENTOS ****/
	
	$("input[name=tipoB]:radio").on('ifChecked',function(){
		console.log($(this).val());
		paxAutocomplete.val("");
	});
	
	formHistorico.submit(function(event){
		event.preventDefault();
		if(idPacienteSel === undefined){
			notify("warning", "Cuidado!", "Primero debe seleccionar un paciente");
		}else if(idEstimulador === undefined){
			notify("warning", "Lo siento!", "El paciente no cuenta con un estimulador asociado");
		}else{
			obtenerHistorico();
		}
		
	});
	
	tablaHistorico.on("click", "tr", function(){
		if(!$(this).hasClass("filaActiva")){
			histSeleccionado = tablaHistorico.row(this).data();console.log("Fila: ");console.log(histSeleccionado);
			var idHist = histSeleccionado.id;console.log("idEstim: "+idHist);
			indexFila = tablaHistorico.row(this);
			tablaHistorico.$('tr.filaActiva').removeClass('filaActiva');
			$(this).addClass("filaActiva");
			longitud = histSeleccionado.longitud;
			latitud = histSeleccionado.latitud;
			initialize();
			google.maps.event.trigger(map, 'resize');
		}
	});
	
	/******** 	SECCION FUNCIONES ****/
	
	function obtenerHistorico(){
		tablaHistorico.clear().draw();
		$.ajax({
			url: ctx+'/historico',
			type: 'GET',
			dataType: 'json',
			data: {
				idPaciente: idPacienteSel, idEstimulador: idEstimulador , mes: mesSelect.val() , anio: anioSelect.val() 
			},
			success: function(historicos){console.log(historicos);
				if(historicos.length > 0){
					tablaHistorico.rows.add(historicos).draw(true);
				}else{
					notify("warning", "Lo siento!", "No se encontraron registros en ese periodo");
				}
			}, error: function(){
				notify("error", "Error", "No fue posible obtener la informaci&oacute;n del hist&oacute;rico");
			}
			
		});
	}
	
	function obtenerEstimulador(){
		$.ajax({
			url: ctx+'/paciente/pax/'+idPacienteSel,
			type: 'GET',
			dataType: 'json',
			success: function(pacienteDTO){console.log(pacienteDTO);
				if(pacienteDTO.estimulador.id !== null){
					idEstimulador = pacienteDTO.estimulador.id;
					estimSel.text(pacienteDTO.estimulador.serie).css('color', '');
					if(pacienteDTO.vinculado === true){
						vinculado.text('SI').css('color', 'green');
					}else{
						vinculado.text('NO').css('color', 'red');
					}
				}else{
					idEstimulador = undefined;
					estimSel.text("NO TIENE").css('color', 'red');
					vinculado.text('NO').css('color', 'red');
				}
				notify("info", "Exito", "Se obtuvo la informaci&oacute;n del paciente de forma correcta");
			}, error: function(){
				idPacienteSel = undefined;
				idEstimulador = undefined;
				notify("error", "Error", "No se pudo obtener la informaci&oacute;n del paciente");
			}
			
		});
	}
	
	function obtenerTiposOnda(){
		$.ajax({
			url: ctx+'/catalogo/tiposOnda',
			type: 'GET',
			dataType: 'json',
			success: function(tiposO){
				tiposOnda = tiposO;
			}, error: function(){
				notify("error", "Error", "Error al obtener los tipos de onda");
			}
		});
	}
	
	function obtenerAnios(){
		var fecha = new Date();
		var anioActual = fecha.getFullYear();
		for(i = anioActual; i>= anioActual-1; i--){
			anioSelect.append($('<option>', {
				value: i,
				text: i
			}));
		}
	}

	function notify(tipo, titulo, mensaje){
		new PNotify({
		     title: titulo,
		     text: mensaje,
		     type: tipo,
		     delay: 2000
		  }); 
	}

	function initialize() {
//		var myCenter = new google.maps.LatLng(19.4362086,-99.1546298);
		var myCenter = new google.maps.LatLng(latitud,longitud);
		var myProp = {
				center: myCenter,
				zoom:15,
				mapTypeId:google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById("googleMap"),myProp);
		var marker=new google.maps.Marker({
		  position:myCenter,
		});
		marker.setMap(map);
	}
	
	function init(){
		google.maps.event.addDomListener(window, 'load', initialize);
//		PNotify.prototype.options.styling = "bootstrap3";
//		swal.setDefaults({
//			allowOutsideClick: false,
//			confirmButtonColor: '#169F85',
//		});
		obtenerAnios();
		obtenerTiposOnda();
	}
	
});

