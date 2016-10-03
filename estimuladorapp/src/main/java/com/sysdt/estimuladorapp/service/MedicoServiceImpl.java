package com.sysdt.estimuladorapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.estimuladorapp.dao.MedicoMapper;
import com.sysdt.estimuladorapp.dto.MedicoDTO;
import com.sysdt.estimuladorapp.enums.RespuestaWS;
import com.sysdt.estimuladorapp.model.Medico;
import com.sysdt.estimuladorapp.model.MedicoExample;
import com.sysdt.estimuladorapp.model.Telefono;
import com.sysdt.estimuladorapp.model.Usuario;
import com.sysdt.estimuladorapp.service.interfaces.MedicoService;
import com.sysdt.estimuladorapp.service.interfaces.TelefonoService;
import com.sysdt.estimuladorapp.service.interfaces.UsuarioService;

@Service
@Transactional
public class MedicoServiceImpl implements MedicoService{

	@Autowired
	private MedicoMapper medicoMapper;
	@Autowired
	private TelefonoService telefonoService;
	@Autowired
	private UsuarioService usuarioService;
	
	public MedicoDTO obtenerMedicoCompletoPorId(int idMedico) throws Exception{
		Medico medico = obtenerMedicoPorId(idMedico);
		Usuario usuario = usuarioService.obtenerUsuarioPorId(medico.getIdUsuario());
		List<Telefono> telefonos = telefonoService.obtenerTelefonosPorIdUsuario(usuario.getId());
		MedicoDTO medicoDTO = new MedicoDTO();
		medicoDTO.setMedico(medico);
		medicoDTO.setTelefonos(telefonos);
		medicoDTO.setUsuario(usuario);
		return medicoDTO;
	}
	
	public List<Medico> obtenerMedicos() throws Exception{
		return medicoMapper.selectByExample(null);
	}
	
	public Medico obtenerMedicoPorId(Integer id)throws Exception{
		if(id != null){
			return medicoMapper.selectByPrimaryKey(id);
		}
		return new Medico();
	}
	
	public Medico obtenerMedicoPorIdUsuario(Integer idUsuario) throws Exception{
		if(idUsuario != null){
			MedicoExample exMed = new MedicoExample();
			exMed.createCriteria().andIdUsuarioEqualTo(idUsuario);
			List<Medico> medicos = medicoMapper.selectByExample(exMed);
			if(medicos != null && medicos.size() > 0){
				return medicos.get(0);
			}
		}
		return new Medico();
	}
	
	/**
	 * Método para buscar duplicidad en el nombre COMPLETO del medico
	 * @param nombre
	 * @param apaterno
	 * @param amaterno
	 * @return Devuelve TRUE si el nombre NO SE DUPLICA
	 * @throws Exception
	 */
	public boolean buscarDuplicidadMedico(String nombre, String apaterno, String amaterno, Integer idUsuario)throws Exception{
		if(nombre != null && apaterno != null && amaterno != null){
			MedicoExample exMedico = new MedicoExample();
			exMedico.createCriteria().andNombreEqualTo(nombre).andApaternoEqualTo(apaterno).andAmaternoEqualTo(amaterno);
			List<Medico> medicos = medicoMapper.selectByExample(exMedico);
			if(medicos != null || medicos.size() == 0 || medicos.get(0).getId() == idUsuario){
				return true;
			}
		}
		return false;
	}
	
	public int insertarMedico(Medico medico)throws Exception{
		if(medico != null){
			medico.setId(0);
			medico.setEnabled(true);
			medicoMapper.insert(medico);
			return medico.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int actualizarMedico(Medico medico)throws Exception{
		if(medico == null){
			return RespuestaWS.INFO_INCOMPLETA.getId();
		}
		if(!buscarDuplicidadMedico(medico.getNombre(), medico.getApaterno(), medico.getAmaterno(), medico.getIdUsuario())){
			return RespuestaWS.NOMBRE_DUPLICADO.getId();
		}
		int res = medicoMapper.updateByPrimaryKeySelective(medico);
		if(res == 1){
			return RespuestaWS.EXITO.getId();
		}else{
			return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		}
	}
	
	public void actualizarEstadoMedico(int idMedico, boolean estado){
		Medico medico = new Medico();
		medico.setId(idMedico);
		medico.setEnabled(estado);
		medicoMapper.updateByPrimaryKeySelective(medico);
	}
	
	public int eliminarMedico(Integer id)throws Exception{
		if(id != null){
			int res = medicoMapper.deleteByPrimaryKey(id);
			if(res == 1){
				return RespuestaWS.EXITO.getId();
			}
			return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int eliminarPorIdUsuario(Integer idUsuario) throws Exception{
		if(idUsuario != null){
			MedicoExample exMed = new MedicoExample();
			exMed.createCriteria().andIdUsuarioEqualTo(idUsuario);
			medicoMapper.deleteByExample(exMed);
			return RespuestaWS.EXITO.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
}
