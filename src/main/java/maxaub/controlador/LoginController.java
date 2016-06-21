package maxaub.controlador;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import maxaub.ejb.interfaz.AdminDAO;
import maxaub.ejb.interfaz.AlumnoDAO;
import maxaub.ejb.interfaz.SocioDAO;
import maxaub.modelo.Admin;
import maxaub.modelo.Alumno;
import maxaub.modelo.Socio;
import util.Idioma;
import util.Tema;
import util.Utils;
//TODO
//String param =
//externalContext.getInitParameter("primefaces.THEME");

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

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
	private String contraseña;

	private Locale idioma;
	private Idioma idiomaSelected;
	private List<Idioma> idiomas;

	private Tema temaSelected;
	private List<Tema> temas;
	
	private List<Alumno> alumnos;

	public LoginController() {
		socio = null;

		idioma = Utils.getLocale();
		idiomaSelected = Idioma.DEFAULT_IDIOMA;
		idiomas = Idioma.IDIOMAS;

		temaSelected = Tema.DEFAULT_TEMA;
		temas = Tema.TEMAS;
		
		alumnos = null;
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

	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	/* locale html lang */
	public Locale getIdioma() {
		return idioma;
	}
	public void setIdioma(Locale idioma) {
		this.idioma = idioma;
	}

	public Idioma getIdiomaSelected() {
		return idiomaSelected;
	}
	public void setIdiomaSelected(Idioma idiomaSelected) {
		this.idiomaSelected = idiomaSelected;
		idioma = new Locale(idiomaSelected.getCod(), idiomaSelected.getNombre());
		FacesContext.getCurrentInstance().getViewRoot().setLocale(idioma);
	}

	public List<Idioma> getIdiomas() {
		return idiomas;
	}

	public Tema getTemaSelected() {
		return temaSelected;
	}
	public void setTemaSelected(Tema temaSelected) {
		this.temaSelected = temaSelected;
	}
	
	public List<Tema> getTemas() {
		return temas;
	}

	public String login() {
		boolean login = true;
		
		if(usuario == null || usuario.trim().length() == 0) {
			String required = 
					Utils.paramMsg("javax.faces.component.UIInput.REQUIRED_detail", 
							Utils.getResourceBundle("usuario"));

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Utils.getResourceBundle("javax.faces.component.UIInput.REQUIRED"),
							required));
			login = false;
		}
		if(contraseña == null || contraseña.trim().length() == 0) {
			String required = 
					Utils.paramMsg("javax.faces.component.UIInput.REQUIRED_detail", 
							Utils.getResourceBundle("contraseña"));

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Utils.getResourceBundle("javax.faces.component.UIInput.REQUIRED"),
							required));
			login = false;
		}
		
		if (!login) {
			return null;
		}

		/* Al introducir un DNI el login es de un Socio */
		if (usuario.matches("\\d{8}[A-Za-z]")) {
			socio = socioDAO.comprobarSocio(usuario, contraseña);
			if (socio == null){
				LOG.debug("[Login {Socio} incorrecto]: Usuario y/o contraseña incorrecto/s");
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Utils.getResourceBundle().getString("login.incorrecto"),
								Utils.getResourceBundle().getString("login.incorrecto.detalle")));
			}
			else {
				LOG.debug("[Login {Socio} correcto]: " + socio.getNombre() + " " + socio.getApellidos());
				socioLogged = true;
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								Utils.getResourceBundle().getString("login.correcto"),
								socio.getNombre() + " " + socio.getApellidos()));
				return "ejemplares?faces-redirect=true";
			}
		}
		/* Si no se introduce un DNI, se considera un administrador */
		else {
			admin = adminDAO.comprobarAdmin(usuario, contraseña);
			if (admin == null){
				LOG.debug("[Login {Admin} incorrecto]: Usuario y/o contraseña incorrecto/s");
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Utils.getResourceBundle("login.incorrecto"),
								Utils.getResourceBundle("login.incorrecto.detalle")));
			}
			else {
				LOG.debug("[Login {Admin} correcto]: " + usuario);
				adminLogged = true;
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								Utils.getResourceBundle("login.correcto"),
								usuario));
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
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						Utils.getResourceBundle("logout"),
						Utils.getResourceBundle("logout.detalle")));		
		return "index"; /* vista por defecto */
	}

	public List<Alumno> getAlumnos() {
		alumnos = alumnoDAO.getAlumnosSocioActivo(socio);
		return alumnos;
	}
	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos; 
	}

	public String editarDatosSocio() {
		try {
			socioDAO.guardarSocio(socio);
			for (Alumno alumno : alumnos) {
				alumnoDAO.guardarAlumno(alumno);
			}
			LOG.debug("Los datos del socio con DNI '" + socio.getDni() + "' se han editado correctamente.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Utils.getResourceBundle("edit.correcto"),
							Utils.getResourceBundle("edit.correcto.detalle")));
		} catch (Exception e) {
			LOG.debug("Ha ocurrido un error al editar los datos del socio.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Utils.getResourceBundle("edit.incorrecto"),
							Utils.getResourceBundle("edit.incorrecto.detalle")));
		}
		return "datos_socio"; /* vista por defecto */
	}

	public String editarDatosAdmin() {
		try {
			adminDAO.guardarAdmin(admin);
			LOG.debug("Los datos del administrador '" + admin.getUsuario() + "' se han editado correctamente.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Utils.getResourceBundle("edit.correcto"),
							Utils.getResourceBundle("edit.correcto.detalle")));
		} catch (Exception e) {
			LOG.debug("Ha ocurrido un error al editar los datos del administrador.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Utils.getResourceBundle("edit.incorrecto"),
							Utils.getResourceBundle("edit.incorrecto.detalle")));
		}
		return "datos_admin"; /* vista por defecto */
	}
}