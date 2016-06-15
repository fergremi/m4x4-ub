package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Socio;

@Local
public interface SocioDAO {
    
	/**
	 * Obtener todos los socios.
	 * 
	 * @return {@link List}<{@link Socio}>
	 */
	public List<Socio> getSocios();
    
	/**
	 * Obtener todos los socios activos.
	 * 
	 * @return {@link List}<{@link Socio}>
	 */
	public List<Socio> getSociosActivos();
    
	/**
	 * Crear socio.
	 * 
	 * @param socio
	 */
	public void crearSocio(Socio socio);
	
	/**
	 * Almacenar socio.
	 * 
	 * @param socio
	 */
	public void guardarSocio(Socio socio);
    
	/**
	 * Eliminar socio.
	 * 
	 * @param socio
	 */
	public void eliminarSocio(Socio socio);
    
	/**
	 * Comprobar si el usuario y la contraseña entregados son válidos.
	 * 
	 * @param usuario
	 * @param contraseña
	 * @return {@link Socio}
	 */
	public Socio comprobarSocio(String usuario, String contraseña);
}