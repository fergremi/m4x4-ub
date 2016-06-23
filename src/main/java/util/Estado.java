package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estado {
	public static final Map<Integer, Estado> mapESTADOS;
	static {
		mapESTADOS = new HashMap<Integer, Estado>();
		mapESTADOS.put(1, new Estado(1, "Bueno"));
		mapESTADOS.put(2, new Estado(2, "Regular"));
		mapESTADOS.put(3, new Estado(3, "Malo"));
	}
	public static final List<Estado> ESTADOS = new ArrayList<Estado>(mapESTADOS.values());
    public static final Estado DEFAULT_ESTADO = ESTADOS.get(1);
    
	private int id;
	private String nombre;

	public Estado() {}

	public Estado(int id, String nombre) {
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