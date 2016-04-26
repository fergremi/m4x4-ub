package maxaub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Idioma
 */
@Entity
@Table(name="idioma")
public class Idioma implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String cod;
	private String descripcion;
	
    public Idioma() {
    	super();
    }
    
    public Idioma(String cod, String descripcion) {
    	this.cod = cod;
    	this.descripcion = descripcion;
    }

	@Id
	@Column(name="IDIO_COD", columnDefinition="VARCHAR2(8 CHAR)", unique=true, nullable=false, length=8)
	public String getCod() {
		return this.cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	@Column(name="IDIO_DESC", columnDefinition="VARCHAR2(50 CHAR)", nullable=false, length=50)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getCod() == null) ? 0 : getCod().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Idioma other = (Idioma) obj;
		if (getCod() == null) {
			if (other.getCod() != null)
				return false;
		} else if (!getCod().equals(other.getCod()))
			return false;
		return true;
	}
	
	public String toString(){
		if (getDescripcion() != null){
			return getDescripcion();
		}
		return super.toString();
	}
}