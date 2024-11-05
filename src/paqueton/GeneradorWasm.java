package paqueton;

import java.util.ArrayList;
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

	public GeneradorWasm(TablaSimbolos ts, GeneradorCodigo gc, String path) {
        this.ts = ts;
        this.gc_main = gc;
        this.filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "paqueton" + File.separator + path + ".wat";
        
        // Poner en blanco el archivo al inicializar
        try (FileWriter fw = new FileWriter(filePath, false)) {
            // El archivo se sobrescribe en blanco al abrirse en modo no-apend (append = false)
        } catch (IOException e) {
            System.err.println("Error al vaciar el archivo: " + e.getMessage());
        }
    }
	
	public void traducir() {
		ArrayList<Terceto> tercetos_main = gc_main.getTercetos();
		for (Terceto t : tercetos_main) {
			this.ejecutarTraduccion(t);
		}
	}
	
	public void ejecutarTraduccion(Terceto t) {
		String operador = t.getOperador().toLowerCase();
		switch(operador) {
			case "*": this.producto(t); break;
			case "/": this.division(t); break;
			case "+": this.suma(t); break;
			case "-": this.resta(t); break;
			case "<": System.out.println("HOOOOLA");this.menor(t); break;
			case "<=": this.menorIgual(t); break;
			case ">": this.mayor(t); break;
			case ">=": this.mayorIgual(t); break;
			case "=": this.igual(t); break;
			case "!=": this.comparacion(t); break;
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
					this.generarTag(t);
			    }
			    break;
		}
	}
	
	private String obtenerGets(Terceto t) {
		String devolver = "";
		String op1 = t.getOp1();
		String op2 = t.getOp2();
		
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
		
	    Matcher matcher1 = pattern.matcher(op1);
	    Matcher matcher2 = pattern.matcher(op2);
	    
	    if(!matcher1.find()) {
	    	System.out.println("Se encontro un terceto a la izquierda");
	    	devolver+="local.get $"+op1+"\n";
	    }
	    if(!matcher2.find()) {
	    	System.out.println("Se encontro un terceto a la derecha");
	    	devolver+="local.get $"+op2+"\n";
	    }
	    
		return devolver;
	}
	
	private void menor(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t) + "f64.lt");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t) + "i32.lt");
		}
	}

	private void menorIgual(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t) + "f64.le");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t) + "i32.le");
		}
		
	}

	private void mayor(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t) + "f64.gt");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t) + "i32.gt");
		}
		
	}

	private void mayorIgual(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t) + "f64.ge");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t) + "i32.ge");
		}
	}

	private void igual(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t) + "f64.eq");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t) + "i32.eq");
		}
	}
	
	private void suma(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t) + "f64.add");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t) + "i32.add");
		}
	}

	private void resta(Terceto t) { //TODO chequeo de que quede negativo
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t) + "f64.sub");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t) + "i32.sub");
		}
	}

	private void producto(Terceto t) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t) + "f64.mul");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t) + "i32.mul");
		}
	}

	private void division(Terceto t) {
		// TODO Auto-generated method stub
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t) + "f64.div");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t) + "i32.div");
		}
	}


	private void generarTag(Terceto t) {
		// TODO Auto-generated method stub
		
	}

	private void saltoIncondicional(Terceto t) {
		// TODO Auto-generated method stub
		
	}

	private void accesoTriple(Terceto t) {
		
	}

	private void escribir(String code) {
        try (FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(code);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
	
	private void toDouble(Terceto t) {
		
	}
	
	private void toUlongint(Terceto t) {
		
	}

	private void comparacion(Terceto t) {
		
	}
	
	private void and(Terceto t) {
		
	}
	
	private void asignacion(Terceto t) {
		
	}
	
	private void invocacionFuncion(Terceto t) {
		
	}
	
	private void retornoFuncion(Terceto t) {
		
	}
	
	private void bifurcacionIncondicional(Terceto t) {
		
	}
	
	private void bifurcacionPorFalso(Terceto t) {
		
	}
	
	private void outf(Terceto t) {
		
	}
	
}
