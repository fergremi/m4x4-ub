package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Prestamo;

@Local
public interface PrestamoDAO {
    public List<Prestamo> getPrestamos();
    public Prestamo getPrestamo(int idPrestamo);
    public void guardarPrestamo(Prestamo prestamo);
    public void eliminarPrestamo(Prestamo prestamo);
}