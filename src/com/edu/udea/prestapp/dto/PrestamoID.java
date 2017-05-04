package com.edu.udea.prestapp.dto;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

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
