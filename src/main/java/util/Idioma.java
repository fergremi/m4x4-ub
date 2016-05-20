package util;

import java.util.ArrayList;
import java.util.List;

public class Idioma {
	public static final List<Idioma> IDIOMAS;
    static {
        IDIOMAS = new ArrayList<Idioma>();
        IDIOMAS.add(new Idioma("es", "Español"));
        IDIOMAS.add(new Idioma("va", "Valenciano"));
    }
    public static final Idioma DEFAULT_IDIOMA = IDIOMAS.get(0);
    
	private String cod;
	private String nombre;

	public Idioma() {}

	public Idioma(String cod, String nombre) {
		this.cod = cod;
		this.nombre = nombre;
	}
	
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}