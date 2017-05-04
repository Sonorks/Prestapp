package com.edu.udea.prestapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

public class PrestamoDaoImp {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void realizarPrestamo(String usuario, int idObjeto, Date fechaPrestamo) throws ExceptionController{
		Date date = new Date(2016,1,1);
		if(usuario.isEmpty() || usuario == null) {
			throw new ExceptionController("El usuario no puede estar vacio");
		}
		if(fechaPrestamo.before(date) || fechaPrestamo == null) {
			throw new ExceptionController("La fecha de prestamo no puede estar vacia");
		}
		ObjetoDaoImp objeto = new ObjetoDaoImp();
		UsuarioDaoImp user = new UsuarioDaoImp(); //se obtiene el usuario por medio del getUsuario
		if(objeto.getObjeto(idObjeto)!=null && user.getUsuario(usuario)!=null){	//se valida si la reserva existe
			Objeto prestado = objeto.getObjeto(idObjeto);
			Usuario prestamista = user.getUsuario(usuario);
			Date fechaPrestacion = new Date();
			Prestamo prestamo = new Prestamo(prestamista.getId(),prestado.getId(),fechaPrestamo,);
			Session session = null;
			try{
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.save(prestamo);
			}catch(HibernateException e){
				throw new ExceptionController("Error al realizar el prestamo");
			}finally{
				session.close();
			}
	}else{
		throw new ExceptionController("El objeto no existe");
	}
	}
	public List<Prestamo> getPrestamos() throws ExceptionController{
		List<Prestamo> lista = new ArrayList<Prestamo>();
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Prestamo.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch (HibernateException e) {
			throw new ExceptionController("Error consultando reservas",e);
		}finally {
			session.close();
		}
		return lista;
	}
	public void realizarDevolucion(int idUsuario, int idObjeto, Date fechaDevolucion) throws ExceptionController{
		
	}
	public List<Prestamo> prestamosACaducar() throws ExceptionController{
		List<Prestamo> lista = new ArrayList<Prestamo>();
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Prestamo.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch (HibernateException e) {
			throw new ExceptionController("Error consultando reservas",e);
		}finally {
			session.close();
		}
		return lista;
	}
}
