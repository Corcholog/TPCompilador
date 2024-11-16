%{
	package paqueton;
	import java.io.*;
	import java.util.ArrayList;
	import java.util.Stack;
%}

%token ID CTE MASI MENOSI ASIGN DIST GOTO UP DOWN TRIPLE FOR ULONGINT DOUBLE IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET CADMUL TAG
%%
prog		: ID {this.ts.addClave($1.sval); this.ts.addAtributo($1.sval,AccionSemantica.USO,"nombre programa"); } cuerpo { tags.get(this.tags.size()-1).tagsValidos(lex.getLineaInicial()); estructurasSintacticas("Se declaró el programa: " + $1.sval); }

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
				String etiquetaAmbito=ambitoActual+":"+$1.sval;
				this.ts.addClave(etiquetaAmbito);
				this.ts.addAtributo(etiquetaAmbito,AccionSemantica.USO,"nombre de tag");
				this.tags.get(tags.size()-1).declaracionTag(etiquetaAmbito, lex.getLineaInicial());
				gc.addTerceto("TAG", etiquetaAmbito, "-");
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

condicion	: '('  condicion_2 ')' { $$.sval = $2.sval;}

		| condicion_2 { ErrorHandler.addErrorSintactico("Falta de paréntesis en la condición", lex.getLineaInicial());}
		| '(' condicion_2 { ErrorHandler.addErrorSintactico("Falta de paréntesis derecho en la condición", lex.getLineaInicial());}
		|  condicion_2 ')' { ErrorHandler.addErrorSintactico("Falta de paréntesis izquierdo en la condición", lex.getLineaInicial());}
		;
		
