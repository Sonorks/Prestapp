package com.edu.udea.prestapp.dao;

import java.util.Date;
import java.util.List;

import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfaceReservaDao {
	public String realizarReserva (int idUsuario, int idObjeto, Date fechaPrestamo) throws ExceptionController;
	public String cancelarReserva (int id) throws ExceptionController;
	public Reserva getReserva(int idUsuario) throws ExceptionController;
	public String modificarReserva (int id, Date nuevaFecha) throws ExceptionController; 
	public List<Reserva> getReservas() throws ExceptionController;
}