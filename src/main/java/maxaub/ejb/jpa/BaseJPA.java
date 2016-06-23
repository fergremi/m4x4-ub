package maxaub.ejb.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

/**
 *  Clase base que obtiene el entityManager
 */
public class BaseJPA {
	protected static final Logger LOG = Logger.getLogger(BaseJPA.class.getName());

	public static final String PERSISTENCE_UNIT_NAME = "openjpa";
	
	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    /**
	 * @return the entityManagerFactory
	 */
	public EntityManagerFactory createEntityManagerFactory() {
		return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = createEntityManagerFactory().createEntityManager();
		}
		return entityManager;
	}
	
	/**
	 * Función especial para las consultas sql ejecutadas con variables tipo String.
	 * Comprobación previa a la ejecución de consultas sql para prevenir sql injection.
	 * 
	 * @param campo
	 * @return isSQLComment
	 */
    protected boolean comprobarComentariosSQL(String campo) {
        if (campo.contains("--"))
            return true;
        else if (campo.contains("#"))
            return true;
        else if (campo.contains("/*"))
            return true;
        return false;
    }
}