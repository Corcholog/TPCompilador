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
	    	// Concatenar lo anterior y avanzar 1 en el código fuente
	    	analizador.concatenar();
	    	analizador.avanzarPos();
	        
	    }
	}

	static class ASE1 extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	        // Falta letra de ese identificador -> warning y lo tomamos como idr
	    	analizador.addError("Falta letra que inicie el identificador");
	        new AS1().ejecutar(analizador);
	    }
	}
	static class ASF1 extends AccionSemantica {
	    public void ejecutar(AnalizadorLexico analizador) {
	        // Devolver el token
	        new AS1().ejecutar(analizador);
	    	int numToken = analizador.getNumToken();
	    	analizador.setNroToken(Integer.toString(numToken));
	    }
	}

	static class ASBR extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
			// contemplado al manejar el programa por lineas (en el switch break line)
	    }
	}

	static class ASBR2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        analizador.concatenaSaltoLinea();
			// Concatenar salto de línea y sumar 1 a la línea
	        //analizador.concatenar("\n");
	        //analizador.sumarLinea();
	    }
	}

	static class ASFBR extends AccionSemantica { //cambiarlo para el comparador.
		public void ejecutar(AnalizadorLexico analizador) {
	    	int numToken = analizador.getIdTokens().get("Constantes");
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
			//primero chequeo palabra reservada
			if (analizador.esPalabraReservada()) {
				int numToken = analizador.getTokenReservada();
		    	analizador.setNroToken(Integer.toString(numToken));
			}
			else { 
				analizador.addTablaSimbolos();
				int numToken = analizador.getIdTokens().get("ID");
				analizador.setNroToken(Integer.toString(numToken));
			}
			
			
			// Sumar 1 línea, retroceder uno, devolver token, y buscar en tabla si es palabra clave
	        //analizador.sumarLinea();
	        //analizador.retroceder();
			// if (analizador.esPalabraClave(analizador.getToken())) {
			//     analizador.devolverToken();
			// }
	    }
	}

	static class ASFBR4 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Es ASFBR3 pero sin chequear palabras clave, y es tipo entero
			//analizador.sumarLinea();
			//analizador.retroceder();
			//analizador.devolverToken();
	        // No se chequea palabra clave
	    }
	}

	static class ASFBR5 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Es ASFBR4 pero chequea tipo double
			//analizador.sumarLinea();
			//analizador.retroceder();
			//analizador.devolverToken();
	        // Chequea tipo double
	    }
	}

	static class ASE2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Faltó el '['
	        // Manejar el error correspondiente
	    }
	}

	static class ASE3 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Carácter inesperado (podemos ignorarlo y seguir)
	        // Ignorar y seguir leyendo
	    }
	}

	static class ASF2 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Devolver el token, pero retrocediendo 1 hacia atrás
			//analizador.retroceder();
			//analizador.devolverToken();
	    }
	}

	static class ASE4 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Faltó el punto para el elevado, HACE AS1
	        new AS1().ejecutar(analizador);
	    }
	}

	static class ASE5 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // No es un dígito que entre en los octales (1-7), HACE AS1
	        new AS1().ejecutar(analizador);
	    }
	}

	static class ASE6 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Faltó una 'd' del elevado, hace AS1
	        new AS1().ejecutar(analizador);
	    }
	}

	static class ASE7 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Sobra un '.', hace AS1
	        new AS1().ejecutar(analizador);
	    }
	}

	static class ASE8 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Falta dígito del exponente, termina dando error y retrocede uno
			//analizador.retroceder();
	        // Manejar error
	    }
	}

	static class ASF3 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Es lo de if palabra clave busca en tabla, etc.
	      //  if (analizador.esPalabraClave(analizador.getToken())) {
	      //      analizador.devolverToken();
	       // }
	    }
	}

	static class ASF4 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Es lo de if palabra clave busca en tabla, etc., pero retrocede uno
	       // analizador.retroceder();
	        //if (analizador.esPalabraClave(analizador.getToken())) {
	        //    analizador.devolverToken();
	       // }
	    }
	}

	static class ASF5 extends AccionSemantica {
		public void ejecutar(AnalizadorLexico analizador) {
	        // Es ASF3 pero sin chequear palabra clave y tiene un tipo entero
	       // analizador.devolverToken();
	        // No se chequea palabra clave
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

