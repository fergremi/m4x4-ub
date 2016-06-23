package util;

import maxaub.modelo.Lote;

/**
 * Clase contenedora del Lote que indica si ha sido asignado a un alumno.
 */
public class LoteWrapper {
	private Lote lote;
	private Boolean prestado;

	public LoteWrapper() {
		super();
	}

	public LoteWrapper(Lote lote, Boolean prestado) {
		super();
		this.lote = lote;
		this.prestado = prestado;
	}

	public Lote getLote() {
		return lote;
	}
	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Boolean getPrestado() {
		return prestado;
	}
	public void setPrestado(Boolean prestado) {
		this.prestado = prestado;
	}
}