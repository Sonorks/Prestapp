package com.edu.udea.prestapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.exception.ExceptionController;

public class ObjetoDaoImp implements InterfaceObjetoDao {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public List<Objeto> getObjetos() throws ExceptionController{
		List<Objeto> lista = new ArrayList<Objeto>();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Objeto.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando objetos",e);
		}
		
		return lista;
	}
	public List<Objeto> getObjetosDisponibles() throws ExceptionController{
		List<Objeto> lista = new ArrayList<Objeto>();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Objeto.class);
			criteria.add( Restrictions.eq("disponibilidad", new Integer(0) ) ); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando disponibilidad",e);
		}
		
		return lista;
	}
	public String modificarDisponibilidad (int id, int tipoCambio) throws ExceptionController{
		Session session = null;
		Objeto objeto = getObjeto(id);
		if(tipoCambio == 1) {
			objeto.setDisponibilidad(true);
		}
		else if (tipoCambio == 2) {
			objeto.setDisponibilidad(false);
		}
		else if (tipoCambio == 3) {
			objeto.setReservado(true);
		}
		else if(tipoCambio == 4) {
			objeto.setReservado(false);
		}
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			session.update(objeto);
			return "Objeto actualizado";
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando disponibilidad",e);
		}
	}
	public String eliminarObjeto(int idObjeto) throws ExceptionController{
		Session session = null;
		Objeto objeto = getObjeto(idObjeto);
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			session.delete(objeto);
			return "Objeto eliminado";
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando disponibilidad",e);
		}
	}
	public Objeto getObjeto(int id) throws ExceptionController{
		Session session = null;
		Objeto objeto = getObjeto(id);
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			objeto = (Objeto) session.get(Objeto.class,id);
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando objeto con id "+id,e);
		}
		return objeto;
	}
}
