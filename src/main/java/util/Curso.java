package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Curso {
	public static final Map<Integer, Curso> mapCURSOS;
	static {
		mapCURSOS = new HashMap<Integer, Curso>();
		mapCURSOS.put(3, new Curso(3, "Tercero"));
		mapCURSOS.put(4, new Curso(4, "Cuarto"));
		mapCURSOS.put(5, new Curso(5, "Quinto"));
		mapCURSOS.put(6, new Curso(6, "Sexto"));
	}
	public static final List<Curso> CURSOS = new ArrayList<Curso>(mapCURSOS.values());
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