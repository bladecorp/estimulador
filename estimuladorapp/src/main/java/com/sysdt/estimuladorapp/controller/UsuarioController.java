package com.sysdt.estimuladorapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysdt.estimuladorapp.dto.ObjetoResponse;
import com.sysdt.estimuladorapp.dto.UsuarioDTO;
import com.sysdt.estimuladorapp.service.interfaces.UsuarioService;
import com.sysdt.estimuladorapp.util.MensajesUtil;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ObjetoResponse procesarUsuarioMedico(HttpServletRequest request) throws Exception{
//		int respuesta = usuarioService.insertarUsuarioCompleto(usuarioDTO);
		boolean nuevo = Boolean.parseBoolean(request.getParameter("nuevo"));
		String u = request.getParameter("usuarioDTO");
		ObjectMapper om = new ObjectMapper();
		UsuarioDTO usuarioDTO = om.readValue(u, UsuarioDTO.class);
		ObjetoResponse response = new ObjetoResponse();
		int res;
		if(nuevo){
			res = usuarioService.insertarUsuarioCompleto(usuarioDTO);
		}else{
			res = usuarioService.actualizarUsuarioCompleto(usuarioDTO);
		}
		response.setCodigo(res);
		response.setMensaje(MensajesUtil.mostrarError(res, "usuario"));
		return response;
	}
	
}
