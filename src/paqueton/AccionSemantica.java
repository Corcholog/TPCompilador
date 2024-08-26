package paqueton;
public abstract class AccionSemantica {
 
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
	        new AS1().ejecutar(analizador);
	    }
	}
	static class ASF1 extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	        new AS1().ejecutar(analizador);
	    	int numToken = analizador.getNumToken();
	    	analizador.setNroToken(Integer.toString(numToken));
	    }
	}
	
	static class ASF1OCTAL extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	        new AS1().ejecutar(analizador);
	        new ASFBR().ejecutar(analizador);
	    }
	}
	
	static class ASF1CONSTANTE extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	        new ASFBR().ejecutar(analizador);
	    }
	}

	static class ASBR extends AccionSemantica {
		// contemplado al manejar el programa por lineas (en el switch break line)
		public void ejecutar(AnalizadorLexico analizador) {
			
	    }
	}

	static class ASBR2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        analizador.concatenaSaltoLinea();
	    }
	}

	static class ASFBR extends AccionSemantica { //cambiarlo para el comparador.
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
				analizador.addTablaSimbolos();
				int numToken = analizador.getIdTokens().get("id");
				analizador.setNroToken(Integer.toString(numToken));
			}
	    }
	}

	static class ASFBR4 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			analizador.addTablaSimbolos();
			analizador.addAtributoTablaSimbolos("tipo","ulongint");
			int numToken = analizador.getIdTokens().get("id");
			analizador.setNroToken(Integer.toString(numToken));
	    }
	}

	static class ASFBR5 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			analizador.addTablaSimbolos();
			analizador.addAtributoTablaSimbolos("tipo","double");
			int numToken = analizador.getIdTokens().get("id");
			analizador.setNroToken(Integer.toString(numToken));
	    }
	}

	static class ASE2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addError("Falta el [ para iniciar la cadena");
	        new AS1().ejecutar(analizador);
	    }
	}

	static class ASE3 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addError("Caracter inesperado");
	    	analizador.avanzarPos();
	    }
	}

	static class ASF2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			new ASFBR().ejecutar(analizador);
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
	    	analizador.addError("Falto el . para armar el exponente");
	        new AS1().ejecutar(analizador);
	    }
	}

	static class ASE5 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addError("No es un digito de los octales");
	    	analizador.avanzarPos();
	    }
	}


	static class ASE7 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addError("Sobra un punto");
	    	analizador.avanzarPos();
	    }
	}

	static class ASE8 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	    	analizador.addError("No tiene digitos el exponente");
	    	analizador.avanzarPos();
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
	        // Es como ASF4 pero no chequea palabra clave y es tipo entero
	        //analizador.retroceder();
	       // analizador.devolverToken();
	        // No se chequea palabra clave
	    }
	}

	static class ASF7 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Es igual a ASF5 pero chequea tipo double
	        //analizador.devolverToken();
	        // Chequea tipo double
	    }
	}

	static class ASF8 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Es ASF6 pero chequea tipo double
	        //analizador.retroceder();
	        //analizador.devolverToken();
	        // Chequea tipo double
	    }
	}

	static class ASE9 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Faltó igual, y retrocedo uno para no perder el valor
	       // analizador.retroceder();
	        // Manejar el error correspondiente
	    }
	}

	static class ASE10 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Faltó el igual, pero como es un espacio no retrocede, sigue compilando
	        // Continuar la compilación
	    }
	}

	static class ASFBR6 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Faltó un igual, y salto de línea y sumo 1 a la línea
	        //analizador.sumarLinea();
	        // Manejar el error correspondiente
	    }
	}

	static class ASE11 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Faltó un '#', se sigue compilando
	        // Continuar la compilación
	    }
	}
	
}

