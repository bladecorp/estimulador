package com.sysdt.estimuladorapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sysdt.estimuladorapp.model.Tipoonda;
import com.sysdt.estimuladorapp.model.Tipoparentesco;
import com.sysdt.estimuladorapp.service.interfaces.CatalogoService;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

	@Autowired
	private CatalogoService catalogoService;
	
	@RequestMapping("/tiposParentescos")
	@ResponseBody
	public List<Tipoparentesco> obtenerParentescos() throws Exception{
		return catalogoService.obtenerTiposParentesco();
	}
	
	@RequestMapping("/tiposOnda")
	@ResponseBody
	public List<Tipoonda> obtenerTiposOnda() throws Exception{
		return catalogoService.obtenerTiposOnda();
	}
	
}
