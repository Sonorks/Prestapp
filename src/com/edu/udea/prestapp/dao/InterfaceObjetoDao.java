package com.edu.udea.prestapp.dao;

import java.util.Date;

import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfaceObjetoDao {
	public <List>Objeto getObjetos() throws ExceptionController;
	public <List>Objeto getObjetosDisponibles() throws ExceptionController;
	public String modificarDisponibilidad (int id, int tipoCambio, Date fechaCambio) throws ExceptionController;
	public String eliminarObjeto(int idObjeto) throws ExceptionController;
}
