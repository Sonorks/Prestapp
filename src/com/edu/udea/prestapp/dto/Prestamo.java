package com.edu.udea.prestapp.dto;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.Date;

public class Prestamo {

	private PrestamoID id;
	private Date fechaPrestamo;
	private Date fechaDevolucion;
	private Date fechaReserva;
	
	public Prestamo(){
		
	}
	
	public Prestamo(PrestamoID id, Date fechaPrestamo, Date fechaDevolucion,
			Date fechaReserva) {
		this.id = id;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.fechaReserva = fechaReserva;
	}


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
