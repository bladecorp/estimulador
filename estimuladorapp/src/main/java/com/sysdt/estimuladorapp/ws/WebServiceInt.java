package com.sysdt.estimuladorapp.ws;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.sysdt.estimuladorapp.dto.PacienteEstimDTO;

@WebService
public interface WebServiceInt {
	
	@WebResult(name="pax")
	public PacienteEstimDTO obtenerPacienteEstim(@WebParam(name="usuario")String usuario, @WebParam(name="password")String password);
	
	@WebResult(name="mensaje")
	public int actualizarToken(@WebParam(name="idUsuario") Integer idUsuario, @WebParam(name="token") String token);

}
