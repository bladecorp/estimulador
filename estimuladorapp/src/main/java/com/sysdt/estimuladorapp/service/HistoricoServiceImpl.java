package com.sysdt.estimuladorapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.estimuladorapp.dao.HistoricoMapper;
import com.sysdt.estimuladorapp.dto.HistoricoDTO;
import com.sysdt.estimuladorapp.exceptions.ExcepcionHistorico;
import com.sysdt.estimuladorapp.model.Historico;
import com.sysdt.estimuladorapp.model.HistoricoExample;
import com.sysdt.estimuladorapp.service.interfaces.HistoricoService;

@Service
@Transactional
public class HistoricoServiceImpl implements HistoricoService{

	@Autowired
	private HistoricoMapper historicoMapper;
	
	private static final Integer FECHA_INICIO = 1;
	private static final Integer FECHA_FIN = 2;

	/**
	 * Se obtiene historico por mes y anio.
	 * @param mes Mes que se desea buscar
	 * @param anio Anio que se desea buscar
	 * @param idPaciente identificador del paciente.
	 * @param idEstimulador identificador del estimulador.
	 * @return Devuelve una lista de Historico.
	 * @throws Exception
	 */
	public List<Historico> obtenerHistoricoPorFecha(Integer mes, Integer anio, Integer idPaciente, Integer idEstimulador)throws Exception{
		if(mes!=null && anio!=null && idPaciente!=null && idEstimulador!=null){
			HistoricoExample exHist = new HistoricoExample();
			exHist.createCriteria().andFechaInicioGreaterThanOrEqualTo(generarFecha(FECHA_INICIO, mes, anio))
			.andFechaInicioLessThanOrEqualTo(generarFecha(FECHA_FIN, mes, anio)) 
			.andIdPacienteEqualTo(idPaciente)
			.andIdEstimuladorEqualTo(idEstimulador);
			return historicoMapper.selectByExample(exHist);
		}
		return new ArrayList<Historico>();
	}
	
	public boolean registrarEventoHistorico(HistoricoDTO historicoDTO) throws ExcepcionHistorico{
		if(validarHistorico(historicoDTO)){
			Historico historico = generarHistorico(historicoDTO);
			historicoMapper.insert(historico);
			return true;
		}
		return false;
	}
	
 	private Historico generarHistorico(HistoricoDTO historicoDTO) {
 		Historico historico = new Historico();
 		historico.setId(0);
 		historico.setLatitud(historicoDTO.getLatitud());
 		historico.setLongitud(historicoDTO.getLongitud());
 		historico.setEstado(historicoDTO.getEstado());
 		historico.setFrecuencia(historicoDTO.getFrecuencia());
 		historico.setAmplitud(historicoDTO.getAmplitud());
 		historico.setTiempo(historicoDTO.getTiempo());
 		historico.setIdPaciente(historicoDTO.getIdPaciente());
 		historico.setIdEstimulador(historicoDTO.getIdEstimulador());
 		historico.setFechaInicio(generarFechaEvento(historicoDTO, FECHA_INICIO));
 		historico.setFechaFin(generarFechaEvento(historicoDTO, FECHA_FIN));
 		historico.setTipoOnda(historicoDTO.getTipoOnda());
 		return historico;
	}
 	
 	private Date generarFechaEvento(HistoricoDTO historicoDTO,Integer tipoFecha) {
		Calendar cal = Calendar.getInstance();
		if(tipoFecha == FECHA_INICIO){
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, historicoDTO.getMinIni());
			cal.set(Calendar.HOUR_OF_DAY, historicoDTO.getHoraIni()); // CAMBIAR POR HOUR OF DAY
			cal.set(Calendar.DAY_OF_MONTH, historicoDTO.getDiaIni());
			cal.set(Calendar.MONTH, historicoDTO.getMesIni()-1);
			cal.set(Calendar.YEAR, historicoDTO.getAnioIni());
		}else if(tipoFecha == FECHA_FIN){
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, historicoDTO.getMinFin());
			cal.set(Calendar.HOUR_OF_DAY, historicoDTO.getHoraFin()); // CAMBIAR POR HOUR OF DAY
			cal.set(Calendar.DAY_OF_MONTH, historicoDTO.getDiaFin());
			cal.set(Calendar.MONTH, historicoDTO.getMesFin()-1);
			cal.set(Calendar.YEAR, historicoDTO.getAnioFin());
		}
		return cal.getTime();
	}

	private boolean validarHistorico(HistoricoDTO historicoDTO)throws ExcepcionHistorico {
 		if(historicoDTO.getLatitud()==null || historicoDTO.getLatitud().trim().length()==0){
 			throw new ExcepcionHistorico("Latitud llegó null o vacio");
 		}
 		if(historicoDTO.getLongitud()==null || historicoDTO.getLongitud().trim().length()==0){
 			throw new ExcepcionHistorico("Longitud llegó null o vacio");
 		}
 		if(historicoDTO.getEstado()==null){
 			throw new ExcepcionHistorico("Estado llegó null");
 		}
 		if(historicoDTO.getFrecuencia()==null){
 			throw new ExcepcionHistorico("Frecuencia llegó null");
 		}
		if(historicoDTO.getAmplitud()==null){
			throw new ExcepcionHistorico("Amplitud llegó null");
		}
		if(historicoDTO.getTiempo()==null){
			throw new ExcepcionHistorico("Tiempo llegó null");
		}
		if(historicoDTO.getIdEstimulador()==null || historicoDTO.getIdEstimulador()==0){
			throw new ExcepcionHistorico("idEstimulador llegó null o 0");
		}
		if(historicoDTO.getIdPaciente()==null || historicoDTO.getIdPaciente()==0){
			throw new ExcepcionHistorico("idPaciente llegó null o 0");
		}
		if(historicoDTO.getMinIni()==null){
			throw new ExcepcionHistorico("Minuto Inicial llegó null");
		}
		if(historicoDTO.getHoraIni()==null){
			throw new ExcepcionHistorico("Hora Inicial llegó null");
		}
		if(historicoDTO.getDiaIni()==null){
			throw new ExcepcionHistorico("Dia Inicial llegó null");
		}
		if(historicoDTO.getMesIni()==null){
			throw new ExcepcionHistorico("Mes Inicial llegó null");
		}
		if(historicoDTO.getAnioIni()==null){
			throw new ExcepcionHistorico("Anio Inicial llego null");
		}
		if(historicoDTO.getMinFin()==null){
			throw new ExcepcionHistorico("Minuto Final llego null");
		}
		if(historicoDTO.getHoraFin()==null){
			throw new ExcepcionHistorico("Hora Final llego null");
		}
		if(historicoDTO.getDiaFin()==null){
			throw new ExcepcionHistorico("Dia Final llego null");
		}
		if(historicoDTO.getMesFin()==null){
			throw new ExcepcionHistorico("Mes Final llego null");
		}
		if(historicoDTO.getAnioFin()==null){
			throw new ExcepcionHistorico("Anio Final llego null");
		}
 		if(historicoDTO.getTipoOnda() == null){
 			throw new ExcepcionHistorico("Tipo de Onda llego null");
 		}
		return true;
	}

	private Date generarFecha(int tipo, int mes, int anio){
		Calendar cal = 	Calendar.getInstance();
		cal.set(Calendar.YEAR, anio);
		cal.set(Calendar.MONTH, mes-1);
		if(tipo == FECHA_INICIO){
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 1);System.out.println(cal.getTime());
			return cal.getTime();
		}else if(tipo == FECHA_FIN){
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);System.out.println(cal.getTime());
			return cal.getTime();
		}
		return null;
	}
	
}
