package maxaub.controlador;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import maxaub.ejb.interfaz.LiteralDAO;

/**
 * Controlador a nivel aplicación que contiene y resuelve los recursos idiomáticos
 * 
 */
@ManagedBean
@ApplicationScoped
public class ResourceController extends ResourceBundle {
    private static final Logger log = Logger.getLogger(ResourceController.class);
    
    @EJB
    private static LiteralDAO literalDAO;
    
    private Locale idioma;
    
    //private List<Map> idiomasLiterales;
    private Map<String, String> literales;

    public ResourceController() {
    	//idioma = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    	idioma = new Locale("es", "Español");
    	try {
			literales = literalDAO.getLiteralesIdioma(idioma);
			log.info("MAP literales: " + literales.toString());
    	} catch(Exception e) {
    		log.info("~~~~~~~~~~~~~~~~~~~~~~~~");
    	}
//    	setParent(ResourceBundle.getBundle(
//    			ResourceController.class.getName(),
//    			idioma,
//    			new DBControl()));
    }

    protected ResourceController(Map<String, String> literales) {
        this.literales = literales;
    }

    @Override
    protected Object handleGetObject(String key) {
    	log.info("idiomaCod: " + idioma.getLanguage() + " -> literal: " + key);
		try {
			return literales != null ? literales.get(key) : parent.getObject(key);
		}
    	catch (Exception e) {
			return "¿" + key + "?";
		}
    }

    @Override
    public Enumeration<String> getKeys() {
    	log.info("getKeys");
        return literales != null ? Collections.enumeration(literales.keySet()) : parent.getKeys();
    }

    protected static class DBControl extends Control {
        @Override
        public ResourceBundle newBundle
            (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            return new ResourceController(literalDAO.getLiteralesIdioma(locale));
        }
    }
}