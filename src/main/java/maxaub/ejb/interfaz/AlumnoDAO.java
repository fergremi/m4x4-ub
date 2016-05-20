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
	 * Crear alumno.
	 * 
	 * @param Alumno
	 */
	public void crearAlumno(Alumno alumno);
	
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
	
	/**
	 * Obtiene todos los alumnos relacionados con el socio. <br>
	 * <code><strong>&ltEAGER FETCHING&gt</strong></code>
	 * 
	 * @param Socio
	 * @return {@link List}<{@link Alumno}>
	 */
	public List<Alumno> getAlumnosSocio(Socio socio);
	
	/**
	 * Obtiene todos los alumnos relacionados con el socio activo. <br>
	 * <code><strong>&ltEAGER FETCHING&gt</strong></code>
	 * 
	 * @param Socio
	 * @return {@link List}<{@link Alumno}>
	 */
	public List<Alumno> getAlumnosSocioActivo(Socio socio);
}