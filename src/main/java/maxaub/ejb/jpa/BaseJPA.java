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
	protected final Logger log = Logger.getLogger(this.getClass().getName());

	public final String PERSISTENCE_UNIT_NAME = "openjpa";
	
	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    
    /**
	 * @return the entityManagerFactory
	 */
	public EntityManagerFactory getEntityManagerFactory() {
		return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return getEntityManagerFactory().createEntityManager();
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