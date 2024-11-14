package paqueton;

import java.util.Map;
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
	private boolean generandoFuncion = false;
	
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
        this.ifs = new Stack<String>();
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
		this.generandoFuncion = true;
		for (String funcion : funciones.keySet()) {
			cuerpoActual = new StringBuilder();
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
			this.funciones.append(cuerpoActual.toString());
			
		}
		this.generandoFuncion = false;
		this.cuerpoActual = cuerpoMain;
		this.variablesActual = inicioMain;
		this.escribir(inicioMain,"(func $main");
		this.aumentarIdentacion();
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

	public void cargarCadmuls() {
		Map<String, Integer> cadmuls = this.ts.getCadenas();
		Integer dirMem = 101;
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
	
 	private void menor(Terceto t) { // una vez que planteemos las triplas se puede modularizar esto seguro.
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.gt_s");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.gt_u");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		}
 		
	}

	private void menorIgual(Terceto t) {
		String tipo = t.getTipo();
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
		}
	}

	private void mayor(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.lt_s");
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.lt_u" );
			this.escribir(variablesActual,"(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		}

	}

	private void mayorIgual(Terceto t) {
		String tipo = t.getTipo();
		System.out.println("op1 " + t.getOp1() + " op2 " + t.getOp2() + " tipo " + tipo);
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
		}
	}

	private void igual(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.eq_s");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.eq_u");
			this.escribir(variablesActual, "(local $comp"+this.posicionActual + " i32)");
			this.escribir(cuerpoActual,"local.set $comp"+this.posicionActual);
			this.escribir(cuerpoActual,"local.get $comp"+this.posicionActual);
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
		}
	}
	
	
	private void suma(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.add");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.add");
		} //t1 := t2 + t3 debe ser suma de arreglos
	}

	private void resta(Terceto t) { //TODO chequeo de que quede negativo
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.sub");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.sub");
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
		}
	}

	private void division(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"f64.div");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t);
			this.escribir(cuerpoActual,"i32.div");
		}
	}

	private void generarTag(Terceto t) {
		this.escribir(cuerpoActual,"(label $"+t.getOp1().replace(':', 'A')+")");
	}

	private void saltoIncondicional(Terceto t) {
		this.escribir(cuerpoActual,"br $"+t.getOp1().replace(':', 'A'));
		
	}

	private void accesoTriple(Terceto t) {
		// TODO Auto-generated method stub
		this.escribir(cuerpoActual,"global.get $"+t.getOp1()+"V"+t.getOp2());
	}

	private void escribir(StringBuilder builder, String code) {
        	builder.append(this.ident + code + "\n");
       
    }
	
	private void escribeArchivo() {
        try (FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
        	bw.write(variablesGlobales.toString() + "\n" );
        	bw.write(funciones.toString()+ "\n");
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
		this.obtenerGets(t, false);
		this.escribir(cuerpoActual,"global.set $" + t.getOp1().replace(':', 'A'));
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
		// TODO Auto-generated method stub
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
	    } else {
	    	this.obtenerGets(t);
	    	this.escribir(cuerpoActual,"i32.const 0");
	    }
		this.escribir(cuerpoActual,"call $log");
	}
}
