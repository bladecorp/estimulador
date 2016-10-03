package com.sysdt.estimuladorapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.estimuladorapp.dao.TelefonoMapper;
import com.sysdt.estimuladorapp.enums.RespuestaWS;
import com.sysdt.estimuladorapp.model.Telefono;
import com.sysdt.estimuladorapp.model.TelefonoExample;
import com.sysdt.estimuladorapp.service.interfaces.TelefonoService;

@Service
@Transactional
public class TelefonoServiceImpl implements TelefonoService{

	@Autowired
	private TelefonoMapper telefonoMapper;
	
	public List<Telefono> obtenerTelefonosPorIdUsuario(Integer idUsuario) throws Exception{
		if(idUsuario != null){
			TelefonoExample exTel = new TelefonoExample();
			exTel.createCriteria().andIdUsuarioEqualTo(idUsuario);
			return telefonoMapper.selectByExample(exTel);
		}
		return new ArrayList<Telefono>();
	}
	
	public int insertarTelefono(Telefono telefono) throws Exception{
		if(telefono != null){
			telefono.setId(0);
			telefono.setNumero(telefono.getNumero() != null ? telefono.getNumero() : "");
			telefonoMapper.insert(telefono);
			return telefono.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public void actualizarTelefonos(int idUsuario, List<Telefono> telefonos) throws Exception{
		if(idUsuario != 0 && telefonos != null){
			eliminarPorIdUsuario(idUsuario);
			for (Telefono telefono : telefonos) {
				telefono.setNumero(telefono.getNumero() != null ? telefono.getNumero() : "");
				telefonoMapper.insert(telefono);
			}
		}
	}
	
	public int eliminarTelefono(Integer id) throws Exception{
		if(id != null){
			telefonoMapper.deleteByPrimaryKey(id);
			return RespuestaWS.EXITO.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int eliminarPorIdUsuario(Integer idUsuario) throws Exception{
		if(idUsuario == null){
			throw new Exception();
		}
		TelefonoExample exTel = new TelefonoExample();
		exTel.createCriteria().andIdUsuarioEqualTo(idUsuario);
		telefonoMapper.deleteByExample(exTel);
		return RespuestaWS.EXITO.getId();
				
	}
	
}
