package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;

import maxaub.modelo.Alumno;

@Stateless
public class AlumnoFacade extends BaseFacade {
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