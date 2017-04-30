package com.edu.udea.prestapp.dao;

import java.util.List;

import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

public interface InterfaceUsuarioDao {
	public String doLogin(String usuario, String contrasena) throws ExceptionController;
	public String restablecerContrasena(String usuario, String correo, String contrasenaActual, String contrasenaNueva) throws ExceptionController;
	public String registrarUsuario(int id, String nombres, String apellidos, String correo, String usuario, String contrasena, String tipoId, String telefono, boolean admin) throws ExceptionController;
	public String modificarUsuario(int id, String nombres, String apellidos, String telefono, String correo) throws ExceptionController;
	public String eliminarUsuario(int id) throws ExceptionController;
	public List<Usuario> getUsuarios() throws ExceptionController;
}
