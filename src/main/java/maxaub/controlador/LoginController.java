package maxaub.controlador;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import maxaub.ejb.jpa.SocioFacade;
import maxaub.modelo.Idioma;
import maxaub.modelo.Socio;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(LoginController.class.getName());

	private Boolean loggedOn = false;

	// Usuario registrado que se ha autenticado
	private Socio socio;

	private boolean showErrorLogin;
	private String usuario;
    private String clave;
    
    private Idioma selectedIdioma;
    private String idioma;//XXX temp
	private Map<String, String> idiomas;
	private List<SelectItem> idiomasListItems;
    
	public LoginController() {
		idioma = null;

		// TODO res['']
		idiomas = new HashMap<String, String>();
		idiomas.put("es", "Español");
		idiomas.put("va", "Valenciano");
		idiomas.put("en", "Inglés");
		idiomas.put("fr", "Francés");
		
		socio = null;
	}
	
	public Boolean getLoggedOn() {
		return loggedOn;
	}
	public void setLoggedOn(Boolean loggedOn) {
		this.loggedOn = loggedOn;
	}
	
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
    
	/* Formulario de login */
    public void setShowErrorLogin(Boolean showErrorAccesoUnico) {
        this.showErrorLogin = showErrorAccesoUnico;
    }
    public boolean isShowErrorLogin() {
        return showErrorLogin;
    }

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	/* Selección de idioma */
	public Idioma getSelectedIdioma() {
		return selectedIdioma;
	}
	public void setSelectedIdioma(Idioma selectedIdioma) {
		this.selectedIdioma = selectedIdioma;
	}
	
	public List<SelectItem> getIdiomasListItems() {
		if (idiomasListItems == null) {
			idiomasListItems = new ArrayList<SelectItem>();
			try {
				Iterator<String> it = idiomas.keySet().iterator();
				while(it.hasNext()){
					String key = (String) it.next();
					log.info("Clave: " + key + " -> Valor: " + idiomas.get(key));
					idiomasListItems.add(new SelectItem(key, idiomas.get(key)));
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return idiomasListItems;
	}
	
	public Locale getLocale() {
		Locale locale = null;
		if ((FacesContext.getCurrentInstance() != null) && (FacesContext.getCurrentInstance().getViewRoot() != null)) {
			locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			if (locale == null) {
				FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
			}
		}
		return locale;
	}

	@EJB
	private SocioFacade socioFacade;
	
	public String doLogin() {
		//TODO comprobar usuario y clave en BD
		socio = socioFacade.comprobarSocio(usuario, clave);
		if (socio == null){
			log.info("Usuario y/o contraseña incorrecto/s");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login incorrecto", "Usuario y/o contraseña incorrecto/s"));
		}
		else {
			log.info(socio.getNombre() + " " + socio.getApellidos());
			loggedOn = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login correcto", socio.getNombre() + " " + socio.getApellidos()));
		}
		return "index";
	}

	
	
	//XXX temp
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
}