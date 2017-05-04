package com.edu.udea.prestapp.dao;

import java.util.Date;
import java.util.List;

import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfacePrestamoDao {
	public void realizarPrestamo(int idUsuario, int idObjeto, Date fechaPrestamo) throws ExceptionController; //o enviarle el usuario completo (?)
	public List<Prestamo> getPrestamos() throws ExceptionController; 
	public void realizarDevolucion(int idUsuario, int idObjeto, Date fechaDevolucion) throws ExceptionController;
	public List<Prestamo> prestamosACaducar() throws ExceptionController;
}
