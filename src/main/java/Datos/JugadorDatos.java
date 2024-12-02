package Datos;

public class JugadorDatos {
    
    private int id, tipoSuscripcion, forma, cantidadPenalizaciones;
    private float promedio; 
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoSuscripcion() {
        return tipoSuscripcion;
    }

    public void setTipoSuscripcion(int tipoSuscripcion) {
        this.tipoSuscripcion = tipoSuscripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getForma() {
        return forma;
    }

    public void setForma(int forma) {
        this.forma = forma;
    }

    public float getPromedio() {
        return promedio;
    }

    public void setPromedio(float promedio) {
        this.promedio = promedio;
    }

    public int getCantidadPenalizaciones() {
        return cantidadPenalizaciones;
    }

    public void setCantidadPenalizaciones(int cantidadPenalizaciones) {
        this.cantidadPenalizaciones = cantidadPenalizaciones;
    }
    
    
}
