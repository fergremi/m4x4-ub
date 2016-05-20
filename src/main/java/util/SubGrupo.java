package util;

import java.util.ArrayList;
import java.util.List;

public class SubGrupo {
	public static final List<SubGrupo> SUBGRUPOS;
    static {
        SUBGRUPOS = new ArrayList<SubGrupo>();
        SUBGRUPOS.add(new SubGrupo("es", "Castellano"));
        SUBGRUPOS.add(new SubGrupo("va", "Valenciano"));
    }
    public static final SubGrupo DEFAULT_SUBGRUPO = SUBGRUPOS.get(0);
    
	private String cod;
	private String nombre;

	public SubGrupo() {}

	public SubGrupo(String cod, String nombre) {
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