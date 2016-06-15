package maxaub.controlador;


import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class DirectivaController implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(DirectivaController.class.getName());
	
	private List<Directivo> directivos;
	
	public DirectivaController() {
		directivos = new ArrayList<>();
		
		Directivo directivo1 = new Directivo("Amparo Jesús-María", "Presidenta", "FAPA");
		directivos.add(directivo1);
		
		Directivo directivo2 = new Directivo("Iñaki Mazkiarán", "Vicepresident", "Comissió pro-millores");
		directivos.add(directivo2);
		
		Directivo directivo3 = new Directivo("Carmen Gutiérrez", "Secretària", "Extraescolars Municipals i campament");
		directivos.add(directivo3);
		
		Directivo directivo4 = new Directivo("Silvia Jiménez", "Sotssecretària", "Formació de mares i pares i Web");
		directivos.add(directivo4);
		
		Directivo directivo5 = new Directivo("Esther Lázaro", "Tresorera", "Comissió econòmica, Loteria, Campament i extraescolars municipals");
		directivos.add(directivo5);
		
		Directivo directivo6 = new Directivo("David Ortolá", "Comptable", "Comissió econòmica i Loteria");
		directivos.add(directivo6);
		
		Directivo directivo7 = new Directivo("Alícia Giménez", "Vocal", "Socialització de llibres, Campament, Trobades y al eixir de classe");
		directivos.add(directivo7);
		
		Directivo directivo8 = new Directivo("Ana Mª Moreno", "Vocal", "Arco Iris i Comissió socialització de llibres");
		directivos.add(directivo8);
		
		Directivo directivo9 = new Directivo("Marián Rubi", "Vocal", "Arco Iris");
		directivos.add(directivo9);
		
		Directivo directivo10 = new Directivo("Mayte Piñol", "Tresorera", "Comissió econòmica i Loteria");
		directivos.add(directivo10);
		
		Directivo directivo11 = new Directivo("Mar March", "Vocal", "Trobades, CAPPEV");
		directivos.add(directivo11);
		
		Directivo directivo12 = new Directivo("Jose A. Rodriguez", "Vocal", "Festes");
		directivos.add(directivo12);
		
		Directivo directivo13 = new Directivo("Ana Vicente", "Vocal", "");
		directivos.add(directivo13);

		Directivo directivo14 = new Directivo("Laura Tenés", "Vocal", "Festes i campament");
		directivos.add(directivo14);
		
		Directivo directivo15 = new Directivo("Paco Carrión", "Vocal", "Cooperació i Web");
		directivos.add(directivo15);
	}
	
	public List<Directivo> getDirectivos() {
		return directivos;
	}
	public void setDirectivos(List<Directivo> directivos) {
		this.directivos = directivos;
	}
	
    public StreamedContent getEstatutos() {        
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/docs/estatutos.doc");
        return new DefaultStreamedContent(stream, "doc", "estatutos.doc");
    }

	public class Directivo {
		private String nombre;
		private String cargo;
		private String responsabilidad;
		
		public Directivo() {
		}
		
		public Directivo(String nombre, String cargo, String responsabilidad) {
			super();
			this.nombre = nombre;
			this.cargo = cargo;
			this.responsabilidad = responsabilidad;
		}

		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
		public String getCargo() {
			return cargo;
		}
		public void setCargo(String cargo) {
			this.cargo = cargo;
		}
		
		public String getResponsabilidad() {
			return responsabilidad;
		}
		public void setResponsabilidad(String responsabilidad) {
			this.responsabilidad = responsabilidad;
		}
	}
}