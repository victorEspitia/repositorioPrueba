package mx.edu.uaz.vistas.ejes;

import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.MessageBox;
import mx.edu.uaz.accesodatos.ADEje;
import mx.edu.uaz.accesodatos.ADUsuario;
import mx.edu.uaz.modelos.Eje;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.utils.Hash;
import mx.edu.uaz.vistas.usuarios.UsuarioForm;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaEjesForm extends VerticalLayout {
	private Grid<Eje> grid;
	private ADEje adEje;
	private Eje eje;
	private Button btnEliminar;
	private Usuario usuarioActual;

	@SuppressWarnings("unchecked")
	public ListaEjesForm(Usuario u){
		setSizeFull();
		setResponsive(true);
		usuarioActual = u;

		
		grid = new Grid<Eje>(Eje.class);
		grid.setStyleName("mi-grid");
		adEje = new ADEje();
		grid.setItems(adEje.obtenEjes());
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setColumns("nombre", "descripcion");
		grid.getColumn("descripcion").setCaption("Descripción");
		grid.addColumn(person -> "Editar",
			      new ButtonRenderer(clickEvent -> {
			    	  TabSheet tab = (TabSheet) this.getParent();
			    	  EjesForm lu = (EjesForm) tab.getTab(0).getComponent();
			    	  eje = (Eje) clickEvent.getItem();
			    	  lu.enlazarExterno(eje);
			    	  tab.setSelectedTab(0);
			    }));
		grid.setResponsive(true);
		grid.setWidth("100%");
		grid.addSelectionListener((SelectionListener<Eje>) event -> {

            if (!event.getAllSelectedItems().isEmpty()){
                eje = event.getFirstSelectedItem().get();

            }
        });

		btnEliminar = new Button("Eliminar", VaadinIcons.TRASH);
		btnEliminar.setStyleName(ValoTheme.BUTTON_DANGER);
		btnEliminar.addClickListener(new EliminarEje());
		
		addComponents(grid, btnEliminar);
	
	}
	public void llenaGrid(){
		grid.setItems(adEje.obtenEjes());
	}

	class EliminarEje implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if (!grid.getSelectedItems().isEmpty()){

				PasswordField input = new PasswordField("Ingrese su contraseña");

				MessageBox.createQuestion()
						.withCaption("Autenticar")
						.withMessage(input)
						.withOkButton(() -> {

					if(!Hash.sha1(input.getValue()).equals(usuarioActual.getPassword())) {
						return;
					} else { ConfirmDialog.show(
								UI.getCurrent(),
								"Confirmar eliminación:",
								"¿Deseas relamente eliminar los registros?",
								"Eliminar", "Cancelar",
							(ConfirmDialog.Listener) dialog -> {
                                if (dialog.isConfirmed()) {
                                    Set<Eje> ejes = grid.getSelectedItems();
                                    List<Eje> ej = new ArrayList<Eje>();
                                    ej.addAll(ejes);
                                    ADUsuario adUsuario = new ADUsuario();
                                    boolean ok = adEje.eliminarEjes(ej);
                                    if (ok){
                                        grid.setItems(adEje.obtenerTodosEjes());
                                        Notification.show("Registros eliminados...", Notification.Type.WARNING_MESSAGE);
                                    }
                                }
                            });
					} })
						.withNoButton(() -> {  return; })
						.open();



			}
			else
				Notification.show("Selecciona al menos un usuario para eliminar", Notification.Type.WARNING_MESSAGE);
		}
			
	}
	
}
