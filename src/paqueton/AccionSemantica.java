package paqueton;
import java.math.BigInteger;
public abstract class AccionSemantica {
	public static final BigInteger MAX_INT = new BigInteger("4294967296");
	public static final String MaxIntRepresentable = "4294967295";
    public static final double MIN_DOUBLE = 2.2250738585072015e-308;
    public static final double MAX_DOUBLE = 1.7976931348623156e+308;
    public static final String REPRESENTACION = "representacion";
    public static final String CANTIDAD = "cantidad";
    public static final String USO = "uso";
    public static final String TIPO = "tipo";
    public static final String TIPO_BASICO = "tipo_basico";
    public static final String PARAMETRO = "parametro";
    public static final String FUNCION = "funcion";
    public static final String ULONGINT = "ulongint";
    public static final String DOUBLE = "double";
    public static final String OCTAL = "octal";
    public static final String TAG = "tag";

    
    protected static String tipoCte;
	
	public void checkString(AnalizadorLexico analizador) {
		String concatActual = analizador.getConcatActual();
		if(concatActual.length() > 15) {
			analizador.addWarning("Se superó el máximo de caracteres para un identificador (15). Se truncará");
			analizador.setConcatActual(concatActual.substring(0, 15));
		}
	}
	
	public void checkLongInt(AnalizadorLexico analizador) {
		BigInteger actual = new BigInteger(analizador.getConcatActual());
		if(actual.compareTo(MAX_INT) >= 0) {
        	analizador.addWarning("La constante Ulongint base 10 esta fuera de rango, es mayor a la representacion, se truncó al maximo representable");
        	analizador.setConcatActual(MaxIntRepresentable);
		}
	}
	
	public void checkDouble(AnalizadorLexico analizador) {
	    try {
	        String number = analizador.getConcatActual();
	        String normalizedNumber = number.toLowerCase().replaceAll("d", "e");

	        // Verificar si falta parte decimal (caso: "2.", "-2.")
	        if (normalizedNumber.endsWith(".")) {
	            analizador.addWarning("Falta parte decimal luego del '.', se agregará un '0'");
	            normalizedNumber += "0";
	        }

	        // Verificar si falta el exponente (caso: "2.0", "2.", "-2.0")
	        if (!normalizedNumber.contains("e")) {
	            analizador.addWarning("Falta exponente, se agregará 'e0'");
	            normalizedNumber += "e0";
	        }

	        // Chequear el rango
	        double value = Double.parseDouble(normalizedNumber);
	        if (value > MAX_DOUBLE) {
	            analizador.addWarning("La constante Double está fuera de rango, es mayor a la representación; se truncó al máximo representable");
	            value = MAX_DOUBLE;
	            analizador.setConcatActual(String.format("%.16e", value));
	        } else if (value < MIN_DOUBLE) {
	            analizador.addWarning("La constante Double está fuera de rango, es menor a la representación; se truncó al mínimo representable");
	            value = MIN_DOUBLE;
	            analizador.setConcatActual(String.format("%.16e", value));
	        } else {
	            // Formateo al estilo WebAssembly (notación científica con 'e')
	            analizador.setConcatActual(String.format("%.16e", value));
	        }

	    } catch (NumberFormatException e) {
	        analizador.addWarning("Número inválido para double");
	    }
	}

	
	public void checkOctal(AnalizadorLexico analizador) {
		String number = analizador.getConcatActual();
		BigInteger actual = new BigInteger(number, 8);
		if(actual.compareTo(MAX_INT) >= 0){
			analizador.addWarning("Constante Ulongint base 8 fuera de rango, se trunco al maximo representable");
	        BigInteger maxOctalInt = new BigInteger("4294967295");
	        String octalString = maxOctalInt.toString(8);
        	analizador.setConcatActual(maxOctalInt.toString());
		}else {
			analizador.setConcatActual(actual.toString());
		}
	}
 
	public abstract void ejecutar(AnalizadorLexico al);
	
