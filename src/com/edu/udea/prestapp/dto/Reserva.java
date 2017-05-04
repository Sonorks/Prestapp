package com.edu.udea.prestapp.dto;

import java.util.Date;

public class Reserva {
	private int id;
	private int idUsuario;
	private int idObjeto;
	private Date fechaReserva;
	private Date fechaPrestamo;
	
	
	public Reserva(){
		
	}
	
	public Reserva(int idUsuario, int idObjeto, Date fechaReserva, Date fechaPrestamo) {
		this.idUsuario = idUsuario;
		this.idObjeto = idObjeto;
		this.fechaReserva = fechaReserva;
		this.fechaPrestamo = fechaPrestamo;
	}
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
	public int getIdObjeto() {
		return idObjeto;
	}
	public void setIdObjeto(int idObjeto) {
		this.idObjeto = idObjeto;
	}
	public Date getFechaReserva() {
		return fechaReserva;
	}
	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	
	
}
