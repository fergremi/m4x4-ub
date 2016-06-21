package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.EjemplarDAO;
import maxaub.modelo.Ejemplar;
import maxaub.modelo.Libro;

@Stateless
public class EjemplarJPA extends BaseJPA implements EjemplarDAO {
	@Override
	public List<Ejemplar> getEjemplares() {
		String sql = "SELECT e FROM Ejemplar AS e ORDER BY e.id";
		TypedQuery<Ejemplar> query = getEntityManager().createQuery(sql, Ejemplar.class);
		List<Ejemplar> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Ejemplar> getEjemplares(Libro libro) {
		String sql = "SELECT e FROM Ejemplar AS e LEFT JOIN FETCH e.libro WHERE e.libro.id = :id ORDER BY e.id";
		TypedQuery<Ejemplar> query = getEntityManager().createQuery(sql, Ejemplar.class);
		query.setParameter("id", libro.getId());
		List<Ejemplar> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Ejemplar> getEjemplaresAsignatura(String asignatura, int estado) {
		if (asignatura == "") {
			LOG.warn("No se ha completado la petición: getLibrosAsignatura -> campo vacío");
			return null;
		}
		if (comprobarComentariosSQL(asignatura)) {
    		LOG.warn("No se ha completado la petición: getLibrosAsignatura -> comentarios SQL bloqueados");
            return null;
        }
		
		String sql = "SELECT e "
				+ "FROM Ejemplar AS e LEFT JOIN FETCH e.libro "
				+ "WHERE e.libro.asignatura = :asignatura "
				+ "AND e.estado = :estado "
				+ "AND e.libro.activo = '1' "
				+ "AND e.id NOT IN (SELECT l.ejemplares.id FROM Lote AS l LEFT JOIN FETCH l.ejemplares) "
				+ "ORDER BY l.id ";
		TypedQuery<Ejemplar> query = getEntityManager().createQuery(sql, Ejemplar.class);
		query.setParameter("estado", estado);
		query.setParameter("asignatura", asignatura);
		List<Ejemplar> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Ejemplar> getEjemplaresEstadoBueno() {
		String sql = "SELECT e FROM Ejemplar AS e WHERE e.estado = 1 ORDER BY e.id";
		TypedQuery<Ejemplar> query = getEntityManager().createQuery(sql, Ejemplar.class);
		List<Ejemplar> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Ejemplar> getEjemplaresEstadoRegular() {
		String sql = "SELECT e FROM Ejemplar AS e WHERE e.estado = 2 ORDER BY e.id";
		TypedQuery<Ejemplar> query = getEntityManager().createQuery(sql, Ejemplar.class);
		List<Ejemplar> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Ejemplar> getEjemplaresEstadoMalo() {
		String sql = "SELECT e FROM Ejemplar AS e WHERE e.estado = 3 ORDER BY e.id";
		TypedQuery<Ejemplar> query = getEntityManager().createQuery(sql, Ejemplar.class);
		List<Ejemplar> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public Ejemplar getEjemplar(int idEjemplar) {
        return getEntityManager().find(Ejemplar.class, idEjemplar);
	}
	
	@Override
	public void crearEjemplar(Ejemplar ejemplar) {
        getEntityManager().persist(ejemplar);
        getEntityManager().flush();
	}
	
	@Override
	public void guardarEjemplar(Ejemplar ejemplar) {
        getEntityManager().merge(ejemplar);
        getEntityManager().flush();
	}
	
	@Override
	public void eliminarEjemplar(Ejemplar ejemplar) {
        getEntityManager().remove(ejemplar);
        getEntityManager().flush();
	}
}