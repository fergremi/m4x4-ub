package maxaub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import maxaub.model.Socio;

public class UserContext implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(UserContext.class);
	
	// Usuario registrado que se ha autenticado
	private Socio socio;
	private String requestedPath;
	
//	private Idioma selectedIdioma;
//    protected List<SelectItem> idiomas;
    
    private String idioma;
	private Map<String, String> idiomas;
	private List<SelectItem> idiomasListItems;
	
	public UserContext() {
		idioma = null;

		// TODO res['']
		idiomas = new HashMap<String, String>();
		idiomas.put("es", "Español");
		idiomas.put("va", "Valenciano");
		idiomas.put("en", "Inglés");
		idiomas.put("fr", "Francés");
		
		socio = null;
	}

	
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public String getRequestedPath() {
		return requestedPath;
	}
	public void setRequestedPath(String requestedPath) {
		this.requestedPath = requestedPath;
	}

	public static UserContext getInstance() {
		UserContext userContext = null;
		
		if (FacesContext.getCurrentInstance() != null) {
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			userContext = (UserContext) elContext.getELResolver().getValue(elContext, null, "userContext");
		}
		return userContext;
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
	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}
	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the idiomasListItems
	 */
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

}