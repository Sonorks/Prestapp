package com.edu.udea.prestapp.bl;

import java.util.ArrayList;

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
	
	public List<Objeto> mostrarObjetos() throws ExceptionController {
		log.info("Iniciando metodo mostrar objetos");
		int cantDisponibles=0;
		List<Objeto> lista;
		List<Objeto> listaDisponibles = new ArrayList();
		try {
			lista= objetoDaoImp.getObjetos();
		}catch(Exception e){
			throw new ExceptionController("Error consultando objetos", e);
		}
		for (int i = 0; i<lista.size(); i++) {
			if(lista.get(i).isDisponibilidad() && !lista.get(i).isReservado() && lista.get(i).getEstado().equals("funcional")) {
				listaDisponibles.add(lista.get(i));
				log.debug(lista.get(i).getNombre() + " disponible");
				cantDisponibles++;
			}
			else {
				log.debug(lista.get(i).getNombre() + " no disponible");
			}
		}
		return listaDisponibles;
	}
	
	public void modificarDisponibilidad(int id, int tipoCambio) throws ExceptionController {
		log.info("Iniciando metodo modificar disponibilidad con id: "+id+ " y tipoCambio: "+tipoCambio);
		//tipos de cambio: 1=Disponible, 2=Prestado, 3 = Reservado
		Objeto obj = null;
		//Date fecha = new Date();
		try{
			obj = objetoDaoImp.getObjeto(id);
		}catch(Exception e) {
			throw new ExceptionController("Error consultando objeto con id "+id,e);
		}
		if(tipoCambio==1 && !obj.isDisponibilidad()) {
			System.out.println("Cambio tipo 1");
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);
		}
		/*else {
			throw new ExceptionController(tipoCambio+ "El objeto ya se encuentra disponible "+obj.isDisponibilidad());
		}*/
		else if(tipoCambio==2 && obj.isDisponibilidad()) {
			System.out.println("Cambio tipo 2");
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);
		}
		/*else {
			throw new ExceptionController(tipoCambio+ "El objeto ya se encuentra prestado "+obj.isDisponibilidad());
		}*/
		else if(tipoCambio==3 && !obj.isReservado()) {
			System.out.println("Cambio tipo 3");
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);
		}
		/*else {
			throw new ExceptionController(tipoCambio+ "El objeto ya se encuentra reservado "+obj.isReservado());
			}*/
		else if(tipoCambio==4 && obj.isReservado()) {
			objetoDaoImp.modificarDisponibilidad(id, tipoCambio);
		}
		else {
			throw new ExceptionController(tipoCambio+ "El objeto no se encontraba reservado "+obj.isReservado());
		}
	}

	public List<Objeto> mostrarObjetosPrestados() throws ExceptionController{
		log.info("Iniciando metodo mostrar objetos prestados");
		List<Objeto> lista;
		List<Objeto> listaPrestados = new ArrayList();
		lista = objetoDaoImp.getObjetosNoDisponibles();
		for(int i = 0 ; i < lista.size(); i ++) {
			if(!lista.get(i).isReservado()) {
				listaPrestados.add(lista.get(i));
			}
		}
		return listaPrestados;
	}
	
	public void eliminarObjeto(String user,int idObjeto) throws ExceptionController {
		log.info("Iniciando metodo eliminar objeto");
		Usuario usuario = usuarioDaoImp.getUsuario(user);
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