package paqueton;

public class ErrorHandler {
	public static int cantErroresLexicos = 0;
	public static int cantErroresSintacticos = 0;
	public static String errores = "";
	private static GeneradorCodigo gc;
	
	public static String getErrores() {
		return ErrorHandler.errores;
	}
	
	public static void setGeneradorCodigo(GeneradorCodigo gc) {
		ErrorHandler.gc = gc;
	}
	
	public static void addErrorLexico(String e, int linea) {
		ErrorHandler.errores += "Error Lexico en linea " + linea + ": " + e + "\n";
		ErrorHandler.cantErroresLexicos+=1;
		ErrorHandler.huboErrorGC();
		
	}
	
	public static void addErrorSemantico(String e, int linea) {
		ErrorHandler.errores += "Error Semantico en linea " + linea + ": " + e + "\n";
		ErrorHandler.huboErrorGC();
	}
	
	public static void addWarningLexico(String e, int linea) {
		ErrorHandler.errores += "Warning Lexico en linea " + linea + ": " + e + "\n";
	}
	
	public static void huboErrorGC() {
		ErrorHandler.gc.huboError();
	}
	
	public static void addErrorSintactico(String e, int linea) {
		ErrorHandler.errores += "Error Sintactico, en linea " + linea + " : " + e + "\n";
		ErrorHandler.gc.huboError();
	}

}
