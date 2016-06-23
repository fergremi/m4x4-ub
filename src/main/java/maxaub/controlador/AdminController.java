package maxaub.controlador;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.event.ToggleEvent;

import maxaub.ejb.interfaz.AlumnoDAO;
import maxaub.ejb.interfaz.EjemplarDAO;
import maxaub.ejb.interfaz.LibroDAO;
import maxaub.ejb.interfaz.LoteDAO;
import maxaub.ejb.interfaz.PrestamoDAO;
import maxaub.ejb.interfaz.SocioDAO;
import maxaub.modelo.Alumno;
import maxaub.modelo.Ejemplar;
import maxaub.modelo.Libro;
import maxaub.modelo.Lote;
import maxaub.modelo.Socio;
import util.Estado;
import util.LoteWrapper;
import util.ResourceBundleUtils;

@ManagedBean
@SessionScoped
public class AdminController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(AdminController.class.getName());

	@EJB
	private LoteDAO loteDAO;
	
	@EJB
	private PrestamoDAO prestamoDAO;

	@EJB
	private SocioDAO socioDAO;

	@EJB
	private AlumnoDAO alumnoDAO;

	@EJB
	private LibroDAO libroDAO;

	@EJB
	private EjemplarDAO ejemplarDAO;

	private List<LoteWrapper> lotesWrapper;
	private List<LoteWrapper> lotesWrapperFiltrados;
	private LoteWrapper loteWrapperSelected;
	
	private List<Socio> socios;
	private List<Socio> sociosFiltrados;
	private Socio socioSelected;
	private List<Alumno> alumnos;

	private List<Libro> libros;
	private List<Libro> librosFiltrados;
	private Libro libroSelected;
	private Libro libro;
	private Libro libroCodebar;
	private BigInteger code;
	private Ejemplar ejemplarSelected;

	private List<Ejemplar> ejemplares;
	private List<Ejemplar> ejemplaresFiltrados;
	private Ejemplar ejemplar;
	
	/* Variables para el filtrado */
	private SelectItem asignaturaSelected;
	private List<SelectItem> asignaturasListItems;

	private SelectItem cursoSelected;

	private SelectItem editorialSelected;
	private List<SelectItem> editorialesListItems;

	private SelectItem idiomaSelected;
	private List<SelectItem> idiomasListItems;

	private String whatsapp;
	private String optativo;
	private String activo;

	public AdminController() {
		super();
		
		loteWrapperSelected = null;
		
		socioSelected = null;
		whatsapp = "";
		optativo = "";
		activo = "";
		
		libro = new Libro();
		libroSelected = null;
		ejemplar = new Ejemplar();
		libroCodebar = null;
		code = null;
		ejemplarSelected = null;
	}
	
	public List<LoteWrapper> getLotesWrapper() {
		if (lotesWrapper == null) {
			lotesWrapper = new ArrayList<LoteWrapper>();
			List<Lote> lotes = new ArrayList<Lote>(loteDAO.getLotes());
			for (Lote lote : lotes) {
				lotesWrapper.add(new LoteWrapper(lote, prestamoDAO.isPrestado(lote)));
			}
		}
		return lotesWrapper;
	}
	
	public List<LoteWrapper> getLotesWrapperFiltrados() {
		return lotesWrapperFiltrados;
	}
	public void setLotesWrapperFiltrados(List<LoteWrapper> lotesWrapperFiltrados) {
		this.lotesWrapperFiltrados = lotesWrapperFiltrados;
	}
	
	public void onLoteToggle(ToggleEvent event) {
		if (loteWrapperSelected != (LoteWrapper) event.getData()) {
			loteWrapperSelected = (LoteWrapper) event.getData();
		}
	}

	//TODO subgrupo
	public void newLote(int curso, String subgrupo) {
		List<String> asignaturas = new ArrayList<String>();
		/* Obligatorias comunes */
		asignaturas.add("Castellano");
		asignaturas.add("Valenciano");
		asignaturas.add("Inglés");
		asignaturas.add("Matemáticas");
		asignaturas.add("Sociales");
		asignaturas.add("Naturales");
		/* Optativas comunes */
		asignaturas.add("Música");

		switch (curso) {
		case 3:
			/* Obligatorias */
			asignaturas.add("Valores Cívicos");
			/* Optativas */
			asignaturas.add("Plástica");
			asignaturas.add("Religión");
			asignaturas.add("Libro lectura");
			asignaturas.add("Libro actividades Inglés");
			break;
		case 4:
			/* Optativas */
			asignaturas.add("Religión");
			asignaturas.add("Libro actividades Inglés");
			break;
		case 5:
			/* Optativas */
			asignaturas.add("Habilidades Sociales");
			asignaturas.add("Dibujo y pintura");
			asignaturas.add("Libro actividades Inglés");
			break;
		case 6:
			/* Sexto usa las comunes */
			break;

		default:
			/* Si el curso no existe, limpiar asignaturas */
			asignaturas = new ArrayList<String>();
			break;
		}

		if (asignaturas.isEmpty()) {
			LOG.debug("No existe el curso introducido.");
			//XXX message?
			return;
		}

		Map<String, Integer> porcentajesEstado = deVes(asignaturas.size());
		if ((porcentajesEstado != null) && (!porcentajesEstado.isEmpty())) {
			List<Ejemplar> ejemplaresLote = new ArrayList<Ejemplar>();
			
			int buenos = 0;
			int malos = 0;
			int regulares = 0;
			for (String asignatura : asignaturas) {
				/* Añade los ejemplares según su estado siguiendo el algoritmo deVes */
				if (buenos < porcentajesEstado.get("BUENO")) {
					ejemplaresLote.add(ejemplarDAO.getEjemplaresAsignatura(asignatura, 1).get(0));
					buenos++;
				} else if (regulares < porcentajesEstado.get("REGULAR")) {
					ejemplaresLote.add(ejemplarDAO.getEjemplaresAsignatura(asignatura, 2).get(0));
					regulares++;
				}else if (malos < porcentajesEstado.get("MALO")) {
					ejemplaresLote.add(ejemplarDAO.getEjemplaresAsignatura(asignatura, 3).get(0));
					malos++;
				}
			}
			
			//XXX check buenos, regulares, malos?
			
			Lote lote = new Lote();
			lote.setSubgrupo(subgrupo);
			lote.setCod(loteDAO.getNextCodLote());
			lote.setEjemplares(ejemplaresLote);
		}
	}

	public Map<String, Integer> deVes(int numAsignaturas) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		List<Ejemplar> buenos = ejemplarDAO.getEjemplaresEstadoBueno();
		List<Ejemplar> regulares = ejemplarDAO.getEjemplaresEstadoRegular();
		List<Ejemplar> malos = ejemplarDAO.getEjemplaresEstadoMalo();

		if (((malos == null) || (malos.isEmpty()))
				|| ((regulares == null) || (regulares.isEmpty()))
				|| ((buenos == null) || (buenos.isEmpty()))){
			LOG.debug("Algoritmo deVes no ejecutado -> No hay ejemplares para cada tipo de estado");
			//TODO message?
			return null;
		}

		double total = malos.size() + regulares.size() + buenos.size();

		double bueno = numAsignaturas * (buenos.size() / total);
		double regular = numAsignaturas * (regulares.size() / total);
		double malo = numAsignaturas * (malos.size() / total);

		/* Redondear por si existe parte decimal */
		bueno = Math.round(bueno);
		regular = Math.round(regular);
		malo = Math.round(malo);

		/* Comprobar que se cumple la cantidad solicitada */
		if ((bueno + regular + malo) == total) {
			map.put("BUENO", (int) bueno);
			map.put("REGULAR", (int) regular);
			map.put("MALO", (int) malo);
			return map;
		} else {
			return null;
		}
	}

	public void loteManual() {
		//TODO 1 funcion ejb q recupere los ejemplares de un tipo de libro generico (lengua, mates, etc) no asignados
		//TODO 2 q ademas mire si obligatorio o optativo
	}

	public List<Socio> getSocios() {
		if (socios == null) {
			socios = new ArrayList<Socio>(socioDAO.getSocios());
		}
		return socios;
	}

	public List<Socio> getSociosFiltrados() {
		return sociosFiltrados;
	}
	public void setSociosFiltrados(List<Socio> sociosFiltrados) {
		this.sociosFiltrados = sociosFiltrados;
	}

	public void onSocioToggle(ToggleEvent event) {
		if (socioSelected != (Socio) event.getData()) {
			socioSelected = (Socio) event.getData();
			alumnos = null;
		}
	}
	public List<Alumno> getAlumnos() {
		if ((alumnos == null) && (socioSelected != null)) {
			alumnos = new ArrayList<Alumno>(alumnoDAO.getAlumnos(socioSelected));
		}
		return alumnos;
	}

	public void guardarSocio() {
		socioDAO.guardarSocio(socioSelected);
	}
	public void guardarAlumno() {
		//XXX
		//alumnoDAO.guardarAlumno(alumno);
	}
	
	public List<Libro> getLibros() {
		if (libros == null) {
			libros = new ArrayList<Libro>(libroDAO.getLibrosActivos());
		}
		return libros;
	}

	public List<Libro> getLibrosFiltrados() {
		return librosFiltrados;
	}
	public void setLibrosFiltrados(List<Libro> librosFiltrados) {
		this.librosFiltrados = librosFiltrados;
	}

	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	
	public void doNuevoLibro() {
		if (libro != null) {
			if (libroDAO.getLibro(libro.getIsbn()) != null) {
				LOG.debug("El libro con ISBN '" + libro.getIsbn() + "' ya se encuentra en la BD.");
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_WARN,
								ResourceBundleUtils.getResourceBundle("libro.duplicated"),
								ResourceBundleUtils.getResourceBundle("libro.duplicated.detalle")));
			} else {
				libroDAO.crearLibro(libro);
				LOG.debug("El libro con ISBN '" + libro.getIsbn() + "' se ha añadido correctament en la BD.");
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_INFO,
								ResourceBundleUtils.getResourceBundle("libro.added"),
								ResourceBundleUtils.getResourceBundle("libro.added.detalle")));
				libro = new Libro();
			}
		} else {
			LOG.debug("Error al añadir el libro en la BD.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							ResourceBundleUtils.getResourceBundle("libro.error.notAdded"),
							ResourceBundleUtils.getResourceBundle("libro.error.notAdded.detalle")));
		}
	}

	public void onLibroToggle(ToggleEvent event) {
		if (libroSelected != (Libro) event.getData()) {
			libroSelected = (Libro) event.getData();
			ejemplares = null;
		}
	}
	public List<Ejemplar> getEjemplares() {
		if ((ejemplares == null) && (libroSelected != null)) {
			try {
				ejemplares = new ArrayList<Ejemplar>(ejemplarDAO.getEjemplares(libroSelected));
			} catch (Exception e) {
				ejemplares = null;
			}
		}
		return ejemplares;
	}
	public void onEjemplarToggle(ToggleEvent event) {
		if (ejemplarSelected != (Ejemplar) event.getData()) {
			ejemplarSelected = (Ejemplar) event.getData();
		}
	}
	
	public void guardarLibro() {
		libroDAO.guardarLibro(libroSelected);
	}
	public void guardarEjemplar() {
		ejemplarDAO.guardarEjemplar(ejemplarSelected);
	}
	
	public List<Ejemplar> getEjemplaresFiltrados() {
		return ejemplaresFiltrados;
	}
	public void setEjemplaresFiltrados(List<Ejemplar> ejemplaresFiltrados) {
		this.ejemplaresFiltrados = ejemplaresFiltrados;
	}
	
	public Ejemplar getEjemplar() {
		return ejemplar;
	}
	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}
	
	public BigInteger getCode() {
		return code;
	}
	public void setCode(BigInteger code) {
		this.code = code;
	}
	public String codeToString(BigInteger code) {
		return String.valueOf(code);
	}

	public void searchLibroCodebar() {
		libroCodebar = libroDAO.getLibroActivo(code);
		if (libroCodebar != null) {
			LOG.debug("Libro con ISBN '" + libroCodebar.getIsbn() + "' encontrado.");
			ejemplar = new Ejemplar();
			ejemplar.setLibro(libroCodebar);
			ejemplar.setEstado(Estado.DEFAULT_ESTADO.getId());
		} else {
			LOG.debug("Error al buscar el libro en la BD.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							ResourceBundleUtils.getResourceBundle("libro.error.notFound"),
							ResourceBundleUtils.getResourceBundle("libro.error.notFound.detalle")));
		}
	}
	public void doDepositarLibro() {
		if (libroCodebar != null) {
			ejemplarDAO.crearEjemplar(ejemplar);
			LOG.debug("El ejemplar para el libro con ISBN '" + libroCodebar.getIsbn() + "' se ha añadido correctament a la BD.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							ResourceBundleUtils.getResourceBundle("ejemplar.added"),
							ResourceBundleUtils.getResourceBundle("ejemplar.added.detalle")));
			libroCodebar = null;
			ejemplar = null;
		} else {
			LOG.debug("Error al añadir el ejemplar en la BD.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							ResourceBundleUtils.getResourceBundle("ejemplar.error.notAdded"),
							ResourceBundleUtils.getResourceBundle("ejemplar.error.notAdded.detalle")));
		}
	}

	public Libro getLibroCodebar() {
		return libroCodebar;
	}
	public void setLibroCodebar(Libro libroCodebar) {
		this.libroCodebar = libroCodebar;
	}
	
	public SelectItem getAsignaturaSelected() {
		return asignaturaSelected;
	}
	public void setAsignaturaSelected(SelectItem asignaturaSelected) {
		this.asignaturaSelected = asignaturaSelected;
	}

	public List<SelectItem> getAsignaturasListItems() {
		if (asignaturasListItems == null) {
			List<String> asignaturas = libroDAO.getAsignaturasLibros();
			asignaturasListItems = new ArrayList<SelectItem>();
			for (String asignatura : asignaturas) {
				asignaturasListItems.add(new SelectItem(asignatura, asignatura));
			}
		}
		return asignaturasListItems;
	}

	public SelectItem getCursoSelected() {
		return cursoSelected;
	}
	public void setCursoSelected(SelectItem cursoSelected) {
		this.cursoSelected = cursoSelected;
	}

	public SelectItem getEditorialSelected() {
		return editorialSelected;
	}
	public void setEditorialSelected(SelectItem editorialSelected) {
		this.editorialSelected = editorialSelected;
	}

	public List<SelectItem> getEditorialesListItems() {
		if (editorialesListItems == null) {
			List<String> editoriales = libroDAO.getEditorialesLibros();
			editorialesListItems = new ArrayList<SelectItem>();
			for (String editorial : editoriales) {
				editorialesListItems.add(new SelectItem(editorial, editorial));
			}
		}
		return editorialesListItems;
	}

	public SelectItem getIdiomaSelected() {
		return idiomaSelected;
	}
	public void setIdiomaSelected(SelectItem idiomaSelected) {
		this.idiomaSelected = idiomaSelected;
	}

	public List<SelectItem> getIdiomasListItems() {
		if (idiomasListItems == null) {
			List<String> idiomas = libroDAO.getIdiomasLibros();
			idiomasListItems = new ArrayList<SelectItem>();
			for (String idioma : idiomas) {
				idiomasListItems.add(new SelectItem(idioma, idioma));
			}
		}
		return idiomasListItems;
	}

	public String getWhatsapp() {
		return whatsapp;
	}
	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}

	public String getOptativo() {
		return optativo;
	}
	public void setOptativo(String optativo) {
		this.optativo = optativo;
	}

	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
}