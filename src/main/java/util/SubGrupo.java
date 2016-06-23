package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubGrupo {
	public static final Map<String, SubGrupo> mapSUBGRUPOS;
	static {
		mapSUBGRUPOS = new HashMap<String, SubGrupo>();
		mapSUBGRUPOS.put("es", new SubGrupo("es", "Castellano"));
		mapSUBGRUPOS.put("va", new SubGrupo("va", "Valenciano"));
	}
	public static final List<SubGrupo> SUBGRUPOS = new ArrayList<SubGrupo>(mapSUBGRUPOS.values());
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