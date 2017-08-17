package mx.edu.uaz.utils;

import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;

public class BuscaComponentes {
	public static Component findComponentById(HasComponents root, String id) {
	    for (Component child : root) {
	        if (id.equals(child.getId())) {
	            return child; // found it!
	        } else if (child instanceof HasComponents) { // recursively go through all children that themselves have children
	            Component result = findComponentById((HasComponents) child, id);
	            if (result != null) {
	                return result;
	            }
	        }
	    }
	    return null; // none was found
	}

}
