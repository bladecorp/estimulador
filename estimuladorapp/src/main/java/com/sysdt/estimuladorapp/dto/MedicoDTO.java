package com.sysdt.estimuladorapp.dto;

import java.util.List;

import com.sysdt.estimuladorapp.model.Medico;
import com.sysdt.estimuladorapp.model.Telefono;
import com.sysdt.estimuladorapp.model.Usuario;

public class MedicoDTO {

	private Medico medico;
	private List<Telefono> telefonos;
	private Usuario usuario;
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public List<Telefono> getTelefonos() {
		return telefonos;
	}
	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
