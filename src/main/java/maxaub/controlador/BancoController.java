package maxaub.controlador;


import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import maxaub.ejb.interfaz.LibroDAO;
import maxaub.modelo.Libro;

@ManagedBean
@SessionScoped
public class BancoController extends BaseInfoController implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(BancoController.class.getName());
	
	@EJB
	LibroDAO libroDAO;
	
	private List<Libro> libros;
	
	public List<Libro> getLibros() {
		if (libros == null) {
			libros = libroDAO.getLibrosActivos();
		}
		return libros;
	}
}