package mx.edu.uaz.persistencia;

import com.vaadin.ui.Notification;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

/**
 * Created by Victor Hugo on 30/05/2017.
 */
public class PersistenciaSesion {
    private static SqlSessionFactory sqlMapper;
    static {
        try {
            Reader reader = Resources.getResourceAsReader("db/ConfigDB.xml");
            sqlMapper = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            Notification.show("Error al leer el archivo de configuración de la base de datos: ", e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    public static SqlSessionFactory getSqlMapper() {
        return sqlMapper;
    }
}