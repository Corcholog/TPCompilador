package paqueton;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
	private Map<String, Map<String, String>> tablaSimbolos;
	private Map<String, GeneradorCodigo> funciones;
	private Map<String, Integer> cadenasMultilineas;
	
	public TablaSimbolos() {
		this.tablaSimbolos = new HashMap<String, Map<String, String>>();
		this.funciones = new HashMap<String, GeneradorCodigo>();
		this.cadenasMultilineas = new HashMap<String, Integer>();
	}
	
	public void addCadmul(String cadena) {
		this.cadenasMultilineas.put(cadena, null);
	}
	
	public Map<String, Integer> getCadenas(){
		return new HashMap<String, Integer>(this.cadenasMultilineas);
	}
	
	public Integer getPosicionMemoria(String key) {
		return this.cadenasMultilineas.get(key);
	}
	
	public void setPosicionMemoria(String key, Integer value) {
		this.cadenasMultilineas.replace(key, value);
	}
	
	
	public Map<String, GeneradorCodigo> getFunciones(){
		return new HashMap<String, GeneradorCodigo>(funciones);
	}
	
	public Map<String, String> getAtributos(String key) {
		return this.tablaSimbolos.get(key);
	}
	
	public GeneradorCodigo getGCFuncion(String clave) {
		if(!this.funciones.containsKey(clave)) {
			this.addFuncion(clave);
		}
		return this.funciones.get(clave);
	}
	
	public void addFuncion(String clave) {
		this.funciones.put(clave, new GeneradorCodigo());
	}
	
	public String getAtributo(String clave, String claveAtributo) {
	    if (this.tablaSimbolos.containsKey(clave)) {
	        if (this.tablaSimbolos.get(clave).containsKey(claveAtributo)) {
	            return this.tablaSimbolos.get(clave).get(claveAtributo);
	        }
	    }
	    return "";
	}

	public void updateAtributo(String clave, String claveAtributo, String valor) {
		this.tablaSimbolos.get(clave).put(claveAtributo, valor);
	}
	
	public void addClave(String clave) {
		if (!this.tablaSimbolos.containsKey(clave)) {			
			this.tablaSimbolos.put(clave, new HashMap<String, String>());
		}
	}
	
	public void addAtributo(String clave, String claveAtributo, String atributo) {
		if (this.tablaSimbolos.containsKey(clave)) {
			if (!this.tablaSimbolos.get(clave).containsKey(claveAtributo)) {			
				this.tablaSimbolos.get(clave).put(claveAtributo, atributo);
			}	
		}
	}
	

	
	
	public boolean estaEnTablaSimbolos(String s) {
		return tablaSimbolos.containsKey(s);
	}
	
	public boolean estaDeclarada(String s) {
		return tablaSimbolos.containsKey(s) && !getAtributo(s, AccionSemantica.TIPO).equals("");
	}
	
	public boolean esUlongInt(String key) {
		return this.tablaSimbolos.get(key).get(AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT);
	}
	
	public void convertirNegativo(String key) {
		    // Verifica si la cantidad es "1"
	    if (this.tablaSimbolos.get(key).get(AccionSemantica.CANTIDAD).equals("1")) {
	        // Obtener los atributos asociados a la key actual
	        Map<String, String> atributos = this.tablaSimbolos.get(key);
	        // Borrar la key actual
	        this.tablaSimbolos.remove(key);
	        // Generar una nueva key con el signo negativo
	        String nuevaKey = "-" + key;
	        // Insertar la nueva key con los mismos atributos
	        this.tablaSimbolos.put(nuevaKey, atributos);
	    } else {
	        // Obtener los atributos de la key original
	        Map<String, String> atributosOriginales = this.tablaSimbolos.get(key);
	        // Crear una copia de los atributos
	        Map<String, String> copiaAtributos = new HashMap<>(atributosOriginales);
	        // Restarle 1 a la cantidad de la key original
	        int cantidad = Integer.parseInt(atributosOriginales.get(AccionSemantica.CANTIDAD));
	        cantidad -= 1;
	        this.tablaSimbolos.get(key).put(AccionSemantica.CANTIDAD, String.valueOf(cantidad));
	        // Establecer la cantidad de la nueva key negativa en 1
	        copiaAtributos.put(AccionSemantica.CANTIDAD, "1");        
	        // Crear una nueva key con el signo negativo
	        String nuevaKey = "-" + key;
	        // Insertar la nueva key con los atributos copiados
	        this.tablaSimbolos.put(nuevaKey, copiaAtributos);
	    }
	}

	
	
	 @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Iterar sobre las entradas del HashMap exterior
        for (Map.Entry<String, Map<String, String>> entry : tablaSimbolos.entrySet()) {
            String claveExterior = entry.getKey();
            Map<String, String> mapaInterior = entry.getValue();

            // Agregar la clave exterior al StringBuilder
            sb.append("Simbolo: ").append(claveExterior).append("\n");

            // Iterar sobre el mapa interior y agregarlo al StringBuilder
            for (Map.Entry<String, String> entradaInterna : mapaInterior.entrySet()) {
                String claveInterna = entradaInterna.getKey();
                String valorInterno = entradaInterna.getValue();

                // Agregar las claves y valores interiores
                sb.append("    ").append(claveInterna).append(": ").append(valorInterno).append("\n");
            }

            sb.append("\n"); // Línea en blanco para separar los símbolos
        }
        for (Map.Entry<String, GeneradorCodigo> gc : this.funciones.entrySet()) {
			String key = gc.getKey();
			GeneradorCodigo val = gc.getValue();
			sb.append("Tercetos de " + key).append("\n" + val);
		}
        return sb.toString();
    }

}
