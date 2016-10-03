package com.sysdt.estimuladorapp.service.interfaces;

import java.util.List;

import com.sysdt.estimuladorapp.model.Telefono;

public interface TelefonoService {

	public List<Telefono> obtenerTelefonosPorIdUsuario(Integer idUsuario) throws Exception;
	
	public int insertarTelefono(Telefono telefono) throws Exception;
	
	public void actualizarTelefonos(int idUsuario, List<Telefono> telefonos) throws Exception;
	
	public int eliminarTelefono(Integer id) throws Exception;
	
	public int eliminarPorIdUsuario(Integer idUsuario) throws Exception;
	
}
