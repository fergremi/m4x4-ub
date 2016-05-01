package maxaub.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Literal
 */
@Entity
@Table(name="literal")
public class Literal implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", columnDefinition="INT(11)", unique=true, nullable=false, precision=11)
	private Long id;
	
	@Column(name="literal", columnDefinition="VARCHAR(25)", nullable=false, length=50)
	private String literal;
	
	@Column(name="traduccion", columnDefinition="MEDIUMTEXT", nullable=false, length=50)
	private String traduccion;
	
	//bi-directional many-to-one association to Idioma
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idioma_cod", nullable=false )
	//@Column(name="idioma", columnDefinition="VARCHAR(8)", nullable=false, length=8)
	private Idioma idioma;
	
    public Literal() {
    }
    
    public Literal(Idioma idioma, String literal, String traduccion) {
    	this.idioma = idioma;
    	this.literal = literal;
    	this.traduccion = traduccion;
    }
    
	public Long getId() {
		return this.id;
	}
	public void setIdLiteral(Long id) {
		this.id = id;
	}
	
	public String getLiteral() {
		return this.literal;
	}
	public void setLiteral(String literal) {
		this.literal = literal;
	}
	
	public String getTraduccion() {
		return this.traduccion;
	}
	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}
	
	public Idioma getIdioma() {
		return this.idioma;
	}
	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}
}