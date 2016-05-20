package maxaub.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Prestamo
 */
@Entity
@Table(name="prestamo")
public class Prestamo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", columnDefinition="INT(11)", unique=true, nullable=false, precision=11)
	private long id;
	
	@Column(name="fecha", columnDefinition="DATE", nullable=false)
	private Date fecha;
	
	@Column(name="pagado", columnDefinition="TINYINT(1)", nullable=false, precision=1)
	private boolean pagado;
	
	//uni-directional many-to-one association to Alumno
	@ManyToOne(fetch=FetchType.LAZY)
	@Column(name="alumno_id", columnDefinition="INT(11)", nullable=false, precision=11)
	private Alumno alumno;
	
	//bi-directional one-to-one association to Lote
	@OneToOne(fetch=FetchType.LAZY)
	@Column(name="lote_cod", columnDefinition="INT(11)", unique=true, nullable=false, precision=11)
	private Lote lote;
	
	public Prestamo() {
		super();
	}
	
	public Prestamo(Date fecha, boolean pagado, Alumno alumno, Lote lote) {
		super();
		this.fecha = fecha;
		this.pagado = pagado;
		this.alumno = alumno;
		this.lote = lote;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getFecha() {
		return this.fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean getPagado() {
		return this.pagado;
	}
	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public Alumno getAlumno() {
		return this.alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	
	public Lote getLote() {
		return this.lote;
	}
	public void setLote(Lote lote) {
		this.lote = lote;
	}
}