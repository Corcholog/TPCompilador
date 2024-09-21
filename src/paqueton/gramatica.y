%{
	package paqueton;
	import java.io.*;
%}

%token ID CTE MASI MENOSI ASIGN DIST GOTO UP DOWN TRIPLE FOR ULONGINT DOUBLE IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CADMUL TAG

%%
prog		: ID BEGIN cuerpo END

		| BEGIN cuerpo END { lex.addErrorLexico("Falta nombre en prog");}
		| ID cuerpo END { lex.addErrorLexico("Falta Begin en prog");}
		| ID BEGIN cuerpo { lex.addErrorLexico("Falta End en prog");}
		| ID BEGIN END { lex.addErrorLexico("Falta Cuerpo en prog");}
		| ID cuerpo { lex.addErrorLexico("Falta begin y end en el prog");}
		| BEGIN cuerpo { lex.addErrorLexico("Falta nombre y end en prog");}
		| BEGIN END { lex.addErrorLexico("Falta nombre y cuerpo en prog");}
		| ID END { lex.addErrorLexico("Falta Cuerpo y Begin en prog");}
		| ID BEGIN { lex.addErrorLexico("Falta Cuerpo y end en prog");}
		;
cuerpo		: cuerpo sentencia
        	| sentencia
		;
sentencia       : sentec_declar
		| sentec_eject
		;
