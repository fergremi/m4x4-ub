package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.SocioDAO;
import maxaub.modelo.Socio;

@Stateless
public class SocioJPA extends BaseJPA implements SocioDAO {
	@Override
	public List<Socio> getSocios() {
		String sql = "SELECT s FROM Socio s ORDER BY s.idSocio";
		TypedQuery<Socio> query = getEntityManager().createQuery(sql, Socio.class);
		List<Socio> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Socio> getSociosActivos() {
		String sql = "SELECT s FROM Socio s WHERE s.activo = '1' ORDER BY s.idSocio";
		TypedQuery<Socio> query = getEntityManager().createQuery(sql, Socio.class);
		List<Socio> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public void guardarSocio(Socio socio) {
		getEntityManager().persist(socio);
		getEntityManager().flush();
	}
	
	@Override
	public void eliminarSocio(Socio socio) {
		getEntityManager().remove(socio);
		getEntityManager().flush();
	}
	
	@Override
	public Socio comprobarSocio(String usuario, String clave) {
		if ((usuario == "") || (clave == "")) {
			log.warn("No se ha completado la petición: comprobarSocio -> campo/s vacío/s");
			return null;
		}
		if ((comprobarComentariosSQL(usuario)) || (comprobarComentariosSQL(clave))) {
    		log.warn("No se ha completado la petición: comprobarSocio -> comentarios SQL bloqueados");
            return null;
        }
		
		String sql = "SELECT s FROM Socio s WHERE s.dni = :usuario and s.clave = :clave"
				+ " AND s.activo = '1'";
		TypedQuery<Socio> query = getEntityManager().createQuery(sql, Socio.class);
		query.setParameter("usuario", usuario);
		query.setParameter("clave", clave);
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}