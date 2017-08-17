package mx.edu.uaz.vistas;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import mx.edu.uaz.accesodatos.ADUsuario;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.utils.Hash;

public class InicioSesion extends CustomLayout {

	private TextField tfMail;
	private PasswordField pfContra;
	private Button btnIniciar;
	private Button btnRegistrar;
	private ADUsuario adUsuario;
	private Label labUser;
	private Label labPass;

	public InicioSesion() {
		interfaz();
	}

	public void interfaz() {

		setTemplateName("index");

		labUser = new Label("Usuario");
		labPass = new Label("Password");

		tfMail = new TextField();
		tfMail.setPlaceholder("");
		tfMail.setWidth("100%");

		pfContra = new PasswordField();
		pfContra.setPlaceholder("holamundo");
		pfContra.setWidth("100%");

		adUsuario = new ADUsuario();

		btnIniciar = new Button("Iniciar Sesion");
		btnIniciar.setWidth("100%");

		btnIniciar.addClickListener(new ClickListener() {
			boolean existe = false;

			@Override
			public void buttonClick(ClickEvent event) {
				for (Usuario u : adUsuario.obtenUsuarios()) {
					if (tfMail.getValue().equals(u.getUser()) && Hash.sha1(pfContra.getValue()).equals(u.getPassword())) {
						if(u.getCargo().equals("admin")) {
							UI.getCurrent().setContent(new Principal(u));
						} else {
							UI.getCurrent().setContent(new PrincipalUser(u));
						}
						existe = true;
					}
				}

				if (!existe) {
					Notification.show("Usuario o contraseña incorrectos", Notification.Type.ERROR_MESSAGE);
				}
			}
		});

		btnRegistrar = new Button("¿No tienes cuenta aún? Registrate Ahora");
		btnRegistrar.setPrimaryStyleName("btn-link");
		btnRegistrar.addClickListener((ClickListener) clickEvent -> UI.getCurrent().setContent(new Registrar()));



		addComponent(labPass,"LabPass");
		addComponent(labUser,"LabUser");
		addComponent(tfMail, "user");
		addComponent(pfContra, "password");
		addComponent(btnIniciar, "submit");

	}

}
