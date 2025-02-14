package paqueton;

import java.util.HashMap;

public class ControlTagAmbito {
	private HashMap<String,Boolean> tags = new HashMap<String,Boolean>();
	
	public void huboGoto(String tag) {
		if(!tags.containsKey(tag))
			tags.put(tag, false);
	}
	
	public void declaracionTag(String tag, int linea) {
		if(!tags.containsKey(tag))
			ErrorHandler.addErrorSemantico("No se pueden declarar etiquetas antes de un GOTO (no se permite saltar hacia arriba).", linea);
		else
			tags.replace(tag, true);
	}
	
	public void tagsValidos(int linea) {
		for(String s : tags.keySet()) {
			if(tags.get(s)==false) {	
				ErrorHandler.addErrorSemantico("Se hace un goto a la etiqueta: "+s+" la cual no ha sido de declarada en el ambito.", linea);
			}
		}
	}
}
