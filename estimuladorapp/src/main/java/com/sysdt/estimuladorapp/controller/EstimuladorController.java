package com.sysdt.estimuladorapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysdt.estimuladorapp.dto.ObjetoResponse;
import com.sysdt.estimuladorapp.model.Estimulador;
import com.sysdt.estimuladorapp.model.Paciente;
import com.sysdt.estimuladorapp.service.interfaces.EstimuladorService;
import com.sysdt.estimuladorapp.service.interfaces.PacienteService;
import com.sysdt.estimuladorapp.util.MensajesUtil;

@Controller
@RequestMapping("/estimulador")
public class EstimuladorController {

	@Autowired
	private EstimuladorService estimuladorService;
	@Autowired
	private PacienteService pacienteService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Estimulador> obtenerEstimuladores() throws Exception{
		return estimuladorService.obtenerEstimuladores();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ObjetoResponse guardarEstimulador(HttpServletRequest request) throws Exception{
		String estim = request.getParameter("estimulador");
		boolean isNuevo = Boolean.parseBoolean(request.getParameter("nuevo"));
		ObjectMapper mapper = new ObjectMapper();
		Estimulador estimulador = mapper.readValue(estim, Estimulador.class);
		int res;
		if(isNuevo){
			res = estimuladorService.insertarEstimulador(estimulador);
		}else{
			res = estimuladorService.actualizarEstimulador(estimulador);
		}
		ObjetoResponse or = new ObjetoResponse();
		or.setCodigo(res);
		or.setMensaje(MensajesUtil.mostrarError(res, "estimulador"));
		return or;
	}
	
	@RequestMapping("/pax/{idPax}")
	public @ResponseBody Paciente obtenerPacienteVinculado(@PathVariable("idPax")int idPaciente) throws Exception{
		return pacienteService.obtenerPacientePorId(idPaciente);
	}
	
	@RequestMapping(value = "/desvincular", method = RequestMethod.POST)
	@ResponseBody
	public int desvincularEstimulador(HttpServletRequest request) throws Exception{
		String estim = request.getParameter("estim");
		ObjectMapper mapper = new ObjectMapper();
		Estimulador estimulador = mapper.readValue(estim, Estimulador.class);
		return estimuladorService.desvincularEstimulador(estimulador);
	}
	
	@RequestMapping(value="/vincular", method= RequestMethod.POST)
	@ResponseBody
	public ObjetoResponse vincularEstimulador(HttpServletRequest request) throws Exception{
		int idEstimulador = Integer.parseInt(request.getParameter("idEstimulador"));
		int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
		String serie = request.getParameter("serie");
		Estimulador estimulador = new Estimulador();
		estimulador.setId(idEstimulador);
		estimulador.setSerie(serie);
		estimulador.setIdPaciente(idPaciente);
		int res = estimuladorService.vincularEstimulador(estimulador); 
//		int res = -1;
		ObjetoResponse or = new ObjetoResponse();
		or.setCodigo(res);
		or.setMensaje(MensajesUtil.mostrarError(res, "estimulador"));
		return or;
	}
	
	@RequestMapping(value = "/estimular", method = RequestMethod.POST)
	@ResponseBody
	public ObjetoResponse enviarEstimulacion(HttpServletRequest request) throws Exception{
		int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
		String estimulacion = request.getParameter("estimulacion");
		int res = estimuladorService.enviarEstimulacion(idPaciente, estimulacion);
//		int res = -1;
		ObjetoResponse or = new ObjetoResponse();
		or.setCodigo(res);
		or.setMensaje(MensajesUtil.mostrarError(res, "estimulador"));
		return or;
	}
	
}
