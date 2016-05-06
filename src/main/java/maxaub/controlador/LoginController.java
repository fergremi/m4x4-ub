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

import maxaub.ejb.interfaz.AdminDAO;
import maxaub.ejb.interfaz.AlumnoDAO;
import maxaub.ejb.interfaz.SocioDAO;
import maxaub.modelo.Admin;
import maxaub.modelo.Alumno;
import maxaub.modelo.Socio;

@ManagedBean
@SessionScoped
public class LoginController extends BaseInfoController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(LoginController.class.getName());
	
	@EJB
	private SocioDAO socioDAO;
	
	@EJB
	private AdminDAO adminDAO;
	
	@EJB
	private AlumnoDAO alumnoDAO;
	
	private Boolean socioLogged = false;
	private Boolean adminLogged = false;

	// Usuario registrado que se ha autenticado
	private Socio socio;
	private Admin admin;

	private boolean showErrorLogin;
	private String usuario;
    private String clave;
    
    private Locale idioma;
    private String idiomaSelected;
    private Map<String, String> idiomas;
	private List<SelectItem> idiomasListItems;
    
	public LoginController() {
		idioma = getLocale();
		idiomaSelected = "es";
		idiomas = new HashMap<String, String>();
		idiomas.put("es", "Español");
		idiomas.put("va", "Valenciano");
		
		socio = null;
	}
	
	public Boolean getSocioLogged() {
		return socioLogged;
	}
	public void setSocioLogged(Boolean socioLogged) {
		this.socioLogged = socioLogged;
	}
	
	public Boolean getAdminLogged() {
		return adminLogged;
	}
	public void setAdminLogged(Boolean adminLogged) {
		this.adminLogged = adminLogged;
	}
	
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
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
	
	/* locale html lang */
	public Locale getIdioma() {
		return idioma;
	}
	public void setIdioma(Locale idioma) {
		this.idioma = idioma;
	}
	
	public String getIdiomaSelected() {
		return idiomaSelected;
	}
	public void setIdiomaSelected(String idiomaSelected) {
		this.idiomaSelected = idiomaSelected;
		idioma = new Locale(idiomaSelected, idiomas.get(idiomaSelected));
		FacesContext.getCurrentInstance().getViewRoot().setLocale(idioma);
	}

	public List<SelectItem> getIdiomasListItems() {
		if (idiomasListItems == null) {
			idiomasListItems = new ArrayList<SelectItem>();
			try {
				Iterator<String> it = idiomas.keySet().iterator();
				while(it.hasNext()){
					String key = (String) it.next();
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

	public String login() {
		/* Al introducir un DNI el login es de un Socio */
		if (usuario.matches("\\d{8}[A-Za-z]")) {
			socio = socioDAO.comprobarSocio(usuario, clave);
			if (socio == null){
				log.info("[Login {Socio} incorrecto]: Usuario y/o contraseña incorrecto/s");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login incorrecto", "Usuario y/o contraseña incorrecto/s"));
			}
			else {
				log.info("[Login {Socio} correcto]: " + socio.getNombre() + " " + socio.getApellidos());
				socioLogged = true;
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login correcto", socio.getNombre() + " " + socio.getApellidos()));
				return "banco?faces-redirect=true";
			}
		}
		/* Si no se introduce un DNI, se considera un administrador */
		else {
			admin = adminDAO.comprobarAdmin(usuario, clave);
			if (admin == null){
				log.info("[Login {Admin} incorrecto]: Usuario y/o contraseña incorrecto/s");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login incorrecto", "Usuario y/o contraseña incorrecto/s"));
			}
			else {
				log.info("[Login {Admin} correcto]: " + usuario);
				adminLogged = true;
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login correcto", usuario));
				return "admin?faces-redirect=true";
			}
		}
		return "index"; /* vista por defecto */
	}
	
	public String logout() {
		if (socio != null) {
			socioLogged = false;
			socio = null;
		}
		if (admin != null) {
			adminLogged = false;
			admin = null;
		}
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión finalizada", "Adiós"));		
		return "index"; /* vista por defecto */
	}
	
	public List<Alumno> getAlumnnos() {
		return alumnoDAO.getAlumnosSocioActivo(socio);
	}
	
	public String editarDatosSocio() {
		log.info("editar datos socio");
		return "datos_socios"; /* vista por defecto */
	}
	
	public String editarDatosAdmin() {
		log.info("editar datos admin");
		return "datos_admin"; /* vista por defecto */
	}
}