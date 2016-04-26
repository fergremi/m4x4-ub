package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import maxaub.model.Alumno;

@Stateless
public class AlumnoFacade {
	@PersistenceContext(unitName = "openjpa")
    private EntityManager entityManager;

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    protected EntityManager getEntityManager() {
        return entityManager;
    }
	
	@SuppressWarnings("unchecked")
	public List<Alumno> getAlumnos() {
        return getEntityManager().createQuery("select f from alumno f order by f.idAlumno").getResultList();
	}
	
	public Alumno getAlumno(int numAlumno) {
        return (Alumno) getEntityManager().createQuery("select f from alumno f where f.idAlumno='" + numAlumno + "'").getResultList();
	}
	
	public void guardarAlumno(Alumno alumno) {
        getEntityManager().merge(alumno);
	}
    
	public void eliminarAlumno(Alumno alumno) {
		getEntityManager().remove(alumno);
	}
}