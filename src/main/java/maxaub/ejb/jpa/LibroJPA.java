package maxaub.ejb.jpa;

import java.math.BigInteger;
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
	public Libro getLibro(BigInteger isbn) {
		if (isbn.compareTo(BigInteger.ZERO) <= 0) {
			LOG.warn("No se ha completado la petición: getLibro -> ISBN de libro no válido");
            return null;
        }
		
		String sql = "SELECT l FROM Libro AS l WHERE l.isbn = :isbn";
		TypedQuery<Libro> query = getEntityManager().createQuery(sql, Libro.class);
		query.setParameter("isbn", isbn);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Libro getLibroActivo(BigInteger isbn) {
		if (isbn.compareTo(BigInteger.ZERO) <= 0) {
			LOG.warn("No se ha completado la petición: getLibroActivo -> ISBN no válido");
			return null;
		}
		
		String sql = "SELECT l FROM Libro AS l WHERE l.isbn = :isbn"
				+ " AND l.activo = '1'";
        TypedQuery<Libro> query = getEntityManager().createQuery(sql, Libro.class);
        query.setParameter("isbn", isbn);
        try {
        	return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void crearLibro(Libro libro) {
        getEntityManager().persist(libro);
        getEntityManager().flush();
	}
	
	@Override
	public void guardarLibro(Libro libro) {
        getEntityManager().merge(libro);
        getEntityManager().flush();
	}
	
	@Override
	public void eliminarLibro(Libro libro) {
		getEntityManager().remove(libro);
		getEntityManager().flush();
	}
	
	@Override
	public List<String> getAsignaturasLibros() {
		String sql = "SELECT DISTINCT l.asignatura FROM Libro AS l ORDER BY l.id";
		TypedQuery<String> query = getEntityManager().createQuery(sql, String.class);
		List<String> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<String> getCursosLibros() {
		String sql = "SELECT DISTINCT l.curso FROM Libro AS l ORDER BY l.id";
		TypedQuery<String> query = getEntityManager().createQuery(sql, String.class);
		List<String> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<String> getEditorialesLibros() {
		String sql = "SELECT DISTINCT l.editorial FROM Libro AS l ORDER BY l.id";
		TypedQuery<String> query = getEntityManager().createQuery(sql, String.class);
		List<String> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<String> getIdiomasLibros() {
		String sql = "SELECT DISTINCT l.idioma FROM Libro AS l ORDER BY l.id";
		TypedQuery<String> query = getEntityManager().createQuery(sql, String.class);
		List<String> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
}