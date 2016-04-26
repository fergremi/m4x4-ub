package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import maxaub.model.Prestamo;

@Stateless
public class PrestamoFacade {
	@PersistenceContext(unitName = "openjpa")
    private EntityManager entityManager;

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    protected EntityManager getEntityManager() {
        return entityManager;
    }
	
	@SuppressWarnings("unchecked")
	public List<Prestamo> getPrestamos() {
        return getEntityManager().createQuery("select p from Prestamo p where order by p.idPrestamo").getResultList();
	}
	
	public Prestamo getPrestamo(int idPrestamo) {
        return getEntityManager().find(Prestamo.class, idPrestamo);
	}
	
	public void guardarPrestamo(Prestamo prestamo) {
        getEntityManager().merge(prestamo); // merge and assign to the detached entity 
	}
    
	public void eliminarPrestamo(Prestamo prestamo) {
        prestamo = getEntityManager().merge(prestamo); // merge and assign to the attached entity 
        getEntityManager().remove(prestamo); // remove the attached entity 
	}
}