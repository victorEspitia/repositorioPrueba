package mx.edu.uaz.vistas.metas;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.TabSheet;
import mx.edu.uaz.modelos.Usuario;

public class MetasTab extends TabSheet {

	private static final long serialVersionUID = 1L;

	public MetasTab(Usuario u){
		addTab(new MetasForm(), "Agregar", VaadinIcons.ADD_DOCK);
		addTab(new ListaMetasForm(u), "Metas", VaadinIcons.LIST);
	}

}
