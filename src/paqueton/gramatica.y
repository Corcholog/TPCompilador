%{
	package paqueton;
	import java.io.*;
%}

%token ID CTE MASI MENOSI ASIGN DIST GOTO UP DOWN TRIPLE FOR ULONGINT DOUBLE IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CADMUL TAG
%%
prog		: ID cuerpo { estructurasSintacticas("Se declaró el programa: " + $1.sval);}

		| cuerpo { lex.addErrorSintactico("Falta el nombre del programa");}
		;

cuerpo		: BEGIN sentencias ';' END
		| BEGIN sentencias ';' { lex.addErrorSintactico("Falta delimitador del programa");}
		//| sentencias ';' END {}
		//| sentencias ';' {}
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
		| patron comparador patron
		| patron patron

		//| expresion_matematica expresion_matematica
		;

patron		: '(' lista_patron ')'

		//|  lista_patron ')' {}
		//|  '(' lista_patron {}
		//|  lista_patron {}
		;

lista_patron    : lista_patron ',' expresion_matematica
		| expresion_matematica

		| lista_patron expresion_matematica { lex.addErrorSintactico("Falta coma en la lista de variables del pattern matching");}
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
			
		| BEGIN ';' END { lex.addErrorSintactico("Falta el cuerpo del control")}
		| BEGIN END { lex.addErrorSintactico("Falta el cuerpo del control")}
		| BEGIN multip_cuerp_fun END { lex.addErrorSintactico("Falta punto y coma");}
		;

cuerpo_iteracion: BEGIN multip_cuerp_fun ';' END
		| sentec_eject ';'
			
		| BEGIN ';' END { lex.addErrorSintactico("Falta el cuerpo de la iteracion")}
		| BEGIN END { lex.addErrorSintactico("Falta el cuerpo de la iteracion")}
		| BEGIN multip_cuerp_fun END { lex.addErrorSintactico("Falta punto y coma");}
		;
		
multip_cuerp_fun: multip_cuerp_fun ';' sentec_eject
		| sentec_eject
		
		| multip_cuerp_fun sentec_eject { lex.addErrorSintactico("Falta punto y coma");}
		;
		
		
declaracion_var : tipo lista_variables {estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
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
		//| lista_variables ID { lex.AddErrorSintactico("Falta coma en la lista de variables");}
		;

tipo		: tipo_basico	
		;

tipo_basico	: DOUBLE
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

		// falta de operadores
		//| expresion_matematica termino { lex.addErrorSintactico("Falta operando en la expresión matematica");}
		;


termino 	: termino '*' factor
		| termino '/' factor
		| factor
		
		//| termino factor { lex.addErrorSintactico("Falta operando en el término");}
		//| '*' factor { lex.addErrorSintactico("Falta operador izquierdo");}
		//| '/' factor { lex.addErrorSintactico("Falta operador izquierdo");}
		//| termino '*' { lex.addErrorSintactico("Falta operador derecho");}
		//| termino '/' { lex.addErrorSintactico("Falta operador derecho");}
		;

factor		: ID
		| constante
		| invoc_fun
		| triple
		;

constante 	: CTE
		//| '-' CTE {
		//		if (ts.esUlongInt($2.sval)){
		//			lex.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos");
		//		}
		//		else {
		//			ts.convertirNegativo($2.sval);
		//		}
		//	}
		;

triple		: ID '{' expresion_matematica '}'
		;
declaracion_fun : tipo_basico FUN ID '(' lista_parametro ')' cuerpo_funcion_p {
										if (this.cantRetornos > 0){
											estructurasSintacticas("Se declaró la función: " + $3.sval);
											ts.addClave($3.sval);
											ts.addAtributo($3.sval,AccionSemantica.TIPO,AccionSemantica.FUNCION);
											ts.addAtributo($3.sval,AccionSemantica.TIPORETORNO,tipoVar);
										}
									}

		| tipo_basico FUN '(' lista_parametro ')' cuerpo_funcion_p { lex.addErrorSintactico("Falta nombre de la funcion declarada");}
		| tipo_basico FUN ID '(' ')' cuerpo_funcion_p { lex.addErrorSintactico("Falta el parametro en la declaracion de la funcion");}
		;

lista_parametro : lista_parametro ',' parametro { lex.addErrorSintactico("Se declaró más de un parametro");}
		| parametro 

parametro	: tipo ID {estructurasSintacticas("Se declaró el parámetro: " + $2.sval + " en la linea: " + lex.getLineaInicial());}
		| ID ID {estructurasSintacticas("Se declaró el parámetro: " + $2.sval + " en la linea: " + lex.getLineaInicial());}

		| tipo { lex.addErrorSintactico("Falta el nombre del parametro");}
		| ID { lex.addErrorSintactico("Falta el nombre del parametro o el tipo");}
		;

cuerpo_funcion_p : {ts.addClave(yylval.sval);} BEGIN bloques_funcion';' END
    		 ;

bloques_funcion : bloques_funcion';' bloque_funcion
    		| bloque_funcion 
    
    		| bloques_funcion bloque_funcion {lex.addErrorSintactico("Falta punto y coma");}
    		;
    
bloque_funcion : retorno
		| sentencia
		;

retorno 	: RET '('expresion')' { this.cantRetornos++; }
		;

invoc_fun	: ID '(' param_real ')' {estructurasSintacticas("Se invocó a la función: " + $1.sval + " en la linea: " + lex.getLineaInicial());}
		;

param_real	: tipo expresion_matematica
		| expresion
		| ID expresion_matematica
		;

sald_mensaj	: OUTF '(' mensaje ')'
		// error de parametro erroneo
		| OUTF '(' ')' { lex.addErrorSintactico("Falta el mensaje del OUTF");}
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

		//| GOTO {System.out.println("falta la etiqueta en el GOTO")}
		;

declar_tipo_trip: TYPEDEF TRIPLE '<' tipo_basico '>' ID {System.out.println("Se declaró un tipo TRIPLE con el ID: " + $6.sval + " en la linea:" + lex.getLineaInicial());}
		
		| TYPEDEF TRIPLE  tipo_basico '>' ID {lex.addErrorLexico("falta < en la declaración del TRIPLE"); }
		| TYPEDEF TRIPLE '<' tipo_basico  ID {lex.addErrorLexico("falta > en la declaración del TRIPLE"); }
		| TYPEDEF TRIPLE  tipo_basico  ID {lex.addErrorLexico("falta > y < en la declaración del TRIPLE"); }
		;
%%
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
String tipoVar;
int cantRetornos;
String estructuras;
public Parser(String nombreArchivo, TablaSimbolos t)
{
	this.nombreArchivo=nombreArchivo;
	this.ts=t;
	this.cantRetornos = 0;
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
	String prueba= "PruebaGramaticaErrores";
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
