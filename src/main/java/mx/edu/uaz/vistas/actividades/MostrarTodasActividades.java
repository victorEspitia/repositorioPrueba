package mx.edu.uaz.vistas.actividades;

import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mx.edu.uaz.accesodatos.ADActividad;
import mx.edu.uaz.accesodatos.ADMeta;
import mx.edu.uaz.modelos.Actividad;
import mx.edu.uaz.modelos.Indicador;
import mx.edu.uaz.modelos.Meta;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.utils.Broadcaster;
import mx.edu.uaz.vistas.InicioSesion;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor Hugo on 01/06/2017.
 */
public class MostrarTodasActividades extends FormLayout{
    private Button btnActualizarActividad;
    private Button btnEliminarActividad;

    private Button btnVerAvances;

    private HorizontalLayout HL;

    private Grid<Actividad> grid;
    private Grid<Actividad> grid2;
    private ADActividad adActividad;

    private List<Actividad> listaActividades;

    private Usuario usuario;
    private Actividad actividad;

    private boolean flag;

    public MostrarTodasActividades(){
        actividad = new Actividad();
        getAllActividades();
        funcionBotones();
    }

    public MostrarTodasActividades(Usuario usuario){
        this.usuario = usuario;
        getActividadesByUser();
        funcionBotones();
    }


    public void getAllActividades(){

        btnActualizarActividad = new Button("Actualizar Actividad");
        btnActualizarActividad.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        btnEliminarActividad = new Button("Eliminar Actividad");
        btnEliminarActividad.setStyleName(ValoTheme.BUTTON_DANGER);

        btnVerAvances = new Button("Avances");
        btnVerAvances.setStyleName(ValoTheme.BUTTON_PRIMARY);

        grid = new Grid<Actividad>(Actividad.class);
        adActividad = new ADActividad();
        grid.setItems(adActividad.obtenerTodasActividades());
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumns("nombre","descripcion","fecha_inicio","fecha_final", "recurso");
        grid.getColumn("fecha_inicio").setCaption("Fecha Inicio");
        grid.getColumn("fecha_final").setCaption("Fecha Final");
        grid.addColumn(Actividad::getId_usuario).setCaption("Usuario");
        grid.setResponsive(true);
        grid.setWidth("100%");

        grid.addSelectionListener((SelectionListener<Actividad>) event -> {
            if (!event.getAllSelectedItems().isEmpty()){
                actividad = event.getFirstSelectedItem().get();
                flag = true;
            }
        });

        addComponents(grid,btnVerAvances);
    }

    public void getActividadesByUser(){

        HL = new HorizontalLayout();
        listaActividades = new ArrayList<>();

        btnActualizarActividad = new Button("Actualizar Actividad");
        btnActualizarActividad.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        btnEliminarActividad = new Button("Eliminar Actividad");
        btnEliminarActividad.setStyleName(ValoTheme.BUTTON_DANGER);

        btnVerAvances = new Button("Avances");
        btnVerAvances.setStyleName(ValoTheme.BUTTON_PRIMARY);

        HL.addComponents(btnActualizarActividad,btnEliminarActividad,btnVerAvances);

        grid2 = new Grid<Actividad>(Actividad.class);
        adActividad = new ADActividad();
        grid2.setItems(adActividad.obtenerActividadesByUser(usuario.getId_usuario()));
        grid2.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid2.setColumns("nombre","descripcion","fecha_inicio","fecha_final", "recurso");
        grid2.getColumn("fecha_inicio").setCaption("Fecha Inicio");
        grid2.getColumn("fecha_final").setCaption("Fecha Final");
        grid2.setResponsive(true);
        grid2.setWidth("100%");

        grid2.addSelectionListener((SelectionListener<Actividad>) event -> {
            if (!event.getAllSelectedItems().isEmpty()){
                actividad = event.getFirstSelectedItem().get();
                flag = true;
            }
        });

        addComponents(grid2,HL);
    }

    public void funcionBotones(){

        btnActualizarActividad.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                if(flag){
                    removeAllComponents();
                    addComponent(new AgregarActividad(actividad,usuario));
                }else{
                    Notification.show("Seleccione una Actividad para actualizar", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

        btnEliminarActividad.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(flag){
                    adActividad = new ADActividad();
                    ConfirmDialog.show(UI.getCurrent(), "ATENCIÓN:", "¿Deseas eliminar la actividad?", "Confirmar", "Cancelar", (ConfirmDialog.Listener) dialog -> {
                        if (dialog.isConfirmed()) {
                            boolean ok = adActividad.borrarActividad(actividad.getId_actividad());
                            if(ok){
                                Broadcaster.broadcast("Actividad: " + actividad.getNombre() + " ha sido eliminada");
                                Notification.show("Actividad Eliminada con exito", Notification.Type.HUMANIZED_MESSAGE);
                                grid2.setItems(adActividad.obtenerActividadesByUser(usuario.getId_usuario()));
                            }else{
                                Notification.show("No se pudo eliminar la actividad", Notification.Type.ERROR_MESSAGE);
                            }
                        }
                    });
                }else{
                    Notification.show("Seleccione una Actividad para eliminar", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

        btnVerAvances.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });
    }

}
