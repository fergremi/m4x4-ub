package es.ine.iria.db;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedNativeQueries({
    @NamedNativeQuery(name="literalAplicacion_Insert", query="insert into literales_aplicaciones values (?1,?2,?3)"),
    @NamedNativeQuery(name="literalAplicacion_Delete", query="delete from literales_aplicaciones where liap_cod = ?1 and liap_appl_cod = ?2")
})

@NamedQueries({
	@NamedQuery(name="literalAplicacion_FindAll", query="SELECT la FROM LiteralAplicacion AS la ORDER BY la.id.cod"),
	@NamedQuery(name="literalAplicacion_DeleteByCod", query="DELETE FROM LiteralAplicacion AS la WHERE la.id.cod = :cod")
})
@Entity
@Table(name="LITERALES_APLICACIONES")
public class LiteralAplicacion {
	
	private LiteralAplicacionPK id;
	private String etiqueta;
	private Aplicacion aplicacion;
	private List<LiteralAplicacionIdioma> literalesAplicacionesIdiomas;
	
	
	public LiteralAplicacion() {
		super();
	}
	
	@EmbeddedId
	public LiteralAplicacionPK getId() {
		return id;
	}
	public void setId(LiteralAplicacionPK id) {
		this.id = id;
	}

	@Column(name="LIAP_ETIQUETA", columnDefinition="VARCHAR2(255 CHAR)", length=255, insertable=true, nullable=true, updatable=true)
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="LIAP_APPL_COD", columnDefinition="VARCHAR2(20 CHAR)", referencedColumnName="APPL_COD", nullable=false)
	public Aplicacion getAplicacion() {
		return aplicacion;
	}
	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	@OneToMany(mappedBy="literalAplicacion", fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	public List<LiteralAplicacionIdioma> getLiteralesAplicacionesIdiomas() {
		return literalesAplicacionesIdiomas;
	}
	public void setLiteralesAplicacionesIdiomas(
			List<LiteralAplicacionIdioma> literalesAplicacionesIdiomas) {
		this.literalesAplicacionesIdiomas = literalesAplicacionesIdiomas;
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
		LiteralAplicacion other = (LiteralAplicacion) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
