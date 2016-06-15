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
		String sql = "SELECT a FROM Admin AS a ORDER BY a.id";
		TypedQuery<Admin> query = getEntityManager().createQuery(sql, Admin.class);
		List<Admin> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public Admin comprobarAdmin(String usuario, String contraseña) {
		if ((usuario == "") || (contraseña == "")) {
			LOG.warn("No se ha completado la petición: comprobarAdmin -> campo/s vacío/s");
			return null;
		}
		if ((comprobarComentariosSQL(usuario)) || (comprobarComentariosSQL(contraseña))) {
    		LOG.warn("No se ha completado la petición: comprobarAdmin -> comentarios SQL bloqueados");
            return null;
        }
		
		String sql = "SELECT a FROM Admin AS a WHERE a.usuario = :usuario AND a.contraseña = :contraseña";
		TypedQuery<Admin> query = getEntityManager().createQuery(sql, Admin.class);
		query.setParameter("usuario", usuario);
		query.setParameter("contraseña", contraseña);
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void guardarAdmin(Admin admin) {
		getEntityManager().merge(admin);
		getEntityManager().flush();
	}
}