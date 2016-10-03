package com.sysdt.estimuladorapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sysdt.estimuladorapp.model.Historico;
import com.sysdt.estimuladorapp.service.interfaces.HistoricoService;

@Controller
@RequestMapping("/historico")
public class HistoricoController {

	@Autowired
	private HistoricoService historicoService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Historico> obtenerHistorico(HttpServletRequest request) throws Exception{
		int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
		int anio = Integer.parseInt(request.getParameter("anio"));
		int mes = Integer.parseInt(request.getParameter("mes"));
		int idEstimulador = Integer.parseInt(request.getParameter("idEstimulador"));
//		idPaciente = 2;anio=2016;mes=3;idEstimulador=16;
		return historicoService.obtenerHistoricoPorFecha(mes, anio, idPaciente, idEstimulador);
	}
	
}