condicion_2 	: expresion_matematica comparador expresion_matematica { 
				String op1 = gc.checkDeclaracion($1.sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
				String op2 = gc.checkDeclaracion($3.sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
				if(op1 != null && op2 != null){
					$$.sval = gc.addTerceto($2.sval, op1, op2);
					gc.checkTipo(gc.getPosActual(), lex.getLineaInicial(), this.ts, this.ambitoActual, $2.sval);
				}else {
					$$.sval = null;
				}
			 }
		| '(' patron_izq ')' comparador '(' patron_der ')' { if(gc.getTerceto(gc.getPosActual()).getOp2().isEmpty()){ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lex.getLineaInicial());} else { $$.sval = gc.updateCompAndGenerate(this.inicioPatron, $4.sval, this.cantPatronIzq, this.cantPatronDer, lex.getLineaInicial());} this.inicioPatron = Integer.MAX_VALUE; $$.sval = "[" + this.gc.getPosActual() + "]";}

		| '(' patron_izq  comparador  patron_der ')' { ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
		| '(' patron_izq ')' comparador  patron_der ')' { ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
		| '(' patron_izq  comparador '(' patron_der ')' { ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
		| '(' patron_izq ')' error '(' patron_der ')' { ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
		| expresion_matematica error expresion_matematica { ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
		;

patron_izq	: lista_patron_izq ',' expresion_matematica { this.iniciarPatron(); this.cantPatronIzq++; $$.sval = gc.addTerceto("COMP", gc.checkDeclaracion($3.sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
		;

lista_patron_izq    : lista_patron_izq ',' expresion_matematica { this.iniciarPatron(); this.cantPatronIzq++;$$.sval = gc.addTerceto("COMP", gc.checkDeclaracion($3.sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
		| expresion_matematica {this.iniciarPatron(); this.cantPatronIzq=1; $$.sval = gc.addTerceto("COMP", gc.checkDeclaracion($1.sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
		;
patron_der	: lista_patron_der ',' expresion_matematica { this.cantPatronDer++; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion($3.sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
		;

lista_patron_der    : lista_patron_der ',' expresion_matematica { this.cantPatronDer++;posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion($3.sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
		| expresion_matematica { this.cantPatronDer = 1; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion($1.sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
		;
		
seleccion 	: IF condicion_punto_control THEN cuerpo_control sinelse_punto_control {estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
        	| IF condicion_punto_control THEN cuerpo_control else_punto_control cuerpo_control endif_punto_control



		| IF condicion_punto_control THEN cuerpo_control { ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
		| IF condicion_punto_control THEN else_punto_control { ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
		| IF condicion_punto_control THEN sinelse_punto_control { ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
		| IF condicion_punto_control THEN cuerpo_control else_punto_control endif_punto_control { ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
		| IF condicion_punto_control THEN else_punto_control endif_punto_control { ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
		;
 

		
sinelse_punto_control : END_IF {
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			gc.addTerceto("Label"+gc.getCantTercetos(), "FIN_IF_SOLO", "-");
			}
			;

condicion_punto_control : condicion {
			gc.addTerceto("BF", $1.sval, ""); 
			gc.push(gc.getPosActual());
		}
		;
else_punto_control : ELSE { 
			gc.addTerceto("BI", "", "-"); 
			int posSig = gc.getCantTercetos();
			gc.actualizarBF(posSig); 
			gc.pop(); 
			gc.push(gc.getPosActual());
			this.gc.addTerceto("Label" + posSig, "else", "-");
		}
		;
endif_punto_control : END_IF {
			int posSig = gc.getCantTercetos();
			gc.actualizarBI(posSig);
			this.gc.addTerceto("Label" + posSig, "endif", "-");
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
		;


comparador	: MASI {$$.sval = ">=";}
		| MENOSI {$$.sval = "<=";}
		| DIST {$$.sval = "!=";}
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
		
		| ID {tipoVar = $1.sval;} lista_variables {estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());} // preguntar
		;

lista_variables : lista_variables ',' ID {checkRedeclaracion($3.sval);}
		| ID {checkRedeclaracion($1.sval);}
		| lista_variables error ID { ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());} // No funciona para dejarlo vacío pero si para cuando el usuario pone un caracter inesperado
		;


tipo		: DOUBLE {$$.sval = "double";}
		| ULONGINT {$$.sval = "ulongint";}
		;

asignacion 	: triple_asig ASIGN expresion_matematica {estructurasSintacticas("Se realizó una asignación a la variable: " + $1.sval + " en la linea: " + lex.getLineaInicial());
						$$.sval = gc.checkTipoAsignacion($1.sval, lex.getLineaInicial(), $3.sval, this.ts,ambitoActual);}

		| ID ASIGN expresion_matematica {  estructurasSintacticas("Se realizó una asignación a la variable: " + $1.sval + " en la linea: " + lex.getLineaInicial());
					$$.sval = gc.checkTipoAsignacion($1.sval, lex.getLineaInicial(), $3.sval, this.ts,ambitoActual);
		}
	 	;

expresion_matematica 	: expresion_matematica '+' termino {$$.sval = gc.checkTipoExpresion($1.sval, $3.sval, lex.getLineaInicial(), this.ts, "+",ambitoActual);}

		| expresion_matematica '-' termino {$$.sval = gc.checkTipoExpresion($1.sval, $3.sval, lex.getLineaInicial(), this.ts, "-",ambitoActual);}

		| termino { $$.sval = $1.sval;}


		| '+' termino { ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
		| expresion_matematica '+' error ')'{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");} 
		| expresion_matematica '+' error ';' { ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
		| expresion_matematica '-' error ')'{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");} 
		| expresion_matematica '-' error ';' { ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
		;


termino 	: termino '*' factor {$$.sval = gc.checkTipoExpresion($1.sval, $3.sval, lex.getLineaInicial(), this.ts, "*",ambitoActual);}
		| termino '/' factor {$$.sval = gc.checkTipoExpresion($1.sval, $3.sval, lex.getLineaInicial(), this.ts, "/",ambitoActual);}
		| factor {$$.sval = $1.sval;}
		
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

factor		: ID {  gc.checkDeclaracion($1.sval, lex.getLineaInicial(), this.ts,ambitoActual);}
		| constante
		| invoc_fun
		| triple
		;

constante 	: CTE 	{ $$.sval = $1.sval;}	
		| '-' CTE   {	if (ts.esUlongInt($2.sval)){
					ErrorHandler.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos", lex.getLineaInicial());
				}
				else {
					ts.convertirNegativo($2.sval);
				}
				$$.sval = $2.sval;
			}
		;

triple_asig     : ID '{' expresion_matematica '}' {String tipo = gc.getTipoAccesoTripla($3.sval, this.ts);
						   if(tipo != "ulongint"){ErrorHandler.addErrorSintactico("Se intento acceder con un tipo distinto a entero a la tripla.", lex.getLineaInicial());}

							String idTripla=gc.checkDeclaracion($1.sval,lex.getLineaInicial(),this.ts,ambitoActual);
						   	if (idTripla != null) {		
						   	 	tipo = this.ts.getAtributo(idTripla, AccionSemantica.TIPO_BASICO); 
						   	 }else{
								ErrorHandler.addErrorSemantico( "La tripla " + idTripla + " nunca fue declarada.", lex.getLineaInicial()) ; 
								tipo = "error";
						  	  }
								$$.sval = $$.sval = gc.addTerceto("ASIGTRIPLA", idTripla, $3.sval, tipo);
							}
		;

triple		: ID '{' expresion_matematica '}' {String tipo = gc.getTipoAccesoTripla($3.sval, this.ts);
						   if(tipo != "ulongint"){ErrorHandler.addErrorSintactico("Se intento acceder con un tipo distinto a entero a la tripla.", lex.getLineaInicial());}											String idTripla=gc.checkDeclaracion($1.sval,lex.getLineaInicial(),this.ts,ambitoActual);
						   	if (idTripla != null) {		
						   	 	tipo = this.ts.getAtributo(idTripla, AccionSemantica.TIPO_BASICO); 
						   	 }else{
								ErrorHandler.addErrorSemantico( "La tripla " + idTripla + " nunca fue declarada.", lex.getLineaInicial()) ; 
								tipo = "error";
						  	  }
								$$.sval = $$.sval = gc.addTerceto("ACCESOTRIPLE", idTripla, $3.sval, tipo);
							}
		;

declaracion_fun : tipo_fun FUN ID '(' { if (esEmbebido($3.sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
					else {
					this.checkRedFuncion($3.sval, $1.sval);
					this.ambitoActual += ":" + $3.sval;
					}
					} lista_parametro ')' { 
								this.cantRetornos.add(0); 
								this.gc_funciones.push(this.ts.getGCFuncion(this.ambitoActual)); 
								this.gc = this.gc_funciones.peek(); 
								this.tags.add(new ControlTagAmbito());
								} cuerpo_funcion_p {
								tipoVar = $1.sval; 
								this.checkRet($3.sval);
								this.gc_funciones.pop();
								this.gc = this.gc_funciones.peek();
								tags.get(this.tags.size()-1).tagsValidos(lex.getLineaInicial());
								tags.remove(this.tags.size()-1);
								this.cambiarAmbito();
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
		| ID {ErrorHandler.addErrorSintactico("No se permite utilizar un tipo definido por el usuario como retorno", lex.getLineaInicial());}
		;

lista_parametro : lista_parametro ',' parametro { ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
		| parametro 

		;

parametro	: tipo ID { 
			this.ts.addClave(this.ambitoActual + ":" + $2.sval);
			String id_param = gc.checkDeclaracion($2.sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
			this.ts.addAtributo(id_param,AccionSemantica.TIPO, $1.sval); this.ts.addAtributo(id_param,AccionSemantica.USO,"nombre parametro"); this.ts.addAtributo(gc.checkDeclaracion(this.ambitoActual, lex.getLineaInicial(),this.ts, this.ambitoActual), AccionSemantica.PARAMETRO, id_param); 
			this.ts.addAtributo(id_param, AccionSemantica.TIPO, $1.sval); estructurasSintacticas("Se declaró el parámetro: " + $2.sval + " en la linea: " +			lex.getLineaInicial());}
		| ID ID { ErrorHandler.addErrorSintactico("No se permite una tripla como parametro", lex.getLineaInicial());}

		| tipo { ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
		| ID { ErrorHandler.addErrorSintactico("Falta el tipo o se intento utilizar una tripla sin nombre.", lex.getLineaInicial());} //buscando en tabla de simbolos se puede saber lo que falta
		;

cuerpo_funcion_p : BEGIN bloques_funcion ';' END

    		 ;

bloques_funcion : bloques_funcion';' bloque_funcion
    		| bloque_funcion 
    
    		| bloques_funcion bloque_funcion {ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
    		;
    
bloque_funcion : retorno
		| sentencia
		;

retorno 	: RET '('expresion_matematica')' { this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					String expresion = gc.checkDeclaracion($3.sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
					gc.checkTipoRetorno(expresion, ambitoActual,this.ts,lex.getLineaInicial());
					$$.sval = gc.addTerceto("RET", expresion, "");		
		}
		;

invoc_fun	: ID '(' {idFuncion = gc.checkDeclaracion($1.sval,lex.getLineaInicial(),this.ts,ambitoActual);} lista_parametro_real ')' { 
							estructurasSintacticas("Se invocó a la función: " + $1.sval + " en la linea: " + lex.getLineaInicial());
							String tipo = "";	
							String idFunc = gc.checkDeclaracion($1.sval,lex.getLineaInicial(),this.ts,ambitoActual);
							if(idFunc != null){
								System.out.println("F invocada es: " + idFunc + "y es de tipo: " + this.ts.getAtributo(idFunc, AccionSemantica.TIPO));
								tipo = this.ts.getAtributo(idFunc, AccionSemantica.TIPO);
							}
							else {
								ErrorHandler.addErrorSemantico("La funcion invocada " + $1.sval + " no existe.", lex.getLineaInicial());
								tipo = "error";
							}
							System.out.println("El tipo de la funcion invocada es: " + tipo);
							$$.sval = gc.addTerceto("INVOC_FUN",idFunc, $4.sval, tipo);//porque $4? :c
		}
		
		| ID '(' ')' { ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}

		;

lista_parametro_real : lista_parametro_real ',' param_real { $$.sval = $3.sval;ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
		| param_real { $$.sval = $1.sval;}
		;

param_real	: expresion_matematica {$$.sval = $1.sval; gc.checkParamReal($1.sval, lex.getLineaInicial(), this.ts, ambitoActual,idFuncion);}
		| tipo ID {
if(!this.ts.getAtributo(gc.checkDeclaracion($2.sval, lex.getLineaInicial(), this.ts, this.ambitoActual), AccionSemantica.TIPO).equals($1.sval)){$$.sval = gc.addTerceto("TO".concat($1.sval), gc.checkDeclaracion($2.sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
if(!this.ts.getAtributo(this.ts.getAtributo(gc.checkDeclaracion(this.ambitoActual, lex.getLineaInicial(), this.ts, ambitoActual), AccionSemantica.PARAMETRO), AccionSemantica.TIPO).equals($1.sval)){ ErrorHandler.addErrorSemantico("El tipo del parametro real no coincide con el tipo del parametro formal.", lex.getLineaInicial());}}
		;

sald_mensaj	: OUTF '(' mensaje ')' {$$.sval = gc.addTerceto("OUTF", $3.sval, "");}

		| OUTF '(' ')' { ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
		| OUTF '(' error ')' { ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
		;

mensaje		: expresion_matematica { $$.sval = $1.sval;}
		| CADMUL { System.out.println("CAMUL: " + $1.sval); $$.sval = $1.sval;}
		;

for		: FOR '(' asignacion_for ';' condicion_for ';' foravanc CTE ')' cuerpo_iteracion {	estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial()); 
				String var = this.varFors.get(this.varFors.size()-1);
				if(!this.ts.getAtributo($8.sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){
					ErrorHandler.addErrorSemantico("La constante de avance no es de tipo entero.", lex.getLineaInicial()); 
					gc.addTerceto("+", gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual), String.valueOf($7.ival * Double.parseDouble($8.sval)));
				} else {
					gc.addTerceto("+", gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual), String.valueOf($7.ival * Integer.parseInt($8.sval)), "ulongint");
				}		
				this.varFors.remove(this.varFors.size()-1);
				if($5.sval != null){
					gc.addTerceto("BI", "["+String.valueOf(Integer.parseInt($5.sval.substring(1, $5.sval.length()-1))-1)+"]", "");
					gc.actualizarBF(gc.getCantTercetos());
					gc.pop();
					this.gc.addTerceto("Label" + this.gc.getCantTercetos(), "endfor", "FOR"+this.cantFors); 
				}
				
		}

		| FOR '(' asignacion_for ';' condicion_for  foravanc '-' CTE ')' cuerpo_iteracion { ErrorHandler.addErrorSintactico("No se puede utilizar una constante negativa, en su lugar se debe utilizar el avance descendiente DOWN.", lex.getLineaInicial());}
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

asignacion_for  : ID ASIGN CTE {String varFor = gc.checkDeclaracion($1.sval,lex.getLineaInicial(),this.ts,ambitoActual);
				if (varFor != null){
					if(!this.ts.getAtributo(varFor, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){ErrorHandler.addErrorSemantico("La variable " + $1.sval + " no es de tipo entero.", lex.getLineaInicial());}
					gc.addTerceto(":=",varFor, $3.sval);
				}
				else{
					gc.addTerceto(":=", $1.sval, $3.sval);
				}
				if(!this.ts.getAtributo($3.sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){ErrorHandler.addErrorSemantico("La constante " + $3.sval + " no es de tipo entero.", lex.getLineaInicial());}
				this.varFors.add($1.sval);
				this.cantFors++;
				this.gc.addTerceto("Label" + this.gc.getCantTercetos(), "FOR"+this.cantFors, "-");
				}
		;

foravanc	: UP {$$.ival = 1;}
		| DOWN {$$.ival = -1;}
		;

goto		: GOTO TAG { $$.sval = gc.addTerceto("GOTO", ambitoActual + ":" + $2.sval,"");
			     this.ts.addAtributo($2.sval,AccionSemantica.USO,"nombre etiqueta");
			     this.tags.get(tags.size()-1).huboGoto(this.ambitoActual+":"+$2.sval);
			}

		| GOTO error ';' {ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
		;



declar_tipo_trip: TYPEDEF TRIPLE '<' tipo '>' ID {this.ts.addClave($6.sval); this.ts.addAtributo($6.sval,AccionSemantica.USO,"nombre de tipo tripla"); estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + $6.sval + " en la linea:" + lex.getLineaInicial()); this.ts.addAtributo($6.sval, "tipotripla", $4.sval); this.ts.addAtributo($6.sval, "tipo", $4.sval);}
		
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
GeneradorWasm gw;
Stack<GeneradorCodigo> gc_funciones;
String tipoVar;
ArrayList<Integer> cantRetornos;
ArrayList<ControlTagAmbito> tags; 
String estructuras;
ArrayList<String> varFors;
String ambitoActual;
String idFuncion;
Integer inicioPatron;
Integer posPatron;
Integer cantPatronIzq;
Integer cantPatronDer;
Integer cantFors;
public Parser(String nombreArchivo, TablaSimbolos t, GeneradorCodigo gc)
{
	this.nombreArchivo=nombreArchivo;
	this.ambitoActual = "global";
	this.inicioPatron = Integer.MAX_VALUE;
	this.posPatron = -1;
	this.cantPatronIzq = 0;
	this.cantPatronDer = 0;
	this.cantFors = 0;
	this.ts=t;
	this.gc = gc;
	this.gc_funciones = new Stack<GeneradorCodigo>();
	this.gc_funciones.push(gc);
	this.cantRetornos = new ArrayList<>();
	this.estructuras = "Estructuras sintacticas detectadas en el codigo fuente :  \n";
	this.lex= new AnalizadorLexico(nombreArchivo, t, this);
	this.varFors = new ArrayList<>();
	this.tags = new ArrayList<>();
	this.tags.add(new ControlTagAmbito());
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

void iniciarPatron(){
	if(this.inicioPatron > gc.getCantTercetos()){
		this.inicioPatron = gc.getCantTercetos();
		this.posPatron = this.inicioPatron;
	}
}

void cambiarAmbito(){
	int index = this.ambitoActual.lastIndexOf(":");
        if (index != -1) {
            this.ambitoActual = this.ambitoActual.substring(0, index); // Retorna todo hasta el ":"
        }
}

boolean esEmbebido(String sval){
	char firstC=sval.charAt(0);
	if ( firstC =='x' || firstC =='y' || firstC =='z' || firstC == 'd' ) {
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
	String varAmbito = ambitoActual +":"+val;
	if (esEmbebido(val)){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable", lex.getLineaInicial());
	}
	else if (redeclaracionTipoErroneo()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable y con tipos erroneos", lex.getLineaInicial());
	}
	else if (this.ts.estaEnTablaSimbolos(varAmbito)){
		ErrorHandler.addErrorSemantico("Se redeclaro la variable en el ambito: " +ambitoActual + " " , lex.getLineaInicial());	
	}
	else {
		ts.addClave(varAmbito);
		ts.addAtributo(varAmbito,AccionSemantica.TIPO,tipoVar);
		ts.addAtributo(varAmbito,AccionSemantica.USO,"nombre variable");
		if(!tipoVar.equals(AccionSemantica.DOUBLE) && !tipoVar.equals(AccionSemantica.ULONGINT)){
			if(!this.ts.getAtributo(tipoVar, AccionSemantica.TIPO).equals("")){
				ts.addAtributo(varAmbito, AccionSemantica.TIPO_BASICO, this.ts.getAtributo(tipoVar, AccionSemantica.TIPO));
			} else {
				ErrorHandler.addErrorSemantico("No existe la tripla con el ID " + tipoVar, lex.getLineaInicial());
			}
		}
	}
}

void estructurasSintacticas(String estructura){
	estructuras += estructura + "\n";
}

void checkRet(String nombreFuncion) {
	if (!nombreFuncion.isEmpty()) {
		if (this.cantRetornos.get(this.cantRetornos.size()-1) > 0){
			estructurasSintacticas("Se declaró la función: " + nombreFuncion);
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

void checkRedFuncion(String nombre, String tipo){
	String funcionAmbito = ambitoActual+":"+nombre;
	if(this.ts.estaEnTablaSimbolos(funcionAmbito)){
		ErrorHandler.addErrorSemantico("Funcion redeclarada "+nombre, lex.getLineaInicial());	
	}
	else {
		this.ts.addClave(funcionAmbito);
		ts.addAtributo(funcionAmbito,AccionSemantica.USO,"nombre funcion");
		this.ts.addAtributo(funcionAmbito, AccionSemantica.TIPO, tipo);
	}
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
    
    //System.out.println(p.lex.getListaTokens());
    //System.out.println("\n" + p.estructuras);	
    System.out.println("Errores y Warnings detectados del codigo fuente :  \n" + p.errores());
    System.out.println("Contenido de la tabla de simbolos:  \n" + tb);
    System.out.println("Codigo intermedio en tercetos: " + gc);
    if(ErrorHandler.huboError() == 0){
	p.gw = new GeneradorWasm(p.ts, p.gc, "salida");
	p.gw.traducir();
    }
    
    if (valido == 0) {
        System.out.println("Se analizo todo el codigo fuente");
    } else {
        System.out.println("No se analizo completamente el codigo fuente, debido a uno o mas errores inesperados");
    }
}