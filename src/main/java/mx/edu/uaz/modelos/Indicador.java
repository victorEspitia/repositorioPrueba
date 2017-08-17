package mx.edu.uaz.modelos;

import java.util.Date;

/**
 * Created by Victor Hugo on 30/05/2017.
 */
public class Indicador {

    private Integer id_indicador;
    private String nombre;
    private String descripcion;
    private Date planeado;
    private Meta id_meta;

    public Indicador(){

    }

    public Indicador(Integer id_indicador, String nombre, String descripcion, Date planeado, Meta id_meta) {
        this.id_indicador = id_indicador;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.planeado = planeado;
        this.id_meta = id_meta;
    }

    public Integer getId_Indicador() {
        return id_indicador;
    }

    public void setId_Indicador(Integer id_indicador) {
        this.id_indicador = id_indicador;
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

    public Date getPlaneado() {
        return planeado;
    }

    public void setPlaneado(Date planeado) {
        this.planeado = planeado;
    }

    public Meta getId_meta() {
        return id_meta;
    }

    public void setId_meta(Meta id_meta) {
        this.id_meta = id_meta;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
