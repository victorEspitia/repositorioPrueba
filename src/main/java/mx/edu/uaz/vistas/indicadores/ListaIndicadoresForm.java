package mx.edu.uaz.vistas.indicadores;

import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.MessageBox;
import mx.edu.uaz.accesodatos.ADIndicador;
import mx.edu.uaz.modelos.Indicador;
import mx.edu.uaz.modelos.Meta;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.utils.Hash;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaIndicadoresForm extends VerticalLayout {
    private Grid<Indicador> grid;
    private ADIndicador adIndicador;
    private Indicador indicador;
    private Button btnEliminar;
    private Usuario usuarioActual;

    @SuppressWarnings("unchecked")
    public ListaIndicadoresForm(Usuario u) {
        usuarioActual = u;
        setSizeFull();
        setResponsive(true);


        grid = new Grid<Indicador>(Indicador.class);
        grid.setStyleName("mi-grid");
        adIndicador = new ADIndicador();
        grid.setItems(adIndicador.obtenIndicadores());
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.setColumns("nombre", "descripcion");
        grid.getColumn("descripcion").setCaption("Descripción");
        grid.addColumn(person -> "Editar",
                new ButtonRenderer(clickEvent -> {
                    TabSheet tab = (TabSheet) this.getParent();
                    IndicadoresForm lu = (IndicadoresForm) tab.getTab(0).getComponent();
                    indicador = (Indicador) clickEvent.getItem();
                    lu.enlazarExterno(indicador);
                    tab.setSelectedTab(0);
                }));
        grid.setResponsive(true);
        grid.setWidth("100%");
        grid.addSelectionListener((SelectionListener<Indicador>) event -> {

            if (!event.getAllSelectedItems().isEmpty()) {
                indicador = event.getFirstSelectedItem().get();

            }
        });

        btnEliminar = new Button("Eliminar", VaadinIcons.TRASH);
        btnEliminar.setStyleName(ValoTheme.BUTTON_DANGER);
        btnEliminar.addClickListener(new EliminarIndicador());

        addComponents(grid, btnEliminar);

    }

    public void llenaGrid() {
        grid.setItems(adIndicador.obtenIndicadores());
    }

    class EliminarIndicador implements ClickListener {

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
                                                    Set<Indicador> indicador = grid.getSelectedItems();
                                                    List<Indicador> ind = new ArrayList<Indicador>();
                                                    ind.addAll(indicador);
                                                    boolean ok = adIndicador.eliminarIndicadores(ind);
                                                    if (ok) {
                                                        grid.setItems(adIndicador.obtenIndicadores());
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
                Notification.show("Selecciona al menos un indicador para eliminar", Notification.Type.WARNING_MESSAGE);
        }

    }

}
