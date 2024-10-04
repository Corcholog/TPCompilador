package paqueton;

public class ErrorHandler {
	public static int cantErroresLexicos = 0;
	public static int cantErroresSintacticos = 0;
	public static String errores = "";
	
	public static String getErrores() {
		return ErrorHandler.errores;
	}
	
	public static void addErrorLexico(String e, int linea) {
		ErrorHandler.errores += "Error Lexico en linea " + linea + ": " + e + "\n";
		ErrorHandler.cantErroresLexicos+=1;
		
	}
	
	public static void addWarningLexico(String e, int linea) {
		ErrorHandler.errores += "Warning Lexico en linea " + linea + ": " + e + "\n";
		
	}
	
	public static void addErrorSintactico(String e, int linea) {
		ErrorHandler.errores += "Error Sintactico, en linea " + linea + " : " + e + "\n";
	}
	
	public static boolean huboError() {
		return (ErrorHandler.cantErroresLexicos + ErrorHandler.cantErroresSintacticos) > 0;
	}
	
	

}
