package mx.edu.uaz.vistas.metas;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import mx.edu.uaz.accesodatos.ADEje;
import mx.edu.uaz.accesodatos.ADMeta;
import mx.edu.uaz.modelos.Eje;
import mx.edu.uaz.modelos.Indicador;
import mx.edu.uaz.modelos.Meta;

import java.util.List;

public class MetasForm extends MetasFormDesign {
	private Binder<Meta> binder;
	private Meta meta;
	private ADMeta adMeta;
	private ADEje adEje;
	private boolean edicion = false;

	public MetasForm(){
		adMeta = new ADMeta();

		meta = new Meta();
		binder = new Binder<Meta>(Meta.class);
		binder.setBean(meta);
		adEje = new ADEje();;
		cbEjes.setItems(adEje.obtenerTodosEjes());

		enlazarDatos();
	}

    public void enlazarExterno(Meta meta) {
        this.meta = meta;

		List<Eje> ejes = adEje.obtenEjes();

		for(int i = 0; i < ejes.size(); i++){
			if(ejes.get(i).getId_eje() == meta.getId_eje().getId_eje()){
				cbEjes.setSelectedItem(ejes.get(i));
				break;
			}
		}

        binder.setBean(meta);
        btnRegistrar.setCaption("Actualizar");
        edicion = true;
    }


	public void enlazarDatos() {

		binder.forField(tfNombre)
				.asRequired("El nombre es requerido")
				.withValidator(nombre -> nombre.length() >= 5, "El nombre debe contener al menos 5 caracteres")
				.bind(Meta::getNombre, Meta::setNombre);

		binder.forField(taDescripción)
				.asRequired("La descripción es requerida")
				.withValidator(nombre -> nombre.length() >= 10, "La descripción debe contener al menos 5 caracteres")
				.bind(Meta::getDescripcion, Meta::setDescripcion);
		
		btnRegistrar.addClickListener(new CrudUsuario());

		btnCancelar.addClickListener((ClickListener) clickEvent -> {
            edicion = false;
            btnRegistrar.setCaption("Registrar");
            binder.setBean(new Meta());
        });

	}
	
	class CrudUsuario implements ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			if (binder.validate().isOk()){
				meta.setId_eje(cbEjes.getSelectedItem().get());

				boolean ok = false;
				if (edicion){
					ok = adMeta.modificar(meta);
					btnRegistrar.setCaption("Registrar");
				}
				else{

					ok = adMeta.guardar(meta);

                    ((ListaMetasForm)((TabSheet)MetasForm.this.getParent()).getTab(1).getComponent()).llenaGrid();
				}
				if (ok){
					Notification.show("La meta "+meta.getNombre()+" se "+((edicion)?"actualizó":"agregó")+" correctamente",
							Notification.Type.TRAY_NOTIFICATION);
					meta= new Meta();
					binder.setBean(meta);
					if(edicion){
						((TabSheet)MetasForm.this.getParent()).setSelectedTab(1);
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
