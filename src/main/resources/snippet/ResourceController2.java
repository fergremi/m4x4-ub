/*
 * Copyright 03/05/2012 Proyecto IRIA - INE – Instituto Nacional de estadística – www.ine.es
 *
 * Licencia con arreglo a la EUPL, Versión 1.1 o –en cuanto
 * sean aprobadas por la Comisión Europea– versiones
 * posteriores de la EUPL (la «Licencia»);
 * Solo podrá usarse esta obra si se respeta la Licencia.
 * Puede obtenerse una copia de la Licencia en:
 *
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
 *
 * Salvo cuando lo exija la legislación aplicable o se acuerde
 * por escrito, el programa distribuido con arreglo a la
 * Licencia se distribuye «TAL CUAL»,
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas
 * ni implícitas.
 * Véase la Licencia en el idioma concreto que rige
 * los permisos y limitaciones que establece la Licencia.
 */

package es.ine.iria2.crud.bundle;

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

import es.ine.iria.cache.finder.CacheFinder;
import es.ine.iria.db.utility.AbstractResourceResolverLocator;
import es.ine.iria.db.utility.ResourceResolver;
import es.ine.iria.security.context.UserContext;
import es.ine.iria2.crud.ControllerUtility;
import es.ine.iria2.crud.CrudFacade;

/**
 * Controlador a nivel aplicación que contiene y resuelve los recursos
 * idiomáticos, se expone como "managed bean" a nivel aplicación con el nombre
 * declarado {@link ResourceController#BEAN_NAME}
 * 
 * @author josema
 */
@SuppressWarnings("serial")
@ManagedBean(name = ResourceController.BEAN_NAME, eager = true)
@ApplicationScoped
public class ResourceController implements Map<String, String>, Serializable {

    /**
     * Acceso al log.
     */
    private static final Logger LOG = Logger.getLogger(ResourceController.class);
    
    /**
     * Nombre del controlador con el que se publica en el contexto de JSF.
     */
    public static final String BEAN_NAME = "res";
    
    /**
     * Prefijo del identificador de la caché de traducciones.
     */
    public static final String CACHE_NAME = "traducciones";

    /**
     * Nombre de la aplicación para hacer los lookups de EJBs.
     */
    private static String ejbAppName;

    /**
     * Caché de traducciones.
     */
    protected transient CacheFinder<ResourceBundle> cache;
    
    /**
     * Nombre de la aplicación donde se publica.
     */
    private String nameApp;

    static {
        ejbAppName = getEjbAppName();
    }

    public ResourceController() {
        this(true);
    }

    /**
     * @param register
     *            Si es {@code true} se registrará en el
     *            {@link AbstractResourceResolverLocator} una instancia de esta
     *            clase.
     */
    protected ResourceController(boolean register) {
        cache = new ResourceCacheFinder(getNameApp());
        if (register) {
            final ResourceController instance = this;
            LOG.debug("AbstractResourceResolverLocator.registerResourceResolver "
                    + instance.getClass());
            AbstractResourceResolverLocator
                    .registerResourceResolver(new ResourceResolver() {

                        @Override
                        public String getResourceString(String resourceKey) {
                            return instance.get(resourceKey);
                        }

                        @Override
                        public boolean existsResource(String resourceKey) {
                            return instance.containsKey(resourceKey);
                        }
                    });
        }
    }

    public static ResourceController getInstance() {
        return ControllerUtility.findController(ResourceController.class);
    }

    /**
     * Declarar en el fichero web.xml un parámetro de contexto con nombre
     * 'nameAppKey' y valor el código de la aplicación (consultar tabla de BD
     * APLICACIONES).
     */
    public String getNameApp() {

        if (nameApp == null) {
            this.nameApp = FacesContext.getCurrentInstance()
                    .getExternalContext().getInitParameter("nameAppKey");
            if (this.nameApp == null) LOG
                    .error("Error, no se encuentra el parametro 'nameAppKey' definido en  el fichero web.xml");
        }

        return nameApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
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

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return null;
    }

