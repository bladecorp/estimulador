package com.sysdt.estimuladorapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysdt.estimuladorapp.dto.MedicoDTO;
import com.sysdt.estimuladorapp.model.Medico;
import com.sysdt.estimuladorapp.service.interfaces.MedicoService;
import com.sysdt.estimuladorapp.service.interfaces.UsuarioService;

@RestController
@RequestMapping("/medico")
public class MedicoController {

	@Autowired
	private MedicoService medicoService;
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping("/all")
	public List<Medico> obtenerMedicos() throws Exception{
		return medicoService.obtenerMedicos();
	}
	
	@RequestMapping("/{id}")
	public MedicoDTO obtenerMedicoCompletoPorId(@PathVariable("id") int id) throws Exception{
		return medicoService.obtenerMedicoCompletoPorId(id);
	}
	
	
	@RequestMapping("/estado")
	public void actualizarEstado(HttpServletRequest request){
		int idMedico = Integer.parseInt(request.getParameter("idMedico"));
		boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
		medicoService.actualizarEstadoMedico(idMedico, estado);
	}
	
}
