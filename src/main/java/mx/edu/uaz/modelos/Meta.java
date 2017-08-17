package mx.edu.uaz.modelos;

/**
 * Created by Victor Hugo on 30/05/2017.
 */
public class Meta {

    private Integer id_meta;
    private String nombre;
    private String descripcion;
    private Eje id_eje;

    public Meta(){

    }

    public Meta(Integer id_meta, String nombre, String descripcion, Eje id_eje) {
        this.id_meta = id_meta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_eje = id_eje;
    }

    public Integer getId_meta() {
        return id_meta;
    }

    public void setId_meta(Integer id_meta) {
        this.id_meta = id_meta;
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

    public Eje getId_eje() {
        return id_eje;
    }

    public void setId_eje(Eje id_eje) {
        this.id_eje = id_eje;
    }

    @Override
    public String toString() {
        return  nombre;
    }
}
