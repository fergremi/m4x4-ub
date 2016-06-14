package maxaub.controlador;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.event.ToggleEvent;

import maxaub.ejb.interfaz.AlumnoDAO;
import maxaub.ejb.interfaz.LibroDAO;
import maxaub.ejb.interfaz.SocioDAO;
import maxaub.modelo.Alumno;
import maxaub.modelo.Libro;
import maxaub.modelo.Lote;
import maxaub.modelo.Socio;

@ManagedBean
@SessionScoped
public class AdminController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(AdminController.class.getName());
	
	@EJB
	private SocioDAO socioDAO;
	
	@EJB
	private AlumnoDAO alumnoDAO;
	
	@EJB
	private LibroDAO libroDAO;
	
	private List<Socio> socios;
	private List<Socio> sociosFiltrados;
	private Socio socioSelected;
	private List<Alumno> alumnos;
	
	private List<Libro> libros;
	private List<Libro> librosFiltrados;
	private Libro libro;
	
	private SelectItem asignaturaSelected;
	private List<SelectItem> asignaturasListItems;
	
	private SelectItem cursoSelected;
	private List<SelectItem> cursosListItems;
	
	private SelectItem editorialSelected;
	private List<SelectItem> editorialesListItems;
	
	private SelectItem idiomaSelected;
	private List<SelectItem> idiomasListItems;
	
	private String whatsappDefault;
	private String optativoDefault;
	private String activoDefault;
	
	private Libro libroCodebar;
	private BigInteger code;

	public AdminController() {
		super();
		
		socioSelected = null;
		libro = new Libro();
		
		whatsappDefault = "";
		optativoDefault = "";
		activoDefault = "";
		
		libroCodebar = null;
		code = null;
	}
	//TODO
	public void newLote() {
		Lote lote = new Lote();
		List<Libro> librosLote = new ArrayList<Libro>();
		int curso = 0;
		switch (curso) {
		case 3:
//			7 OBLIGADAS:
//				(Lengua, LLengua, Ingles, Matemáticas, Sociales, Naturales y Valores Cívicos)
//			5 OPTATIVAS:
//				(Música, Plástica, Religión, libro de lectura y el libro de actividades de Inglés)
			break;
		case 4:
//			 6 OBLIGADAS:
//				 (Lengua, LLengua, Ingles, Matemáticas, Sociales y Naturales)
//			 3 OPTATIVAS:
//				 (Música, Religión y el libro de actividades de Inglés)		
			break;
		case 5:
//			6 OBLIGADAS:
//			(Lengua, LLengua, Ingles, Matemáticas, Sociales y Naturales)
//			4 OPTATIVAS:
//				(Música, Habilidades Sociales, Dibujo y pintura y el libro de actividades de Inglés)
			break;
		case 6:
//			6 OBLIGADAS:
//				(Lengua, LLengua, Ingles, Matemáticas, Sociales y Naturales)
//			1 OPTATIVA:
//				(Música)
			break;

		default:
			break;
		}
	}
	
	public void deVes() {
		
	}
	
	public void loteManual() {
		//TODO 1 funcion ejb q recupere los ejemplares de un tipo de libro generico (lengua, mates, etc) no asignados
		//TODO 2 q ademas mire si obligatorio o optativo
	}
	
	public void a() {
		
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
	
	public List<Alumno> getAlumnos() {
		if (alumnos == null) {
			alumnos = new ArrayList<Alumno>(alumnoDAO.getAlumnosSocio(socioSelected));
		}
		return alumnos;
	}
	
	public void onRowToggle(ToggleEvent event) {
		if (socioSelected != (Socio) event.getData()) {
			socioSelected = (Socio) event.getData();
			alumnos = null;
		}
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
				// TODO: handle exception
				log.info("duplicated!");
			} else {
				libroDAO.crearLibro(libro);
				libro = new Libro();
			}
		} else {
			//TODO add message
			log.info("libro null!");
		}
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

	public List<SelectItem> getCursosListItems() {
		if (cursosListItems == null) {
			List<String> cursos = libroDAO.getCursosLibros();
			cursosListItems = new ArrayList<SelectItem>();
			for (String curso : cursos) {
				cursosListItems.add(new SelectItem(curso, curso));
			}
		}
		return cursosListItems;
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

	public String getWhatsappDefault() {
		return whatsappDefault;
	}
	public void setWhatsappDefault(String whatsappDefault) {
		this.whatsappDefault = whatsappDefault;
	}

	public String getOptativoDefault() {
		return optativoDefault;
	}
	public void setOptativoDefault(String optativoDefault) {
		this.optativoDefault = optativoDefault;
	}

	public String getActivoDefault() {
		return activoDefault;
	}
	public void setActivoDefault(String activoDefault) {
		this.activoDefault = activoDefault;
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
			//TODO add message
			log.info("libro isbn " + libroCodebar.getIsbn());
		} else {
			//TODO add message
			log.info("libro null ");
		}
	}
	
	public void doDepositarLibro() {
		if (libroCodebar != null) {
			//TODO add message			
			log.info("libro isbn " + libroCodebar.getIsbn());
		} else {
			//TODO add message
			log.info("libro null ");
		}
	}

	public Libro getLibroCodebar() {
		return libroCodebar;
	}
	public void setLibroCodebar(Libro libroCodebar) {
		this.libroCodebar = libroCodebar;
	}
}