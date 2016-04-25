package es.ine.iria.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedNativeQueries({
    @NamedNativeQuery(name="literalAplicacionIdioma_Insert", query="insert into literales_aplicaciones_idiomas values (?1,?2,?3,?4,?5)"),
    @NamedNativeQuery(name="literalAplicacionIdioma_Select", query="select liai_idio_cod, liai_etiqueta from literales_aplicaciones_idiomas where liai_liap_cod = ?1 and liai_appl_cod = ?2 and liai_idap_appl_cod = ?3"),
    @NamedNativeQuery(name="literalAplicacionIdioma_Delete", query="delete from literales_aplicaciones_idiomas where liai_liap_cod = ?1 and liai_appl_cod = ?2 and liai_idap_appl_cod = ?3")
})

@NamedQueries({
	@NamedQuery(name="literalAplicacionIdioma_FindByLiteralAplicacion", query="SELECT lai FROM LiteralAplicacionIdioma AS lai WHERE lai.literalAplicacion.id.cod = :literalApliCod"),
	@NamedQuery(name="literalAplicacionIdioma_FindByAplicacion", query="SELECT lai FROM LiteralAplicacionIdioma AS lai WHERE lai.id.applCod = :applCod"),
	@NamedQuery(name="literalAplicacionIdioma_DeleteByCod", query="DELETE FROM LiteralAplicacionIdioma AS lai WHERE lai.id.liapCod = :liapCod")
})

@SuppressWarnings("serial")
@Entity
@Table(name="LITERALES_APLICACIONES_IDIOMAS")
public class LiteralAplicacionIdioma implements Serializable {

	private LiteralAplicacionIdiomaPK id;
	private String etiqueta;
	
	private LiteralAplicacion literalAplicacion; 
	private AplicacionIdioma aplicacionIdioma;
	
	public LiteralAplicacionIdioma() {
		super();
	}

	@EmbeddedId
	public LiteralAplicacionIdiomaPK getId() {
		return id;
	}
	
	public void setId(LiteralAplicacionIdiomaPK id) {
		this.id = id;
	}
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="LIAI_IDAP_APPL_COD", referencedColumnName="IDAP_APPL_COD", nullable=false, insertable=true),
		@JoinColumn(name="LIAI_IDIO_COD", referencedColumnName="IDAP_IDIO_COD", nullable=false, insertable=true)
	})
	public AplicacionIdioma getAplicacionIdioma() {
		return aplicacionIdioma;
	}

	public void setAplicacionIdioma(AplicacionIdioma aplicacionIdioma) {
		this.aplicacionIdioma = aplicacionIdioma;
	}

	@Column(name="LIAI_ETIQUETA", length=255,  columnDefinition="VARCHAR2 (255 CHAR)", updatable=true, insertable=true, nullable=true)
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name="LIAI_LIAP_COD", referencedColumnName="LIAP_COD", nullable=false, insertable=true),
		@JoinColumn(name="LIAI_APPL_COD", referencedColumnName="LIAP_APPL_COD", nullable=false, insertable=true)
	})
	public LiteralAplicacion getLiteralAplicacion() {
		return literalAplicacion;
	}
	public void setLiteralAplicacion(LiteralAplicacion literalAplicacion) {
		this.literalAplicacion = literalAplicacion;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
		LiteralAplicacionIdioma other = (LiteralAplicacionIdioma) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	

}
