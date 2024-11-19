package paqueton;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class GeneradorWasm {
	private TablaSimbolos ts;
	private GeneradorCodigo gc_main;
	private String filePath;
	private String funcionActual;
	private int posicionActual;
	private String ident;
	// Pilas que manejan anidaciones de flujos de control
	private Stack<String> tipoFlujoActual = new Stack<String>();
	private Stack<String> fors;
	private Stack<String> bloquesGOTO;
	private boolean hayPatternMatching = false;
	//AUXILIARES PARA OPERACIONES DE TRIPLAS
	private String aux1= new String(""); //guardo datos de operaciones de triplas
	private String aux2=new String("");
	private String aux3=new String("");
	private String aux1Tripla= new String(""); //guardo el dato de acceder a una tripla
	private String aux2Tripla=new String("");
	private String aux3Tripla=new String("");
	// Estructuras 
	private Set<String> accesoTriplas = new HashSet<String>();
	private Set<String> gotos = new HashSet<String>();
	private Map<String, Integer> funcionesId= new HashMap<String,Integer>();
	private ManejadorErroresEjecucion erroresEj = new ManejadorErroresEjecucion();
	// Variables que arman partes del archivo .wta
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
        this.bloquesGOTO = new Stack<String>();
        this.fors = new Stack<String>();
        this.posicionActual = 0;
        this.ident = "";
        this.filePath = Paths.get(System.getProperty("user.dir"))
                .resolve("wat2wasm")
                .resolve("bin")
                .resolve(path + ".wat")
                .toString();
        
        // Poner en blanco el archivo al inicializar
        try (FileWriter fw = new FileWriter(filePath, false)) {
            // El archivo se sobrescribe en blanco al abrirse en modo no-apend (append = false)
        } catch (IOException e) {
            System.err.println("Error al vaciar el archivo: " + e.getMessage());
        }
    }
	
	// Logica de identado
	private void reducirIdentacion() {
		int ultimoTabulador = this.ident.lastIndexOf("\t");
		if (ultimoTabulador != -1) {
		    this.ident = this.ident.substring(0, ultimoTabulador) + this.ident.substring(ultimoTabulador + 1);
		}
	}
	
	private void aumentarIdentacion() {
		this.ident += "\t";
	}
	
	// Carga de variables a webassembly
	public void cargarErrores() {
		erroresEj.agregarError("negativo", "\"Error en ejecucion: El resultado de una operacion sin signo dio negativo.\")");
		erroresEj.agregarError("overflow", "\"Error en ejecucion: El resultado de una suma de enteros genero overflow.\")");
		erroresEj.agregarError("recursion", "\"Error en ejecucion: se realizo una recursion sobre una funcion.\")");
		erroresEj.agregarError("rango", "\"Error en ejecucion: indice fuera de rango.\")");
		erroresEj.agregarError("conversionErronea", "\"Error en ejecucion: se intenta realizar una conversion de flotante negativo a entero sin signo.\")");
		for (String error : erroresEj.getErrores().keySet()) {
			this.escribir(this.variablesGlobales, "(data (i32.const " + erroresEj.getDir(error) + ")" +  erroresEj.getMsj(error));
		}
	}
	
	public void cargarCadmuls() {
		Map<String, Integer> cadmuls = this.ts.getCadenas();
		Integer dirMem = erroresEj.getDirMax();
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
		this.escribir(this.variablesGlobales, "(global $funcionLlamadora (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUXOVERFLOW (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $f64auxTripla (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $i32auxTripla (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $f64aux2Tripla (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $i32aux2Tripla (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $f64aux3Tripla (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $i32aux3Tripla (mut i32) (i32.const 0))");
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
		this.escribir(this.variablesGlobales, "(global $AUX3V1f64 (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX3V2f64 (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX3V3f64 (mut f64) (f64.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX3V1i32 (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX3V2i32 (mut i32) (i32.const 0))");
		this.escribir(this.variablesGlobales, "(global $AUX3V3i32 (mut i32) (i32.const 0))");
	}
	
	// Bucle principal de la traduccion
	
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
			case "and": this.hayPatternMatching = false; this.and(t);  break;
			case "nand": this.hayPatternMatching = false; this.nand(t);  break;
			case "bi": this.bifurcacionIncondicional(t); break;
			case "bf": this.bifurcacionPorFalso(t); break;
			case ":=": this.asignacion(t); break;
			case "todouble": this.toDouble(t); break;
			case "inicioif": this.tipoFlujoActual.push("IF"); break;
			case "patron": this.hayPatternMatching = true; break;
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
			    break;
		}
		t.setHecho(true);
	}
		
	public void traducir() {
		this.escribir(this.variablesGlobales,"(module");
		this.escribir(this.variablesGlobales,"(import \"console\" \"log\" (func $log (param i32 i32)))");
		this.escribir(this.variablesGlobales,"(import \"js\" \"mem\" (memory 1))");
		this.escribir(this.variablesGlobales, "(import \"env\" \"console_log\" (func $console_log_i32 (param i32)))");
		this.escribir(this.variablesGlobales, "(import \"env\" \"console_log\" (func $console_log_f64 (param f64)))");
		this.escribir(this.variablesGlobales, "(import \"env\" \"exit\" (func $exit))");
		this.cargarVariables();
		this.cargarErrores();
		this.cargarCadmuls();
		this.escribir(this.variablesGlobales, "");
		this.reducirIdentacion();
		this.funcionesId.put("global", 0);
		int contador = 1;
		GeneradorCodigo aux = gc_main;
		Map<String, GeneradorCodigo> funciones = this.ts.getFunciones();
		for (String funcion : funciones.keySet()) {
			this.funcionesId.put(funcion, contador);
			cuerpoActual = new StringBuilder();
			bloquesGOTO = new Stack<String>();
			this.funcionActual = funcion;
			gc_main = funciones.get(funcion);
			String parametro = this.ts.getAtributo(funcion, AccionSemantica.PARAMETRO);
			String tipoParam = this.ts.getAtributo(parametro, AccionSemantica.TIPO).equals("double")? "f64":"i32";
			String tipoFuncion = this.ts.getAtributo(funcion, AccionSemantica.TIPO).equals("double")? "f64":"i32";
			parametro = parametro.replace(':', 'A');
			this.escribir(this.funciones, "( func $"+funcion.replace(':', 'A')  + " (param $"+ parametro  + " " + tipoParam+") (result "+ tipoFuncion + ")");
			this.escribir(variablesGlobales, "(global $"+ parametro +" (mut "+tipoParam+") ("+tipoParam+".const 0))");
			this.escribir(cuerpoActual, "local.get $" + parametro);
			this.escribir(cuerpoActual, "global.set $" + parametro);
			variablesActual = new StringBuilder();
			this.escribir(variablesActual,"(local $" + funcion.replace(':', 'A') +"retorno " + tipoFuncion + ")");
			this.aumentarIdentacion();
			
			this.escribir(cuerpoActual, "i32.const " + contador);
			this.escribir(cuerpoActual, "global.get $funcionLlamadora");
			this.escribir(cuerpoActual, "i32.eq");
			this.escribir(cuerpoActual, "(if");
			this.aumentarIdentacion();
			this.escribir(cuerpoActual,"(then");
			this.aumentarIdentacion();
			this.escribir(cuerpoActual, "i32.const "+erroresEj.getDir("recursion"));
			this.escribir(cuerpoActual, "i32.const "+erroresEj.getSizeMsj("recursion"));
			this.escribir(cuerpoActual,"call $log");
			this.escribir(cuerpoActual, "call $exit");
			this.reducirIdentacion();
			this.escribir(cuerpoActual,")");
			this.reducirIdentacion();
			this.escribir(cuerpoActual,")");
			
			this.escribir(cuerpoActual,tipoFuncion+".const 0");
			this.escribir(cuerpoActual,"local.set $"+funcion.replace(':', 'A') +"retorno");
			for (int i = 0; i < gc_main.getCantTercetos(); i++) {
				this.posicionActual = i;
				Terceto t = gc_main.getTerceto(i);
				if(!t.isHecho()) {
					this.ejecutarTraduccion(t);				
				}
			}
			contador++;
			this.escribir(cuerpoActual,"local.get $"+funcion.replace(':', 'A') +"retorno");
			this.reducirIdentacion();
			this.escribir(cuerpoActual,")\n");
			this.agregarBloquesGoto(this.variablesActual);
			this.funciones.append(variablesActual);
			this.funciones.append(cuerpoActual.toString());
			
		}
		this.funcionActual = "global";
		gc_main = aux;
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
	

	// Obtencion de operandos para la pila de webAssembly
	private void obtenerGets(Terceto t) {
		this.obtenerGets(t, true);
	}
	
	private void obtenerGets(Terceto t, boolean quieroIzquierdo) {
		int pos = this.posicionActual;
		String op1 = t.getOp1();
		String op2 = t.getOp2();
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher1 = pattern.matcher(op1);
	    Matcher matcher2 = pattern.matcher(op2);
	    boolean find1 = matcher1.find();
	    boolean find2 = matcher2.find();
	    
	    if(quieroIzquierdo && !find1) {
	    	if(!op1.equals("")) {
	    		if(op1.matches("^[0-9].*") || op1.matches("^-.*")) {
	    			
	    		 this.escribir(cuerpoActual,this.ts.getAtributo(op1, AccionSemantica.TIPO).equals("double") ? "f64.const "+op1 : "i32.const "+op1);	
	    		} else {
	    			this.escribir(cuerpoActual,"global.get $"+op1.replace(':', 'A')); 		
	    		}
	    	}
	    }else if(find1){ //o una expreion comun o un acceso a tripla
	    	int indiceTerceto = Integer.parseInt(matcher1.group(1)); //el terceto posta
	    	Terceto t_op1 = this.gc_main.getTerceto(indiceTerceto);
	    	if(this.posicionActual < indiceTerceto) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    	}else if(this.posicionActual > indiceTerceto && !t_op1.isHecho()) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    	}
	    	if(t_op1.getOperador().equals("ACCESOTRIPLE")) {
	    		String AuxTripla;
	    		String tipoOp1 = t_op1.getTipo().equals("ulongint")?"i32":"f64";
	    		if(aux1Tripla.equals(t.getOp1()))
					AuxTripla="auxTripla";
				else if(aux2Tripla.equals(t.getOp1()))
					AuxTripla="aux2Tripla";
				else
					AuxTripla="aux3Tripla";
	    		this.escribir(cuerpoActual, "global.get $"+tipoOp1+AuxTripla);
	    		if(AuxTripla.equals("auxTripla"))
					this.aux1Tripla="";
				else if (AuxTripla.equals("aux2Tripla"))
					this.aux2Tripla="";
				else 
					this.aux3Tripla="";
	    	}
	    	
	    }

	    if(!find2) {
	    	if(!op2.equals("")) {
	    		if(op2.matches("^[0-9].*") || op2.matches("^-.*")) {
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
	    	if(t_op2.getOperador().equals("ACCESOTRIPLE")) {
	    		String AuxTripla;
	    		String tipoOp2 = t_op2.getTipo().equals("ulongint")?"i32":"f64";
	    		if(aux1Tripla.equals(t.getOp2()))
					AuxTripla="auxTripla";
				else if(aux2Tripla.equals(t.getOp2()))
					AuxTripla="aux2Tripla";
				else
					AuxTripla="aux3Tripla";
	    		this.escribir(cuerpoActual, "global.get $"+tipoOp2+AuxTripla);
	    		if(AuxTripla.equals("auxTripla"))
					this.aux1Tripla="";
				else if (AuxTripla.equals("aux2Tripla"))
					this.aux2Tripla="";
				else 
					this.aux3Tripla="";
	    	}
	    }
	    this.posicionActual = pos;
	}


	//Comparaciones----------------------------------------------------------------------------------------
 	private void menor(Terceto t) { 
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.lt");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.lt_u");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getTipo(),"tipotripla");
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.lt_u":"f64.lt", "i32.eq", t.getOp1(), t.getOp2());
		}
	}

	private void menorIgual(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.le");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.le_u");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getTipo(),"tipotripla");
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.le_u":"f64.le", "i32.eq", t.getOp1(), t.getOp2());
		}
	}

	private void mayor(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.gt");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.gt_u" );
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getTipo(),"tipotripla");
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.gt_u":"f64.gt", "i32.eq", t.getOp1(), t.getOp2());
		}
	}

	private void mayorIgual(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.ge");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.ge_u");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getTipo(),"tipotripla");
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.ge_u":"f64.ge", "i32.eq",t.getOp1(), t.getOp2());
		}
	}

	private void igual(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.eq");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.eq");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getTipo(),"tipotripla");
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.eq":"f64.eq", "i32.eq", t.getOp1(), t.getOp2());
		}

	}
	
	private void distinto(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.ne");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.ne");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			if(!this.hayPatternMatching)this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else {
			String tipo_basico = this.ts.getAtributo(t.getTipo(),"tipotripla");
			this.comparacionTripla(tipo_basico.equals("ulongint")? "i32.ne":"f64.ne", "i32.eq", t.getOp1(), t.getOp2());
		}
	}
	
	private void and(Terceto t) {
		this.obtenerComparaciones(t);
		this.escribir(cuerpoActual,"i32.and");
	}
	
	private void nand(Terceto t) {
		this.obtenerComparaciones(t);
		this.escribir(cuerpoActual,"i32.and");
		this.escribir(cuerpoActual,"i32.eqz");
	}
	
	private void comparacionTripla(String comp, String eq, String op1, String op2) {
		String op1v1 = "";
		String op1v2 = "";
		String op1v3 = "";
		String op2v1 = "";
		String op2v2 = "";
		String op2v3 = "";
		String aux= "";
		String aux2= "";
		int pos = this.posicionActual;
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher1 = pattern.matcher(op1);
	    Matcher matcher2 = pattern.matcher(op2);
	    boolean find1 = matcher1.find();
	    boolean find2 = matcher2.find();
		if(find1) { //expresion
	    	int indiceTerceto = Integer.parseInt(matcher1.group(1)); //operando1
	    	Terceto t_op1 = gc_main.getTerceto(indiceTerceto);

			if(this.posicionActual < indiceTerceto) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    	}else if(this.posicionActual > indiceTerceto && !t_op1.isHecho()) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    	}
			this.posicionActual=pos;
			
			if(this.aux1.equals(op1))
				aux="AUX1";
			else if(this.aux2.equals(op1))
				aux="AUX2";
			else
				aux="AUX3";
			String tipo = t_op1.getTipo();
			tipo = this.ts.getAtributo(tipo,"tipotripla");
			tipo = tipo.equals("ulongint")?"i32":"f64";
			op1v1 = aux+"V1"+tipo;
			op1v2 = aux+"V2"+tipo;
			op1v3 = aux+"V3"+tipo;
			
		}else { //id de tripla
			op1 = op1.replace(':', 'A');
			op1v1 = op1+"V1";
			op1v2 = op1+"V2";
			op1v3 = op1+"V3";
			
		}
		if(find2) {
			
			int indiceTerceto = Integer.parseInt(matcher2.group(1)); //operando1
	    	Terceto t_op2 = gc_main.getTerceto(indiceTerceto);

			if(this.posicionActual < indiceTerceto) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op2);
	    	}else if(this.posicionActual > indiceTerceto && !t_op2.isHecho()) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op2);
	    	}
			this.posicionActual=pos;
			
			if(this.aux1.equals(op2))
				aux2="AUX1";
			else if(this.aux2.equals(op2))
				aux2="AUX2";
			else
				aux2="AUX3";
			String tipo = t_op2.getTipo();
			if(!tipo.equals("double") && !tipo.equals("ulongint")) {
				tipo = this.ts.getAtributo(tipo,"tipotripla");
			}
			tipo = tipo.equals("ulongint")?"i32":"f64";
			op2v1 = aux2+"V1"+tipo;
			op2v2 = aux2+"V2"+tipo;
			op2v3 = aux2+"V3"+tipo;
			
		} else {
			op2 = op2.replace(':', 'A');
			op2v1 = op2+"V1";
			op2v2 = op2+"V2";
			op2v3 = op2+"V3";
			
		}
		
		
		this.escribir(cuerpoActual, "global.get $"+op1v1);
		this.escribir(cuerpoActual, "global.get $"+op2v1);
		this.escribir(cuerpoActual, comp);
		this.escribir(variablesActual, "(local $comp"+this.posicionActual+"V1" + " i32)");
		this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual+"V1");
		this.escribir(cuerpoActual, "global.get $"+op1v2);
		this.escribir(cuerpoActual, "global.get $"+op2v2);
		this.escribir(cuerpoActual, comp);
		this.escribir(variablesActual, "(local $comp"+this.posicionActual+"V2" + " i32)");
		this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual+"V2");
		this.escribir(cuerpoActual, "global.get $"+op1v3);
		this.escribir(cuerpoActual, "global.get $"+op2v3);
		this.escribir(cuerpoActual, comp);
		this.escribir(variablesActual, "(local $comp"+this.posicionActual+"V3" + " i32)");
		this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual+"V3");
		this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual+"V3");
		this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual+"V2");
		this.escribir(cuerpoActual, eq);
		this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual+"V1");
		this.escribir(cuerpoActual, eq);
		
		if (aux.equals("AUX1") || aux2.equals("AUX1"))
			this.aux1=new String("");
		else if(aux.equals("AUX2") || aux2.equals("AUX2"))
			this.aux2=new String("");
		else if (aux.equals("AUX3") || aux2.equals("AUX3"))
				this.aux3=new String("");
		
		if(this.hayPatternMatching) {
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
		}
		
	}
	
	private void obtenerComparaciones(Terceto t) {
		int comp1 = Integer.parseInt(t.getOp1().split("\\[|\\]")[1]);
		int comp2 = Integer.parseInt(t.getOp2().split("\\[|\\]")[1]);
		
		if(!gc_main.getTerceto(this.posicionActual-1).getOperador().equals("AND")) 
			this.escribir(cuerpoActual,"local.get $comp"+comp1);
			
		this.escribir(cuerpoActual,"local.get $comp"+comp2);
	}
	
	// Operaciones Aritmeticas------------------------------------------------------------
	
	private void checkOverflow(String get, String error) {
		this.escribir(cuerpoActual, get);
		this.escribir(cuerpoActual, "i32.const 0");
		this.escribir(cuerpoActual, "i32.lt_s");
		this.escribir(cuerpoActual,"(if");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual,"(then");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "i32.const "+erroresEj.getDir(error));
		this.escribir(cuerpoActual, "i32.const "+erroresEj.getSizeMsj(error));
		this.escribir(cuerpoActual, "call $log");
		this.escribir(cuerpoActual, "call $exit");
		this.reducirIdentacion();
		this.escribir(cuerpoActual,")");
		this.reducirIdentacion();
		this.escribir(cuerpoActual,")");
	}

	private void suma(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.add");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.add");
			this.escribir(cuerpoActual, "global.set $AUXOVERFLOW");
			this.checkOverflow("global.get $AUXOVERFLOW", "overflow");
			this.escribir(cuerpoActual, "global.get $AUXOVERFLOW");
		} else {
			this.aritmeticaTripla(t, ".add");
		}
	}
	
	private void resta(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.sub");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.sub");
			this.escribir(cuerpoActual, "global.set $AUXOVERFLOW");
			this.checkOverflow("global.get $AUXOVERFLOW", "negativo");
			this.escribir(cuerpoActual, "global.get $AUXOVERFLOW");
		} else {
			this.aritmeticaTripla(t, ".sub");
		}
	}

	private void producto(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.mul");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.mul");
		} else {
			this.aritmeticaTripla(t, ".mul");
		}
	}

	private void division(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.div");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.div_u");
		} else {
			this.aritmeticaTripla(t, this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO).equals("ulongint")?".div_u":".div");
		}
	}
	
	private void aritmeticaTripla(Terceto t, String sufijoOperacion) {
		String op1 = t.getOp1();
		String op2 = t.getOp2().replace(':', 'A');
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher1 = pattern.matcher(op1);
	    Matcher matcher2 = pattern.matcher(op2);
	    boolean find1 = matcher1.find();
	    boolean find2 = matcher2.find();
	    int pos = this.posicionActual;
	    String tipo;
	    if(!find1) {
	    	tipo = this.ts.getAtributo(op1, AccionSemantica.TIPO_BASICO).equals("ulongint")?"i32":"f64";
	    }else {
	    	String tipoTripla = t.getTipo();
	    	tipo = ts.getAtributo(tipoTripla,"tipotripla").equals("ulongint")?"i32":"f64";
	    }
	    String operacion = tipo + sufijoOperacion;
	    String aux = "";
	    String aux2 = "";
	    op1 = op1.replace(':', 'A');
	    
	    

    	if(find1 && !find2) {
	    	int indiceTerceto = Integer.parseInt(matcher1.group(1)); //operando1
	    	Terceto t_op1 = gc_main.getTerceto(indiceTerceto);
    		if(this.posicionActual < indiceTerceto) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    	}else if(this.posicionActual > indiceTerceto && !t_op1.isHecho()) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    	}
			this.posicionActual=pos;
    		
    		if(this.aux1.equals(t.getOp1()))
    			aux="AUX1";
    		else if(this.aux2.equals(t.getOp1()))
    			aux="AUX2";
    		else
    			aux="AUX3";
    		this.escribir(cuerpoActual, "global.get $"+aux+"V1"+tipo);
    		this.escribir(cuerpoActual, "global.get $"+op2+"V1");
    		this.escribir(cuerpoActual, operacion);
    		
    		this.escribir(cuerpoActual, "global.set $"+aux+"V1"+tipo);
    		
    		this.escribir(cuerpoActual, "global.get $"+aux+"V2"+tipo);
    		this.escribir(cuerpoActual, "global.get $"+op2+"V2");
    		this.escribir(cuerpoActual, operacion);
    		this.escribir(cuerpoActual, "global.set $"+aux+"V2"+tipo);
    		
    		this.escribir(cuerpoActual, "global.get $"+aux+"V3"+tipo);
    		this.escribir(cuerpoActual, "global.get $"+op2+"V3");
    		this.escribir(cuerpoActual, operacion);
    		this.escribir(cuerpoActual, "global.set $"+aux+"V3"+tipo);
    		if(aux.equals("AUX1")) {
    			this.aux1="["+this.posicionActual+"]";
    		}else if(aux.equals("AUX2")){
    			this.aux2="["+this.posicionActual+"]";
    		}else {
    			this.aux3="["+this.posicionActual+"]";
    		}
    	} else if(find1 && find2) {
    		int indiceTerceto = Integer.parseInt(matcher1.group(1)); //operando1
	    	Terceto t_op1 = gc_main.getTerceto(indiceTerceto);
    		if(this.posicionActual < indiceTerceto) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    	}else if(this.posicionActual > indiceTerceto && !t_op1.isHecho()) {
	    		this.posicionActual = indiceTerceto;
	    		this.ejecutarTraduccion(t_op1);
	    	}
			this.posicionActual=pos;
			
			int indiceTerceto2 = Integer.parseInt(matcher2.group(1)); //operando2
	    	Terceto t_op2 = gc_main.getTerceto(indiceTerceto2);
    		if(this.posicionActual < indiceTerceto2) {
	    		this.posicionActual = indiceTerceto2;
	    		this.ejecutarTraduccion(t_op2);
	    	}else if(this.posicionActual > indiceTerceto2 && !t_op2.isHecho()) {
	    		this.posicionActual = indiceTerceto2;
	    		this.ejecutarTraduccion(t_op2);
	    	}
			this.posicionActual=pos;
			
    		if(this.aux1.equals(t.getOp1()))
    			aux="AUX1";
    		if(this.aux2.equals(t.getOp1()))
    			aux="AUX2";
    		if(this.aux3.equals(t.getOp1()))
    			aux="AUX3";
    		if(this.aux1.equals(t.getOp2()))
    			aux2="AUX1";
    		if(this.aux2.equals(t.getOp2()))
    			aux2="AUX2";
    		if(this.aux3.equals(t.getOp2()))
    			aux2="AUX3";
    		this.escribir(cuerpoActual, "global.get $"+aux+"V1"+tipo);
    		this.escribir(cuerpoActual, "global.get $"+aux2+"V1"+tipo);
    		this.escribir(cuerpoActual, operacion);
    		this.escribir(cuerpoActual, "global.set $"+aux+"V1"+tipo);
    		
    		this.escribir(cuerpoActual, "global.get $"+aux+"V2"+tipo);
    		this.escribir(cuerpoActual, "global.get $"+aux2+"V2"+tipo);
    		this.escribir(cuerpoActual, operacion);
    		this.escribir(cuerpoActual, "global.set $"+aux+"V2"+tipo);
    		
    		this.escribir(cuerpoActual, "global.get $"+aux+"V3"+tipo);
    		this.escribir(cuerpoActual, "global.get $"+aux2+"V3"+tipo);
    		this.escribir(cuerpoActual, operacion);
    		this.escribir(cuerpoActual, "global.set $"+aux+"V3"+tipo);
    		if(aux.equals("AUX1"))
    			this.aux1="["+this.posicionActual+"]";
    		if(aux.equals("AUX2"))
    			this.aux2="["+this.posicionActual+"]";
    		if(aux.equals("AUX3"))
    			this.aux3="["+this.posicionActual+"]";
    		if(aux2.equals("AUX1"))
    			this.aux1=new String("");
    		if(aux2.equals("AUX2"))
    			this.aux2=new String("");
    		if(aux2.equals("AUX3"))
    			this.aux3=new String("");
    	} else if(!find1 && find2){
    		
    		int indiceTerceto2 = Integer.parseInt(matcher2.group(1)); //operando1
	    	Terceto t_op2 = gc_main.getTerceto(indiceTerceto2);
    		if(this.posicionActual < indiceTerceto2) {
	    		this.posicionActual = indiceTerceto2;
	    		this.ejecutarTraduccion(t_op2);
	    	}else if(this.posicionActual > indiceTerceto2 && !t_op2.isHecho()) {
	    		this.posicionActual = indiceTerceto2;
	    		this.ejecutarTraduccion(t_op2);
	    	}
			this.posicionActual=pos;
    		
    		if(this.aux1.equals(t.getOp2()))
    			aux="AUX1";
    		else if(this.aux2.equals(t.getOp2()))
    			aux="AUX2";
    		else
    			aux="AUX3";
    		this.escribir(cuerpoActual, "global.get $"+aux+"V1"+tipo);
    		this.escribir(cuerpoActual, "global.get $"+op1+"V1");
    		this.escribir(cuerpoActual, operacion);
    		
    		this.escribir(cuerpoActual, "global.set $"+aux+"V1"+tipo);
    		
    		this.escribir(cuerpoActual, "global.get $"+aux+"V2"+tipo);
    		this.escribir(cuerpoActual, "global.get $"+op1+"V2");
    		this.escribir(cuerpoActual, operacion);
    		this.escribir(cuerpoActual, "global.set $"+aux+"V2"+tipo);
    		
    		this.escribir(cuerpoActual, "global.get $"+aux+"V3"+tipo);
    		this.escribir(cuerpoActual, "global.get $"+op1+"V3");
    		this.escribir(cuerpoActual, operacion);
    		this.escribir(cuerpoActual, "global.set $"+aux+"V3"+tipo);
    		if(aux.equals("AUX1")) {
    			this.aux1="["+this.posicionActual+"]";
    		}else if(aux.equals("AUX2")){
    			this.aux2="["+this.posicionActual+"]";
    		}else {
    			this.aux3="["+this.posicionActual+"]";
    		}
    	} else {
    		
    		if(this.aux1.equals(new String("")) ) {
    			aux = "AUX1";
    		}else if(this.aux2.equals(new String("")) ) {
    			aux = "AUX2";
    		}else {
    			aux = "AUX3";
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
    		if(aux.equals("AUX1")) {
    			this.aux1="["+this.posicionActual+"]";
    		}else if(aux.equals("AUX2")){
    			this.aux2="["+this.posicionActual+"]";
    		}else {
    			this.aux3="["+this.posicionActual+"]";
    		}
    	}
    	if(tipo.equals("ulongint") ) {
    		if(sufijoOperacion.equals("add")) {
        		this.checkOverflow("global.get $"+aux+"V1"+tipo, "overflow");
        		this.checkOverflow("global.get $"+aux+"V2"+tipo, "overflow");
        		this.checkOverflow("global.get $"+aux+"V3"+tipo, "overflow");
    		}else if(sufijoOperacion.equals("sub")) {
    			this.checkOverflow("global.get $"+aux+"V1"+tipo, "negativo");
        		this.checkOverflow("global.get $"+aux+"V2"+tipo, "negativo");
        		this.checkOverflow("global.get $"+aux+"V3"+tipo, "negativo");
    		}

    	}
	    
	}
	
	//Asignaciones-----------------------------------------------------------------------

	private void asigTripla(Terceto t) {
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
		if(!this.accesoTriplas.contains(op1)) {
			this.escribir(variablesGlobales, "(global $accesoAsig"+op1.replace(':', 'A')+ " (mut i32) (i32.const 1))");
			this.escribir(variablesGlobales, "(global $acceso"+op1.replace(':', 'A')+  " (mut i32) (i32.const 1))");
			this.accesoTriplas.add(op1);
		}
		this.escribir(cuerpoActual, "global.set $accesoAsig"+op1.replace(':', 'A'));
	}

	private void asignacion(Terceto t) {	    
		String tipo = t.getTipo();
		String op2 = t.getOp2();
		String op1 = t.getOp1();
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher2 = pattern.matcher(op2);
	    Matcher matcher1 = pattern.matcher(op1);
	    String aux="";
	    boolean findOp2 = matcher2.find();
	    boolean findOp1 = matcher1.find();
		if(!tipo.equals("ulongint") && !tipo.equals("double")) {
			tipo = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO).equals("ulongint")?"i32":"f64";
			if(findOp2) {
				if(this.aux1.equals(op2))
					aux="AUX1";
				else
					aux="AUX2";
				this.escribir(cuerpoActual, "global.get $"+aux+"V1"+tipo);
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V1");
				this.escribir(cuerpoActual, "global.get $"+aux+"V2"+tipo);
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V2");
				this.escribir(cuerpoActual, "global.get $"+aux+"V3"+tipo);
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V3");
				if(aux.equals("AUX1")) {
					this.aux1= new String("");
				}else {
					this.aux2= new String("");
				}
				
			}else {
				this.escribir(cuerpoActual, "global.get $" + op2.replace(':', 'A') + "V1");
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V1");
				this.escribir(cuerpoActual, "global.get $" + op2.replace(':', 'A') + "V2");
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V2");
				this.escribir(cuerpoActual, "global.get $" + op2.replace(':', 'A') + "V3");
				this.escribir(cuerpoActual, "global.set $" + op1.replace(':', 'A') + "V3");
			}
		}else {
			String loQueAsigno = "";
			String AuxTripla="";
			if(findOp2) {
				String  posicionTercetoOP2 = t.getOp2().substring(1,t.getOp2().length()-1);
				Terceto tOp2 = gc_main.getTerceto(Integer.parseInt(posicionTercetoOP2));
				String operadorOp2 = tOp2.getOperador();
				String tipoOp2 = tOp2.getTipo().equals("ulongint")?"i32":"f64";
				if(operadorOp2.equals("ACCESOTRIPLE")) {
					if(aux1Tripla.equals(t.getOp2()))
						AuxTripla="auxTripla";
					else if(aux2Tripla.equals(t.getOp2()))
						AuxTripla="aux2Tripla";
					else
						AuxTripla="aux3Tripla";
					loQueAsigno = "global.get $"+tipoOp2+AuxTripla;
				}
				else if(findOp1) {
					if(aux1Tripla.equals("")) {
						AuxTripla="auxTripla";
						aux1Tripla=t.getOp2();
					}else if(aux2Tripla.equals("")) {
						AuxTripla="aux2Tripla";
						aux2Tripla=t.getOp2();
					}else {
						AuxTripla="aux3Tripla";
						aux3Tripla=t.getOp2();
					}
					loQueAsigno = "global.get $"+tipoOp2+AuxTripla;
					this.escribir(cuerpoActual,"global.set $"+tipoOp2+AuxTripla);
				}
			}else {
				String tipoOp2 = this.ts.getAtributo(op2, AccionSemantica.TIPO).equals("ulongint")?"i32":"f64";
				if(this.ts.getAtributo(op2, AccionSemantica.USO).equals("nombre variable")){
					loQueAsigno = "global.get $"+op2.replace(':', 'A');
				}else{
					loQueAsigno =  tipoOp2+".const "+op2;
				}
			}
			//lo asigno
			if(findOp1) { //asignacion a tripla
				String  posicionTerceto = t.getOp1().substring(1,t.getOp1().length()-1);
				Terceto asignacion = gc_main.getTerceto(Integer.parseInt(posicionTerceto));
				String IdAsignacion =asignacion.getOp1().replace(':', 'A');
				this.escribir(cuerpoActual, "global.get $accesoAsig"+IdAsignacion);
				this.escribir(cuerpoActual, "i32.const 1");
				this.escribir(cuerpoActual, "i32.eq");
				this.escribir(cuerpoActual, "(if");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "(then");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, loQueAsigno);
				this.escribir(cuerpoActual, "global.set $"+IdAsignacion+"V1");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.escribir(cuerpoActual, "(else");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "global.get $accesoAsig"+IdAsignacion);
				this.escribir(cuerpoActual, "i32.const 2");
				this.escribir(cuerpoActual, "i32.eq");
				this.escribir(cuerpoActual, "(if");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "(then");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, loQueAsigno);
				this.escribir(cuerpoActual, "global.set $"+IdAsignacion+"V2");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.escribir(cuerpoActual, "(else");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "global.get $accesoAsig"+IdAsignacion);
				this.escribir(cuerpoActual, "i32.const 3");
				this.escribir(cuerpoActual, "i32.eq");
				this.escribir(cuerpoActual, "(if");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "(then");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, loQueAsigno);
				this.escribir(cuerpoActual, "global.set $"+IdAsignacion+"V3");
				this.reducirIdentacion();
				this.escribir(cuerpoActual, ")");
				this.escribir(cuerpoActual, "(else");
				this.aumentarIdentacion();
				this.escribir(cuerpoActual, "i32.const "+erroresEj.getDir("rango"));
				this.escribir(cuerpoActual, "i32.const "+erroresEj.getSizeMsj("rango"));
				this.escribir(cuerpoActual, "call $log");
				this.escribir(cuerpoActual, "call $exit");
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
				if(AuxTripla.equals("auxTripla"))
					this.aux1Tripla="";
				else if (AuxTripla.equals("aux2Tripla"))
					this.aux2Tripla="";
				else 
					this.aux3Tripla="";
			}else { //id
				this.obtenerGets(t, false);
				this.escribir(cuerpoActual,"global.set $" + t.getOp1().replace(':', 'A'));	
			}
			
		}
	}
	
	// Tags y saltos
	public void agregarBloquesGoto(StringBuilder sb) {
	    while (!bloquesGOTO.isEmpty()) {
	        sb.append(bloquesGOTO.pop()).append("\n");
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

	private void generarLabel(Terceto t) {
		String op1 = t.getOp1().replace(':', 'A');
		switch(op1) {
		case "else":break;
		case "endif": this.tipoFlujoActual.pop();
					this.reducirIdentacion();
					this.escribir(cuerpoActual,")");
					this.reducirIdentacion();
					this.escribir(cuerpoActual,")");
					break;
		case "FIN_IF_SOLO" : this.reducirIdentacion();
					this.escribir(cuerpoActual,")");
					this.reducirIdentacion();
					this.escribir(cuerpoActual,")"); 
					break;
		case "endfor": this.tipoFlujoActual.pop(); this.fors.pop(); break;
		default: 
			this.tipoFlujoActual.push("FOR");
			this.escribir(cuerpoActual,"block $endforA"+ t.getOp1());
			this.escribir(cuerpoActual,"loop $"+op1);
			this.aumentarIdentacion();
			this.fors.push(op1);
			break;
		}
	}
	
	private void bifurcacionIncondicional(Terceto t) {
		if(this.tipoFlujoActual.peek().equals("IF")) {
			this.reducirIdentacion();
			this.escribir(cuerpoActual,")");
			this.escribir(cuerpoActual,"(else");
			this.aumentarIdentacion();
		}else if(this.tipoFlujoActual.peek().equals("FOR")) {
			this.escribir(cuerpoActual,"br $"+ this.fors.peek());
			this.reducirIdentacion();
			this.escribir(cuerpoActual,"end");
			this.escribir(cuerpoActual,"end");
		}
	}
	
	private void bifurcacionPorFalso(Terceto t) {
		boolean esIf = tipoFlujoActual.peek().equals("IF");
		if(esIf) {
			this.escribir(cuerpoActual,"(if");
			this.aumentarIdentacion();
			this.escribir(cuerpoActual,"(then");
			this.aumentarIdentacion();
		}else {
			this.escribir(cuerpoActual,"br_if $"+ "endforA"+this.fors.peek());
		}
		
	}
	
	// Acceso a elemento de tripla
	private void accesoTriple(Terceto t) {
		
		String op1 = t.getOp1();
		
		if(!this.accesoTriplas.contains(op1)) {
			this.escribir(variablesGlobales, "(global $acceso"+op1.replace(':', 'A')+  " (mut i32) (i32.const 1))");
			this.escribir(variablesGlobales, "(global $accesoAsig"+op1.replace(':', 'A')+ " (mut i32) (i32.const 1))");
			this.accesoTriplas.add(op1);
		}
		
		
		String op2 = t.getOp2();
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
	    Matcher matcher = pattern.matcher(op2);
	    
	    boolean find = matcher.find();
		String tipoOp1 = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO);
		op1 =t.getOp1().replace(':', 'A');

		if(find) { //asigno un terceto, puede ser un accesoTriple
			String  posicionOp2 = t.getOp2().substring(1,t.getOp2().length()-1);
			Terceto TercetoOP2 = gc_main.getTerceto(Integer.parseInt(posicionOp2));
			String tipoOp2 = TercetoOP2.getTipo().equals("ulongint")?"i32":"f64";
			String AuxTripla="";
			if(TercetoOP2.getOperador().equals("ACCESOTRIPLE")) {
				if(aux1Tripla.equals(t.getOp2()))
					AuxTripla="auxTripla";
				else if(aux2Tripla.equals(t.getOp2()))
					AuxTripla="aux2Tripla";
				else
					AuxTripla="aux3Tripla";
				this.escribir(cuerpoActual, "global.get $"+tipoOp2+AuxTripla);
				if(AuxTripla.equals("auxTripla"))
					this.aux1Tripla="";
				else if (AuxTripla.equals("aux2Tripla"))
					this.aux2Tripla="";
				else 
					this.aux3Tripla="";
			}
			this.escribir(cuerpoActual, "global.set $acceso"+op1);			
		}else {
			if(op2.matches("^[0-9].*")) {
				this.escribir(cuerpoActual, "i32.const "+op2);
			}else {
				this.escribir(cuerpoActual, "global.get $"+op2);
			}
			this.escribir(cuerpoActual, "global.set $acceso"+op1);	
		}
		
		this.escribir(cuerpoActual, "global.get $acceso"+op1);
		this.escribir(cuerpoActual, "i32.const 1");
		this.escribir(cuerpoActual, "i32.eq");
		this.escribir(cuerpoActual, "(if");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "(then");
		this.aumentarIdentacion();
		tipoOp1 = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO).equals("ulongint")?"i32":"f64";
		String auxTripla = "";
		if(aux1Tripla.equals("")) {
			auxTripla="auxTripla";
			this.aux1Tripla="["+this.posicionActual+"]";
		}else if(aux2Tripla.equals("")){
			auxTripla="aux2Tripla";
			this.aux2Tripla="["+this.posicionActual+"]";
		}else {
			auxTripla="aux3Tripla";
			this.aux3Tripla="["+this.posicionActual+"]";
		}
		this.escribir(cuerpoActual, "global.get $"+op1+"V1");
		this.escribir(cuerpoActual, "global.set $"+tipoOp1+auxTripla);
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.escribir(cuerpoActual, "(else");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "global.get $acceso"+op1);
		this.escribir(cuerpoActual, "i32.const 2");
		this.escribir(cuerpoActual, "i32.eq");
		this.escribir(cuerpoActual, "(if");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "(then");
		this.aumentarIdentacion();


		this.escribir(cuerpoActual, "global.get $"+op1+"V2");
		this.escribir(cuerpoActual, "global.set $"+tipoOp1+auxTripla);
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.escribir(cuerpoActual, "(else");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "global.get $acceso"+op1);
		this.escribir(cuerpoActual, "i32.const 3");
		this.escribir(cuerpoActual, "i32.eq");
		this.escribir(cuerpoActual, "(if");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "(then");
		this.aumentarIdentacion();

		
		this.escribir(cuerpoActual, "global.get $"+op1+"V3");
		this.escribir(cuerpoActual, "global.set $"+tipoOp1+auxTripla);
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.escribir(cuerpoActual, "(else");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "i32.const "+erroresEj.getDir("rango"));
		this.escribir(cuerpoActual, "i32.const "+erroresEj.getSizeMsj("rango"));
		this.escribir(cuerpoActual, "call $log");
		this.escribir(cuerpoActual, "call $exit");
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
	}

	// Escritura del .wat
	private void escribir(StringBuilder builder, String code) {
        	builder.append(this.ident + code + "\n");
    }
	
	private void escribeArchivo() {
        try (FileWriter fw = new FileWriter(this.filePath, true);
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
	
	// Conversiones 
	
	private void toDouble(Terceto t) {
		this.obtenerGets(t);
		this.escribir(cuerpoActual,"f64.convert_i32_s");
	}
	
	private void toUlongint(Terceto t) {
		this.obtenerGets(t);
		this.escribir(cuerpoActual, "f64.const 0");
		this.escribir(cuerpoActual, "f64.lt");
		this.escribir(cuerpoActual, "(if");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "(then");
		this.aumentarIdentacion();
		this.escribir(cuerpoActual, "i32.const "+erroresEj.getDir("conversionErronea"));
		this.escribir(cuerpoActual, "i32.const "+erroresEj.getSizeMsj("conversionErronea"));
		this.escribir(cuerpoActual, "call $log");
		this.escribir(cuerpoActual, "call $exit");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.reducirIdentacion();
		this.escribir(cuerpoActual, ")");
		this.obtenerGets(t);
		this.escribir(cuerpoActual,"i32.trunc_f64_u");
	}

	// Invocación de Funciones
	private void invocacionFuncion(Terceto t) {
		this.obtenerGets(t, false);
		String funcion = t.getOp1();
		this.escribir(cuerpoActual ,"i32.const " + this.funcionesId.get(funcionActual));
		this.escribir(cuerpoActual, "global.set $funcionLlamadora");
		this.escribir(cuerpoActual,"call $" + funcion.replace(':', 'A'));
	}
	
	private void retornoFuncion(Terceto t) {
		this.obtenerGets(t);
		this.escribir(cuerpoActual,"local.set $"+this.funcionActual.replace(':', 'A') +"retorno");
		this.escribir(cuerpoActual,"local.get $"+this.funcionActual.replace(':', 'A') +"retorno");
		this.escribir(cuerpoActual, "return");
	}
	
	// Impresiones por pantalla
	
	private void imprimirTriplas(String var, String tipo, String aux) { //para imprimir si es la id o una expresion
		this.escribir(cuerpoActual, "global.get $"+var+"V1"+aux);
		this.escribir(cuerpoActual, "call $console_log_"+tipo);
		this.escribir(cuerpoActual, "global.get $"+var+"V2"+aux);
		this.escribir(cuerpoActual, "call $console_log_"+tipo);
		this.escribir(cuerpoActual, "global.get $"+var+"V3"+aux);
		this.escribir(cuerpoActual, "call $console_log_"+tipo);
	}
	
	private void imprimirTriplas(String var, String tipo) { //si no es una auxiliar no tengo que agregarle al final el tipo
		imprimirTriplas(var,tipo,"");
	}
	
	private void outf(Terceto t) {
		String cadmul = t.getOp1();
		if (cadmul.startsWith("CADMUL:")) { //mensaje comun
	        this.escribir(cuerpoActual,"i32.const "+ this.ts.getPosicionMemoria(cadmul));
	        this.escribir(cuerpoActual,"i32.const "+ cadmul.substring("CADMUL:".length()).length());
	        this.escribir(cuerpoActual,"call $log");
	    } else {
			Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
			String op1 = t.getOp1();
		    Matcher matcher1 = pattern.matcher(op1);
		    boolean find = matcher1.find();
		    if(find) { //hay terceto, si hay operacion tiene que estar, salvo acceso o terceto de operacion de tripla
		    	String terceto = matcher1.group(1);
		    	Terceto tercetoExpresion = gc_main.getTerceto(Integer.parseInt(terceto));
		    	String tipo = tercetoExpresion.getTipo().equals("ulongint")?"i32":"f64"; //tipo del terceto(si es un acceso a tripla) o constante comun
		    	
		    	if(tercetoExpresion.getOperador().equals("ACCESOTRIPLE")) { //es un acceso a tripla y tengo que hacer get al dato
		    		String AuxTripla="";
		    		if(aux1Tripla.equals(t.getOp1()))
						AuxTripla="auxTripla";
					else if(aux2Tripla.equals(t.getOp1()))
						AuxTripla="aux2Tripla";
					else
						AuxTripla="aux3Tripla";
					this.escribir(cuerpoActual, "global.get $"+tipo+AuxTripla);
					if(AuxTripla.equals("auxTripla"))
						this.aux1Tripla="";
					else if (AuxTripla.equals("aux2Tripla"))
						this.aux2Tripla="";
					else 
						this.aux3Tripla="";
					this.escribir(cuerpoActual, "call $console_log_"+tipo);
					
				}else { // no es acceso a tripla
					
					if(!tercetoExpresion.getTipo().equals("ulongint") && !tercetoExpresion.getTipo().equals("double")) { //es una expresion de triplas
						tipo= this.ts.getAtributo(tercetoExpresion.getTipo(), "tipotripla"); //busco el tipo real de la tripla
						tipo = tipo.equals("ulongint")?"i32":"f64";
						String DatoGuardado;
						if(this.aux1.equals(t.getOp1())) //guardo los datos de el terceto op1 en aux1
							DatoGuardado="AUX1";
			    		else
			    			DatoGuardado="AUX2";
		    			imprimirTriplas(DatoGuardado,tipo,tipo);
		    			if(DatoGuardado.equals("AUX1")) {//Libero la variable auxiliar
		        			this.aux1=""; //entonces use lo de aux 1 y libero esa variable
		        		}else {
		        			this.aux2="";
		        		}
					}else {
						this.escribir(cuerpoActual, "call $console_log_"+tipo); //es una cosntante comun
					}
				}
		    	
		    	
		    	
		    }else { //no es un terceto
		    	String tipo = this.ts.getAtributo(op1, AccionSemantica.TIPO);
		    	if(this.ts.getAtributo(op1, AccionSemantica.USO).contains("nombre") && ( this.ts.getAtributo(op1, AccionSemantica.TIPO_BASICO).equals("") )) {//ID Y NO ES TRIPLA
		    		op1 = op1.replace(':', 'A');
		    		this.escribir(cuerpoActual, "global.get $"+op1);
		    		tipo = tipo.equals("ulongint")?"i32":"f64";
		    		this.escribir(cuerpoActual, "call $console_log_"+tipo);
		    	}else {
		    		op1 = op1.replace(':', 'A');
		    		switch(tipo) { //constantes
		    		case "ulongint":this.escribir(cuerpoActual, "i32.const "+op1);
		    					this.escribir(cuerpoActual, "call $console_log_i32");
		    					break;
		    		case "double":this.escribir(cuerpoActual, "f64.const "+op1);
		    					this.escribir(cuerpoActual, "call $console_log_f64");
		    					break;
		    		default: //TRIPLA
		    			tipo = this.ts.getAtributo(t.getOp1(), AccionSemantica.TIPO_BASICO).equals("ulongint")?"i32":"f64";
		    			imprimirTriplas(op1,tipo);
		    			break;
		    		}
		    	}
		    }
	    	
	    }
	}
}
