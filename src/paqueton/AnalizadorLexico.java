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

public class AnalizadorLexico {
	private int linea = 1;
	private int pos = 0;
	private int estado = 0;
	private ArrayList<String> lineasCodigo;
	private File archivo;
	private Map<Integer, String> idTokens;
	private Map<String, Map<String, String>> tablaSimbolos;
	
	public int getAlgo() {
		return 1;
	};

	public AnalizadorLexico(String ruta) {
		 // Crear un mapa para almacenar los pares ID - Valor
        this.idTokens = new HashMap<>();
        this.tablaSimbolos = new HashMap<String, Map<String, String>>();
        // Agregar los datos al mapa
        idTokens.put(1, "ID");
        idTokens.put(2, "Constantes");
        idTokens.put(3, "+");
        idTokens.put(4, "-");
        idTokens.put(5, ">");
        idTokens.put(6, "<");
        idTokens.put(7, ">=");
        idTokens.put(8, "<=");
        idTokens.put(9, "=");
        idTokens.put(10, ",");
        idTokens.put(11, ".");
        idTokens.put(12, ";");
        idTokens.put(13, "(");
        idTokens.put(14, ")");
        idTokens.put(15, ":=");
        idTokens.put(16, "_");
        idTokens.put(17, "/");
        idTokens.put(18, "*");
        idTokens.put(19, "cadena multilinea");
        idTokens.put(20, "}");
        idTokens.put(21, "{");
        idTokens.put(22, "GOTO");
        idTokens.put(23, "UP");
        idTokens.put(24, "DOWN");
        idTokens.put(25, "TRIPLE");
        idTokens.put(26, "FOR");
        idTokens.put(27, "ulongint");
        idTokens.put(28, "double");
        idTokens.put(29, "IF");
        idTokens.put(30, "THEN");
        idTokens.put(31, "ELSE");
        idTokens.put(32, "BEGIN");
        idTokens.put(33, "END");
        idTokens.put(34, "END_IF");
        idTokens.put(35, "OUTF");
        idTokens.put(36, "TYPEDEF");
        idTokens.put(37, "FUN");
        idTokens.put(38, "RET");
        
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
        AnalizadorLexico anal = new AnalizadorLexico(rutaArchivo);
        
    }
    
}

