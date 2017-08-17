package mx.edu.uaz.modelos;

/**
 * Created by jaetmar on 5/31/17.
 */
public class Usuario {

    private int id_usuario;
    private String nombre;
    private String apellidos;
    private String correo;
    private String cargo;
    private String user;
    private String password;
    private String nombre_comisionado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_comisionado() {
        return nombre_comisionado;
    }

    public void setNombre_comisionado(String nombre_comisionado) {
        this.nombre_comisionado = nombre_comisionado;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos ;
    }
}
