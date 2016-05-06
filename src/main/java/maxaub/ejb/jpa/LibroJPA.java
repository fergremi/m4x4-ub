package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.LibroDAO;
import maxaub.modelo.Libro;

@Stateless
public class LibroJPA extends BaseJPA implements LibroDAO {
	@Override
	public List<Libro> getLibros() {
		String sql = "SELECT l FROM Libro AS l ORDER BY l.id";
		TypedQuery<Libro> query = getEntityManager().createQuery(sql, Libro.class);
		List<Libro> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Libro> getLibrosActivos() {
		String sql = "SELECT l FROM Libro AS l WHERE l.activo = '1' ORDER BY l.id";
		TypedQuery<Libro> query = getEntityManager().createQuery(sql, Libro.class);
		List<Libro> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public Libro getLibro(int idLibro) {
		if (idLibro <= 0) {
			log.info("No se ha completado la petición: getLibro -> identificador de libro no válido");
            return null;
        }
		return getEntityManager().find(Libro.class, idLibro);
	}
	
	@Override
	public Libro getLibroActivo(int idLibro) {
		if (idLibro <= 0) {
			log.warn("No se ha completado la petición: getLibroActivo -> índice no válido");
			return null;
		}
		
		String sql = "SELECT l FROM Libro AS l WHERE l.id ='" + idLibro + "'"
				+ " AND l.activo = '1'";
        TypedQuery<Libro> query = getEntityManager().createQuery(sql, Libro.class);
        try {
        	return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void guardarLibro(Libro libro) {
        getEntityManager().persist(libro);
        getEntityManager().flush();
	}
	
	@Override
	public void eliminarLibro(Libro libro) {
		getEntityManager().remove(libro);
		getEntityManager().flush();
	}
}