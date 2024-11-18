package paqueton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneradorCodigo {
	private ArrayList<Terceto> tercetos;
	private Stack<Integer> flujoControl;
	private boolean huboError = false;
	


	
	public GeneradorCodigo() {
		this.tercetos = new ArrayList<Terceto>();
		this.flujoControl = new Stack<Integer>();
	}
	
	public void push(int pos) {
		this.flujoControl.push(pos);
	}
	
	public void pop() {
		this.flujoControl.pop();
	}
	
	public Integer peek(){
		return this.flujoControl.peek();
	}
	
	public int getPosActual() {
		return this.tercetos.size()-1;
	}
	
	public void eliminarTerceto(int i) {
		this.tercetos.remove(i);
	}
	
	public int getCantTercetos() {
		return this.tercetos.size();
	}

	public ArrayList<Terceto> getTercetos() {
		return new ArrayList<Terceto>(this.tercetos);
	}
	
	public void huboError() {
		this.huboError = true;
	}
	
	public String addTerceto(String op, String op1, String op2) {
		this.tercetos.add(new Terceto(op, op1, op2));
		return "[" + (this.tercetos.size()-1) + "]";
	}
	
	public String addTerceto(String op, String op1, String op2, String tipo) {
		this.tercetos.add(new Terceto(op, op1, op2, tipo));
		//System.out.println("Agregamos: [" + op + ", " + op1 + ", "+ op2 + "]");
		return "[" + (this.tercetos.size()-1) + "]";
	}
	
	public Terceto getTerceto(int pos) {
		if(pos >= this.getCantTercetos()) {
			return null;
		}
		return this.tercetos.get(pos);
	}
	
	public boolean esTercetoTripla(String t, TablaSimbolos ts) {
		if(t.startsWith("[")) {
			Terceto terceto = this.getTerceto(Integer.parseInt(t.substring(1,t.length()-1)));
			String tipo = terceto.getTipo();
			if(!ts.getAtributo(tipo, AccionSemantica.USO).equals("nombre de tipo tripla")) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	public void invertirCondicion(String terceto) {
		if(terceto != null) {
			Terceto t = this.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1)));
			String operador = t.getOperador();
			switch (operador) {
			case "<": t.setOperador(">="); break;
			case "<=": t.setOperador(">"); break;
			case ">": t.setOperador("<="); break;
			case ">=": t.setOperador("<"); break;
			case "=": t.setOperador("!="); break;
			case "!=": t.setOperador("="); break;
			case "AND": t.setOperador("NAND"); break;
			default:
			}
		}
		
	}
	
	public void checkTipoRetorno(String retorno, String funcion, TablaSimbolos ts, int linea) {
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
		
	    Matcher matcher = pattern.matcher(retorno);
	    if(matcher.find()) {
	    	String tipo = this.getTerceto(Integer.parseInt(retorno.substring(1, retorno.length()-1))).getTipo();
	    	if(!tipo.equals(ts.getAtributo(funcion, AccionSemantica.TIPO))) {
	    		ErrorHandler.addErrorSemantico("El tipo del retorno es distinto al de la funcion.", linea);
	    	}
	    }else {
	    	if(!ts.getAtributo(retorno, AccionSemantica.TIPO).equals(ts.getAtributo(funcion, AccionSemantica.TIPO))) {
	    		ErrorHandler.addErrorSemantico("El tipo del retorno es distinto al de la funcion.", linea);
	    	}
	    }
	}
	
	public int updateAndCheckSize(int pos, String op2, int lineaActual, TablaSimbolos ts, String ambitoActual) {
		if(pos >= this.getCantTercetos()) {
			ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lineaActual);
		} else {
			Terceto t = this.getTerceto(pos);
			int newPos = pos;
			while(newPos < this.getPosActual() && !t.getOperador().equals("COMP")) {
				newPos++;
				t = this.getTerceto(newPos);
			}
			if(t.getOperador().equals("COMP")) {
				t.setOp2(op2);
			}
			this.checkTipo(newPos, lineaActual, ts, ambitoActual, "");
			pos = newPos;
		}
		return pos;
	}
	
	public String checkTipo(int pos, int lineaActual, TablaSimbolos ts, String ambitoActual, String operando) {
		Terceto t = this.getTerceto(pos);
		String op_izq = t.getOp1();
		String op_der = t.getOp2();
		
		
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
		
	    Matcher matcher_izq = pattern.matcher(op_izq);
	    Matcher matcher_der = pattern.matcher(op_der);
	    
	    boolean matchres_izq = matcher_izq.find();
	    boolean matchres_der = matcher_der.find();
	    boolean noDeclarado = false;
	    
	    String id_izq="";
	    String id_der="";
	    String tipo_der="";
	    String tipo_izq = "";
	    String retorno_der = "";
	    String retorno_izq = "";
	    
	    if(matchres_izq) {
	    	Terceto t1 = this.getTerceto(Integer.parseInt(matcher_izq.group(1)));
	    	id_izq = "";
	    	retorno_izq = op_izq;
	    	tipo_izq = t1.getTipo();
	    	if(tipo_izq.equals("error")) {
    			noDeclarado = true;
	    	}
	    } else {
	    	id_izq = op_izq;
	    	retorno_izq = id_izq;
		    id_izq = checkDeclaracion(id_izq, lineaActual, ts,ambitoActual); 
	    	tipo_izq = ts.getAtributo(id_izq, AccionSemantica.TIPO);
	    }
	    
	    if(matchres_der) {
	    	Terceto t2 = this.getTerceto(Integer.parseInt(matcher_der.group(1)));
    		id_der = "";
    		retorno_der = op_der;
    		tipo_der = t2.getTipo();
	    	if(tipo_der.equals("error")) {
    			noDeclarado = true;
    		}
	    } else {
	    	id_der = op_der;
	    	retorno_der = op_der;
		    id_der = checkDeclaracion(id_der, lineaActual, ts,ambitoActual); 
		    tipo_der = ts.getAtributo(id_der, AccionSemantica.TIPO);
	    }
	    
	    if((id_izq == null) || (id_der == null)) {
	    	noDeclarado = true;
	    }
	    
	    if(!noDeclarado) {
	    	if(!tipo_der.equals(tipo_izq)) {
	    		if(id_der.equals("")) {
	    			ErrorHandler.addErrorSemantico("Tipo inesperado en comparacion. La variable izquierda " + id_izq + " es " + tipo_izq + " y se compara con " + tipo_der, lineaActual);
	    		}
	    		else {
	    			ErrorHandler.addErrorSemantico("Tipo inesperado en comparacion. La variable izquierda es " + tipo_izq + " y la variable derecha " + id_der +  " es " + tipo_der, lineaActual);
	    		}
	    	}
	    }else {
	    	if(id_izq == null) {
	    		ErrorHandler.addErrorSemantico("La variable del lado izquierdo de la comparacion no esta declarada o no esta al alcance.", lineaActual);
	    	}
	    	if(id_der == null){
	    		ErrorHandler.addErrorSemantico("La variable del lado derecho de la comparacion no esta declarada o no esta al alcance.", lineaActual);
	    	}
	    	
	    }
	    
	    t.setTipo(tipo_izq);
	    if(operando == "") {
	    	return null;
	    }
	    
	    this.eliminarTerceto(pos);
	    return this.addTerceto(operando, retorno_izq, retorno_der, tipo_der);
	}
	
	public int getPosCorchetes(String input) {
	    Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(input);
	    return Integer.parseInt(matcher.group(1));
	}

	public void checkParamReal(String expresion, int lineaActual, TablaSimbolos ts, String ambitoActual,String funcion) {
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(expresion);
	    String tipo = "";
	    boolean estaDeclarado = false;
	    if(!matcher.find()) {	
	    	String parametro = checkDeclaracion(expresion, lineaActual, ts, ambitoActual);
	    	estaDeclarado = parametro != null;
	    	tipo = ts.getAtributo(parametro, AccionSemantica.TIPO);
	    } else {
	    	Terceto tripla = this.getTerceto(Integer.parseInt(matcher.group(1)));
	    	estaDeclarado = true;
	    	tipo = tripla.getTipo();
	    }
	    
	    if (!estaDeclarado) {
	    	ErrorHandler.addErrorSemantico("el parametro real " +expresion+ "  no esta al alcance", lineaActual);
	    }
	    
	    
	    
	    if(!ts.getAtributo(ts.getAtributo(funcion , AccionSemantica.PARAMETRO), AccionSemantica.TIPO).equals(tipo)){ 
	    	ErrorHandler.addErrorSemantico("El tipo del parametro real no coincide con el tipo del parametro formal.", lineaActual);
    	}
	}
	
	public String checkTipoAsignacion(String id, int lineaActual, String opAsig, TablaSimbolos ts, String ambitoActual) {
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(opAsig);
	    
	    Matcher matcher_id = pattern.matcher(id);
	    boolean matchres_id = matcher_id.find();
	    boolean matchres = matcher.find();
	    boolean declarado = true;
	    
	    String id_izq="";
	    String id_der="";
	    String tipo_der="";
	    String tipo_izq = "";
	    
	    if(matchres_id) {
	    	Terceto t = this.getTerceto(Integer.parseInt(matcher_id.group(1)));
	    	id_izq = t.getOp1();
		    id_izq = checkDeclaracion(id_izq, lineaActual, ts,ambitoActual); 
	    	tipo_izq = ts.getAtributo(id_izq, AccionSemantica.TIPO_BASICO);
	    	id_izq = id;
	    } else {
	    	id_izq = id;
		    id_izq = checkDeclaracion(id_izq, lineaActual, ts,ambitoActual); 
		    tipo_izq = ts.getAtributo(id_izq, AccionSemantica.TIPO);
	    }
	    
	    if(id_izq == null) {
	    	declarado = false;
	    }
	    
	    if(matchres) { 
	    	Terceto t = this.getTerceto(Integer.parseInt(matcher.group(1)));
	    	if(t.getOperador().equals("ACCESOTRIPLE")) {
	    		id_der = t.getOp1();
			    id_der = checkDeclaracion(id_der, lineaActual, ts,ambitoActual); 
			    id_der = opAsig;
	    		tipo_der = t.getTipo();
	    	} else {
	    		id_der = opAsig;
	    		tipo_der = t.getTipo();
	    	}
	    	if(tipo_der.equals("error")) {
    			declarado = false;
    		}
	    } else {
	    	id_der = opAsig;
		    id_der = checkDeclaracion(id_der, lineaActual, ts,ambitoActual); 
		    tipo_der = ts.getAtributo(id_der, AccionSemantica.TIPO);
	    	if(id_der == null) {
	    		declarado = false;
	    	}
	    }
	    
	    if(declarado) {
	    	if(!tipo_der.equals(tipo_izq)) {
	    		if(id_der.equals("")) {
	    			ErrorHandler.addErrorSemantico("Tipo inesperado en asignacion. La variable izquierda " + id_izq + " es " + tipo_izq + " y lo que se asigna es " + tipo_der, lineaActual);
	    		}
	    		else {
	    			ErrorHandler.addErrorSemantico("Tipo inesperado en asignacion. La variable izquierda " + id_izq + " es " + tipo_izq + " y la variable derecha " + id_der +  " es " + tipo_der, lineaActual);
	    		}
	    	}
	    }else {
	    	ErrorHandler.addErrorSemantico("La variable no esta declarada en la Asignacion",lineaActual);
	    }
	    
	    return this.addTerceto(":=", id_izq, id_der, tipo_der);
	}

	public String obtenerVariableSinAmbito(String texto) {
        String[] partes = texto.split(":");
        if (partes.length > 1) {
            return partes[partes.length - 1];
        } else {
            return texto;
        }
    }
	
	public String getTipoAccesoTripla(String acceso, TablaSimbolos ts) {
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(acceso);
	    
	    if(matcher.find()) {
	    	return this.getTerceto(Integer.parseInt(acceso.substring(1, acceso.length()-1))).getTipo();
	    } else {
	    	return ts.getAtributo(acceso, AccionSemantica.TIPO);
	    }
	}

	public String checkDeclaracion(String id, int lineaInicial, TablaSimbolos ts,String ambito) {
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(id);
	    
		if (id.matches("^[0-9].*") || id.matches("^-.*") || matcher.find()) {
			return id;
		} 
		else {
			String var = obtenerVariableSinAmbito(id);
			String[] partes = ambito.split(":");
			// Usamos un for inverso para eliminar desde el ambito actual hacia el global
			for (int i = partes.length - 1; i >= 0; i--) {
			    // Unimos las partes desde el índice 0 hasta i
			    String nuevaCadena = String.join(":", Arrays.copyOfRange(partes, 0, i + 1));
			    String claveTs= nuevaCadena + ":" + var;
			    if (ts.estaEnTablaSimbolos(claveTs)) {
			    	return claveTs;
			    }
			}
			ErrorHandler.addErrorSemantico("La variable " + id + " no esta al alcance o no fue declarada.",  lineaInicial);
			return null;
		}
	}
	
	public String checkTipoExpresion(String op_izq, String op_der, int lineaActual, TablaSimbolos ts, String operando,String ambitoActual) {
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(op_der);
	    
	    Matcher matcher_id = pattern.matcher(op_izq);
	    boolean matchres_id = matcher_id.find();
	    boolean matchres = matcher.find();
	    boolean declarado = true;
	    
	    String id_izq="";
	    String id_der="";
	    String tipo_der="";
	    String tipo_izq = "";
	    
	    
	    if(matchres_id) {
	    	id_izq = op_izq;
	    	Terceto tripla = this.getTerceto(Integer.parseInt(matcher_id.group(1)));
	    	tipo_izq = tripla.getTipo();
	    	if(tipo_der.equals("error")) {
    			declarado = false;	    
	    	}	
	    } else {
	    	id_izq = op_izq;
		    id_izq = checkDeclaracion(id_izq, lineaActual, ts,ambitoActual); 
	    	tipo_izq = ts.getAtributo(id_izq, AccionSemantica.TIPO);
	    	if (tipo_izq.equals("")) {
	    		tipo_izq = "error";
	    	}
	    }
	    
	    if(id_izq == null) {
	    	declarado = false;
	    }
	    
	    if(matchres) { 
	    	id_der = op_der;
	    	Terceto asig = this.getTerceto(Integer.parseInt(matcher.group(1)));
	    	tipo_der = asig.getTipo();
	    	if(tipo_der.equals("error")) {
    			declarado = false;
	    	}
    		
	    } else {
	    	id_der = op_der;
		    id_der = checkDeclaracion(id_der, lineaActual, ts,ambitoActual); 
		    tipo_der = ts.getAtributo(id_der, AccionSemantica.TIPO);

	    	if(id_der == null) {
	    		declarado = false;
	    	}
	    }
	    
	    if(declarado) {
	    	if(!tipo_der.equals(tipo_izq)) {
	    			ErrorHandler.addErrorSemantico("Tipo inesperado en la expresion. La variable izquierda " + id_izq + " es " + tipo_izq + " y la variable derecha " + id_der +  " es " + tipo_der, lineaActual);
	    	}
	    }
	    else {
			ErrorHandler.addErrorSemantico("alguna de las variables no esta declarada en la expresion",lineaActual);
	    }
	    
	    String retorno = this.addTerceto(operando, id_izq, id_der, tipo_izq);
    	return retorno;
		
	}
	
	public String updateCompAndGenerate(int pos, String comp, int sizePatronIzq, int sizePatronDer, int lineaActual) {
		if(sizePatronIzq < sizePatronDer) {
			ErrorHandler.addErrorSemantico("La cantidad de elementos del patron izquierdo es menor a la del patron derecho", lineaActual);
		} else if (sizePatronIzq > sizePatronDer){
			ErrorHandler.addErrorSemantico("La cantidad de elementos del patron derecho es menor a la del patron izquierdo", lineaActual);
		} else {
			ArrayList<Integer> comparadores = new ArrayList<>();
		    for (int i = pos; i < this.tercetos.size(); i++) {
		        Terceto t = this.getTerceto(i);
		        if(t.getOperador().equals("COMP")) {
		        	t.setOperador(comp);
		        	comparadores.add(i);
		        }
		    }
		    int ultimoTercetoGenerado = pos;
		    this.addTerceto("AND", "[" + comparadores.getFirst() + "]", "[" + comparadores.get(1) + "]");
		    ultimoTercetoGenerado = this.tercetos.size() - 1; 
		    for (int i = 2; i < comparadores.size(); i++) {
		    	this.addTerceto("AND", "[" + ultimoTercetoGenerado + "]", "[" + comparadores.get(i) + "]");
		        ultimoTercetoGenerado = this.tercetos.size() - 1; 
			}
		    return "[" + ultimoTercetoGenerado + "]";
		}
		return "error";
	}
	
	public void actualizarBI(int ref) {
		if(!huboError) {
			this.tercetos.get(this.flujoControl.peek()).setOp1("[" + String.valueOf(ref) + "]");
		}
		
	}
	
	public void actualizarBF(int ref) {
		if(!huboError) {
			this.tercetos.get(this.flujoControl.peek()).setOp2("[" + String.valueOf(ref) + "]");
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int cont = 0;
		for (Terceto terceto : tercetos) {
			sb.append("\n" + cont + ". (" + terceto.getOperador() + ", " + terceto.getOp1() + ", " + terceto.getOp2() +") tipo: "+ terceto.getTipo());
			cont++;
		}
		return sb.toString();
	}
}