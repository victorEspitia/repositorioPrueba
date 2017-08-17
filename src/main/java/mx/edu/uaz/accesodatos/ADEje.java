package mx.edu.uaz.accesodatos;

import com.vaadin.ui.Notification;
import mx.edu.uaz.modelos.Eje;
import mx.edu.uaz.persistencia.PersistenciaSesion;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by Victor Hugo on 30/05/2017.
 */
public class ADEje {


    public boolean guardar(Eje ejes){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.insert("guardarEje", ejes);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al guardar el eje ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean modificar(Eje ejes){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.update("modificarEje", ejes);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al modificar el eje ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public List<Eje> obtenEjes(){
        List<Eje> ejes = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            ejes = sesion.selectList("obtenEjes");

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de ejes ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ejes;
    }

    public List<Eje> obtenerTodosEjes(){
        List<Eje> ejes = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            ejes = sesion.selectList("obtenEjes");

        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de ejes ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ejes;
    }

    public boolean borrar(int id_Eje){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.delete("borrarEjes", id_Eje);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al borrar el eje ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean eliminarEjes(List<Eje> ejes){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {

            for (Eje e : ejes) {
                sesion.delete("borrarEjes", e.getId_eje());
            }

            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al borrar ejes ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;

    }



}
