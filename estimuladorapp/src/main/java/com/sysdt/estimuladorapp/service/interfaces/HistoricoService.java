package com.sysdt.estimuladorapp.service.interfaces;

import java.util.List;

import com.sysdt.estimuladorapp.dto.HistoricoDTO;
import com.sysdt.estimuladorapp.exceptions.ExcepcionHistorico;
import com.sysdt.estimuladorapp.model.Historico;

public interface HistoricoService {

	public List<Historico> obtenerHistoricoPorFecha(Integer mes, Integer anio, Integer idPaciente, Integer idEstimulador)throws Exception;
	
	public boolean registrarEventoHistorico(HistoricoDTO historicoDTO) throws ExcepcionHistorico;
 	
}
