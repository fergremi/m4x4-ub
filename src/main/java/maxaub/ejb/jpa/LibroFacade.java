package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import maxaub.model.Libro;

@Stateless
public class LibroFacade {
	@PersistenceContext(unitName = "openjpa")
    private EntityManager entityManager;

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    protected EntityManager getEntityManager() {
        return entityManager;
    }
	
	@SuppressWarnings("unchecked")
	public List<Libro> getLibros() {
        return getEntityManager().createQuery("select libro from Libro libro order by libro.idLibro").getResultList();
	}
	
	public Libro getLibro(int idLibro) {
		if (idLibro <= 0) {
            // Error: IDENTIFICADOR DE LIBRO NO VÁLIDO
			log.info("No se ha completado la petición: getLibro -> identificador de libro no válido");
            return null;
        }

        // Consulta: busca el libro
		return getEntityManager().find(Libro.class, idLibro);
	}
	
	public void guardarLibro(Libro libro) {
        getEntityManager().merge(libro);
	}
	
	public void eliminarLibro(Libro libro) {
		getEntityManager().remove(libro);
	}
}