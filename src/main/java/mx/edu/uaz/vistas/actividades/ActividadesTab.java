package mx.edu.uaz.vistas.actividades;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.TabSheet;
import mx.edu.uaz.modelos.Usuario;

/**
 * Created by Victor Hugo on 01/06/2017.
 */
public class ActividadesTab extends TabSheet {

    private static final long serialVersionUID = 2L;

    public ActividadesTab(Usuario user){
        addTab( new MostrarTodasActividades(),"Todas Las Actividades", VaadinIcons.LIST);
        addTab( new MostrarTodasActividades(user),"Mis Actividades", VaadinIcons.LIST);
    }
}
