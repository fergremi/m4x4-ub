package maxaub;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import model.Idioma;
import model.Socio;

public class UserContext implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Usuario registrado que se ha autenticado
	private Socio socio;
	private String requestedPath;
	
	private Idioma selectedIdioma;
    protected List<SelectItem> idiomas;
	
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
	 * @return the selectedIdioma
	 */
	public Idioma getSelectedIdioma() {
		return selectedIdioma;
	}
	/**
	 * @param selectedIdioma the selectedIdioma to set
	 */
	public void setSelectedIdioma(Idioma selectedIdioma) {
		this.selectedIdioma = selectedIdioma;
	}
	
}