package maxaub.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class BaseInfoController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(BaseInfoController.class);

	/**
	 * Cursos
	 */
	private Map<String, String> cursosActuales;
	private List<SelectItem> cursosActualesListItems;

	private Map<String, String> cursosFuturos;
	private List<SelectItem> cursosFuturosListItems;
	
	/**
	 * Subgrupos
	 */
	private static List<String> subgrupos;
	private List<SelectItem> subgruposListItems;
	
	private String clave2;

	public BaseInfoController() {
		// TODO res['']
		cursosActuales = new HashMap<String, String>();
		cursosActuales.put("3º", "Tercero");
		cursosActuales.put("4º", "Cuarto");
		cursosActuales.put("5º", "Quinto");
		cursosActuales.put("6º", "Sexto");

		// TODO res['']
		cursosFuturos = new HashMap<String, String>();
		cursosFuturos.put("3º", "tercero");
		cursosFuturos.put("4º", "cuarto");
		cursosFuturos.put("5º", "quinto");
		cursosFuturos.put("6º", "sexto");
		
		// TODO res['']
		subgrupos = Arrays.asList("es", "va");
		
		clave2 = null;
	}

	public List<SelectItem> getCursosActualesListItems() {
		if (cursosActualesListItems == null) {
			cursosActualesListItems = new ArrayList<SelectItem>();
			try {
				Iterator<String> it = cursosActuales.keySet().iterator();
				while(it.hasNext()){
					String key = (String) it.next();
					log.info("Clave: " + key + " -> Valor: " + cursosActuales.get(key));
					cursosActualesListItems.add(new SelectItem(key, cursosActuales.get(key)));
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return cursosActualesListItems;
	}
	
	public List<SelectItem> getCursosFuturosListItems() {
		if (cursosFuturosListItems == null) {
			cursosFuturosListItems = new ArrayList<SelectItem>();
			try {
				Iterator<String> it = cursosFuturos.keySet().iterator();
				while(it.hasNext()){
					String key = (String) it.next();
					log.info("Clave: " + key + " -> Valor: " + cursosFuturos.get(key));
					cursosFuturosListItems.add(new SelectItem(key, cursosFuturos.get(key)));
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return cursosFuturosListItems;
	}

	public List<SelectItem> getSubgruposListItems() {
		if (subgruposListItems == null) {
			subgruposListItems = new ArrayList<SelectItem>();
			try {
				for (String subgrupo : subgrupos) {
					log.info("Subgrupo: " + subgrupo);
					subgruposListItems.add(new SelectItem(subgrupo, subgrupo));
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return subgruposListItems;
	}

    public String getClave2() {
		return clave2;
	}
	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}
	
	public String doRegistro() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "ok"));
		return "success";
	}
}