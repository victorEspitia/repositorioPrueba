package mx.edu.uaz.vistas.indicadores;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.TabSheet;
import mx.edu.uaz.modelos.Usuario;

public class IndicadoresTab extends TabSheet {

	private static final long serialVersionUID = 1L;

	public IndicadoresTab(Usuario u){
		addTab(new IndicadoresForm(), "Agregar", VaadinIcons.ADD_DOCK);
		addTab(new ListaIndicadoresForm(u), "Indicadores", VaadinIcons.LIST);
	}

}
