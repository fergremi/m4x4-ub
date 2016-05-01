package maxaub.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Idioma
 */
@Entity
@Table(name="idioma")
public class Idioma implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="cod", columnDefinition="VARCHAR(8)", unique=true, nullable=false, length=8)
	private String cod;
	
	@Column(name="descripcion", columnDefinition="VARCHAR(50)", nullable=false, length=50)
	private String descripcion;
	
	/*
	 * bi-directional
	 */
	
	//one-to-many association to Literal
	@OneToMany(mappedBy="idioma", fetch=FetchType.LAZY)
	private List<Literal> literales;
	
    public Idioma() {
    }
    
    public Idioma(String cod, String descripcion) {
    	this.cod = cod;
    	this.descripcion = descripcion;
    }
    
	public String getCod() {
		return this.cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Literal> getLiterales() {
		return this.literales;
	}
	public void setLiterales(List<Literal> literales) {
		this.literales = literales;
	}
}