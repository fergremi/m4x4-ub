package maxaub.controller;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

public class BaseController {
    private static final Logger log = Logger.getLogger(BaseController.class);
    
	public final String PERSISTENCE_UNIT_NAME = "openjpa";
//	EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//	EntityManager em = emf.createEntityManager();
	
	protected EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return emf.createEntityManager();
    }
    
/*
    protected RegistroUsuario getRegistroUsuarioByUsuario(String usuario) {
        try {
            TypedQuery<RegistroUsuario> q = getEntityManager().createNamedQuery(
                    "usuarioInterno_FindByUsuario", RegistroUsuario.class);
            q.setParameter("usuario", usuario);
            List<RegistroUsuario> list = q.getResultList();
            if (list.size() == 1) {
                return list.get(0);
            }
        } catch (Exception e) {
            log.error("Error cargando usuario.", e);
        }
        return null;
    }
*/
    /**
     * Devuelve el idioma actual
     * 
     * @return
     */
    public static String getSessionLanguage() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
    }

    public static String findParameterValueByName(ActionEvent event, String name) {
        List<UIComponent> componentList = event.getComponent().getChildren();

        for (UIComponent comp : componentList) {
            if (comp instanceof UIParameter) {
                UIParameter param = (UIParameter) comp;
                if (name.equals(param.getName())) {
                    return param.getValue().toString();
                }
            }
        }
        return null;
    }

    public static UserTransaction getTransaction() {
        InitialContext ctx;
        try {
            ctx = new InitialContext();
            UserTransaction tx = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
            return tx;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rollbackTransaction(UserTransaction tx) {
        if (tx != null) {
            try {
                tx.rollback();
            } catch (Exception ex) {
                log.warn(ex);
            }
        }
    }

    public static String getRequestParameter(String key) {
        return (String) FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get(key);
    }

/*
    protected <E> List<E> getList(String query) {
        return getList(query, null);
    }
    @SuppressWarnings("unchecked")
    protected <E> List<E> getList(String query, Map<String, ?> parameters) {
        Query q = null;
        List<E> ris = null;
        try {
            q = getEntityManager().createQuery(query);

            for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
            ris = q.getResultList();
        } catch (Exception e) {
            log.error("Error con la query :" + query, e);
        }
        return ris;
    }

    @SuppressWarnings("unchecked")
    protected <T> T findByNamedQuery(String namedQuery, Map<String, ?> parameters) {
        Query q = null;
        try {
            q = getEntityManager().createNamedQuery(namedQuery);
            for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            log.error("Error con la named query :" + namedQuery, e);
        }
        return (T) q.getSingleResult();
    }
*/
}