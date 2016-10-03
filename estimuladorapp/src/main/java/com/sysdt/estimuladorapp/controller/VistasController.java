package com.sysdt.estimuladorapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sysdt.estimuladorapp.model.Tipoonda;
import com.sysdt.estimuladorapp.service.interfaces.CatalogoService;

@Controller
public class VistasController {
	
	@Autowired
	private CatalogoService catalogoService;
	
	@RequestMapping("/")
	public String index(Model model){
		return "login";
	}
	
	@RequestMapping("/historial")
	public String n1(){
		return "n1";
	}
	
	@RequestMapping("/adminmedico")
	public String adminMedico(Model model) throws Exception{
		return "adminMedico";
	}
	
	@RequestMapping("/adminestimulador")
	public String adminEstimulador(){
		return "adminEstimulador";
	}
	
	@RequestMapping("/adminpaciente")
	public String adminPaciente(){
		return "adminPaciente";
	}
	
	@RequestMapping("/estimulacion")
	public String estimulacion(Model model) throws Exception{
		List<Tipoonda> tiposOnda = catalogoService.obtenerTiposOnda();
		model.addAttribute("tiposOnda", tiposOnda);
		return "estimulacion";
	}
	
}
