package maxaub.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import maxaub.model.Alumno;
import maxaub.model.Socio;

@ManagedBean
@SessionScoped
public class RegistroController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(RegistroController.class);

	private Boolean showErrorRegistroAlumno;
	private Boolean showErrorRegistroTutor;
	private Boolean showErrorRegistroColaborar;
	
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

	public RegistroController() {
		showErrorRegistroAlumno = false;
		showErrorRegistroTutor = false;
		showErrorRegistroColaborar = false;
		
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
		
		alumno = null;
		socio = null;
	}

	/**
	 * @return the cursoActual
	 */
	public String getCursoActual() {
		return cursoActual;
	}

	/**
	 * @param cursoActual the cursoActual to set
	 */
	public void setCursoActual(String cursoActual) {
		this.cursoActual = cursoActual;
	}
	
	/**
	 * @return the cursoFuturo
	 */
	public String getCursoFuturo() {
		return cursoFuturo;
	}

	/**
	 * @param cursoFuturo the cursoFuturo to set
	 */
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

	/**
	 * @return the subgrupo
	 */
	public String getSubgrupo() {
		return subgrupo;
	}

	/**
	 * @param subgrupo the subgrupo to set
	 */
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

	/**
	 * @return the optativas
	 */
	public Boolean getOptativas() {
		return optativas;
	}

	/**
	 * @param optativas the optativas to set
	 */
	public void setOptativas(Boolean optativas) {
		this.optativas = optativas;
	}
	
	public void optativasChange(ValueChangeEvent event) {
        optativas = null;
    }
	
	/**
	 * @return the participar
	 */
	public Boolean getParticipar() {
		return participar;
	}

	/**
	 * @param participar the participar to set
	 */
	public void setParticipar(Boolean participar) {
		this.participar = participar;
	}
	
	public void participarChange(ValueChangeEvent event) {
		participar = null;
    }
	
	/**
	 * @return the envioWhatsapp
	 */
	public Boolean getEnvioWhatsapp() {
		return envioWhatsapp;
	}

	/**
	 * @param envioWhatsapp the envioWhatsapp to set
	 */
	public void setEnvioWhatsapp(Boolean envioWhatsapp) {
		this.envioWhatsapp = envioWhatsapp;
	}
	
	public void envioWhatsappChange(ValueChangeEvent event) {
		envioWhatsapp = null;
    }
	
	/**
	 * @return the colaborar
	 */
	public Boolean getColaborar() {
		return colaborar;
	}

	/**
	 * @param colaborar the colaborar to set
	 */
	public void setColaborar(Boolean colaborar) {
		this.colaborar = colaborar;
	}
	
	public void colaborarChange(ValueChangeEvent event) {
		colaborar = null;
    }

	/**
	 * @return the showErrorRegistroAlumno
	 */
	public Boolean getShowErrorRegistroAlumno() {
		return showErrorRegistroAlumno;
	}

	/**
	 * @param showErrorRegistroAlumno the showErrorRegistroAlumno to set
	 */
	public void setShowErrorRegistroAlumno(Boolean showErrorRegistroAlumno) {
		this.showErrorRegistroAlumno = showErrorRegistroAlumno;
	}
	
	/**
	 * @return the showErrorRegistroTutor
	 */
	public Boolean getShowErrorRegistroTutor() {
		return showErrorRegistroTutor;
	}

	/**
	 * @param showErrorRegistroTutor the showErrorRegistroTutor to set
	 */
	public void setShowErrorRegistroTutor(Boolean showErrorRegistroTutor) {
		this.showErrorRegistroTutor = showErrorRegistroTutor;
	}
	
	/**
	 * @return the showErrorColaborar
	 */
	public Boolean getShowErrorRegistroColaborar() {
		return showErrorRegistroColaborar;
	}

	/**
	 * @param showErrorColaborar the showErrorColaborar to set
	 */
	public void setShowErrorRegistroColaborar(Boolean showErrorRegistroColaborar) {
		this.showErrorRegistroColaborar = showErrorRegistroColaborar;
	}

	/**
	 * @return the alumno
	 */
	public Alumno getAlumno() {
		return alumno;
	}

	/**
	 * @param alumno the alumno to set
	 */
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	/**
	 * @return the socio
	 */
	public Socio getSocio() {
		return socio;
	}

	/**
	 * @param socio the socio to set
	 */
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	
	public String doRegistroAlumno() {
		return "success";
	}
	
	public String doRegistroTutor() {
		return "success";
	}
	
	public String doRegistroColaborar() {
		return "success";
	}
}