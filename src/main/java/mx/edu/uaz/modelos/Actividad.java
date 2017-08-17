package mx.edu.uaz.modelos;

import java.util.Date;

/**
 * Created by Victor Hugo on 31/05/2017.
 */
public class Actividad {

    private Integer id_actividad;
    private String nombre;
    private String descripcion;
    private Date fecha_inicio;
    private Date fecha_final;
    private String recurso;
    private Recurso id_origenRecurso;
    private Indicador id_indicador;
    private Usuario id_usuario;


    public Actividad(){

    }

    public Actividad(String nombre, String descripcion, Date fecha_inicio, Date fecha_final, String recurso, Recurso id_origenRecurso, Indicador id_indicador, Usuario id_usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.recurso = recurso;
        this.id_origenRecurso = id_origenRecurso;
        this.id_indicador = id_indicador;
        this.id_usuario = id_usuario;
    }

    public Integer getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(Integer id_actividad) {
        this.id_actividad = id_actividad;
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

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public Recurso getId_origenRecurso() {
        return id_origenRecurso;
    }

    public void setId_origenRecurso(Recurso id_origenRecurso) {
        this.id_origenRecurso = id_origenRecurso;
    }

    public Indicador getId_indicador() {
        return id_indicador;
    }

    public void setId_indicador(Indicador id_indicador) {
        this.id_indicador = id_indicador;
    }

    public Usuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return  nombre;
    }
}
