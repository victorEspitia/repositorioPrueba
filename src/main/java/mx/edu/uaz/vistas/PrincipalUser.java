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
public class PrincipalUser extends CustomLayout{

    private Image imageUsuario1;
    private Image imageUsuario2;
    private Image imageUsuario3;

    private Button btnActividades;
    private Button btnAgregarActividades;


    private Button btnCerrarSesion, btnProfile;

    private Label lblName, lblUpName, lblComision;

    private CssLayout contenidoPrincipal;

    Usuario user;

    public PrincipalUser(Usuario u){
        this.user = u;
        crearInterfaz();
        contenidoPrincipal.addComponent(new ActividadesTab(user));
    }

    public void crearInterfaz(){
        setTemplateName("principalUser");
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
        contenidoPrincipal.setWidth("100%");
        contenidoPrincipal.setHeight("100%");

        addComponent(contenidoPrincipal, "main-content");
    }

    public void inicializarBotones(){

        btnActividades = new Button("Administrar Actividades");
        btnActividades.addClickListener(new clickButton());
        btnActividades.setHeight("100%");
        btnActividades.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        btnAgregarActividades = new Button("Agregar Actividades");
        btnAgregarActividades.addClickListener(new clickButton());
        btnAgregarActividades.setHeight("100%");
        btnAgregarActividades.setStyleName(ValoTheme.BUTTON_BORDERLESS);

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

        addComponent(btnActividades, "btn-act");
        addComponent(btnAgregarActividades, "btn-act");
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

                case "Administrar Actividades":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new ActividadesTab(user));
                    break;

                case "Agregar Actividades":
                    contenidoPrincipal.removeAllComponents();
                    contenidoPrincipal.addComponent(new AgregarActividad(user));
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

