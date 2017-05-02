package com.edu.udea.prestapp.dto;

import java.io.Serializable;

public class PrestamoID implements Serializable{

	private int idObjeto;
	private int idUsuario;
	
	public int getIdObjeto() {
		return idObjeto;
	}
	public void setIdObjeto(int idObjeto) {
		this.idObjeto = idObjeto;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
}
