package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.LoteDAO;
import maxaub.modelo.Lote;

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
	public Lote getLote(int idLote) {
        return getEntityManager().find(Lote.class, idLote);
	}
	
	@Override
	public void guardarLote(Lote lote) {
        getEntityManager().persist(lote);
        getEntityManager().flush();
	}
	
	@Override
	public void eliminarLote(Lote lote) {
        getEntityManager().remove(lote);
        getEntityManager().flush();
	}
}