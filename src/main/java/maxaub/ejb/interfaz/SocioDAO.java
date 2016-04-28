package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Socio;

@Local
public interface SocioDAO {
    public List<Socio> getSocios();
    public void guardarSocio(Socio socio);
    public void eliminarSocio(Socio socio);
    public Socio comprobarSocio(String usuario, String clave);
}