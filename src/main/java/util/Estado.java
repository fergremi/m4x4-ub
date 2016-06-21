package util;

import java.util.ArrayList;
import java.util.List;

public class Estado {
	public static final List<Estado> ESTADOS;
    static {
        ESTADOS = new ArrayList<Estado>();
        ESTADOS.add(new Estado(1, "Bueno"));
        ESTADOS.add(new Estado(2, "Regular"));
        ESTADOS.add(new Estado(3, "Malo"));
    }
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

	public boolean equals(Object obj) {
		Estado estado = (Estado) obj;
		if (getId() == estado.getId()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}