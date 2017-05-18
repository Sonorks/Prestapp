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
import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.dao.ObjetoDaoImp;
import com.edu.udea.prestapp.dao.PrestamoDaoImp;
import com.edu.udea.prestapp.dao.ReservaDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.dto.PrestamoID;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;
//COMENTAR TODA ESTA MONDA
@Transactional
public class PrestamoBL {
	final Logger log = Logger.getLogger(PrestamoBL.class.getName());
	private PrestamoDaoImp prestamoDaoImp;
	private UsuarioDaoImp usuarioDaoImp;
	private ObjetoDaoImp objetoDaoImp;
	private ReservaDaoImp reservaDaoImp;
	public PrestamoDaoImp getPrestamoDaoImp() {
		return prestamoDaoImp;
	}
	public void setPrestamoDaoImp(PrestamoDaoImp prestamoDaoImp) {
		this.prestamoDaoImp = prestamoDaoImp;
	}
	
	public void realizarPrestamo(String usuarioPrestamista, int idObjeto) throws ExceptionController {
		log.info("Iniciando metodo realizar prestamo");
		Usuario user = usuarioDaoImp.getUsuario(usuarioPrestamista);
		Objeto obj = objetoDaoImp.getObjeto(idObjeto);
		List<Reserva> lista;
		lista = reservaDaoImp.getReservas();
		int cantReservasPorUsuario=0;
		for (int i = 0 ; i < lista.size(); i++) {
			if(lista.get(i).getUsuario().getId() == user.getId()) {
				cantReservasPorUsuario++;
			}
		}
		if(obj != null && user != null && cantReservasPorUsuario==0) {
			if(obj.isDisponibilidad()) {
				Date fechaPrestamo = new Date();
				prestamoDaoImp.realizarPrestamo(usuarioPrestamista, idObjeto, fechaPrestamo);
			}
			else {
				throw new ExceptionController("El objeto no se encuentra disponible");
			}
		}
		else {
			throw new ExceptionController("Objeto o usuario inexistente");
		}
	}

	public void realizarDevolucion(String user, int idObjeto, int idUsuario) throws ExceptionController {
		log.info("Iniciando metodo realizar devolucion");
		PrestamoID prestamoId = null;
		Prestamo prestamo = null;
		Usuario usuario = usuarioDaoImp.getUsuario(user);
		Date fechaDevolucion = new Date();
		if(!usuario.isAdmin()) {
			throw new ExceptionController("El usuario no es administrador");
		}
		else {
			List<Prestamo> listaPrestamos = prestamoDaoImp.getPrestamos();
			for ( int i = 0 ; i< listaPrestamos.size(); i++) {
				if(listaPrestamos.get(i).getId().getUsuario().getId() == idUsuario
						&& listaPrestamos.get(i).getId().getObjeto().getId() == idObjeto) {
					prestamo = listaPrestamos.get(i);
				}
			}
		prestamo.setFechaDevolucion(fechaDevolucion); 
		}
		
	}
}
