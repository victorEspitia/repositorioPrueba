package mx.edu.uaz.principal;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.*;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import mx.edu.uaz.utils.Broadcaster;
import mx.edu.uaz.utils.BuscaComponentes;
import mx.edu.uaz.vistas.InicioSesion;
import mx.edu.uaz.vistas.SpreadsheetReport;

/**
 * This principal is the application entry point. A principal may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The principal is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("estilos")
@JavaScript({
        "vaadin://js/jquery-2.2.3.min.js",
        "vaadin://js/bootstrap.min.js",
        "https://code.jquery.com/ui/1.11.4/jquery-ui.min.js",
        "https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js",
        "vaadin://js/app.min.js",
        "https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js",
        "vaadin://js/bootstrap-datepicker.js",
        "vaadin://js/bootstrap3-wysihtml5.all.min.js",
        "vaadin://js/dashboard.js",
        "vaadin://js/daterangepicker.js",
        "vaadin://js/demo.js",
        "vaadin://js/fastclick.min.js",
        "vaadin://js/jquery-jvectormap-1.2.2.min.js",
        "vaadin://js/jquery-jvectormap-world-mill-en.js",
        "vaadin://js/jquery.knob.js",
        "vaadin://js/jquery.slimscroll.min.js",
        "vaadin://js/jquery.sparkline.min.js",
        "vaadin://js/morris.min.js"
})

@StyleSheet({
        "vaadin://css/estilo.css",
        "vaadin://css/_all-skins.min.css",
        "vaadin://css/bootstrap.min.css",
        "vaadin://css/AdminLTE.min.css",
        "vaadin://css/blue.css",
        "vaadin://css/bootstrap3-wysihtml5.css",
        "vaadin://css/datepicker3.css",
        "vaadin://css/daterangepicker.css",
        "vaadin://css/jquery-jvectormap-1.2.2.css",
        "vaadin://css/index.css",
        "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css",
        "https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css"
})
@Viewport("width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no")
@Push(PushMode.MANUAL)
public class InicioUI extends UI implements Broadcaster.BroadcastListener {

    @WebServlet(urlPatterns = "/*", name = "InicioUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = InicioUI.class, productionMode = false)
    public static class InicioUIServlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new InicioSesion());
        Broadcaster.register(this);
    }

    @Override
    public void dettach() {
        Broadcaster.unregister(this);
        super.detach();
    }

    @Override
    public void receiveBroadcast(String message) {
        access(new Runnable() {
            @Override
            public void run() {
                Notification.show(message, Notification.Type.TRAY_NOTIFICATION);
                SpreadsheetReport spreadsheetreport = (SpreadsheetReport) BuscaComponentes.findComponentById(UI.getCurrent(), "spreadsheetreport");
                if(spreadsheetreport != null) {
                    spreadsheetreport.crearReporte();
                }
                UI.getCurrent().push();
            }
        });
    }


}
