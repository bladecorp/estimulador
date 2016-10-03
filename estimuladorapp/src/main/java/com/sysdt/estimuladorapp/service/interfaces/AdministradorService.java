package com.sysdt.estimuladorapp.service.interfaces;

import java.util.List;

import com.sysdt.estimuladorapp.model.Administrador;

public interface AdministradorService {

	public List<Administrador> obtenerAdministradores() throws Exception;
	
	public Administrador obtenerAdministradorPorId(Integer id);
	
	public Administrador obtenerAdministradorPorIdUsuario(Integer idUsuario) throws Exception;
	
	public boolean buscarDuplicidadAdministrador(String nombre, String apaterno, String amaterno)throws Exception;
	
	public int insertarAdministrador(Administrador admin)throws Exception;
	
	public int actualizarAdministrador(Administrador admin)throws Exception;
	
	public int eliminarAdministrador(Integer id) throws Exception;
	
	public int eliminarPorIdUsuario(Integer idUsuario) throws Exception;
	
}
