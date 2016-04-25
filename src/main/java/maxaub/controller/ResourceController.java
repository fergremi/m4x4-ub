package maxaub.controller;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import maxaub.UserContext;


/**
 * Controlador a nivel aplicación que contiene y resuelve los recursos
 * idiomáticos, se expone como "managed bean" a nivel aplicación con el nombre
 * declarado {@link ResourceController#BEAN_NAME}
 * 
 */
@ManagedBean(name = ResourceController.BEAN_NAME, eager = true)
@ApplicationScoped
public class ResourceController implements Map<String, String>, Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * Acceso al log.
     */
    private static final Logger LOG = Logger.getLogger(ResourceController.class);
    
    /**
     * Nombre del controlador con el que se publica en el contexto de JSF.
     */
    public static final String BEAN_NAME = "res";

    public ResourceController() {
    }

    public static ResourceController getInstance() {
        return ControllerUtility.findController(ResourceController.class);
    }
    
    @Override
    public boolean containsKey(Object key) {
        if (key != null) {
            final Locale locale = getLocale();
            final boolean esLiteralSencillo = key instanceof String;
            final String claveParaTraducir = esLiteralSencillo ? key.toString()
                    : getPrefijoTraduccion(key);
            return existsTraduccion(locale, claveParaTraducir);
        }
        return false;
    }

    private boolean existsTraduccion(Locale locale, String claveParaTraducir) {
        try {
            innerGetTraduccion(locale, claveParaTraducir);
            return true;
        } catch (MissingResourceException ex) {
            return false;
        }
    }

    public static Locale getLocale() {
        // Parche, para pillar bien el idioma seleccionado
        try {
            if (UserContext.getInstance().getSelectedIdioma() != null) {
                String cod = UserContext.getInstance().getSelectedIdioma().getCod();
                FacesContext.getCurrentInstance().getViewRoot()
                        .setLocale(new Locale(cod, ""));
            }
        } catch (NullPointerException npe) {
            // No hay user context
        }
        Locale locale = FacesContext.getCurrentInstance().getViewRoot()
                .getLocale();

        if (locale == null) {
            locale = FacesContext.getCurrentInstance().getApplication()
                    .getDefaultLocale();
        }
        if (locale == null) {
            locale = new Locale("es", "");
        }
        return locale;
    }

    private String innerGetTraduccion(Locale locale, String claveParaTraducir)
            throws MissingResourceException {
        try {
            ResourceBundle resources = cache.find(locale.toString(), CACHE_NAME + getNameApp());
            if (resources != null) {
                return resources.getString(claveParaTraducir);
            }
        } catch (MissingResourceException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

    public String getTraduccion(Locale locale, String claveParaTraducir,
            boolean noEsLiteralSencillo) {
        try {
            return innerGetTraduccion(locale, claveParaTraducir);
        } catch (MissingResourceException ex) {
            return noEsLiteralSencillo ? getTraduccion(new Locale("no_trad"),
                    claveParaTraducir, false) : noTraduccion(claveParaTraducir);
        }
    }

    public static String noTraduccion(final String literal) {
        return "???" + literal + "???";
    }

    @Override
    public String get(Object key) {
        String value = null;

        if (key != null) {
            final Locale locale = getLocale();
            final boolean esLiteralSencillo = key instanceof String;
            final String claveParaTraducir = esLiteralSencillo ? key.toString()
                    : getPrefijoTraduccion(key);
            value = getTraduccion(locale, claveParaTraducir, !esLiteralSencillo);
        }
        return value;
    }
    
    public String get(String key, Locale locale) {
        return getTraduccion(locale, key, false);
    }

    public String get(String key, Locale locale, Locale defaultLocale) {
        try {
            return innerGetTraduccion(locale, key);
        } catch (MissingResourceException e) {
            try {
                return innerGetTraduccion(defaultLocale, key);
            } catch (MissingResourceException ex) {
                return noTraduccion(key);
            }
        }
    }

	@Override
	public void clear() {
	}

	@Override
	public boolean containsValue(Object arg0) {
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public String put(String arg0, String arg1) {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> arg0) {
	}

	@Override
	public String remove(Object arg0) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Collection<String> values() {
		return null;
	}
}
