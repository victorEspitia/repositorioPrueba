package mx.edu.uaz.vistas.usuarios;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import mx.edu.uaz.accesodatos.ADUsuario;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.utils.*;

public class UsuarioForm extends UsuarioFormDesign {
	private Binder<Usuario> binder;
	private Usuario usuario;
	private ADUsuario adUsuario;
	private boolean edicion = false;
	private Usuario usuarioActual;

	public UsuarioForm(Usuario usuarioActual){
		this.usuarioActual = usuarioActual;
		usuario = new Usuario();
		binder = new Binder<Usuario>(Usuario.class);
		binder.setBean(usuario);
		enlazarDatos();
	}

    public void enlazarExterno(Usuario usuario) {
	    usuario.setPassword("");
        this.usuario = usuario;
        binder.setBean(usuario);
        btnRegistrar.setCaption("Actualizar");
        edicion = true;
    }

	public void enlazarDatos() {

		binder.forField(tfUsuario)
				.asRequired("El usuario es requerido")
				.withValidator(nombre -> nombre.length() >= 5, "El usuario debe contener al menos 5 caracteres")
				.bind(Usuario::getUser, Usuario::setUser);

		binder.forField(pfPassword)
				.asRequired("La contraseña es requerida")
				.withValidator(nombre -> nombre.length() >= 5, "La contraseña debe contener al menos 5 caracteres")
				.bind(Usuario::getPassword, Usuario::setPassword);
		
		binder.forField(tfNombre)
			.asRequired("El nombre es requerido")
			.withValidator(nombre -> nombre.length() >= 3, "El nombre debe contener al menos 3 caracteres")
			.bind(Usuario::getNombre, Usuario::setNombre);
		
		binder.forField(tfApellidos)
			.asRequired("Los apellidos son requeridos")
			.withValidator(apellidos -> apellidos.length() >= 7, "Los apellidos debe contener al menos 7 caracteres")
			.bind(Usuario::getApellidos, Usuario::setApellidos);
	
		binder.forField(tfCorreo)
			.asRequired("El e-mail es requerido")
			.withValidator(new EmailValidator("El correo {0} no tiene el formato correcto"))
			.bind(Usuario::getCorreo, Usuario::setCorreo);

		binder.forField(tfNombreComision)
			.asRequired("La comisión no puede ir vacía")
			.withValidator(comision -> comision.length() >= 5, "Los comision debe contener al menos 5 caracteres")
			.bind(Usuario::getNombre_comisionado, Usuario::setNombre_comisionado);
		
		btnRegistrar.addClickListener(new CrudUsuario());

		btnCancelar.addClickListener((ClickListener) clickEvent -> {
            edicion = false;
            btnRegistrar.setCaption("Registrar");
            binder.setBean(new Usuario());
        });

		uploadFoto.setVisible(false);
	}
	
	class CrudUsuario implements ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			if (binder.validate().isOk()){
				usuario.setCargo("user");
				usuario.setPassword(Hash.sha1(pfPassword.getValue()));

				adUsuario = new ADUsuario();
				boolean ok = false;
				if (edicion){
					ok = adUsuario.modificarUsuario(usuario);
					btnRegistrar.setCaption("Registrar");
				}
				else{

					ok = adUsuario.guardarUsuario(usuario);

                    ((ListaUsuariosForm)((TabSheet)UsuarioForm.this.getParent()).getTab(1).getComponent()).llenaGrid();
				}
				if (ok){
					Notification.show("El usuario "+usuario.getNombre()+" se "+((edicion)?"actualizó":"agregó")+" correctamente",
							Notification.Type.TRAY_NOTIFICATION);
					usuario= new Usuario();
					binder.setBean(usuario);
					if(edicion){
						((TabSheet)UsuarioForm.this.getParent()).setSelectedTab(1);
						edicion = false;
					}
				}
			}
			else{
				Notification.show("Verifica tus datos", Notification.Type.WARNING_MESSAGE);
			}
		}
		
	}
	
}
