package com.edu.udea.prestapp.bl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.dao.ObjetoDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.exception.ExceptionController;

@Transactional
public class ObjetoBL {
	private ObjetoDaoImp objetoDaoImp;

	public ObjetoDaoImp getObjetoDaoImp() {
		return objetoDaoImp;
	}

	public void setObjetoDaoImp(ObjetoDaoImp objetoDaoImp) {
		this.objetoDaoImp = objetoDaoImp;
	}
	
	public void mostrarObjetos() throws ExceptionController {
		int cantDisponibles=0;
		List<Objeto> lista;
		try {
			lista= objetoDaoImp.getObjetos();
		}catch(Exception e){
			throw new ExceptionController("Error consultando objetos", e);
		}
		for (int i = 0; i<lista.size(); i++) {
			if(lista.get(i).isDisponibilidad() && !lista.get(i).isReservado() && lista.get(i).getEstado()=="bueno") {
				System.out.println(lista.get(i).getNombre() + " disponible");
				cantDisponibles++;
			}
			else {
				System.out.println(lista.get(i).getNombre() + " no disponible");
			}
		}
		System.out.println("Cantidad de objetos disponibles: "+cantDisponibles);
	}
	
	public void modificarDisponibilidad(int id, int tipoCambio) throws ExceptionController {
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
}
