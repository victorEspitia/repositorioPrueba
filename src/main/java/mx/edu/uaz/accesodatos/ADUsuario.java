package mx.edu.uaz.accesodatos;

import com.vaadin.ui.Notification;
import mx.edu.uaz.modelos.Usuario;
import mx.edu.uaz.persistencia.PersistenciaSesion;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ADUsuario {

    public boolean guardarUsuario(Usuario usuario){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            System.out.print(usuario);
            sesion.insert("guardarUsuario", usuario);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al guardar el usuario ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean modificarUsuario(Usuario usuario){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            sesion.update("modificarUsuario", usuario);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al modificar el usuario ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public List<Usuario> obtenUsuarios(){
        List<Usuario> usuarios = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            usuarios = sesion.selectList("obtenUsuarios");

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de usuarios ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return usuarios;
    }

    public List<Usuario> obtenUserNames(){
        List<Usuario> usuarios = null;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {
            usuarios = sesion.selectList("obtenUserNames");

        } catch (Exception e) {
            Notification.show("Error al obtener la lista de usuarios ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return usuarios;
    }

    public boolean eliminarUsuarios(List<Usuario> usuarios){
        boolean ok = false;
        SqlSession sesion = PersistenciaSesion.getSqlMapper().openSession();
        try {

            for (Usuario u : usuarios) {
                sesion.delete("borrarUsuario", u.getId_usuario());
            }

            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al borrar el usuario ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;

    }


}
