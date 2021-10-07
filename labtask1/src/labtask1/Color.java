package labtask1;

public class Color {
	private int code;
	private int quantity;
	
	public Color() {
	}
	
	public Color(int code, int quantity) {
		super();
		this.code = code;
		this.quantity = quantity;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Color [code=" + code + ", quantity=" + quantity + "]";
	}
	
	
}

