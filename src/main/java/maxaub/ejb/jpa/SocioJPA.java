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
		String sql = "SELECT s FROM Socio AS s ORDER BY s.id";
		TypedQuery<Socio> query = getEntityManager().createQuery(sql, Socio.class);
		List<Socio> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Socio> getSociosActivos() {
		String sql = "SELECT s FROM Socio AS s WHERE s.activo = '1' ORDER BY s.id";
		TypedQuery<Socio> query = getEntityManager().createQuery(sql, Socio.class);
		List<Socio> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public void crearSocio(Socio socio) {
		getEntityManager().persist(socio);
		getEntityManager().flush();
	}
	
	@Override
	public void guardarSocio(Socio socio) {
		getEntityManager().merge(socio);
		getEntityManager().flush();
	}
	
	@Override
	public void eliminarSocio(Socio socio) {
		getEntityManager().remove(socio);
		getEntityManager().flush();
	}
	
	@Override
	public Socio comprobarSocio(String usuario, String contraseña) {
		if ((usuario == "") || (contraseña == "")) {
			log.warn("No se ha completado la petición: comprobarSocio -> campo/s vacío/s");
			return null;
		}
		if ((comprobarComentariosSQL(usuario)) || (comprobarComentariosSQL(contraseña))) {
    		log.warn("No se ha completado la petición: comprobarSocio -> comentarios SQL bloqueados");
            return null;
        }
		
		String sql = "SELECT s FROM Socio AS s WHERE s.dni = :usuario AND s.contraseña = :contraseña"
				+ " AND s.activo = '1'";
		TypedQuery<Socio> query = getEntityManager().createQuery(sql, Socio.class);
		query.setParameter("usuario", usuario);
		query.setParameter("contraseña", contraseña);
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}