package com.sysdt.estimuladorapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sysdt.estimuladorapp.dto.PacienteDTO;
import com.sysdt.estimuladorapp.dto.UsuarioDTO;
import com.sysdt.estimuladorapp.model.Paciente;
import com.sysdt.estimuladorapp.service.interfaces.PacienteService;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

	@Autowired
	private PacienteService pacienteService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Paciente> obtenerPacientes() throws Exception{
		return pacienteService.obtenerPacientes();
	}
	
	@RequestMapping("/{id}")
	@ResponseBody
	public UsuarioDTO obtenerPacienteCompletoPorIdUsuario(@PathVariable(value="id") int idUsuario) throws Exception{
		UsuarioDTO usuarioDTO = pacienteService.obtenerPacienteCompletoPorIdUsuario(idUsuario);
		return usuarioDTO;
	}
	
	@RequestMapping("/filtro")
	public @ResponseBody List<Paciente> buscarPacienteFiltro(HttpServletRequest request){
		String filtro = request.getParameter("term");
		int tipoFiltro = Integer.parseInt(request.getParameter("tipoFiltro"));
		return pacienteService.obtenerPacientesPorFiltro(tipoFiltro, filtro);
	}
	
	@RequestMapping(value = "/estado", method = RequestMethod.GET)
	@ResponseBody
	public void cambiarEstadoPaciente(HttpServletRequest request){
		int idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
		boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
		pacienteService.actualizarEstadoPaciente(idPaciente, estado);
	}
	
	@RequestMapping("/pax/{id}")
	@ResponseBody
	public PacienteDTO obtenerPacienteCompletoPorId(@PathVariable(value="id") int idPaciente) throws Exception{
		return pacienteService.obtenerPacienteCompletoPorId(idPaciente);
	}
		
}
