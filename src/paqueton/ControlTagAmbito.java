package paqueton;

import java.util.HashMap;

public class ControlTagAmbito {
	private HashMap<String,Boolean> tags = new HashMap<String,Boolean>();
	
	public void huboGoto(String tag) {
		if(!tags.containsKey(tag))
			tags.put(tag, false);
	}
	
	public void declaracionTag(String tag) {
		if(!tags.containsKey(tag))
			tags.put(tag, true);
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
