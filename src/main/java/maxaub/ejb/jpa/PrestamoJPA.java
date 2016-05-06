package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.PrestamoDAO;
import maxaub.modelo.Prestamo;

@Stateless
public class PrestamoJPA extends BaseJPA implements PrestamoDAO {
	@Override
	public List<Prestamo> getPrestamos() {
		String sql = "SELECT p FROM Prestamo AS p ORDER BY p.id";
		TypedQuery<Prestamo> query = getEntityManager().createQuery(sql, Prestamo.class);
		List<Prestamo> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public Prestamo getPrestamo(int idPrestamo) {
        return getEntityManager().find(Prestamo.class, idPrestamo);
	}
	
	@Override
	public void guardarPrestamo(Prestamo prestamo) {
        getEntityManager().persist(prestamo);
        getEntityManager().flush();
	}
	
	@Override
	public void eliminarPrestamo(Prestamo prestamo) {
        getEntityManager().remove(prestamo);
        getEntityManager().flush();
	}
}