    public static Locale getLocale() {
        // Parche, para pillar bien el idioma seleccionado
        try {
            if (UserContext.getInstance().getSelectedIdioma() != null) {
                String cod = UserContext.getInstance().getSelectedIdioma()
                        .getCod();
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
    
    private static class PrefijoTraduccionCacheBean implements Serializable {

        private String prefix;
        private Method method;
        
        public PrefijoTraduccionCacheBean() {
            
        }
        public PrefijoTraduccionCacheBean(String prefix, Method method) {
            super();
            this.prefix = prefix;
            this.method = method;
        }

        public String getPrefix() {
            return prefix;
        }
        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
        public Method getMethod() {
            return method;
        }
        public void setMethod(Method method) {
            this.method = method;
        }
        
    }
    
    private static Map<String, PrefijoTraduccionCacheBean> prefijoCache = new HashMap<String, PrefijoTraduccionCacheBean>();

    /**
     * Devuelve la clave para recuperar la traducción de datos de las tablas
     * <tabla>_IDIOMAS como cuestionarios_idiomas, encuestas_idiomas, periodos
     * etc..
     * 
     * @param key
     * @return
     */
    protected static String getPrefijoTraduccion(Object key) {
        
        try {
            String className = key.getClass().getName();
            PrefijoTraduccionCacheBean prefijo = null;
            synchronized (prefijoCache) {
                prefijo = prefijoCache.get(className);
            }
            if (prefijo != null) {
                return prefijo.getPrefix() + prefijo.getMethod().invoke(key, new Object[] {});
            }

            for (Method method : key.getClass().getDeclaredMethods()) {

                /*
                 * La clase tiene un campo id de tipo Long o String, sacar el
                 * prefix de la notación Column e invocar le getter del id para
                 * completar la clave p.e. para cuestionarios sería CUES_625
                 */
                if (method.getName().startsWith("get")
                        && method.getAnnotation(Id.class) != null) {
                    Column col = method.getAnnotation(Column.class);
                    prefijo = new PrefijoTraduccionCacheBean(col.name().substring(0, col.name().indexOf('_')) + "_", method);
                    synchronized (prefijoCache) {
                        prefijoCache.put(className, prefijo);
                    }
                    return prefijo.getPrefix() + prefijo.getMethod().invoke(key, new Object[] {});
                }

                /*
                 * la clase tiene un campo EmbeddedId, en este cado el prefix se
                 * saca de la anotación Column pero el valor se saca con el
                 * método toString de la clase que hay que sobrescribir (ojo, no
                 * se pueden recorrer los getters porqué no tenemos garantías
                 * sobre el orden en el que los leeremos y la clave podría
                 * resultar incorrecta), p.e. para Campos sería
                 * CAMP_UM_PERSONAS_UMPE_CIF
                 */
                if (method.getName().startsWith("get")
                        && method.getAnnotation(EmbeddedId.class) != null) {
                    Object embeddedPK = method.invoke(key, new Object[] {});
                    for (Method methodEmb : embeddedPK.getClass()
                            .getDeclaredMethods()) {
                        if (methodEmb.getName().startsWith("get")) {
                            Column col = methodEmb.getAnnotation(Column.class);
                            prefijo = new PrefijoTraduccionCacheBean(col.name().substring(0, col.name().indexOf('_')) + "_", method);
                            synchronized (prefijoCache) {
                                prefijoCache.put(className, prefijo);
                            }
                            return prefijo.getPrefix() + prefijo.getMethod().invoke(key, new Object[] {});
                        }
                    }
                }
               
            }
            // si no podemos averiguar la clave con las anotaciones pues...
            // Huston tenemos un problema
            LOG.error("No se ha podido averiguar la clave para la traduccion");

        } catch (IllegalAccessException e) {
            LOG.error(e);
        } catch (InvocationTargetException e) {
            LOG.error(e);
        }
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
    public String put(String key, String value) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
    }

    @Override
    public String remove(Object key) {
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

    @Override
    public void clear() {
    }
    
    public static CrudFacade getCrudFacade() {
        return getEjb();
    }

    private static CrudFacade getEjb() {
        String lookupName = null;
        if (StringUtils.isNotBlank(ejbAppName)) {
            lookupName = "java:global/" + ejbAppName + "/" + CrudFacade.class.getSimpleName();
        } else {
            lookupName = "java:module/" + CrudFacade.class.getSimpleName();
        }
        try {
            InitialContext initCtx = new InitialContext();
            return (CrudFacade) javax.rmi.PortableRemoteObject.narrow(initCtx.lookup(lookupName), CrudFacade.class);
        } catch (NamingException n) {
            LOG.error("No se ha encontrado el EJB: " + lookupName);
        } catch (Exception ex) {
            LOG.error("Error resolviendo el EJB: " + lookupName, ex);
        }
        return null;
    }
    
    private static String getEjbAppName() {
        try {
            return (String) new InitialContext().lookup("java:app/AppName");
        } catch (NamingException e) {
            LOG.error("No se ha encontrado el nombre de la aplicacion java:app/AppName");
        }
        return null;
    }
}
