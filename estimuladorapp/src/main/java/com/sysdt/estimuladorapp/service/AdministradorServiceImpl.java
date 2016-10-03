package com.sysdt.estimuladorapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.estimuladorapp.dao.AdministradorMapper;
import com.sysdt.estimuladorapp.enums.RespuestaWS;
import com.sysdt.estimuladorapp.model.Administrador;
import com.sysdt.estimuladorapp.model.AdministradorExample;
import com.sysdt.estimuladorapp.service.interfaces.AdministradorService;

@Service
@Transactional
public class AdministradorServiceImpl implements AdministradorService{
	
	@Autowired
	private AdministradorMapper administradorMapper;
	
	public List<Administrador> obtenerAdministradores() throws Exception{
		return administradorMapper.selectByExample(null);
	}
	
	public Administrador obtenerAdministradorPorId(Integer id){
		if(id != null){
			return administradorMapper.selectByPrimaryKey(id);
		}
		return new Administrador();
	}
	
	public Administrador obtenerAdministradorPorIdUsuario(Integer idUsuario) throws Exception{
		if(idUsuario != null){
			AdministradorExample exAdmin = new AdministradorExample();
			exAdmin.createCriteria().andIdUsuarioEqualTo(idUsuario);
			List<Administrador> administradores = administradorMapper.selectByExample(exAdmin);
			if(administradores != null && administradores.size() > 0){
				return administradores.get(0);
			}
		}
		return new Administrador();
	}
	
	/**
	 * Método para buscar duplicidad en el nombre COMPLETO del administrador
	 * @param nombre
	 * @param apaterno
	 * @param amaterno
	 * @return Devuelve TRUE si el nombre NO SE DUPLICA
	 * @throws Exception
	 */
	public boolean buscarDuplicidadAdministrador(String nombre, String apaterno, String amaterno)throws Exception{
		if(nombre != null && apaterno != null && amaterno != null){
			AdministradorExample exAdmin = new AdministradorExample();
			exAdmin.createCriteria().andNombreEqualTo(nombre).andApaternoEqualTo(apaterno).andAmaternoEqualTo(amaterno);
			List<Administrador> administradores = administradorMapper.selectByExample(exAdmin);
			if(administradores != null && administradores.size() == 0){
				return true;
			}
		}
		return false;
	}
	
	public int insertarAdministrador(Administrador admin)throws Exception{
		if(admin != null){
			admin.setId(0);
			administradorMapper.insert(admin);
			return admin.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int actualizarAdministrador(Administrador admin)throws Exception{
		if(admin == null){
			return RespuestaWS.INFO_INCOMPLETA.getId();
		}
		if(!buscarDuplicidadAdministrador(admin.getNombre(), admin.getApaterno(), admin.getAmaterno())){
			return RespuestaWS.NOMBRE_DUPLICADO.getId();
		}
		int res = administradorMapper.updateByPrimaryKeySelective(admin);
		if(res == 1){
			return RespuestaWS.EXITO.getId();
		}
			return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		
		
	}
	
	public int eliminarAdministrador(Integer id) throws Exception{
		if(id != null){
			int res = administradorMapper.deleteByPrimaryKey(id);
			if(res == 1){
				return RespuestaWS.EXITO.getId();
			}
			return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int eliminarPorIdUsuario(Integer idUsuario) throws Exception{
		if(idUsuario != null){
			AdministradorExample exAdmin = new AdministradorExample();
			exAdmin.createCriteria().andIdUsuarioEqualTo(idUsuario);
			int res = administradorMapper.deleteByExample(exAdmin);
			if(res > 0){
				return RespuestaWS.EXITO.getId();
			}
			return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}

}
