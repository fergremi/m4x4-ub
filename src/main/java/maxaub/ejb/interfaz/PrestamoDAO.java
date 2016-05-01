package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Prestamo;

@Local
public interface PrestamoDAO {
	/**
	 * Obtener todos los préstamos.
	 * 
	 * @return List<Prestamo>
	 */
	public List<Prestamo> getPrestamos();
    
	/**
	 * Obtener préstamo.
	 * 
	 * @param idPrestamo
	 * @return Prestamo
	 */
	public Prestamo getPrestamo(int idPrestamo);
    
	/**
	 * Almacenar préstamo.
	 * 
	 * @param Prestamo
	 */
	public void guardarPrestamo(Prestamo prestamo);
    
	/**
	 * Eliminar préstamo.
	 * 
	 * @param Prestamo
	 */
	public void eliminarPrestamo(Prestamo prestamo);
}