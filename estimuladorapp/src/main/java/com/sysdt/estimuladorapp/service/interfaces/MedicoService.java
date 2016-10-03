package com.sysdt.estimuladorapp.service.interfaces;

import java.util.List;

import com.sysdt.estimuladorapp.dto.MedicoDTO;
import com.sysdt.estimuladorapp.model.Medico;

public interface MedicoService {

	public List<Medico> obtenerMedicos() throws Exception;
	
	public MedicoDTO obtenerMedicoCompletoPorId(int idMedico) throws Exception;
	
	public Medico obtenerMedicoPorId(Integer id)throws Exception;
	
	public Medico obtenerMedicoPorIdUsuario(Integer idUsuario) throws Exception;
	
	public boolean buscarDuplicidadMedico(String nombre, String apaterno, String amaterno, Integer idUsuario)throws Exception;
	
	public int insertarMedico(Medico medico)throws Exception;
	
	public int actualizarMedico(Medico medico)throws Exception;
	
	public void actualizarEstadoMedico(int idMedico, boolean estado);
	
	public int eliminarMedico(Integer id)throws Exception;
	
	public int eliminarPorIdUsuario(Integer idUsuario) throws Exception;
	
}
