package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.modelo.Socio;

@Stateless
public class SocioFacade extends BaseFacade {
	@SuppressWarnings("unchecked")
	public List<Socio> getSocios() {
		return getEntityManager().createQuery("select s from socio s where s.activo=1 order by s.idSocio").getResultList();
	}  

	public void guardarSocio(Socio socio) {
		getEntityManager().merge(socio);
	}

	public void eliminarSocio(Socio socio) {
		getEntityManager().remove(socio);
	}
	
	/**
	 * Comprobar si el usuario y la clave entregados son válidos
	 * 
	 * @param usuario
	 * @param clave
	 * @return
	 */
	public Socio comprobarSocio(String usuario, String clave) {
		if ((usuario == "") || (clave == "")) {
			// Error: CAMPO/S VACÍO/S
			log.warn("No se ha completado la petición: comprobarSocio -> campo/s vacío/s");
			return null;
		}
		if ((comprobarComentariosSQL(usuario)) || (comprobarComentariosSQL(clave))) {
            // Error: NO SE PERMITEN COMENTARIOS SQL
    		log.warn("No se ha completado la petición: comprobarUsuario -> comentarios SQL bloqueados");
            return null;
        }

		// Consulta: buscamos una correspondencia usuario/clave          
		TypedQuery<Socio> query = getEntityManager().createQuery(
				"SELECT s FROM Socio s WHERE s.dni= :usuario and s.clave= :clave",
				Socio.class);
		query.setParameter("usuario", usuario);
		query.setParameter("clave", clave);
		List<Socio> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}