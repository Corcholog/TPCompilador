package paqueton;

import java.util.ArrayList;
import java.util.Map;
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
	private String posicionActual;
	private String ident;

	public GeneradorWasm(TablaSimbolos ts, GeneradorCodigo gc, String path) {
        this.ts = ts;
        this.gc_main = gc;
        this.funcionActual = "";
        this.ident = "";
        this.filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "paqueton" + File.separator + path + ".wat";
        
        // Poner en blanco el archivo al inicializar
        try (FileWriter fw = new FileWriter(filePath, false)) {
            // El archivo se sobrescribe en blanco al abrirse en modo no-apend (append = false)
        } catch (IOException e) {
            System.err.println("Error al vaciar el archivo: " + e.getMessage());
        }
    }
	
	public void traducir() {
		this.escribir("(module\n");
		this.ident += "\t";
		Map<String, GeneradorCodigo> funciones = this.ts.getFunciones();
		for (String funcion : funciones.keySet()) {
			this.funcionActual = funcion;
			GeneradorCodigo gcFuncion = funciones.get(funcion);
			String parametro = this.ts.getAtributo(funcion, AccionSemantica.PARAMETRO);
			String tipoParam = this.ts.getAtributo(parametro, AccionSemantica.TIPO).equals("double")? "f64":"i32";
			String tipoFuncion = this.ts.getAtributo(funcion, AccionSemantica.TIPO).equals("double")? "f64":"i32";
			this.escribir("( func $"+funcion + " (param $"+ parametro + " " + tipoParam+") (result "+ tipoFuncion + ")");
			this.ident = "\t\t";
			this.escribir(tipoFuncion+".const 0");
			this.escribir("local.set $"+funcion+"retorno");
			for (int i = 0; i < gcFuncion.getCantTercetos(); i++) {
				Terceto t = gcFuncion.getTerceto(i);
				if(!t.isHecho()) {
					this.ejecutarTraduccion(t, i);				
				}
			}
			this.escribir("local.get $"+funcion+"retorno");
			this.ident = "\t";
			this.escribir(")\n");
			
		}
		for (int i = 0; i < gc_main.getCantTercetos(); i++) {
			Terceto t = gc_main.getTerceto(i);
			if(!t.isHecho()) {
				this.ejecutarTraduccion(t, i);				
			}
		}
		this.ident = "\t";
		this.escribir(")");
	}
	
	public void ejecutarTraduccion(Terceto t, int pos) {
		String operador = t.getOperador().toLowerCase();
		switch(operador) {
			case "*": this.producto(t, pos); break;
			case "/": this.division(t, pos); break;
			case "+": this.suma(t, pos); break;
			case "-": this.resta(t, pos); break;
			case "<": this.menor(t, pos); break;
			case "<=": this.menorIgual(t, pos); break;
			case ">": this.mayor(t, pos); break;
			case ">=": this.mayorIgual(t, pos); break;
			case "=": this.igual(t, pos); break;
			case "!=": this.distinto(t, pos); break;
			case "and": this.and(t, pos); break;
			case "bi": this.bifurcacionIncondicional(t); break;
			case "bf": this.bifurcacionPorFalso(t); break;
			case ":=": this.asignacion(t, pos); break;
			case "todouble": this.toDouble(t); break;
			case "toulongint": this.toUlongint(t); break;
			case "invoc_fun": this.invocacionFuncion(t, pos); break;
			case "ret": this.retornoFuncion(t, pos); break;
			case "outf": this.outf(t); break;
			case "accesotriple": this.accesoTriple(t);break;
			case "goto": this.saltoIncondicional(t); break;
			case "tag": this.generarTag(t); break;
			
			default: 
				if (operador.matches("^label\\d*")) {
					this.generarTag(t);
			    }
				System.out.println(operador);
			    break;
		}
		t.setHecho(true);
	}
	
	private void obtenerGets(Terceto t, int pos) {
		this.obtenerGets(t, pos, true);
	}
	
	private void obtenerGets(Terceto t, int pos, boolean quieroIzquierdo) {
		String devolver = "";
		String op1 = t.getOp1();
		String op2 = t.getOp2();
		
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
		
	    Matcher matcher1 = pattern.matcher(op1);
	    boolean find1 = matcher1.find();
	    Matcher matcher2 = pattern.matcher(op2);
	    
	    if(quieroIzquierdo && !find1) {
	    	if(!op1.equals("")) {
	    		if(op1.matches("^[0-9].*")) {
	    		 this.escribir(t.getTipo().equals("double") ? "f64.const "+t.getOp1() : "i32.const "+t.getOp1());	
	    		} else {
	    			this.escribir("local.get $"+op1); 		
	    		}
	    	}
	    }else if(find1){
	    	int indiceTerceto = Integer.parseInt(matcher1.group(1));
	    	Terceto t_op1 = this.gc_main.getTerceto(indiceTerceto);
	    	if(pos < indiceTerceto) {
	    		this.ejecutarTraduccion(t_op1, indiceTerceto);
	    		
	    	}else if(pos > indiceTerceto && !t_op1.isHecho()) {
	    		this.ejecutarTraduccion(t_op1, indiceTerceto);
	    	}
	    }
	    
	    if(!matcher2.find()) {
	    	if(!op2.equals("")) {
	    		if(op2.matches("^[0-9].*")) {
	    			this.escribir(t.getTipo().equals("double") ? "f64.const "+t.getOp2() : "i32.const "+t.getOp2()); 
	    		} else {
	    			this.escribir("local.get $"+op2);	
	    		}    		
	    	}
	    } else {
	    	int indiceTerceto = Integer.parseInt(matcher2.group(1));
	    	Terceto t_op2 = this.gc_main.getTerceto(indiceTerceto);
	    	if(pos < indiceTerceto) {
	    		this.ejecutarTraduccion(t_op2, indiceTerceto);
	    		
	    	}else if(pos > indiceTerceto && !t_op2.isHecho()) {
	    		this.ejecutarTraduccion(t_op2, indiceTerceto);
	    	}
	    }
	}

	private String obtenerComparaciones(Terceto t, int pos) {
	    int comp1 = Integer.parseInt(t.getOp1().split("\\[|\\]")[1]);
	    int comp2 = Integer.parseInt(t.getOp2().split("\\[|\\]")[1]);
	    
	    if(gc_main.getTerceto(pos-1).getOperador().equals("AND")) {
	    	return "local.get $comp"+comp2+"\n";
	    }
	    return "local.get $comp"+comp1+"\nlocal.get $comp"+comp2+"\n";
	}
	
 	private void menor(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.lt");
			this.escribir("local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.lt" );
			this.escribir("local.set $comp"+i);
		}
	}

	private void menorIgual(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.le");
			this.escribir("local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.le");
			this.escribir("local.set $comp"+i);
		}
		
	}

	private void mayor(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.gt");
			this.escribir("local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.gt");
			this.escribir("local.set $comp"+i);
		}
	}

	private void mayorIgual(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.ge");
			this.escribir("local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.ge");
			this.escribir("local.set $comp"+i);
		}
	}

	private void igual(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.eq");
			this.escribir("local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.eq");
			this.escribir("local.set $comp"+i);
		}
	}
	
	private void distinto(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.ne");
			this.escribir("local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.ne");
			this.escribir("local.set $comp"+i);
		}
	}
	
	private void suma(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.add");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.add");
		} //t1 := t2 + t3 debe ser suma de arreglos
	}

	private void resta(Terceto t, int i) { //TODO chequeo de que quede negativo
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.sub");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.sub");
		}
	}

	private void producto(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.mul");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.mul");
		}
	}

	private void division(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.obtenerGets(t, i);
			this.escribir("f64.div");
		} else if(tipo.equals("ulongint")) {
			this.obtenerGets(t, i);
			this.escribir("i32.div");
		}
	}

	private void generarTag(Terceto t) {
		// TODO Auto-generated method stub
		
	}

	private void saltoIncondicional(Terceto t) {
		// TODO Auto-generated method stub
		
	}

	private void accesoTriple(Terceto t) {
		// TODO Auto-generated method stub
		
	}

	private void escribir(String code) {
        try (FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
        	bw.write(this.ident + code);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
	
	private void toDouble(Terceto t) {
		this.obtenerGets(t, -1);
		this.escribir("f64_convert_i32_s");
	}
	
	private void toUlongint(Terceto t) {
		this.obtenerGets(t, -1);
		this.escribir("i32.trunc_f64_s");
	}

	private void asignacion(Terceto t, int i) {
		this.obtenerGets(t, i, false);
		this.escribir("local.set $" + t.getOp1());
	}
	
	private void and(Terceto t, int i) {
		this.escribir(this.obtenerComparaciones(t, i)+"i32.and");
	}
	
	private void invocacionFuncion(Terceto t, int i) {
		this.obtenerGets(t, i, false);
		this.escribir("call $" + t.getOp1());
	}
	
	private void retornoFuncion(Terceto t, int i) {
		this.obtenerGets(t, i);
		this.escribir("local.set $"+this.funcionActual+"retorno");
	}
	
	private void bifurcacionIncondicional(Terceto t) {
		
	}
	
	private void bifurcacionPorFalso(Terceto t) {
		
	}
	
	private void outf(Terceto t) {
		
	}
	
}
