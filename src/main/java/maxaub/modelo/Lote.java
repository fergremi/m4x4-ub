package maxaub.modelo;

import java.io.Serializable;
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
 * Lote
 */
@Entity
@Table(name="lote")
public class Lote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", columnDefinition="INT(11)", unique=true, nullable=false, precision=11)
	private long id;
	
	@Column(name="cod", columnDefinition="INT(11)", unique=true, nullable=false, precision=11)
	private long cod;
	
	//uni-directional one-to-many association to Libro
	@OneToMany(fetch=FetchType.LAZY)
	@Column(name="libro_id", columnDefinition="INT(11)", nullable=false, precision=11)
	private List<Libro> libros;
	
	public Lote() {
	}
	
	public Lote(long cod, List<Libro> libros) {
		this.cod = cod;
		this.libros = libros;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCod() {
		return this.cod;
	}
	public void setCod(long cod) {
		this.cod = cod;
	}
	
	public List<Libro> getLibros() {
		return this.libros;
	}
	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}
}