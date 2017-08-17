package mx.edu.uaz.vistas.actividades;

import com.vaadin.data.Binder;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mx.edu.uaz.accesodatos.*;
import mx.edu.uaz.modelos.*;
import mx.edu.uaz.utils.Broadcaster;
import mx.edu.uaz.utils.LocalDateTimeToDateConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor Hugo on 31/05/2017.
 */
public class AgregarActividad extends AddActividad {

    private ADRecurso adRecurso;
    private ADMeta adMeta;
    private ADEje adEje;
    private ADIndicador adIndicador;
    private ADActividad adActividad;

    private List<Recurso> listaRecurso1;
    private List<Eje> listaEje1;
    private List<Meta> listaMeta1;
    private List<Indicador> listaIndicador1;

    private boolean flag;

    private Eje eje;
    private Meta meta;
    private Actividad actividad;
    private Usuario usuario;

    private Binder<Actividad> binder;

    public AgregarActividad(Usuario usuario){
        actividad = new Actividad();
        this.usuario = usuario;
        inicializarInterfaz();

        flag = true;
    }

    public AgregarActividad(Actividad actividad, Usuario usuario){
        this.usuario = usuario;
        this.actividad = actividad;
        inicializarInterfaz();

        flag = false;
    }

    public void inicializarInterfaz(){
        funcionBotones();
        inicializarComponentes();
        funcionCombo();
        componenteBinder();
    }

    public void inicializarComponentes(){

        cbMetas.setEnabled(false);
        cbIndicadores.setEnabled(false);
    }

