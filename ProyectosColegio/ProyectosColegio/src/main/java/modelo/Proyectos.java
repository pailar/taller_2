package modelo;

public class Proyectos {
    private int id;
    private String nombre;
    private int idFuncionarioResponsable;

    public Proyectos() {
    }

    public Proyectos(int id, String nombre, int idFuncionarioResponsable) {
        this.id = id;
        this.nombre = nombre;
        this.idFuncionarioResponsable = idFuncionarioResponsable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdFuncionarioResponsable() {
        return idFuncionarioResponsable;
    }

    public void setIdFuncionarioResponsable(int idFuncionarioResponsable) {
        this.idFuncionarioResponsable = idFuncionarioResponsable;
    }

    @Override
    public String toString() {
        return "Proyectos{" + "id=" + id + ", nombre=" + nombre + ", idFuncionarioResponsable=" + idFuncionarioResponsable + '}';
    }
}