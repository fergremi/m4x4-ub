package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.LoteDAO;
import maxaub.modelo.Lote;
import maxaub.modelo.Socio;

@Stateless
public class LoteJPA extends BaseJPA implements LoteDAO {
	@Override
	public List<Lote> getLotes() {
		String sql = "SELECT l FROM Lote AS l ORDER BY l.id";
		TypedQuery<Lote> query = getEntityManager().createQuery(sql, Lote.class);
		List<Lote> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Lote> getLotes(Socio socio) {
		String sql = "SELECT DISTINCT l FROM "
				+ "Alumno AS a LEFT JOIN FETCH a.socio, "
				+ "Prestamo AS p LEFT JOIN FETCH p.alumno LEFT JOIN FETCH p.lote, "
				+ "Lote AS l LEFT JOIN FETCH l.ejemplares "
				+ "WHERE a.socio.id = :id "
				+ "AND p.alumno.id = a.id "
				+ "AND l.cod = p.lote.cod ";
		
		TypedQuery<Lote> query = getEntityManager().createQuery(sql, Lote.class);
		query.setParameter("id", socio.getId());
		List<Lote> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public Lote getLote(int idLote) {
        return getEntityManager().find(Lote.class, idLote);
	}
	
	@Override
	public void crearLote(Lote lote) {
        getEntityManager().persist(lote);
        getEntityManager().flush();
	}
	
	@Override
	public void guardarLote(Lote lote) {
        getEntityManager().merge(lote);
        getEntityManager().flush();
	}
	
	@Override
	public void eliminarLote(Lote lote) {
        getEntityManager().remove(lote);
        getEntityManager().flush();
	}
	
	@Override
	public Long getNextCodLote() {
		String sql = "SELECT MAX(l.cod) FROM Lote AS l ORDER BY l.id";
		TypedQuery<Long> query = getEntityManager().createQuery(sql, Long.class);
		Long res = query.getSingleResult();
		if (res == null) {
			return (long) 1;
		}
		return res++;
	}
}