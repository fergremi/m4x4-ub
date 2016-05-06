package maxaub.controlador;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

import maxaub.modelo.Alumno;
import maxaub.modelo.Socio;

@ManagedBean
@SessionScoped
public class RegistroController extends BaseInfoController implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RegistroController.class);

	private Boolean showErrorRegistro;
	
	/**
	 * Cursos
	 */
	private String cursoActual;
	private String cursoFuturo;
	
	/**
	 * Subgrupo
	 */
	private String subgrupo;
	
	/**
	 * RadioButtons
	 */
	private Boolean optativas;
	private Boolean participar;
	private Boolean envioWhatsapp;
	private Boolean colaborar;
	
	private Alumno alumno;
	private Socio socio;
	
	private Boolean saltar;
	
	public RegistroController() {
		showErrorRegistro = false;
		
		cursoActual = null;
		cursoFuturo = null;

		subgrupo = null;
		
		/* RadioButtons default value */
		optativas = true;
		participar = true;
		envioWhatsapp = true;
		colaborar = true;
		
		alumno = new Alumno();
		socio = new Socio();
		
		saltar = false;
	}

	public String getCursoActual() {
		return cursoActual;
	}
	public void setCursoActual(String cursoActual) {
		this.cursoActual = cursoActual;
	}
	
	public String getCursoFuturo() {
		return cursoFuturo;
	}
	public void setCursoFuturo(String cursoFuturo) {
		this.cursoFuturo = cursoFuturo;
	}

	public String getSubgrupo() {
		return subgrupo;
	}
	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}
	
	public void subgrupoChange(ValueChangeEvent event) {
        subgrupo = null;
    }
	
	public Boolean getOptativas() {
		return optativas;
	}
	public void setOptativas(Boolean optativas) {
		this.optativas = optativas;
	}
	
	public void optativasChange(ValueChangeEvent event) {
        optativas = null;
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
	
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
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
        }
        else {
            return event.getNewStep();
        }
    }
	
	public String doRegistro() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "ok"));
		return "success";
	}
}