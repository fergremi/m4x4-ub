package maxaub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * LoteId
 */
@Embeddable
public class LoteId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer prestamoIdPrestamo;
	private String prestamoAlumnoSocioDni;
	private Integer libroIsbn;
	
	public LoteId() {
	}
	
	public LoteId(Integer prestamoIdPrestamo, String prestamoAlumnoSocioDni, Integer libroIsbn) {
		this.prestamoIdPrestamo = prestamoIdPrestamo;
		this.prestamoAlumnoSocioDni = prestamoAlumnoSocioDni;
		this.libroIsbn = libroIsbn;
	}
	
	@Column(name="prestamo_id", columnDefinition="NUMBER(11)", precision=11, insertable=true, nullable=true, updatable=true)
	public Integer getPrestamoIdPrestamo() {
		return this.prestamoIdPrestamo;
	}
	public void setPrestamoIdPrestamo(Integer prestamoIdPrestamo) {
		this.prestamoIdPrestamo = prestamoIdPrestamo;
	}
	
	@Column(name="prestamo_alumno_socio_DNI", columnDefinition="VARCHAR2(9 CHAR)", nullable=false, length=9, insertable=true, updatable=true)
	public String getPrestamoAlumnoSocioDni() {
		return this.prestamoAlumnoSocioDni;
	}
	public void setPrestamoAlumnoSocioDni(String prestamoAlumnoSocioDni) {
		this.prestamoAlumnoSocioDni = prestamoAlumnoSocioDni;
	}
	
	@Column(name="libro_ISBN", columnDefinition="NUMBER(11)", precision=11, insertable=true, nullable=true, updatable=true)
	public Integer getLibroIsbn() {
		return this.libroIsbn;
	}
	public void setLibroIsbn(Integer libroIsbn) {
		this.libroIsbn = libroIsbn;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LoteId))
			return false;
		LoteId castOther = (LoteId) other;

		return (this.getPrestamoIdPrestamo() == castOther.getPrestamoIdPrestamo())
				&& ((this.getPrestamoAlumnoSocioDni() == castOther.getPrestamoAlumnoSocioDni())
						|| (this.getPrestamoAlumnoSocioDni() != null && castOther.getPrestamoAlumnoSocioDni() != null
								&& this.getPrestamoAlumnoSocioDni().equals(castOther.getPrestamoAlumnoSocioDni())))
				&& (this.getLibroIsbn() == castOther.getLibroIsbn());
	}
	
	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPrestamoIdPrestamo();
		result = 37 * result + (getPrestamoAlumnoSocioDni() == null ? 0 : this.getPrestamoAlumnoSocioDni().hashCode());
		result = 37 * result + this.getLibroIsbn();
		return result;
	}
}