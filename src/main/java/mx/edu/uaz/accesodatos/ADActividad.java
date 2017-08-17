package mx.edu.uaz.accesodatos;

import com.vaadin.ui.Notification;
import mx.edu.uaz.modelos.Actividad;
import mx.edu.uaz.modelos.Eje;
import mx.edu.uaz.modelos.Indicador;
import mx.edu.uaz.modelos.Meta;
import mx.edu.uaz.persistencia.PersistenciaSesion;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by Victor Hugo on 01/06/2017.
 */
public class ADActividad {

    public boolean guardarActividad(Actividad actividad){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.insert("guardarActividad", actividad);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al guardar la actividad ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public List<Actividad> obtenerTodasActividades(){
        List<Actividad> actividad = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            actividad = sesion.selectList("obtenActividades");

        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de ejes ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return actividad;
    }

    public List<Actividad> obtenerActividadesByUser(int id_usuario){
        List<Actividad> actividad = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            actividad = sesion.selectList("obtenActividadesByUser", id_usuario);

        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de ejes ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return actividad;
    }

    public boolean modificarActividad(Actividad actividad){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.update("modificarActividad", actividad);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al modificar la actividad ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean borrarActividad(int id_actividad){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.delete("borrarActividad", id_actividad);
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

    public Eje obtenEjeByActividad(int id_actividad){
        Eje eje = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            eje = sesion.selectOne("obtenEjeByActividad", id_actividad);

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de Ejes de la actividad ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return eje;
    }

    public List<Actividad> obtenActividadesByEje(int id_eje){
        List<Actividad> actividades = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            actividades = sesion.selectList("obtenActividadesByEje", id_eje);

        } catch (Exception e) {
            Notification.show("Error al obtener la lista actividades por eje ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return actividades;
    }

    public List<Actividad> obtenActividadesByIndicador(int id_indicador){
        List<Actividad> actividades = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            actividades = sesion.selectList("obtenActividadesByIndicador", id_indicador);

        } catch (Exception e) {
            Notification.show("Error al obtener la lista actividades por indicador ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return actividades;
    }

    public Meta obtenMetaByActividad(int id_actividad){
        Meta meta = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            meta = sesion.selectOne("obtenMetaByActividad", id_actividad);

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de Ejes de la actividad ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return meta;
    }

    public Indicador obtenIndicadorByActividad(int id_actividad){
        Indicador indicador = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            indicador = sesion.selectOne("obtenIndicadorByActividad", id_actividad);

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de Ejes de la actividad ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return indicador;
    }
}