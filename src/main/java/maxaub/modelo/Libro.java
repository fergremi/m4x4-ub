package maxaub.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Libro
 */
@Entity
@Table(name="librogenerico")
public class Libro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", columnDefinition="INT(11)", unique=true, nullable=false, precision=11)
	private long id;
	
	@Column(name="isbn", columnDefinition="BIGINT(13)", nullable=false, precision=11)
	private BigInteger isbn;
	
	@Column(name="titulo", columnDefinition="VARCHAR(100)", nullable=false, length=100)
	private String titulo;
	
	@Column(name="asignatura", columnDefinition="VARCHAR(45)", nullable=false, length=45)
	private String asignatura;
	
	@Column(name="curso", columnDefinition="VARCHAR(10)", nullable=false, length=10)
	private String curso;
	
	@Column(name="editorial", columnDefinition="VARCHAR(45)", nullable=false, length=45)
	private String editorial;
	
	@Column(name="año_edicion", columnDefinition="INT(4)", nullable=false)
	private int añoEdicion;
	
	@Column(name="cantidad", columnDefinition="INT(5)", nullable=false, precision=5)
	private int cantidad = 0;
	
	@Column(name="optativo", columnDefinition="TINYINT(1)", nullable=false, precision=1)
	private boolean optativo = false;
	
	@Column(name="idioma", columnDefinition="VARCHAR(45)", nullable=false, length=45)
	private String idioma;
	
	@Column(name="imagen", columnDefinition="VARCHAR(100)", nullable=true, length=100)
	private String imagen;
	
	@Column(name="activo", columnDefinition="TINYINT(1)", nullable=false, precision=1)
	private boolean activo = true;
	
	/*
	 * bi-directional
	 */
	
	//one-to-one association to Ejemplar
	@OneToMany(mappedBy="libro", fetch=FetchType.LAZY)
	private List<Ejemplar> ejemplares = new ArrayList<Ejemplar>();
	
	public Libro() {
		super();
	}
	
	public Libro(BigInteger isbn, String titulo, String asignatura, String curso, String editorial,
			int añoEdicion, int cantidad, boolean optativo, String idioma, String imagen,
			boolean activo) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.asignatura = asignatura;
		this.curso = curso;
		this.editorial = editorial;
		this.añoEdicion = añoEdicion;
		this.cantidad =  cantidad;
		this.optativo = optativo;
		this.idioma = idioma;
		this.imagen = imagen;
		this.activo = activo;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public BigInteger getIsbn() {
		return this.isbn;
	}
	public void setIsbn(BigInteger isbn) {
		this.isbn = isbn;
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getAsignatura() {
		return this.asignatura;
	}
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
	
	public String getCurso() {
		return this.curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	public String getEditorial() {
		return this.editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
		
	public int getAñoEdicion() {
        return this.añoEdicion;
    }
	public void setAñoEdicion(int añoEdicion) {
        this.añoEdicion = añoEdicion;
    }
	
	public int getCantidad() {
		return this.cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public boolean isOptativo() {
		return optativo;
	}
	public void setOptativo(boolean optativo) {
		this.optativo = optativo;
	}

	public String getIdioma() {
		return this.idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	public String getImagen() {
		return this.imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public boolean getActivo() {
		return this.activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	/*
	 * bi-directional
	 */
	
	public List<Ejemplar> getEjemplares() {
		return this.ejemplares;
	}
	public void setEjemplares(List<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}
}