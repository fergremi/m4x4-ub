package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;

import maxaub.modelo.Lote;

@Stateless
public class LoteFacade extends BaseFacade {
	@SuppressWarnings("unchecked")
	public List<Lote> getLotes() {
        return getEntityManager().createQuery("select lote from Lote lote order by lote.idLote").getResultList();
	}
	
	public Lote getLote(int idLote) {
        return getEntityManager().find(Lote.class, idLote);
	}
	
	public void guardarLote(Lote lote) {
        getEntityManager().merge(lote); // merge and assign to the detached entity 
	}
    
	public void eliminarLote(Lote lote) {
        lote = getEntityManager().merge(lote); // merge and assign to the attached entity 
        getEntityManager().remove(lote); // remove the attached entity 
	}
}