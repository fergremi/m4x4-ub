package maxaub.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

import maxaub.ejb.interfaz.AlumnoDAO;
import maxaub.ejb.interfaz.SocioDAO;
import maxaub.modelo.Alumno;
import maxaub.modelo.Socio;
import util.Curso;
import util.SubGrupo;

@ManagedBean
@SessionScoped
public class RegistroController implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RegistroController.class);

	@EJB
	private SocioDAO socioDAO;

	@EJB
	private AlumnoDAO alumnoDAO;
	
	private Boolean showErrorRegistro;
	
	private List<Alumno> alumnos;
	private List<Curso> cursos;
	private List<SubGrupo> subgrupos;
	
	/**
	 * RadioButtons
	 */
	private Boolean participar;
	private Boolean envioWhatsapp;
	private Boolean colaborar;
	
	private Alumno alumnoActual;
	private Socio socio;
	
	private Boolean saltar;
	
	private String contraseña2;
	
	public RegistroController() {
		showErrorRegistro = false;
		
		alumnos = new ArrayList<Alumno>();
		alumnos.add(new Alumno());
		
		cursos = Curso.CURSOS;
		subgrupos = SubGrupo.SUBGRUPOS;
		
		socio = new Socio();
		
		saltar = false;
		
		contraseña2 = null;
	}
	
	public List<Alumno> getAlumnos() {
		return alumnos;
	}
	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	public void añadirAlumno() {
		alumnos.add(new Alumno());
	}
	public void quitarAlumno(Alumno alumno) {
		alumnos.remove(alumno);
	}

	public List<Curso> getCursos() {
		return cursos;
	}
	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<SubGrupo> getSubgrupos() {
		return subgrupos;
	}
	public void setSubgrupos(List<SubGrupo> subgrupos) {
		this.subgrupos = subgrupos;
	}

	public Boolean getParticipar() {
		return participar;
	}
	public void setParticipar(Boolean participar) {
		this.participar = participar;
	}
	
	public void participarChange(ValueChangeEvent event) {
		participar = null;
    }
	
	public Boolean getEnvioWhatsapp() {
		return envioWhatsapp;
	}
	public void setEnvioWhatsapp(Boolean envioWhatsapp) {
		this.envioWhatsapp = envioWhatsapp;
	}
	
	public void envioWhatsappChange(ValueChangeEvent event) {
		envioWhatsapp = null;
    }
	
	public Boolean getColaborar() {
		return colaborar;
	}
	public void setColaborar(Boolean colaborar) {
		this.colaborar = colaborar;
	}
	
	public void colaborarChange(ValueChangeEvent event) {
		colaborar = null;
    }

	public Boolean getShowErrorRegistro() {
		return showErrorRegistro;
	}
	public void setShowErrorRegistro(Boolean showErrorRegistro) {
		this.showErrorRegistro = showErrorRegistro;
	}
	
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	
	public Alumno getAlumnoActual() {
		return alumnoActual;
	}
	public void setAlumnoActual(Alumno alumnoActual) {
		this.alumnoActual = alumnoActual;
	}

	public Boolean isSaltar() {
        return saltar;
    }
    public void Saltar(Boolean saltar) {
        this.saltar = saltar;
    }
    
	public String onFlowProcess(FlowEvent event) {
        if(saltar) {
        	saltar = false;
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }
	
	public String getContraseña2() {
		return contraseña2;
	}
	public void setContraseña2(String contraseña2) {
		this.contraseña2 = contraseña2;
	}
	
	public String doRegistro() {
        try {
			socioDAO.crearSocio(socio);
			
			for (Alumno alumno : alumnos) {
				alumno.setSocio(socio);
				alumnoDAO.crearAlumno(alumno);
			}//TODO res
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "ok"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "fail"));
		}
		return "index";
	}
}