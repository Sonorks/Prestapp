package com.edu.udea.prestapp.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.exception.ExceptionController;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations="classpath:SpringBeanDefinition.xml")
public class ObjetoDaoTest {
	@Autowired
	InterfaceObjetoDao objetoDao;
	final Logger log = Logger.getLogger(ObjetoDaoTest.class.getName());
	@Test
	public void testGetObjetos() {
		log.info("Iniciando prueba de obtener todos los Objetos de la BD");
		Objeto objeto = null;
		List<Objeto> lista = null;
		try {
			//ciudadDao = new CiudadDaoSpring(); //se construye el objeto con la implementacion de la interfaz
			lista = objetoDao.getObjetos(); //llamado del metodo
			assertTrue(lista.size()>0);  //si se obtienen datos
		}catch(ExceptionController e) {
			e.printStackTrace(); //manda todo el error a consola
			fail(e.getMessage());  //mensaje personalizado
		}
	}

	@Test
	public void testGetObjetosDisponibles() {
		fail("Not yet implemented");
	}

	@Test
	public void testModificarDisponibilidad() {
		fail("Not yet implemented");
	}

	@Test
	public void testEliminarObjeto() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetObjeto() {
		fail("Not yet implemented");
	}

}
