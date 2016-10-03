package com.sysdt.estimuladorapp.service.interfaces;

import java.util.List;

import com.sysdt.estimuladorapp.dto.PacienteDTO;
import com.sysdt.estimuladorapp.dto.UsuarioDTO;
import com.sysdt.estimuladorapp.model.Paciente;

public interface PacienteService {

	public List<Paciente> obtenerPacientes()throws Exception;
	
	public Paciente obtenerPacientePorId(Integer id)throws Exception;
	
	public PacienteDTO obtenerPacienteCompletoPorId(int idPaciente) throws Exception;
	
	public UsuarioDTO obtenerPacienteCompletoPorIdUsuario(int idUsuario) throws Exception;
	
	public List<Paciente> obtenerPacientesPorFiltro(int tipoFiltro, String valor);
	
	public Paciente obtenerPacientePorIdUsuario(Integer idUsuario)throws Exception;
	
	public List<PacienteDTO> obtenerPacientesCompletos() throws Exception;
	
	public PacienteDTO obtenerPacienteCompletoPorNombre(String nombre, String apaterno, String amaterno) throws Exception;
	
	public boolean buscarDuplicidadPaciente(String nombre, String apaterno, String amaterno, Integer idUsuario)throws Exception;
	
	public int insertarPaciente(Paciente paciente) throws Exception;
	
	public int actualizarPaciente(Paciente paciente) throws Exception;
	
	public void actualizarEstadoPaciente(int idPaciente, boolean estado);
	
}
