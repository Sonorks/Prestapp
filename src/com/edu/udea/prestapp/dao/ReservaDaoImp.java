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
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

public class ReservaDaoImp {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void realizarReserva (String usuario, int idObjeto, Date fechaPrestamo) throws ExceptionController{
		if(fechaPrestamo == null) {
			throw new ExceptionController("La fecha no puede estar vacia");
		}
		if(usuario.isEmpty() || usuario == null) {
			throw new ExceptionController("El usuario no puede estar vacio");
		}
		ObjetoDaoImp objeto = new ObjetoDaoImp();
		UsuarioDaoImp user = new UsuarioDaoImp(); //se obtiene la reserva por medio del getReserva
		if(objeto.getObjeto(idObjeto)!=null && user.getUsuario(usuario)!=null){	//se valida si la reserva existe
				Objeto prestamo = objeto.getObjeto(idObjeto);
				Usuario prestamista = user.getUsuario(usuario);
				Date fechaReserva = new Date();
				Reserva reserva = new Reserva(prestamista.getId(),prestamo.getId(),fechaReserva,fechaPrestamo);
				Session session = null;
				try{
					session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
					session.save(reserva);
				}catch(HibernateException e){
					throw new ExceptionController("Error al realizar el prestamo");
				}finally{
					session.close();
				}
		}else{
			throw new ExceptionController("El objeto no existe");
		}
	}
	
	public void cancelarReserva (int id) throws ExceptionController{
		Reserva reserva = getReserva(id); //se obtiene el usuario por medio del getReserva
		Session session = null;
		if(reserva.getId()==id){
			try{
				session = sessionFactory.getCurrentSession();//Se obtiene la sesion
				session.delete(reserva);
			}catch(HibernateException e){
				throw new ExceptionController("Error consultando reserva");
			}finally{
				session.close();
			}
		}else{
			throw new ExceptionController("Credenciales incorrectas");
		}
	}
	
	public Reserva getReserva(int id) throws ExceptionController{
		Session session = null;
		Reserva reserva = new Reserva();
		try{
			session = sessionFactory.getCurrentSession();//Se obtiene la sesion
			Criteria criteria = session.createCriteria(Reserva.class);
			criteria.add(Restrictions.eq("id", id));//Se agrega la condicion con la que se hace la consulta
			reserva = (Reserva)criteria.uniqueResult();//Unique porque sé y estoy seguro que me va a arrojar solo 1 valor
			//uniqueResult retorna un objeto tipo "object"
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando reserva", e);
		}finally{
			session.close();
		}
		return reserva;
	}
	public void modificarReserva (int id, Date nuevaFecha) throws ExceptionController{
		if(nuevaFecha==null){
			throw new ExceptionController("La fecha no puede estar vacia");
		}
		Reserva reserva = getReserva(id); //se obtiene la reserva por medio del getReserva
		if(reserva!=null || nuevaFecha.after(reserva.getFechaReserva())){
			reserva.setFechaReserva(nuevaFecha);
			Session session = null;
			try {
				session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
				session.update(reserva);
			} catch (HibernateException e) {
				throw new ExceptionController("Error consultando reserva");
			}finally {
				session.close();
			}
		}else{
			throw new ExceptionController("Error en la fecha de reserva");
		}
		
	}
	public List<Reserva> getReservas() throws ExceptionController{
		List<Reserva> lista = new ArrayList<Reserva>();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession(); //para conectarse con el BEAN definido en SpringConf.xml
			Criteria criteria = session.createCriteria(Reserva.class); //retorna la busqueda en la tabla seleccionada
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ExceptionController("Error consultando reservas",e);
		}finally {
			session.close();
		}
		return lista;
	}
}
