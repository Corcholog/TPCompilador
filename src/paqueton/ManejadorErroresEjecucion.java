package paqueton;

import java.util.HashMap;
import java.util.Map;

public class ManejadorErroresEjecucion {
    private int direccionMemoriaActual = 101; 
    private Map<String, MensajeError> erroresEjecucion = new HashMap<>(); 

    public void agregarError(String clave, String mensaje) {
        MensajeError mensajeError = new MensajeError(direccionMemoriaActual, mensaje);
        erroresEjecucion.put(clave, mensajeError);
        direccionMemoriaActual += mensaje.length(); 
    }

    public Map<String, MensajeError> getErrores() {
        return new HashMap<String, MensajeError>(erroresEjecucion);
    }
    
    public int getDirMax() {
    	return this.direccionMemoriaActual;
    }

    public int getDir(String clave) {
        MensajeError mensajeError = erroresEjecucion.get(clave);
        return (mensajeError != null) ? mensajeError.getDireccionMemoria() : -1;
    }

    public String getMsj(String clave) {
        MensajeError mensajeError = erroresEjecucion.get(clave);
        return (mensajeError != null) ? mensajeError.getMensaje() : null;
    }
    
    public String getSizeMsj(String clave) {
    	return String.valueOf(erroresEjecucion.get(clave).size());
    }
}
