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
import util.ResourceBundleUtils;

@ManagedBean
@SessionScoped
public class RegistroController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(RegistroController.class.getName());

	@EJB
	private SocioDAO socioDAO;

	@EJB
	private AlumnoDAO alumnoDAO;
	
	private Boolean showErrorRegistro;
	
	private List<Alumno> alumnos;
	
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
        	socio.setActivo(true);
			socioDAO.crearSocio(socio);
			LOG.debug("El socio con DNI '" + socio.getDni() + "' se ha registrado correctamente.");
			
			for (Alumno alumno : alumnos) {
				alumno.setActivo(true);
				alumno.setSocio(socio);
				alumnoDAO.crearAlumno(alumno);
				LOG.debug("El alumno '" + alumno.getNombre() + "' del socio con DNI '" + socio.getDni() + "' se ha registrado correctamente.");
			}
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							ResourceBundleUtils.getResourceBundle("registro.correcto"),
							ResourceBundleUtils.getResourceBundle("registro.correcto.detalle")));
			socio = new Socio();
			alumnos = new ArrayList<Alumno>();
			alumnos.add(new Alumno());
			return "index?faces-redirect=true";
		} catch (Exception e) {
			LOG.debug("Ha ocurrido un error durante el registro.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							ResourceBundleUtils.getResourceBundle("registro.incorrecto"),
							ResourceBundleUtils.getResourceBundle("registro.incorrecto.detalle")));
			return null;
		}
	}
}