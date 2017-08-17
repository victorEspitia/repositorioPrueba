package mx.edu.uaz.modelos;

/**
 * Created by Victor Hugo on 31/05/2017.
 */
public class Recurso {

    private Integer id_origenRecurso;
    private String nombre_recurso;

    public Recurso(){

    }

    public Recurso(String nombre_recurso) {
        this.nombre_recurso = nombre_recurso;
    }

    public Integer getId_origenRecurso() {
        return id_origenRecurso;
    }

    public void setId_origenRecurso(Integer id_origenRecurso) {
        this.id_origenRecurso = id_origenRecurso;
    }

    public String getNombre_recurso() {
        return nombre_recurso;
    }

    public void setNombre_recurso(String nombre_recurso) {
        this.nombre_recurso = nombre_recurso;
    }

    @Override
    public String toString() {
        return  nombre_recurso ;
    }
}
