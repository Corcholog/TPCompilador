package paqueton;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
	private Map<String, Map<String, String>> tablaSimbolos;
	
	public TablaSimbolos() {
		this.tablaSimbolos = new HashMap<String, Map<String, String>>();
	}
	
	public Map<String, String> getAtributos(String key) {
		return this.tablaSimbolos.get(key);
	}
	
	public String getAtributo(String clave, String claveAtributo) {
		return this.tablaSimbolos.get(clave).get(claveAtributo);
	}
	
	public void updateAtributo(String clave, String claveAtributo, String valor) {
		this.tablaSimbolos.get(clave).put(claveAtributo, valor);
	}
	
	public void addClave(String clave) {
		this.tablaSimbolos.put(clave, new HashMap<String, String>());
	}
	
	public void addAtributo(String clave, String claveAtributo, String atributo) {
		this.tablaSimbolos.get(clave).put(claveAtributo, atributo);
	}
	
	public boolean estaEnTablaSimbolos(String s) {
		return tablaSimbolos.containsKey(s.toUpperCase());
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
        return sb.toString();
    }

}
