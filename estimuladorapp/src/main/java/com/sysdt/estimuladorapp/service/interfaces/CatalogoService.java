package com.sysdt.estimuladorapp.service.interfaces;

import java.util.List;

import com.sysdt.estimuladorapp.model.Tipoonda;
import com.sysdt.estimuladorapp.model.Tipoparentesco;
import com.sysdt.estimuladorapp.model.Tipotelefono;
import com.sysdt.estimuladorapp.model.Tipousuario;

public interface CatalogoService {

	public List<Tipousuario> obtenerTiposUsuario() throws Exception;
	
	public List<Tipotelefono> obtenerTiposTelefono() throws Exception;
	
	public List<Tipoparentesco> obtenerTiposParentesco() throws Exception;
	
	public List<Tipoonda> obtenerTiposOnda() throws Exception;
	
}
