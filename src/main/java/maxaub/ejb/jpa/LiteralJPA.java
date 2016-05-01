package maxaub.ejb.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.LiteralDAO;
import maxaub.modelo.Literal;

@Stateless
public class LiteralJPA extends BaseJPA implements LiteralDAO {
	@Override
	public List<Literal> getLiterales() {
		String sql = "SELECT l FROM Literal l ORDER BY l.literal";
		TypedQuery<Literal> query = getEntityManager().createQuery(sql, Literal.class);
		List<Literal> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public Map<String, String> getLiteralesIdioma(Locale locale) {
		String sql = "SELECT l FROM Literal l WHERE l.idioma.cod = :idiomaCod ORDER BY l.literal";
		TypedQuery<Literal> query = getEntityManager().createQuery(sql, Literal.class);
		query.setParameter("idiomaCod", locale.getLanguage());
		List<Literal> list = query.getResultList();
		if (!list.isEmpty()) {
			Map<String, String> literales = new HashMap<String, String>();                
		    for(Literal literal : list){
		    	literales.put(literal.getLiteral(), literal.getTraduccion());
		        log.info("literal[" + literal.getLiteral() + "][" + literal.getTraduccion() + "]");
		    }
		    return literales;
		}
		return null;
	}
	
	@Override
	public Literal getLiteral(String literal) {
		if (literal == null) {
			log.warn("No se ha completado la petición: getLiteral -> literal no válido");
			return null;
		}
		
		String sql = "SELECT l FROM Literal l WHERE l.literal ='" + literal + "'";
        TypedQuery<Literal> query = getEntityManager().createQuery(sql, Literal.class);
        try {
        	return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void guardarLiteral(Literal literal) {
		getEntityManager().persist(literal);
		getEntityManager().flush();
	}

	@Override
	public void eliminarLiteral(Literal literal) {
		getEntityManager().remove(literal);
		getEntityManager().flush();
	}
}