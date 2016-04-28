package maxaub.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Alumno
 */
@Entity
@Table(name="alumno")
public class Alumno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idAlumno;
	private String socioDni;
	private Socio socio;
	private String nombre;
	private String apellidos;
	private Integer edad;
	private Date cursoActual;
	private Date cursoFuturo;
	private String subgrupo;
	private String optativas;
	private List<Prestamo> prestamos = new ArrayList<Prestamo>();
	
	public Alumno() {
	}
	
	public Alumno(Socio socio, String nombre, String apellidos, Integer edad, Date cursoActual, String subgrupo,
			String optativas) {
		this.socio = socio;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.cursoActual = cursoActual;
		this.subgrupo = subgrupo;
		this.optativas = optativas;
	}
	
	public Alumno(Socio socio, String nombre, String apellidos, Integer edad, Date cursoActual, Date cursoFuturo,
			String subgrupo, String optativas, List<Prestamo> prestamos) {
		this.socio = socio;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.cursoActual = cursoActual;
		this.cursoFuturo = cursoFuturo;
		this.subgrupo = subgrupo;
		this.optativas = optativas;
		this.prestamos = prestamos;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", columnDefinition="NUMBER(19)", unique=true, nullable=false, precision=19)
	public Integer getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}
	
    @Column(name="socio_DNI", columnDefinition="VARCHAR2(9 CHAR)", nullable=false, length=9)
	public String getSocioDni() {
		return this.socioDni;
	}
	public void setSocioDni(String socioDni) {
		this.socioDni = socioDni;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="socio", nullable=false, insertable=true, updatable=true)
	public Socio getSocio() {
		return this.socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	
    @Column(name="nombre", columnDefinition="VARCHAR2(16 CHAR)", nullable=false, length=16)
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
    @Column(name="apellidos", columnDefinition="VARCHAR2(25 CHAR)", nullable=false, length=25)
	public String getApellidos() {
		return this.apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
    @Column(name="edad", columnDefinition="NUMBER(11)", nullable=false, precision=11)
	public Integer getEdad() {
		return this.edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	
    @Column(name="curso_actual", columnDefinition="DATE", nullable=false)
	public Date getCursoActual() {
		return this.cursoActual;
	}
	public void setCursoActual(Date cursoActual) {
		this.cursoActual = cursoActual;
	}
	
    @Column(name="curso_futuro", columnDefinition="DATE", nullable=false)
	public Date getCursoFuturo() {
		return this.cursoFuturo;
	}
	public void setCursoFuturo(Date cursoFuturo) {
		this.cursoFuturo = cursoFuturo;
	}
    
	@Column(name="subgrupo", columnDefinition="VARCHAR2(10 CHAR)", nullable=false, length=10)
	public String getSubgrupo() {
		return this.subgrupo;
	}
	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}
    
	@Column(name="optativas", columnDefinition="VARCHAR2(45 CHAR)", nullable=false, length=45)
	public String getOptativas() {
		return this.optativas;
	}
	public void setOptativas(String optativas) {
		this.optativas = optativas;
	}
	
	//bi-directional one-to-many association to Prestamo
	@OneToMany(mappedBy="alumno", fetch=FetchType.LAZY)
	public List<Prestamo> getPrestamos() {
		return this.prestamos;
	}
	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
}