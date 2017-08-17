package mx.edu.uaz.vistas.metas;

import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.MessageBox;
import mx.edu.uaz.accesodatos.ADMeta;
import mx.edu.uaz.modelos.Eje;
import mx.edu.uaz.modelos.Meta;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.utils.Hash;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaMetasForm extends VerticalLayout {
    private Grid<Meta> grid;
    private ADMeta adMeta;
    private Meta meta;
    private Button btnEliminar;
    private Usuario usuarioActual;

    @SuppressWarnings("unchecked")
    public ListaMetasForm(Usuario u) {
        usuarioActual = u;
        setSizeFull();
        setResponsive(true);


        grid = new Grid<Meta>(Meta.class);
        grid.setStyleName("mi-grid");
        adMeta = new ADMeta();
        grid.setItems(adMeta.obtenMetas());
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.setColumns("nombre", "descripcion");
        grid.getColumn("descripcion").setCaption("Descripción");
        grid.addColumn(person -> "Editar",
                new ButtonRenderer(clickEvent -> {
                    TabSheet tab = (TabSheet) this.getParent();
                    MetasForm lu = (MetasForm) tab.getTab(0).getComponent();
                    meta = (Meta) clickEvent.getItem();
                    lu.enlazarExterno(meta);
                    tab.setSelectedTab(0);
                }));
        grid.setResponsive(true);
        grid.setWidth("100%");
        grid.addSelectionListener((SelectionListener<Meta>) event -> {

            if (!event.getAllSelectedItems().isEmpty()) {
                meta = event.getFirstSelectedItem().get();

            }
        });

        btnEliminar = new Button("Eliminar", VaadinIcons.TRASH);
        btnEliminar.setStyleName(ValoTheme.BUTTON_DANGER);
        btnEliminar.addClickListener(new EliminarMeta());

        addComponents(grid, btnEliminar);

    }

    public void llenaGrid() {
        grid.setItems(adMeta.obtenMetas());
    }

    class EliminarMeta implements ClickListener {

        @Override
        public void buttonClick(ClickEvent event) {
            if (!grid.getSelectedItems().isEmpty()) {

                PasswordField input = new PasswordField("Ingrese su contraseña");

                MessageBox.createQuestion()
                        .withCaption("Autenticar")
                        .withMessage(input)
                        .withOkButton(() -> {

                            if (!Hash.sha1(input.getValue()).equals(usuarioActual.getPassword())) {
                                return;
                            } else {
                                ConfirmDialog.show(
                                        UI.getCurrent(),
                                        "Confirmar eliminación:",
                                        "¿Deseas relamente eliminar los registros?",
                                        "Eliminar", "Cancelar",
                                        new ConfirmDialog.Listener() {

                                            public void onClose(ConfirmDialog dialog) {
                                                if (dialog.isConfirmed()) {
                                                    Set<Meta> metas = grid.getSelectedItems();
                                                    List<Meta> met = new ArrayList<Meta>();
                                                    met.addAll(metas);
                                                    boolean ok = adMeta.eliminarMetas(met);
                                                    if (ok) {
                                                        grid.setItems(adMeta.obtenMetas());
                                                        Notification.show("Registros eliminados...", Notification.Type.WARNING_MESSAGE);
                                                    }
                                                }
                                            }
                                        });
                            }
                        })
                        .withNoButton(() -> {
                            return;
                        })
                        .open();

            } else
                Notification.show("Selecciona al menos un usuario para eliminar", Notification.Type.WARNING_MESSAGE);
        }

    }

}
