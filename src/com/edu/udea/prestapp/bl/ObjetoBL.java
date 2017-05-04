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
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

@Transactional
public class ObjetoBL {
	final Logger log = Logger.getLogger(ObjetoBL.class.getName());
	private ObjetoDaoImp objetoDaoImp;
	private UsuarioDaoImp usuarioDaoImp;
	public ObjetoDaoImp getObjetoDaoImp() {
		return objetoDaoImp;
	}

	public void setObjetoDaoImp(ObjetoDaoImp objetoDaoImp) {
		this.objetoDaoImp = objetoDaoImp;
	}
	
	public void mostrarObjetos() throws ExceptionController {
		log.info("Iniciando metodo mostrar objetos");
		int cantDisponibles=0;
		List<Objeto> lista;
		try {
			lista= objetoDaoImp.getObjetos();
		}catch(Exception e){
			throw new ExceptionController("Error consultando objetos", e);
		}
		for (int i = 0; i<lista.size(); i++) {
			if(lista.get(i).isDisponibilidad() && !lista.get(i).isReservado() && lista.get(i).getEstado()=="bueno") {
				log.debug(lista.get(i).getNombre() + " disponible");
				cantDisponibles++;
			}
			else {
				log.debug(lista.get(i).getNombre() + " no disponible");
			}
		}
		System.out.println("Cantidad de objetos disponibles: "+cantDisponibles);
	}
	
	public void modificarDisponibilidad(int id, int tipoCambio) throws ExceptionController {
		log.info("Iniciando metodo modificar disponibilidad");
		//tipos de cambio: 1=Disponible, 2=Prestado, 3 = Reservado
		Objeto obj = null;
		//Date fecha = new Date();
		try{
			obj = objetoDaoImp.getObjeto(id);
		}catch(Exception e) {
			throw new ExceptionController("Error consultando objeto con id "+id,e);
		}
		if(tipoCambio==1 && obj.isDisponibilidad()) {
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);
		}
		else {
			throw new ExceptionController("El objeto ya se encuentra disponible");
		}
		if(tipoCambio==2 && !obj.isDisponibilidad()) {
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);
		}
		else {
			throw new ExceptionController("El objeto ya se encuentra prestado");
		}
		if(tipoCambio==3 && !obj.isReservado()) {
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);
		}
		else {
			throw new ExceptionController("El objeto ya se encuentra reservado");
			}
		if(tipoCambio==4 && obj.isReservado()) {
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);
		}
		else {
			throw new ExceptionController("El objeto no se encontraba reservado");
		}
	}

	public void mostrarObjetosPrestados() throws ExceptionController{
		log.info("Iniciando metodo mostrar objetos prestados");
		List<Objeto> lista;
		lista = objetoDaoImp.getObjetosNoDisponibles();
		for(int i = 0 ; i < lista.size(); i ++) {
			if(!lista.get(i).isReservado())
			System.out.println(lista.get(i).getNombre()+ " prestado");
		}
	}
	
	public void eliminarObjeto(Usuario usuario,int idObjeto) throws ExceptionController {
		log.info("Iniciando metodo eliminar objeto");
		if(!usuario.isAdmin()) {
			throw new ExceptionController("El usuario no es administrador");
		}
		else {
			Objeto obj = objetoDaoImp.getObjeto(idObjeto);
			if(obj != null && obj.isDisponibilidad() && !obj.isReservado()) {
				objetoDaoImp.eliminarObjeto(idObjeto);
			}
			else {
				throw new ExceptionController("El objeto no puede ser borrado");
			}
		}
	}
}