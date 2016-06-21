package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Ejemplar;
import maxaub.modelo.Libro;

@Local
public interface EjemplarDAO {
	/**
	 * Obtener todos los ejemplares.
	 * 
	 * @return {@link List}<{@link Ejemplar}>
	 */
	public List<Ejemplar> getEjemplares();
	
	/**
	 * Obtener todos los ejemplares de un libro.
	 * 
	 * @param libro
	 * @return {@link List}<{@link Ejemplar}>
	 */
	public List<Ejemplar> getEjemplares(Libro libro);

	/**
	 * Obtener los ejemplares sin lote de una asignatura,
	 * cuyo libro esté activo.
	 * 
	 * @param asignatura
	 * @return {@link List}<{@link Libro}>
	 */
	public List<Ejemplar> getEjemplaresAsignatura(String asignatura, int estado);
	
	/**
	 * Obtener los ejemplares cuyo estado sea bueno.
	 * 
	 * @return {@link List}<{@link Ejemplar}>
	 */
	public List<Ejemplar> getEjemplaresEstadoBueno();

	/**
	 * Obtener los ejemplares cuyo estado sea regular.
	 * 
	 * @return {@link List}<{@link Ejemplar}>
	 */
	public List<Ejemplar> getEjemplaresEstadoRegular();

	/**
	 * Obtener los ejemplares cuyo estado sea malo.
	 * 
	 * @return {@link List}<{@link Ejemplar}>
	 */
	public List<Ejemplar> getEjemplaresEstadoMalo();

	/**
	 * Obtener ejemplar.
	 * 
	 * @param idEjemplar
	 * @return {@link Ejemplar}
	 */
	public Ejemplar getEjemplar(int idEjemplar);

	/**
	 * Crear ejemplar.
	 * 
	 * @param ejemplar
	 */
	public void crearEjemplar(Ejemplar ejemplar);

	/**
	 * Almacenar ejemplar.
	 * 
	 * @param ejemplar
	 */
	public void guardarEjemplar(Ejemplar ejemplar);

	/**
	 * Eliminar ejemplar.
	 * 
	 * @param ejemplar
	 */
	public void eliminarEjemplar(Ejemplar ejemplar);
}