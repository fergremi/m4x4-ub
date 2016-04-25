package ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import model.Libro;

@Local
public interface LibroDAO {
    public List<Libro> getLibros();
    public Libro getLibro(int idLibro);
    public void guardarLibro(Libro libro);
    public void eliminarLibro(Libro libro);
}