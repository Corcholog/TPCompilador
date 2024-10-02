%{
	package paqueton;
	import java.io.*;
	import java.util.ArrayList;
%}

%token ID CTE MASI MENOSI ASIGN DIST GOTO UP DOWN TRIPLE FOR ULONGINT DOUBLE IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CADMUL TAG
%%
prog		: ID cuerpo { estructurasSintacticas("Se declaró el programa: " + $1.sval);}

		| cuerpo_error { lex.addErrorSintactico("Falta el nombre del programa");}
		;

cuerpo_error	: BEGIN sentencias ';' END
	
		| BEGIN sentencia END { lex.addErrorSintactico("Falta punto y coma");}
		| BEGIN sentencias ';' { lex.addErrorSintactico("Falta delimitador del programa");}
		;

cuerpo		: BEGIN sentencias ';' END

		
		| BEGIN sentencia END { lex.addErrorSintactico("Falta punto y coma");}
		| BEGIN sentencias ';' { lex.addErrorSintactico("Falta delimitador END del programa");}
		| sentencias ';' END { lex.addErrorSintactico("Falta delimitador BEGIN del programa");}
		| sentencias ';' { lex.addErrorSintactico("Falta delimitador BEGIN y END del programa");}
		;

sentencias : sentencias ';' sentencia
		| sentencia
		
		| sentencias sentencia {lex.addErrorSintactico("Falta punto y coma");}	
		;

		
sentencia       : sentec_declar
		| sentec_eject
		;
		
