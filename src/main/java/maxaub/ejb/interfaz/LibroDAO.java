package maxaub.ejb.interfaz;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Libro;

@Local
public interface LibroDAO {
	/**
	 * Obtener todos los libros.
	 * 
	 * @return {@link List}<{@link Libro}>
	 */
	public List<Libro> getLibros();
    
	/**
	 * Obtener todos los libros activos.
	 * 
	 * @return {@link List}<{@link Libro}>
	 */
	public List<Libro> getLibrosActivos();
	
	/**
	 * Obtener libro.
	 * 
	 * @param isbn
	 * @return {@link Libro}
	 */
	public Libro getLibro(BigInteger isbn);
    
	/**
	 * Obtener libro activo.
	 * 
	 * @param isbn
	 * @return {@link Libro}
	 */
	public Libro getLibroActivo(BigInteger isbn);
    
	/**
	 * Crear libro.
	 * 
	 * @param libro
	 */
	public void crearLibro(Libro libro);
	
	/**
	 * Almacenar libro.
	 * 
	 * @param libro
	 */
	public void guardarLibro(Libro libro);
    
	/**
	 * Eliminar libro.
	 * 
	 * @param libro
	 */
	public void eliminarLibro(Libro libro);
	
	/**
	 * Obtener todas las asignaturas de todos los libros.
	 * 
	 * @return {@link List}<{@link String}>
	 */
	public List<String> getAsignaturasLibros();
	
	/**
	 * Obtener todos los cursos de todos los libros.
	 * 
	 * @return {@link List}<{@link String}>
	 */
	public List<String> getCursosLibros();
	
	/**
	 * Obtener todas las editoriales de todos los libros.
	 * 
	 * @return {@link List}<{@link String}>
	 */
	public List<String> getEditorialesLibros();
	
	/**
	 * Obtener todos los idiomas de todos los libros.
	 * 
	 * @return {@link List}<{@link String}>
	 */
	public List<String> getIdiomasLibros();
}