package maxaub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Lote
 */
@Entity
@Table(name="lote")
public class Lote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LoteId id;
	private Libro libro;
	private Prestamo prestamo;
	private String estado;
	
	public Lote() {
	}
	
	public Lote(LoteId id, Libro libro, Prestamo prestamo, String estado) {
		this.id = id;
		this.libro = libro;
		this.prestamo = prestamo;
		this.estado = estado;
	}
	
	@EmbeddedId
	public LoteId getId() {
		return this.id;
	}
	public void setId(LoteId id) {
		this.id = id;
	}
	
	//bi-directional many-to-one association to Libro
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="libro", nullable=false)
	public Libro getLibro() {
		return this.libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	
	//bi-directional many-to-one association to Prestamo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "prestamo_id", referencedColumnName = "id", nullable = false),
		@JoinColumn(name = "prestamo_alumno_socio_DNI", referencedColumnName = "alumno_socio_DNI", nullable = false)
	})
	public Prestamo getPrestamo() {
		return this.prestamo;
	}
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}
	
	@Column(name="estado", columnDefinition="VARCHAR2(45 CHAR)", nullable=false, length=45)
	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}