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
		for (int i = 0; i < gc_main.getCantTercetos(); i++) {
			Terceto t = gc_main.getTerceto(i);
			if(!t.isHecho()) {
				this.ejecutarTraduccion(t, i);				
			}
		}
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
				System.out.println(operador);
			    break;
		}
		t.setHecho(true);
	}
	
	private String obtenerGets(Terceto t, int pos) {
		return this.obtenerGets(t, pos, true);
	}
	
	private String obtenerGets(Terceto t, int pos, boolean quieroIzquierdo) {
		String devolver = "";
		String op1 = t.getOp1();
		String op2 = t.getOp2();
		
		Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
		
	    Matcher matcher1 = pattern.matcher(op1);
	    boolean find1 = matcher1.find();
	    Matcher matcher2 = pattern.matcher(op2);
	    
	    if(quieroIzquierdo && !find1) {
	    	System.out.println("No se encontro un terceto a la izquierda en terceto " + pos);
	    	if(!op1.equals("")) {
	    		devolver+="local.get $"+op1+"\n";	    		
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
	    	System.out.println("No se encontro un terceto a la derecha");
	    	if(!op2.equals("")) {
	    		devolver+="local.get $"+op2+"\n";	    		
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
	    
		return devolver;
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
			this.escribir(this.obtenerGets(t, i) + "f64.lt\n" + "local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.lt\n" + "local.set $comp"+i);
		}
	}

	private void menorIgual(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t, i) + "f64.le\n" + "local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.le\n" + "local.set $comp"+i);
		}
		
	}

	private void mayor(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t, i) + "f64.gt\n" + "local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.gt\n" + "local.set $comp"+i);
		}
		
	}

	private void mayorIgual(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t, i) + "f64.ge\n" + "local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.ge\n" + "local.set $comp"+i);
		}
	}

	private void igual(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t, i) + "f64.eq\n" + "local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.eq\n" + "local.set $comp"+i);
		}
	}
	
	private void distinto(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t, i) + "f64.ne\n" + "local.set $comp"+i);
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.ne\n" + "local.set $comp"+i);
		}
	}
	
	private void suma(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t, i) + "f64.add");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.add");
		} //t1 := t2 + t3 debe ser suma de arreglos
	}

	private void resta(Terceto t, int i) { //TODO chequeo de que quede negativo
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t, i) + "f64.sub");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.sub");
		}
	}

	private void producto(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t, i) + "f64.mul");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.mul");
		}
	}

	private void division(Terceto t, int i) {
		String tipo = t.getTipo();
		if(tipo.equals("double")) {
			this.escribir(this.obtenerGets(t, i) + "f64.div");
		} else if(tipo.equals("ulongint")) {
			this.escribir(this.obtenerGets(t, i) + "i32.div");
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
            bw.write(code);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
	
	private void toDouble(Terceto t) {
		this.escribir(this.obtenerGets(t, -1) + "f64_convert_i32_s");
	}
	
	private void toUlongint(Terceto t) {
		this.escribir(this.obtenerGets(t, -1) + "i32.trunc_f64_s");
	}

	private void asignacion(Terceto t, int i) {
		this.escribir(this.obtenerGets(t, i, false) + "local.set $"+ t.getOp1());
	}
	
	private void and(Terceto t, int i) {
		this.escribir(this.obtenerComparaciones(t, i)+"i32.and");
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
