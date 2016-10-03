package com.sysdt.estimuladorapp.dto;

import com.sysdt.estimuladorapp.model.Estimulador;
import com.sysdt.estimuladorapp.model.Paciente;


public class PacienteEstimDTO {

	private Paciente paciente;
	private Estimulador estimulador;
	private int codigoError;
	private String mensajeError;
	
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
	public int getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(int codigoError) {
		this.codigoError = codigoError;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	
}
