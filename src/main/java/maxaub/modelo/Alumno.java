package maxaub.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Alumno
 */
@Entity
@Table(name="alumno")
public class Alumno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition="INT(11)", unique=true, nullable=false, precision=11)
	private long id;
	
	@Column(name="nombre", columnDefinition="VARCHAR(15)", nullable=false, length=15)
	private String nombre;
	
	@Column(name="apellidos", columnDefinition="VARCHAR(25)", nullable=false, length=25)
	private String apellidos;
	
	@Column(name="edad", columnDefinition="INT(2)", nullable=false, precision=2)
	private int edad;
	
	@Column(name="curso_actual", columnDefinition="INT(1)", nullable=false, precision=1)
	private int cursoActual;
	
	@Column(name="curso_futuro", columnDefinition="INT(1)", nullable=false, precision=1)
	private int cursoFuturo;
	
	@Column(name="subgrupo", columnDefinition="VARCHAR(10)", nullable=false, length=10)
	private String subgrupo;
	
	@Column(name="optativas", columnDefinition="TINYINT(1)", nullable=false, precision=1)
	private boolean optativas;
	
	@Column(name="activo", columnDefinition="TINYINT(1)", nullable=false, precision=1)
	private boolean activo;
	
	//uni-directional many-to-one association to Socio
	@ManyToOne(fetch=FetchType.LAZY)
	@Column(name="socio_id", columnDefinition="INT(11)", nullable=false, precision=11)
	private Socio socio;
	
	public Alumno() {
		super();
	}
	
	public Alumno(String nombre, String apellidos, int edad, int cursoActual, int cursoFuturo, String subgrupo,
			boolean optativas, boolean activo, Socio socio) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.cursoActual = cursoActual;
		this.cursoFuturo = cursoFuturo;
		this.subgrupo = subgrupo;
		this.optativas = optativas;
		this.activo = activo;
		this.socio = socio;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return this.apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public int getEdad() {
		return this.edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	public int getCursoActual() {
		return this.cursoActual;
	}
	public void setCursoActual(int cursoActual) {
		this.cursoActual = cursoActual;
	}
	
	public int getCursoFuturo() {
		return this.cursoFuturo;
	}
	public void setCursoFuturo(int cursoFuturo) {
		this.cursoFuturo = cursoFuturo;
	}
    
	public String getSubgrupo() {
		return this.subgrupo;
	}
	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}
    
	public boolean getOptativas() {
		return this.optativas;
	}
	public void setOptativas(boolean optativas) {
		this.optativas = optativas;
	}
	
	public boolean getActivo() {
		return this.activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public Socio getSocio() {
		return this.socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
}