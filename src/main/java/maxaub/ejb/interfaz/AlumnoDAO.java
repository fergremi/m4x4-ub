package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Alumno;

@Local
public interface AlumnoDAO {
	/**
	 * Obtener todos los alumnos.
	 * 
	 * @return List<Alumno>
	 */
	public List<Alumno> getAlumnos();
    
	/**
	 * Obtener todos los alumnos activos.
	 * 
	 * @return List<Alumno>
	 */
	public List<Alumno> getAlumnosActivos();
    
	/**
	 * Obtener alumno.
	 * 
	 * @param idAlumno
	 * @return Alumno
	 */
	public Alumno getAlumno(int numAlumno);
    
	/**
	 * Obtener alumno activo.
	 * 
	 * @param idAlumno
	 * @return Alumno
	 */
	public Alumno getAlumnoActivo(int idAlumno);
    
	/**
	 * Almacenar alumno.
	 * 
	 * @param Alumno
	 */
	public void guardarAlumno(Alumno alumno);
    
	/**
	 * Eliminar alumno.
	 * 
	 * @param Alumno
	 */
	public void eliminarAlumno(Alumno alumno);
}