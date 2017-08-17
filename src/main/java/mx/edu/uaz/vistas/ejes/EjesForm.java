package mx.edu.uaz.vistas.ejes;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.edu.uaz.accesodatos.ADEje;
import mx.edu.uaz.modelos.Eje;
import mx.edu.uaz.utils.BuscaComponentes;
import mx.edu.uaz.utils.Hash;
import mx.edu.uaz.vistas.usuarios.ListaUsuariosForm;
import mx.edu.uaz.vistas.usuarios.UsuarioFormDesign;

public class EjesForm extends EjesFormDesign {
	private Binder<Eje> binder;
	private Eje eje;
	private ADEje adEje;
	private boolean edicion = false;

	public EjesForm(){
		eje = new Eje();
		binder = new Binder<Eje>(Eje.class);
		binder.setBean(eje);
		enlazarDatos();
	}

    public void enlazarExterno(Eje eje) {
        this.eje = eje;
        binder.setBean(eje);
        btnRegistrar.setCaption("Actualizar");
        edicion = true;
    }

	public void enlazarDatos() {

		binder.forField(tfNombre)
				.asRequired("El nombre es requerido")
				.withValidator(nombre -> nombre.length() >= 5, "El nombre debe contener al menos 5 caracteres")
				.bind(Eje::getNombre, Eje::setNombre);

		binder.forField(taDescripción)
				.asRequired("La descripción es requerida")
				.withValidator(nombre -> nombre.length() >= 10, "La descripción debe contener al menos 5 caracteres")
				.bind(Eje::getDescripcion, Eje::setDescripcion);
		
		btnRegistrar.addClickListener(new CrudUsuario());

		btnCancelar.addClickListener((ClickListener) clickEvent -> {
            edicion = false;
            btnRegistrar.setCaption("Registrar");
            binder.setBean(new Eje());
        });

	}
	
	class CrudUsuario implements ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			if (binder.validate().isOk()){

				adEje = new ADEje();
				boolean ok = false;
				if (edicion){
					ok = adEje.modificar(eje);
					btnRegistrar.setCaption("Registrar");
				}
				else{

					ok = adEje.guardar(eje);

                    ((ListaEjesForm)((TabSheet)EjesForm.this.getParent()).getTab(1).getComponent()).llenaGrid();
				}
				if (ok){
					Notification.show("El eje "+eje.getNombre()+" se "+((edicion)?"actualizó":"agregó")+" correctamente",
							Notification.Type.TRAY_NOTIFICATION);
					eje= new Eje();
					binder.setBean(eje);
					if(edicion){
						((TabSheet)EjesForm.this.getParent()).setSelectedTab(1);
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
