package util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class Utils {
	private static final String baseName = "traduccion.maxaub";

	public static Locale getLocale() {
		Locale locale = null;
		if ((FacesContext.getCurrentInstance() != null) && (FacesContext.getCurrentInstance().getViewRoot() != null)) {
			locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			if (locale == null) {
				FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
			}
		}
		return locale;
	}

	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(baseName, getLocale());
	}

	public static String paramMsg(String msgKey, String paramValue) {
		MessageFormat messageFormat = new MessageFormat(getResourceBundle().getString(msgKey));
		Object[] args = {paramValue};
		return messageFormat.format(args);
	}
}