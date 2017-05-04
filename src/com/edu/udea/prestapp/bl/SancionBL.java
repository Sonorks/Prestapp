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
import com.edu.udea.prestapp.dao.SancionDaoImp;
import com.edu.udea.prestapp.dao.UsuarioDaoImp;
import com.edu.udea.prestapp.dto.Objeto;
import com.edu.udea.prestapp.dto.Prestamo;
import com.edu.udea.prestapp.dto.Usuario;
import com.edu.udea.prestapp.exception.ExceptionController;

@Transactional
public class SancionBL {
	final Logger log = Logger.getLogger(SancionBL.class.getName());
	private SancionDaoImp sancionDaoImp;
	private UsuarioDaoImp usuarioDaoImp;
	private ReservaDaoImp reservaDaoImp;
	private ObjetoDaoImp objetoDaoImp;
	private PrestamoDaoImp prestamoDaoImp;
	public SancionDaoImp getSancionDaoImp() {
		return sancionDaoImp;
	}

	public void setSancionDaoImp(SancionDaoImp sancionDaoImp) {
		this.sancionDaoImp = sancionDaoImp;
	}
	
	public void sancionarUsuario(String tipoSancion, String usuario, Usuario admin, int idObjeto) throws ExceptionController {
		log.info("Iniciando metodo Sancionar usuario");
		Usuario user = usuarioDaoImp.getUsuario(usuario);
		Date inicioSancion = new Date();
		if(user != null && user.isAdmin()) {
			if(tipoSancion.equals("Prestamo vencido")) {
				Date finSancion = null;
				finSancion.setTime(inicioSancion.getTime()+(86400000*7));
				try {
					sancionDaoImp.sancionarUsuario(usuario, tipoSancion, inicioSancion, finSancion);
				}catch(Exception e) {
					throw new ExceptionController("Error creando sancion");
				}
			}
			else if(tipoSancion.equals("Incumplir reserva")){
				
			}
			else if(tipoSancion.equals("Objeto perdido")) {
				Date finSancion = null;
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
				
			}
		}
	}
}
