package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Prestamo generated by hbm2java
 */
@Entity
@Table(name="prestamo")
public class Prestamo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PrestamoId id;
	private Alumno alumno;
	private Date fecha;
	private String tipoLote;
	private String pagado;
	private List<Lote> lotes = new ArrayList<Lote>(0);
	
	public Prestamo() {
	}
	
	public Prestamo(PrestamoId id, Alumno alumno, Date fecha, String tipoLote) {
		this.id = id;
		this.alumno = alumno;
		this.fecha = fecha;
		this.tipoLote = tipoLote;
	}
	
	public Prestamo(PrestamoId id, Alumno alumno, Date fecha, String tipoLote, String pagado, List<Lote> lotes) {
		this.id = id;
		this.alumno = alumno;
		this.fecha = fecha;
		this.tipoLote = tipoLote;
		this.pagado = pagado;
		this.lotes = lotes;
	}
	
	@EmbeddedId
	public PrestamoId getId() {
		return this.id;
	}
	public void setId(PrestamoId id) {
		this.id = id;
	}
	
	//bi-directional many-to-one association to Alumno
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="alumno", nullable=false)
	public Alumno getAlumno() {
		return this.alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	
    @Column(name="fecha", columnDefinition="DATE", nullable=false)
	public Date getFecha() {
		return this.fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="tipo_lote", columnDefinition="VARCHAR2(45 CHAR)", nullable=false, length=45)
	public String getTipoLote() {
		return this.tipoLote;
	}
	public void setTipoLote(String tipoLote) {
		this.tipoLote = tipoLote;
	}
	
	@Column(name="pagado", columnDefinition="VARCHAR2(45 CHAR)", nullable=false, length=45)
	public String getPagado() {
		return this.pagado;
	}
	public void setPagado(String pagado) {
		this.pagado = pagado;
	}
	
	//bi-directional one-to-many association to Lote
	@OneToMany(mappedBy="prestamo", fetch=FetchType.LAZY)
	public List<Lote> getLotes() {
		return this.lotes;
	}
	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}
}