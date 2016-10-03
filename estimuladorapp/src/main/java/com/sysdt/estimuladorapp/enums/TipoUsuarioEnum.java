package com.sysdt.estimuladorapp.enums;

public enum TipoUsuarioEnum {

	ADMINISTRADOR(1),
	MEDICO(2),
	PACIENTE(3);
	
	private int id;

	private TipoUsuarioEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	};
	
}
