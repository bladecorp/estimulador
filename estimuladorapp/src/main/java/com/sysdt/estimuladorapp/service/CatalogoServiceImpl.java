package com.sysdt.estimuladorapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.estimuladorapp.dao.TipoondaMapper;
import com.sysdt.estimuladorapp.dao.TipoparentescoMapper;
import com.sysdt.estimuladorapp.dao.TipotelefonoMapper;
import com.sysdt.estimuladorapp.dao.TipousuarioMapper;
import com.sysdt.estimuladorapp.model.Tipoonda;
import com.sysdt.estimuladorapp.model.Tipoparentesco;
import com.sysdt.estimuladorapp.model.Tipotelefono;
import com.sysdt.estimuladorapp.model.Tipousuario;
import com.sysdt.estimuladorapp.service.interfaces.CatalogoService;

@Service
@Transactional
public class CatalogoServiceImpl implements CatalogoService{
	
	@Autowired
	private TipousuarioMapper tipousuarioMapper;
	@Autowired
	private TipotelefonoMapper tipotelefonoMapper;
	@Autowired
	private TipoparentescoMapper tipoparentescoMapper;
	@Autowired
	private TipoondaMapper tipoondaMapper;

	public List<Tipousuario> obtenerTiposUsuario() throws Exception{
		return tipousuarioMapper.selectByExample(null);
	}
	
	public List<Tipotelefono> obtenerTiposTelefono() throws Exception{
		return tipotelefonoMapper.selectByExample(null);
	}
	
	public List<Tipoparentesco> obtenerTiposParentesco() throws Exception{
		return tipoparentescoMapper.selectByExample(null);
	}
	
	public List<Tipoonda> obtenerTiposOnda() throws Exception{
		return tipoondaMapper.selectByExample(null);
	}

}
