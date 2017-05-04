package com.edu.udea.prestapp.dao;

import java.util.Date;
import java.util.List;

import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfaceSancionDao {
	public void sancionarUsuario(int idUsuario, String tipoSancion, Date inicioSancion, Date finSancion) throws ExceptionController;
	public void eliminarSancion(int id) throws ExceptionController;
	public List<Sancion> getSanciones() throws ExceptionController;
}
