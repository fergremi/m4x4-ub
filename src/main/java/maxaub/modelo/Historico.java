package maxaub.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Histórico
 */
@Entity
@Table(name="historico")
public class Historico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", columnDefinition="INT(11)", unique=true, nullable=false, precision=11)
	private long id;
	
	@Column(name="fecha", columnDefinition="DATE", nullable=false)
	private Date fecha;
	
	//uni-directional one-to-many association to Lote
	@OneToOne(fetch=FetchType.LAZY)
	@Column(name="lote_id", columnDefinition="INT(11)", nullable=false, precision=11)
	private Lote lote;
	
    public Historico() {
    	super();
    }
    
    public Historico(Date fecha, Lote lote) {
    	super();
    	this.fecha = fecha;
    	this.lote = lote;
    }
    
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Lote getLote() {
		return this.lote;
	}
	public void setLote(Lote lote) {
		this.lote = lote;
	}
}