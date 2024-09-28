%{
	package paqueton;
	import java.io.*;
%}

%token ID CTE MASI MENOSI ASIGN DIST GOTO UP DOWN TRIPLE FOR ULONGINT DOUBLE IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CADMUL TAG

%%
prog		: ID BEGIN cuerpo END {estructurasSintacticas("Se declaró el programa: " + $1.sval);}

		| BEGIN cuerpo END { lex.addErrorSintactico("Falta nombre en prog");}
		| ID cuerpo END { lex.addErrorSintactico("Falta Begin en prog");}
		| ID BEGIN cuerpo { lex.addErrorSintactico("Falta End en prog");}
		| ID BEGIN END { lex.addErrorSintactico("Falta Cuerpo en prog");}
		| ID cuerpo { lex.addErrorSintactico("Falta begin y end en el prog");}
		| BEGIN cuerpo { lex.addErrorSintactico("Falta nombre y end en prog");}
		| BEGIN END { lex.addErrorSintactico("Falta nombre y cuerpo en prog");}
		| ID END { lex.addErrorSintactico("Falta Cuerpo y Begin en prog");}
		| ID BEGIN { lex.addErrorSintactico("Falta Cuerpo y end en prog");}
		;
cuerpo		: cuerpo sentencia
        	| sentencia
		;
sentencia       : sentec_declar
		| sentec_eject
		;
