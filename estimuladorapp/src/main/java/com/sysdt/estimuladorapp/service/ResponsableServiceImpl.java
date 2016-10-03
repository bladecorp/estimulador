package com.sysdt.estimuladorapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.estimuladorapp.dao.ResponsableMapper;
import com.sysdt.estimuladorapp.enums.RespuestaWS;
import com.sysdt.estimuladorapp.model.Responsable;
import com.sysdt.estimuladorapp.model.ResponsableExample;
import com.sysdt.estimuladorapp.service.interfaces.ResponsableService;

@Service
@Transactional
public class ResponsableServiceImpl implements ResponsableService{

	@Autowired
	private ResponsableMapper responsableMapper;
	
	public List<Responsable> obtenerResponsablesPorIdPaciente(Integer idPaciente)throws Exception{
		if(idPaciente != null){
			ResponsableExample exResponsable = new ResponsableExample();
			exResponsable.createCriteria().andIdPacienteEqualTo(idPaciente);
			return responsableMapper.selectByExample(exResponsable);
		}
		return new ArrayList<Responsable>();
	}
	
	public int insertarResponsable(Responsable responsable) throws Exception{
		if(responsable != null){
			responsable.setId(0);
			responsableMapper.insert(responsable);
			return responsable.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int actualizarResponsable(Responsable responsable) throws Exception{
		if(responsable != null){
			int res = responsableMapper.updateByPrimaryKeySelective(responsable);
			if(res == 1){
				return RespuestaWS.EXITO.getId();
			}
			return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int eliminarResponsable(Integer idResponsable)throws Exception{
		if(idResponsable != null){
			int res = responsableMapper.deleteByPrimaryKey(idResponsable);
			if(res == 1){
				return RespuestaWS.EXITO.getId();
			}
			RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
}