sentec_declar	: declaracion_var
		| declaracion_fun  {	estructurasSintacticas("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
		| TAG {	estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial()); }
		| declar_tipo_trip
		;
		
sentec_eject	: asignacion 
		| invoc_fun 
		| seleccion 
		| sald_mensaj  {estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
		| for 
		| goto  {estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
		
		;

condicion	: '(' condicion_2 ')'

		| condicion_2 { lex.addErrorSintactico("Falta de paréntesis en la condición");}
		| '(' condicion_2 { lex.addErrorSintactico("Falta de paréntesis derecho en la condición");}
		|  condicion_2 ')' { lex.addErrorSintactico("Falta de paréntesis izquierdo en la condición");}
		;
		
condicion_2 	: expresion_matematica comparador expresion_matematica
		| '(' patron ')' comparador '(' patron ')'
		| '(' patron  comparador  patron ')' { lex.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda");}
		| '(' patron ')' comparador  patron ')' { lex.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón");}
		| '(' patron  comparador '(' patron ')' { lex.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón");}

		| '(' patron ')' error '(' patron ')' { lex.addErrorSintactico("Falta comparador entre los patrones");}
		| expresion_matematica error expresion_matematica { lex.addErrorSintactico("Falta comparador entre las expresiones");}
		;

patron		: lista_patron ',' expresion_matematica
		;

lista_patron    : lista_patron ',' expresion_matematica 
		| expresion_matematica
	
		// ESTE GENERA 3 SHIFT REDUCE Y FUNCIONA EL - CTE
		//| lista_patron expresion_matematica { lex.addErrorSintactico("Falta coma en la lista de variables del pattern matching");}
		;
		
seleccion 	: IF condicion THEN cuerpo_control END_IF {estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
        	| IF condicion THEN cuerpo_control ELSE cuerpo_control END_IF {estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());}

		| IF condicion THEN cuerpo_control ELSE cuerpo_control { lex.addErrorSintactico("Falta END_IF con ELSE");}
		| IF condicion THEN cuerpo_control { lex.addErrorSintactico("Falta END_IF");}
		| IF condicion THEN END_IF{ lex.addErrorSintactico("Falta el cuerpo de control del then");}
		| IF condicion THEN cuerpo_control ELSE END_IF{ lex.addErrorSintactico("Falta el cuerpo de control del ELSE");}
		| IF condicion THEN ELSE END_IF{ lex.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE");}
		;
		
comparador	: MASI
		| MENOSI
		| DIST
		| '='	
		| '<'
		| '>'
		;
		
cuerpo_control	: BEGIN multip_cuerp_fun ';' END
		| sentec_eject ';'
			
		| BEGIN ';' END { lex.addErrorSintactico("Falta el cuerpo del control");}
		| BEGIN END { lex.addErrorSintactico("Falta el cuerpo del control");}
		| BEGIN multip_cuerp_fun END { lex.addErrorSintactico("Falta punto y coma");}
		;

cuerpo_iteracion: BEGIN multip_cuerp_fun ';' END
		| sentec_eject ';'
			
		| BEGIN ';' END { lex.addErrorSintactico("Falta el cuerpo de la iteracion");}
		| BEGIN END { lex.addErrorSintactico("Falta el cuerpo de la iteracion");}
		| BEGIN multip_cuerp_fun END { lex.addErrorSintactico("Falta punto y coma");}
		;
		
multip_cuerp_fun: multip_cuerp_fun ';' sentec_eject
		| sentec_eject
		
		| multip_cuerp_fun sentec_eject { lex.addErrorSintactico("Falta punto y coma");}
		;
		
		
declaracion_var : tipo lista_variables {estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
		// esta declaracion de tripla rompe con delimitador del cuerpo del programa si quitas BEGIN
		| ID lista_variables {estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
		;

lista_variables : lista_variables ',' ID {      
						if (matcheanTipos()){
							ts.addClave($3.sval);
							ts.addAtributo($3.sval,AccionSemantica.TIPO,tipoVar);
						}
						else {
							lex.addErrorSintactico("se declaro la variable "+ $3.sval + " que difiere del tipo declarado: " + tipoVar);
						}
					}
		| ID {  
			if (matcheanTipos()){
				ts.addClave($1.sval);
				ts.addAtributo($1.sval,AccionSemantica.TIPO,tipoVar);
		      } 
		      else {
		      	lex.addErrorSintactico("se declaro la variable "+ $1.sval + " que difiere del tipo declarado: " + tipoVar);
		      }
		}
		| lista_variables error ID { lex.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto");} // No funciona para dejarlo vacío pero si para cuando el usuario pone un caracter inesperado
		;


tipo		: DOUBLE
		| ULONGINT 
		;

asignacion 	: triple ASIGN expresion {estructurasSintacticas("Se realizó una asignación a la variable: " + $1.sval + " en la linea: " + lex.getLineaInicial());}
		| ID ASIGN expresion {estructurasSintacticas("Se realizó una asignación a la variable: " + $1.sval + " en la linea: " + lex.getLineaInicial());}

	 	;

expresion : expresion_matematica
	  | condicion_2
	  ;

expresion_matematica 	: expresion_matematica '+' termino
		| expresion_matematica '-' termino
		| termino

		
		//| expresion_matematica termino { lex.addErrorSintactico("Falta operador en la expresión matematica");}
		| '+' termino { lex.addErrorSintactico("Falta operando izquierdo");}
		| expresion_matematica '+' error ')'{ lex.addErrorSintactico("Falta operando derecho");} 
		| expresion_matematica '+' error ';' { lex.addErrorSintactico("Falta operando derecho");}
		| expresion_matematica '-' error ')'{ lex.addErrorSintactico("Falta operando derecho");} 
		| expresion_matematica '-' error ';' { lex.addErrorSintactico("Falta operando derecho");}
		//| '-' termino
		;


termino 	: termino '*' factor
		| termino '/' factor
		| factor
		
		//| termino error factor { lex.addErrorSintactico("Falta operador en el término");}
		| '*' factor { lex.addErrorSintactico("Falta operando izquierdo");}
		| '/' factor { lex.addErrorSintactico("Falta operando izquierdo");}
		| termino '/' error ')'{ lex.addErrorSintactico("Falta operando derecho");}
		| termino '*' error ')'{ lex.addErrorSintactico("Falta operando derecho");}
		| termino '*' error ';'{ lex.addErrorSintactico("Falta operando derecho");}
		| termino '/' error ';'{ lex.addErrorSintactico("Falta operando derecho");}
		;

factor		: ID
		| constante
		| invoc_fun
		| triple
		;

constante 	: CTE 		
		| '-' CTE   {	System.out.println("EEEEEEEEEEEEEEEEEEEEE" + "\n" + lex.getLineaInicial());
				if (ts.esUlongInt($2.sval)){
					lex.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos");
				}
				else {
					ts.convertirNegativo($2.sval);
				}
			}
		;

triple		: ID '{' expresion_matematica '}'
		;

declaracion_fun : tipo_fun FUN ID '(' lista_parametro ')' { this.cantRetornos.add(0); } cuerpo_funcion_p {
										if (this.cantRetornos.get(this.cantRetornos.size()-1) > 0){
											estructurasSintacticas("Se declaró la función: " + $3.sval);
											ts.addClave($3.sval);
											ts.addAtributo($3.sval,AccionSemantica.TIPO,AccionSemantica.FUNCION);
											ts.addAtributo($3.sval,AccionSemantica.TIPORETORNO,tipoVar);
										} else {
											lex.addErrorSintactico("Falta el retorno de la función: " + $3.sval);
										}
										this.cantRetornos.remove(this.cantRetornos.size()-1);
									}
		| tipo_fun FUN '(' lista_parametro ')' cuerpo_funcion_p { lex.addErrorSintactico("Falta nombre de la funcion declarada");}
		| tipo_fun FUN ID '(' ')' cuerpo_funcion_p { lex.addErrorSintactico("Falta el parametro en la declaracion de la funcion");}
		;

tipo_fun 	: tipo
		| ID
		;

lista_parametro : lista_parametro ',' parametro { lex.addErrorSintactico("Se declaró más de un parametro");}
		| parametro 

		;

parametro	: tipo ID {estructurasSintacticas("Se declaró el parámetro: " + $2.sval + " en la linea: " + lex.getLineaInicial());}
		| ID ID {estructurasSintacticas("Se declaró el parámetro: " + $2.sval + " en la linea: " + lex.getLineaInicial());}

		| tipo { lex.addErrorSintactico("Falta el nombre del parametro");}
		| ID { lex.addErrorSintactico("Falta el nombre del parametro o el tipo");} //buscando en tabla de simbolos se puede saber lo que falta
		;

cuerpo_funcion_p : {ts.addClave(yylval.sval);} BEGIN bloques_funcion ';' END

		 // No pudimos hacer que ese punto y coma falte, lo cual obliga a funciones anidadas llevar un ; luego del END
    		 ;

bloques_funcion : bloques_funcion';' bloque_funcion
    		| bloque_funcion 
    
    		| bloques_funcion bloque_funcion {lex.addErrorSintactico("Falta punto y coma");}
    		;
    
bloque_funcion : retorno
		| sentencia
		;

retorno 	: RET '('expresion')' { this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); }
		;

invoc_fun	: ID '(' lista_parametro_real ')' {estructurasSintacticas("Se invocó a la función: " + $1.sval + " en la linea: " + lex.getLineaInicial());}
		
		| ID '(' ')' { lex.addErrorSintactico("Falta de parámetros en la invocación a la función");}

		;

lista_parametro_real : lista_parametro_real ',' param_real { lex.addErrorSintactico("Se utilizó más de un parámetro para invocar ala función");}
		| param_real
		;

param_real	: tipo expresion_matematica
		| expresion

		// genera 4 shift reduce en -CTE (conversión explícita como tripla (tipos definidos por el usuario))
		//| ID expresion_matematica
		;

sald_mensaj	: OUTF '(' mensaje ')'

		| OUTF '(' ')' { lex.addErrorSintactico("Falta el mensaje del OUTF");}
		| OUTF '(' error ')' { lex.addErrorSintactico("Parámetro invalido del OUTF");}
		;

mensaje		: expresion
		| CADMUL
		;

for		: FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_iteracion {estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());}
		
		| FOR '(' ID ASIGN CTE ';' condicion  foravanc CTE ')' cuerpo_iteracion { lex.addErrorSintactico("Falta punto y coma entre condicion y avance");}
		| FOR '(' ID ASIGN CTE  condicion ';' foravanc CTE ')' cuerpo_iteracion { lex.addErrorSintactico("Falta punto y coma entre asignacion y condicion");}
		| FOR '(' ID ASIGN CTE  condicion foravanc CTE ')' cuerpo_iteracion { lex.addErrorSintactico("Faltan todos los punto y coma del for");}
		| FOR '(' ID ASIGN CTE ';' condicion ';' CTE ')' cuerpo_iteracion { lex.addErrorSintactico("Falta UP/DOWN");}
		| FOR '(' ID ASIGN CTE ';' condicion ';' foravanc ')' cuerpo_iteracion {lex.addErrorSintactico("Falta valor del UP/DOWN");}
		| FOR '(' ID ASIGN CTE ';' condicion  CTE ')' cuerpo_iteracion { lex.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance");}
		| FOR '(' ID ASIGN CTE ';' condicion  foravanc ')' cuerpo_iteracion { lex.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance");}
		| FOR '(' ID ASIGN CTE ';' condicion ';'  ')' cuerpo_iteracion { { lex.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance");}}
		;

foravanc	: UP
		| DOWN
		;

goto		: GOTO TAG

		| GOTO error ';' {lex.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.");}
		;



declar_tipo_trip: TYPEDEF TRIPLE '<' tipo '>' ID {System.out.println("Se declaró un tipo TRIPLE con el ID: " + $6.sval + " en la linea:" + lex.getLineaInicial());}
		
		| TYPEDEF TRIPLE  tipo '>' ID {lex.addErrorSintactico("falta < en la declaración del TRIPLE"); }
		| TYPEDEF TRIPLE '<' tipo  ID {lex.addErrorSintactico("falta > en la declaración del TRIPLE"); }
		| TYPEDEF TRIPLE  tipo  ID {lex.addErrorSintactico("falta > y < en la declaración del TRIPLE"); }
		| TYPEDEF '<' tipo '>' ID { lex.addErrorSintactico("Falta la palabra clave TRIPLE");}
		| TYPEDEF TRIPLE '<' tipo '>' error ';' { lex.addErrorSintactico("Falta el ID de la tripla definida.");}
		;
%%
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
String tipoVar;
ArrayList<Integer> cantRetornos;
String estructuras;
public Parser(String nombreArchivo, TablaSimbolos t)
{
	this.nombreArchivo=nombreArchivo;
	this.ts=t;
	this.cantRetornos = new ArrayList<>();
	this.estructuras = "Estructuras sintacticas detectadas en el codigo fuente :  \n";
	this.lex= new AnalizadorLexico(nombreArchivo, t, this);
}

String yyerror(String a) {
	return a;
}
String errores() {
	return lex.getErrores();
}
int yylex() {
	return lex.yylex();
}

boolean matcheanTipos(){
	char firstC=yylval.sval.charAt(0);
	if ( (tipoVar.equals(AccionSemantica.DOUBLE) && (firstC =='x' || firstC =='y' || firstC =='z')) || (tipoVar.equals(AccionSemantica.ULONGINT) && (firstC == 'd')) ) {
		return false;
	}
	return true;
}

void estructurasSintacticas(String estructura){
	estructuras += estructura + "\n";
}

public static void main(String[] args) {
	String prueba= "programaAreconocer";
	TablaSimbolos tb= new TablaSimbolos();
	Parser p = new Parser(prueba,tb);
	int valido = p.yyparse();
	System.out.println(p.lex.getListaTokens());
	System.out.println("\n" + p.estructuras);	
	System.out.println("Errores y Warnings detectados del codigo fuente :  \n" + p.errores());
	System.out.println("Contenido de la tabla de simbolos:  \n" + tb);
	if (valido == 0) {
		System.out.println("Se analizo todo el codigo fuente");
	}
	else {
		System.out.println("No se analizo completamente el codigo fuente , debido a uno o mas errores inesperados");
	}
	
}
