package maxaub.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import maxaub.ejb.interfaz.LibroDAO;
import maxaub.ejb.interfaz.LoteDAO;
import maxaub.modelo.Ejemplar;
import maxaub.modelo.Libro;
import maxaub.modelo.Lote;

@ManagedBean
@SessionScoped
public class BancoController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(BancoController.class.getName());

	@EJB
	LibroDAO libroDAO;

	@EJB
	LoteDAO loteDAO;
	
	private List<Libro> libros;
	private List<Ejemplar> ejemplares;

	public List<Libro> getLibros() {
		if (libros == null) {
			libros = libroDAO.getLibrosActivos();
		}
		return libros;
	}

	public List<Ejemplar> getEjemplares() {
		if (ejemplares == null) {
			ejemplares =  new ArrayList<Ejemplar>();

			/* Obtener la instancia del bean 'loginController' */
			LoginController loginController =  (LoginController) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("loginController");
			
			List<Lote> lotes = loteDAO.getLotes(loginController.getSocio());
			for(Lote l : lotes) {
				LOG.debug("Lote id '" + l.getId() + "'");
				ejemplares.addAll(l.getEjemplares());
				for(Ejemplar e : l.getEjemplares()) {
					LOG.debug("	Ejemplar id '" + e.getId() + "'");
				}
			}
		}
		return ejemplares;
	}
}