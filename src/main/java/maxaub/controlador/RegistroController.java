package maxaub.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

import maxaub.modelo.Alumno;
import maxaub.modelo.Socio;

@ManagedBean
@SessionScoped
public class RegistroController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(RegistroController.class);

	private Boolean showErrorRegistro;
	
	/**
	 * Cursos
	 */
	private String cursoActual;
	private Map<String, String> cursosActuales;
	private List<SelectItem> cursosActualesListItems;

	private String cursoFuturo;
	private Map<String, String> cursosFuturos;
	private List<SelectItem> cursosFuturosListItems;
	
	/**
	 * Subgrupos
	 */
	private String subgrupo;
	private static List<String> subgrupos;
	private List<SelectItem> subgruposListItems;
	
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
	
	private String clave2;

	public RegistroController() {
		showErrorRegistro = false;
		
		cursoActual = null;
		cursoFuturo = null;

		// TODO res['']
		cursosActuales = new HashMap<String, String>();
		cursosActuales.put("3º", "Tercero");
		cursosActuales.put("4º", "Cuarto");
		cursosActuales.put("5º", "Quinto");
		cursosActuales.put("6º", "Sexto");

		// TODO res['']
		cursosFuturos = new HashMap<String, String>();
		cursosFuturos.put("3º", "tercero");
		cursosFuturos.put("4º", "cuarto");
		cursosFuturos.put("5º", "quinto");
		cursosFuturos.put("6º", "sexto");
		
		subgrupo = null;
		// TODO res['']
		subgrupos = Arrays.asList("es", "va");
		
		/* RadioButtons default value */
		optativas = true;
		participar = true;
		envioWhatsapp = true;
		colaborar = true;
		
		alumno = new Alumno();
		socio = new Socio();
		
		saltar = false;
		
		clave2 = null;
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

	public List<SelectItem> getCursosActualesListItems() {
		if (cursosActualesListItems == null) {
			cursosActualesListItems = new ArrayList<SelectItem>();
			try {
				Iterator<String> it = cursosActuales.keySet().iterator();
				while(it.hasNext()){
					String key = (String) it.next();
					log.info("Clave: " + key + " -> Valor: " + cursosActuales.get(key));
					cursosActualesListItems.add(new SelectItem(key, cursosActuales.get(key)));
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return cursosActualesListItems;
	}
	
	public List<SelectItem> getCursosFuturosListItems() {
		if (cursosFuturosListItems == null) {
			cursosFuturosListItems = new ArrayList<SelectItem>();
			try {
				Iterator<String> it = cursosFuturos.keySet().iterator();
				while(it.hasNext()){
					String key = (String) it.next();
					log.info("Clave: " + key + " -> Valor: " + cursosFuturos.get(key));
					cursosFuturosListItems.add(new SelectItem(key, cursosFuturos.get(key)));
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return cursosFuturosListItems;
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
	
	public List<SelectItem> getSubgruposListItems() {
		if (subgruposListItems == null) {
			subgruposListItems = new ArrayList<SelectItem>();
			try {
				for (String subgrupo : subgrupos) {
					log.info("Subgrupo: " + subgrupo);
					subgruposListItems.add(new SelectItem(subgrupo, subgrupo));
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return subgruposListItems;
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
    
    public String getClave2() {
		return clave2;
	}
	public void setClave2(String clave2) {
		this.clave2 = clave2;
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