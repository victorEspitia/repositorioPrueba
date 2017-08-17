package mx.edu.uaz.vistas.usuarios;

import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import mx.edu.uaz.accesodatos.ADUsuario;
import mx.edu.uaz.modelos.Usuario;
import org.vaadin.dialogs.ConfirmDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaUsuariosForm extends VerticalLayout {
	private Grid<Usuario> grid;
	private ADUsuario adUsuario;
	private Usuario usuario;
	private Button btnEliminar;
	
	@SuppressWarnings("unchecked")
	public ListaUsuariosForm(){

		grid = new Grid<Usuario>(Usuario.class);
		grid.setStyleName("mi-grid");
		adUsuario = new ADUsuario();
		grid.setItems(adUsuario.obtenUsuarios());
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setColumns("nombre", "apellidos", "nombre_comisionado", "correo","cargo");
		grid.getColumn("nombre_comisionado").setCaption("Comisión");
		grid.addColumn(person -> "Editar",
			      new ButtonRenderer(clickEvent -> {
			    	  TabSheet tab = (TabSheet) this.getParent();
			    	  UsuarioForm lu = (UsuarioForm) tab.getTab(0).getComponent();
			    	  usuario = (Usuario) clickEvent.getItem();
			    	  lu.enlazarExterno(usuario);
			    	  tab.setSelectedTab(0);
			    }));

		grid.addSelectionListener((SelectionListener<Usuario>) event -> {
            if (!event.getAllSelectedItems().isEmpty()){
                usuario = event.getFirstSelectedItem().get();

            }
        });

		grid.setSizeFull();

		btnEliminar = new Button("Eliminar", VaadinIcons.TRASH);
		btnEliminar.setStyleName(ValoTheme.BUTTON_DANGER);
		btnEliminar.addClickListener(new EliminarUsuario());
		
		addComponents(grid, btnEliminar);
	
	}
	public void llenaGrid(){
		grid.setItems(adUsuario.obtenUsuarios());
	}
	class EliminarUsuario implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if (!grid.getSelectedItems().isEmpty()){
				ConfirmDialog.show(
					UI.getCurrent(),
					"Confirmar eliminación:", 
					"¿Deseas relamente eliminar los registros?",
				    "Eliminar", "Cancelar", 
				    new ConfirmDialog.Listener() {

				        public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			                	Set<Usuario> usuarios = grid.getSelectedItems();
								List<Usuario> users = new ArrayList<Usuario>();
								users.addAll(usuarios);
								ADUsuario adUsuario = new ADUsuario();
								boolean ok = adUsuario.eliminarUsuarios(users);
								if (ok){
									grid.setItems(adUsuario.obtenUsuarios());
									Notification.show("Registros eliminados...", Notification.Type.WARNING_MESSAGE);
								}
				              } 
				            }
				        });
			}
			else
				Notification.show("Selecciona al menos un usuario para eliminar", Notification.Type.WARNING_MESSAGE);
		}
			
	}
	
}
