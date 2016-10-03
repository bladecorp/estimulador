package com.sysdt.estimuladorapp.dto;

import java.util.ArrayList;
import java.util.List;

import com.sysdt.estimuladorapp.model.Estimulador;
import com.sysdt.estimuladorapp.model.Paciente;
import com.sysdt.estimuladorapp.model.Responsable;

public class PacienteDTO {

	private int codigoError;
	private String mensaje;
	private Paciente paciente;
	private Estimulador estimulador;
	private List<Responsable> responsables;
	private boolean vinculado;
	
	public PacienteDTO(){
		mensaje="Sin";
		paciente = new Paciente();
		estimulador = new Estimulador();
		responsables = new ArrayList<Responsable>();
	}

	public int getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(int codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Estimulador getEstimulador() {
		return estimulador;
	}

	public void setEstimulador(Estimulador estimulador) {
		this.estimulador = estimulador;
	}

	public List<Responsable> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<Responsable> responsables) {
		this.responsables = responsables;
	}

	public boolean isVinculado() {
		return vinculado;
	}

	public void setVinculado(boolean vinculado) {
		this.vinculado = vinculado;
	}

	
}
