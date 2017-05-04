package com.edu.udea.prestapp.bl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.exception.ExceptionController;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Usuario;

@Transactional
public class UsuarioBL {
	private UsuarioDaoImp usuarioDaoImp;

	public UsuarioDaoImp getUsuarioDaoImp() {
		return usuarioDaoImp;
	}

	public void setUsuarioDaoImp(UsuarioDaoImp usuarioDaoImp) {
		this.usuarioDaoImp = usuarioDaoImp;
	}
	
	public String doLogin(String user, String password) throws ExceptionController {
		if(user == null || user.isEmpty()) { //validando que se reciba un usuario
			throw new ExceptionController("El usuario no puede estar vacía");
		}
		if( password == null || password.isEmpty()) { //validando que se reciba una contraseña
			throw new ExceptionController("La contraseña no puede estar vacía");
		}
		for(int i = 0 ; i < user.length(); i++) {
			char c = user.charAt(i);
			if(!Character.isLetterOrDigit(c)) {
				throw new ExceptionController("El usuario contiene caracteres inválidos");
			}
		}
		for(int i = 0 ; i < password.length(); i++) {
			char c = password.charAt(i);
			if(!Character.isLetterOrDigit(c)) {
				throw new ExceptionController("La contraseña contiene caracteres inválidos");
			}
		}
		Usuario usuario = usuarioDaoImp.getUsuario(user); //se obtiene el usuario por medio del obtener(login)
		if(usuario == null) {
			throw new ExceptionController("Usuario o contraseña incorrecta");
		}
		if(!usuario.getContrasena().equals(password)) {
			throw new ExceptionController("Credenciales incorrectas");
		}
		if(usuario.isAdmin()) {
			return "admin";
		}
		return "usuario";
	}
	public void restablecerContrasena(String usuario, String correo, String contrasenaActual, String contrasenaNueva, Usuario username) throws ExceptionController{
		if(usuario.isEmpty() || usuario == null) { //validando que se reciba un usuario
			throw new ExceptionController("El usuario no puede estar vacía");
		}
		if(contrasenaActual.isEmpty() || contrasenaActual == null) { //validando que se reciba una contraseña
			throw new ExceptionController("Digite la contraseña actual");
		}
		if(contrasenaNueva.isEmpty() || contrasenaNueva == null) { //validando que se reciba una contraseña
			throw new ExceptionController("Digite la nueva contraseña");
		}
		Usuario user = usuarioDaoImp.getUsuario(usuario); //se obtiene el usuario por medio del getUsuario
		if(user == null) {
			throw new ExceptionController("Usuario o contraseña incorrecta");
		}
		if(user.getContrasena().equals(contrasenaActual) && user.getCorreo().equals(correo)) {
			usuarioDaoImp.restablecerContrasena(contrasenaNueva, user);
		}else{
			throw new ExceptionController("Credenciales incorrectas");
		}
	}
}
