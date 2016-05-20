package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Admin;

@Local
public interface AdminDAO {
	/**
	 * Obtener todos los administradores.
	 * 
	 * @return List<Admin>
	 */
	public List<Admin> getAdmins();
    
	/**
	 * Comprobar si el usuario y la contraseña entregados son válidos.
	 * 
	 * @param usuario
	 * @param contraseña
	 * @return Admin
	 */
	public Admin comprobarAdmin(String usuario, String contraseña);
	
	/**
	 * Almacenar admin.
	 * 
	 * @param Admin
	 */
	public void guardarAdmin(Admin admin);
}