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
import com.edu.udea.prestapp.dao.PrestamoDaoImp;
import com.edu.udea.prestapp.dao.ReservaDaoImp;
import com.edu.udea.prestapp.dao.SancionDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.dto.Reserva;
import com.edu.udea.prestapp.dto.Sancion;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

@Transactional
public class SancionBL {
	final Logger log = Logger.getLogger(SancionBL.class.getName());
	@Autowired
	private SancionDaoImp sancionDaoImp;
	@Autowired
	private UsuarioDaoImp usuarioDaoImp;
	@Autowired
	private ReservaDaoImp reservaDaoImp;
	@Autowired
	private ObjetoDaoImp objetoDaoImp;
	@Autowired
	private PrestamoDaoImp prestamoDaoImp;
	public SancionDaoImp getSancionDaoImp() {
		return sancionDaoImp;
	}

	public void setSancionDaoImp(SancionDaoImp sancionDaoImp) {
		this.sancionDaoImp = sancionDaoImp;
	}
	
	public void sancionarUsuario(String tipoSancion, String usuario, String adm, int idObjeto, int idReserva) throws ExceptionController {
		log.info("Iniciando metodo Sancionar usuario");
		Usuario admin = usuarioDaoImp.getUsuario(adm);
		Usuario user = usuarioDaoImp.getUsuario(usuario);
		Date inicioSancion = new Date();
		Date finSancion = null;
		if(user != null && user.isAdmin()) {
			if(tipoSancion.equals("Prestamo vencido")) {
				finSancion.setTime(inicioSancion.getTime()+(86400000*7));
				try {
					sancionDaoImp.sancionarUsuario(usuario, tipoSancion, inicioSancion, finSancion);
				}catch(Exception e) {
					throw new ExceptionController("Error creando sancion");
				}
			}
			else if(tipoSancion.equals("Incumplir reserva")){
				Reserva reserva = reservaDaoImp.getReserva(idReserva);
				Usuario usuarito = usuarioDaoImp.getUsuario(usuario);
				finSancion.setTime(inicioSancion.getTime()+(86400000*7));
				if(reserva != null && usuarito!=null){
					try{
						sancionDaoImp.sancionarUsuario(usuario, tipoSancion, inicioSancion, finSancion);
					}catch(ExceptionController e){
						throw new ExceptionController("Error en la sanción");
					}
				}else{
					throw new ExceptionController("Credenciales incorrectas");
				}
				
			}
			else if(tipoSancion.equals("Objeto perdido")) {
				Usuario usuarito = usuarioDaoImp.getUsuario(usuario);
				if(usuarito == null) {
					throw new ExceptionController("Usuario no existe");
				}
				Objeto obj = objetoDaoImp.getObjeto(idObjeto);
				List<Prestamo> lista = prestamoDaoImp.getPrestamos();
				boolean existePrestamo= false;
				for ( int i = 0; i<lista.size(); i++) {
					if(lista.get(i).getId().getUsuario().equals(usuarito) && lista.get(i).getId().getObjeto().equals(obj)) {
						existePrestamo = true;
					}
				}
				if(obj != null) {
					sancionDaoImp.sancionarUsuario(usuario, tipoSancion, inicioSancion, finSancion);
				}else {
					throw new ExceptionController("No existe objeto");
				}
			}
			else if(tipoSancion.equals("Objeto dañado")) {
				Usuario usuarito = usuarioDaoImp.getUsuario(usuario);
				Objeto obj = objetoDaoImp.getObjeto(idObjeto);
				List<Prestamo> lista = prestamoDaoImp.getPrestamos();
				if(usuarito!=null && obj != null){
					for(int i = 0; i<lista.size(); i++) {
						if(lista.get(i).getId().getUsuario().equals(usuarito) && lista.get(i).getId().getObjeto().equals(obj)){
							try{
								sancionDaoImp.sancionarUsuario(usuario, tipoSancion, inicioSancion, finSancion);
							}catch (ExceptionController e) {
								throw new ExceptionController("No se realizó la sanción");
							}
						}else{
							throw new ExceptionController("EL objeto no se ha prestado");
						}
					}
				}else{
					throw new ExceptionController("Credenciales incorrectas");
				}
			}
		}
	}
	
	public void eliminarSancion(int id, String usuario) throws ExceptionController{
		log.info("Iniciando metodo eliminar sancion");
		boolean existeSancion = false;
		Sancion sancion = null;
		Usuario user = usuarioDaoImp.getUsuario(usuario);
		String username = usuarioDaoImp.getUsuario(usuario).getUsuario();
		if(user!=null && user.isAdmin()){
			try {
				List<Sancion> sanciones = sancionDaoImp.getSanciones();
				for (int i = 0 ; i < sanciones.size(); i++) {
					if(sanciones.get(i).getId() == id && username!=null) {
						sancion = sanciones.get(i);
						Date limitesancion = new Date();
						if(limitesancion.getTime() <= sancion.getFinSancion().getTime()){
							sancionDaoImp.eliminarSancion(usuario);
						}else{
							throw new ExceptionController("La sancion no esta activa");
						}
					}else{
						throw new ExceptionController("Credenciales incorrectas");
					}
				}
			}catch(ExceptionController e) {
				throw new ExceptionController("Error obteniendo sancion");
			}
		}
	}
}
