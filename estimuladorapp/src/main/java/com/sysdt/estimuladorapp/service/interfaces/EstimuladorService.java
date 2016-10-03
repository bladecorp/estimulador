package com.sysdt.estimuladorapp.service.interfaces;

import java.util.List;

import com.sysdt.estimuladorapp.model.Estimulador;

public interface EstimuladorService {

	public List<Estimulador> obtenerEstimuladores() throws Exception;
	
	public Estimulador obtenerEstimuladorPorIdPaciente(Integer idPaciente)throws Exception;
	
	public int insertarEstimulador(Estimulador estimulador) throws Exception;
	
	public int vincularEstimulador(Estimulador estimulador) throws Exception;
	
	public int actualizarEstimulador(Estimulador estimulador) throws Exception;
	
	public int desvincularEstimulador(Estimulador estimulador) throws Exception;
	
	public int enviarEstimulacion(int idPaciente, String estimulacion) throws Exception;
	
	
}
