package com.sysdt.estimuladorapp.service.interfaces;

import java.util.List;

import com.sysdt.estimuladorapp.model.Responsable;

public interface ResponsableService {

	public List<Responsable> obtenerResponsablesPorIdPaciente(Integer idPaciente)throws Exception;
	
	public int insertarResponsable(Responsable responsable) throws Exception;
	
	public int actualizarResponsable(Responsable responsable) throws Exception;
	
	public int eliminarResponsable(Integer idResponsable)throws Exception;
	
}
