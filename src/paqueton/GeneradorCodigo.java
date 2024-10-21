package paqueton;

import java.util.ArrayList;
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
	
	public int getCantTercetos() {
		return this.tercetos.size();
	}

	public ArrayList<Terceto> getTercetos() {
		return this.tercetos;
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
		return "[" + (this.tercetos.size()-1) + "]";
	}
	
	public Terceto getTerceto(int pos) {
		return this.tercetos.get(pos);
	}
	
	public void updateAndCheckSize(int pos, String op2, int lineaActual, TablaSimbolos ts) {
		if(pos >= this.getCantTercetos()) {
			ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lineaActual);
		} else {
			Terceto t = this.getTerceto(pos);
			t.setOp2(op2);
			this.checkTipo(pos, lineaActual, ts);
		}
	}
	
	public void checkTipo(int pos, int lineaActual, TablaSimbolos ts) {
		Terceto t = this.getTerceto(pos);
		String op1 = t.getOp1();
		String op2 = t.getOp2();
		String tipoOp1 = ts.getAtributo(op1, AccionSemantica.TIPO);
		String tipoOp2 = ts.getAtributo(op2, AccionSemantica.TIPO);
		if(tipoOp1.isEmpty()) {
			ErrorHandler.addErrorSemantico("El operando " + op1 + " no esta declarado", lineaActual);
		}
		if(tipoOp2.isEmpty()) {
			ErrorHandler.addErrorSemantico("El operando " + op2 + " no esta declarado", lineaActual);
		}
		else if(!tipoOp1.equals(tipoOp2) && !tipoOp1.isEmpty()) {
			ErrorHandler.addErrorSemantico("El tipo de los operandos no es igual. El operando " + op1 + " es " + tipoOp1 + " y el operando " + op2 + " es " + tipoOp2, lineaActual);
		}
	}
	
	public int getPosCorchetes(String input) {
	    Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(input);
	    return Integer.parseInt(matcher.group(1));
	}

	public void checkParamReal(String expresion, int lineaActual, TablaSimbolos ts, String funcionActual) {
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(expresion);
	    String tipo = "";
	    if(!matcher.find()) {
	    	tipo = ts.getAtributo(expresion, AccionSemantica.TIPO);
	    } else {
	    	Terceto tripla = this.getTerceto(Integer.parseInt(matcher.group(1)));
	    	tipo = ts.getAtributo(tripla.getOp1(), AccionSemantica.TIPO_BASICO);
	    }
	    
	    if(!ts.getAtributo(ts.getAtributo(funcionActual, AccionSemantica.PARAMETRO), AccionSemantica.TIPO).equals(tipo)){ 
    		ErrorHandler.addErrorSemantico("El tipo del parametro real no coincide con el tipo del parametro formal.", lineaActual);
    	}
	}
	
	public void checkTipoAsignacion(String id, int lineaActual, String opAsig, TablaSimbolos ts) {
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(opAsig);
	    
	    Matcher matcher_id = pattern.matcher(id);
	    boolean matchres_id = matcher_id.find();
	    boolean matchres = matcher.find();
	    boolean noDeclarado = false;
	    
	    String id_izq="";
	    String id_der="";
	    String tipo_der="";
	    String tipo_izq = "";
	    
	    if(matchres_id) {
	    	Terceto tripla = this.getTerceto(Integer.parseInt(matcher_id.group(1)));
	    	id_izq = tripla.getOp1();
	    	tipo_izq = ts.getAtributo(id_izq, AccionSemantica.TIPO_BASICO);
	    } else {
	    	id_izq = id;
	    	tipo_izq = ts.getAtributo(id_izq, AccionSemantica.TIPO);
	    }
	    if(!checkDeclaracion(id_izq, lineaActual, ts)) {
	    	noDeclarado = true;
	    }
	    
	    if(matchres) { // tengo terceto lado derecho
	    	Terceto asig = this.getTerceto(Integer.parseInt(matcher.group(1)));
	    	if(asig.getOperador().equals("ACCESOTRIPLE")) {
	    		id_der = asig.getOp1();
		    	tipo_der = asig.getTipo();
	    	} else {
	    		tipo_der = asig.getTipo();
	    	}
	    	if(tipo_der.equals("error")) {
    			noDeclarado = true;
    		}
	    } else {
	    	id_der = opAsig;
	    	tipo_der = ts.getAtributo(id_der, AccionSemantica.TIPO);
	    	
	    	if(!checkDeclaracion(id_der, lineaActual, ts)) {
	    		noDeclarado = true;
	    	}
	    }
	    
	    if(!noDeclarado) {
	    	if(!tipo_der.equals(tipo_izq)) {
	    		if(id_der.equals("")) {
	    			ErrorHandler.addErrorSemantico("Tipo inesperado en asignacion. La variable izquierda " + id_izq + " es " + tipo_izq + " y lo que se asigna es " + tipo_der, lineaActual);
	    		}
	    		else {
	    			ErrorHandler.addErrorSemantico("Tipo inesperado en asignacion. La variable izquierda " + id_izq + " es " + tipo_izq + " y la variable derecha " + id_der +  " es " + tipo_der, lineaActual);
	    		}
	    	}
	    }
	}
	
	public boolean checkDeclaracion(String id, int lineaInicial, TablaSimbolos ts) {
		if(!ts.estaDeclarada(id)) {
			ErrorHandler.addErrorSemantico("La variable " + id + " nunca fue declarada", lineaInicial);
			return false;
		}
		return true;
	}
	
	public String checkTipoExpresion(String op_izq, String op_der, int lineaInicial, TablaSimbolos ts, String operando) {
		Pattern pattern_izq = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher_izq = pattern_izq.matcher(op_izq);
	    Pattern pattern_der = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher_der = pattern_der.matcher(op_der);
	    
	    // ambos son tercetos
	    if (matcher_izq.find() && matcher_der.find()) {
	    	Terceto t_izq = this.getTerceto(Integer.parseInt(matcher_izq.group(1)));
	    	Terceto t_der = this.getTerceto(Integer.parseInt(matcher_der.group(1)));
	    	String tipo_izq = t_izq.getTipo();
	    	String tipo_der = t_der.getTipo();
	    	if(!tipo_izq.equals(tipo_der)) {
	    		ErrorHandler.addErrorSemantico("El tipo de los operandos es distinto. Del lado izquierdo se tiene " + tipo_izq + " y del lado derecho se tiene " + tipo_der + " .", lineaInicial);
	    		return this.addTerceto(operando, op_izq, op_der, "error");
	    	}
	    	return this.addTerceto(operando, op_izq, op_der, tipo_izq);
	    } else if(!matcher_izq.find() && !matcher_der.find()){
	    	String tipo_izq = ts.getAtributo(op_izq, "tipo");
	    	String tipo_der = ts.getAtributo(op_der, "tipo");
	    	if(!tipo_izq.equals(tipo_der)) {
	    		ErrorHandler.addErrorSemantico("El tipo de los operandos es distinto. Del lado izquierdo se tiene " + op_izq + " del tipo " + tipo_izq + " y del lado derecho se tiene " + op_der + " del tipo " + tipo_der + " .", lineaInicial);
	    		return this.addTerceto(operando, op_izq, op_der, "error");
	    	}
	    	return this.addTerceto(operando, op_izq, op_der, tipo_izq);
	    } else if(matcher_izq.find() && !matcher_der.find()) {
	    	Terceto t_izq = this.getTerceto(Integer.parseInt(matcher_izq.group(1)));
	    	String tipo_izq = t_izq.getTipo();
	    	String tipo_der = ts.getAtributo(op_der, "tipo");
	    	if(!tipo_izq.equals(tipo_der)) {
	    		ErrorHandler.addErrorSemantico("El tipo de los operandos es distinto. Del lado izquierdo se tiene " + tipo_izq + " y del lado derecho se tiene "+ op_der + " del tipo " + tipo_der + " .", lineaInicial);
	    		return this.addTerceto(operando, op_izq, op_der, "error");
	    	}
	    	return this.addTerceto(operando, op_izq, op_der, tipo_izq);
	    } else {
	    	Terceto t_der = this.getTerceto(Integer.parseInt(matcher_der.group(1)));
	    	String tipo_der = t_der.getTipo();
	    	String tipo_izq = ts.getAtributo(op_izq, "tipo");
	    	if(!tipo_izq.equals(tipo_der)) {
	    		ErrorHandler.addErrorSemantico("El tipo de los operandos es distinto. Del lado izquierdo se tiene " + tipo_izq + " y del lado derecho se tiene " + tipo_der + " .", lineaInicial);
	    		return this.addTerceto(operando, op_izq, op_der, "error");
	    	}
	    	return this.addTerceto(operando, op_izq, op_der, tipo_izq);
	    }
	    
	    
	}
	
	public String updateCompAndGenerate(int pos, String comp) {
		System.out.println("AAAAA");
	    for (int i = pos; i < this.tercetos.size(); i++) {
	        Terceto t = this.getTerceto(i);
	        t.setOperador(comp);
	    }

	    int ultimoTercetoGenerado = pos;
	    
	    this.addTerceto("AND", "[" + pos + "]", "[" + (pos + 1) + "]");
	    ultimoTercetoGenerado = this.tercetos.size() - 1; 
	    
	    int size = this.tercetos.size();

	    for (int i = pos + 2; i < size - 1; i++) {
	        this.addTerceto("AND", "[" + ultimoTercetoGenerado + "]", "[" + i + "]");
	        ultimoTercetoGenerado = this.tercetos.size() - 1; 
	    }

	    return "[" + ultimoTercetoGenerado + "]";
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
			sb.append("\n" + cont + ". (" + terceto.getOperador() + ", " + terceto.getOp1() + ", " + terceto.getOp2() +")");
			cont++;
		}
		return sb.toString();
	}
}