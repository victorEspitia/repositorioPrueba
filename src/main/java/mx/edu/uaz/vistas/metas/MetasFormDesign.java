package mx.edu.uaz.vistas.metas;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.ComboBox;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class MetasFormDesign extends CssLayout {
    protected ComboBox<mx.edu.uaz.modelos.Eje> cbEjes;
    protected TextField tfNombre;
    protected TextArea taDescripción;
    protected Button btnRegistrar;
    protected Button btnCancelar;

    public MetasFormDesign() {
        Design.read(this);
    }
}