package com.sysdt.estimuladorapp.service.interfaces;

import com.sysdt.estimuladorapp.dto.PacienteEstimDTO;
import com.sysdt.estimuladorapp.dto.UsuarioDTO;
import com.sysdt.estimuladorapp.model.Usuario;

public interface UsuarioService {

	public UsuarioDTO obtenerUsuarioCompleto(String usuario, String password) throws Exception;
	
	public int insertarUsuarioCompleto(UsuarioDTO usuarioDTO) throws Exception;
	
	public Usuario obtenerUsuarioPorId(Integer id)throws Exception;
	
	public boolean buscarDuplicidadUsuario(String nomUsuario, Integer idUsuario) throws Exception;
	
	public int actualizarUsuarioCompleto(UsuarioDTO usuarioDTO) throws Exception;
	
	public int actualizarUsuario(Usuario usuario) throws Exception;
	
	public int eliminarUsuarioCompleto(Integer idUsuario, Integer idTipoUsuario) throws Exception;
	
	public PacienteEstimDTO obtenerPacienteYestimulador(String username, String password) throws Exception;
	
	public int actualizarToken(Integer idUsuario, String token) throws Exception;
	
}
