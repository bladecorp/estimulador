package com.sysdt.estimuladorapp.enums;

public enum RespuestaWS {

	EXITO(-1),
	EXCEPCION(-2),
	INFO_INCOMPLETA(-3),
	NO_SE_ENCONTRO_REG(-4),
	NOMBRE_DUPLICADO(-5),
	USUARIO_DUPLICADO(-6),
	IMPOSIBLE_ELIMINAR_PACIENTE(-7),
	PASSWORD_INCORRECTO(-8),
	ESTIMULADOR_DUPLICADO(-9),
	ERROR_AL_ENVIAR_ESTIMULACION(-10),
	CUENTA_DESHABILITADA(-11),
	PAX_YA_ASOCIADO_A_ESTIMULADOR(-12);
	
	private int id;

	private RespuestaWS(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
