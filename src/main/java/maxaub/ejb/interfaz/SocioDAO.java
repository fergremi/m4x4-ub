package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Socio;

@Local
public interface SocioDAO {
    
	/**
	 * Obtener todos los socios.
	 * 
	 * @return List<Socio>
	 */
	public List<Socio> getSocios();
    
	/**
	 * Obtener todos los socios activos.
	 * 
	 * @return List<Socio>
	 */
	public List<Socio> getSociosActivos();
    
	/**
	 * Almacenar socio.
	 * 
	 * @param Socio
	 */
	public void guardarSocio(Socio socio);
    
	/**
	 * Eliminar socio.
	 * 
	 * @param Socio
	 */
	public void eliminarSocio(Socio socio);
    
	/**
	 * Comprobar si el usuario y la contraseña entregados son válidos.
	 * 
	 * @param usuario
	 * @param contraseña
	 * @return Socio
	 */
	public Socio comprobarSocio(String usuario, String contraseña);
}