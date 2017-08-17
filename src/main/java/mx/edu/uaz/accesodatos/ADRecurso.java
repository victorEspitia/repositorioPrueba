package mx.edu.uaz.accesodatos;

import com.vaadin.ui.Notification;
import mx.edu.uaz.modelos.Meta;
import mx.edu.uaz.modelos.Recurso;
import mx.edu.uaz.persistencia.PersistenciaSesion;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by Victor Hugo on 31/05/2017.
 */
public class ADRecurso {

    public boolean guardarRecurso(Recurso recurso){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.insert("guardarRecurso", recurso);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al guardar el recurso ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean modificarRecurso(Recurso recurso){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.update("modificarMetas", recurso);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al modificar las metas ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public List<Recurso> obtenRecursos(){
        List<Recurso> recursos = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            recursos = sesion.selectList("obtenRecursos");

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de metas ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return recursos;
    }
}
