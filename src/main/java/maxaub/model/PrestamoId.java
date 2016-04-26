package maxaub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PrestamoId
 */
@Embeddable
public class PrestamoId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idPrestamo;
	private String alumnoSocioDni;
	
	public PrestamoId() {
	}
	
	public PrestamoId(Integer idPrestamo, String alumnoSocioDni) {
		this.idPrestamo = idPrestamo;
		this.alumnoSocioDni = alumnoSocioDni;
	}
	
	@Column(name="id", columnDefinition="NUMBER(11)", precision=11, nullable=false)
	public Integer getIdPrestamo() {
		return this.idPrestamo;
	}
	public void setIdPrestamo(Integer idPrestamo) {
		this.idPrestamo = idPrestamo;
	}
	
	@Column(name="alumno_socio_DNI", columnDefinition="VARCHAR2(9 CHAR)", nullable=false, length=9)
	public String getAlumnoSocioDni() {
		return this.alumnoSocioDni;
	}
	public void setAlumnoSocioDni(String alumnoSocioDni) {
		this.alumnoSocioDni = alumnoSocioDni;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrestamoId))
			return false;
		PrestamoId castOther = (PrestamoId) other;

		return (this.getIdPrestamo() == castOther.getIdPrestamo())
				&& ((this.getAlumnoSocioDni() == castOther.getAlumnoSocioDni())
						|| (this.getAlumnoSocioDni() != null && castOther.getAlumnoSocioDni() != null
								&& this.getAlumnoSocioDni().equals(castOther.getAlumnoSocioDni())));
	}
	
	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdPrestamo();
		result = 37 * result + (getAlumnoSocioDni() == null ? 0 : this.getAlumnoSocioDni().hashCode());
		return result;
	}
}