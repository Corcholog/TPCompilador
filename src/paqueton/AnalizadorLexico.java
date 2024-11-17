package paqueton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class AnalizadorLexico {
	private int linea = 1;
	private int pos = 0;
	private int estado = 0;
	private boolean huboError;
	private boolean saltoLinea;
	private int nroToken;
	private String concatActual;
	private ArrayList<String> lineasCodigo;
	private File archivo;
	private Integer[][] matTrans ;
	private Set<String> idTokens;
	private TablaSimbolos tablaSimbolos;
	private AccionSemantica[][] matAcciones;
	private int lineaInicial;
	private int lineaInicialDevolver;
	private Parser parce;
	private String listaTokens;
	private String tokenErrorHandler;
	
 	public AnalizadorLexico(String ruta, TablaSimbolos ts, Parser parce) {
 		this.parce = parce;
		this.inicializarMatTrans();
		this.inicializarIdTokens();
		this.inicializarMatAcciones();
		this.tokenErrorHandler = "";
		this.saltoLinea = false;
		this.huboError=false;
		this.listaTokens = "Tokens: \n";
		this.setConcatActual("");
		this.tablaSimbolos = ts;
		String camino = Paths.get(System.getProperty("user.dir"))
                .getParent()
                .resolve("src")
                .resolve("codes")
                .resolve(ruta + ".txt")
                .toString();
		this.archivo = new File(camino);
		this.lineaInicial=0;
		this.lineaInicialDevolver=1;
        if (!archivo.exists()) {
            System.out.println("El archivo especificado no existe: " + camino);
            return;
        }

        // Leer el contenido del archivo línea por línea
        try {
            this.lineasCodigo = (ArrayList<String>) Files.readAllLines(Paths.get(camino));
            System.out.println("Se leyo correctamente el archivo \n");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo.");
            e.printStackTrace();
        }
	}
 	
 	// inicializaciones
 	private void inicializarMatTrans() { //chequear matriz jeje xD
		this.matTrans = new Integer[][]{
				{8,	9,	1,	8,	0, -1, 11, -1, 12, -1, 10, -1, 13, 2, 0, 15, 0, 16, -1, 0, 1,0, -1, -1}, //
				{-1, -1, 1,	-1, -1, -1, -1, -1, -1, -1,	-1,	-1,	-1,	1, -1, -1, -1,	-1,	3, -1, 1,-1, -1, -1}, //
				{-1, -1, 4,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	4, -1, -1, -1, -1, 3, -1, 4,-1, -1, -1}, //
				{-1, -1, 3, -1, -1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	3, -1, -1, -1,	5,	3,	-1,	3,-1, -1, -1}, //
				{-1, -1, 4, -1, -1, -1, -1, -1, -1, 6, -1,	-1,	-1,	4, -1, -1, -1,	-1,	-1,	-1,	4,-1, -1, -1}, //
				{-1, -1, 7, -1,	-1,	-1,	-1,	-1,	-1,	6, -1, -1, -1, 7, -1, -1, -1, -1, -1, -1, 7,-1, -1,-1}, //
				{-1, -1, 7,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	7, -1, -1, -1, -1, -1, -1, 7,-1, -1,-1}, //
				{-1, -1, 7,	-1, -1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	7,	-1,	-1,	-1,	-1,	-1,	-1, 7,-1, -1,-1}, //
				{8,	8,	8,	8,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	8, -1, -1, -1, 8, -1, -1, 8,-1, -1,-1}, //
				{9,	9,	9,	9,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	9,	-1,	-1,	-1,	9,	-1,	-1,	9,-1, -1,-1}, //
				{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1, -1,-1}, //
				{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1}, //
				{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1}, //
				{14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 0,	14,	14,	14,	14,	14,	14,14, 14,14}, //
				{14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 0, 14,	14,	14,	14,	14,	14,14, 14, 14}, //
				{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, -1, 15, 15, 15, 15,15, 15, 15}, //
				{16, 16, 16, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, 16, -1, -1, 16,-1, -1,-1},
				{-1,	-1,	4,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	17,	-1,	-1,	-1,	-1,	-1,	-1,	4, -1, -1,-1}
		};
	}
	
 	private void inicializarMatAcciones() {
		AccionSemantica.AS0 as0 = new AccionSemantica.AS0();
		AccionSemantica.AS1 as1 = new AccionSemantica.AS1();
		AccionSemantica.ASE1 ase1 = new AccionSemantica.ASE1();
		AccionSemantica.ASF1 asf1 = new AccionSemantica.ASF1();
		AccionSemantica.ASF1Comp asf1c = new AccionSemantica.ASF1Comp();
		AccionSemantica.ASF2OCTAL asf2octal = new AccionSemantica.ASF2OCTAL();
		AccionSemantica.ASBR asbr = new AccionSemantica.ASBR();
		AccionSemantica.ASBR2 asbr2 = new AccionSemantica.ASBR2();
		AccionSemantica.ASFBR3 asfbr3 = new AccionSemantica.ASFBR3();
		AccionSemantica.ASFBR4 asfbr4 = new AccionSemantica.ASFBR4();
		AccionSemantica.ASFBR5 asfbr5 = new AccionSemantica.ASFBR5();
		AccionSemantica.ASE2 ase2 = new AccionSemantica.ASE2();
		AccionSemantica.ASE3 ase3 = new AccionSemantica.ASE3();
		AccionSemantica.ASF2Double asf2double = new AccionSemantica.ASF2Double();
		AccionSemantica.ASF2LongInt asf2longint = new AccionSemantica.ASF2LongInt();
		AccionSemantica.ASF2COMP ASF2COMP = new AccionSemantica.ASF2COMP();
		AccionSemantica.ASE4 ase4 = new AccionSemantica.ASE4();
		AccionSemantica.ASE5 ase5 = new AccionSemantica.ASE5();
		AccionSemantica.ASE7 ase7 = new AccionSemantica.ASE7();
		AccionSemantica.ASE8 ase8 = new AccionSemantica.ASE8();
		AccionSemantica.ASE9 ase9 = new AccionSemantica.ASE9();
		AccionSemantica.ASE10 ase10 = new AccionSemantica.ASE10();
		AccionSemantica.ASE11 ase11 = new AccionSemantica.ASE11();
		AccionSemantica.ASDescartaComentario asd = new AccionSemantica.ASDescartaComentario();
		AccionSemantica.ASFGOTO asfgoto = new AccionSemantica.ASFGOTO();
        
 		this.matAcciones = new AccionSemantica[][]{
			{as1, as1, as1, ase1, as0, asf1, as1, asf1,	as1, asf1, as1,	asf1, as1, as1, asbr, as1, ase2, as1, asf1, ase3, as1,ase10, asf1, asf1},
			{asf2longint, asf2longint, as1, asf2longint,	asf2longint,	asf2longint,	asf2longint,	asf2longint,	asf2longint,	asf2longint,	asf2longint,	asf2longint,	asf2longint,	as1,	asf2longint,	asf2longint,	asf2longint,	ase4,	as1,	asf2longint,	as1,asf2longint, asf2longint, asf2longint},
			{asf2octal,	asf2octal,	ase5,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	as1,	asf2octal ,asf2octal,	asf2octal,	ase4, as1,	asf2octal,	as1,asf2octal, asf2octal, asf2octal},
			{asf2double,	asf2double,	as1,	asf2double	,asf2double	,asf2double,	asf2double	,asf2double	,asf2double,	asf2double,	asf2double	,asf2double	,asf2double,	as1	,asf2double	,asf2double	,asf2double	,as1	,ase7,	asf2double	,as1,asf2double, asf2double, asf2double},
			{asf2octal,	asf2octal,	ase5,	asf2octal,	asf2octal	,asf2octal	,asf2octal,	asf2octal	,asf2octal	,asf2octal	,asf2octal	,asf2octal,	asf2octal,	as1,	asf2octal	,asf2octal,	asf2octal	,asf2octal,	asf2octal,	asf2octal,	as1,asf2octal, asf2octal, asf2octal},
			{ase8,	ase8, as1	,ase8	,ase8	,ase8,	ase8	,ase8,	ase8	,as1	,ase8,	ase8,	ase8,	as1,	ase8,	ase8	,ase8,	ase8	,ase8	,ase8,	as1,ase8, ase8, ase8},
			{ase8,	ase8,	as1,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	as1,	ase8,	ase8,	ase8	,ase8,	ase8,	ase8,	as1,ase8, ase8, ase8},
			{asf2double,	asf2double,	as1	,asf2double,	asf2double,	asf2double	,asf2double,	asf2double	,asf2double	,asf2double	,asf2double	,asf2double	,asf2double	,as1	,asf2double	,asf2double	,asf2double	,asf2double	,asf2double	,asf2double	,as1,asf2double, asf2double, asf2double},
			{as1,	as1	,as1	,as1	,asfbr3	,asfbr3	,asfbr3	,asfbr3	,asfbr3	,asfbr3	,asfbr3	,asfbr3	,asfbr3	,as1	,asfbr3,	asfbr3,	asfbr3	,as1,	asfbr3,	asfbr3	,as1,asfgoto, asfbr3, asfbr3},
			{as1,	as1	,as1	,as1,	asfbr4	,asfbr4,	asfbr4,	asfbr4	,asfbr4	,asfbr4,	asfbr4,	asfbr4	,asfbr4	,as1,	asfbr4	,asfbr4	,asfbr4,	as1,	asfbr4,	asfbr4,	as1,asfgoto, asfbr4, asfbr4},
			{ase9	,ase9	,ase9,	ase9,	ase9,	ase9	,ase9,	asf1,	ase9	,ase9,	ase9,	ase9	,ase9	,ase9	,ase9,	ase9,	ase9	,ase9,	ase9,	ase9,	ase9,ase9, ase9, ase9},
			{ASF2COMP,	ASF2COMP,	ASF2COMP,	ASF2COMP	,asf1	,ASF2COMP,	ASF2COMP	,asf1,	ASF2COMP	,ASF2COMP,	ASF2COMP,	ASF2COMP	,ASF2COMP	,ASF2COMP,	ASF2COMP	,ASF2COMP,	ASF2COMP,	ASF2COMP,	ASF2COMP,	ASF2COMP,	ASF2COMP,ASF2COMP, ASF2COMP, ASF2COMP},
			{ase9,	ase9,	ase9,	ase9,	ase9,	ase9,	ase9	,asf1,	ase9	,ase9	,ase9	,ase9	,ase9	,ase9	,ase9,	ase9	,ase9,	ase9	,ase9,	ase9,	ase9,ase9, ase9, ase9},
			{ase11,	ase11	,ase11,	ase11	,ase11	,ase11	,ase11	,ase11	,ase11	,ase11	,ase11	,ase11	,as1	,ase11	,asd	,ase11,	ase11,	ase11	,ase11,	ase11,	ase11,ase11, ase11, ase11},
			{as1,	as1	,as1,	as1,	as1,	as1	,as1,	as1	,as1	,as1	,as1,	as1,	as1,	as1,	asd,	as1,	as1,	as1,	as1,	as1,	as1,as1, as1, as1},
			{as1,	as1,	as1,	as1	,as1,	as1,	as1	,as1	,as1	,as1	,as1	,as1	,as1	,as1,	asbr2,	as1,	asf1c	,as1,	as1,	ase3,	as1,as1, as1, as1},
			{as1,	as1,	as1	,as1	,asfbr5,	asfbr5,	asfbr5,	asfbr5	,asfbr5,	asfbr5,	asfbr5,	asfbr5,	asfbr5	,as1,	asfbr5,	asfbr5	,asfbr5	,as1	,asfbr5	,asfbr5,	as1,asfgoto, asfbr5, asfbr5},
			{asf2octal,	asf2octal,	ase5,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	asf2octal,	as0,	asf2octal,	asf2octal,	asf2octal,	ase4,	asf2octal,	asf2octal,	as1, asf2octal, asf2octal ,asf2octal}
		};
 	}

 	private void inicializarIdTokens() { //cambiar el idToken a un set
        this.idTokens = new HashSet<>();
        idTokens.add("goto");
        idTokens.add("up");
        idTokens.add("down");
        idTokens.add("triple");
        idTokens.add("for");
        idTokens.add("ulongint");
        idTokens.add("double");
        idTokens.add("if");
        idTokens.add("then");
        idTokens.add("else");
        idTokens.add("begin");
        idTokens.add("end");
        idTokens.add("end_if");
        idTokens.add("outf");
        idTokens.add("typedef");
        idTokens.add("fun");
        idTokens.add("ret");
    }
 	
	//yylex()
 	
	public int yylex() {
		
		if (!this.tokenErrorHandler.isEmpty()) {
			String aux = this.tokenErrorHandler;
			this.setErrorHandlerToken("");
			return aux.charAt(0);
		}
		//chequeo errores
		if(lineaInicial==0) {
			lineaInicial=linea;
			lineaInicialDevolver=lineaInicial;
		}
		boolean corta = false;
		if(finalArchivo()) {
			return 0; //end of file
		}
		while ((estado != -1)) {
			if (finalArchivo()) {
				corta = concatActual.equals("");
				if (corta ) {
					return 0;
				}
				if (concatActual.charAt(0) == '[') {
					this.addWarning("Faltó cerrar cadena multilinea, se agregó ] .");
					return Parser.CADMUL; //cadena multilinea
				}
			}
			if(!finalArchivo() && lineasCodigo.get(linea-1).length() == 0) {
				this.avanzarLinea();
			}else {
				if (!corta) {	
					int estado_anterior = estado;
					int col = getColumna();
					estado = matTrans[estado][col];
					matAcciones[estado_anterior][col].ejecutar(this);
					if(col == 14) {
						saltoLinea = false;
					}
					//System.out.println(col);
				    //System.out.println("Voy leyendo: " + concatActual + "\n");
					//System.out.println("Estado: " + estado_anterior + " pasa a: " + estado + "\n");
					//System.out.println("Se ejecuta: " + matAcciones[estado_anterior][col].getClass().getName().split("\\$")[1]);
					//System.out.println("__________________________________________________________ \n");
				}
			}
		}
		if (huboError) {
			this.reset();
			return yylex();
		}		
		int devolver = nroToken;
		listaTokens +=  "[\""+ Parser.getNombreVariable(devolver) +"\"] - ";
		this.reset();	
		return devolver;
	}
	
	public String getListaTokens() {
		return listaTokens;
	}
	
	public Parser getParser() {
		return this.parce;
	}
	
	public int getLineaInicial() {
		return lineaInicialDevolver;
	}

 	public void addError(String e) {
 		ErrorHandler.addErrorLexico(e, lineaInicial);
 		this.huboError=true;
 	}
 	
 	public void addWarning(String e) {
 		ErrorHandler.addWarningLexico(e, lineaInicial);
 	}
 	
	public void addTablaSimbolos() { //yylval
		if (!tablaSimbolos.estaEnTablaSimbolos(concatActual)){
			tablaSimbolos.addClave(concatActual);
		}
	}
	
	public void addTablaSimbolos(String ambitoVar) { //yylval
		if (!tablaSimbolos.estaEnTablaSimbolos(ambitoVar + concatActual)){
			tablaSimbolos.addClave(ambitoVar + concatActual);
		}
	}
	
	public void addAtributoTablaSimbolos(String claveAtributo, String atributo) {
		this.getTablaSimbolos().addAtributo(concatActual, claveAtributo, atributo);
	}
	
	public void addAtributoTablaSimbolos(String claveAtributo, String atributo,String ambitoVar) {
		this.getTablaSimbolos().addAtributo(ambitoVar+concatActual, claveAtributo, atributo);
	}
	
	
	
	public boolean esPalabraReservada() {
		return (this.idTokens.contains(concatActual));
	}
	
	public boolean finalArchivo() {
		return lineasCodigo.size() < linea;
	}
	
	// getters
	
	private int getColumna() {
		Character character = null;
		if (!finalArchivo()) {
			character = lineasCodigo.get(linea-1).toLowerCase().charAt(pos);
		}
 		if (saltoLinea) {
 			return 14;
 		} else {
 			switch (character) {
 	 		case 'x': case 'y': case 'z':
 	 	        return 1;
 	 	    
 	 		case 'd':
 	 	        return 17;
 	 	    
 	 	    case 'a': case 'b': case 'c': case 'e': case 'f': case 'g':
 	 	    case 'h': case 'i': case 'j': case 'k': case 'l': case 'm': case 'n':
 	 	    case 'o': case 'p': case 'q': case 'r': case 's': case 't': case 'u':
 	 	    case 'v': case 'w':
 	 	        return 0;
 	 	        
 	 	    case '0':
 		        return 13;
 		        
 	 	    case '1': case '2': case '3': case '4': case '5': case '6': case '7':
 		        return 20;
 	 	        
 	 	    case '8': case '9':
 	 	        return 2;
 	 	        
 	 	    case '_':
 	 	        return 3;
 	 	    //en el case de abajo manejamos espacios y tabulaciones de la misma manera.
 	 	    case '\t':    
 	 	    case ' ':
 	 	        return 4;
 	 	        
 	 	    case '+': case '-':
 		        return 9;
 	 	        
 	 	    case '*': case '/':
 	 	        return 5;
 	 	        
 	 	    case '<': case '>':
 	 	        return 6;
 	 	        
 	 	    case '=':
 	 	        return 7;
 	 	        
 	 	    case '!':
 	 	        return 8;
 	 	        
 	 	    case ':':
 	 	        return 10;
 	 	        
 	 	    case '.':
 	 	        return 18;
 	 	        
 	 	    case '(': case ')': case ',': case ';':
 	 	        return 11;
 	 	        
 	 	    case '#':
 	 	        return 12;
 	 	          
 	 	    case '[':
 	 	        return 15;
 	 	        
 	 	    case ']':
 	 	        return 16;
 	 	        
 	 	    case '@':
 	 	        return 21;
 	 	       
 	 	    case '{':
 	 	    	return 22;
 	 	    	
 	 	    case '}':
 	 	    	return 23;
 	 	 
 	 	    default:
 	 	        // Caso: otro (carácter no contemplado en los casos anteriores)
 	 	    	return 19;
 	 		}
 		}
 		
 	}

 	public int getIdToken() {
 		switch (concatActual) {
 	    case ">=":
 	        return Parser.MASI;
 	    case "<=":
 	    	return Parser.MENOSI;
 	    case ":=":
 	    	return Parser.ASIGN;
 	    case "!=":
 	    	return Parser.DIST;
 	    case "goto":
 	    	return Parser.GOTO;
 	    case "up":
 	    	return Parser.UP;
 	    case "down":
 	    	return Parser.DOWN;
 	    case "triple":
 	    	return Parser.TRIPLE;
 	    case "for":
 	    	return Parser.FOR;
 	    case "ulongint":
 	    	return Parser.ULONGINT;
 	    case "double":
 	    	return Parser.DOUBLE;
 	    case "if":
 	    	return Parser.IF;
 	    case "then":
 	    	return Parser.THEN;
 	    case "else":
 	    	return Parser.ELSE;
 	    case "begin":
 	    	return Parser.BEGIN;
 	    case "end":
 	    	return Parser.END;
 	    case "end_if":
 	    	return Parser.END_IF;
 	    case "outf":
 	    	return Parser.OUTF;
 	    case "typedef":
 	    	return Parser.TYPEDEF;
 	    case "fun":
 	    	return Parser.FUN;
 	    case "ret":
 	    	return Parser.RET;
 	    default:
 	        if( Character.toString(concatActual.charAt(0)).equals("[") )
 	        	return Parser.CADMUL;
 	        else {
 	        	int ascii = concatActual.charAt(0);
 	        	return ascii;
 	        }
 		}

 	}
	
	public TablaSimbolos getTablaSimbolos() {
		return tablaSimbolos;
	}

	public int getNroToken() {
		return nroToken;
	}

	public String getConcatActual() {
		return concatActual;
	}
	
	public void setConcatActual(String concatActual) {
		this.concatActual = concatActual.toLowerCase();
	}
	
	public void setErrorHandlerToken(String token) {
		this.tokenErrorHandler = token;
	}
    
	public void setNroToken(int nroToken) {
		this.nroToken = nroToken;
	}	
	
	public void concatenar() {
		setConcatActual( (getConcatActual() + lineasCodigo.get(linea-1).charAt(pos) ).toLowerCase() );
	}
	
	public void concatenaMultilinea() {
		setConcatActual(getConcatActual() + " ");
	}
	
	private void reset() {
		this.huboError=false;
		this.concatActual = "";
		this.estado = 0;
		this.nroToken = -4;
		this.lineaInicial=0;
	}
	
	public void avanzarLinea() {
		if(concatActual.equals("")) {
			lineaInicial++;
			lineaInicialDevolver=lineaInicial;
		}
		saltoLinea = true;
		linea++;
		pos = 0;
	}
	
	public void avanzarPos() {
		if(lineasCodigo.get(linea-1).length()-1 == pos) {
			avanzarLinea();
		} else {
			pos++;
		}
	}
		
	public static void main(String[] args) {
        String nombreArchivo = "pruebaCodigoLexico";
       
        TablaSimbolos ts = new TablaSimbolos();
        AnalizadorLexico anal = new AnalizadorLexico(nombreArchivo, ts, new Parser());
        int fin = anal.yylex();
        while (fin != 0) {
        	fin = anal.yylex();
        }
        System.out.println(anal.parce.errores());
    }
}