sentec_declar	: declaracion_var ';' {	System.out.println("Se declaro la variable, en linea: " + lex.getLineaInicial()); }
		| declaracion_fun ';' {	System.out.println("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
		| declar_tipo_trip ';' {System.out.println("Se declaro la variable tripla, en linea: " + lex.getLineaInicial()); }

		| declaracion_var { lex.addErrorLexico("Se declaro la variable, falta ;"); }
		| declaracion_fun {	lex.addErrorLexico("Se declaro la funcion, falta ;"); }
		| declar_tipo_trip {lex.addErrorLexico("Se declaro la variable tripla, falta ;"); }

		;
sentec_eject	: asignacion ';' {System.out.println("Se realizo una asignacion, en linea: " + lex.getLineaInicial()); }
		| invoc_fun ';' {System.out.println("Se invoco una funcion, en linea: " + lex.getLineaInicial()); }
		| seleccion ';' {System.out.println("Se hizo un if, en linea: " + lex.getLineaInicial()); }
		| sald_mensaj ';' {System.out.println("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
		| for ';' {System.out.println("Se declaro un for, en linea: " + lex.getLineaInicial()); }
		| goto ';' {System.out.println("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
		| TAG {	System.out.println("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial()); }
		;
condicion	: '(' condicion_2 ')'

		|  condicion_2 ')' {lex.addErrorLexico("falta el ( en la comparacion"); }
		| '(' condicion_2  {lex.addErrorLexico("falta el ) en la comparacion"); }
		| condicion_2  {lex.addErrorLexico("falta el ( y el ) en la comparacion"); }
		;
condicion_2 	: expresion comparador expresion
		| '(' lista_expres ')' comparador '(' lista_expres ')'

		| comparador expresion {lex.addErrorLexico("falta la primera expresion en la comparacion"); }
		| expresion  comparador {lex.addErrorLexico("falta la segunda expresion en la comparacion"); }
		| comparador {lex.addErrorLexico("faltan lasexpresiones en la comparacion"); }
		| '(' lista_expres  ')' '(' lista_expres ')' {lex.addErrorLexico("falta la primera lista de elementos en la comparacion"); }
		| '(' ')' comparador '(' lista_expres ')' {lex.addErrorLexico("falta el comparador"); }
		| '('lista_expres ')' comparador '(' ')' {lex.addErrorLexico("falta la segunda lista de elementos en la comparacion"); }
		| '(' ')' comparador '(' ')' {lex.addErrorLexico("faltan las listas de elementos en la comparacion"); }
		;
lista_expres	: lista_expres ',' expresion
		|lista_expres expresion {lex.addErrorLexico("Se esta comparando una lista de expresiones, falta ,"); }
		| expresion
		;
seleccion 	: IF condicion THEN cuerpo_control END_IF
        	| IF condicion THEN cuerpo_control ELSE cuerpo_control END_IF

		| condicion THEN cuerpo_control END_IF {lex.addErrorLexico("falta el if en la seleccion"); }
		| IF condicion THEN cuerpo_control {lex.addErrorLexico("falta el END_IF en la seleccion"); }
		| condicion THEN cuerpo_control {lex.addErrorLexico("falta el IF y el END_IF en la seleccion"); }
		| IF condicion THEN cuerpo_control cuerpo_control END_IF {lex.addErrorLexico("falta el else en la seleccion"); }
		| condicion THEN cuerpo_control cuerpo_control END_IF {lex.addErrorLexico("falta el if y el else en la seleccion"); }
		| IF condicion THEN cuerpo_control cuerpo_control {lex.addErrorLexico("falta el end_if y el else en la seleccion"); }
		| condicion THEN cuerpo_control cuerpo_control {lex.addErrorLexico("falta el if, end_if y el else en la seleccion"); }
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

		| ID '{' '}' {lex.addErrorLexico("falta la variable que indica la posicion"); }
		;
declaracion_var : tipo lista_variables 
		;
lista_variables : lista_variables ',' ID
		| ID	
		;
tipo		: tipo_basico	
		| ID
		;
tipo_basico	: DOUBLE
		| ULONGINT 
		;
asignacion 	: variable ASIGN expresion

		| variable ASIGN {lex.addErrorLexico("falta la expresion en la asignacion"); }
		| ASIGN expresion {lex.addErrorLexico("falta la variable en la asignacion"); }
		| ASIGN  {lex.addErrorLexico("falta la variable y la expresion en la asignacion"); }
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
		| invoc_fun
		;
declaracion_fun : tipo FUN ID '(' parametro ')' BEGIN cuerpo_funcion END

		| FUN ID '(' parametro ')' BEGIN cuerpo_funcion END {lex.addErrorLexico("falta el tipo de la funcion declarada"); }
		| tipo FUN '(' parametro ')' BEGIN cuerpo_funcion END {lex.addErrorLexico("falta el identificador de la funcion declarada"); }
		| tipo FUN ID '(' parametro ')' cuerpo_funcion END {lex.addErrorLexico("falta el begin de la funcion declarada"); }
		| tipo FUN ID '(' ')' BEGIN cuerpo_funcion END {lex.addErrorLexico("falta el parametro en la funcion declarada"); }
		| tipo FUN ID '(' parametro ')' BEGIN END {lex.addErrorLexico("falta el cuerpo en la funcion declarada"); }
		| tipo FUN ID  parametro ')' BEGIN cuerpo_funcion  END {lex.addErrorLexico("falta el ( en la funcion declarada"); }
		| tipo FUN ID  '(' parametro BEGIN cuerpo_funcion  END {lex.addErrorLexico("falta el ) en la funcion declarada"); }
		| tipo FUN ID  parametro BEGIN cuerpo_funcion  END {lex.addErrorLexico("falta el ( y el ) en la funcion declarada"); }
		| tipo FUN ID  parametro ')' BEGIN END {lex.addErrorLexico("falta el ( y el cuerpo en la funcion declarada"); }
		| tipo FUN ID '(' parametro  BEGIN END {lex.addErrorLexico("falta el ) y el cuerpo en la funcion declarada"); }
		| tipo FUN ID parametro BEGIN END {lex.addErrorLexico("falta el ( ) y el cuerpo en la funcion declarada"); }
		;
parametro	: tipo ID
		;
cuerpo_funcion	: cuerpo RET '(' expresion ')' ';'

		| RET '(' expresion ')' ';' {lex.addErrorLexico("falta el cuerpo en el cuerpo en la funcion declarada"); }
		| cuerpo RET '('  ')' ';' {lex.addErrorLexico("falta la expresion en el cuerpo en la funcion declarada"); }
		| cuerpo RET  expresion ')' ';' {lex.addErrorLexico("falta el parentesis izquierdo en el cuerpo en la funcion declarada"); }
		| cuerpo RET '(' expresion ';' {lex.addErrorLexico("falta el parentesis derecho en el cuerpo en la funcion declarada"); }
		| cuerpo RET '(' expresion ')' {lex.addErrorLexico("falta el punto y coma en el cuerpo en la funcion declarada"); }
		;
invoc_fun	: ID '(' param_real ')'

		| ID '(' ')'  {lex.addErrorLexico("falta el parametro real en la invocación"); }
		;
param_real	: tipo expresion
		| expresion

		;
sald_mensaj	: OUTF '(' mensaje ')'

		| OUTF mensaje ')' {lex.addErrorLexico("falta el parentesis izquierdo del mensaje del OUTF"); }
		| OUTF '(' mensaje {lex.addErrorLexico("falta el parentesis derecho del mensaje del OUTF"); }
		| OUTF mensaje {lex.addErrorLexico("faltan ambos parentesis del mensaje del OUTF"); }
		| OUTF '('  ')' {lex.addErrorLexico("falta el mensaje del OUTF"); }
		| OUTF {lex.addErrorLexico("falta el mensaje y los parentesis del OUTF"); }
		;
mensaje		: expresion
		| CADMUL
		;
for		: FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control
		
		| FOR ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorLexico("falta el parentesis izquierdo del FOR"); }
		| FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE cuerpo_control {lex.addErrorLexico("falta el parentesis derecho del FOR"); }
		| FOR ID ASIGN CTE ';' condicion ';' foravanc CTE cuerpo_control {lex.addErrorLexico("faltan ambos parentesis del FOR"); }

		| FOR '(' ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorLexico("falta el ID del FOR"); }
		| FOR '(' ID CTE ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorLexico("falta la asignacion del FOR"); }
		| FOR '(' ID ASIGN ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorLexico("falta la constante a asignar del FOR"); }
		| FOR '(' ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorLexico("falta todo ID ASIGN CTE del FOR"); }
		| FOR '(' ID ASIGN CTE ';'  ';' foravanc CTE ')' cuerpo_control {lex.addErrorLexico("falta la condicion del FOR"); }
		| FOR '(' ID ASIGN CTE ';' condicion ';'  CTE ')' cuerpo_control {lex.addErrorLexico("falta el avance del FOR"); }
		| FOR '(' ID ASIGN CTE ';' condicion ';' foravanc ')' cuerpo_control {lex.addErrorLexico("falta la constante de avance del FOR"); }
		| FOR '(' ID ASIGN CTE ';'')' cuerpo_control {lex.addErrorLexico("falta condicion y avance entero del FOR"); }

		| FOR '(' ';'  ';' foravanc CTE ')' {lex.addErrorLexico("falta asignacion entera y condicion entera del FOR"); }
		| FOR '(' ID ASIGN CTE ';'  ';'')' cuerpo_control {lex.addErrorLexico("falta condicion entera y avance entero del FOR"); }
		| FOR '(' ';' condicion ';' ')' cuerpo_control {lex.addErrorLexico("falta asignacion entera y avance entero del FOR"); }


		
		| '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control {lex.addErrorLexico("falta el FOR"); }
		
		
		;
foravanc	: UP
		| DOWN
		;
declar_tipo_trip: TYPEDEF TRIPLE '<' tipo_basico '>' ID
		
		| TRIPLE '<' tipo_basico '>' ID {lex.addErrorLexico("falta TYPEDEF en la declaración del TRIPLE"); }
		| TYPEDEF  '<' tipo_basico '>' ID {lex.addErrorLexico("falta TRIPLE en la declaración del TRIPLE"); }
		| TYPEDEF TRIPLE  tipo_basico '>' ID {lex.addErrorLexico("falta < en la declaración del TRIPLE"); }
		| TYPEDEF TRIPLE '<' tipo_basico  ID {lex.addErrorLexico("falta > en la declaración del TRIPLE"); }
		| TYPEDEF TRIPLE  tipo_basico  ID {lex.addErrorLexico("falta > y < en la declaración del TRIPLE"); }
		| TYPEDEF TRIPLE '<'  '>' ID {lex.addErrorLexico("falta el tipo_basico en la declaración del TRIPLE"); }
		;
goto		: GOTO TAG
		;
%%
String nombreArchivo;
AnalizadorLexico lex;
public Parser(String nombreArchivo, TablaSimbolos t)
{
	this.nombreArchivo=nombreArchivo;
	this.lex= new AnalizadorLexico(nombreArchivo,t);
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

public static void main(String[] args) {
	String prueba= "PruebaGramaticaErrores";
	TablaSimbolos tb= new TablaSimbolos();
	Parser p = new Parser(prueba,tb);
	System.out.println(p.yyparse());
	System.out.println(p.errores());
}
