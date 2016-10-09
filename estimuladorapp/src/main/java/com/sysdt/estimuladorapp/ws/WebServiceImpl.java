package com.sysdt.estimuladorapp.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysdt.estimuladorapp.dto.HistoricoDTO;
import com.sysdt.estimuladorapp.dto.PacienteEstimDTO;
import com.sysdt.estimuladorapp.enums.RespuestaWS;
import com.sysdt.estimuladorapp.exceptions.ExcepcionHistorico;
import com.sysdt.estimuladorapp.service.interfaces.HistoricoService;
import com.sysdt.estimuladorapp.service.interfaces.UsuarioService;

@Service
public class WebServiceImpl implements WebServiceInt{
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private HistoricoService historicoService;

	@Override
	public PacienteEstimDTO obtenerPacienteEstim(String usuario, String password) {
		try {
			return usuarioService.obtenerPacienteYestimulador(usuario, password);
		} catch (Exception e) {
			PacienteEstimDTO pac = new PacienteEstimDTO();
			pac.setCodigoError(RespuestaWS.EXCEPCION.getId());
			pac.setMensajeError("Ocurrio una excepción: "+e.getMessage());
			return pac;
		}
	}

	@Override
	public int actualizarToken(Integer idUsuario, String token) {
		try {
			return usuarioService.actualizarToken(idUsuario, token);
		} catch (Exception e) {
			return RespuestaWS.EXCEPCION.getId();
		}
	}

	@Override
	public boolean registrarEventoHistorico(HistoricoDTO historicoDTO) throws ExcepcionHistorico {
		return historicoService.registrarEventoHistorico(historicoDTO);
	}

}
