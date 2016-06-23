package maxaub.controlador;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import util.Curso;
import util.Estado;
import util.Idioma;
import util.SubGrupo;
import util.Tema;

@ManagedBean
@ApplicationScoped
public class UtilController implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(UtilController.class.getName());

	private List<Idioma> idiomas;
	private List<Tema> temas;
	
	private List<Curso> cursos;
	private List<SubGrupo> subgrupos;
	private List<Estado> estados;

	public UtilController() {
		super();
		
		idiomas = Idioma.IDIOMAS;
		temas = Tema.TEMAS;
		
		estados = Estado.ESTADOS;
		cursos = Curso.CURSOS;
		subgrupos = SubGrupo.SUBGRUPOS;
	}
	
	public List<Idioma> getIdiomas() {
		return idiomas;
	}
	public void setIdiomas(List<Idioma> idiomas) {
		this.idiomas = idiomas;
	}

	public List<Tema> getTemas() {
		return temas;
	}
	public void setTemas(List<Tema> temas) {
		this.temas = temas;
	}

	public List<Curso> getCursos() {
		return cursos;
	}
	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
	public String cursoToString(int curso) {
		if (Curso.mapCURSOS.containsKey(curso)) {
			return Curso.mapCURSOS.get(curso).getNombre();
		} 
		return String.valueOf(curso);
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	public String estadoToString(int estado) {
		if (Estado.mapESTADOS.containsKey(estado)) {
			return Estado.mapESTADOS.get(estado).getNombre();
		} 
		return String.valueOf(estado);
	}

	public List<SubGrupo> getSubgrupos() {
		return subgrupos;
	}
	public void setSubgrupos(List<SubGrupo> subgrupos) {
		this.subgrupos = subgrupos;
	}
	public String subgrupoToString(String subgrupo) {
		if (SubGrupo.mapSUBGRUPOS.containsKey(subgrupo)) {
			return SubGrupo.mapSUBGRUPOS.get(subgrupo).getNombre();
		} 
		return subgrupo;
	}
}