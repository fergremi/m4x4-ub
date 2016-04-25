package controller;


import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import ejb.jpa.SocioFacade;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(LoginController.class.getName());

    private String usuario;
    private String clave;

    private boolean showErrorLogin;

    public void setShowErrorLogin(Boolean showErrorAccesoUnico) {
        this.showErrorLogin = showErrorAccesoUnico;
    }

    public boolean isShowErrorLogin() {
        return showErrorLogin;
    }

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	@EJB
	private SocioFacade socioFacade;
	
	public void doLogin() {
		//TODO comprobar usuario y clave en BD
		if(socioFacade.comprobarSocio(usuario, clave)==null){
			log.info("va a ser q no");
		}
		else {
		log.info(socioFacade.comprobarSocio(usuario, clave).getNombre());
		}
	}
}