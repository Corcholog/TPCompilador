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
	
	public void addClave(String clave) {
		this.tablaSimbolos.put(clave, new HashMap<String, String>());
	}
	
	public void addAtributo(String clave, String claveAtributo, String atributo) {
		this.tablaSimbolos.get(clave).put(claveAtributo, atributo);
	}

}
