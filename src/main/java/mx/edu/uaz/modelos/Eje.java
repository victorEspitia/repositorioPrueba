package mx.edu.uaz.modelos;

/**
 * Created by Victor Hugo on 30/05/2017.
 */
public class Eje {

    private Integer id_eje;
    private String nombre;
    private String descripcion;

    public Eje(){

    }

    public Eje(Integer id_eje, String nombre, String descripcion) {
        this.id_eje = id_eje;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getId_eje() {
        return id_eje;
    }

    public void setId_eje(Integer id_eje) {
        this.id_eje = id_eje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
