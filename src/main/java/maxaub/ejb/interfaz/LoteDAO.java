package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Lote;

@Local
public interface LoteDAO {
	/**
	 * Obtener todos los lotes.
	 * 
	 * @return {@link List}<{@link Lote}>
	 */
	public List<Lote> getLotes();
    
	/**
	 * Obtener lote.
	 * 
	 * @param idLote
	 * @return {@link Lote}
	 */
	public Lote getLote(int idLote);
    
	/**
	 * Crear lote.
	 * 
	 * @param lote
	 */
	public void crearLote(Lote lote);
	
	/**
	 * Almacenar lote.
	 * 
	 * @param lote
	 */
	public void guardarLote(Lote lote);
    
	/**
	 * Eliminar lote.
	 * 
	 * @param lote
	 */
	public void eliminarLote(Lote lote);

	/**
	 * Obtener el siguiente código disponible para un lote.
	 * 
	 * @return {@link Long}
	 */
	public Long getNextCodLote();
}