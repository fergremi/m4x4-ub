package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Lote;
import maxaub.modelo.Prestamo;

@Local
public interface PrestamoDAO {
	/**
	 * Obtener todos los préstamos.
	 * 
	 * @return {@link List}<{@link Prestamo}>
	 */
	public List<Prestamo> getPrestamos();
    
	/**
	 * Obtener préstamo.
	 * 
	 * @param idPrestamo
	 * @return {@link Prestamo}
	 */
	public Prestamo getPrestamo(int idPrestamo);
    
	/**
	 * Crear préstamo.
	 * 
	 * @param prestamo
	 */
	public void crearPrestamo(Prestamo prestamo);
	
	/**
	 * Almacenar préstamo.
	 * 
	 * @param prestamo
	 */
	public void guardarPrestamo(Prestamo prestamo);
    
	/**
	 * Eliminar préstamo.
	 * 
	 * @param prestamo
	 */
	public void eliminarPrestamo(Prestamo prestamo);
	
	/**
	 * Indica si un lote tiene asignado algún préstamo.
	 * 
	 * @param lote
	 * @return {@link Boolean}
	 */
	public Boolean isPrestado(Lote lote);
}