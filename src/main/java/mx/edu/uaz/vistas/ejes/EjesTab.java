package mx.edu.uaz.vistas.ejes;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.TabSheet;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.vistas.usuarios.ListaUsuariosForm;
import mx.edu.uaz.vistas.usuarios.UsuarioForm;

public class EjesTab extends TabSheet {

	private static final long serialVersionUID = 1L;

	public EjesTab(Usuario u){
		setResponsive(true);
		addTab(new EjesForm(), "Agregar", VaadinIcons.ADD_DOCK);
		addTab(new ListaEjesForm(u), "Ejes", VaadinIcons.LIST);
	}

}
