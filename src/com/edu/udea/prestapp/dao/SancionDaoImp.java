package com.edu.udea.prestapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.exception.ExceptionController;

public class SancionDaoImp {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void sancionarUsuario(String usuario, String tipoSancion, Date inicioSancion, Date finSancion) throws ExceptionController{
		Date date = new Date(2016,1,1);
		if(tipoSancion.isEmpty() || tipoSancion == null) {
			throw new ExceptionController("El tipo de sanción no puede estar vacio");
		}
		if(inicioSancion.before(date) || inicioSancion == null) {
			throw new ExceptionController("La fecha inicial no puede estar vacia");
		}
		if(finSancion == null || finSancion.before(inicioSancion)) {
			throw new ExceptionController("La fecha final no puede estar vacio");
		}
		UsuarioDaoImp user = new UsuarioDaoImp(); //se obtiene el usuario por medio del getUsuario
		if(user.getUsuario(usuario)!=null){
			Usuario sancionado = user.getUsuario(usuario);
			Session session = null;
			try{
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.save(sancionado);
			}catch(HibernateException e) {
				throw new ExceptionController("Error al sancionar el usuario");
			}finally {
				session.close();
			}
		}else{
			throw new ExceptionController("El usuario no existe");
		}
	}
	public void eliminarSancion(String usuario) throws ExceptionController{
		if(usuario.isEmpty() || usuario == null) {
			throw new ExceptionController("El usuario no puede estar vacio");
		}
		UsuarioDaoImp user = new UsuarioDaoImp(); //se obtiene el usuario por medio del getUsuario
		if(user.getUsuario(usuario)!=null){
			Usuario sancionado = user.getUsuario(usuario);
			Session session = null;
			try{
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.delete(sancionado);
			}catch(HibernateException e){
				throw new ExceptionController("Error consultando usuario");
			}finally{
				session.close();
			}
		}else{
			throw new ExceptionController("Credenciales incorrectas");
		}
	}
	
	public List<Sancion> getSanciones() throws ExceptionController{
		List<Sancion> lista = new ArrayList<Sancion>();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Sancion.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando sanciones",e);
		}finally {
			session.close();
		}
		return lista;
		
	}
}
