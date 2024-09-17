%{
	package paqueton;
	import java.io.*;
%}

%token ID CTE MASI MENOSI ASIGN DIST GOTO UP DOWN TRIPLE FOR ULONGINT DOUBLE IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CADMUL TAG

%%
prog		: ID BEGIN cuerpo END
		;
cuerpo		: cuerpo sentencia
        	| sentencia
		;
sentencia       : sentec_declar
		| sentec_eject
		;
sentec_declar	: declaracion_var ';'
		| declaracion_fun ';'
		| declar_tipo_trip ';'
		;
sentec_eject	: asignacion ';'
		| invoc_fun ';'
		| seleccion ';'
		| sald_mensaj ';'
		| for ';'
		| goto ';'
		;
condicion	: '(' expresion comparador expresion ')'
		| '(' '(' lista_expres ')' comparador '(' lista_expres ')' ')'
		;
lista_expres	: lista_expres ',' expresion
		| expresion
		;
seleccion 	: IF condicion THEN cuerpo_control END_IF
        	| IF condicion THEN cuerpo_control ELSE cuerpo_control END_IF
		;
comparador	: MASI
		| MENOSI
		| DIST
		| '='
		;
cuerpo_control	: BEGIN multip_cuerp_fun END
		| sentec_eject
		;
multip_cuerp_fun: multip_cuerp_fun sentec_eject
		| sentec_eject
		;
variable	: ID
		| ID '{' variable '}'
		;
declaracion_var : tipo lista_variables 
		;
lista_variables : lista_variables ',' ID
		| ID
		;
tipo		: ULONGINT 
		| DOUBLE 
		| ID
		;
asignacion 	: variable ASIGN expresion
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
		;
parametro	: tipo ID
		;
cuerpo_funcion	: cuerpo RET '(' expresion ')'
		;
invoc_fun	: ID '(' param_real ')'
		;
param_real	: tipo expresion
		| expresion
		;
sald_mensaj	: OUTF '(' mensaje ')'
		;
mensaje		: expresion
		| CADMUL
		;
for		: FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control
		;
foravanc	: UP
		| DOWN
		;
declar_tipo_trip: TYPEDEF TRIPLE '<' tipo '>' ID
		;
goto		: GOTO TAG
		;