	static class AS0 extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	        analizador.avanzarPos();
	    }
	}

	static class AS1 extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.concatenar();
	    	analizador.avanzarPos();   
	    }
	}

	static class ASE1 extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addError("Falta letra que inicie el identificador");
	    }
	}
	
	static class ASF1 extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	        new AS1().ejecutar(analizador);
	    	int numToken = analizador.getIdToken();
	    	analizador.setNroToken(numToken);
	    }
	}
	
	static class ASF1Comp extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	        new ASF1().ejecutar(analizador);
	        String concatActual = analizador.getConcatActual();
	        String cadMul = "CADMUL:"+concatActual.substring(1, concatActual.length()-1);
	        analizador.getTablaSimbolos().addCadmul(cadMul);
	        analizador.getParser().yylval = new ParserVal(cadMul);
	    }
	}
	
	static class ASF1OCTAL extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	    	checkOctal(analizador);
			this.tipoCte=ULONGINT;
		    new AS1().ejecutar(analizador);
		    new ASFBR().ejecutar(analizador);
		    analizador.getTablaSimbolos().addAtributo(analizador.getConcatActual(), REPRESENTACION, OCTAL);
	    
	    }
	}

	static class ASBR extends AccionSemantica {
		// contemplado al manejar el programa por lineas (en el switch break line)
		public void ejecutar(AnalizadorLexico analizador) {
			
	    }
	}

	static class ASBR2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        analizador.concatenaMultilinea();
	    }
	}

	static class ASFBR extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			int numToken = Parser.CTE;
			TablaSimbolos ts = analizador.getTablaSimbolos();
			String concatActual = analizador.getConcatActual();
			if(!ts.estaEnTablaSimbolos(concatActual)) {
				analizador.addTablaSimbolos();
				ts.addAtributo(concatActual, CANTIDAD, "1");
				ts.addAtributo(concatActual,TIPO, this.tipoCte);
			} else {
				int cant = Integer.parseInt(ts.getAtributo(concatActual, CANTIDAD));
				cant++;
				ts.updateAtributo(concatActual, CANTIDAD, Integer.toString(cant));
			}
	    	analizador.setNroToken(numToken);	
	    	analizador.getParser().yylval = new ParserVal(concatActual);
	    }
	}
	
	


	static class ASFBR3 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			if (analizador.esPalabraReservada()) {
				if (analizador.getConcatActual().equals(DOUBLE) || analizador.getConcatActual().equals(ULONGINT)) {
					analizador.getParser().tipoVar = analizador.getConcatActual();
				}
				int numToken = analizador.getIdToken();
		    	analizador.setNroToken(numToken);
			}
			else { 
				super.checkString(analizador);
				//analizador.addTablaSimbolos();
				int numToken = Parser.ID;
				analizador.setNroToken(numToken);
				analizador.getParser().yylval = new ParserVal(analizador.getConcatActual());
			}
	    }
	}

	static class ASFBR4 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			super.checkString(analizador);
			analizador.addTablaSimbolos("global:");
			analizador.addAtributoTablaSimbolos(TIPO,ULONGINT,"global:");
			analizador.addAtributoTablaSimbolos(USO,"nombre variable","global:");
			analizador.getParser().yylval = new ParserVal(analizador.getConcatActual());
			int numToken = Parser.ID;
			analizador.setNroToken(numToken);
	    }
	}

	static class ASFBR5 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			if (analizador.esPalabraReservada()) {
				if (analizador.getConcatActual().equals(DOUBLE)) {
					analizador.getParser().tipoVar = analizador.getConcatActual();
				}
				int numToken = analizador.getIdToken();
		    	analizador.setNroToken(numToken);
			}
			else {
				super.checkString(analizador);
				analizador.addTablaSimbolos("global:");
				analizador.addAtributoTablaSimbolos(TIPO,DOUBLE,"global:");
				analizador.addAtributoTablaSimbolos(USO,"nombre variable","global:");
				analizador.getParser().yylval = new ParserVal(analizador.getConcatActual()); 
				int numToken = Parser.ID;
				analizador.setNroToken(numToken);				
			}
	    }
	}

	static class ASE2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addWarning("Faltó el [ para iniciar la cadena, no se toma como una.");
	    	analizador.avanzarPos();
	    }
	}

	static class ASE3 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addWarning("Caracter inesperado");
	    	analizador.avanzarPos();
	    }
	}
	
	static class ASE10 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addWarning("Vino un @ sin antes especificar un ID, es descartado");
	    	analizador.avanzarPos();
	    }
	} 

	static class ASF2LongInt extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			checkLongInt(analizador);
			this.tipoCte=ULONGINT;
			new ASFBR().ejecutar(analizador);
	    }
	}
	static class ASF2Double extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			checkDouble(analizador);
			this.tipoCte=DOUBLE;
			new ASFBR().ejecutar(analizador);
			
	    }
	}
	
	static class ASF2OCTAL extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	    	checkOctal(analizador);
			this.tipoCte=ULONGINT;
		    new ASFBR().ejecutar(analizador);
		    analizador.getTablaSimbolos().addAtributo(analizador.getConcatActual(), REPRESENTACION, OCTAL);
	    }
	}
	
	static class ASF2COMP extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	int numToken = analizador.getIdToken();
	    	analizador.setNroToken(numToken);	  
		}
	}
	

	static class ASE4 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addWarning("Si quisiste armar un double, te falto la parte decimal");
	        new ASFBR().ejecutar(analizador);
	    }
	}

	static class ASE5 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addWarning("Un digito no fue de los octales, se descartó.");
	    	analizador.avanzarPos();
	    }
	}


	static class ASE7 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addWarning("Sobraba un punto, se descartó");
	    	analizador.avanzarPos();
	    }
	}

	static class ASE8 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addError("No tiene digitos el exponente.");
	    }
	}


	static class ASE9 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			analizador.addWarning("Faltó un =, fue agregado.");
			analizador.setConcatActual(analizador.getConcatActual().concat("="));
			int numToken = analizador.getIdToken();
	    	analizador.setNroToken(numToken);
	    }
	}


	static class ASE11 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			analizador.addWarning("Faltó un #, fue agregado.");
			analizador.setConcatActual(analizador.getConcatActual().concat("#"));
			new AS1().ejecutar(analizador);
	    }
	}
	
	static class ASDescartaComentario extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			analizador.setConcatActual("");
		}
	}
	
	static class ASFGOTO extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			new AS1().ejecutar(analizador);  
	    	super.checkString(analizador);
	    	int numToken = Parser.TAG;
			//analizador.addTablaSimbolos();
	    	analizador.setNroToken(numToken);
	    	//analizador.getTablaSimbolos().addAtributo(analizador.getConcatActual(),TIPO,TAG);
	    	analizador.getParser().yylval = new ParserVal(analizador.getConcatActual());
		}
	}

}

