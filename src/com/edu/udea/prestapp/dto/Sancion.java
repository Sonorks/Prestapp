package com.edu.udea.prestapp.dto;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.Date;

public class Sancion {

	private int id;
	private int idUsuario;
	private String tipoSancion;
	private Date inicioSancion;
	private Date finSancion;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getTipoSancion() {
		return tipoSancion;
	}
	public void setTipoSancion(String tipoSancion) {
		this.tipoSancion = tipoSancion;
	}
	public Date getInicioSancion() {
		return inicioSancion;
	}
	public void setInicioSancion(Date inicioSancion) {
		this.inicioSancion = inicioSancion;
	}
	public Date getFinSancion() {
		return finSancion;
	}
	public void setFinSancion(Date finSancion) {
		this.finSancion = finSancion;
	}
	
	
}
