package mx.edu.uaz.vistas.indicadores;

import com.vaadin.data.Binder;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import mx.edu.uaz.accesodatos.ADEje;
import mx.edu.uaz.accesodatos.ADIndicador;
import mx.edu.uaz.accesodatos.ADMeta;
import mx.edu.uaz.modelos.Eje;
import mx.edu.uaz.modelos.Indicador;
import mx.edu.uaz.modelos.Meta;
import mx.edu.uaz.utils.LocalDateTimeToDateConverter;

import java.util.List;

public class IndicadoresForm extends IndicadoresFormDesign {
	private Binder<Indicador> binder;
	private Indicador indicador;
	private ADMeta adMeta;
	private ADEje adEje;
	private ADIndicador adIndicador;
	private boolean edicion = false;

	public IndicadoresForm(){
		indicador = new Indicador();
		binder = new Binder<Indicador>(Indicador.class);
		binder.setBean(indicador);
		adEje = new ADEje();;
		adMeta = new ADMeta();
		adIndicador = new ADIndicador();

		cbEjes.setItems(adEje.obtenerTodosEjes());
		cbEjes.addSelectionListener(new SingleSelectionListener<Eje>() {
			@Override
			public void selectionChange(SingleSelectionEvent singleSelectionEvent) {
				Eje eje = (Eje) singleSelectionEvent.getFirstSelectedItem().get();

				List<Meta> metas = adMeta.obtenMetasByIDEje(eje.getId_eje());
				if(metas.size()>0){
					cbMetas.setItems(metas);
					cbMetas.setEnabled(true);
				}else{
					cbMetas.setEnabled(false);
					Notification.show("Seleccionar otra meta, no tiene ninguna meta asignada", Notification.Type.WARNING_MESSAGE);
				}
			}
		});

		enlazarDatos();
	}

    public void enlazarExterno(Indicador indicador) {
		getEjeByIndicador(indicador);
        this.indicador = indicador;
        binder.setBean(indicador);
        btnRegistrar.setCaption("Actualizar");
        edicion = true;
    }

	public void getEjeByIndicador(Indicador indicador){

		List<Eje> eje = adIndicador.obtenEjesByIndicadores(indicador.getId_meta().getId_meta());
		List<Eje> ejes = adEje.obtenEjes();

		for(int i = 0; i < ejes.size(); i++){
			if(ejes.get(i).getId_eje() == eje.get(0).getId_eje()){
				cbEjes.setSelectedItem(ejes.get(i));
				cbMetas.setSelectedItem(adMeta.obtenMetasByIDEje(ejes.get(i).getId_eje()).get(0));
				break;
			}
		}
	}

	public void enlazarDatos() {

		binder.forField(tfNombre)
				.asRequired("El nombre es requerido")
				.withValidator(nombre -> nombre.length() >= 5, "El nombre debe contener al menos 5 caracteres")
				.bind(Indicador::getNombre, Indicador::setNombre);

		binder.forField(taDescripción)
				.asRequired("La descripción es requerida")
				.withValidator(nombre -> nombre.length() >= 10, "La descripción debe contener al menos 5 caracteres")
				.bind(Indicador::getDescripcion, Indicador::setDescripcion);

		binder.forField(dfPlaneado)
				.asRequired("Debe ser requierida una fecha")
				.withConverter(new LocalDateTimeToDateConverter())
				.bind("planeado");
		
		btnRegistrar.addClickListener(new CrudIndicador());

		btnCancelar.addClickListener((ClickListener) clickEvent -> {
            edicion = false;
            btnRegistrar.setCaption("Registrar");
            binder.setBean(new Indicador());
        });

	}
	
	class CrudIndicador implements ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			if (binder.validate().isOk()){

				indicador.setId_meta(cbMetas.getSelectedItem().get());

				boolean ok;
				if (edicion){
					ok = adIndicador.modificarIndicador(indicador);
					btnRegistrar.setCaption("Registrar");
				}
				else{

					ok = adIndicador.guardarIndicador(indicador);

                    ((ListaIndicadoresForm)((TabSheet)IndicadoresForm.this.getParent()).getTab(1).getComponent()).llenaGrid();
				}
				if (ok){
					Notification.show("El indicador  "+indicador.getNombre()+" se "+((edicion)?"actualizó":"agregó")+" correctamente",
							Notification.Type.TRAY_NOTIFICATION);
					indicador= new Indicador();
					binder.setBean(indicador);
					if(edicion){
						((TabSheet)IndicadoresForm.this.getParent()).setSelectedTab(1);
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
