package mx.edu.uaz.vistas.usuarios;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.TabSheet;
import mx.edu.uaz.modelos.Usuario;

public class UsuariosTab extends TabSheet {

	private static final long serialVersionUID = 1L;

	public UsuariosTab(Usuario user){
		setWidth("100%");
		UsuarioForm uf = new UsuarioForm(user);
		uf.setWidth("100%");
		addTab(uf, "Agregar", VaadinIcons.ADD_DOCK);
		addTab(new ListaUsuariosForm(), "Usuarios", VaadinIcons.LIST);
	}

}
