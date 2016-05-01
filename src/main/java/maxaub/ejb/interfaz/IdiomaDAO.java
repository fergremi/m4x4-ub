package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Idioma;

@Local
public interface IdiomaDAO {
	/**
	 * Obtener todos los idiomas.
	 * 
	 * @return List<Idioma>
	 */
	public List<Idioma> getIdiomas();
    
	/**
	 * Obtener todos los idiomas activos.
	 * 
	 * @return List<Idioma>
	 */
	public List<Idioma> getIdiomasActivos();
    
	/**
	 * Obtener idioma.
	 * 
	 * @param codIdioma
	 * @return Idioma
	 */
	public Idioma getIdioma(String codIdioma);
    
	/**
	 * Obtener idioma activo.
	 * 
	 * @param codIdioma
	 * @return Idioma
	 */
	public Idioma getIdiomaActivo(String codIdioma);
}