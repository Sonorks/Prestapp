package com.edu.udea.prestapp.dto;

import java.util.Date;

public class Prestamo {

	private PrestamoID id;
	private Date fechaPrestamo;
	private Date fechaDevolucion;
	private Date fechaReserva;
	
	public PrestamoID getId() {
		return id;
	}
	public void setId(PrestamoID id) {
		this.id = id;
	}
	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}
	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}
	public Date getFechaReserva() {
		return fechaReserva;
	}
	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	
	
}
