package paqueton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import paqueton.AccionSemantica;


public class AnalizadorLexico {
	private int linea = 1;
	private int pos = 0;
	private int estado = 0;
	private boolean saltoLinea;
	private String nroToken;
	private String concatActual;
	private ArrayList<String> lineasCodigo;
	private File archivo;
	private Integer[][] matTrans ;
	private Map<String, Integer> idTokens;
	private TablaSimbolos tablaSimbolos;
	private AccionSemantica[][] matAcciones;
	
	
 	public AnalizadorLexico(String ruta, TablaSimbolos ts) {
		this.inicializarMatTrans();
		this.inicializarIdTokens();
		this.inicializarMatAcciones();
		this.saltoLinea = false;
		this.setConcatActual("");
		this.tablaSimbolos = ts;
		this.archivo = new File(ruta);
        if (!archivo.exists()) {
            System.out.println("El archivo especificado no existe: " + ruta);
            return;
        }

        // Leer el contenido del archivo línea por línea
        try {
            this.lineasCodigo = (ArrayList<String>) Files.readAllLines(Paths.get(ruta));
            System.out.println("Contenido del archivo con detección de saltos de línea:");
            
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo.");
            e.printStackTrace();
        }
	}

 	private void inicializarMatTrans() {
		this.matTrans = new Integer[][]{
				{8,	9,	1,	-1,	0, -1, 11, -1, 12, -1, 10, -1, 13, 2, 0, 15, -1, 16, -1, -1, 1},
				{-1, -1, 1,	-1, -1, -1, -1, -1, -1, -1,	-1,	-1,	-1,	-1,	1, -1, -1, -1,	5,	3, -1, 1},
				{-1, -1, 4,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	1, -1, -1, -1, -1, 3, -1, 4},
				{-1, -1, 3, -1, -1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	3, -1, -1, -1,	5,	3,	-1,	3},
				{-1, -1, -1, -1, -1, -1, -1, -1, -1, 6, -1,	-1,	-1,	4, -1, -1, -1,	-1,	-1,	-1,	4},
				{-1, -1, 7, -1,	-1,	-1,	-1,	-1,	-1,	6, -1, -1, -1, 7, -1, -1, -1, -1, -1, -1, 7},
				{-1, -1, 7,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	7, -1, -1, -1, -1, -1, -1, 7},
				{-1, -1, 7,	-1, -1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1, 7},
				{8,	8,	8,	8,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	8, -1, -1, -1, 8, -1, -1, 8},
				{9,	9,	9,	9,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	-1,	9,	-1,	-1,	-1,	9,	-1,	-1,	9},
				{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
				{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
				{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
				{13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 13, 0,	13,	13,	13,	13,	13,	13},
				{14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 0, 14,	14,	14,	14,	14,	14},
				{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, -1, 15, 15, 15, 15},
				{16, 16, 16, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, 16, -1, -1, 16}
		};
	}
	
 	private void inicializarMatAcciones() {
		AccionSemantica.AS0 as0 = new AccionSemantica.AS0();
		AccionSemantica.AS1 as1 = new AccionSemantica.AS1();
		AccionSemantica.ASE1 ase1 = new AccionSemantica.ASE1();
		AccionSemantica.ASF1 asf1 = new AccionSemantica.ASF1();
		AccionSemantica.ASBR asbr = new AccionSemantica.ASBR();
		AccionSemantica.ASBR2 asbr2 = new AccionSemantica.ASBR2();
		AccionSemantica.ASFBR asfbr = new AccionSemantica.ASFBR();
		AccionSemantica.ASFBR2 asfbr2 = new AccionSemantica.ASFBR2();
		AccionSemantica.ASFBR3 asfbr3 = new AccionSemantica.ASFBR3();
		AccionSemantica.ASFBR4 asfbr4 = new AccionSemantica.ASFBR4();
		AccionSemantica.ASFBR5 asfbr5 = new AccionSemantica.ASFBR5();
		AccionSemantica.ASE2 ase2 = new AccionSemantica.ASE2();
		AccionSemantica.ASE3 ase3 = new AccionSemantica.ASE3();
		AccionSemantica.ASF2 asf2 = new AccionSemantica.ASF2();
		AccionSemantica.ASE4 ase4 = new AccionSemantica.ASE4();
		AccionSemantica.ASE5 ase5 = new AccionSemantica.ASE5();
		AccionSemantica.ASE6 ase6 = new AccionSemantica.ASE6();
		AccionSemantica.ASE7 ase7 = new AccionSemantica.ASE7();
		AccionSemantica.ASE8 ase8 = new AccionSemantica.ASE8();
		AccionSemantica.ASF3 asf3 = new AccionSemantica.ASF3();
		AccionSemantica.ASF4 asf4 = new AccionSemantica.ASF4();
		AccionSemantica.ASF5 asf5 = new AccionSemantica.ASF5();
		AccionSemantica.ASF6 asf6 = new AccionSemantica.ASF6();
		AccionSemantica.ASF7 asf7 = new AccionSemantica.ASF7();
		AccionSemantica.ASF8 asf8 = new AccionSemantica.ASF8();
		AccionSemantica.ASE9 ase9 = new AccionSemantica.ASE9();
		AccionSemantica.ASE10 ase10 = new AccionSemantica.ASE10();
		AccionSemantica.ASFBR6 asfbr6 = new AccionSemantica.ASFBR6();
		AccionSemantica.ASE11 ase11 = new AccionSemantica.ASE11();
        
 		this.matAcciones = new AccionSemantica[][]{
			{as1, as1, as1, ase1, as0, asf1, as1, asf1,	as1, asf1, as1,	asf1, as1, as1, asbr, as1, ase2, as1, asf1, ase3, as1},
			{asf2, asf2, as1, asf2,	asf1,	asf2,	asf2,	asf2,	asf2,	asf2,	asf2,	asf2,	asf2,	as1,	asfbr,	asf2,	asf2,	ase4,	as1,	asf2,	as1},
			{asf2,	asf2,	ase5,	asf2,	asf1,	asf2,	asf2,	asf2,	asf2,	asf2,	asf2,	asf2,	asf2,	asf1,	asfbr ,asf2,	asf2,	ase4, as1,	asf2,	as1},
			{asf2,	asf2,	as1,	asf2	,asf1	,asf2,	asf2	,asf2	,asf2,	ase6,	asf2	,asf2	,asf2,	as1	,asfbr	,asf2	,asf2	,as1	,ase7,	asf2	,as1},
			{asf2,	asf2,	ase5,	asf2,	asf1	,asf2	,asf2,	asf2	,asf2	,asf2	,asf2	,asf2,	asf2,	as1,	asfbr	,asf2,	asf2	,asf2,	asf2,	asf2,	as1},
			{ase8,	ase8, as1	,ase8	,ase8	,ase8,	ase8	,ase8,	ase8	,as1	,ase8,	ase8,	ase8,	as1,	asfbr2,	ase8	,ase8,	ase8	,ase8	,ase8,	as1},
			{ase8,	ase8,	as1,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	ase8,	as1,	asfbr2,	ase8,	ase8	,ase8,	ase8,	ase8,	as1},
			{asf1,	asf1,	as1	,asf1,	asf1,	asf1	,asf1,	asf1	,asf1	,asf1	,asf1	,asf1	,asf1	,as1	,asfbr	,asf1	,asf1	,asf1	,asf1	,asf1	,as1},
			{as1,	as1	,as1	,as1	,asf3	,asf4	,asf4	,asf4	,asf4	,asf4	,asf4	,asf4	,asf4	,as1	,asfbr3,	asf4,	asf4	,as1,	asf4,	asf4	,as1},
			{as1,	as1	,as1	,as1,	asf5	,asf6,	asf6,	asf6	,asf6	,asf6,	asf6,	asf6	,asf6	,as1,	asfbr4	,asf6	,asf6,	as1,	asf6,	asf6,	as1},
			{ase9	,ase9	,ase9,	ase9,	ase10,	ase9	,ase9,	asf1,	ase9	,ase9,	ase9,	ase9	,ase9	,ase9	,asfbr6,	ase9,	ase9	,ase9,	ase9,	ase9,	ase9},
			{asf2,	asf2,	asf2,	asf2	,asf1	,asf2,	asf2	,asf1,	asf2	,asf2,	asf2,	asf2	,asf2	,asf2,	asfbr	,asf2,	asf2,	asf2,	asf2,	asf2,	asf2},
			{ase9,	ase9,	ase9,	ase9,	ase10,	ase9,	ase9	,asf1,	ase9	,ase9	,ase9	,ase9	,ase9	,ase9	,asfbr6,	ase9	,ase9,	ase9	,ase9,	ase9,	ase9},
			{ase11,	ase11	,ase11,	ase11	,ase11	,ase11	,ase11	,ase11	,ase11	,ase11	,ase11	,ase11	,as1	,ase11	,asbr	,ase11,	ase11,	ase11	,ase11,	ase11,	ase11},
			{as1,	as1	,as1,	as1,	as1,	as1	,as1,	as1	,as1	,as1	,as1,	as1,	as1,	as1,	asbr,	as1,	as1,	as1,	as1,	as1,	as1},
			{as1,	as1,	as1,	as1	,as1,	as1,	as1	,as1	,as1	,as1	,as1	,as1	,as1	,as1,	asbr2,	as1,	asf1	,as1,	as1,	ase3,	as1},
			{as1,	as1,	as1	,as1	,asf7,	asf8,	asf8,	asf8	,asf8,	asf8,	asf8,	asf8,	asf8	,as1,	asfbr5,	asf8	,asf8	,as1	,asf8	,asf8,	as1}
		};
 	}

	private void inicializarIdTokens() {
		// Crear un mapa para almacenar los pares ID - Valor
        this.idTokens = new HashMap<>();
        idTokens.put("ID", 1);
        idTokens.put("Constantes", 2);
        idTokens.put("+", 3);
        idTokens.put("-", 4);
        idTokens.put(">", 5);
        idTokens.put("<", 6);
        idTokens.put(">=", 7);
        idTokens.put("<=", 8);
        idTokens.put("=", 9);
        idTokens.put(",", 10);
        idTokens.put(".", 11);
        idTokens.put(";", 12);
        idTokens.put("(", 13);
        idTokens.put(")", 14);
        idTokens.put(":=", 15);
        idTokens.put("_", 16);
        idTokens.put("/", 17);
        idTokens.put("*", 18);
        idTokens.put("cadena multilinea", 19);
        idTokens.put("}", 20);
        idTokens.put("{", 21);
        idTokens.put("GOTO", 22);
        idTokens.put("UP", 23);
        idTokens.put("DOWN", 24);
        idTokens.put("TRIPLE", 25);
        idTokens.put("FOR", 26);
        idTokens.put("ulongint", 27);
        idTokens.put("double", 28);
        idTokens.put("IF", 29);
        idTokens.put("THEN", 30);
        idTokens.put("ELSE", 31);
        idTokens.put("BEGIN", 32);
        idTokens.put("END", 33);
        idTokens.put("END_IF", 34);
        idTokens.put("OUTF", 35);
        idTokens.put("TYPEDEF", 36);
        idTokens.put("FUN", 37);
        idTokens.put("RET", 38);
	}
 	
	private int getColumna() {
		Character character = null;
		if (!finalArchivo()) {
			character = lineasCodigo.get(linea-1).charAt(pos);
		}
 		
 		if (saltoLinea) {
 			saltoLinea = false;
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
 	 	        
 	 	    case ' ':
 	 	        return 4;
 	 	        
 	 	    case '+': case '-':
 		        return 9;
 	 	        
 	 	    case '*': case '/': case '%':
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

 	 	    default:
 	 	        // Caso: otro (carácter no contemplado en los casos anteriores)
 	 	    	return 19;
 	 		}
 		}
 		
 	}

	
 	public Map<String, Integer> getIdTokens(){
 		return this.idTokens;
 	}
	
	public Tupla getToken() {
		while ((estado != -1)) {
			System.out.println(finalArchivo());
			int estado_anterior = estado;
			int col = getColumna();
			estado = matTrans[estado][col];
			System.out.println("Estado anterior: " + estado_anterior);
			System.out.println("Estado actual: " + estado);
			matAcciones[estado_anterior][col].ejecutar(this);
		}

		Tupla retorno = new Tupla(nroToken, concatActual);
		this.reset();
		return retorno;
	}
	
	public void concatenar() {
		setConcatActual(getConcatActual() + lineasCodigo.get(linea-1).charAt(pos));
	}
	
	private void reset() {
		this.concatActual = "";
		this.estado = 0;
		this.nroToken = "";
	}
	
	public boolean finalArchivo() {
		return lineasCodigo.size() < linea;
	}
	
	public void avanzarLinea() {
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
        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Pedir al usuario que elija el nombre del archivo (sin extensión)
        System.out.print("Ingresa el nombre del archivo (con extensión): ");
        String nombreArchivo = scanner.nextLine();
        scanner.close();
       
        // Asumir que la ruta del proyecto es el directorio actual
        String rutaProyecto = System.getProperty("user.dir");
        String rutaArchivo = rutaProyecto + File.separator + "codes" + File.separator + nombreArchivo;
        System.out.println(rutaArchivo);
        TablaSimbolos ts = new TablaSimbolos();
        AnalizadorLexico anal = new AnalizadorLexico(rutaArchivo, ts);
        
        while (!anal.finalArchivo()) {
        	Tupla fin = anal.getToken();
        	System.out.println(fin.getKey());
        	System.out.println(fin.getValue());
        }
        
    }

	public TablaSimbolos getTablaSimbolos() {
		return tablaSimbolos;
	}

	public String getNroToken() {
		return nroToken;
	}

	public void setNroToken(String nroToken) {
		this.nroToken = nroToken;
	}

	public String getConcatActual() {
		return concatActual;
	}

	public void setConcatActual(String concatActual) {
		this.concatActual = concatActual;
	}
    
}

