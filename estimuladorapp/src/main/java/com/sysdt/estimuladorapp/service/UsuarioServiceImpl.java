package com.sysdt.estimuladorapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.estimuladorapp.dao.UsuarioMapper;
import com.sysdt.estimuladorapp.dto.PacienteEstimDTO;
import com.sysdt.estimuladorapp.dto.UsuarioDTO;
import com.sysdt.estimuladorapp.enums.RespuestaWS;
import com.sysdt.estimuladorapp.enums.TipoUsuarioEnum;
import com.sysdt.estimuladorapp.model.Administrador;
import com.sysdt.estimuladorapp.model.Estimulador;
import com.sysdt.estimuladorapp.model.Medico;
import com.sysdt.estimuladorapp.model.Paciente;
import com.sysdt.estimuladorapp.model.Responsable;
import com.sysdt.estimuladorapp.model.Telefono;
import com.sysdt.estimuladorapp.model.Usuario;
import com.sysdt.estimuladorapp.model.UsuarioExample;
import com.sysdt.estimuladorapp.service.interfaces.AdministradorService;
import com.sysdt.estimuladorapp.service.interfaces.EstimuladorService;
import com.sysdt.estimuladorapp.service.interfaces.MedicoService;
import com.sysdt.estimuladorapp.service.interfaces.PacienteService;
import com.sysdt.estimuladorapp.service.interfaces.ResponsableService;
import com.sysdt.estimuladorapp.service.interfaces.TelefonoService;
import com.sysdt.estimuladorapp.service.interfaces.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioMapper usuarioMapper;
	@Autowired
	private MedicoService medicoService;
	@Autowired
	private AdministradorService administradorService;
	@Autowired
	private PacienteService pacienteService;
	@Autowired
	private TelefonoService telefonoService;
	@Autowired
	private ResponsableService responsableService;
	@Autowired
	private EstimuladorService estimuladorService;
	
	public UsuarioDTO obtenerUsuarioCompleto(String usuario, String password) throws Exception{
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setCodigoError(RespuestaWS.INFO_INCOMPLETA.getId());
		usuarioDTO.setMensajeWS("Usuario o password llego nulo");
		if(usuario != null && usuario.length() > 0 && password != null && password.length() > 0){
			
			Usuario user = obtenerUsuario(usuario, password);
			if(user.getId() == RespuestaWS.NO_SE_ENCONTRO_REG.getId()){
				usuarioDTO.setCodigoError(RespuestaWS.NO_SE_ENCONTRO_REG.getId());
				usuarioDTO.setMensajeWS("No se encontro usuario");
				user=null;
			}
			else if(user.getId() == RespuestaWS.PASSWORD_INCORRECTO.getId()){
				usuarioDTO.setCodigoError(RespuestaWS.PASSWORD_INCORRECTO.getId());
				usuarioDTO.setMensajeWS("El password es incorrecto");
				user=null;
			}
			
			if(user!=null && user.getId()!=null){
				
				usuarioDTO.setUsuario(user);
				
				List<Telefono> telefonos = telefonoService.obtenerTelefonosPorIdUsuario(user.getId());
				usuarioDTO.setTelefonos(telefonos);
				
				if(user.getIdTipoUsuario() == TipoUsuarioEnum.ADMINISTRADOR.getId()){
					
					Administrador admin = administradorService.obtenerAdministradorPorIdUsuario(user.getId());
					if(admin != null){
						usuarioDTO.setCodigoError(RespuestaWS.EXITO.getId());
						usuarioDTO.setMensajeWS("Exito al obtener Administrador");
						usuarioDTO.setAdministrador(admin);
						return usuarioDTO;
					}
					usuarioDTO.setCodigoError(RespuestaWS.NO_SE_ENCONTRO_REG.getId());
					usuarioDTO.setMensajeWS("No se encontro administrador");
					
				}else if(user.getIdTipoUsuario() == TipoUsuarioEnum.MEDICO.getId()){
					
					Medico medico = medicoService.obtenerMedicoPorIdUsuario(user.getId());
					if(medico != null){
						if(!medico.getEnabled()){
							usuarioDTO.setCodigoError(RespuestaWS.CUENTA_DESHABILITADA.getId());
							usuarioDTO.setMensajeWS("La cuenta esta deshabilitada");
							return usuarioDTO;
						}
						usuarioDTO.setCodigoError(RespuestaWS.EXITO.getId());
						usuarioDTO.setMensajeWS("Exito al obtener Medico");
						usuarioDTO.setMedico(medico);
						return usuarioDTO;
					}
					usuarioDTO.setCodigoError(RespuestaWS.NO_SE_ENCONTRO_REG.getId());
					usuarioDTO.setMensajeWS("No se encontro medico");
					
				}else if(user.getIdTipoUsuario() == TipoUsuarioEnum.PACIENTE.getId()){
					
					Paciente paciente = pacienteService.obtenerPacientePorIdUsuario(user.getId());
					usuarioDTO.setCodigoError(RespuestaWS.NO_SE_ENCONTRO_REG.getId());
					usuarioDTO.setMensajeWS("No se encontro paciente");
					if(paciente != null && paciente.getId() != null){
						if(!paciente.getEnabled()){
							usuarioDTO.setCodigoError(RespuestaWS.CUENTA_DESHABILITADA.getId());
							usuarioDTO.setMensajeWS("La cuenta esta deshabilitada");
							return usuarioDTO;
						}
						usuarioDTO.getPacienteDTO().setPaciente(paciente);
						Estimulador estimulador = estimuladorService.obtenerEstimuladorPorIdPaciente(paciente.getId());
						List<Responsable> responsables = responsableService.obtenerResponsablesPorIdPaciente(paciente.getId());
						if(estimulador != null && responsables != null){
							usuarioDTO.setCodigoError(RespuestaWS.EXITO.getId());
							usuarioDTO.setMensajeWS("Exito al obtener el usuario completo");
							usuarioDTO.getPacienteDTO().setEstimulador(estimulador);
							usuarioDTO.getPacienteDTO().setResponsables(responsables);
							return usuarioDTO;
						}
						usuarioDTO.setCodigoError(RespuestaWS.NO_SE_ENCONTRO_REG.getId());
						if(estimulador == null){
							usuarioDTO.setMensajeWS("No se encontro estimulador");
						}
						if(responsables == null){
							usuarioDTO.setMensajeWS("No se encontraron responsables");
						}
					}// se cierra validacion paciente
					
				}//se cierra opcion PACIENTE
				
			}// se cierra validacion user
		}// se cierra validacion usuario y password
		return usuarioDTO;
	}
	
	public int insertarUsuarioCompleto(UsuarioDTO usuarioDTO) throws Exception{
		if(usuarioDTO != null){
			int idUsuario = insertarUsuario(usuarioDTO.getUsuario());System.out.println("idUsuario = "+idUsuario);
			if(idUsuario != RespuestaWS.USUARIO_DUPLICADO.getId()){System.out.println("Pasa validacion usuario dup");
			//	usuarioDTO.getUsuario().se
			System.out.println("idUsuario = "+usuarioDTO.getUsuario().getId());
				for(Telefono tel : usuarioDTO.getTelefonos()){
					tel.setIdUsuario(idUsuario);
					telefonoService.insertarTelefono(tel);
				}System.out.println("Pasa la iteracion de telefonos");
				if(usuarioDTO.getUsuario().getIdTipoUsuario() == TipoUsuarioEnum.ADMINISTRADOR.getId()){
					Administrador admin = usuarioDTO.getAdministrador();
					if(administradorService.buscarDuplicidadAdministrador(admin.getNombre(), admin.getApaterno(), admin.getAmaterno())){
						usuarioDTO.getAdministrador().setIdUsuario(usuarioDTO.getUsuario().getId());
						administradorService.insertarAdministrador(usuarioDTO.getAdministrador());
						return RespuestaWS.EXITO.getId();
					}
				}else if(usuarioDTO.getUsuario().getIdTipoUsuario() == TipoUsuarioEnum.MEDICO.getId()){
					System.out.println("Entra a médico");
					Medico medico = usuarioDTO.getMedico();
					if(medicoService.buscarDuplicidadMedico(medico.getNombre(), medico.getApaterno(), medico.getAmaterno(), medico.getIdUsuario())){
						System.out.println("Antes de insertar medico");
						
						usuarioDTO.getMedico().setIdUsuario(usuarioDTO.getUsuario().getId());
						System.out.print(usuarioDTO.getMedico().toString());
						medicoService.insertarMedico(usuarioDTO.getMedico());
						return RespuestaWS.EXITO.getId();
					}
				}else if(usuarioDTO.getUsuario().getIdTipoUsuario() == TipoUsuarioEnum.PACIENTE.getId()){
					Paciente paciente = usuarioDTO.getPacienteDTO().getPaciente();
					if(pacienteService.buscarDuplicidadPaciente(paciente.getNombre(), paciente.getApaterno(), paciente.getAmaterno(), paciente.getIdUsuario())){
						usuarioDTO.getPacienteDTO().getPaciente().setIdUsuario(usuarioDTO.getUsuario().getId());
						pacienteService.insertarPaciente(usuarioDTO.getPacienteDTO().getPaciente());
						int idPaciente = usuarioDTO.getPacienteDTO().getPaciente().getId();
						for(Responsable responsable : usuarioDTO.getPacienteDTO().getResponsables()){
							responsable.setIdPaciente(idPaciente);
							responsableService.insertarResponsable(responsable);
						}
						usuarioDTO.getPacienteDTO().getEstimulador().setIdPaciente(idPaciente);
						estimuladorService.actualizarEstimulador(usuarioDTO.getPacienteDTO().getEstimulador());
						return RespuestaWS.EXITO.getId();
					}
				}
				return RespuestaWS.NOMBRE_DUPLICADO.getId();
			}
			return RespuestaWS.USUARIO_DUPLICADO.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	private Usuario obtenerUsuario(String usuario, String password) throws Exception{
		Usuario us = new Usuario();
		us.setId(RespuestaWS.NO_SE_ENCONTRO_REG.getId());
		UsuarioExample exUser = new UsuarioExample();
		exUser.createCriteria().andUsuarioEqualTo(usuario);
		List<Usuario> usuarios = usuarioMapper.selectByExample(exUser);
		if(usuarios!=null && usuarios.size()>0){
			Usuario user = usuarios.get(0);
			if(user.getPassword().contentEquals(password)){
				return user;
			}
			us.setId(RespuestaWS.PASSWORD_INCORRECTO.getId());
		}
		return us;
	}
	
	public Usuario obtenerUsuarioPorId(Integer id)throws Exception{
		if(id != null){
			Usuario us = usuarioMapper.selectByPrimaryKey(id);
			if(us!=null){
				return us;
			}
		}
		return new Usuario();
	}
	
	/**
	 * Método para buscar duplicidad en el nombre del Usuario
	 * @param nomUsuario nombre del usuario
	 * @return Devuelve TRUE si el nombre NO SE DUPLICA
	 * @throws Exception
	 */
	public boolean buscarDuplicidadUsuario(String nomUsuario, Integer idUsuario) throws Exception{
		if(nomUsuario!=null){
			UsuarioExample exUser = new UsuarioExample();
			exUser.createCriteria().andUsuarioEqualTo(nomUsuario);
			List<Usuario> usuarios = usuarioMapper.selectByExample(exUser);
			if(usuarios!=null && usuarios.size() == 0){
				return true;
			}else if(idUsuario != null && usuarios.get(0).getId() == idUsuario){
				return true;
			}
		}
		return false;
	}
	
	private int insertarUsuario(Usuario usuario) throws Exception{
		if(usuario != null){
			if(buscarDuplicidadUsuario(usuario.getUsuario(), usuario.getId())){
				usuario.setId(0);
				usuarioMapper.insert(usuario);
				return usuario.getId();
			}
			return RespuestaWS.USUARIO_DUPLICADO.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int actualizarUsuarioCompleto(UsuarioDTO usuarioDTO) throws Exception{
		if(usuarioDTO == null || (usuarioDTO.getAdministrador() ==null && usuarioDTO.getMedico()==null 
															&& usuarioDTO.getPacienteDTO()==null)){
			return RespuestaWS.INFO_INCOMPLETA.getId();
		}
		actualizarUsuario(usuarioDTO.getUsuario());
		if(usuarioDTO.getTelefonos() != null){
			telefonoService.actualizarTelefonos(usuarioDTO.getUsuario().getId(), usuarioDTO.getTelefonos());
		}
		if(usuarioDTO.getUsuario().getIdTipoUsuario() == TipoUsuarioEnum.ADMINISTRADOR.getId()){
			 administradorService.actualizarAdministrador(usuarioDTO.getAdministrador());
		}else if(usuarioDTO.getUsuario().getIdTipoUsuario() == TipoUsuarioEnum.MEDICO.getId()){
			medicoService.actualizarMedico(usuarioDTO.getMedico());
		}else if(usuarioDTO.getUsuario().getIdTipoUsuario() == TipoUsuarioEnum.PACIENTE.getId()){
			pacienteService.actualizarPaciente(usuarioDTO.getPacienteDTO().getPaciente());
			for(Responsable resp : usuarioDTO.getPacienteDTO().getResponsables()){
				responsableService.actualizarResponsable(resp);
			}
		}
		
		return RespuestaWS.EXITO.getId();
	}
	
	public int actualizarUsuario(Usuario usuario) throws Exception{
		if(usuario == null){
			return RespuestaWS.INFO_INCOMPLETA.getId();
		}
		if(!buscarDuplicidadUsuario(usuario.getUsuario(), usuario.getId())){
			return RespuestaWS.USUARIO_DUPLICADO.getId();
		}
		int res = usuarioMapper.updateByPrimaryKeySelective(usuario);
		if(res == 1){
			return RespuestaWS.EXITO.getId();
		}else{
			return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		}
	}
	
	private int eliminarUsuario(Integer id)throws Exception{
		if(id != null){
			usuarioMapper.deleteByPrimaryKey(id);
			return RespuestaWS.EXITO.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int eliminarUsuarioCompleto(Integer idUsuario, Integer idTipoUsuario) throws Exception{
		if(idUsuario == null || idTipoUsuario == null){
			return RespuestaWS.INFO_INCOMPLETA.getId();
		}
		boolean exito = false;
		if(idTipoUsuario == TipoUsuarioEnum.ADMINISTRADOR.getId()){
			administradorService.eliminarPorIdUsuario(idUsuario);
			exito = true;
		}else if(idTipoUsuario == TipoUsuarioEnum.MEDICO.getId()){
			medicoService.eliminarPorIdUsuario(idUsuario);
			exito = true;
		}else {
			return RespuestaWS.IMPOSIBLE_ELIMINAR_PACIENTE.getId();
		}
		if(exito){
			telefonoService.eliminarPorIdUsuario(idUsuario);
			eliminarUsuario(idUsuario);
		}
		return RespuestaWS.EXITO.getId();
	}
	
	public PacienteEstimDTO obtenerPacienteYestimulador(String username, String password) throws Exception{
		UsuarioDTO usuarioDTO = obtenerUsuarioCompleto(username, password);
		PacienteEstimDTO pacienteEstimDTO = new PacienteEstimDTO();
		pacienteEstimDTO.setPaciente(usuarioDTO.getPacienteDTO().getPaciente());
		pacienteEstimDTO.setEstimulador(usuarioDTO.getPacienteDTO().getEstimulador());
		pacienteEstimDTO.setCodigoError(usuarioDTO.getCodigoError());
		pacienteEstimDTO.setMensajeError(usuarioDTO.getMensajeWS());
		if(pacienteEstimDTO.getCodigoError() == RespuestaWS.EXITO.getId() && pacienteEstimDTO.getPaciente().getId() == null){
			pacienteEstimDTO.setCodigoError(RespuestaWS.NO_SE_ENCONTRO_REG.getId());
			pacienteEstimDTO.setMensajeError("No se encontro registro del paciente");
		}
		if(pacienteEstimDTO.getCodigoError() == RespuestaWS.EXITO.getId() && pacienteEstimDTO.getEstimulador().getId() == null){
			pacienteEstimDTO.setCodigoError(RespuestaWS.NO_SE_ENCONTRO_REG.getId());
			pacienteEstimDTO.setMensajeError("El paciente no tiene estimulador asignado");
		}
		return pacienteEstimDTO;
	}
	
	public int actualizarToken(Integer idUsuario, String token) throws Exception{
		if(idUsuario != null && token != null){
			Usuario usuario = new Usuario();
			usuario.setId(idUsuario);
			usuario.setToken(token);
			int exito = usuarioMapper.updateByPrimaryKeySelective(usuario);
			if(exito > 0){
				return RespuestaWS.EXITO.getId();
			}else{
				return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
			}
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
}
