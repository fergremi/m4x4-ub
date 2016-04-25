package es.ine.iria.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class LiteralAplicacionIdiomaPK implements Serializable{

	private String liapCod;
	private String idioCod;
	private String applCod;
	private String idapApplCod;


	public LiteralAplicacionIdiomaPK() {
		super();
	}
	
	@Column(name="LIAI_IDIO_COD", length=8,  columnDefinition="VARCHAR2 (8 CHAR)", nullable=false)
	public String getIdioCod() {
		return idioCod;
	}
	public void setIdioCod(String idioCod) {
		this.idioCod = idioCod;
	}
	
	@Column(name="LIAI_LIAP_COD", length=255,  columnDefinition="VARCHAR2 (255 CHAR)", nullable=false)
	public String getLiapCod() {
		return liapCod;
	}
	public void setLiapCod(String liapCod) {
		this.liapCod = liapCod;
	}
	
	@Column(name="LIAI_APPL_COD", length=20,  columnDefinition="VARCHAR2 (20 CHAR)", nullable=false)
	public String getApplCod() {
		return applCod;
	}
	public void setApplCod(String applCod) {
		this.applCod = applCod;
	}

	@Column(name="LIAI_IDAP_APPL_COD", length=20, columnDefinition="VARCHAR2 (20 CHAR)", nullable=false)
	public String getIdapApplCod() {
		return idapApplCod;
	}
	public void setIdapApplCod(String idapApplCod) {
		this.idapApplCod = idapApplCod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getApplCod() == null) ? 0 : getApplCod().hashCode());
		result = prime * result
				+ ((getIdapApplCod() == null) ? 0 : getIdapApplCod().hashCode());
		result = prime * result + ((getIdioCod() == null) ? 0 : getIdioCod().hashCode());
		result = prime * result + ((getLiapCod() == null) ? 0 : getLiapCod().hashCode());
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
		LiteralAplicacionIdiomaPK other = (LiteralAplicacionIdiomaPK) obj;
		if (getApplCod() == null) {
			if (other.getApplCod() != null)
				return false;
		} else if (!getApplCod().equals(other.getApplCod()))
			return false;
		if (getIdapApplCod() == null) {
			if (other.getIdapApplCod() != null)
				return false;
		} else if (!getIdapApplCod().equals(other.getIdapApplCod()))
			return false;
		if (getIdioCod() == null) {
			if (other.getIdioCod() != null)
				return false;
		} else if (!getIdioCod().equals(other.getIdioCod()))
			return false;
		if (getLiapCod() == null) {
			if (other.getLiapCod() != null)
				return false;
		} else if (!getLiapCod().equals(other.getLiapCod()))
			return false;
		return true;
	}
	
}
