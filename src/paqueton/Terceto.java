package paqueton;

public class Terceto {
	private String operador;
	private String op1;
	private String op2;
	private String regAux;
	private String tipo;
	
	public Terceto(String op, String op1, String op2) {
		this.operador = op;
		this.op1 = op1;
		this.op2 = op2;
		this.setTipo("");
	}
	
	public Terceto(String op, String op1, String op2, String tipo) {
		this.operador = op;
		this.op1 = op1;
		this.op2 = op2;
		this.setTipo(tipo);
	}
	
	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	public String getOp2() {
		return op2;
	}

	public void setOp2(String op2) {
		this.op2 = op2;
	}



	public String getRegAux() {
		return regAux;
	}



	public void setRegAux(String regAux) {
		this.regAux = regAux;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return "[" + this.getOperador() + ", " + this.getOp1() + ", " + this.getOp2() +"]";
	}
}

