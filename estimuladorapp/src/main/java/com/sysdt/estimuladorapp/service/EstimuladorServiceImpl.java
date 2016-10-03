package com.sysdt.estimuladorapp.service;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import com.sysdt.estimuladorapp.dao.EstimuladorMapper;
import com.sysdt.estimuladorapp.dto.PushbotsMsg;
import com.sysdt.estimuladorapp.enums.RespuestaWS;
import com.sysdt.estimuladorapp.model.Estimulador;
import com.sysdt.estimuladorapp.model.EstimuladorExample;
import com.sysdt.estimuladorapp.model.Paciente;
import com.sysdt.estimuladorapp.model.Usuario;
import com.sysdt.estimuladorapp.service.interfaces.EstimuladorService;
import com.sysdt.estimuladorapp.service.interfaces.PacienteService;
import com.sysdt.estimuladorapp.service.interfaces.UsuarioService;

@Service
@Transactional
public class EstimuladorServiceImpl implements EstimuladorService{

//	private static final String APP_ID = "571931814a9efab3868b4567";
//	private static final String SECRET = "75e01580abac8547e7ddbe8ad5f2dea1";
	private static final PushbotsMsg pushBotsMsg = obtenerMensajesPersonalizados();

	@Autowired
	private EstimuladorMapper estimuladorMapper;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PacienteService pacienteService;
	
	
	public List<Estimulador> obtenerEstimuladores() throws Exception{
		return estimuladorMapper.selectByExample(null);
	}
	
	public Estimulador obtenerEstimuladorPorIdPaciente(Integer idPaciente)throws Exception{
		if(idPaciente != null){
			EstimuladorExample exEstimulador = new EstimuladorExample();
			exEstimulador.createCriteria().andIdPacienteEqualTo(idPaciente);
			List<Estimulador> estimuladores = estimuladorMapper.selectByExample(exEstimulador);
			if(estimuladores != null && estimuladores.size() > 0){
				return estimuladores.get(0);
			}
		}
		return new Estimulador();
	}
	
