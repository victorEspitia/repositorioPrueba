package mx.edu.uaz.accesodatos;

import com.vaadin.ui.Notification;
import mx.edu.uaz.modelos.Meta;
import mx.edu.uaz.persistencia.PersistenciaSesion;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by Victor Hugo on 30/05/2017.
 */
public class ADMeta {

    public boolean guardar(Meta meta){
            boolean ok = false;
            SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
            try {
                sesion.insert("guardarMetas", meta);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("Error al guardar las metas ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
            }
            finally {
                sesion.close();
            }
            return ok;
        }

    public boolean modificar(Meta meta){
            boolean ok = false;
            SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
            try {
                sesion.update("modificarMetas", meta);
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

    public List<Meta> obtenMetas(){
            List<Meta> ejes = null;
            SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
            try {
                ejes = sesion.selectList("obtenMetas");

            } catch (Exception e) {
                Notification.show("Error al obtener la lista de metas ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
            }
            finally {
                sesion.close();
            }
            return ejes;
        }

    public List<Meta> obtenMetasByIDEje(int id_eje){
        List<Meta> ejes = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            ejes = sesion.selectList("obtenMetasByIDEje",id_eje);

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de metas ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ejes;
    }

    public boolean eliminarMetas(List<Meta> metas){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {

            for (Meta m : metas) {
                sesion.delete("borrarMeta", m.getId_meta());
            }

            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al borrar metas ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;

    }
}
