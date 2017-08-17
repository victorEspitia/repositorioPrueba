package mx.edu.uaz.vistas;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.utils.FilesFromFolder;
import mx.edu.uaz.vistas.actividades.ActividadesTab;
import mx.edu.uaz.vistas.actividades.AgregarActividad;
import mx.edu.uaz.vistas.ejes.EjesTab;
import mx.edu.uaz.vistas.indicadores.IndicadoresTab;
import mx.edu.uaz.vistas.metas.MetasTab;
import mx.edu.uaz.vistas.usuarios.UsuarioPerfil;
import mx.edu.uaz.vistas.usuarios.UsuariosTab;
import org.vaadin.dialogs.ConfirmDialog;
import java.io.File;


/**
 * Created by Victor Hugo on 24/05/2017.
 */
public class Principal extends CustomLayout{

    private Image imageUsuario1;
    private Image imageUsuario2;
    private Image imageUsuario3;

    private Button btnReportes;
    private Button btnEjes;
    private Button btnMetas;
    private Button btnIndicadores;
    private Button btnActividades;
    private Button btnAgregarActividades;

    private Button btnUsuarios, btnCerrarSesion, btnProfile;

    private Label lblName, lblUpName, lblComision;

    private CssLayout contenidoPrincipal;

    Usuario user;

    public Principal(Usuario u){
        this.user = u;
        crearInterfaz();
        contenidoPrincipal.addComponent(new ActividadesTab(user));
    }

    public void crearInterfaz(){
        setTemplateName("principal");
        setPrimaryStyleName("hold-transition skin-blue sidebar-mini");
        setSizeFull();

        inicializarBotones();
        inicializarLayout();
        inicializarFoto();
    }

    public void inicializarFoto(){
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+ "/images/";

        String file = FilesFromFolder.getCompleteFileName(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+ "/images/", user.getNombre() + "-" + user.getId_usuario());

        FileResource resource;
        if(file == null) {
            resource = new FileResource(new File(basepath + "default.jpg"));
        } else {
            resource = new FileResource(new File(basepath + file));
        }

        imageUsuario1 = new Image();
        imageUsuario1.setSource(resource);
        imageUsuario1.setId("imgUser-1");
        imageUsuario1.addStyleName( "img-circle");
        imageUsuario1.setWidth(20, Unit.PIXELS);
        imageUsuario1.setHeight(20, Unit.PIXELS);

        imageUsuario2 = new Image();
        imageUsuario2.setSource(resource);
        imageUsuario2.setId("imgUser-2");
        imageUsuario2.addStyleName( "img-circle");
        imageUsuario2.setWidth(75, Unit.PIXELS);
        imageUsuario2.setHeight(75, Unit.PIXELS);

        imageUsuario3 = new Image();
        imageUsuario3.setSource(resource);
        imageUsuario3.setId("imgUser-3");
        imageUsuario3.addStyleName( "img-circle");
        imageUsuario3.setWidth(50, Unit.PIXELS);
        imageUsuario3.setHeight(50, Unit.PIXELS);


        addComponent(imageUsuario1, "img-user-1");
        addComponent(imageUsuario2, "img-user-2");
        addComponent(imageUsuario3, "img-user-3");


    }

    public void inicializarLayout(){
        contenidoPrincipal = new CssLayout();
        contenidoPrincipal.setSizeFull();
        addComponent(contenidoPrincipal, "main-content");
    }

    public void inicializarBotones(){
        btnReportes = new Button("Generar Reportes");
        btnReportes.addClickListener(new clickButton());
        btnReportes.setHeight("100%");
        btnReportes.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        btnEjes = new Button("Administrar Ejes");
        btnEjes.addClickListener(new clickButton());
        btnEjes.setHeight("100%");
        btnEjes.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        btnMetas = new Button("Administrar Metas");
        btnMetas.addClickListener(new clickButton());
        btnMetas.setHeight("100%");
        btnMetas.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        btnIndicadores = new Button("Administrar Indicadores");
        btnIndicadores.addClickListener(new clickButton());
        btnIndicadores.setHeight("100%");
        btnIndicadores.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        btnActividades = new Button("Administrar Actividades");
        btnActividades.addClickListener(new clickButton());
        btnActividades.setHeight("100%");
        btnActividades.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        btnAgregarActividades = new Button("Agregar Actividades");
        btnAgregarActividades.addClickListener(new clickButton());
        btnAgregarActividades.setHeight("100%");
        btnAgregarActividades.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        btnUsuarios = new Button("Administrar Usuarios");
        btnUsuarios.addClickListener(new clickButton());
        btnUsuarios.setHeight("100%");
        btnUsuarios.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setPrimaryStyleName("btn btn-default btn-flat");
        btnCerrarSesion.addClickListener(new clickButton());

        btnProfile = new Button("Ver Perfil");
        btnProfile.setPrimaryStyleName("btn btn-default btn-flat");
        btnProfile.addClickListener(new clickButton());

        lblName = new Label(user.getNombre() + " " + user.getApellidos());

        lblUpName = new Label(user.getNombre() + " " + user.getApellidos());
        lblUpName.setPrimaryStyleName("label-blanco");

        lblComision = new Label(user.getNombre_comisionado());
        lblComision.setPrimaryStyleName("label-blanco");

        addComponent(btnReportes, "btn-rep");
        addComponent(btnEjes, "btn-eje");
        addComponent(btnMetas, "btn-meta");
        addComponent(btnIndicadores, "btn-ind");
        addComponent(btnActividades, "btn-add-act");
        addComponent(btnAgregarActividades, "btn-all-act");
        addComponent(btnUsuarios, "btn-usr");
        addComponent(btnCerrarSesion, "btn-signout");
        addComponent(btnProfile, "btn-profile");
        addComponent(lblName, "lbl-name");
        addComponent(lblUpName, "lbl-Upname");
        addComponent(lblComision, "lbl-comision");

    }


    class clickButton implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            Button x = (Button) clickEvent.getSource();
            switch (x.getCaption()){
                case "Generar Reportes":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new SpreadsheetReport(user));
                    break;

                case "Administrar Ejes":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new EjesTab(user));
                    break;

                case "Administrar Metas":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new MetasTab(user));
                    break;

                case "Administrar Indicadores":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new IndicadoresTab(user));
                    break;

                case "Administrar Actividades":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new ActividadesTab(user));
                    break;

                case "Agregar Actividades":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new AgregarActividad(user));
                    break;

                case "Administrar Usuarios":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new UsuariosTab(user));
                    break;
                case "Cerrar Sesión":
                    ConfirmDialog.show(UI.getCurrent(), "ATENCIÓN:", "¿Deseas cerrar sesión?", "Confirmar", "Cancelar", (ConfirmDialog.Listener) dialog -> {
                        if (dialog.isConfirmed()) {
                            UI.getCurrent().setContent(new InicioSesion());
                        }
                    });
                    break;
                case "Ver Perfil":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new UsuarioPerfil(user));
                    break;
            }
        }
    }
}

