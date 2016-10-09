package com.sysdt.estimuladorapp.ws;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.sysdt.estimuladorapp.dto.HistoricoDTO;
import com.sysdt.estimuladorapp.dto.PacienteEstimDTO;
import com.sysdt.estimuladorapp.exceptions.ExcepcionHistorico;

@WebService
public interface WebServiceInt {
	
	@WebResult(name="pax")
	public PacienteEstimDTO obtenerPacienteEstim(@WebParam(name="usuario")String usuario, @WebParam(name="password")String password);
	
	@WebResult(name="mensaje")
	public int actualizarToken(@WebParam(name="idUsuario") Integer idUsuario, @WebParam(name="token") String token);
	
	@WebResult(name="exito")
	public boolean registrarEventoHistorico(@WebParam(name="historicoDTO") HistoricoDTO historicoDTO)throws ExcepcionHistorico;

}
