package com.sysdt.estimuladorapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.estimuladorapp.dao.PacienteMapper;
import com.sysdt.estimuladorapp.dto.PacienteDTO;
import com.sysdt.estimuladorapp.dto.UsuarioDTO;
import com.sysdt.estimuladorapp.enums.RespuestaWS;
import com.sysdt.estimuladorapp.model.Estimulador;
import com.sysdt.estimuladorapp.model.Paciente;
import com.sysdt.estimuladorapp.model.PacienteExample;
import com.sysdt.estimuladorapp.model.PacienteExample.Criteria;
import com.sysdt.estimuladorapp.model.Responsable;
import com.sysdt.estimuladorapp.model.Telefono;
import com.sysdt.estimuladorapp.model.Usuario;
import com.sysdt.estimuladorapp.service.interfaces.EstimuladorService;
import com.sysdt.estimuladorapp.service.interfaces.PacienteService;
import com.sysdt.estimuladorapp.service.interfaces.ResponsableService;
import com.sysdt.estimuladorapp.service.interfaces.TelefonoService;
import com.sysdt.estimuladorapp.service.interfaces.UsuarioService;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService{

	@Autowired
	private PacienteMapper pacienteMapper;
	@Autowired
	private EstimuladorService estimuladorService;
	@Autowired
	private ResponsableService responsableService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private TelefonoService telefonoService;
	
	private final int FILTRO_POR_ID = 1;
	private final int FILTRO_POR_NOMBRE = 2;
	private final int FILTRO_POR_APATERNO = 3;
	
	public List<Paciente> obtenerPacientes()throws Exception{
		return pacienteMapper.selectByExample(null);
	}
	
	public Paciente obtenerPacientePorId(Integer id)throws Exception{
		if(id != null){
			return pacienteMapper.selectByPrimaryKey(id);
		}
		return new Paciente();
	}
	
	public PacienteDTO obtenerPacienteCompletoPorId(int idPaciente) throws Exception{
		PacienteDTO pacienteDTO = new PacienteDTO();
		Paciente paciente = obtenerPacientePorId(idPaciente);
		pacienteDTO.setPaciente(paciente);
		Estimulador estimulador = estimuladorService.obtenerEstimuladorPorIdPaciente(idPaciente);
		pacienteDTO.setEstimulador(estimulador);
		Usuario usuario = usuarioService.obtenerUsuarioPorId(paciente.getIdUsuario());
		pacienteDTO.setVinculado(false);
		if(usuario != null && (usuario.getToken() != null && !usuario.getToken().trim().isEmpty())){
			pacienteDTO.setVinculado(true);
		}
		return pacienteDTO;
	}
	
	public UsuarioDTO obtenerPacienteCompletoPorIdUsuario(int idUsuario) throws Exception{
		
		Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
		Paciente paciente = obtenerPacientePorIdUsuario(idUsuario);
		Estimulador estimulador = estimuladorService.obtenerEstimuladorPorIdPaciente(paciente.getId());
		List<Telefono> telefonos = telefonoService.obtenerTelefonosPorIdUsuario(idUsuario);
		List<Responsable> responsables = responsableService.obtenerResponsablesPorIdPaciente(paciente.getId());
		
		PacienteDTO pacienteDTO = new PacienteDTO();
		pacienteDTO.setPaciente(paciente);
		pacienteDTO.setEstimulador(estimulador);
		pacienteDTO.setResponsables(responsables);
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setPacienteDTO(pacienteDTO);
		usuarioDTO.setTelefonos(telefonos);
		usuarioDTO.setUsuario(usuario);
		
		return usuarioDTO;
	}
	
	public List<Paciente> obtenerPacientesPorFiltro(int tipoFiltro, String valor){
		List<Paciente> pacientes = new ArrayList<Paciente>();
		PacienteExample exPax = new PacienteExample();
		Criteria criteria = exPax.createCriteria();
		if(tipoFiltro == FILTRO_POR_ID){
			criteria.andIdEqualTo(Integer.parseInt(valor));
		}else if(tipoFiltro == FILTRO_POR_NOMBRE){
			criteria.andNombreLike(valor+"%");
		}else if(tipoFiltro == FILTRO_POR_APATERNO){
			criteria.andApaternoLike(valor+"%");
		}
		pacientes = pacienteMapper.selectByExample(exPax);
		return pacientes;
	}
	
	public Paciente obtenerPacientePorIdUsuario(Integer idUsuario)throws Exception{
		if(idUsuario != null){
			PacienteExample exPaciente = new PacienteExample();
			exPaciente.createCriteria().andIdUsuarioEqualTo(idUsuario);
			List<Paciente> pacientes = pacienteMapper.selectByExample(exPaciente);
			if(pacientes != null && pacientes.size() >0){
				return pacientes.get(0);
			}
		}
		return new Paciente();
	}
	
	public List<PacienteDTO> obtenerPacientesCompletos() throws Exception{
		List<PacienteDTO> pacientesDTO = new ArrayList<PacienteDTO>();
		List<Paciente> pacientes = pacienteMapper.selectByExample(null);
		for(Paciente paciente : pacientes){
			Estimulador estimulador = estimuladorService.obtenerEstimuladorPorIdPaciente(paciente.getId());
			List<Responsable> responsables = responsableService.obtenerResponsablesPorIdPaciente(paciente.getId());
			PacienteDTO pacienteDTO = new PacienteDTO();
			pacienteDTO.setPaciente(paciente);
			pacienteDTO.setEstimulador(estimulador);
			pacienteDTO.setResponsables(responsables);
			pacientesDTO.add(pacienteDTO);
		}
		return pacientesDTO;
	}
	
	public PacienteDTO obtenerPacienteCompletoPorNombre(String nombre, String apaterno, String amaterno) throws Exception{
		PacienteDTO pacienteDTO = new PacienteDTO();
		pacienteDTO.setCodigoError(RespuestaWS.INFO_INCOMPLETA.getId());
		pacienteDTO.setMensaje("Alguno de los datos llego nulo");System.out.println("Antes de valid general");
		if(nombre != null && apaterno != null && amaterno != null){
			PacienteExample exPaciente = new PacienteExample();
			exPaciente.createCriteria().andNombreEqualTo(nombre).andApaternoEqualTo(apaterno).andAmaternoEqualTo(amaterno);
			List<Paciente> pacientes = pacienteMapper.selectByExample(exPaciente);
			pacienteDTO.setCodigoError(RespuestaWS.NO_SE_ENCONTRO_REG.getId());
			pacienteDTO.setMensaje("No se encontro al paciente");System.out.println("Antes de valid registros encont");
			if(pacientes != null && pacientes.size() > 0){
				Usuario usuario = usuarioService.obtenerUsuarioPorId(pacientes.get(0).getIdUsuario());
				if(usuario.getToken()!=null && usuario.getToken().trim().length()>0){pacienteDTO.setVinculado(true);}
				
				int idPaciente = pacientes.get(0).getId();
				pacienteDTO.setMensaje("No se encontro el estimulador");
				Estimulador est = estimuladorService.obtenerEstimuladorPorIdPaciente(idPaciente);
				if(est != null){
					List<Responsable> responsables = responsableService.obtenerResponsablesPorIdPaciente(idPaciente);
					pacienteDTO.setMensaje("No se encontraron responsables");
					if(responsables != null && responsables.size() > 0){
						pacienteDTO.setPaciente(pacientes.get(0));
						pacienteDTO.setEstimulador(est);
						pacienteDTO.setResponsables(responsables);
						pacienteDTO.setCodigoError(RespuestaWS.EXITO.getId());
						pacienteDTO.setMensaje("Exito al obtenet paciente completo");
					}
				}
			}
		}
		return pacienteDTO;
	}
	
	public boolean buscarDuplicidadPaciente(String nombre, String apaterno, String amaterno, Integer idUsuario)throws Exception{
		if(nombre != null && apaterno != null && amaterno != null){
			PacienteExample exPaciente = new PacienteExample();
			exPaciente.createCriteria().andNombreEqualTo(nombre).andApaternoEqualTo(apaterno).andAmaternoEqualTo(amaterno);
			List<Paciente> pacientes = pacienteMapper.selectByExample(exPaciente);
			if(pacientes != null || pacientes.size() == 0 || pacientes.get(0).getIdUsuario() == idUsuario){
				return true;
			}
		}
		return false;
	}
	
	public int insertarPaciente(Paciente paciente) throws Exception{
		if(paciente != null){
			paciente.setId(0);
			paciente.setEnabled(true);
			pacienteMapper.insert(paciente);
			return paciente.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int actualizarPaciente(Paciente paciente) throws Exception{
		if(paciente == null){
			return RespuestaWS.INFO_INCOMPLETA.getId();
		}
		if(!buscarDuplicidadPaciente(paciente.getNombre(), paciente.getApaterno(), paciente.getAmaterno(), paciente.getIdUsuario())){
			return RespuestaWS.NOMBRE_DUPLICADO.getId();
		}
		int resp = pacienteMapper.updateByPrimaryKeySelective(paciente);
		if(resp == 1){
			return RespuestaWS.EXITO.getId();
		}else{
			return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		}	
	}
	
	public void actualizarEstadoPaciente(int idPaciente, boolean estado){
		Paciente paciente = new Paciente();
		paciente.setId(idPaciente);
		paciente.setEnabled(estado);
		pacienteMapper.updateByPrimaryKeySelective(paciente);
	}
		
}