sentec_declar	: declaracion_var ';'
		| declaracion_fun ';' {	estructurasSintacticas("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
		| declaracion_var { lex.addErrorSintactico("Se declaro la variable, falta ;"); }
		| declaracion_fun {	lex.addErrorSintactico("Se declaro la funcion, falta ;"); }

		;
sentec_eject	: asignacion ';' 
		| invoc_fun ';' 
		| seleccion ';'
		| sald_mensaj ';' {estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
		| for ';' 
		| goto ';' {estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
		| TAG {	estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial()); }
		;
condicion	: '(' condicion_2 ')'

		|  condicion_2 ')' {lex.addErrorSintactico("falta el ( en la comparacion"); }
		| '(' condicion_2  {lex.addErrorSintactico("falta el ) en la comparacion"); }
		| condicion_2  {lex.addErrorSintactico("falta el ( y el ) en la comparacion"); }
		;
condicion_2 	: expresion comparador expresion
		| '(' lista_expres ')' comparador '(' lista_expres ')'

		| comparador expresion {lex.addErrorSintactico("falta la primera expresion en la comparacion"); }
		| expresion  comparador {lex.addErrorSintactico("falta la segunda expresion en la comparacion"); }
		| comparador {lex.addErrorSintactico("faltan lasexpresiones en la comparacion"); }
		| '(' lista_expres  ')' '(' lista_expres ')' {lex.addErrorSintactico("falta el comparador"); }
		| '(' ')' comparador '(' lista_expres ')' {lex.addErrorSintactico("falta la primera lista de elementos en la comparacion"); }
		| '('lista_expres ')' comparador '(' ')' {lex.addErrorSintactico("falta la segunda lista de elementos en la comparacion"); }
		| '(' ')' comparador '(' ')' {lex.addErrorSintactico("faltan las listas de elementos en la comparacion"); }
		;
lista_expres	: lista_expres ',' expresion
		| expresion
		;
seleccion 	: IF condicion THEN cuerpo_control END_IF {estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
        	| IF condicion THEN cuerpo_control ELSE cuerpo_control END_IF {estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());}

		| condicion THEN cuerpo_control END_IF {lex.addErrorSintactico("falta el if en la seleccion"); }
		| IF condicion THEN cuerpo_control {lex.addErrorSintactico("falta el END_IF en la seleccion"); }
		| condicion THEN cuerpo_control {lex.addErrorSintactico("falta el IF y el END_IF en la seleccion"); }
		| IF condicion THEN cuerpo_control cuerpo_control END_IF {lex.addErrorSintactico("falta el else en la seleccion"); }
		| condicion THEN cuerpo_control cuerpo_control END_IF {lex.addErrorSintactico("falta el if y el else en la seleccion"); }
		| IF condicion THEN cuerpo_control cuerpo_control {lex.addErrorSintactico("falta el end_if y el else en la seleccion"); }
		| condicion THEN cuerpo_control cuerpo_control {lex.addErrorSintactico("falta el if, end_if y el else en la seleccion"); }
		;
comparador	: MASI
		| MENOSI
		| DIST
		| '='	
		| '<'
		| '>'
		;
cuerpo_control	: BEGIN multip_cuerp_fun END
		| sentec_eject
		;
multip_cuerp_fun: multip_cuerp_fun sentec_eject
		| sentec_eject
		;
variable	: ID
		| ID '{' variable '}'
		| ID '{' '}' {lex.addErrorSintactico("falta la variable que indica la posicion"); }

		;
declaracion_var : tipo lista_variables {estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
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
		;
tipo		: tipo_basico	
		;

tipo_basico	: DOUBLE
		| ULONGINT 
		;
asignacion 	: variable ASIGN expresion {estructurasSintacticas("Se realizó una asignación a la variable: " + $1.sval + " en la linea: " + lex.getLineaInicial());}

		| variable ASIGN {lex.addErrorSintactico("falta la expresion en la asignacion"); }
		| ASIGN expresion {lex.addErrorSintactico("falta la variable en la asignacion"); }
		| ASIGN  {lex.addErrorSintactico("falta la variable y la expresion en la asignacion"); }
	 	;
expresion 	: expresion '+' termino
		| expresion '-' termino
		| termino
		;
termino 	: termino '*' factor
		| termino '/' factor
		| factor
		;
factor		: variable
		| CTE
		| '-' CTE {
				if (ts.esUlongInt($2.sval)){
					lex.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos");
				}
				else {
					ts.convertirNegativo($2.sval);
				}
			}
		| invoc_fun
		;
declaracion_fun : tipo_basico FUN ID '(' parametro ')' BEGIN cuerpo_funcion_p END {
										if (this.cantRetornos > 0){
											estructurasSintacticas("Se declaró la función: " + $3.sval);
											ts.addClave($3.sval);
											ts.addAtributo($3.sval,AccionSemantica.TIPO,AccionSemantica.FUNCION);
											ts.addAtributo($3.sval,AccionSemantica.TIPORETORNO,tipoVar);
										}
									}

		| FUN ID '(' parametro ')' BEGIN cuerpo_funcion END {lex.addErrorSintactico("falta el tipo de la funcion declarada"); }
		| tipo_basico FUN '(' parametro ')' BEGIN cuerpo_funcion END {lex.addErrorSintactico("falta el nombre de la funcion declarada"); }
		| tipo_basico FUN ID '(' parametro ')' cuerpo_funcion END {lex.addErrorSintactico("falta el begin de la funcion declarada"); }
		| tipo_basico FUN ID '(' ')' BEGIN cuerpo_funcion END {lex.addErrorSintactico("falta el  parametro en la funcion declarada"); }
		| tipo_basico FUN ID '(' parametro ')' BEGIN END {lex.addErrorSintactico("falta el cuerpo en la funcion declarada"); }
		| tipo_basico FUN ID  parametro ')' BEGIN cuerpo_funcion  END {lex.addErrorSintactico("falta el ( en la funcion declarada"); }
		| tipo_basico FUN ID  '(' parametro BEGIN cuerpo_funcion  END {lex.addErrorSintactico("falta el ) en la funcion declarada"); }
		| tipo_basico FUN ID  parametro BEGIN cuerpo_funcion  END {lex.addErrorSintactico("falta el ( y el ) en la funcion declarada"); }
		| tipo_basico FUN ID  parametro ')' BEGIN END {lex.addErrorSintactico("falta el ( y el cuerpo en la funcion declarada"); }
		| tipo_basico FUN ID '(' parametro  BEGIN END {lex.addErrorSintactico("falta el ) y el cuerpo en la funcion declarada"); }
		| tipo_basico FUN ID parametro BEGIN END {lex.addErrorSintactico("falta el ( ) y el cuerpo en la funcion declarada"); }
		;

parametro	: tipo ID {estructurasSintacticas("Se declaró el parámetro: " + $2.sval + " en la linea: " + lex.getLineaInicial());}
		;


cuerpo_funcion_p	: {ts.addClave(yylval.sval)} cuerpo_funcion 
			;

cuerpo_funcion	: cuerpo_funcion retorno 
		| cuerpo_funcion sentencia
		| retorno
		| sentencia
		;

retorno 	: RET '('expresion')' ';' { this.cantRetornos ++;}
		| RET '('  ')' ';' {lex.addErrorSintactico("falta la expresion en el cuerpo en la funcion declarada"); }
		| RET  expresion ')' ';' {lex.addErrorSintactico("falta el parentesis izquierdo en el cuerpo en la funcion declarada"); }
		| RET '(' expresion ';' {lex.addErrorSintactico("falta el parentesis derecho en el cuerpo en la funcion declarada"); }
		| RET '(' expresion ')' {lex.addErrorSintactico("falta el punto y coma en el cuerpo en la funcion declarada"); }
		;

invoc_fun	: ID '(' param_real ')' {estructurasSintacticas("Se invocó a la función: " + $1.sval + " en la linea: " + lex.getLineaInicial());}

		| ID '(' ')'  {lex.addErrorSintactico("falta el parametro real en la invocación"); }
		;
param_real	: tipo expresion
		| expresion

		;
sald_mensaj	: OUTF '(' mensaje ')'

		| OUTF mensaje ')' {lex.addErrorSintactico("falta el parentesis izquierdo del mensaje del OUTF"); }
		| OUTF '(' mensaje {lex.addErrorSintactico("falta el parentesis derecho del mensaje del OUTF"); }
		| OUTF mensaje {lex.addErrorSintactico("faltan ambos parentesis del mensaje del OUTF"); }
		| OUTF '('  ')' {lex.addErrorSintactico("falta el parametro del OUTF"); }
		| OUTF {lex.addErrorSintactico("falta el mensaje y los parentesis del OUTF"); }
		;
mensaje		: expresion
		| CADMUL
		;
for		: FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control {estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());}
		
		| FOR ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorSintactico("falta el parentesis izquierdo del FOR"); }
		| FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE cuerpo_control {lex.addErrorSintactico("falta el parentesis derecho del FOR"); }
		| FOR ID ASIGN CTE ';' condicion ';' foravanc CTE cuerpo_control {lex.addErrorSintactico("faltan ambos parentesis del FOR"); }

		| FOR '(' ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorSintactico("falta el ID del FOR"); }
		| FOR '(' ID CTE ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorSintactico("falta la asignacion del FOR"); }
		| FOR '(' ID ASIGN ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorSintactico("falta la constante a asignar del FOR"); }
		| FOR '(' ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorSintactico("falta todo ID ASIGN CTE del FOR"); }
		| FOR '(' ID ASIGN CTE ';'  ';' foravanc CTE ')' cuerpo_control {lex.addErrorSintactico("falta la condicion del FOR"); }
		| FOR '(' ID ASIGN CTE ';' condicion ';'  CTE ')' cuerpo_control {lex.addErrorSintactico("falta el avance del FOR"); }
		| FOR '(' ID ASIGN CTE ';' condicion ';' foravanc ')' cuerpo_control {lex.addErrorSintactico("falta la constante de avance del FOR"); }
		| FOR '(' ID ASIGN CTE ';'')' cuerpo_control {lex.addErrorSintactico("falta condicion y avance entero del FOR"); }

		| FOR '(' ';'  ';' foravanc CTE ')' {lex.addErrorSintactico("falta asignacion entera y condicion entera del FOR"); }
		| FOR '(' ID ASIGN CTE ';'  ';'')' cuerpo_control {lex.addErrorSintactico("falta condicion entera y avance entero del FOR"); }
		| FOR '(' ';' condicion ';' ')' cuerpo_control {lex.addErrorSintactico("falta asignacion entera y avance entero del FOR"); }


		
		| '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorSintactico("falta el FOR"); }
		
		
		;
foravanc	: UP
		| DOWN
		;

goto		: GOTO TAG
		| GOTO {System.out.println("falta la etiqueta en el GOTO")}
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
