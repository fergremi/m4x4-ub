package maxaub.ejb.interfaz;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Local;

import maxaub.modelo.Literal;

@Local
public interface LiteralDAO {
	/**
	 * Obtener todos los literales.
	 * 
	 * @return List<Literales>
	 */
	public List<Literal> getLiterales();
    
	/**
	 * Obtener todos los literales del idioma seleccionado.
	 * 
	 * @param locale
	 * @return
	 */
	public Map<String, String> getLiteralesIdioma(Locale locale);
	
	/**
	 * Obtener literal.
	 * 
	 * @param literal
	 * @return Literal
	 */
	public Literal getLiteral(String literal);
    
	/**
	 * Almacenar literal.
	 * 
	 * @param Literal
	 */
	public void guardarLiteral(Literal literal);
    
	/**
	 * Eliminar literal.
	 * 
	 * @param Literal
	 */
	public void eliminarLiteral(Literal literal);
}