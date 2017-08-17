package mx.edu.uaz.vistas;

import com.vaadin.data.HasValue;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import mx.edu.uaz.accesodatos.ADUsuario;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.utils.Hash;

import java.util.List;

public class Registrar extends CustomLayout {

	private TextField tfNombre;
	private TextField tfApellidos;
	private TextField tfComision;
	private TextField tfCorreo;
	private TextField tfUser;
	private PasswordField pfPassword;
	private PasswordField pfPasswordConf;
	private Button btnAtras;
	private Button btnRegistrar;
	private ADUsuario adUsuario;
	private List<Usuario> userNames;

	public Registrar() {
		interfaz();
	}

	public void interfaz() {

		addStyleName("v-scrollable");
		setHeight("100%");
		setStyleName("custom-principal");
		setSizeFull();
		setResponsive(true);

		tfNombre = new TextField("Nombre");
		tfApellidos = new TextField("Apellidos");

		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponents(tfNombre, tfApellidos);


		tfComision = new TextField("Comisión");
		tfCorreo = new TextField("Correo");
		tfUser = new TextField("Usuario");
		pfPassword = new PasswordField("Contraseña");
		pfPasswordConf = new PasswordField("Confirmar Contraseña");
		adUsuario = new ADUsuario();

		btnRegistrar = new Button("Registrar");
		btnRegistrar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnRegistrar.setWidth("100%");

		userNames = adUsuario.obtenUserNames();

		btnRegistrar.addClickListener(new ClickListener() {
		    boolean existe = false;

			@Override
			public void buttonClick(ClickEvent event) {


                if (!existe) {
				    Notification.show("Usuario o contraseña incorrectos", Notification.Type.ERROR_MESSAGE);
                }
			}
		});

		tfUser.addValueChangeListener((HasValue.ValueChangeListener<String>) valueChangeEvent -> {
			boolean flag = false;
			for (Usuario u : userNames) {
				if (u.getUser().equals(tfUser.getValue())) {
					flag = true;
				}
			}

			if(flag) {
				Notification.show("No es posible", Notification.Type.ERROR_MESSAGE);
			}

		});

		setTemplateName("registro");
		addComponent(hl, "nombres");
		addComponent(tfComision, "comision");
		addComponent(tfCorreo, "correo");
		addComponent(tfUser, "user");
		addComponent(pfPassword, "password");
		addComponent(pfPasswordConf, "passwordConf");
		addComponent(btnRegistrar, "registrar");
	}

}