    public void funcionCombo(){

        adRecurso = new ADRecurso();
        adEje = new ADEje();
        adIndicador = new ADIndicador();
        adMeta = new ADMeta();

        listaRecurso1 = new ArrayList<>();
        listaRecurso1 = adRecurso.obtenRecursos();
        cbRecursos.setItems(listaRecurso1);

        listaEje1 = adEje.obtenerTodosEjes();
        cbEjes.setItems(listaEje1);
        cbEjes.addSelectionListener(new SingleSelectionListener<Eje>() {
            @Override
            public void selectionChange(SingleSelectionEvent<Eje> singleSelectionEvent) {
                eje = (Eje) singleSelectionEvent.getFirstSelectedItem().get();

                listaMeta1 = adMeta.obtenMetasByIDEje(eje.getId_eje());
                if(listaMeta1.size()>0){
                    cbMetas.setItems(listaMeta1);
                    cbMetas.setEnabled(true);
                }else{
                    cbMetas.setEnabled(false);
                    cbIndicadores.setEnabled(false);
                    Notification.show("Seleccionar otro eje, no tiene ninguna meta asignada", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

        cbMetas.addSelectionListener(new SingleSelectionListener<Meta>() {
            @Override
            public void selectionChange(SingleSelectionEvent<Meta> singleSelectionEvent) {
                meta = (Meta) singleSelectionEvent.getFirstSelectedItem().get();

                listaIndicador1 = adIndicador.obtenIndicadoresByidMeta(meta.getId_meta());
                if(listaIndicador1.size()>0){
                    cbIndicadores.setItems(listaIndicador1);
                    cbIndicadores.setEnabled(true);
                }else{
                    cbIndicadores.setEnabled(false);
                    Notification.show("Seleccionar otra meta, no tiene ningun indicador asignado", Notification.Type.WARNING_MESSAGE);
                }
            }
        });


    }

    public void componenteBinder(){

        binder = new Binder<Actividad>(Actividad.class);
        binder.setBean(actividad);

        binder.forField(tfNombreActividad)
                .asRequired("El nombre es requerido")
                .withValidator(nombre -> nombre.length() >=10, "El nombre debe contener al menos 10 caracteres")
                .bind(Actividad::getNombre, Actividad::setNombre);

        binder.forField(taDescripcion)
                .withValidator(descripcion -> descripcion.length() >=10, "La descripción debe contener al menos 10 caracteres")
                .bind(Actividad::getDescripcion, Actividad::setDescripcion);

        binder.forField(tfRecursos)
                .withValidator(recursos -> recursos.length() >=3, "Los recursos debe contener al menos 2 caracteres")
                .bind(Actividad::getRecurso, Actividad::setRecurso);

        binder.forField(cbIndicadores)
                .asRequired("Debes seleccionar un indicador")
                .bind("id_indicador");

        binder.forField(cbRecursos)
                .asRequired("Debes seleccionar una recurso")
                .bind("id_origenRecurso");

        binder.forField(dfFechaInicio)
                .asRequired("Debe ser requierida una fecha")
                .withConverter(new LocalDateTimeToDateConverter())
                .bind("fecha_inicio");

        binder.forField(dfFechaFinal)
                .asRequired("Debe ser requierida una fecha")
                .withConverter(new LocalDateTimeToDateConverter())
                .bind("fecha_final");
    }

    public void funcionBotones(){

        btnGuardarActividad.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(binder.validate().isOk()){
                    boolean ok=false;
                    adActividad = new ADActividad();
                    if(flag){
                        actividad.setId_usuario(usuario);
                        System.out.println(usuario.getNombre());
                        ok = adActividad.guardarActividad(actividad);
                        if(ok){
                            Notification.show("Guardada correctamente la actividad", Notification.Type.HUMANIZED_MESSAGE);
                            Broadcaster.broadcast("Actividad: " + actividad.getNombre() + " ha sido agregada");
                            actividad = new Actividad();
                            binder.setBean(actividad);
                        }else {
                            Notification.show("No se puedo guardar la actividad", Notification.Type.ERROR_MESSAGE);
                        }
                    }else{
                        ok = adActividad.modificarActividad(actividad);
                        if(ok){
                            Notification.show("Actualizada correctamente la actividad", Notification.Type.HUMANIZED_MESSAGE);
                            Broadcaster.broadcast("Actividad: " + actividad.getNombre() + " ha sido actualizada");
                            FormLayout fl = (FormLayout) getParent();

                            TabSheet tb = (TabSheet) fl.getParent();
                            tb.removeAllComponents();
                            tb.addTab( new MostrarTodasActividades(),"Todas Las Actividades", VaadinIcons.LIST);
                            tb.addTab( new MostrarTodasActividades(usuario),"Mis Actividades", VaadinIcons.LIST);
                        }else{
                            Notification.show("No se puedo actualizar la actividad", Notification.Type.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        btnRecursos.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window subWindow = new Window("ORIGEN RECURSOS");
                VerticalLayout subContent = new VerticalLayout();

                subWindow.setContent(subContent);
                subWindow.setWidth("500px");
                subWindow.setHeight("200px");
                subWindow.setStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);

                subWindow.setResizable(false);

                TextField tfNombreRecurso = new TextField("Nuevo Recurso:");
                tfNombreRecurso.setWidth("100%");

                Button btnAgregarRecurso = new Button("Agregar Recurso");
                btnAgregarRecurso.setWidth("100%");
                btnAgregarRecurso.setStyleName(ValoTheme.BUTTON_PRIMARY);


                subContent.addComponents(tfNombreRecurso,btnAgregarRecurso);

                subWindow.center();
                getUI().addWindow(subWindow);


                btnAgregarRecurso.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        ADRecurso adRecurso = new ADRecurso();
                        if(!tfNombreRecurso.getValue().isEmpty()){
                            boolean ok = adRecurso.guardarRecurso(new Recurso(tfNombreRecurso.getValue()));
                            if(ok){
                                Notification.show("Recurso Guardado exitosamente", Notification.Type.HUMANIZED_MESSAGE);
                                tfNombreRecurso.setValue("");
                                listaRecurso1 = adRecurso.obtenRecursos();
                                cbRecursos.setItems(listaRecurso1);
                            }else{
                                Notification.show("Recurso No Guardado", Notification.Type.ERROR_MESSAGE);
                            }
                        }else{
                            Notification.show("Ingresar Nombre del recurso", Notification.Type.WARNING_MESSAGE);
                        }
                    }
                });

            }
        });
    }
}
