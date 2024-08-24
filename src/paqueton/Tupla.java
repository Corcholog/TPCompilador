package paqueton;

public class Tupla {
	private String u;
	private String v;
	
	public Tupla(String u, String v) {
		if (u == null) {
			this.u = "";
		}else {
			this.u = new String(u);
		}
		if (v == null) {
			this.v = "";
		}else {
			this.v = new String(v);
		}
		
	}
	
	public String getKey() {
		return u;
	}
	
	public String getValue() {
		return v;
	}
}
