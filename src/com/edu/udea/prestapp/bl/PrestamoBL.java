package com.edu.udea.prestapp.bl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.dao.ObjetoDaoImp;
import com.edu.udea.prestapp.dao.PrestamoDaoImp;
import com.edu.udea.prestapp.dao.ReservaDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

@Transactional
public class PrestamoBL {
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
	
	public void realizarPrestamo (String usuarioPrestamista, int idObjeto) throws ExceptionController {
		Usuario user = usuarioDaoImp.getUsuario(usuarioPrestamista);
		Objeto obj = objetoDaoImp.getObjeto(idObjeto);
		List<Reserva> lista;
		lista = reservaDaoImp.getReservas();
		int cantReservasPorUsuario=0;
		for (int i = 0 ; i < lista.size(); i++) {
			if(lista.get(i).getIdUsuario() == user.getId()) {
				cantReservasPorUsuario++;
			}
		}
		if(obj != null && user != null && cantReservasPorUsuario<3) {
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
}
