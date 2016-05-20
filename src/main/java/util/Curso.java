package util;

import java.util.ArrayList;
import java.util.List;

public class Curso {
	public static final List<Curso> CURSOS;
    static {
        CURSOS = new ArrayList<Curso>();
        CURSOS.add(new Curso(3, "Tercero"));
        CURSOS.add(new Curso(4, "Cuarto"));
        CURSOS.add(new Curso(5, "Quinto"));
        CURSOS.add(new Curso(6, "Sexto"));
    }
    public static final Curso DEFAULT_CURSO = CURSOS.get(0);
    
	private int id;
	private String nombre;

	public Curso() {}

	public Curso(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}