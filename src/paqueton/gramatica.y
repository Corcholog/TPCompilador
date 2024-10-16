%{
	package paqueton;
	import java.io.*;
	import java.util.ArrayList;
	import java.util.Stack;
%}

%token ID CTE MASI MENOSI ASIGN DIST GOTO UP DOWN TRIPLE FOR ULONGINT DOUBLE IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CADMUL TAG
%%
prog		: ID cuerpo { estructurasSintacticas("Se declaró el programa: " + $1.sval);}

		| cuerpo_error { ErrorHandler.addErrorSintactico("Falta el nombre del programa", lex.getLineaInicial());}
		;

cuerpo_error	: BEGIN sentencias ';' END
	
		| BEGIN sentencia END { ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
		| BEGIN sentencias ';' { ErrorHandler.addErrorSintactico("Falta delimitador del programa", lex.getLineaInicial());}
		;

cuerpo		: BEGIN sentencias ';' END

		
		| BEGIN sentencia END { ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
		| BEGIN sentencias ';' { ErrorHandler.addErrorSintactico("Falta delimitador END del programa", lex.getLineaInicial());}
		| sentencias ';' END { ErrorHandler.addErrorSintactico("Falta delimitador BEGIN del programa", lex.getLineaInicial());}
		| sentencias ';' { ErrorHandler.addErrorSintactico("Falta delimitador BEGIN y END del programa", lex.getLineaInicial());}
		;

sentencias : sentencias ';' sentencia
		| sentencia
		| error ';' { ErrorHandler.addErrorSintactico("Sentencia inválida.", lex.getLineaInicial());}
		
		| sentencias sentencia {ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}	
		;

		
sentencia       : sentec_declar
		| sentec_eject
		;
		
sentec_declar	: declaracion_var
		| declaracion_fun  {	estructurasSintacticas("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
		| TAG {	if(esEmbebido($1.sval)){
			ErrorHandler.addErrorSemantico("No se puede declarar una etiqueta que tenga tipos embebidos", lex.getLineaInicial());
			} else {
				estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial());
			}
		}
		| declar_tipo_trip
		;
		
sentec_eject	: asignacion 
		| invoc_fun 
		| seleccion 
		| sald_mensaj  {estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
		| for 
		| goto  {estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
		
		;

condicion	: '(' condicion_2 ')' { $$.sval = $2.sval;}

		| condicion_2 { ErrorHandler.addErrorSintactico("Falta de paréntesis en la condición", lex.getLineaInicial());}
		| '(' condicion_2 { ErrorHandler.addErrorSintactico("Falta de paréntesis derecho en la condición", lex.getLineaInicial());}
		|  condicion_2 ')' { ErrorHandler.addErrorSintactico("Falta de paréntesis izquierdo en la condición", lex.getLineaInicial());}
		;
		
condicion_2 	: expresion_matematica comparador expresion_matematica { $$.sval = gc.addTerceto($2.sval, $1.sval, $3.sval);}
		| '(' patron ')' comparador '(' patron ')' { $$.sval = gc.addTerceto($4.sval, "{" + $2.sval + "}", "{" + $6.sval + "}");}

		| '(' patron  comparador  patron ')' { ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
		| '(' patron ')' comparador  patron ')' { ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
		| '(' patron  comparador '(' patron ')' { ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
		| '(' patron ')' error '(' patron ')' { ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
		| expresion_matematica error expresion_matematica { ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
		;

patron		: lista_patron ',' expresion_matematica { $$.sval = $1.sval + "," + $3.sval; }
		;

lista_patron    : lista_patron ',' expresion_matematica { $$.sval = $1.sval + "," + $3.sval; }
		| expresion_matematica {$$.sval = $1.sval;}
		;
		
seleccion 	: IF condicion_punto_control THEN cuerpo_control sinelse_punto_control {estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
        	| IF condicion_punto_control THEN cuerpo_control else_punto_control cuerpo_control endif_punto_control
		// ANDA, PERO VAN A FUNCIONAR MAL LOS TERCETOS. HAY Q CONTROLAR O ALGO

		| IF condicion_punto_control THEN cuerpo_control { ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
		| IF condicion_punto_control THEN else_punto_control { ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
		| IF condicion_punto_control THEN sinelse_punto_control { ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
		| IF condicion_punto_control THEN cuerpo_control else_punto_control endif_punto_control { ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
		| IF condicion_punto_control THEN else_punto_control endif_punto_control { ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
		;
 

		
sinelse_punto_control : END_IF {
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			}
			;

condicion_punto_control : condicion {
			gc.addTerceto("BF", $1.sval, ""); 
			gc.push(gc.getPosActual());
		}
		;

else_punto_control : ELSE { 
			gc.addTerceto("BI", "", "-"); 
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop(); 
			gc.push(gc.getPosActual());
		}
		;
endif_punto_control : END_IF {
			gc.actualizarBI(gc.getCantTercetos()); 
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
		;
comparador	: MASI
		| MENOSI
		| DIST
		| '=' {$$.sval = "=";}
		| '<' {$$.sval = "<";}
		| '>' {$$.sval = ">";}
		;
		
cuerpo_control	: BEGIN multip_cuerp_fun ';' END
		| sentec_eject ';'
			
		| BEGIN ';' END { ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
		| BEGIN END { ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
		| BEGIN multip_cuerp_fun END { ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
		;

cuerpo_iteracion: BEGIN multip_cuerp_fun ';' END
		| sentec_eject ';'
			
		| BEGIN ';' END { ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
		| BEGIN END { ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
		| BEGIN multip_cuerp_fun END { ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
		;
		
multip_cuerp_fun: multip_cuerp_fun ';' sentec_eject
		| sentec_eject
		
		| multip_cuerp_fun sentec_eject { ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
		;
		
		
declaracion_var : tipo lista_variables {estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
		
		| ID '>' lista_variables {estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());} // preguntar
		| embed {estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
		;


embed           : lista_variables_e
		;

lista_variables_e : lista_variables_e ',' ID {if(!esEmbebido($3.sval)){ErrorHandler.addErrorSemantico("No se puede omitir el tipo en declaracion de tipos no-embebidos.", lex.getLineaInicial());};}
		| ID {if(!esEmbebido($1.sval)){ErrorHandler.addErrorSemantico("No se puede omitir el tipo en declaracion de tipos no-embebidos.", lex.getLineaInicial());};}
		;


lista_variables : lista_variables ',' ID {checkRedeclaracion($3.sval);}
		| ID {checkRedeclaracion($1.sval);}
		| lista_variables error ID { ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());} // No funciona para dejarlo vacío pero si para cuando el usuario pone un caracter inesperado
		;


tipo		: DOUBLE
		| ULONGINT 
		;

asignacion 	: triple ASIGN expresion {estructurasSintacticas("Se realizó una asignación a la variable: " + $1.sval + " en la linea: " + lex.getLineaInicial());
						$$.sval = gc.addTerceto(":=", $1.sval, $3.sval);
					}
		| ID ASIGN expresion {estructurasSintacticas("Se realizó una asignación a la variable: " + $1.sval + " en la linea: " + lex.getLineaInicial());
					$$.sval = gc.addTerceto(":=", $1.sval, $3.sval);
		}
			
	 	;

expresion : expresion_matematica
	  | condicion_2
	  ;

expresion_matematica 	: expresion_matematica '+' termino {$$.sval = gc.addTerceto("+", $1.sval, $3.sval);}
		| expresion_matematica '-' termino {$$.sval = gc.addTerceto("-", $1.sval, $3.sval);}
		| termino 

		
		//| expresion_matematica termino { ErrorHandler.addErrorSintactico("Falta operador en la expresión matematica", lex.getLineaInicial());}
		| '+' termino { ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
		| expresion_matematica '+' error ')'{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");} 
		| expresion_matematica '+' error ';' { ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
		| expresion_matematica '-' error ')'{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");} 
		| expresion_matematica '-' error ';' { ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
		//| '-' termino
		;


termino 	: termino '*' factor {$$.sval = gc.addTerceto("*", $1.sval, $3.sval);}
		| termino '/' factor {$$.sval = gc.addTerceto("/", $1.sval, $3.sval);}
		| factor
		
		//| termino error factor { ErrorHandler.addErrorSintactico("Falta operador en el término", lex.getLineaInicial());}
		| '*' factor { ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
		| '/' factor { ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
		| termino '/' error ')'{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
		| termino '*' error ')'{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
		| termino '*' error ';'{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
		| termino '/' error ';'{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
		;

factor		: ID
		| constante
		| invoc_fun
		| triple
		;

constante 	: CTE 		
		| '-' CTE   {	if (ts.esUlongInt($2.sval)){
					ErrorHandler.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos", lex.getLineaInicial());
				}
				else {
					ts.convertirNegativo($2.sval);
				}
			}
		;

triple		: ID '{' expresion_matematica '}' {$$.sval = gc.addTerceto("ACCESOTRIPLE", $1.sval, $3.sval);}
		;

declaracion_fun : tipo_fun FUN ID '(' lista_parametro ')' { this.cantRetornos.add(0); this.gc_funciones.push(this.ts.getGCFuncion($3.sval)); this.gc = this.gc_funciones.peek();} cuerpo_funcion_p {this.checkRet($3.sval);
								this.gc_funciones.pop();
								this.gc = this.gc_funciones.peek();
								if (esEmbebido($3.sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}										;
							}
		| tipo_fun FUN '(' lista_parametro ')'{ this.cantRetornos.add(0);} cuerpo_funcion_p { ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
		| tipo_fun FUN ID '(' ')' { this.cantRetornos.add(0);} cuerpo_funcion_p { ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet($3.sval);
							    if (esEmbebido($3.sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
							   }
		;

tipo_fun 	: tipo
		| ID
		;

lista_parametro : lista_parametro ',' parametro { ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
		| parametro 

		;

parametro	: tipo ID {estructurasSintacticas("Se declaró el parámetro: " + $2.sval + " en la linea: " + lex.getLineaInicial());}
		| ID ID {estructurasSintacticas("Se declaró el parámetro: " + $2.sval + " en la linea: " + lex.getLineaInicial());}

		| tipo { ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
		| ID { ErrorHandler.addErrorSintactico("Falta el nombre del parametro o el tipo", lex.getLineaInicial());} //buscando en tabla de simbolos se puede saber lo que falta
		;

cuerpo_funcion_p : {ts.addClave(yylval.sval);} BEGIN bloques_funcion ';' END

		 // No pudimos hacer que ese punto y coma falte, lo cual obliga a funciones anidadas llevar un ; luego del END
    		 ;

bloques_funcion : bloques_funcion';' bloque_funcion
    		| bloque_funcion 
    
    		| bloques_funcion bloque_funcion {ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
    		;
    
bloque_funcion : retorno
		| sentencia
		;

retorno 	: RET '('expresion')' { this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					$$.sval = gc.addTerceto("RET", $3.sval, "");		
		}
		;

invoc_fun	: ID '(' lista_parametro_real ')' {estructurasSintacticas("Se invocó a la función: " + $1.sval + " en la linea: " + lex.getLineaInicial());
							$$.sval = gc.addTerceto("INVOC_FUN", $1.sval, $3.sval);
		}
		
		| ID '(' ')' { ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}

		;

lista_parametro_real : lista_parametro_real ',' param_real { ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
		| param_real { $$.sval = $1.sval;}
		;

param_real	: tipo expresion_matematica {$$.sval = gc.addTerceto("TO".concat($1.sval), $2.sval, "");}
		| expresion {$$.sval = $1.sval;}

		// genera 4 shift reduce en -CTE (conversión explícita como tripla (tipos definidos por el usuario))
		//| ID expresion_matematica
		;

sald_mensaj	: OUTF '(' mensaje ')' {$$.sval = gc.addTerceto("OUTF", $3.sval, "");}

		| OUTF '(' ')' { ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
		| OUTF '(' error ')' { ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
		;

mensaje		: expresion
		| CADMUL
		;

for		: FOR '(' asignacion_for ';' condicion_for ';' foravanc CTE ')' cuerpo_iteracion {	estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());
													String var = this.varFors.get(this.varFors.size()-1);
													gc.addTerceto("+", var, String.valueOf($7.ival * Integer.parseInt($8.sval)));
													this.varFors.remove(this.varFors.size()-1);
													gc.addTerceto("BI", $5.sval, "");
													gc.actualizarBF(gc.getCantTercetos());
													gc.pop();
		}

		| FOR '(' asignacion_for ';' condicion_for  foravanc CTE ')' cuerpo_iteracion { ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
		| FOR '(' asignacion_for  condicion_for ';' foravanc CTE ')' cuerpo_iteracion { ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
		| FOR '(' asignacion_for  condicion_for foravanc CTE ')' cuerpo_iteracion { ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
		| FOR '(' asignacion_for ';' condicion_for ';' CTE ')' cuerpo_iteracion { ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
		| FOR '(' asignacion_for ';' condicion_for ';' foravanc ')' cuerpo_iteracion {ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
		| FOR '(' asignacion_for ';' condicion_for  CTE ')' cuerpo_iteracion { ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
		| FOR '(' asignacion_for ';' condicion_for  foravanc ')' cuerpo_iteracion { ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
		| FOR '(' asignacion_for ';' condicion_for ';'  ')' cuerpo_iteracion { { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
		;


condicion_for   : condicion { $$.sval = $1.sval;
				gc.addTerceto("BF", $1.sval, "");
				gc.push(gc.getPosActual());
			}
		;

asignacion_for  : ID ASIGN CTE { gc.addTerceto(":=", $1.sval, $3.sval);
				this.varFors.add($1.sval);
				}
		;

foravanc	: UP {$$.ival = 1;}
		| DOWN {$$.ival = -1;}
		;

goto		: GOTO TAG { $$.sval = gc.addTerceto("GOTO", $2.sval,"");} // luego se deberá setear a donde salta

		| GOTO error ';' {ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
		;



declar_tipo_trip: TYPEDEF TRIPLE '<' tipo '>' ID {estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + $6.sval + " en la linea:" + lex.getLineaInicial());}
		
		| TYPEDEF TRIPLE  tipo '>' ID {ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
		| TYPEDEF TRIPLE '<' tipo  ID {ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
		| TYPEDEF TRIPLE  tipo  ID {ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
		| TYPEDEF '<' tipo '>' ID { ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
		| TYPEDEF TRIPLE '<' tipo '>' error ';' { ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
		;
%%
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
GeneradorCodigo gc;
Stack<GeneradorCodigo> gc_funciones;
String tipoVar;
ArrayList<Integer> cantRetornos;
String estructuras;
ArrayList<String> varFors;
String ambitoActual;
public Parser(String nombreArchivo, TablaSimbolos t, GeneradorCodigo gc)
{
	this.nombreArchivo=nombreArchivo;
	this.ambitoActual = "";
	this.ts=t;
	this.gc = gc;
	this.gc_funciones = new Stack<GeneradorCodigo>();
	this.gc_funciones.push(gc);
	this.cantRetornos = new ArrayList<>();
	this.estructuras = "Estructuras sintacticas detectadas en el codigo fuente :  \n";
	this.lex= new AnalizadorLexico(nombreArchivo, t, this);
	this.varFors = new ArrayList<>();
}
String yyerror(String a) {
	return a;
}
String errores() {
	return ErrorHandler.getErrores();
}
int yylex() {
	return lex.yylex();
}

boolean esEmbebido(String sval){
	char firstC=sval.charAt(0);
	if ( firstC =='x' || firstC =='y' || firstC =='z' || firstC == 'd' ) {
		return true;
	}
	return false;
}

boolean varRedeclarada(){
	char firstC=yylval.sval.charAt(0);
	if ( (tipoVar.equals(AccionSemantica.ULONGINT) && (firstC =='x' || firstC =='y' || firstC =='z')) || (tipoVar.equals(AccionSemantica.DOUBLE) && (firstC == 'd')) ) {
		return true;
	}
	return false;
}

boolean redeclaracionTipoErroneo(){
	char firstC=yylval.sval.charAt(0);
	if ( (tipoVar.equals(AccionSemantica.DOUBLE) && (firstC =='x' || firstC =='y' || firstC =='z')) || (tipoVar.equals(AccionSemantica.ULONGINT) && (firstC == 'd')) ) {
		return true;
	}
	return false;
}

void checkRedeclaracion(String val){
	if (varRedeclarada()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable", lex.getLineaInicial());
	}
	else if (redeclaracionTipoErroneo()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable y con tipos erroneos", lex.getLineaInicial());
	}
	else {
		ts.addClave(val);
		ts.addAtributo(val,AccionSemantica.TIPO,tipoVar);lex.getLineaInicial();
	}
}

void checkRedeclaracionFuncion(){
	if (varRedeclarada()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la funcion", lex.getLineaInicial());
	}
	else if (redeclaracionTipoErroneo()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la funcion y con tipos erroneos", lex.getLineaInicial());
	}
}

void estructurasSintacticas(String estructura){
	estructuras += estructura + "\n";
}

void checkRet(String nombreFuncion) {
	if (!nombreFuncion.isEmpty()) {
		if (this.cantRetornos.get(this.cantRetornos.size()-1) > 0){
			estructurasSintacticas("Se declaró la función: " + nombreFuncion);
			ts.addClave(nombreFuncion);
			ts.addAtributo(nombreFuncion,AccionSemantica.TIPO,AccionSemantica.FUNCION);
			ts.addAtributo(nombreFuncion,AccionSemantica.TIPORETORNO,tipoVar);
		} else {
			ErrorHandler.addErrorSintactico("Falta el retorno de la función: " + nombreFuncion, lex.getLineaInicial());
		}
		
	} else {
		if (this.cantRetornos.get(this.cantRetornos.size()-1) == 0){
			ErrorHandler.addErrorSintactico("Falta el retorno de la función", lex.getLineaInicial());
		}
	}
	this.cantRetornos.remove(this.cantRetornos.size()-1);
}

public static String getNombreVariable(int numero) {
    switch (numero) {
        case YYERRCODE: return "YYERRCODE";
        case ID: return "ID";
        case CTE: return "CTE";
        case MASI: return ">=";
        case MENOSI: return "<=";
        case ASIGN: return ":=";
        case DIST: return "!=";
        case GOTO: return "GOTO";
        case UP: return "UP";
        case DOWN: return "DOWN";
        case TRIPLE: return "TRIPLE";
        case FOR: return "FOR";
        case ULONGINT: return "ULONGINT";
        case DOUBLE: return "DOUBLE";
        case IF: return "IF";
        case THEN: return "THEN";
        case ELSE: return "ELSE";
        case BEGIN: return "BEGIN";
        case END: return "END";
        case END_IF: return "END_IF";
        case OUTF: return "OUTF";
        case TYPEDEF: return "TYPEDEF";
        case FUN: return "FUN";
        case RET: return "RET";
        case CADMUL: return "CADMUL";
        case TAG: return "TAG";
        default:
            // Si el número está en el rango ASCII (0-255), convierte a carácter
            if (numero >= 0 && numero <= 255) {
            	char num = (char) numero;
                return String.valueOf(num);
            } else {
                return null;
            }
    }
}

public static void main(String[] args) {
    // Verificamos que el nombre de "prueba" sea pasado como argumento
/*
    if (args.length < 1) {
        System.out.println("Por favor, proporciona el nombre de la prueba como argumento.");
        return;
    }

    // Tomamos el primer argumento como el valor de prueba
    String prueba = args[0];*/
    String prueba = "pruebaCodigoSemantica";
    TablaSimbolos tb = new TablaSimbolos();
    GeneradorCodigo gc = new GeneradorCodigo();
    
    Parser p = new Parser(prueba, tb, gc);
    ErrorHandler.setGeneradorCodigo(p.gc);
    
    int valido = p.yyparse();
    
    System.out.println(p.lex.getListaTokens());
    System.out.println("\n" + p.estructuras);	
    System.out.println("Errores y Warnings detectados del codigo fuente :  \n" + p.errores());
    System.out.println("Contenido de la tabla de simbolos:  \n" + tb);
    System.out.println("Codigo intermedio en tercetos: " + gc);
    
    if (valido == 0) {
        System.out.println("Se analizo todo el codigo fuente");
    } else {
        System.out.println("No se analizo completamente el codigo fuente, debido a uno o mas errores inesperados");
    }
}