package com.sysdt.estimuladorapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sysdt.estimuladorapp.dto.ObjetoResponse;
import com.sysdt.estimuladorapp.dto.UsuarioDTO;
import com.sysdt.estimuladorapp.enums.RespuestaWS;
import com.sysdt.estimuladorapp.enums.TipoUsuarioEnum;
import com.sysdt.estimuladorapp.service.interfaces.UsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public ObjetoResponse login(HttpServletRequest request, Model model) throws Exception{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioCompleto(username, password);
		ObjetoResponse or = new ObjetoResponse();
		or.setCodigo(usuarioDTO.getCodigoError());
		or.setMensaje(usuarioDTO.getMensajeWS());
		if(usuarioDTO.getCodigoError() == RespuestaWS.EXITO.getId()){
			if(usuarioDTO.getUsuario().getIdTipoUsuario() == TipoUsuarioEnum.ADMINISTRADOR.getId() || 
					usuarioDTO.getUsuario().getIdTipoUsuario() == TipoUsuarioEnum.MEDICO.getId()){
				
				request.getSession().setAttribute("usuarioDTO", usuarioDTO);
				if(usuarioDTO.getAdministrador().getId() != null){
					model.addAttribute("nomUser", usuarioDTO.getAdministrador().getNombre()+" "+
					usuarioDTO.getAdministrador().getApaterno()+" "+usuarioDTO.getAdministrador().getAmaterno());
				}else{
					model.addAttribute("nomUser",usuarioDTO.getMedico().getNombre()+" "+
							usuarioDTO.getMedico().getApaterno()+" "+usuarioDTO.getMedico().getAmaterno());
				}
				model.addAttribute("tipoUser", usuarioDTO.getUsuario().getIdTipoUsuario());
			}else{
				or.setCodigo(RespuestaWS.EXCEPCION.getId());
				or.setMensaje("Usted no tiene acceso a este sistema");
			}
			
		}
		return or;
	}
	
	@RequestMapping(value="/acceso", method = RequestMethod.GET)
	public String loginRedirect(HttpServletRequest request){
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession(false).getAttribute("usuarioDTO");
		return "principal";
		
	}
	
	@RequestMapping("/accesoDenegado")
	public String accesoDenegado(){
		return "login";
		
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String cerrarSesion(HttpServletRequest request){
		request.getSession().invalidate();
		request.setAttribute("sesion", "exito");
		return "login";
	}
	
}
