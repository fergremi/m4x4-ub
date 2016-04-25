package es.ine.iria.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class LiteralAplicacionPK implements Serializable{
	
	private String apliCod;
	private String cod;
	
	public LiteralAplicacionPK() {
		super();
	}

	@Column(name="LIAP_APPL_COD", columnDefinition="VARCHAR2(20 CHAR)", length=20, insertable=true, nullable=false)
	public String getApliCod() {
		return apliCod;
	}

	public void setApliCod(String apliCod) {
		this.apliCod = apliCod;
	}

	
	@Column(name="LIAP_COD", columnDefinition="VARCHAR2(255 CHAR)", length=255, insertable=true, nullable=false)
	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getApliCod() == null) ? 0 : getApliCod().hashCode());
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
		LiteralAplicacionPK other = (LiteralAplicacionPK) obj;
		if (getApliCod() == null) {
			if (other.getApliCod() != null)
				return false;
		} else if (!getApliCod().equals(other.getApliCod()))
			return false;
		if (getCod() == null) {
			if (other.getCod() != null)
				return false;
		} else if (!getCod().equals(other.getCod()))
			return false;
		return true;
	}

}
