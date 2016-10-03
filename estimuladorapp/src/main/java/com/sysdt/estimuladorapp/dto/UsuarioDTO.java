package com.sysdt.estimuladorapp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sysdt.estimuladorapp.model.Administrador;
import com.sysdt.estimuladorapp.model.Medico;
import com.sysdt.estimuladorapp.model.Telefono;
import com.sysdt.estimuladorapp.model.Usuario;

@XmlRootElement
public class UsuarioDTO implements Serializable{

	private int codigoError;
	private String mensajeWS;
	private Usuario usuario;
	private List<Telefono> telefonos;
	private Medico medico;
	private Administrador administrador;
	private PacienteDTO pacienteDTO;
	
	public UsuarioDTO(){
		usuario = new Usuario();
		telefonos = new ArrayList<Telefono>();
		medico = new Medico();
		administrador = new Administrador();
		pacienteDTO = new PacienteDTO();
	}

	public int getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(int codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensajeWS() {
		return mensajeWS;
	}

	public void setMensajeWS(String mensajeWS) {
		this.mensajeWS = mensajeWS;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public PacienteDTO getPacienteDTO() {
		return pacienteDTO;
	}

	public void setPacienteDTO(PacienteDTO pacienteDTO) {
		this.pacienteDTO = pacienteDTO;
	}
	
}
