package com.edu.udea.prestapp.bl;

/**
 * @author Cristian Berrio - cbp453252.hdrl@gmail.com
 * @author Julian Vasquez - julivas96@gmail.com
 * @author David Acevedo - davida.acevedo@udea.edu.co
 * @version = 1.0
 */

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.dao.ObjetoDaoImp;
import com.edu.udea.prestapp.dao.ReservaDaoImp;
import com.edu.udea.prestapp.dao.SancionDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.exception.ExceptionController;

@Transactional
public class ReservaBL {
	final Logger log = Logger.getLogger(ReservaBL.class.getName());
	@Autowired
	private ReservaDaoImp reservaDaoImp;
	@Autowired
	private UsuarioDaoImp usuarioDaoImp;
	@Autowired
	private ObjetoDaoImp objetoDaoImp;
	@Autowired
	private SancionDaoImp sancionDaoImp;
	public ReservaDaoImp getReservaDaoImp() {
		return reservaDaoImp;
	}

	public void setReservaDaoImp(ReservaDaoImp reservaDaoImp) {
		this.reservaDaoImp = reservaDaoImp;
	}
	
	public void reservarObjeto(String usuario, int idObjeto, Date fechaPrestamo) throws ExceptionController {
		log.info("Iniciando metodo reservar objeto");
		Date fechaActual = new Date(); 
		List<Reserva> lista = reservaDaoImp.getReservas();
		int cantReservasPorUsuario=0;
		for (int i = 0 ; i < lista.size(); i++) {
			if(lista.get(i).getUsuario().getId() == usuarioDaoImp.getUsuario(usuario).getId()) {
				cantReservasPorUsuario++;
			}
		}
		List<Sancion> listaSancion = sancionDaoImp.getSanciones();
		for (int j = 0 ; j < lista.size(); j++) {
			if(lista.get(j).getUsuario().getUsuario().equals(usuario)) {
				
				throw new ExceptionController("El usuario está sancionado.");
			}
		}
		Objeto obj = objetoDaoImp.getObjeto(idObjeto);
		if(fechaPrestamo.getTime() >= fechaActual.getTime()+259200000 && cantReservasPorUsuario == 0 && obj.isDisponibilidad()) {
			reservaDaoImp.realizarReserva(usuario, idObjeto, fechaPrestamo);
		}
		else {
			throw new ExceptionController("Minimo 3 dias de anticipacion para reservar");
		}
	}

	public void cancelarReserva(int id, String usuario) throws ExceptionController {
		log.info("Iniciando metodo cancelar Reserva");
		Reserva reserva = reservaDaoImp.getReserva(id);
		Date fechaPrestamo = reserva.getFechaPrestamo();
		Date actual = new Date();
		if(fechaPrestamo.getTime()-86400000 < actual.getTime()) {
			throw new ExceptionController("La reserva no se puede cancelar faltando un dia");
		}else {
			if(reserva != null && reserva.getUsuario().getUsuario().equals(usuario)) {
				reservaDaoImp.cancelarReserva(id);
			}
			else {
				throw new ExceptionController("La reserva no existe o el usuario no es el mismo");
			}
		}
		
	}

	public void modificarReserva(int id, String usuario, Date nuevaFecha) throws ExceptionController {
		log.info("Iniciando metodo modificar Reserva");
		Reserva reserva = reservaDaoImp.getReserva(id);
		Date fechaPrestamo = reserva.getFechaPrestamo();
		Date actual = new Date();
		if(fechaPrestamo.getTime()-(86400000*3) < actual.getTime()) {
			throw new ExceptionController("La reserva no se puede modificar faltando 3 dia");
		}else {
			if(reserva != null && reserva.getUsuario().getUsuario().equals(usuario)) {
				reservaDaoImp.modificarReserva(id, nuevaFecha);
			}
			else {
				throw new ExceptionController("La reserva no existe o el usuario no es el mismo");
			}
		}
	}
}
