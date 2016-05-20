package util;

import java.util.ArrayList;
import java.util.List;

public class Tema {
    public static final List<Tema> TEMAS;
    static {
        TEMAS = new ArrayList<Tema>();
        TEMAS.add(new Tema("afterdark", "Afterdark"));
        TEMAS.add(new Tema("afternoon", "Afternoon"));
        TEMAS.add(new Tema("afterwork", "Afterwork"));
        TEMAS.add(new Tema("aristo", "Aristo"));
        TEMAS.add(new Tema("black-tie", "Black-Tie"));
        TEMAS.add(new Tema("blitzer", "Blitzer"));
        TEMAS.add(new Tema("bluesky", "Bluesky"));
        TEMAS.add(new Tema("bootstrap", "Bootstrap"));
        TEMAS.add(new Tema("casablanca", "Casablanca"));
        TEMAS.add(new Tema("cupertino", "Cupertino"));
        TEMAS.add(new Tema("cruze", "Cruze"));
        TEMAS.add(new Tema("dark-hive", "Dark-Hive"));
        TEMAS.add(new Tema("delta", "Delta"));
        TEMAS.add(new Tema("dot-luv", "Dot-Luv"));
        TEMAS.add(new Tema("eggplant", "Eggplant"));
        TEMAS.add(new Tema("excite-bike", "Excite-Bike"));
        TEMAS.add(new Tema("flick", "Flick"));
        TEMAS.add(new Tema("glass-x", "Glass-X"));
        TEMAS.add(new Tema("home", "Home"));
        TEMAS.add(new Tema("hot-sneaks", "Hot-Sneaks"));
        TEMAS.add(new Tema("humanity", "Humanity"));
        TEMAS.add(new Tema("le-frog", "Le-Frog"));
        TEMAS.add(new Tema("midnight", "Midnight"));
        TEMAS.add(new Tema("mint-choc", "MInteger-Choc"));
        TEMAS.add(new Tema("overcast", "Overcast"));
        TEMAS.add(new Tema("pepper-grinder", "Pepper-Grinder"));
        TEMAS.add(new Tema("redmond", "Redmond"));
        TEMAS.add(new Tema("rocket", "Rocket"));
        TEMAS.add(new Tema("sam", "Sam"));
        TEMAS.add(new Tema("smoothness", "Smoothness"));
        TEMAS.add(new Tema("south-street", "South-Street"));
        TEMAS.add(new Tema("start", "Start"));
        TEMAS.add(new Tema("sunny", "Sunny"));
        TEMAS.add(new Tema("swanky-purse", "Swanky-Purse"));
        TEMAS.add(new Tema("trontastic", "Trontastic"));
        TEMAS.add(new Tema("ui-darkness", "UI-Darkness"));
        TEMAS.add(new Tema("ui-lightness", "UI-Lightness"));
        TEMAS.add(new Tema("vader", "Vader"));
    }
    public static final Tema DEFAULT_TEMA = TEMAS.get(35);
    
	private String cod;
	private String nombre;

	public Tema() {}

	public Tema(String cod, String nombre) {
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