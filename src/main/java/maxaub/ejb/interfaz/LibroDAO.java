package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Libro;

@Local
public interface LibroDAO {
	/**
	 * Obtener todos los libros.
	 * 
	 * @return List<Libro>
	 */
	public List<Libro> getLibros();
    
	/**
	 * Obtener todos los libros activos.
	 * 
	 * @return List<Libro>
	 */
	public List<Libro> getLibrosActivos();
    
	/**
	 * Obtener libro.
	 * 
	 * @param idLibro
	 * @return Libro
	 */
	public Libro getLibro(int idLibro);
    
	/**
	 * Obtener libro activo.
	 * 
	 * @param idLibro
	 * @return Libro
	 */
	public Libro getLibroActivo(int idLibro);
    
	/**
	 * Almacenar libro.
	 * 
	 * @param Libro
	 */
	public void guardarLibro(Libro libro);
    
	/**
	 * Eliminar libro.
	 * 
	 * @param Libro
	 */
	public void eliminarLibro(Libro libro);
}