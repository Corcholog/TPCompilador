package paqueton;

public class Terceto {
	private String operador;
	private String op1;
	private String op2;
	
	public Terceto(String op, String op1, String op2) {
		this.operador = op;
		this.op1 = op1;
		this.op2 = op2;
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

	
}

