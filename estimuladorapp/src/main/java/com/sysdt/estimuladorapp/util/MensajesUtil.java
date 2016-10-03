package com.sysdt.estimuladorapp.util;

import com.sysdt.estimuladorapp.enums.RespuestaWS;


public class MensajesUtil {
	
	public static String mostrarError(int tipo, String tipoUs){
		if(tipo == RespuestaWS.EXCEPCION.getId()){
			return "Ocurri� una excepci�n en el web service";
		}
		if(tipo == RespuestaWS.INFO_INCOMPLETA.getId()){
			return "La informaci�n lleg� incompleta al web service";
		}
		if(tipo == RespuestaWS.NO_SE_ENCONTRO_REG.getId()){
			return "No se encontr� registro del "+tipoUs;
		}
		if(tipo == RespuestaWS.NOMBRE_DUPLICADO.getId()){
			return "El nombre del "+tipoUs+" ya est� registrado";
		}
		if(tipo == RespuestaWS.USUARIO_DUPLICADO.getId()){
			return "El nombre de usuario ya est� registrado";
		}
		if(tipo == RespuestaWS.IMPOSIBLE_ELIMINAR_PACIENTE.getId()){
			return "No se pueden eliminar pacientes";
		}
		if(tipo == RespuestaWS.PASSWORD_INCORRECTO.getId()){
			return "El password es incorrecto";
		}
		if(tipo == RespuestaWS.ESTIMULADOR_DUPLICADO.getId()){
			return "El n�mero de serie ya est� registrado";
		}
		if(tipo == RespuestaWS.ERROR_AL_ENVIAR_ESTIMULACION.getId()){
			return "Error al enviar la estimulaci�n";
		}
		if(tipo == RespuestaWS.CUENTA_DESHABILITADA.getId()){
			return "La cuenta est� deshabilitada";
		}
		if(tipo == RespuestaWS.PAX_YA_ASOCIADO_A_ESTIMULADOR.getId()){
			return "El paciente ya tiene un estimulador vinculado";
		}
		return "";
	}

}
