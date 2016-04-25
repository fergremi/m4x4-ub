package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import maxaub.ejb.interfaz.SocioDAO;
import model.Socio;

@Stateless
public class SocioFacade {
	@PersistenceContext(unitName = "openjpa")
    private EntityManager entityManager;

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    protected EntityManager getEntityManager() {
        return entityManager;
    }
	
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

	public Socio comprobarSocio(String usuario, String clave) {
		if ((usuario == "") || (clave == "")) {
			// Error: CAMPO/S VACÍO/S
			log.info("No se ha completado la petición: comprobarSocio -> campo/s vacío/s");
			return null;
		}

		// Consulta: buscamos una correspondencia usuario/clave          
		return (Socio) getEntityManager().createQuery("SELECT s FROM Socio s WHERE s.dni='" + usuario + "' and s.clave='" + clave + "'").getResultList();
	}
}