	public int insertarEstimulador(Estimulador estimulador) throws Exception{
		if(estimulador != null && estimulador.getSerie()!=null){
			if(buscarDuplicidad(estimulador.getSerie(), 0)){
				estimulador.setId(0);estimulador.setSerie(estimulador.getSerie().toUpperCase());
				estimuladorMapper.insert(estimulador);
				return RespuestaWS.EXITO.getId();
			}else{
				return RespuestaWS.ESTIMULADOR_DUPLICADO.getId();
			}
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int actualizarEstimulador(Estimulador estimulador) throws Exception{
		if(estimulador != null && estimulador.getSerie()!=null){
			if(buscarDuplicidad(estimulador.getSerie(), estimulador.getId())){
				int res = estimuladorMapper.updateByPrimaryKeySelective(estimulador);
				if(res == 1){
					return RespuestaWS.EXITO.getId();
				}
				return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
			}
			return RespuestaWS.ESTIMULADOR_DUPLICADO.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	public int vincularEstimulador(Estimulador estimulador) throws Exception{
		if(buscarVinculacionPaciente(estimulador.getIdPaciente())){
			return actualizarEstimulador(estimulador);
		}else{
			return RespuestaWS.PAX_YA_ASOCIADO_A_ESTIMULADOR.getId();
		}
	}
	
	public int desvincularEstimulador(Estimulador estimulador) throws Exception{
		if(estimulador != null){
			estimulador.setIdPaciente(null);
			int res = estimuladorMapper.updateByPrimaryKey(estimulador);
			if(res == 1){
				return RespuestaWS.EXITO.getId();
			}
			return RespuestaWS.NO_SE_ENCONTRO_REG.getId();
		}
		return RespuestaWS.INFO_INCOMPLETA.getId();
	}
	
	private boolean buscarDuplicidad(String serie, int id){
		if(serie!=null){
			EstimuladorExample exEst = new EstimuladorExample();
			exEst.createCriteria().andSerieEqualTo(serie);
			List<Estimulador> estimuladores = estimuladorMapper.selectByExample(exEst);
			if(estimuladores!=null && estimuladores.size()==0){
				return true;
			}else if(estimuladores.get(0).getId() == id)
				return true;
		}
		return false;
	}
	
	private boolean buscarVinculacionPaciente(int idPaciente){
		EstimuladorExample exEstim = new EstimuladorExample();
		exEstim.createCriteria().andIdPacienteEqualTo(idPaciente);
		int res = estimuladorMapper.countByExample(exEstim);
		if(res > 0){
			return false;
		}
		return true;
	}
	
	public int enviarEstimulacion(int idPaciente, String estimulacion) throws Exception {
		
		Paciente pax = pacienteService.obtenerPacientePorId(idPaciente);
		Usuario user = usuarioService.obtenerUsuarioPorId(pax.getIdUsuario());
		if(user.getId() == null || pax == null){return RespuestaWS.NO_SE_ENCONTRO_REG.getId();}
		
		if(!pax.getEnabled()){return RespuestaWS.CUENTA_DESHABILITADA.getId();}
		
		if(user.getToken() == null || user.getToken().trim().length() == 0){return RespuestaWS.NO_SE_ENCONTRO_REG.getId();}
		
		int respuesta = EnviarHttpPush(user.getToken(), estimulacion);
		if(respuesta == HttpStatus.SC_OK){
			return RespuestaWS.EXITO.getId();
		}else{
			return RespuestaWS.ERROR_AL_ENVIAR_ESTIMULACION.getId();
		}
		
	}
	
	private int EnviarHttpPush(String tokenPaciente, String evento)throws Exception{
		String url = "https://api.pushbots.com/push/one";
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		//post.setHeader("Content-Type", "application/json");
		post.setHeader("x-pushbots-appid", pushBotsMsg.getAppId());
		post.setHeader("x-pushbots-secret", pushBotsMsg.getAppSecret());
		//StringEntity entity = new StringEntity("{'platform' : Integer ,  'token' : String ,  'msg' : String ,"+" 'sound' : String }");
		//StringEntity entity = new StringEntity("{\"platform\":\"[1]\", \"msg\":\"Hi from Tali\" ,\"badge\":\"10\" ,\"sound\":\"default\"}");
		
//		StringEntity entity = new StringEntity("{\"platform\":\"1\", \"token\":\""+tokenPaciente+"\", \"msg\":\""+evento+"\" ,\"sound\":\"default\"}");
		StringEntity entity = new StringEntity("{\"platform\":\"1\","
												+ "\"token\":\""+tokenPaciente+"\","
												+ "\"msg\":\""+pushBotsMsg.getMensaje()+"\","
												+ "\"payload\":{\"estimulacion\":\""+evento+"\","
												+ "\"customNotificationTitle\":\""+pushBotsMsg.getTitulo()+"\"},"
												+ "\"sound\":\"\"}");   
		entity.setContentType("application/json");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		return response.getStatusLine().getStatusCode();
	}
	
	public static PushbotsMsg obtenerMensajesPersonalizados(){
		PushbotsMsg push = new PushbotsMsg();
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
//			Document document = documentBuilder.parse(ClassLoader.getSystemResourceAsStream("mensajesPushbots.xml"));
			Document document = documentBuilder.parse(EstimuladorServiceImpl.class.getClassLoader().getResourceAsStream("mensajesPushbots.xml"));
			String titulo = document.getElementsByTagName("Titulo").item(0).getTextContent();
			String mensaje = document.getElementsByTagName("Mensaje").item(0).getTextContent();
			String appId = document.getElementsByTagName("AppId").item(0).getTextContent();
			String appSecret = document.getElementsByTagName("AppSecret").item(0).getTextContent();
			
			push.setTitulo(titulo);
			push.setMensaje(mensaje);
			push.setAppId(appId);
			push.setAppSecret(appSecret);
		}catch(Exception ex){
			push.setTitulo("Atencion");
			push.setMensaje("Ha recibido un mensaje de su medico");
		}
		return push;
	}
	
}
