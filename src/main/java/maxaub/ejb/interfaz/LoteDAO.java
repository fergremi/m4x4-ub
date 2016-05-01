package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.modelo.Lote;

@Local
public interface LoteDAO {
	/**
	 * Obtener todos los lotes.
	 * 
	 * @return List<Lote>
	 */
	public List<Lote> getLotes();
    
	/**
	 * Obtener lote.
	 * 
	 * @param idLote
	 * @return Lote
	 */
	public Lote getLote(int idLote);
    
	/**
	 * Almacenar lote.
	 * 
	 * @param Lote
	 */
	public void guardarLote(Lote lote);
    
	/**
	 * Eliminar lote.
	 * 
	 * @param Lote
	 */
	public void eliminarLote(Lote lote);
}