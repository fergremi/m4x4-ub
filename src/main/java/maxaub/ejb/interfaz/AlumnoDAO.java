package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Alumno;
import maxaub.modelo.Socio;

@Local
public interface AlumnoDAO {
	/**
	 * Obtener todos los alumnos.
	 * 
	 * @return {@link List}<{@link Alumno}>
	 */
	public List<Alumno> getAlumnos();
    
	/**
	 * Obtener todos los alumnos activos.
	 * 
	 * @return {@link List}<{@link Alumno}>
	 */
	public List<Alumno> getAlumnosActivos();
    
	/**
	 * Obtener alumno.
	 * 
	 * @param idAlumno
	 * @return {@link Alumno}
	 */
	public Alumno getAlumno(int idAlumno);
    
	/**
	 * Obtener alumno activo.
	 * 
	 * @param idAlumno
	 * @return {@link Alumno}
	 */
	public Alumno getAlumnoActivo(int idAlumno);
    
	/**
	 * Crear alumno.
	 * 
	 * @param alumno
	 */
	public void crearAlumno(Alumno alumno);
	
	/**
	 * Almacenar alumno.
	 * 
	 * @param alumno
	 */
	public void guardarAlumno(Alumno alumno);
    
	/**
	 * Eliminar alumno.
	 * 
	 * @param alumno
	 */
	public void eliminarAlumno(Alumno alumno);
	
	/**
	 * Obtiene todos los alumnos relacionados con el socio. <br>
	 * <code><strong>&ltEAGER FETCHING&gt</strong></code>
	 * 
	 * @param socio
	 * @return {@link List}<{@link Alumno}>
	 */
	public List<Alumno> getAlumnosSocio(Socio socio);
	
	/**
	 * Obtiene todos los alumnos relacionados con el socio activo. <br>
	 * <code><strong>&ltEAGER FETCHING&gt</strong></code>
	 * 
	 * @param socio
	 * @return {@link List}<{@link Alumno}>
	 */
	public List<Alumno> getAlumnosSocioActivo(Socio socio);
}