 * Copyright 24/04/2012 Proyecto IRIA - Instituto Nacional de Estadística (INE-ESPAÑA)- www.ine.es

package es.ine.iria.security.context;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import es.ine.iria.db.Cuestionario;
import es.ine.iria.db.Encuesta;
import es.ine.iria.db.Idioma;
import es.ine.iria.db.MetodoRecogida;
import es.ine.iria.db.ParametroSistema;
import es.ine.iria.db.Periodo;
import es.ine.iria.db.Rol;
import es.ine.iria.db.Ume;
import es.ine.iria.db.UnidadRecogida;
import es.ine.iria.db.UsuarioInterno;
import es.ine.iria.db.UsuarioInternoEncuesta;
import es.ine.iria.db.UsuarioInternoMetodoRecogidaEncuesta;
import es.ine.iria.runtime.jsf.parameters.ParametersConstants;
import es.ine.iria.security.IriaSecurityException;
import es.ine.iria.security.SecurityUtility;
import es.ine.iria.security.context.jaxb.AdminContext;

public class UserContext implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String nameApp;

    private static final Logger LOG = Logger.getLogger(UserContext.class);

    private UsuarioInterno usuarioInterno;

    private Cuestionario cuestionario;

    private Ume ume;

    private Idioma selectedIdioma;
    protected List<SelectItem> idiomas;
    
    private Map<ActionKey,Boolean> accionesCache;
    

     * Filtro que contiene el contexto seleccionado
    FilterContext filterContext = new FilterContext();
     * Filtro que contiene el contexto de Seguridad del usuario
    SecurityContext securityContext = new SecurityContext();

     * Variable que nos indica si el contexto de usuario existe y es válido
    private boolean validateContext = false;

     * Número de filas en las tablas
    private Integer rows;

     * Variable que nos indica el timeZone de la máquina
    private TimeZone timeZone = TimeZone.getDefault();

     * Nos indica que venimos desde el designer, por tanto la identificación del
    private boolean vengoServlet = false;

    public static final String BEAN_NAME = "userContext";

    public static UserContext getInstance() {
        return SecurityUtility.findController(UserContext.class, BEAN_NAME);
    }

    public Locale getLocale() {
        Locale locale = null;
        if (FacesContext.getCurrentInstance() != null
                && FacesContext.getCurrentInstance().getViewRoot() != null) {
            locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            if (locale == null) {
                FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
            }
        }
        return locale;
    }

    public UsuarioInterno getUsuarioInterno() {
        return usuarioInterno;
    }

    public void setUsuario(UsuarioInterno usuarioInterno) throws Exception {
        this.usuarioInterno = usuarioInterno;
        // Inicializamos la encuesta y proyecto
        this.filterContext.setUsuarioInterno(usuarioInterno);
        this.securityContext.setUsuario(usuarioInterno);

        this.filterContext.setEncuesta(null);
        this.securityContext.setEncuesta(null);

        // Comprobamos el contexto
        if (usuarioInterno.getContexto() != null) {
            // Validar que el contexto sea válido con respecto al sistema de
            // seguridad
            try {
                AdminContext adminContext = JAXBContextUtility.loadAdminContext(usuarioInterno
                        .getContexto());
                // Validar y transferir a variables miembro
                setValidateContext(validate(adminContext));
                // Si el contexto es valido cargo el contexto
                if (validateContext) {
                    loadContext(adminContext);
                }
            } catch (Exception ex) {
                LOG.error("Error en setUsuario", ex);
                setValidateContext(false);

            }
        } else {
            // Si no tiene contexto entonces lo invalidamos para que entre
            setValidateContext(false);
        }

        // Cargamos por defecto el idioma Español...
        cargarIdiomaPorDefecto();

    }

     * @return the filterContext
    public FilterContext getFilterContext() {
        return filterContext;
    }

     * @param filterContext
    public void setFilterContext(FilterContext filterContext) {
        this.filterContext = filterContext;
    }

     * @return the securityContext
    public SecurityContext getSecurityContext() {
        return securityContext;
    }

     * @param securityContext
    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

     * @param adminContext
    protected void loadContext(AdminContext adminContext) {

        LOG.debug("loadContext()");
        if (adminContext.getEncuesta() != null) {
            TypedQuery<Encuesta> query = getEntityManager().createNamedQuery(
                    "encuesta_FindByIdWithProyecto", Encuesta.class);
            query.setParameter("encuId", Long.parseLong(adminContext.getEncuesta().getValue()));
            List<Encuesta> encus = query.getResultList();
            if (!encus.isEmpty()) {
                /*
                 * Cargo la encuesta tanto en el filtro como en el contexto de
                 * seguridad.
                 */
                this.filterContext.setEncuesta(encus.get(0));
            }
        }

        if (adminContext.getPeriodo() != null) {
            TypedQuery<Periodo> query = getEntityManager().createNamedQuery("periodo_FindByPeriodoAndEncuesta", Periodo.class);
            query.setParameter("encuId", Long.parseLong(adminContext.getEncuesta().getValue()));
            query.setParameter("periId", Long.parseLong(adminContext.getPeriodo().getValue()));
            List<Periodo> periodos = query.getResultList();
            if (!periodos.isEmpty()) {
                // Cargo el periodo únicamente en el filtro
                this.filterContext.setPeriodo(periodos.get(0));
            }
        }

        if (adminContext.getCuestionario() != null) {
            Cuestionario cuestionario = getEntityManager().find(Cuestionario.class,
                    Long.parseLong(adminContext.getCuestionario().getValue()));
            this.filterContext.setCuestionario(cuestionario);
        }

        if (adminContext.getMetodoRecogida() != null) {
            TypedQuery<MetodoRecogida> query = getEntityManager().createNamedQuery("metodosRecogida_FindById", MetodoRecogida.class);
            query.setParameter("metodoId", adminContext.getMetodoRecogida().getValue());
            List<MetodoRecogida> mrs = query.getResultList();
            if (!mrs.isEmpty()) {
                // Cargo el periodo únicamente en el filtro
                this.filterContext.setMetodoRecogida(mrs.get(0));
            }
        }

        if (adminContext.getUnidadRecogida() != null) {
            TypedQuery<UnidadRecogida> query = getEntityManager().createNamedQuery("unidadRecogida_FindById", UnidadRecogida.class);
            query.setParameter("id", Long.parseLong(adminContext.getUnidadRecogida().getValue()));
            List<UnidadRecogida> urs = query.getResultList();
            if (!urs.isEmpty()) {
                // Cargo el periodo únicamente en el filtro
                this.filterContext.setUnidadRecogida(urs.get(0));
            }
        }
    }

     * @param adminContext
    @SuppressWarnings("unchecked")
    public boolean validate(AdminContext adminContext) {
        Query query = null;

        if (usuarioInterno != null) {

            // Si el usuario no es correcto en sus tablas por su rol, entonces
            // false
            if (!comprobacionNivelRol()) {
                return false;
            }

            // Compruebo que en su contexto cargado tenga como minimo lo
            // necesario para entrar
            Rol rol = getUsuarioInterno().getRol();

            if (rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_1)) {

                if (adminContext.getUnidadRecogida() == null) {
                    return false;
                }
            }

            if (rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_2)) {

                if (adminContext.getEncuesta() == null) {
                    return false;
                }
            }

            if (rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_3)) {

                if (adminContext.getEncuesta() == null) {
                    return false;
                }
                if (adminContext.getUnidadRecogida() == null) {
                    return false;
                }
            }

            // Si soy nivel 1 o 3, compruebo la unidad de recogida que la
            // tenga el usuario
            if (rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_1)
                    || rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_3)) {
                // Si tengo cargado una unidad de recogida
                if (adminContext.getUnidadRecogida() != null) {

                    // Compruebo si es valida
                    Long urecId = Long.valueOf(adminContext.getUnidadRecogida().getValue());
                    query = getEntityManager().createNamedQuery(
                            "usuarioInternoUnidadRecogida_FindAllUnidadRecogidaByUser");
                    query.setParameter("usuaId", (usuarioInterno.getId()));
                    List<UnidadRecogida> lista = query.getResultList();
                    boolean encontrado = false;
                    for (UnidadRecogida unidadRecogida : lista) {
                        if (unidadRecogida.getId().equals(urecId)) {
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        return false;
                    }
                }
            }

            // Si no soy el nivel 0, compruebo los datos que cargo sean
            // correctos
            if (!rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_0)) {
                // Tengo en contexto la encuesta
                if (adminContext.getEncuesta() != null) {
                    // Compruebo la encuesta, dependiendo del
                    // nivel del rol miro si esta en la tabla indicada.

                    if (usuarioInterno.getRol().getNivelRol().getCod()
                            .equals(ParametersConstants.NIVEL_ROL_0)) {

                        // Mira si tengo la encuesta activa y su proyecto es
                        // activo
                        query = getEntityManager().createNamedQuery("encuesta_FindById");
                        query.setParameter("id",
                                Long.parseLong(adminContext.getEncuesta().getValue()));

                        // Si no tengo valores para usuario y encuesta entonces
                        // ha
                        // cambiado el contexto
                        if (query.getResultList().isEmpty()) {
                            return false;
                        }
                    }

                    if (usuarioInterno.getRol().getNivelRol().getCod()
                            .equals(ParametersConstants.NIVEL_ROL_1)) {
                        query = getEntityManager().createNamedQuery(
                                "EncuestaUsuarioNivel1_FindByUsuarioEncuesta");
                        query.setParameter("usuaId", usuarioInterno.getId());
                        query.setParameter("encuId",
                                Long.parseLong(adminContext.getEncuesta().getValue()));
                    }

                    if (usuarioInterno.getRol().getNivelRol().getCod()
                            .equals(ParametersConstants.NIVEL_ROL_2)) {

                        query = getEntityManager().createNamedQuery(
                                "UsuarioInternoEncuesta_FindByUsuarioAndEncuesta");
                        query.setParameter("usuaId", usuarioInterno.getId());
                        query.setParameter("encuId",
                                Long.parseLong(adminContext.getEncuesta().getValue()));

                        // Si no tengo valores para usuario y encuesta entonces
                        // ha
                        // cambiado el contexto
                        if (query.getResultList().isEmpty()) {
                            return false;
                        }

                    }

                    if (usuarioInterno.getRol().getNivelRol().getCod()
                            .equals(ParametersConstants.NIVEL_ROL_3)) {

                        // Si soy nivel 3, tengo que tener un Metodo de Recogida
                        // Asociado
                        if (adminContext.getMetodoRecogida() == null) {
                            return false;
                        }
                        query = getEntityManager().createNamedQuery(
                                "UsuarioInternoMetodoRecogidaEncuesta_FindByUsuarioEncuestaMetodo");
                        query.setParameter("usuaId", usuarioInterno.getId());
                        query.setParameter("encuId",
                                Long.parseLong(adminContext.getEncuesta().getValue()));
                        query.setParameter("codMetodo", adminContext.getMetodoRecogida().getValue());

                        // Si no tengo valores para usuario y encuesta entonces
                        // ha
                        // cambiado el contexto
                        if (query.getResultList().isEmpty()) {
                            return false;
                        }

                    }

                    // Tengo en contexto el periodo
                    if (adminContext.getPeriodo() != null) {
                        // Compruebo el periodo
                        query = getEntityManager().createNamedQuery(
                                "periodo_FindByPeriodoAndEncuesta");
                        query.setParameter("periId",
                                Long.parseLong(adminContext.getPeriodo().getValue()));
                        query.setParameter("encuId",
                                Long.parseLong(adminContext.getEncuesta().getValue()));

                        // Si no tengo valores para usuario y encuesta entonces
                        // ha
                        // cambiado el contexto
                        if (query.getResultList().isEmpty()) {
                            return false;
                        }

                    }

                    // Tengo contexto el metodo de recogida
                    if (adminContext.getMetodoRecogida() != null) {
                        // Compruebo el metodo de Recogida
                        if (usuarioInterno.getRol().getNivelRol().getCod()
                                .equals(ParametersConstants.NIVEL_ROL_3)) {

                            query = getEntityManager()
                                    .createNamedQuery(
                                            "EncuestaMetodoRecogidaUsuarioNivel3_FindByUsuarioEncuestaMetodo");
                            query.setParameter("usuaId", (usuarioInterno.getId()));
                            query.setParameter("encuId",
                                    Long.parseLong(adminContext.getEncuesta().getValue()));
                            query.setParameter("mereCod", adminContext.getMetodoRecogida()
                                    .getValue());
                            if (query.getResultList().isEmpty()) {
                                return false;
                            }

                        }
                    }
                }
            }
            // Si he comprobado todos los filtros devuelto true

            return true;

        } else {
            // Si no hay usuario, no hay contexto
            return false;
        }

    }

    public String getRequestedPath() {
        // Que siempre vaya a inicio.
        return "/";
    }

    public void setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
    }

    public Cuestionario getCuestionario() {
        return cuestionario;
    }

    public void setUme(Ume ume) {
        this.ume = ume;
    }

    public Ume getUme() {
        return ume;
    }

    public String goLogout() {
        invalidateSession("LOG_OUT");
        return ParametersConstants.SUCCESS;
    }

    public String goChangeUser() {

        return ParametersConstants.SUCCESS;

    }
    
    public static void invalidateSession(final String exitMode) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                .getSession(false);
        if (session != null) {
            session.setAttribute("SESSION_EXIT_MODE", exitMode);
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public String goContext() {
        // Cargar los datos del usuario al ContextController
        ContextController contextController = ContextController.getInstance();
        contextController.setEncuesta(getFilterContext().getEncuesta());
        contextController.setPeriodo(getFilterContext().getPeriodo());
        contextController.setMetodoRecogida(getFilterContext().getMetodoRecogida());
        contextController.setUnidadRecogida(getFilterContext().getUnidadRecogida());
        // No vengo del Login, sirve para mostrar el boton cancelar
        contextController.setLogin(false);
        return ParametersConstants.SUCCESS;
    }

     * @param validateContext
    public void setValidateContext(boolean validateContext) {

        this.validateContext = validateContext;
    }

     * @return the validateContext
    public boolean isValidateContext() {
        return validateContext;
    }

     * Obtiene la lista de idiomas disponibles en la aplicación.
    public List<SelectItem> getIdiomas() {
        if (this.idiomas == null) {
            Query query = getEntityManager().createNamedQuery("aplicacionIdioma_FindByAplicacion");
            query.setParameter("apliCod", getNameApp());
            try {
                this.idiomas = createSelectItems(query.getResultList(), "descripcion");
            } catch (Exception ex) {
                LOG.error("Error al obtener los idiomas", ex);
                this.idiomas = new ArrayList<SelectItem>();
            }
        }
        return this.idiomas;
    }

    public void setIdiomas(List<SelectItem> idiomas) {
        this.idiomas = idiomas;
    }

    protected List<SelectItem> createSelectItems(String queryName, String labelProperty)
            throws Exception {
        Query query = getEntityManager().createNamedQuery(queryName);
        return createSelectItems(query.getResultList(), null, labelProperty);
    }

    protected List<SelectItem> createSelectItems(List<?> values, String labelProperty)
            throws Exception {
        return createSelectItems(values, null, labelProperty);
    }

    protected List<SelectItem> createSelectItems(List<?> list, String valueProperty,
            String labelProperty) throws Exception {
        Method valueGetMethod = null;
        Method labelGetMethod = null;
        ArrayList<SelectItem> ret = new ArrayList<SelectItem>();

        for (Object obj : list) {
            Object value = null;
            if (valueGetMethod == null && valueProperty != null) {
                valueGetMethod = obj.getClass().getMethod(
                        "get" + valueProperty.substring(0, 1).toUpperCase()
                                + valueProperty.substring(1), (Class[]) null);
            } else {
                value = obj;
            }
            if (labelGetMethod == null) {
                labelGetMethod = obj.getClass().getMethod(
                        "get" + labelProperty.substring(0, 1).toUpperCase()
                                + labelProperty.substring(1), (Class[]) null);
            }
            if (value == null && valueGetMethod != null) {
                value = valueGetMethod.invoke(obj, (Object[]) null);
            }
            Object label = labelGetMethod.invoke(obj, (Object[]) null);
            String strLabel = null;
            if (label != null) {
                if (label instanceof String) {
                    strLabel = (String) label;
                } else {
                    strLabel = label.toString();
                }
            } else {
                strLabel = "";
            }
            ret.add(new SelectItem(value, strLabel));
        }
        return ret;
    }

     * @param selectedIdioma
    public void setSelectedIdioma(Idioma selectedIdioma) {
        this.selectedIdioma = selectedIdioma;
    }

     * @return the selectedIdioma
    public Idioma getSelectedIdioma() {
        return selectedIdioma;
    }

     * Metodo que cambia el idioma
    public String doChangeLanguage() {

        if (getSelectedIdioma() != null) {
            FacesContext.getCurrentInstance().getViewRoot()
                    .setLocale(new Locale(getSelectedIdioma().getCod(), ""));
        }
        return ParametersConstants.SUCCESS;

    }

    public void cargarIdiomaPorDefecto() throws Exception {

        for (SelectItem select : this.getIdiomas()) {
            Idioma idioma = (Idioma) select.getValue();
            if (idioma.getCod().equals("es")) {
                selectedIdioma = idioma;
            }
        }

    }

     * @param timeZone
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

     * @return the timeZone
    public TimeZone getTimeZone() {

        return timeZone;
    }

    public boolean comprobacionNivelRol() {

        Rol rol = getUsuarioInterno().getRol();
        UsuarioInterno usuarioInterno = getUsuarioInterno();

        if (rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_0)
                || rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_1)) {

            // Compruebo que el usuario existe en Usuarios_Internos

            return existUsuariosInternos(usuarioInterno)
                    && !existUsuariosInternosEncuestas(usuarioInterno)
                    && !existUsuariosInternosEncuestasMetodosRecogida(usuarioInterno);

        }

        if (rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_2)) {

            // Compruebo que el usuario existe en Usuarios_Internos_Enc

            return existUsuariosInternos(usuarioInterno)
                    && existUsuariosInternosEncuestas(usuarioInterno)
                    && !existUsuariosInternosEncuestasMetodosRecogida(usuarioInterno);

        }

        if (rol.getNivelRol().getCod().equals(ParametersConstants.NIVEL_ROL_3)) {

            // Compruebo que el usuario existe en Usui_Mere_Enc

            return existUsuariosInternos(usuarioInterno)
                    && existUsuariosInternosEncuestas(usuarioInterno)
                    && existUsuariosInternosEncuestasMetodosRecogida(usuarioInterno);

        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean existUsuariosInternos(UsuarioInterno usuarioInterno) {
        Query query = getEntityManager().createNamedQuery("usuarioInterno_FindById");
        query.setParameter("id", usuarioInterno.getId());
        List<UsuarioInterno> lista = query.getResultList();
        if (lista.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    @SuppressWarnings("unchecked")
    public boolean existUsuariosInternosEncuestas(UsuarioInterno usuarioInterno) {
        Query query = getEntityManager().createNamedQuery("UsuarioInternoEncuesta_FindByUsuario");
        query.setParameter("usuaId", usuarioInterno.getId());
        List<UsuarioInternoEncuesta> lista = query.getResultList();
        if (lista.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean existUsuariosInternosEncuestasMetodosRecogida(UsuarioInterno usuarioInterno) {
        Query query = getEntityManager().createNamedQuery(
                "UsuarioInternoMetodoRecogidaEncuesta_FindByUsuario");
        query.setParameter("usuaId", usuarioInterno.getId());
        @SuppressWarnings("unchecked")
        List<UsuarioInternoMetodoRecogidaEncuesta> lista = query.getResultList();
        if (lista.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

     * Método que se encarga de almacenar el contexto del usuario en la BD.
    public void saveFilterContextToDB() throws IriaSecurityException {

        // Creamos fichero xml mediante JAXB
        AdminContext adminContext = new AdminContext();

        if (getFilterContext().getEncuesta() != null) {
            es.ine.iria.security.context.jaxb.Encuesta enc = new es.ine.iria.security.context.jaxb.Encuesta();
            enc.setValue(String.valueOf(getFilterContext().getEncuesta().getId()));
            adminContext.setEncuesta(enc);
        }

        if (getFilterContext().getPeriodo() != null) {
            es.ine.iria.security.context.jaxb.Periodo per = new es.ine.iria.security.context.jaxb.Periodo();
            per.setValue(String.valueOf(getFilterContext().getPeriodo().getId().getId()));
            adminContext.setPeriodo(per);
        }

        if (getFilterContext().getMetodoRecogida() != null) {
            es.ine.iria.security.context.jaxb.MetodoRecogida met = new es.ine.iria.security.context.jaxb.MetodoRecogida();
            met.setValue(getFilterContext().getMetodoRecogida().getCod());
            adminContext.setMetodoRecogida(met);
        }

        if (getFilterContext().getUnidadRecogida() != null) {
            es.ine.iria.security.context.jaxb.UnidadRecogida ur = new es.ine.iria.security.context.jaxb.UnidadRecogida();
            ur.setValue(String.valueOf(getFilterContext().getUnidadRecogida().getId()));
            adminContext.setUnidadRecogida(ur);
        }

        UserTransaction tx = null;
        try {
            String st = JAXBContextUtility.saveAdminContext(adminContext);

            // Almacenarlo en la base de datos....
            tx = SecurityUtility.getTransaction();
            tx.begin();
            EntityManager em = getEntityManager();

            UsuarioInterno user = getUsuarioInterno();
            user.setContexto(st);
            em.merge(user);

            tx.commit();

        } catch (Exception e) {
            SecurityUtility.rollbackTransaction(tx);
            LOG.error(e);
            throw new IriaSecurityException("errorActualizando");
        }

    }

    public void setVengoServlet(boolean vengoServlet) {
        this.vengoServlet = vengoServlet;
    }

    public boolean isVengoServlet() {
        return vengoServlet;
    }

    protected EntityManager getEntityManager() {
        return SecurityUtility.getEntityManager();
    }

    public Integer getRows() {

        if (rows == null) {
            // No pueden haber más campos que los definidos en parametros
            // sistema...
            try {
                ParametroSistema param = es.ine.iria.security.parameters.ParametersConstants
                        .getParametro(es.ine.iria.security.parameters.ParametersConstants.PARAMETRO_MaxNumFilasEnListas);
                rows = Integer.parseInt(param.getValor());

            } catch (Exception ex) {
                LOG.error(ex);
                rows = 10;
            }
        }
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

     * Declarar en el fichero web.xml un parámetro de contexto con nombre
    public String getNameApp() {

        if (nameApp == null) {
            nameApp = FacesContext.getCurrentInstance().getExternalContext()
                    .getInitParameter("nameAppKey");
            if (nameApp == null) LOG
                    .error("Error, no se encuentra el parametro 'nameAppKey' definido en  el fichero web.xml");
        }

        return nameApp;
    }
    
    public boolean permisoByAccionFichaUme(ActionKey actionKey)
    {
    	Boolean ret = getAccionesCache().get(actionKey);
    	if(ret == null)
    	{
    		ret = SecurityUtility.permisoByAccionFichaUme(actionKey.getAcciName());
    		getAccionesCache().put(actionKey, ret);
    	}
    	return ret;
    }

	public Map<ActionKey, Boolean> getAccionesCache() 
	{
		if(accionesCache == null)
		{
			accionesCache = new HashMap<ActionKey, Boolean>();
		}
		return accionesCache;
	}   
	
	@SuppressWarnings("serial")
    public class ActionKey implements Serializable
	{
		private String mereCod;
		private Long encuId;
		private String acciName;
		
		public ActionKey(String mereCod, Long encuId, String acciName) 
		{
			super();
			this.mereCod = mereCod;
			this.encuId = encuId;
			this.acciName = acciName;
		}
		
		public String getMereCod() 
		{
			return mereCod;
		}

		public void setMereCod(String mereCod) 
		{
			this.mereCod = mereCod;
		}

		public Long getEncuId() 
		{
			return encuId;
		}

		public void setEncuId(Long encuId) 
		{
			this.encuId = encuId;
		}

		public String getAcciName() 
		{
			return acciName;
		}

		public void setAcciName(String acciName)
		{
			this.acciName = acciName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((getAcciName()  == null) ? 0 : getAcciName() .hashCode());
			result = prime * result
					+ ((getEncuId() == null) ? 0 : getEncuId().hashCode());
			result = prime * result
					+ ((getMereCod() == null) ? 0 : getMereCod().hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ActionKey other = (ActionKey) obj;
			if (getAcciName()  == null) {
				if (other.getAcciName()  != null)
					return false;
			} else if (!getAcciName() .equals(other.getAcciName() ))
				return false;
			if (getEncuId() == null) {
				if (other.getEncuId() != null)
					return false;
			} else if (!getEncuId().equals(other.getEncuId()))
				return false;
			if (getMereCod() == null) {
				if (other.getMereCod() != null)
					return false;
			} else if (!getMereCod().equals(other.getMereCod()))
				return false;
			return true;
		}
	}
	
     * Recuperar la entidad Encuesta con el proyecto correspondiente.
    public static Encuesta loadEncuesta(Encuesta e) {
        if (e != null && e.getProyecto() == null) {
            TypedQuery<Encuesta> query = SecurityUtility.getEntityManager().createNamedQuery(
                    "encuesta_FindByIdWithProyecto", Encuesta.class);
            query.setParameter("encuId", e.getId());
            List<Encuesta> list = query.getResultList();
            if (!list.isEmpty()) {
                return list.get(0);
            }
        }
        return e;
    }

     * Flag para deshabilitar la optimización del c:if.
    private boolean disableOptimize = false;
    private Boolean disableOptimizeParam;

    public boolean isDisableOptimize() {
        if (disableOptimizeParam == null) {
            disableOptimizeParam = Boolean
                    .parseBoolean(es.ine.iria.security.parameters.ParametersConstants.getAsString(
                            "DISABLE_C_IF_OPTIMIZATION", "false"));
            disableOptimize = disableOptimizeParam;
        }
        return disableOptimize;
    }

    public void setDisableOptimize(boolean disableOptimize) {
        this.disableOptimize = disableOptimize;
    }
}
