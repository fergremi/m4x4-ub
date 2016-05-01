package maxaub.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Estado
 */
@Entity
@Table(name="estado")
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", columnDefinition="INT(11)", unique=true, nullable=false, precision=11)
	private int id;
	
	@Column(name="estado", columnDefinition="INT(1)", nullable=false, precision=1)
	private int estado;
	
	//bi-directional one-to-one association to Libro
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="libro", nullable=false)
	private Libro libro;
	
	public Estado() {
	}
	
	public Estado(int estado, Libro libro) {
		this.estado = estado;
		this.libro = libro;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
		
	public int getEstado() {
		return this.estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public Libro getLibro() {
		return this.libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
}