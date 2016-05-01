package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.IdiomaDAO;
import maxaub.modelo.Idioma;

@Stateless
public class IdiomaJPA extends BaseJPA implements IdiomaDAO {
	@Override
	public List<Idioma> getIdiomas() {
		String sql = "SELECT i FROM Idioma i ORDER BY i.codIdioma";
		TypedQuery<Idioma> query = getEntityManager().createQuery(sql, Idioma.class);
		List<Idioma> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Idioma> getIdiomasActivos() {
		String sql = "SELECT i FROM Idioma i WHERE i.activo = '1' ORDER BY i.codIdioma";
		TypedQuery<Idioma> query = getEntityManager().createQuery(sql, Idioma.class);
		List<Idioma> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public Idioma getIdioma(String codIdioma) {
		if (codIdioma == null) {
			log.info("No se ha completado la petición: getIdiomaActivo -> código de idioma no válido");
            return null;
        }
		return getEntityManager().find(Idioma.class, codIdioma);
	}
	
	@Override
	public Idioma getIdiomaActivo(String codIdioma) {
		if (codIdioma == null) {
			log.warn("No se ha completado la petición: getIdioma -> código de idioma no válido");
			return null;
		}
		
		String sql = "SELECT i FROM Idioma i WHERE i.codIdioma ='" + codIdioma + "'"
				+ " AND i.activo = '1'";
        TypedQuery<Idioma> query = getEntityManager().createQuery(sql, Idioma.class);
        try {
        	return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}