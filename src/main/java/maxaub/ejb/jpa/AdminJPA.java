package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.AdminDAO;
import maxaub.modelo.Admin;

@Stateless
public class AdminJPA extends BaseJPA implements AdminDAO {
	@Override
	public List<Admin> getAdmins() {
		String sql = "SELECT a FROM Admin a ORDER BY a.idAdmin";
		TypedQuery<Admin> query = getEntityManager().createQuery(sql, Admin.class);
		List<Admin> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public Admin comprobarAdmin(String usuario, String clave) {
		if ((usuario == "") || (clave == "")) {
			log.warn("No se ha completado la petición: comprobarAdmin -> campo/s vacío/s");
			return null;
		}
		if ((comprobarComentariosSQL(usuario)) || (comprobarComentariosSQL(clave))) {
    		log.warn("No se ha completado la petición: comprobarAdmin -> comentarios SQL bloqueados");
            return null;
        }
		
		String sql = "SELECT a FROM Admin a WHERE a.usuario = :usuario and a.clave = :clave"
				+ " AND s.activo = '1'";
		TypedQuery<Admin> query = getEntityManager().createQuery(sql, Admin.class);
		query.setParameter("usuario", usuario);
		query.setParameter("clave", clave);
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}