package maxaub.ejb.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import maxaub.ejb.interfaz.AlumnoDAO;
import maxaub.modelo.Alumno;

@Stateless
public class AlumnoJPA extends BaseJPA implements AlumnoDAO {
	@Override
	public List<Alumno> getAlumnos() {
		String sql = "SELECT a FROM Alumno a ORDER BY a.idAlumno";
		TypedQuery<Alumno> query = getEntityManager().createQuery(sql, Alumno.class);
		List<Alumno> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<Alumno> getAlumnosActivos() {
		String sql = "SELECT a FROM Alumno a WHERE a.activo = '1' ORDER BY a.idAlumno";
		TypedQuery<Alumno> query = getEntityManager().createQuery(sql, Alumno.class);
		List<Alumno> list = query.getResultList();
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public Alumno getAlumno(int idAlumno) {
		if (idAlumno <= 0) {
			log.warn("No se ha completado la petición: getAlumno -> índice no válido");
			return null;
		}
		
		return getEntityManager().find(Alumno.class, idAlumno);
	}
	
	@Override
	public Alumno getAlumnoActivo(int idAlumno) {
		if (idAlumno <= 0) {
			log.warn("No se ha completado la petición: getAlumnoActivo -> índice no válido");
			return null;
		}
		
		String sql = "SELECT a FROM Alumno a WHERE a.idAlumno ='" + idAlumno + "'"
				+ " AND a.activo = '1'";
        TypedQuery<Alumno> query = getEntityManager().createQuery(sql, Alumno.class);
        try {
        	return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void guardarAlumno(Alumno alumno) {
        getEntityManager().persist(alumno);
        getEntityManager().flush();
	}
	
	@Override
	public void eliminarAlumno(Alumno alumno) {
		getEntityManager().remove(alumno);
		getEntityManager().flush();
	}
}