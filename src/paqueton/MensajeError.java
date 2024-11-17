package paqueton;

public class MensajeError {
    private int direccionMemoria;
    private String mensaje;
    private int size;

    public MensajeError(int direccionMemoria, String mensaje) {
        this.direccionMemoria = direccionMemoria;
        this.mensaje = mensaje;
        this.size = mensaje.length();
    }

    public int getDireccionMemoria() {
        return direccionMemoria;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int size() {
        return size;
    }
}
