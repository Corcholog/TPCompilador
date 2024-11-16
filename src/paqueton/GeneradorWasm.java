package paqueton;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorWasm {
	private TablaSimbolos ts;
	private GeneradorCodigo gc_main;
	private String filePath;
	private String funcionActual;
	private int posicionActual;
	private String ident;
	private String tipoFlujoActual;
	private Stack<String> ifs;
	private Stack<String> fors;
	private Stack<String> bloquesGOTO;
	private boolean aux1EnUso;
	private boolean seAccedioAtripla;
	private Set<String> accesoTriplas = new HashSet<String>();
	private Set<String> gotos = new HashSet<String>();

	
	private StringBuilder variablesGlobales = new StringBuilder();
	private StringBuilder funciones = new StringBuilder();
	private StringBuilder inicioMain = new StringBuilder();
	private StringBuilder cuerpoMain = new StringBuilder(); 
	private StringBuilder variablesActual = new StringBuilder();
	private StringBuilder cuerpoActual = new StringBuilder();
	
	public GeneradorWasm(TablaSimbolos ts, GeneradorCodigo gc, String path) {
        this.ts = ts;
        this.gc_main = gc;
        this.funcionActual = "";
        this.tipoFlujoActual = "IF";
        this.aux1EnUso = false;
        this.seAccedioAtripla = false;
        this.ifs = new Stack<String>();
        this.bloquesGOTO = new Stack<String>();
        this.ifs.add("IF1");
        this.fors = new Stack<String>();
        this.posicionActual = 0;
        this.ident = "";
        this.filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "paqueton" + File.separator + path + ".wat";
        
        // Poner en blanco el archivo al inicializar
        try (FileWriter fw = new FileWriter(filePath, false)) {
            // El archivo se sobrescribe en blanco al abrirse en modo no-apend (append = false)
        } catch (IOException e) {
            System.err.println("Error al vaciar el archivo: " + e.getMessage());
        }
    }
	
	private void reducirIdentacion() {
		int ultimoTabulador = this.ident.lastIndexOf("\t");
		if (ultimoTabulador != -1) {
		    this.ident = this.ident.substring(0, ultimoTabulador) + this.ident.substring(ultimoTabulador + 1);
		}
	}
	
	private void aumentarIdentacion() {
		this.ident += "\t";
	}
	
	public void traducir() {
		this.escribir(this.variablesGlobales,"(module");
		this.escribir(this.variablesGlobales,"(import \"console\" \"log\" (func $log (param i32 i32)))");
		this.escribir(this.variablesGlobales,"(import \"js\" \"mem\" (memory 1))");
		this.cargarVariables();
		this.cargarCadmuls();
		this.escribir(this.variablesGlobales, "");
		this.reducirIdentacion();
		Map<String, GeneradorCodigo> funciones = this.ts.getFunciones();
		for (String funcion : funciones.keySet()) {
			cuerpoActual = new StringBuilder();
			bloquesGOTO = new Stack<String>();
			this.funcionActual = funcion;
			GeneradorCodigo gcFuncion = funciones.get(funcion);
			String parametro = this.ts.getAtributo(funcion, AccionSemantica.PARAMETRO);
			String tipoParam = this.ts.getAtributo(parametro, AccionSemantica.TIPO).equals("double")? "f64":"i32";
			String tipoFuncion = this.ts.getAtributo(funcion, AccionSemantica.TIPO).equals("double")? "f64":"i32";
			this.escribir(this.funciones, "( func $"+funcion.replace(':', 'A')  + " (param $"+ parametro.replace(':', 'A')  + " " + tipoParam+") (result "+ tipoFuncion + ")");
			this.aumentarIdentacion();
			variablesActual = this.funciones;
			this.escribir(variablesActual,"(local $" + funcion.replace(':', 'A') +"retorno " + tipoFuncion + ")");
			this.escribir(cuerpoActual,tipoFuncion+".const 0");
			this.escribir(cuerpoActual,"local.set $"+funcion.replace(':', 'A') +"retorno");
			for (int i = 0; i < gcFuncion.getCantTercetos(); i++) {
				this.posicionActual = i;
				Terceto t = gcFuncion.getTerceto(i);
				if(!t.isHecho()) {
					this.ejecutarTraduccion(t);				
				}
			}
			this.escribir(cuerpoActual,"local.get $"+funcion.replace(':', 'A') +"retorno");
			this.reducirIdentacion();
			this.escribir(cuerpoActual,")\n");
			this.agregarBloquesGoto(this.variablesActual);
			this.funciones.append(variablesActual);
			this.funciones.append(cuerpoActual.toString());
			
		}
		this.cuerpoActual = cuerpoMain;
		this.variablesActual = inicioMain;
		this.escribir(inicioMain,"(func $main");
		this.aumentarIdentacion();
		bloquesGOTO = new Stack<String>();
		for (int i = 0; i < gc_main.getCantTercetos(); i++) {
			Terceto t = gc_main.getTerceto(i);
			this.posicionActual = i;
			if(!t.isHecho()) {
				this.ejecutarTraduccion(t);				
			}
		}
		this.reducirIdentacion();
		this.escribir(cuerpoMain,")");
		this.reducirIdentacion();
		this.escribir(cuerpoMain,"	(export \"main\" (func $main))");
		this.escribir(cuerpoMain,")");
		this.escribeArchivo();
	}
	
	public void agregarBloquesGoto(StringBuilder sb) {
	    while (!bloquesGOTO.isEmpty()) {
	        sb.append(bloquesGOTO.pop()).append("\n");
	    }
	}

	public void cargarCadmuls() {
		Map<String, Integer> cadmuls = this.ts.getCadenas();
		this.escribir(this.variablesGlobales, "(data (i32.const 101)" + "\"Error en ejecucion: El resultado de una operacion sin signo dio negativo.\")");
		this.escribir(this.variablesGlobales, "(data (i32.const 174)" + "\"Error en ejecucion: se realizo una recursion sobre una funcion.\")");
		this.escribir(this.variablesGlobales, "(data (i32.const 237)" + "\"Error en ejecucion: indice fuera de rango.\")");
		Integer dirMem = 279;
		for(String key : cadmuls.keySet()) {
			this.escribir(this.variablesGlobales,"(data (i32.const " + dirMem + ") \"" + key.substring("CADMUL:".length()) + "\")");
			this.ts.setPosicionMemoria(key, dirMem);
			dirMem+= key.length();
		}
	}
	
	public void cargarVariables() {
		Map<String, Map<String, String>> tabla = this.ts.getTabla();
		for (Map.Entry<String, Map<String, String>> entry : tabla.entrySet()) {
			String key = entry.getKey();
			Map<String, String> val = entry.getValue();
			if("nombre variable".equals(val.get(AccionSemantica.USO))) {
				String tipoVar = val.get(AccionSemantica.TIPO);
				switch(tipoVar) {
				case "ulongint": tipoVar = "i32";break;
				case "double": tipoVar = "f64";break;
				default: tipoVar = val.get(AccionSemantica.TIPO_BASICO).equals("ulongint")?"i32":"f64"; 
					String key1 = key + "V1";
					this.escribir(this.variablesGlobales,"(global $"+key1.replace(':', 'A')+" (mut " +tipoVar+")" + "(" + tipoVar + ".const 0))");
					String key2 = key + "V2";
					this.escribir(this.variablesGlobales,"(global $"+key2.replace(':', 'A')+" (mut " +tipoVar+")" + "(" + tipoVar + ".const 0))");
					key = key + "V3";
				break;
				}
				this.escribir(this.variablesGlobales,"(global $"+key.replace(':', 'A')+" (mut " +tipoVar+")" + "(" + tipoVar + ".const 0))");
			}
		}
		this.escribir(this.variablesGlobales, "(global $AUXNEG (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $f64auxTripla (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $i32auxTripla (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX1V1i32 (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX1V2i32 (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX1V3i32 (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX2V1i32 (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX2V2i32 (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX2V3i32 (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX1V1f64 (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX1V2f64 (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX1V3f64 (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX2V1f64 (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX2V2f64 (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX2V3f64 (mut f64) (f64.const 0))");
	}
	
	public void ejecutarTraduccion(Terceto t) {
		String operador = t.getOperador().toLowerCase();
		switch(operador) {
			case "*": this.producto(t); break;
			case "/": this.division(t); break;
			case "+": this.suma(t); break;
			case "-": this.resta(t); break;
			case "<": this.menor(t); break;
			case "<=": this.menorIgual(t); break;
			case ">": this.mayor(t); break;
			case ">=": this.mayorIgual(t); break;
			case "=": this.igual(t); break;
			case "!=": this.distinto(t); break;
			case "and": this.and(t); break;
			case "bi": this.bifurcacionIncondicional(t); break;
			case "bf": this.bifurcacionPorFalso(t); break;
			case ":=": this.asignacion(t); break;
			case "todouble": this.toDouble(t); break;
			case "toulongint": this.toUlongint(t); break;
			case "invoc_fun": this.invocacionFuncion(t); break;
			case "ret": this.retornoFuncion(t); break;
			case "outf": this.outf(t); break;
			case "accesotriple": this.accesoTriple(t);break;
			case "asigtripla" : this.asigTripla(t); break;
			case "goto": this.saltoIncondicional(t); break;
			case "tag": this.generarTag(t); break;
			
			default: 
				if (operador.matches("^label\\d*")) {
					this.generarLabel(t);
			    }
				System.out.println(operador);
			    break;
		}
		t.setHecho(true);
	}
	
	private void asigTripla(Terceto t) {
		this.seAccedioAtripla = true;
		String op1 = t.getOp1();
		String op2 = t.getOp2();
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
		
	    Matcher matcher1 = pattern.matcher(op2);
	    boolean find = matcher1.find();
		if(!find) {
			if(op2.matches("^[0-9].*")) {
				
				this.escribir(cuerpoActual, "i32.const "+op2);
			}else {
				this.escribir(cuerpoActual,"global.get $"+op2.replace(':', 'A'));	
			}
		}
		//LAVIEJA
		if(!this.accesoTriplas.contains(op1)) {
			this.escribir(variablesActual, "(local $accesoAsig"+op1.replace(':', 'A')+ " i32)");
			this.escribir(variablesActual, "(local $acceso"+op1.replace(':', 'A')+ " i32)");
			this.accesoTriplas.add(op1);
		}
		this.escribir(cuerpoActual, "local.set $accesoAsig"+op1.replace(':', 'A'));
	}
	
	private void obtenerGets(Terceto t) {
		this.obtenerGets(t, true);
	}
	
	private void obtenerGets(Terceto t, boolean quieroIzquierdo) {
		int pos = this.posicionActual;
		String op1 = t.getOp1();
		String op2 = t.getOp2();
		
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
		
	    Matcher matcher1 = pattern.matcher(op1);
	    boolean find1 = matcher1.find();
	    Matcher matcher2 = pattern.matcher(op2);
	    
	    if(quieroIzquierdo && !find1) {
	    	if(!op1.equals("")) {
	    		if(op1.matches("^[0-9].*")) {
	    		 this.escribir(cuerpoActual,t.getTipo().equals("double") ? "f64.const "+op1 : "i32.const "+op1);	
	    		} else {
	    			this.escribir(cuerpoActual,"global.get $"+op1.replace(':', 'A')); 		
	    		}
	    	}
	    }else if(find1){
	    	int indiceTerceto = Integer.parseInt(matcher1.group(1));
	    	Terceto t_op1 = this.gc_main.getTerceto(indiceTerceto);
	    	if(this.posicionActual < indiceTerceto) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    		
	    	}else if(this.posicionActual > indiceTerceto && !t_op1.isHecho()) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    	}
	    }

	    if(!matcher2.find()) {
	    	if(!op2.equals("")) {
	    		if(op2.matches("^[0-9].*")) {
	    			this.escribir(cuerpoActual,this.ts.getAtributo(op2, AccionSemantica.TIPO).equals("double") ? "f64.const "+t.getOp2() : "i32.const "+t.getOp2()); 
	    		} else {
	    			this.escribir(cuerpoActual,"global.get $"+op2.replace(':', 'A'));	
	    		}
	    	}
	    } else {
	    	int indiceTerceto = Integer.parseInt(matcher2.group(1));
	    	Terceto t_op2 = this.gc_main.getTerceto(indiceTerceto);
	    	if(pos < indiceTerceto) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op2);
	    		
	    	}else if(pos > indiceTerceto && !t_op2.isHecho()) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op2);
	    	}
	    }
	    this.posicionActual = pos;
	}

	private void obtenerComparaciones(Terceto t) {
	    int comp1 = Integer.parseInt(t.getOp1().split("\\[|\\]")[1]);
	    int comp2 = Integer.parseInt(t.getOp2().split("\\[|\\]")[1]);
	    
	    if(gc_main.getTerceto(this.posicionActual-1).getOperador().equals("AND")) {
	    	this.escribir(cuerpoActual,"local.get $comp"+comp2);
	    }else{
	    	this.escribir(cuerpoActual,"local.get $comp"+comp1);
	    	this.escribir(cuerpoActual,"local.get $comp"+comp2);
	    }
	}
	
	private void comparacionTripla(String comp, String and, String op1, String op2) {
		op1 = op1.replace(':', 'A');
		op2 = op2.replace(':', 'A');
		this.escribir(cuerpoActual, "global.get $"+op1+"V1");
		this.escribir(cuerpoActual, "global.get $"+op2+"V1");
		this.escribir(cuerpoActual, comp);
		this.escribir(variablesActual, "(local $comp"+this.posicionActual+"V1" + " i32)");
		this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual+"V1");
		this.escribir(cuerpoActual, "global.get $"+op1+"V2");
		this.escribir(cuerpoActual, "global.get $"+op2+"V2");
		this.escribir(cuerpoActual, comp);
		this.escribir(variablesActual, "(local $comp"+this.posicionActual+"V2" + " i32)");
		this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual+"V2");
		this.escribir(cuerpoActual, "global.get $"+op1+"V3");
		this.escribir(cuerpoActual, "global.get $"+op2+"V3");
		this.escribir(cuerpoActual, comp);
		this.escribir(variablesActual, "(local $comp"+this.posicionActual+"V3" + " i32)");
		this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual+"V3");
		this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual+"V3");
		this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual+"V2");
		this.escribir(cuerpoActual, and);
		this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual+"V1");
		this.escribir(cuerpoActual, and);
	}
	
	private void aritmeticaTripla(Terceto t, String sufijoOperacion) {
		String op = t.getOperador();
		String op1 = t.getOp1();
		String op2 = t.getOp2().replace(':', 'A');
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
		
	    Matcher matcher1 = pattern.matcher(op1);
	    Matcher matcher2 = pattern.matcher(op2);
	    boolean find1 = matcher1.find();
	    boolean find2 = matcher2.find();
	    String tipo = this.ts.getAtributo(op1, AccionSemantica.TIPO_BASICO).equals("ulongint")?"i32":"f64";
	    String operacion = tipo + sufijoOperacion;
	    String aux = "AUX1";
	    op1 = op1.replace(':', 'A');
	    if(find1 && !find2) {
	    	this.escribir(cuerpoActual, "global.get $AUX1"+"V1"+tipo);
			this.escribir(cuerpoActual, "global.get $"+op2+"V1");
			this.escribir(cuerpoActual, operacion);
			
			this.escribir(cuerpoActual, "global.set $AUX1"+"V1"+tipo);
			
			this.escribir(cuerpoActual, "global.get $AUX1"+"V2"+tipo);
			this.escribir(cuerpoActual, "global.get $"+op2+"V2");
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $AUX1"+"V2"+tipo);
			
			this.escribir(cuerpoActual, "global.get $AUX1"+"V3"+tipo);
			this.escribir(cuerpoActual, "global.get $"+op2+"V3");
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $AUX1"+"V3"+tipo);
	    } else if(find1 && find2) {
	    	this.escribir(cuerpoActual, "global.get $AUX1"+"V1"+tipo);
			this.escribir(cuerpoActual, "global.get $AUX2"+"V1"+tipo);
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $AUX1"+"V1"+tipo);
			
			this.escribir(cuerpoActual, "global.get $AUX1"+"V2"+tipo);
			this.escribir(cuerpoActual, "global.get $AUX2"+"V2"+tipo);
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $AUX1"+"V2"+tipo);
			
			this.escribir(cuerpoActual, "global.get $AUX1"+"V3"+tipo);
			this.escribir(cuerpoActual, "global.get $AUX2"+"V3"+tipo);
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $AUX1"+"V3"+tipo);
	    } else if(!find1 && find2){
	    	this.escribir(cuerpoActual, "global.get $AUX1"+"V1"+tipo);
			this.escribir(cuerpoActual, "global.get $"+op1+"V1");
			this.escribir(cuerpoActual, operacion);
			
			this.escribir(cuerpoActual, "global.set $AUX1"+"V1"+tipo);
			
			this.escribir(cuerpoActual, "global.get $AUX1"+"V2"+tipo);
			this.escribir(cuerpoActual, "global.get $"+op1+"V2");
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $AUX1"+"V2"+tipo);
			
			this.escribir(cuerpoActual, "global.get $AUX1"+"V3"+tipo);
			this.escribir(cuerpoActual, "global.get $"+op1+"V3");
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $AUX1"+"V3"+tipo);
	    } else {
	    	
	    	if(this.aux1EnUso) {
	    		aux = "AUX2";
	    	}else {
	    		aux = "AUX1";
	    		this.aux1EnUso = true;
	    	}
	    	this.escribir(cuerpoActual, "global.get $"+op1+"V1");
			this.escribir(cuerpoActual, "global.get $"+op2+"V1");
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $"+aux+"V1"+tipo);
			
			this.escribir(cuerpoActual, "global.get $"+op1+"V2");
			this.escribir(cuerpoActual, "global.get $"+op2+"V2");
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $"+aux+"V2"+tipo);
			
			this.escribir(cuerpoActual, "global.get $"+op1+"V3");
			this.escribir(cuerpoActual, "global.get $"+op2+"V3");
			this.escribir(cuerpoActual, operacion);
			this.escribir(cuerpoActual, "global.set $"+aux+"V3"+tipo);
	    }
	    if(tipo.equals("ulongint") && sufijoOperacion.equals("sub")) {
	    	this.checkResNegativo("global.get $"+aux+"V1"+tipo);
	    	this.checkResNegativo("global.get $"+aux+"V2"+tipo);
	    	this.checkResNegativo("global.get $"+aux+"V3"+tipo);
	    }
	}
	
 	private void menor(Terceto t) { // una vez que planteemos las triplas se puede modularizar esto seguro.
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.lt_s");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.lt_u");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO);
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.lt_u":"f64.lt_s", tipo_basico.equals("ulongint")? "i32.eq":"f64.eq", t.getOp1(), t.getOp2());
		}
	}

	private void menorIgual(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.le_s");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.le_u");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO);
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.le_u":"f64.le_s", tipo_basico.equals("ulongint")? "i32.eq":"f64.eq", t.getOp1(), t.getOp2());
		}
	}

	private void mayor(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.gt_s");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.gt_u" );
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO);
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.gt_u":"f64.gt_s", tipo_basico.equals("ulongint")? "i32.eq":"f64.eq", t.getOp1(), t.getOp2());
		}
	}

	private void mayorIgual(Terceto t) {
		String tipo = t.getTipo();
		System.out.println("op1 " + t.getOp1() + " op2 " + t.getOp2() + " tipo " + tipo);
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.ge_s");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.ge_u");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO);
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.ge_u":"f64.ge_s", tipo_basico.equals("ulongint")? "i32.eq":"f64.eq",t.getOp1(), t.getOp2());
		}
	}

	private void igual(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.eq");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.eq");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO);
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.eq":"f64.eq", tipo_basico.equals("ulongint")? "i32.eq":"f64.eq", t.getOp1(), t.getOp2());
		}

	}
	
	private void distinto(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.ne_s");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.ne_u");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO);
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.ne_u":"f64.ne_s", tipo_basico.equals("ulongint")? "i32.eq":"f64.eq", t.getOp1(), t.getOp2());
		}
	}
	
	private void setAuxTripla(String tipo) {
		if(seAccedioAtripla) {
			this.escribir(cuerpoActual, "global.set $"+tipo+"auxTripla");
			this.escribir(cuerpoActual, "global.get $"+tipo+"auxTripla");
		}
	}
	
	private void suma(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.add");
			this.setAuxTripla("f64");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.add");
			this.setAuxTripla("i32");
		} else {
			this.aritmeticaTripla(t, ".add");
		}
	}
	
	private void checkResNegativo(String get) {
		this.escribir(cuerpoActual, get);
		this.escribir(cuerpoActual, "i32.const 0");
		this.escribir(cuerpoActual, "i32.lt_s");
		this.escribir(cuerpoActual,"(if");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual,"(then");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "i32.const 101");
		this.escribir(cuerpoActual, "i32.const 73");
		this.escribir(cuerpoActual, "call $log");
		//exit
		this.reducirIdentacion();
		this.escribir(cuerpoActual,")");
		this.reducirIdentacion();
		this.escribir(cuerpoActual,")");
	}

	private void resta(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.sub");
			this.setAuxTripla("f64");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.sub");
			this.setAuxTripla("i32");
			this.escribir(cuerpoActual, "global.set $AUXNEG");
			this.checkResNegativo("global.get $AUXNEG");
			this.escribir(cuerpoActual, "global.get $AUXNEG");
		} else {
			this.aritmeticaTripla(t, ".sub");
		}
	}

	private void producto(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.mul");
			this.setAuxTripla("f64");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.mul");
			this.setAuxTripla("i32");
		} else {
			this.aritmeticaTripla(t, ".mul");
		}
	}

	private void division(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.div_s");
			this.setAuxTripla("f64");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.div_u");
			this.setAuxTripla("i32");
		} else {
			this.aritmeticaTripla(t, this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO).equals("ulongint")?"div_u":"div_s");
		}
	}

	private void generarTag(Terceto t) {
		String tag = t.getOp1();
		if(!gotos.contains(tag)) {
			this.bloquesGOTO.push(this.ident + "(block $"+tag.replace(':', 'A'));
			this.escribir(cuerpoActual, ") ;; fin de tag: " + tag);
			this.gotos.add(tag);
		}
		
	}

	private void saltoIncondicional(Terceto t) {
		this.escribir(cuerpoActual,"br $"+t.getOp1().replace(':', 'A'));
		
	}

	private void accesoTriple(Terceto t) {
		// TODO Auto-generated method stub
		String op1 = t.getOp1();
		if(!this.accesoTriplas.contains(op1)) {
			this.escribir(variablesActual, "(local $acceso"+op1.replace(':', 'A')+ " i32)");
			this.accesoTriplas.add(op1);
		}
		
		
		String op2 = t.getOp2();
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(op2);
	    
	    boolean find = matcher.find();
		String tipoOp1 = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO);
		op1 =t.getOp1().replace(':', 'A');

		if(find) {
			this.escribir(cuerpoActual, "local.set $acceso"+op1);			
		}else {
			if(op2.matches("^[0-9].*")) {
				this.escribir(cuerpoActual, "i32.const "+op2);
			}else {
				this.escribir(cuerpoActual, "global.get $"+op1);
			}
			this.escribir(cuerpoActual, "local.set $acceso"+op1);	
		}
		
		this.escribir(cuerpoActual, "local.get $acceso"+op1);
		this.escribir(cuerpoActual, "i32.const 1");
		this.escribir(cuerpoActual, "i32.eq");
		this.escribir(cuerpoActual, "(if");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "(then");
		this.aumentarIdentacion();
		tipoOp1 = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO).equals("ulongint")?"i32":"f64";

		
		this.escribir(cuerpoActual, "global.get $"+op1+"V1");
		this.escribir(cuerpoActual, "global.set $"+tipoOp1+"auxTripla");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.escribir(cuerpoActual, "(else");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "local.get $acceso"+op1);
		this.escribir(cuerpoActual, "i32.const 2");
		this.escribir(cuerpoActual, "i32.eq");
		this.escribir(cuerpoActual, "(if");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "(then");
		this.aumentarIdentacion();


		this.escribir(cuerpoActual, "global.get $"+op1+"V2");
		this.escribir(cuerpoActual, "global.set $"+tipoOp1+"auxTripla");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.escribir(cuerpoActual, "(else");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "local.get $acceso"+op1);
		this.escribir(cuerpoActual, "i32.const 3");
		this.escribir(cuerpoActual, "i32.eq");
		this.escribir(cuerpoActual, "(if");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "(then");
		this.aumentarIdentacion();

		
		this.escribir(cuerpoActual, "global.get $"+op1+"V3");
		this.escribir(cuerpoActual, "global.set $"+tipoOp1+"auxTripla");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.escribir(cuerpoActual, "(else");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "i32.const 237");
		this.escribir(cuerpoActual, "i32.const 42");
		this.escribir(cuerpoActual, "call $log");
		// TODO call a exit 
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, "global.get $"+tipoOp1+"auxTripla");

	}

	private void escribir(StringBuilder builder, String code) {
        	builder.append(this.ident + code + "\n");
       
    }
	
	private void escribeArchivo() {
        try (FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
        	bw.write(variablesGlobales.toString() + "\n" );
        	bw.write(funciones.toString()+ "\n");
        	this.agregarBloquesGoto(inicioMain);
        	bw.write(inicioMain.toString() + "\n");
        	bw.write(cuerpoMain.toString() + "\n");
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
	
	private void toDouble(Terceto t) {
		this.obtenerGets(t);
		this.escribir(cuerpoActual,"f64_convert_i32_s");
	}
	
	private void toUlongint(Terceto t) {
		this.obtenerGets(t);
		this.escribir(cuerpoActual,"i32.trunc_f64_u");
	}

	private void asignacion(Terceto t) {	    
		String tipo = t.getTipo();
		String op2 = t.getOp2();
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(op2);
	    
	    boolean find = matcher.find();
		if(!tipo.equals("ulongint") && !tipo.equals("double")) {
			String op1 = t.getOp1();
			tipo = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO).equals("ulongint")?"i32":"f64";
			if(find) {
				this.escribir(cuerpoActual, "global.get $AUX1V1"+tipo);
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V1");
				this.escribir(cuerpoActual, "global.get $AUX1V2"+tipo);
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V2");
				this.escribir(cuerpoActual, "global.get $AUX1V3"+tipo);
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V3");
			}else {
				this.escribir(cuerpoActual, "global.get $" + op2.replace(':', 'A') + "V1");
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V1");
				this.escribir(cuerpoActual, "global.get $" + op2.replace(':', 'A') + "V2");
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V2");
				this.escribir(cuerpoActual, "global.get $" + op2.replace(':', 'A') + "V3");
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V3");
			}
		}else {
			String tipoOp1 = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO);
			if(!tipoOp1.equals("ulongint") && !tipoOp1.equals("double")) {
				String op1 =t.getOp1().replace(':', 'A');
				this.escribir(cuerpoActual, "local.get $accesoAsig"+op1);
				this.escribir(cuerpoActual, "i32.const 1");
				this.escribir(cuerpoActual, "i32.eq");
				this.escribir(cuerpoActual, "(if");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "(then");
				this.aumentarIdentacion();
				String tipoOp2 = this.ts.getAtributo(op2, AccionSemantica.TIPO).equals("ulongint")?"i32":"f64";
				tipoOp1 = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO).equals("ulongint")?"i32":"f64";
				if(!find) {
					if(this.ts.getAtributo(op2, AccionSemantica.USO).equals("nombre variable")) {
						this.escribir(cuerpoActual, "global.get $"+op2.replace(':', 'A'));
					}else {
						this.escribir(cuerpoActual, tipoOp2+".const " + op2);
					}
				} else {
					this.escribir(cuerpoActual, "global.get $"+tipoOp1+"auxTripla");
				}
				this.escribir(cuerpoActual, "global.set $"+op1+"V1");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.escribir(cuerpoActual, "(else");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "local.get $accesoAsig"+op1);
				this.escribir(cuerpoActual, "i32.const 2");
				this.escribir(cuerpoActual, "i32.eq");
				this.escribir(cuerpoActual, "(if");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "(then");
				this.aumentarIdentacion();
				if(!find) {
					if(this.ts.getAtributo(op2, AccionSemantica.USO).equals("nombre variable")) {
						this.escribir(cuerpoActual, "global.get $"+op2.replace(':', 'A'));
					}else {
						this.escribir(cuerpoActual, tipoOp2+".const " + op2);
					}
				} else {
					this.escribir(cuerpoActual, "global.get $"+tipoOp1+"auxTripla");
				}
				this.escribir(cuerpoActual, "global.set $"+op1+"V2");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.escribir(cuerpoActual, "(else");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "local.get $accesoAsig"+op1);
				this.escribir(cuerpoActual, "i32.const 3");
				this.escribir(cuerpoActual, "i32.eq");
				this.escribir(cuerpoActual, "(if");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "(then");
				this.aumentarIdentacion();
				if(!find) {
					if(this.ts.getAtributo(op2, AccionSemantica.USO).equals("nombre variable")) {
						this.escribir(cuerpoActual, "global.get $"+op2.replace(':', 'A'));
					}else {
						this.escribir(cuerpoActual, tipoOp2+".const " + op2);
					}
				} else {
					this.escribir(cuerpoActual, "global.get $"+tipoOp1+"auxTripla");
				}
				this.escribir(cuerpoActual, "global.set $"+op1+"V3");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.escribir(cuerpoActual, "(else");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "i32.const 237");
				this.escribir(cuerpoActual, "i32.const 42");
				this.escribir(cuerpoActual, "call $log");
				// TODO call a exit 
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				if(find) {
					this.escribir(cuerpoActual, "global.set $"+tipoOp1+"auxTripla");
				}
				this.reducirIdentacion();
				this.seAccedioAtripla = false;
			}else {
				this.obtenerGets(t, false);
				this.escribir(cuerpoActual,"global.set $" + t.getOp1().replace(':', 'A'));
			}
		}
	}
	
	private void and(Terceto t) {
		this.obtenerComparaciones(t);
		this.escribir(cuerpoActual,"i32.and");
	}
	
	private void invocacionFuncion(Terceto t) {
		this.obtenerGets(t, false);
		this.escribir(cuerpoActual,"call $" + t.getOp1().replace(':', 'A'));
	}
	
	private void retornoFuncion(Terceto t) {
		this.obtenerGets(t);
		this.escribir(cuerpoActual,"local.set $"+this.funcionActual.replace(':', 'A') +"retorno");
	}
	
	private void generarLabel(Terceto t) {
		String op1 = t.getOp1().replace(':', 'A');
		switch(op1) {
		case "else": /*this.escribir("($elseA"+this.ifs.peek()+")");*/ break;
		case "endif": this.reducirIdentacion();this.escribir(cuerpoActual,")");this.reducirIdentacion();this.escribir(cuerpoActual,")");break;
		case "FIN_IF_SOLO" : this.reducirIdentacion();this.escribir(cuerpoActual,")");this.reducirIdentacion();this.escribir(cuerpoActual,")"); this.ifs.add("IF"+this.ifs.size()); break;
		case "endfor": this.tipoFlujoActual="IF"; this.fors.pop(); break;
		default: 
			this.tipoFlujoActual = "FOR";
			this.escribir(cuerpoActual,"block $endforA"+ t.getOp1());
			this.escribir(cuerpoActual,"loop $"+op1);
			this.aumentarIdentacion();
			this.fors.push(op1);
			break;
		}
	}
	
	private void bifurcacionIncondicional(Terceto t) {
		if(this.tipoFlujoActual.equals("IF")) {
			this.reducirIdentacion();
			this.escribir(cuerpoActual,")");
			this.escribir(cuerpoActual,"(else");
			this.aumentarIdentacion();
		}else if(this.tipoFlujoActual.equals("FOR")) {
			this.escribir(cuerpoActual,"br $"+ this.fors.peek());
			this.reducirIdentacion();
			this.escribir(cuerpoActual,"end");
			this.escribir(cuerpoActual,"end");
		}
	}
	
	private void bifurcacionPorFalso(Terceto t) {
		boolean esIf = tipoFlujoActual.equals("IF");
		if(esIf) {
			this.escribir(cuerpoActual,"(if");
			this.aumentarIdentacion();
			this.escribir(cuerpoActual,"(then");
			this.aumentarIdentacion();
		}else {
			this.escribir(cuerpoActual,"br_if $"+ "endforA"+this.fors.peek());
		}
		
	}
	
	private void outf(Terceto t) {
		String cadmul = t.getOp1();
		if (cadmul.startsWith("CADMUL:")) {
	        this.escribir(cuerpoActual,"i32.const "+ this.ts.getPosicionMemoria(cadmul));
	        this.escribir(cuerpoActual,"i32.const "+ cadmul.substring("CADMUL:".length()).length());
	        this.escribir(cuerpoActual,"call $log");
	    } /*else {
	    	this.obtenerGets(t);
	    	this.escribir(cuerpoActual,"i32.const 0");
	    }*/
	}
}
