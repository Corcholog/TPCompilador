package paqueton;

import java.util.ArrayList;
import java.util.Stack;

public class GeneradorCodigo {
	private ArrayList<Terceto> tercetos;
	private Stack<Integer> flujoControl;
	private boolean huboError = false;
	
	public GeneradorCodigo() {
		this.tercetos = new ArrayList<Terceto>();
		this.flujoControl = new Stack<Integer>();
	}
	
	public void push(int pos) {
		this.flujoControl.push(pos);
	}
	
	public void pop() {
		this.flujoControl.pop();
	}
	
	public Integer peek(){
		return this.flujoControl.peek();
	}
	
	public int getPosActual() {
		return this.tercetos.size()-1;
	}
	
	public int getCantTercetos() {
		return this.tercetos.size();
	}

	public ArrayList<Terceto> getTercetos() {
		return this.tercetos;
	}
	
	public void huboError() {
		this.huboError = true;
	}
	
	public String addTerceto(String op, String op1, String op2) {
		if(!huboError) {
			this.tercetos.add(new Terceto(op, op1, op2));
			return "[" + (this.tercetos.size()-1) + "]";
		}
		return "";
	}
	
	public Terceto getTerceto(int pos) {
		return this.tercetos.get(pos);
	}
	
	public void actualizarBI(int ref) {
		if(!huboError) {
			this.tercetos.get(this.flujoControl.peek()).setOp1("[" + String.valueOf(ref) + "]");
		}
		
	}
	
	public void actualizarBF(int ref) {
		if(!huboError) {
			this.tercetos.get(this.flujoControl.peek()).setOp2("[" + String.valueOf(ref) + "]");
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int cont = 0;
		for (Terceto terceto : tercetos) {
			sb.append("\n" + cont + ". (" + terceto.getOperador() + ", " + terceto.getOp1() + ", " + terceto.getOp2() +")");
			cont++;
		}
		return sb.toString();
	}
}
