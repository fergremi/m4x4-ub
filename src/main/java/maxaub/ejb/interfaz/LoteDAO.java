package maxaub.ejb.interfaz;

import java.util.List;

import javax.ejb.Local;

import maxaub.model.Lote;

@Local
public interface LoteDAO {
    public List<Lote> getLotes();
    public Lote getLote(int idLote);
    public void guardarLote(Lote lote);
    public void eliminarLote(Lote lote);
}