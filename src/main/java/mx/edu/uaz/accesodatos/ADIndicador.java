package mx.edu.uaz.accesodatos;

import com.vaadin.ui.Notification;
import mx.edu.uaz.modelos.Eje;
import mx.edu.uaz.modelos.Indicador;
import mx.edu.uaz.modelos.Meta;
import mx.edu.uaz.persistencia.PersistenciaSesion;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by Victor Hugo on 31/05/2017.
 */
public class ADIndicador {

    public boolean guardarIndicador(Indicador indicador){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.insert("guardarIndicador", indicador);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al guardar el indicador ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public List<Indicador> obtenIndicadores(){
        List<Indicador> indicador = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            indicador = sesion.selectList("obtenIndicadores");

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de metas ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return indicador;
    }

    public List<Eje> obtenEjesByIndicadores(int id_meta){
        List<Eje> indicador = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            indicador = sesion.selectList("obtenEjesByIndicadores", id_meta);

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de Ejes del Indicador ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return indicador;
    }

    public boolean modificarIndicador(Indicador indicador){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.update("modificarIndicador", indicador);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al modificar el indicador ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public List<Indicador> obtenIndicadoresByidMeta(int id_meta){
        List<Indicador> indicador = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            indicador = sesion.selectList("obtenIndicadoresByidMeta", id_meta);

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de Ejes del Indicador ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return indicador;
    }

    public boolean eliminarIndicadores(List<Indicador> indicadores){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {

            for (Indicador i : indicadores) {
                sesion.delete("borrarIndicador", i.getId_Indicador());
            }

            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al borrar los indicadores ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;

    }
}