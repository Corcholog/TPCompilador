package paqueton;
import java.math.BigInteger;
public abstract class AccionSemantica {
	public static final BigInteger MAX_INT = new BigInteger("4294967296");
    public static final double MIN_DOUBLE = 2.2250738585072014e-308;
    public static final double MAX_DOUBLE = 1.7976931348623157e+308;
	
	public void checkString(AnalizadorLexico analizador) {
		String concatActual = analizador.getConcatActual();
		if(concatActual.length() > 15) {
			analizador.addWarning("Se superó el máximo de caracteres para un identificador (15). Se truncará");
			analizador.setConcatActual(concatActual.substring(0, 15));
		}
	}
	
	public boolean checkLongInt(AnalizadorLexico analizador) {
		BigInteger actual = new BigInteger(analizador.getConcatActual());
		if(actual.compareTo(MAX_INT) >= 0 || actual.compareTo(new BigInteger("0")) == -1) {
			analizador.addError("Constante fuera de rango.");
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkDouble(AnalizadorLexico analizador) {
        try {
        	String number = analizador.getConcatActual();
            String normalizedNumber = number.replaceAll("d", "e").replaceAll("D", "e");
            double value = Double.parseDouble(normalizedNumber);
            
            boolean enRango = (value >= MIN_DOUBLE && value <= MAX_DOUBLE) || 
                    (value <= -MIN_DOUBLE && value >= -MAX_DOUBLE) || 
                    value == 0.0;
            if (!enRango) {
            	analizador.addError("Constante double fuera de rango");
            }
            return enRango;
        } catch (NumberFormatException e) {
            // Si el formato no es correcto, no es un double válido
            return false;
        }
	}
	
	public boolean checkOctal(AnalizadorLexico analizador) {
		String number = analizador.getConcatActual();
		BigInteger actual = new BigInteger(number, 8);
		if(actual.compareTo(MAX_INT) >= 0 || actual.compareTo(new BigInteger("0")) == -1) {
			analizador.addError("Constante fuera de rango.");
			return false;
		} else {
			return true;
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
	    	int numToken = analizador.getTokenReservada();
	    	analizador.setNroToken(Integer.toString(numToken));
	    }
	}
	
	static class ASF1Constante extends AccionSemantica{
		public void ejecutar(AnalizadorLexico analizador) {
		
		}
	}
	
	static class ASF1Comp extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	        new AS1().ejecutar(analizador);
	    	int numToken = analizador.getNumToken();
	    	analizador.setNroToken(Integer.toString(numToken));
	    }
	}
	
	static class ASF1OCTAL extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	    	if (checkOctal(analizador)) {
		        new AS1().ejecutar(analizador);
		        new ASFBR().ejecutar(analizador);
	    	}
	    }
	}
	
	static class ASF1Double extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	    	if(checkDouble(analizador)) {
		        new ASFBR().ejecutar(analizador);
	    	}
	    }
	}
	
	static class ASF1LongInt extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	    	if(checkLongInt(analizador)) {
		        new ASFBR().ejecutar(analizador);
	    	}
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
			int numToken = analizador.getIdTokens().get("constantes");
	    	analizador.setNroToken(Integer.toString(numToken));	     
	    }
	}
	
	

	static class ASFBR2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addError("Falta numero del exponente");
	    }
	}

	static class ASFBR3 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			if (analizador.esPalabraReservada()) {
				int numToken = analizador.getTokenReservada();
		    	analizador.setNroToken(Integer.toString(numToken));
			}
			else { 
				super.checkString(analizador);
				analizador.addTablaSimbolos();
				int numToken = analizador.getIdTokens().get("id");
				analizador.setNroToken(Integer.toString(numToken));
			}
	    }
	}

	static class ASFBR4 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			super.checkString(analizador);
			analizador.addTablaSimbolos();
			analizador.addAtributoTablaSimbolos("tipo","ulongint");
			int numToken = analizador.getIdTokens().get("id");
			analizador.setNroToken(Integer.toString(numToken));
	    }
	}

	static class ASFBR5 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			super.checkString(analizador);
			analizador.addTablaSimbolos();
			analizador.addAtributoTablaSimbolos("tipo","double");
			int numToken = analizador.getIdTokens().get("id");
			analizador.setNroToken(Integer.toString(numToken));
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

	static class ASF2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			new ASFBR().ejecutar(analizador);
	    }
	}
	
	static class ASF2LongInt extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			if(checkLongInt(analizador)) {
				new ASFBR().ejecutar(analizador);
			}
	    }
	}
	static class ASF2Double extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			if(checkDouble(analizador)) {
				new ASFBR().ejecutar(analizador);
			}
	    }
	}
	
	static class ASF2OCTAL extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	    	if (checkOctal(analizador)) {
		        new ASFBR().ejecutar(analizador);
	    	}
	    }
	}
	
	static class ASF2COMP extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	int numToken = analizador.getNumToken();
	    	analizador.setNroToken(Integer.toString(numToken));	  
		}
	}

	static class ASE4 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addWarning("Si quisiste armar un double, te falto la parte decimal");
	        new ASF2().ejecutar(analizador);
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

	static class ASF3 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			new ASFBR3().ejecutar(analizador);
		}
	}

	static class ASF4 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			new ASFBR3().ejecutar(analizador);
	    }
	}

	static class ASF5 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			new ASFBR4().ejecutar(analizador);
	    }
	}

	static class ASF6 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			new ASFBR4().ejecutar(analizador);
	    }
	}

	static class ASF7 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			new ASFBR5().ejecutar(analizador);
	    }
	}

	static class ASF8 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			new ASFBR5().ejecutar(analizador);
	    }
	}

	static class ASE9 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			analizador.addWarning("Faltó un =, fue agregado.");
			analizador.setConcatActual(analizador.getConcatActual().concat("="));
			int numToken = analizador.getTokenReservada();
	    	analizador.setNroToken(Integer.toString(numToken));
	    }
	}

	static class ASE10 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        new ASE9().ejecutar(analizador);
	    }
	}

	static class ASFBR6 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        new ASE9().ejecutar(analizador);
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
	
}

